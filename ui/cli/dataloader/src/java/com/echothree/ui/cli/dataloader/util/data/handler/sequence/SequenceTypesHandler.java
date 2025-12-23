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

package com.echothree.ui.cli.dataloader.util.data.handler.sequence;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.SequenceService;
import com.echothree.control.user.sequence.common.edit.SequenceTypeEdit;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeResult;
import com.echothree.control.user.sequence.common.spec.SequenceSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.EditMode;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceTypesHandler
        extends BaseHandler {

    SequenceService sequenceService;

    /** Creates a new instance of SequenceTypesHandler */
    public SequenceTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceType")) {
            var spec = SequenceSpecFactory.getSequenceTypeSpec();
            var editForm = SequenceFormFactory.getEditSequenceTypeForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = sequenceService.editSequenceType(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSequenceTypeName.name())) {
                    var createForm = SequenceFormFactory.getCreateSequenceTypeForm();

                    createForm.set(getAttrsMap(attrs));

                    commandResult = sequenceService.createSequenceType(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceTypeResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SequenceTypeEdit)result.getEdit();
                    var prefix = attrs.getValue("prefix");
                    var sequenceEncoderTypeName = attrs.getValue("sequenceEncoderTypeName");
                    var sequenceChecksumTypeName = attrs.getValue("sequenceChecksumTypeName");
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;
                    
                    if(!edit.getPrefix().equals(prefix)) {
                        edit.setPrefix(prefix);
                        changed = true;
                    }

                    if(!edit.getSequenceEncoderTypeName().equals(sequenceEncoderTypeName)) {
                        edit.setSequenceEncoderTypeName(sequenceEncoderTypeName);
                        changed = true;
                    }

                    if(!edit.getSequenceChecksumTypeName().equals(sequenceChecksumTypeName)) {
                        edit.setSequenceChecksumTypeName(sequenceChecksumTypeName);
                        changed = true;
                    }

                    if(!edit.getIsDefault().equals(isDefault)) {
                        edit.setIsDefault(isDefault);
                        changed = true;
                    }

                    if(!edit.getSortOrder().equals(sortOrder)) {
                        edit.setSortOrder(sortOrder);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = sequenceService.editSequenceType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = sequenceService.editSequenceType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }

            initialDataParser.pushHandler(new SequenceTypeHandler(initialDataParser, this, spec.getSequenceTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceTypes")) {
            initialDataParser.popHandler();
        }
    }
}