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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemAliasForm;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemAliasChecksumTypeLogic;
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
import java.util.regex.Pattern;

public class CreateItemAliasCommand
        extends BaseSimpleCommand<CreateItemAliasForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAlias.name(), SecurityRoles.Create.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemAliasCommand */
    public CreateItemAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            var itemDetail = item.getLastDetail();
            var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                var itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                
                if(itemUnitOfMeasureType != null) {
                    var itemAliasTypeName = form.getItemAliasTypeName();
                    var itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);
                    
                    if(itemAliasType != null) {
                        var itemAliasTypeDetail = itemAliasType.getLastDetail();
                        var validationPattern = itemAliasTypeDetail.getValidationPattern();
                        var alias = form.getAlias();
                        
                        if(validationPattern != null) {
                            var pattern = Pattern.compile(validationPattern);
                            var m = pattern.matcher(alias);
                            
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
                                    var duplicateItem = itemControl.getItemByName(alias);

                                    if(duplicateItem == null) {
                                        var itemAlias = itemControl.getItemAliasByAlias(alias);

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
