// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.index;

import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.edit.IndexFieldEdit;
import com.echothree.control.user.index.common.edit.IndexTypeDescriptionEdit;
import com.echothree.control.user.index.common.form.CreateIndexFieldForm;
import com.echothree.control.user.index.common.form.CreateIndexTypeDescriptionForm;
import com.echothree.control.user.index.common.form.EditIndexFieldForm;
import com.echothree.control.user.index.common.form.EditIndexTypeDescriptionForm;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.index.common.result.EditIndexFieldResult;
import com.echothree.control.user.index.common.result.EditIndexTypeDescriptionResult;
import com.echothree.control.user.index.common.spec.IndexFieldSpec;
import com.echothree.control.user.index.common.spec.IndexSpecFactory;
import com.echothree.control.user.index.common.spec.IndexTypeDescriptionSpec;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexTypeHandler
        extends BaseHandler {
    
    IndexService indexService;
    String indexTypeName;
    
    /** Creates a new instance of IndexTypeHandler */
    public IndexTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String indexTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.indexTypeName = indexTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexTypeDescription")) {
            IndexTypeDescriptionSpec spec = IndexSpecFactory.getIndexTypeDescriptionSpec();
            EditIndexTypeDescriptionForm editForm = IndexFormFactory.getEditIndexTypeDescriptionForm();

            spec.setIndexTypeName(indexTypeName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = indexService.editIndexTypeDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownIndexTypeDescription.name())) {
                    CreateIndexTypeDescriptionForm createForm = IndexFormFactory.getCreateIndexTypeDescriptionForm();

                    createForm.setIndexTypeName(indexTypeName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = indexService.createIndexTypeDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditIndexTypeDescriptionResult result = (EditIndexTypeDescriptionResult)executionResult.getResult();

                if(result != null) {
                    IndexTypeDescriptionEdit edit = (IndexTypeDescriptionEdit)result.getEdit();
                    String description = attrs.getValue("description");
                    boolean changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = indexService.editIndexTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = indexService.editIndexTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
        } else if(localName.equals("indexField")) {
            IndexFieldSpec spec = IndexSpecFactory.getIndexFieldSpec();
            EditIndexFieldForm editForm = IndexFormFactory.getEditIndexFieldForm();

            spec.setIndexTypeName(indexTypeName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = indexService.editIndexField(initialDataParser.getUserVisit(), editForm);

            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownIndexFieldName.name())) {
                    CreateIndexFieldForm createForm = IndexFormFactory.getCreateIndexFieldForm();

                    createForm.setIndexTypeName(indexTypeName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = indexService.createIndexField(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditIndexFieldResult result = (EditIndexFieldResult)executionResult.getResult();

                if(result != null) {
                    IndexFieldEdit edit = (IndexFieldEdit)result.getEdit();
                    String isDefault = attrs.getValue("isDefault");
                    String sortOrder = attrs.getValue("sortOrder");
                    boolean changed = false;
                    
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
                        
                        commandResult = indexService.editIndexField(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = indexService.editIndexField(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new IndexFieldHandler(initialDataParser, this, indexTypeName, spec.getIndexFieldName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("indexType")) {
            initialDataParser.popHandler();
        }
    }
    
}
