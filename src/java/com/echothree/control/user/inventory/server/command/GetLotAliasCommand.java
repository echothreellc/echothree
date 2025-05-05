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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetLotAliasForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.inventory.server.control.LotControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetLotAliasCommand
        extends BaseSimpleCommand<GetLotAliasForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotAliasType.name(), SecurityRoles.Review.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetLotAliasCommand */
    public GetLotAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var lotControl = Session.getModelController(LotControl.class);
        var result = InventoryResultFactory.getGetLotAliasResult();
        var lotName = form.getLotName();
        var lot = lotControl.getLotByName(lotName);

        if(lot != null) {
            var lotAliasControl = Session.getModelController(LotAliasControl.class);
            var lotAliasTypeName = form.getLotAliasTypeName();
            var lotAliasType = lotAliasControl.getLotAliasTypeByName(lotAliasTypeName);

            if(lotAliasType != null) {
                var lotAlias = lotAliasControl.getLotAlias(lot, lotAliasType);

                if(lotAlias != null) {
                    result.setLotAlias(lotAliasControl.getLotAliasTransfer(getUserVisit(), lotAlias));
                } else {
                    addExecutionError(ExecutionErrors.UnknownLotAlias.name(), lotName, lotAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLotAliasTypeName.name(), lotAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotName.name(), lotName);
        }

        return result;
    }
    
}
