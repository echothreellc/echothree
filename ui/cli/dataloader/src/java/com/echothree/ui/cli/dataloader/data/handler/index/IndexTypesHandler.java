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

package com.echothree.ui.cli.dataloader.data.handler.index;

import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.edit.IndexTypeEdit;
import com.echothree.control.user.index.common.form.CreateIndexTypeForm;
import com.echothree.control.user.index.common.form.EditIndexTypeForm;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.index.common.result.EditIndexTypeResult;
import com.echothree.control.user.index.common.spec.IndexSpecFactory;
import com.echothree.control.user.index.common.spec.IndexTypeSpec;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexTypesHandler
        extends BaseHandler {
    
    IndexService indexService;
    
    /** Creates a new instance of IndexTypesHandler */
    public IndexTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            indexService = IndexUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("indexType")) {
            IndexTypeSpec spec = IndexSpecFactory.getIndexTypeSpec();
            EditIndexTypeForm editForm = IndexFormFactory.getEditIndexTypeForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = indexService.editIndexType(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownIndexTypeName.name())) {
                    CreateIndexTypeForm createForm = IndexFormFactory.getCreateIndexTypeForm();

                    createForm.set(getAttrsMap(attrs));

                    commandResult = indexService.createIndexType(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLog().error(commandResult);
                    }
                } else {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditIndexTypeResult result = (EditIndexTypeResult)executionResult.getResult();

                if(result != null) {
                    IndexTypeEdit edit = (IndexTypeEdit)result.getEdit();
                    String editComponentVendorName = edit.getComponentVendorName();
                    String editEntityTypeName = edit.getEntityTypeName();
                    String componentVendorName = attrs.getValue("componentVendorName");
                    String entityTypeName = attrs.getValue("entityTypeName");
                    String isDefault = attrs.getValue("isDefault");
                    String sortOrder = attrs.getValue("sortOrder");
                    boolean changed = false;
                    
                    if((editComponentVendorName == null && componentVendorName != null) || (editComponentVendorName != null && componentVendorName == null)
                        || (editComponentVendorName != null && componentVendorName != null && !editComponentVendorName.equals(componentVendorName))) {
                        edit.setComponentVendorName(componentVendorName);
                        changed = true;
                    }
                    
                    if((editEntityTypeName == null && entityTypeName != null) || (editEntityTypeName != null && entityTypeName == null)
                        || (editEntityTypeName != null && entityTypeName != null && !editEntityTypeName.equals(entityTypeName))) {
                        edit.setEntityTypeName(entityTypeName);
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
                        
                        commandResult = indexService.editIndexType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = indexService.editIndexType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new IndexTypeHandler(initialDataParser, this, spec.getIndexTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("indexTypes")) {
            initialDataParser.popHandler();
        }
    }

}
