// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.item;

import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.result.EditItemAliasTypeResult;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import java.util.Objects;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemAliasTypesHandler
        extends BaseHandler {

    ItemService itemService;
    
    /** Creates a new instance of ItemAliasTypesHandler */
    public ItemAliasTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("itemAliasType")) {
            var spec = ItemSpecFactory.getItemAliasTypeUniversalSpec();
            var editForm = ItemFormFactory.getEditItemAliasTypeForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = itemService.editItemAliasType(initialDataParser.getUserVisit(), editForm);

            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownItemAliasTypeName.name())) {
                    var createForm = ItemFormFactory.getCreateItemAliasTypeForm();

                    createForm.set(spec.get());

                    commandResult = itemService.createItemAliasType(initialDataParser.getUserVisit(), createForm);

                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemAliasTypeResult)executionResult.getResult();

                if(result != null) {
                    var edit = result.getEdit();
                    var validationPattern = attrs.getValue("validationPattern");
                    var itemAliasChecksumTypeName = attrs.getValue("itemAliasChecksumTypeName");
                    var allowMultiple = attrs.getValue("allowMultiple");
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;

                    if(!Objects.equals(edit.getValidationPattern(), validationPattern)) {
                        edit.setValidationPattern(validationPattern);
                        changed = true;
                    }

                    if(!edit.getItemAliasChecksumTypeName().equals(itemAliasChecksumTypeName)) {
                        edit.setItemAliasChecksumTypeName(itemAliasChecksumTypeName);
                        changed = true;
                    }

                    if(!edit.getAllowMultiple().equals(allowMultiple)) {
                        edit.setAllowMultiple(allowMultiple);
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

                        commandResult = itemService.editItemAliasType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);

                        commandResult = itemService.editItemAliasType(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new ItemAliasTypeHandler(initialDataParser, this, spec.getItemAliasTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("itemAliasTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
