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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonEdit;
import com.echothree.control.user.returnpolicy.common.result.EditReturnReasonResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnReasonLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditReturnReasonCommand
        extends BaseAbstractEditCommand<ReturnReasonUniversalSpec, ReturnReasonEdit, EditReturnReasonResult, ReturnReason, ReturnReason> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReason.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnReasonLogic returnReasonLogic;


    /** Creates a new instance of EditReturnReasonCommand */
    public EditReturnReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnReasonResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnReasonResult();
    }

    @Override
    public ReturnReasonEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnReasonEdit();
    }

    @Override
    public ReturnReason getEntity(EditReturnReasonResult result) {
        return returnReasonLogic.getReturnReasonByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public ReturnReason getLockEntity(ReturnReason returnReason) {
        return returnReason;
    }

    @Override
    public void fillInResult(EditReturnReasonResult result, ReturnReason returnReason) {
        result.setReturnReason(returnPolicyControl.getReturnReasonTransfer(getUserVisit(), returnReason));
    }

    @Override
    public void doLock(ReturnReasonEdit edit, ReturnReason returnReason) {
        var returnReasonDescription = returnPolicyControl.getReturnReasonDescription(returnReason, getPreferredLanguage());
        var returnReasonDetail = returnReason.getLastDetail();

        edit.setReturnReasonName(returnReasonDetail.getReturnReasonName());
        edit.setIsDefault(returnReasonDetail.getIsDefault().toString());
        edit.setSortOrder(returnReasonDetail.getSortOrder().toString());

        if(returnReasonDescription != null) {
            edit.setDescription(returnReasonDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ReturnReason returnReason) {
        var returnKind = returnReason.getLastDetail().getReturnKind();
        var returnReasonName = edit.getReturnReasonName();
        var duplicateReturnReason = returnPolicyControl.getReturnReasonByName(returnKind, returnReasonName);

        if(duplicateReturnReason != null && !returnReason.equals(duplicateReturnReason)) {
            addExecutionError(ExecutionErrors.DuplicateReturnReasonName.name(),
                    returnKind.getLastDetail().getReturnKindName(), returnReasonName);
        }
    }

    @Override
    public void doUpdate(ReturnReason returnReason) {
        var partyPK = getPartyPK();
        var returnReasonDetailValue = returnPolicyControl.getReturnReasonDetailValueForUpdate(returnReason);
        var returnReasonDescription = returnPolicyControl.getReturnReasonDescriptionForUpdate(returnReason, getPreferredLanguage());
        var description = edit.getDescription();

        returnReasonDetailValue.setReturnReasonName(edit.getReturnReasonName());
        returnReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnReasonFromValue(returnReasonDetailValue, partyPK);

        if(returnReasonDescription == null && description != null) {
            returnPolicyControl.createReturnReasonDescription(returnReason, getPreferredLanguage(), description, partyPK);
        } else if(returnReasonDescription != null && description == null) {
            returnPolicyControl.deleteReturnReasonDescription(returnReasonDescription, partyPK);
        } else if(returnReasonDescription != null && description != null) {
            var returnReasonDescriptionValue = returnPolicyControl.getReturnReasonDescriptionValue(returnReasonDescription);

            returnReasonDescriptionValue.setDescription(description);
            returnPolicyControl.updateReturnReasonDescriptionFromValue(returnReasonDescriptionValue, partyPK);
        }
    }

}
