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

import com.echothree.control.user.returnpolicy.common.edit.ReturnKindDescriptionEdit;
import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyEditFactory;
import com.echothree.control.user.returnpolicy.common.form.EditReturnKindDescriptionForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnKindDescriptionResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKindDescription;
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
public class EditReturnKindDescriptionCommand
        extends BaseAbstractEditCommand<ReturnKindDescriptionSpec, ReturnKindDescriptionEdit, EditReturnKindDescriptionResult, ReturnKindDescription, ReturnKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditReturnKindDescriptionCommand */
    public EditReturnKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnKindDescriptionResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnKindDescriptionResult();
    }

    @Override
    public ReturnKindDescriptionEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnKindDescriptionEdit();
    }

    @Override
    public ReturnKindDescription getEntity(EditReturnKindDescriptionResult result) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        ReturnKindDescription returnKindDescription = null;
        var returnKindName = spec.getReturnKindName();
        var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    returnKindDescription = returnPolicyControl.getReturnKindDescription(returnKind, language);
                } else { // EditMode.UPDATE
                    returnKindDescription = returnPolicyControl.getReturnKindDescriptionForUpdate(returnKind, language);
                }

                if(returnKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownReturnKindDescription.name(), returnKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnKindDescription;
    }

    @Override
    public ReturnKind getLockEntity(ReturnKindDescription returnKindDescription) {
        return returnKindDescription.getReturnKind();
    }

    @Override
    public void fillInResult(EditReturnKindDescriptionResult result, ReturnKindDescription returnKindDescription) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        result.setReturnKindDescription(returnPolicyControl.getReturnKindDescriptionTransfer(getUserVisit(), returnKindDescription));
    }

    @Override
    public void doLock(ReturnKindDescriptionEdit edit, ReturnKindDescription returnKindDescription) {
        edit.setDescription(returnKindDescription.getDescription());
    }

    @Override
    public void doUpdate(ReturnKindDescription returnKindDescription) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindDescriptionValue = returnPolicyControl.getReturnKindDescriptionValue(returnKindDescription);

        returnKindDescriptionValue.setDescription(edit.getDescription());

        returnPolicyControl.updateReturnKindDescriptionFromValue(returnKindDescriptionValue, getPartyPK());
    }

}
