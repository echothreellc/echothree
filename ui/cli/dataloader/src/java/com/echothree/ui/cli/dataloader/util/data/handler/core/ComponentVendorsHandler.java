// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.dataloader.util.data.handler.core;

import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.result.EditComponentVendorResult;
import com.echothree.control.user.core.common.spec.CoreSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ComponentVendorsHandler
        extends BaseHandler {

    CoreService coreService;

    /** Creates a new instance of ComponentVendorsHandler */
    public ComponentVendorsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            coreService = CoreUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("componentVendor")) {
            var spec = CoreSpecFactory.getComponentVendorSpec();
            var editForm = CoreFormFactory.getEditComponentVendorForm();

            spec.set(getAttrsMap(attrs));

            var commandAction = getCommandAction(spec);
            getLogger().debug("Found: {}", commandAction);
            if(commandAction == null || commandAction.equals("create")) {
                var attrsMap = getAttrsMap(attrs);

                editForm.setSpec(spec);
                editForm.setEditMode(EditMode.LOCK);

                var commandResult = coreService.editComponentVendor(initialDataParser.getUserVisit(), editForm);

                if(commandResult.hasErrors()) {
                    if(commandResult.containsExecutionError(ExecutionErrors.UnknownComponentVendorName.name())) {
                        var createForm = CoreFormFactory.getCreateComponentVendorForm();

                        createForm.set(spec.get());

                        getLogger().debug("Creating: {}", spec.getComponentVendorName());
                        commandResult = coreService.createComponentVendor(initialDataParser.getUserVisit(), createForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditComponentVendorResult)executionResult.getResult();

                    getLogger().debug("Checking for modifications: {}", spec.getComponentVendorName());
                    if(result != null) {
                        updateEditFormValues(editForm, attrsMap, result);

                        commandResult = coreService.editComponentVendor(initialDataParser.getUserVisit(), editForm);
                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }

            initialDataParser.pushHandler(new ComponentVendorHandler(initialDataParser, this, spec.getComponentVendorName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("componentVendors")) {
            initialDataParser.popHandler();
        }
    }
}