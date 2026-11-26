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
import com.echothree.control.user.returnpolicy.common.edit.ReturnReasonDescriptionEdit;
import com.echothree.control.user.returnpolicy.common.result.EditReturnReasonDescriptionResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnReasonDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.returnpolicy.server.logic.ReturnReasonLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReason;
import com.echothree.model.data.returnpolicy.server.entity.ReturnReasonDescription;
import com.echothree.util.common.message.ExecutionErrors;
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
public class EditReturnReasonDescriptionCommand
        extends BaseEditCommand<ReturnReasonDescriptionSpec, ReturnReasonDescriptionEdit, EditReturnReasonDescriptionResult, ReturnReasonDescription, ReturnReason> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnReason.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    ReturnPolicyControl returnPolicyControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    ReturnReasonLogic returnReasonLogic;

    /** Creates a new instance of EditReturnReasonDescriptionCommand */
    public EditReturnReasonDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnReasonDescriptionResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnReasonDescriptionResult();
    }

    @Override
    public ReturnReasonDescriptionEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnReasonDescriptionEdit();
    }

    @Override
    public ReturnReasonDescription getEntity(EditReturnReasonDescriptionResult result) {
        ReturnReasonDescription returnReasonDescription = null;
        var returnKindName = spec.getReturnKindName();
        var returnReasonName = spec.getReturnReasonName();
        var languageIsoName = spec.getLanguageIsoName();
        var returnReason = returnReasonLogic.getReturnReasonByName(this, returnKindName, returnReasonName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            returnReasonDescription = returnPolicyControl.getReturnReasonDescription(returnReason, language,
                    editModeToEntityPermission(editMode));

            if(returnReasonDescription == null) {
                addExecutionError(ExecutionErrors.UnknownReturnReasonDescription.name(), returnKindName, returnReasonName, languageIsoName);
            }
        }

        return returnReasonDescription;
    }

    @Override
    public ReturnReason getLockEntity(ReturnReasonDescription returnReasonDescription) {
        return returnReasonDescription.getReturnReason();
    }

    @Override
    public void fillInResult(EditReturnReasonDescriptionResult result, ReturnReasonDescription returnReasonDescription) {
        result.setReturnReasonDescription(returnPolicyControl.getReturnReasonDescriptionTransfer(getUserVisit(), returnReasonDescription));
    }

    @Override
    public void doLock(ReturnReasonDescriptionEdit edit, ReturnReasonDescription returnReasonDescription) {
        edit.setDescription(returnReasonDescription.getDescription());
    }

    @Override
    public void doUpdate(ReturnReasonDescription returnReasonDescription) {
        var returnReasonDescriptionValue = returnPolicyControl.getReturnReasonDescriptionValue(returnReasonDescription);

        returnReasonDescriptionValue.setDescription(edit.getDescription());

        returnPolicyControl.updateReturnReasonDescriptionFromValue(returnReasonDescriptionValue, getPartyPK());
    }

}
