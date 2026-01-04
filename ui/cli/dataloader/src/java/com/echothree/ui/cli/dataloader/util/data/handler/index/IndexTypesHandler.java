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

package com.echothree.ui.cli.dataloader.util.data.handler.index;

import com.echothree.control.user.index.common.IndexUtil;
import com.echothree.control.user.index.common.IndexService;
import com.echothree.control.user.index.common.edit.IndexTypeEdit;
import com.echothree.control.user.index.common.form.IndexFormFactory;
import com.echothree.control.user.index.common.result.EditIndexTypeResult;
import com.echothree.control.user.index.common.spec.IndexSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.EditMode;
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
            var spec = IndexSpecFactory.getIndexTypeSpec();
            var editForm = IndexFormFactory.getEditIndexTypeForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = indexService.editIndexType(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownIndexTypeName.name())) {
                    var createForm = IndexFormFactory.getCreateIndexTypeForm();

                    createForm.set(getAttrsMap(attrs));

                    commandResult = indexService.createIndexType(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditIndexTypeResult)executionResult.getResult();

                if(result != null) {
                    var edit = (IndexTypeEdit)result.getEdit();
                    var editComponentVendorName = edit.getComponentVendorName();
                    var editEntityTypeName = edit.getEntityTypeName();
                    var componentVendorName = attrs.getValue("componentVendorName");
                    var entityTypeName = attrs.getValue("entityTypeName");
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;
                    
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
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = indexService.editIndexType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
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
