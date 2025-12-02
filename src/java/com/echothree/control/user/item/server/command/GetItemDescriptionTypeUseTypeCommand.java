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

import com.echothree.control.user.item.common.form.GetItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemDescriptionTypeUseTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetItemDescriptionTypeUseTypeCommand
        extends BaseSingleEntityCommand<ItemDescriptionTypeUseType, GetItemDescriptionTypeUseTypeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemDescriptionTypeUseType.name(), SecurityRoles.Review.name()
                        )
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemDescriptionTypeUseTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetItemDescriptionTypeUseTypeCommand */
    public GetItemDescriptionTypeUseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected ItemDescriptionTypeUseType getEntity() {
        var itemDescriptionTypeUseType = ItemDescriptionTypeUseTypeLogic.getInstance().getItemDescriptionTypeUseTypeByUniversalSpec(this, form, true);

        if(itemDescriptionTypeUseType != null) {
            sendEvent(itemDescriptionTypeUseType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return itemDescriptionTypeUseType;
    }

    @Override
    protected BaseResult getResult(ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        var itemDescriptionTypeUseTypeControl = Session.getModelController(ItemControl.class);
        var result = ItemResultFactory.getGetItemDescriptionTypeUseTypeResult();

        if(itemDescriptionTypeUseType != null) {
            result.setItemDescriptionTypeUseType(itemDescriptionTypeUseTypeControl.getItemDescriptionTypeUseTypeTransfer(getUserVisit(), itemDescriptionTypeUseType));
        }

        return result;
    }

}
