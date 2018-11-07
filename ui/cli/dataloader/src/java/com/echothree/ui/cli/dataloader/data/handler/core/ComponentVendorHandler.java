// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateCommandForm;
import com.echothree.control.user.core.common.form.CreateComponentForm;
import com.echothree.control.user.core.common.form.CreateEntityTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ComponentVendorHandler
        extends BaseHandler {

    CoreService coreService;
    String componentVendorName;

    /** Creates a new instance of ComponentVendorHandler */
    public ComponentVendorHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            coreService = CoreUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
        this.componentVendorName = componentVendorName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("component")) {
            CreateComponentForm commandForm = CoreFormFactory.getCreateComponentForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.set(getAttrsMap(attrs));

            String commandAction = (String)commandForm.get("CommandAction");
            if(commandAction == null || commandAction.equals("create")) {
                coreService.createComponent(initialDataParser.getUserVisit(), commandForm);
            }

            initialDataParser.pushHandler(new ComponentHandler(initialDataParser, this, componentVendorName, commandForm.getComponentName()));
        } else if(localName.equals("entityType")) {
            CreateEntityTypeForm commandForm = CoreFormFactory.getCreateEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.set(getAttrsMap(attrs));

            String commandAction = (String)commandForm.get("CommandAction");
            if(commandAction == null || commandAction.equals("create")) {
                coreService.createEntityType(initialDataParser.getUserVisit(), commandForm);
            }

            initialDataParser.pushHandler(new EntityTypeHandler(initialDataParser, this, componentVendorName, commandForm.getEntityTypeName()));
        } else if(localName.equals("command")) {
            CreateCommandForm commandForm = CoreFormFactory.getCreateCommandForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.set(getAttrsMap(attrs));

            String commandAction = (String)commandForm.get("CommandAction");
            if(commandAction == null || commandAction.equals("create")) {
                coreService.createCommand(initialDataParser.getUserVisit(), commandForm);
            }

            initialDataParser.pushHandler(new CommandHandler(initialDataParser, this, componentVendorName, commandForm.getCommandName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("componentVendor")) {
            initialDataParser.popHandler();
        }
    }
}