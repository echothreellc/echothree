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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.edit.ReturnTypeEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnTypeForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnTypeResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditReturnTypeCommand
        extends BaseAbstractEditCommand<ReturnTypeSpec, ReturnTypeEdit, EditReturnTypeResult, ReturnType, ReturnType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditReturnTypeCommand */
    public EditReturnTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnTypeResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnTypeResult();
    }

    @Override
    public ReturnTypeEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnTypeEdit();
    }

    ReturnKind returnKind;

    @Override
    public ReturnType getEntity(EditReturnTypeResult result) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        ReturnType returnType = null;
        var returnKindName = spec.getReturnKindName();

        returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind != null) {
            var returnTypeName = spec.getReturnTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
            } else { // EditMode.UPDATE
                returnType = returnPolicyControl.getReturnTypeByNameForUpdate(returnKind, returnTypeName);
            }

            if(returnType == null) {
                addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnKindName, returnTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnType;
    }

    @Override
    public ReturnType getLockEntity(ReturnType returnType) {
        return returnType;
    }

    @Override
    public void fillInResult(EditReturnTypeResult result, ReturnType returnType) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        result.setReturnType(returnPolicyControl.getReturnTypeTransfer(getUserVisit(), returnType));
    }

    Sequence returnSequence;

    @Override
    public void doLock(ReturnTypeEdit edit, ReturnType returnType) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnTypeDescription = returnPolicyControl.getReturnTypeDescription(returnType, getPreferredLanguage());
        var returnTypeDetail = returnType.getLastDetail();

        returnSequence = returnTypeDetail.getReturnSequence();

        edit.setReturnTypeName(returnTypeDetail.getReturnTypeName());
        edit.setReturnSequenceName(returnSequence == null ? null : returnSequence.getLastDetail().getSequenceName());
        edit.setIsDefault(returnTypeDetail.getIsDefault().toString());
        edit.setSortOrder(returnTypeDetail.getSortOrder().toString());

        if(returnTypeDescription != null) {
            edit.setDescription(returnTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ReturnType returnType) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindDetail = returnKind.getLastDetail();
        var returnTypeName = edit.getReturnTypeName();
        var duplicateReturnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);

        if(duplicateReturnType != null && !returnType.equals(duplicateReturnType)) {
            addExecutionError(ExecutionErrors.DuplicateReturnTypeName.name(), returnKindDetail.getReturnKindName(), returnTypeName);
        } else {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var returnSequenceType = returnKindDetail.getReturnSequenceType();
            var returnSequenceName = edit.getReturnSequenceName();

            returnSequence = returnSequenceName == null ? null : sequenceControl.getSequenceByName(returnSequenceType, returnSequenceName);

            if(returnSequenceName != null && returnSequence == null) {
                addExecutionError(ExecutionErrors.UnknownReturnSequenceName.name(), returnSequenceType.getLastDetail().getSequenceTypeName(), returnSequenceName);
            }
        }
    }

    @Override
    public void doUpdate(ReturnType returnType) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var partyPK = getPartyPK();
        var returnTypeDetailValue = returnPolicyControl.getReturnTypeDetailValueForUpdate(returnType);
        var returnTypeDescription = returnPolicyControl.getReturnTypeDescriptionForUpdate(returnType, getPreferredLanguage());
        var description = edit.getDescription();

        returnTypeDetailValue.setReturnTypeName(edit.getReturnTypeName());
        returnTypeDetailValue.setReturnSequencePK(returnSequence == null ? null : returnSequence.getPrimaryKey());
        returnTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnTypeFromValue(returnTypeDetailValue, partyPK);

        if(returnTypeDescription == null && description != null) {
            returnPolicyControl.createReturnTypeDescription(returnType, getPreferredLanguage(), description, partyPK);
        } else if(returnTypeDescription != null && description == null) {
            returnPolicyControl.deleteReturnTypeDescription(returnTypeDescription, partyPK);
        } else if(returnTypeDescription != null && description != null) {
            var returnTypeDescriptionValue = returnPolicyControl.getReturnTypeDescriptionValue(returnTypeDescription);

            returnTypeDescriptionValue.setDescription(description);
            returnPolicyControl.updateReturnTypeDescriptionFromValue(returnTypeDescriptionValue, partyPK);
        }
    }

}
