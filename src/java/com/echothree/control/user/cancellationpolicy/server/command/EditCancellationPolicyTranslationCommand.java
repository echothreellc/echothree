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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyTranslationEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationPolicyTranslationForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationPolicyTranslationResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicyTranslationSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyTranslation;
import com.echothree.model.data.core.server.entity.MimeType;
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
public class EditCancellationPolicyTranslationCommand
        extends BaseAbstractEditCommand<CancellationPolicyTranslationSpec, CancellationPolicyTranslationEdit, EditCancellationPolicyTranslationResult, CancellationPolicyTranslation, CancellationPolicy> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicy.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("PolicyMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Policy", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditCancellationPolicyTranslationCommand */
    public EditCancellationPolicyTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCancellationPolicyTranslationResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationPolicyTranslationResult();
    }

    @Override
    public CancellationPolicyTranslationEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationPolicyTranslationEdit();
    }

    CancellationKind cancellationKind;
    
    @Override
    public CancellationPolicyTranslation getEntity(EditCancellationPolicyTranslationResult result) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        CancellationPolicyTranslation cancellationPolicyTranslation = null;
        var cancellationKindName = spec.getCancellationKindName();
        
        cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

        if(cancellationKind != null) {
            var cancellationPolicyName = spec.getCancellationPolicyName();
            var cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

            if(cancellationPolicy != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        cancellationPolicyTranslation = cancellationPolicyControl.getCancellationPolicyTranslation(cancellationPolicy, language);
                    } else { // EditMode.UPDATE
                        cancellationPolicyTranslation = cancellationPolicyControl.getCancellationPolicyTranslationForUpdate(cancellationPolicy, language);
                    }

                    if(cancellationPolicyTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyTranslation.name(), cancellationKindName, cancellationPolicyName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationKindName, cancellationPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }

        return cancellationPolicyTranslation;
    }

    @Override
    public CancellationPolicy getLockEntity(CancellationPolicyTranslation cancellationPolicyTranslation) {
        return cancellationPolicyTranslation.getCancellationPolicy();
    }

    @Override
    public void fillInResult(EditCancellationPolicyTranslationResult result, CancellationPolicyTranslation cancellationPolicyTranslation) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

        result.setCancellationPolicyTranslation(cancellationPolicyControl.getCancellationPolicyTranslationTransfer(getUserVisit(), cancellationPolicyTranslation));
    }

    MimeType policyMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(CancellationPolicyTranslationEdit edit, CancellationPolicyTranslation cancellationPolicyTranslation) {
        policyMimeType = cancellationPolicyTranslation.getPolicyMimeType();
        
        edit.setDescription(cancellationPolicyTranslation.getDescription());
        edit.setPolicyMimeTypeName(policyMimeType == null? null: policyMimeType.getLastDetail().getMimeTypeName());
        edit.setPolicy(cancellationPolicyTranslation.getPolicy());
    }

    @Override
    protected void canUpdate(CancellationPolicyTranslation cancellationPolicyTranslation) {
        var mimeTypeLogic = MimeTypeLogic.getInstance();
        var policyMimeTypeName = edit.getPolicyMimeTypeName();
        var policy = edit.getPolicy();
        
        policyMimeType = mimeTypeLogic.checkMimeType(this, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
    }
    
    @Override
    public void doUpdate(CancellationPolicyTranslation cancellationPolicyTranslation) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationPolicyTranslationValue = cancellationPolicyControl.getCancellationPolicyTranslationValue(cancellationPolicyTranslation);
        
        cancellationPolicyTranslationValue.setDescription(edit.getDescription());
        cancellationPolicyTranslationValue.setPolicyMimeTypePK(policyMimeType == null? null: policyMimeType.getPrimaryKey());
        cancellationPolicyTranslationValue.setPolicy(edit.getPolicy());
        
        cancellationPolicyControl.updateCancellationPolicyTranslationFromValue(cancellationPolicyTranslationValue, getPartyPK());
    }

}
