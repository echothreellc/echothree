// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.index;

import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.edit.IndexFieldDescriptionEdit;
import com.echothree.control.user.index.common.form.CreateIndexFieldDescriptionForm;
import com.echothree.control.user.index.common.form.EditIndexFieldDescriptionForm;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.index.common.result.EditIndexFieldDescriptionResult;
import com.echothree.control.user.index.common.spec.IndexFieldDescriptionSpec;
import com.echothree.control.user.index.common.spec.IndexSpecFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexFieldHandler
        extends BaseHandler {
    
    IndexService indexService;
    String indexTypeName;
    String indexFieldName;
    
    /** Creates a new instance of IndexTypeHandler */
    public IndexFieldHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String indexTypeName, String indexFieldName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.indexTypeName = indexTypeName;
        this.indexFieldName = indexFieldName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexFieldDescription")) {
            IndexFieldDescriptionSpec spec = IndexSpecFactory.getIndexFieldDescriptionSpec();
            EditIndexFieldDescriptionForm editForm = IndexFormFactory.getEditIndexFieldDescriptionForm();

            spec.setIndexTypeName(indexTypeName);
            spec.setIndexFieldName(indexFieldName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = indexService.editIndexFieldDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownIndexFieldDescription.name())) {
                    CreateIndexFieldDescriptionForm createForm = IndexFormFactory.getCreateIndexFieldDescriptionForm();

                    createForm.setIndexTypeName(indexTypeName);
                    createForm.setIndexFieldName(indexFieldName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = indexService.createIndexFieldDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLog().error(commandResult);
                    }
                } else {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditIndexFieldDescriptionResult result = (EditIndexFieldDescriptionResult)executionResult.getResult();

                if(result != null) {
                    IndexFieldDescriptionEdit edit = (IndexFieldDescriptionEdit)result.getEdit();
                    String description = attrs.getValue("description");
                    boolean changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = indexService.editIndexFieldDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = indexService.editIndexFieldDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("indexField")) {
            initialDataParser.popHandler();
        }
    }
    
}
