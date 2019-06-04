// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemAliasForm;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.item.server.logic.ItemAliasChecksumTypeLogic;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDetail;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateItemAliasCommand
        extends BaseSimpleCommand<CreateItemAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemAliasCommand */
    public CreateItemAliasCommand(UserVisitPK userVisitPK, CreateItemAliasForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var uomControl = (UomControl)Session.getModelController(UomControl.class);
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            ItemDetail itemDetail = item.getLastDetail();
            UnitOfMeasureKind unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                
                if(itemUnitOfMeasureType != null) {
                    String itemAliasTypeName = form.getItemAliasTypeName();
                    ItemAliasType itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);
                    
                    if(itemAliasType != null) {
                        ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getLastDetail();
                        String validationPattern = itemAliasTypeDetail.getValidationPattern();
                        String alias = form.getAlias();
                        
                        if(validationPattern != null) {
                            Pattern pattern = Pattern.compile(validationPattern);
                            Matcher m = pattern.matcher(alias);
                            
                            if(!m.matches()) {
                                addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            ItemAliasChecksumTypeLogic.getInstance().checkItemAliasChecksum(this, itemAliasType, alias);

                            if(!hasExecutionErrors()) {
                                if(!itemAliasTypeDetail.getAllowMultiple()) {
                                    if(itemControl.countItemAliases(item, unitOfMeasureType, itemAliasType) != 0) {
                                        addExecutionError(ExecutionErrors.MultipleItemAliasesNotAllowed.name(), itemName, unitOfMeasureTypeName, itemAliasTypeName);
                                    }
                                }

                                if(!hasExecutionErrors()) {
                                    Item duplicateItem = itemControl.getItemByName(alias);

                                    if(duplicateItem == null) {
                                        ItemAlias itemAlias = itemControl.getItemAliasByAlias(alias);

                                        if(itemAlias == null) {
                                            itemControl.createItemAlias(item, unitOfMeasureType, itemAliasType, alias, getPartyPK());
                                        } else {
                                            addExecutionError(ExecutionErrors.DuplicateItemAlias.name(), itemAlias.getItem().getLastDetail().getItemName(),
                                                    itemAlias.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName(), itemAlias.getAlias());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.AliasDuplicatesItemName.name(), duplicateItem.getLastDetail().getItemName());
                                    }
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemAliasType.name(), itemAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
