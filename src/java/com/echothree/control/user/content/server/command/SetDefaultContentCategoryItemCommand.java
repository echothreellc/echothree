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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.SetDefaultContentCategoryItemForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultContentCategoryItemCommand
        extends BaseSimpleCommand<SetDefaultContentCategoryItemForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCategoryItem.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultContentCategoryItemCommand */
    public SetDefaultContentCategoryItemCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentCollectionName = form.getContentCollectionName();
        var contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            var contentCatalogName = form.getContentCatalogName();
            var contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog != null) {
                var contentCategoryName = form.getContentCategoryName();
                var contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                
                if(contentCategory != null) {
                    var itemControl = Session.getModelController(ItemControl.class);
                    var itemName = form.getItemName();
                    var item = itemControl.getItemByName(itemName);
                    
                    if(item != null) {
                        var inventoryControl = Session.getModelController(InventoryControl.class);
                        var inventoryConditionName = form.getInventoryConditionName();
                        var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                        
                        if(inventoryCondition != null) {
                            var uomControl = Session.getModelController(UomControl.class);
                            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                            var itemDetail = item.getLastDetail();
                            var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                            
                            if(unitOfMeasureType != null) {
                                var accountingControl = Session.getModelController(AccountingControl.class);
                                var currencyIsoName = form.getCurrencyIsoName();
                                var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                                
                                if(currency != null) {
                                    var contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog,
                                            item, inventoryCondition, unitOfMeasureType, currency);
                                    
                                    if(contentCatalogItem != null) {
                                        var contentCategoryItemValue = contentControl.getContentCategoryItemValueForUpdate(contentCategory,
                                                contentCatalogItem);
                                        
                                        if(contentCategoryItemValue != null) {
                                            contentCategoryItemValue.setIsDefault(Boolean.TRUE);
                                            
                                            ContentLogic.getInstance().updateContentCategoryItemFromValue(contentCategoryItemValue, getPartyPK());
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownContentCategoryItem.name(),
                                                    contentCollection.getLastDetail().getContentCollectionName(),
                                                    contentCatalog.getLastDetail().getContentCatalogName(),
                                                    contentCategory.getLastDetail().getContentCategoryName(),
                                                    item.getLastDetail().getItemName(),
                                                    inventoryCondition.getLastDetail().getInventoryConditionName(),
                                                    unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                                    currency.getCurrencyIsoName());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownContentCatalogItem.name(),
                                                contentCollection.getLastDetail().getContentCollectionName(),
                                                contentCatalog.getLastDetail().getContentCatalogName(),
                                                item.getLastDetail().getItemName(),
                                                inventoryCondition.getLastDetail().getInventoryConditionName(),
                                                unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(),
                                                currency.getCurrencyIsoName());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(),
                            contentCollection.getLastDetail().getContentCollectionName(),
                            contentCatalog.getLastDetail().getContentCatalogName(), contentCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(),
                        contentCollection.getLastDetail().getContentCollectionName(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }
        
        return null;
    }
    
}
