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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.CreateLotAliasForm;
import com.echothree.control.user.inventory.server.command.util.LotAliasUtil;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDetail;
import com.echothree.model.data.inventory.server.entity.LotType;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateLotAliasCommand
        extends BaseSimpleCommand<CreateLotAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateLotAliasCommand */
    public CreateLotAliasCommand(UserVisitPK userVisitPK, CreateLotAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(LotAliasUtil.getInstance().getSecurityRoleGroupNameByLotTypeSpec(form), SecurityRoles.Create.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String lotTypeName = form.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType != null) {
            String lotName = form.getLotName();
            Lot lot = inventoryControl.getLotByName(lotType, lotName);

            if(lot != null) {
                String lotAliasTypeName = form.getLotAliasTypeName();
                LotAliasType lotAliasType = inventoryControl.getLotAliasTypeByName(lotType, lotAliasTypeName);

                if(lotAliasType != null) {
                    LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetail();
                    String validationPattern = lotAliasTypeDetail.getValidationPattern();
                    String alias = form.getAlias();

                    if(validationPattern != null) {
                        Pattern pattern = Pattern.compile(validationPattern);
                        Matcher m = pattern.matcher(alias);

                        if(!m.matches()) {
                            addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                        }
                    }

                    if(!hasExecutionErrors()) {
                        if(inventoryControl.getLotAlias(lot, lotAliasType) == null && inventoryControl.getLotAliasByAlias(lotAliasType, alias) == null) {
                            inventoryControl.createLotAlias(lot, lotAliasType, alias, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateLotAlias.name(), lotTypeName, lotName, lotAliasTypeName, alias);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLotAliasTypeName.name(), lotTypeName, lotAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLotName.name(), lotTypeName, lotName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }

        return null;
    }
    
}
