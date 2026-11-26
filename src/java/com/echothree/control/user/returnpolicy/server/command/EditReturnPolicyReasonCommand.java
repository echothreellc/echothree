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
import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyReasonEdit;
import com.echothree.control.user.returnpolicy.common.result.EditReturnPolicyReasonResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicyReasonSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnPolicyReasonLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyReason;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditReturnPolicyReasonCommand
        extends BaseEditCommand<ReturnPolicyReasonSpec, ReturnPolicyReasonEdit, EditReturnPolicyReasonResult, ReturnPolicyReason, ReturnPolicy> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicyReason.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnPolicyReasonLogic returnPolicyReasonLogic;

    /** Creates a new instance of EditReturnPolicyReasonCommand */
    public EditReturnPolicyReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnPolicyReasonResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnPolicyReasonResult();
    }

    @Override
    public ReturnPolicyReasonEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnPolicyReasonEdit();
    }

    @Override
    public ReturnPolicyReason getEntity(EditReturnPolicyReasonResult result) {
        return returnPolicyReasonLogic.getReturnPolicyReason(this, spec.getReturnKindName(), spec.getReturnPolicyName(),
                spec.getReturnReasonName(), editModeToEntityPermission(editMode));
    }

    @Override
    public ReturnPolicy getLockEntity(ReturnPolicyReason returnPolicyReason) {
        return returnPolicyReason.getReturnPolicy();
    }

    @Override
    public void fillInResult(EditReturnPolicyReasonResult result, ReturnPolicyReason returnPolicyReason) {
        result.setReturnPolicyReason(returnPolicyControl.getReturnPolicyReasonTransfer(getUserVisit(), returnPolicyReason));
    }

    @Override
    public void doLock(ReturnPolicyReasonEdit edit, ReturnPolicyReason returnPolicyReason) {
        edit.setIsDefault(returnPolicyReason.getIsDefault().toString());
        edit.setSortOrder(returnPolicyReason.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ReturnPolicyReason returnPolicyReason) {
        var returnPolicyReasonValue = returnPolicyReason.getReturnPolicyReasonValue().clone();

        returnPolicyReasonValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnPolicyReasonValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnPolicyReasonFromValue(returnPolicyReasonValue, getPartyPK());
    }

}
