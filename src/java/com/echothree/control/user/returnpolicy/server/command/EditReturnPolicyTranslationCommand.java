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
import com.echothree.control.user.returnpolicy.common.edit.ReturnPolicyTranslationEdit;
import com.echothree.control.user.returnpolicy.common.form.EditReturnPolicyTranslationForm;
import com.echothree.control.user.returnpolicy.common.result.EditReturnPolicyTranslationResult;
import com.echothree.control.user.returnpolicy.common.result.ReturnPolicyResultFactory;
import com.echothree.control.user.returnpolicy.common.spec.ReturnPolicyTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicyTranslation;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditReturnPolicyTranslationCommand
        extends BaseAbstractEditCommand<ReturnPolicyTranslationSpec, ReturnPolicyTranslationEdit, EditReturnPolicyTranslationResult, ReturnPolicyTranslation, ReturnPolicy> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ReturnPolicy.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("PolicyMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Policy", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditReturnPolicyTranslationCommand */
    public EditReturnPolicyTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditReturnPolicyTranslationResult getResult() {
        return ReturnPolicyResultFactory.getEditReturnPolicyTranslationResult();
    }

    @Override
    public ReturnPolicyTranslationEdit getEdit() {
        return ReturnPolicyEditFactory.getReturnPolicyTranslationEdit();
    }

    ReturnKind returnKind;
    
    @Override
    public ReturnPolicyTranslation getEntity(EditReturnPolicyTranslationResult result) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        ReturnPolicyTranslation returnPolicyTranslation = null;
        var returnKindName = spec.getReturnKindName();
        
        returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind != null) {
            var returnPolicyName = spec.getReturnPolicyName();
            var returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);

            if(returnPolicy != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        returnPolicyTranslation = returnPolicyControl.getReturnPolicyTranslation(returnPolicy, language);
                    } else { // EditMode.UPDATE
                        returnPolicyTranslation = returnPolicyControl.getReturnPolicyTranslationForUpdate(returnPolicy, language);
                    }

                    if(returnPolicyTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownReturnPolicyTranslation.name(), returnKindName, returnPolicyName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnKindName, returnPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnPolicyTranslation;
    }

    @Override
    public ReturnPolicy getLockEntity(ReturnPolicyTranslation returnPolicyTranslation) {
        return returnPolicyTranslation.getReturnPolicy();
    }

    @Override
    public void fillInResult(EditReturnPolicyTranslationResult result, ReturnPolicyTranslation returnPolicyTranslation) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);

        result.setReturnPolicyTranslation(returnPolicyControl.getReturnPolicyTranslationTransfer(getUserVisit(), returnPolicyTranslation));
    }

    MimeType policyMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(ReturnPolicyTranslationEdit edit, ReturnPolicyTranslation returnPolicyTranslation) {
        policyMimeType = returnPolicyTranslation.getPolicyMimeType();
        
        edit.setDescription(returnPolicyTranslation.getDescription());
        edit.setPolicyMimeTypeName(policyMimeType == null? null: policyMimeType.getLastDetail().getMimeTypeName());
        edit.setPolicy(returnPolicyTranslation.getPolicy());
    }

    @Override
    protected void canUpdate(ReturnPolicyTranslation returnPolicyTranslation) {
        var mimeTypeLogic = MimeTypeLogic.getInstance();
        var policyMimeTypeName = edit.getPolicyMimeTypeName();
        var policy = edit.getPolicy();
        
        policyMimeType = mimeTypeLogic.checkMimeType(this, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
    }
    
    @Override
    public void doUpdate(ReturnPolicyTranslation returnPolicyTranslation) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnPolicyTranslationValue = returnPolicyControl.getReturnPolicyTranslationValue(returnPolicyTranslation);
        
        returnPolicyTranslationValue.setDescription(edit.getDescription());
        returnPolicyTranslationValue.setPolicyMimeTypePK(policyMimeType == null? null: policyMimeType.getPrimaryKey());
        returnPolicyTranslationValue.setPolicy(edit.getPolicy());
        
        returnPolicyControl.updateReturnPolicyTranslationFromValue(returnPolicyTranslationValue, getPartyPK());
    }

}
