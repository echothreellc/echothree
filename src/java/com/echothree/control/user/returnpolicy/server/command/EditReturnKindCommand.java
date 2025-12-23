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

import com.echothree.control.user.returnpolicy.common.edit.ReturnKindEdit;
import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.form.EditReturnKindForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnKindResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnKindSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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
public class EditReturnKindCommand
        extends BaseAbstractEditCommand<ReturnKindSpec, ReturnKindEdit, EditReturnKindResult, ReturnKind, ReturnKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnSequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditReturnKindCommand */
    public EditReturnKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnKindResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnKindResult();
    }

    @Override
    public ReturnKindEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnKindEdit();
    }

    @Override
    public ReturnKind getEntity(EditReturnKindResult result) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        ReturnKind returnKind;
        var returnKindName = spec.getReturnKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        } else { // EditMode.UPDATE
            returnKind = returnPolicyControl.getReturnKindByNameForUpdate(returnKindName);
        }

        if(returnKind == null) {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnKind;
    }

    @Override
    public ReturnKind getLockEntity(ReturnKind returnKind) {
        return returnKind;
    }

    @Override
    public void fillInResult(EditReturnKindResult result, ReturnKind returnKind) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        result.setReturnKind(returnPolicyControl.getReturnKindTransfer(getUserVisit(), returnKind));
    }

    SequenceType returnSequenceType;

    @Override
    public void doLock(ReturnKindEdit edit, ReturnKind returnKind) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindDescription = returnPolicyControl.getReturnKindDescription(returnKind, getPreferredLanguage());
        var returnKindDetail = returnKind.getLastDetail();

        returnSequenceType = returnKindDetail.getReturnSequenceType();

        edit.setReturnKindName(returnKindDetail.getReturnKindName());
        edit.setReturnSequenceTypeName(returnSequenceType.getLastDetail().getSequenceTypeName());
        edit.setIsDefault(returnKindDetail.getIsDefault().toString());
        edit.setSortOrder(returnKindDetail.getSortOrder().toString());

        if(returnKindDescription != null) {
            edit.setDescription(returnKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ReturnKind returnKind) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindName = edit.getReturnKindName();
        var duplicateReturnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(duplicateReturnKind != null && !returnKind.equals(duplicateReturnKind)) {
            addExecutionError(ExecutionErrors.DuplicateReturnKindName.name(), returnKindName);
        } else {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var returnSequenceTypeName = edit.getReturnSequenceTypeName();

            returnSequenceType = sequenceControl.getSequenceTypeByName(returnSequenceTypeName);

            if(returnSequenceType == null) {
                addExecutionError(ExecutionErrors.UnknownReturnSequenceTypeName.name(), returnSequenceTypeName);
            }
        }
    }

    @Override
    public void doUpdate(ReturnKind returnKind) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var partyPK = getPartyPK();
        var returnKindDetailValue = returnPolicyControl.getReturnKindDetailValueForUpdate(returnKind);
        var returnKindDescription = returnPolicyControl.getReturnKindDescriptionForUpdate(returnKind, getPreferredLanguage());
        var description = edit.getDescription();

        returnKindDetailValue.setReturnKindName(edit.getReturnKindName());
        returnKindDetailValue.setReturnSequenceTypePK(returnSequenceType.getPrimaryKey());
        returnKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        returnKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        returnPolicyControl.updateReturnKindFromValue(returnKindDetailValue, partyPK);

        if(returnKindDescription == null && description != null) {
            returnPolicyControl.createReturnKindDescription(returnKind, getPreferredLanguage(), description, partyPK);
        } else if(returnKindDescription != null && description == null) {
            returnPolicyControl.deleteReturnKindDescription(returnKindDescription, partyPK);
        } else if(returnKindDescription != null && description != null) {
            var returnKindDescriptionValue = returnPolicyControl.getReturnKindDescriptionValue(returnKindDescription);

            returnKindDescriptionValue.setDescription(description);
            returnPolicyControl.updateReturnKindDescriptionFromValue(returnKindDescriptionValue, partyPK);
        }
    }

}
