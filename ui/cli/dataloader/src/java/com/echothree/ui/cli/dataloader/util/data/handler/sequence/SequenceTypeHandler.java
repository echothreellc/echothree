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

import com.echothree.control.user.sequence.common.SequenceService;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.edit.SequenceTypeDescriptionEdit;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeDescriptionResult;
import com.echothree.control.user.sequence.common.spec.SequenceSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceTypeHandler
        extends BaseHandler {

    SequenceService sequenceService;
    String sequenceTypeName;

    /** Creates a new instance of SequenceTypeHandler */
    public SequenceTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String sequenceTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.sequenceTypeName = sequenceTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceTypeDescription")) {
            var spec = SequenceSpecFactory.getSequenceTypeDescriptionSpec();
            var editForm = SequenceFormFactory.getEditSequenceTypeDescriptionForm();

            spec.setSequenceTypeName(sequenceTypeName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = sequenceService.editSequenceTypeDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSequenceTypeDescription.name())) {
                    var createForm = SequenceFormFactory.getCreateSequenceTypeDescriptionForm();

                    createForm.setSequenceTypeName(sequenceTypeName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = sequenceService.createSequenceTypeDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSequenceTypeDescriptionResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SequenceTypeDescriptionEdit)result.getEdit();
                    var description = attrs.getValue("description");
                    var changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = sequenceService.editSequenceTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = sequenceService.editSequenceTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
        } else if(localName.equals("sequence")) {
            var commandForm = SequenceFormFactory.getCreateSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.set(getAttrsMap(attrs));

            var commandResult = sequenceService.createSequence(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                initialDataParser.pushHandler(new SequenceHandler(initialDataParser, this, sequenceTypeName, commandForm.getSequenceName()));
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceType")) {
            initialDataParser.popHandler();
        }
    }
}