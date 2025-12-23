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

package com.echothree.ui.cli.dataloader.util.data.handler.item;

import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemVolumeTypeHandler
        extends BaseHandler {

    ItemService itemService;
    String itemVolumeTypeName;
    
    /** Creates a new instance of ItemVolumeTypeHandler */
    public ItemVolumeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String itemVolumeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.itemVolumeTypeName = itemVolumeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("itemVolumeTypeDescription")) {
            var spec = ItemSpecFactory.getItemVolumeTypeDescriptionSpec();
            var editForm = ItemFormFactory.getEditItemVolumeTypeDescriptionForm();

            spec.setItemVolumeTypeName(itemVolumeTypeName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = itemService.editItemVolumeTypeDescription(initialDataParser.getUserVisit(), editForm);

            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownItemVolumeTypeDescription.name())) {
                    var createForm = ItemFormFactory.getCreateItemVolumeTypeDescriptionForm();

                    createForm.set(spec.get());

                    commandResult = itemService.createItemVolumeTypeDescription(initialDataParser.getUserVisit(), createForm);

                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditItemVolumeTypeDescriptionResult)executionResult.getResult();

                if(result != null) {
                    var edit = result.getEdit();
                    var description = attrs.getValue("description");
                    var changed = false;

                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);

                        commandResult = itemService.editItemVolumeTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);

                        commandResult = itemService.editItemVolumeTypeDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("itemVolumeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
