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
import com.echothree.control.user.returnpolicy.common.edit.ReturnTypeShippingMethodEdit;
import com.echothree.control.user.returnpolicy.common.result.EditReturnTypeShippingMethodResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnTypeShippingMethodSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnTypeShippingMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeShippingMethod;
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
public class EditReturnTypeShippingMethodCommand
        extends BaseAbstractEditCommand<ReturnTypeShippingMethodSpec, ReturnTypeShippingMethodEdit, EditReturnTypeShippingMethodResult, ReturnTypeShippingMethod, ReturnType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnTypeShippingMethod.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    ReturnTypeShippingMethodLogic returnTypeShippingMethodLogic;

    /** Creates a new instance of EditReturnTypeShippingMethodCommand */
    public EditReturnTypeShippingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnTypeShippingMethodResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnTypeShippingMethodResult();
    }

    @Override
    public ReturnTypeShippingMethodEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnTypeShippingMethodEdit();
    }

    @Override
    public ReturnTypeShippingMethod getEntity(EditReturnTypeShippingMethodResult result) {
        return returnTypeShippingMethodLogic.getReturnTypeShippingMethod(this, spec.getReturnKindName(), spec.getReturnTypeName(),
                spec.getShippingMethodName(), editModeToEntityPermission(editMode));
    }

    @Override
    public ReturnType getLockEntity(ReturnTypeShippingMethod returnTypeShippingMethod) {
        return returnTypeShippingMethod.getReturnType();
    }

    @Override
    public void fillInResult(EditReturnTypeShippingMethodResult result, ReturnTypeShippingMethod returnTypeShippingMethod) {
        result.setReturnTypeShippingMethod(returnPolicyControl.getReturnTypeShippingMethodTransfer(getUserVisit(), returnTypeShippingMethod));
    }

    @Override
    public void doLock(ReturnTypeShippingMethodEdit edit, ReturnTypeShippingMethod returnTypeShippingMethod) {
        edit.setIsDefault(returnTypeShippingMethod.getIsDefault().toString());
        edit.setSortOrder(returnTypeShippingMethod.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ReturnTypeShippingMethod returnTypeShippingMethod) {
        var returnTypeShippingMethodValue = returnTypeShippingMethod.getReturnTypeShippingMethodValue().clone();

        returnTypeShippingMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnTypeShippingMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, getPartyPK());
    }

}
