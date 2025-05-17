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

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEdit;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationPolicyForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationPolicyResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicySpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
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

public class EditCancellationPolicyCommand
        extends BaseAbstractEditCommand<CancellationPolicySpec, CancellationPolicyEdit, EditCancellationPolicyResult, CancellationPolicy, CancellationPolicy> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicy.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("PolicyMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Policy", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditCancellationPolicyCommand */
    public EditCancellationPolicyCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCancellationPolicyResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationPolicyResult();
    }

    @Override
    public CancellationPolicyEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationPolicyEdit();
    }

    CancellationKind cancellationKind;

    @Override
    public CancellationPolicy getEntity(EditCancellationPolicyResult result) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        CancellationPolicy cancellationPolicy = null;
        var cancellationKindName = spec.getCancellationKindName();

        cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

        if(cancellationKind != null) {
            var cancellationPolicyName = spec.getCancellationPolicyName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
            } else { // EditMode.UPDATE
                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByNameForUpdate(cancellationKind, cancellationPolicyName);
            }

            if(cancellationPolicy != null) {
                result.setCancellationPolicy(cancellationPolicyControl.getCancellationPolicyTransfer(getUserVisit(), cancellationPolicy));
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationKindName, cancellationPolicyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }

        return cancellationPolicy;
    }

    @Override
    public CancellationPolicy getLockEntity(CancellationPolicy cancellationPolicy) {
        return cancellationPolicy;
    }

    @Override
    public void fillInResult(EditCancellationPolicyResult result, CancellationPolicy cancellationPolicy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);

        result.setCancellationPolicy(cancellationPolicyControl.getCancellationPolicyTransfer(getUserVisit(), cancellationPolicy));
    }

    MimeType policyMimeType;

    @Override
    public void doLock(CancellationPolicyEdit edit, CancellationPolicy cancellationPolicy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationPolicyTranslation = cancellationPolicyControl.getCancellationPolicyTranslation(cancellationPolicy, getPreferredLanguage());
        var cancellationPolicyDetail = cancellationPolicy.getLastDetail();

        edit.setCancellationPolicyName(cancellationPolicyDetail.getCancellationPolicyName());
        edit.setIsDefault(cancellationPolicyDetail.getIsDefault().toString());
        edit.setSortOrder(cancellationPolicyDetail.getSortOrder().toString());

        if(cancellationPolicyTranslation != null) {
            policyMimeType = cancellationPolicyTranslation.getPolicyMimeType();

            edit.setDescription(cancellationPolicyTranslation.getDescription());
            edit.setPolicyMimeTypeName(policyMimeType == null? null: policyMimeType.getLastDetail().getMimeTypeName());
            edit.setPolicy(cancellationPolicyTranslation.getPolicy());
        }
    }

    @Override
    public void canUpdate(CancellationPolicy cancellationPolicy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var cancellationPolicyName = edit.getCancellationPolicyName();
        var duplicateCancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);

        if(duplicateCancellationPolicy != null && !cancellationPolicy.equals(duplicateCancellationPolicy)) {
            addExecutionError(ExecutionErrors.DuplicateCancellationPolicyName.name(), cancellationPolicyName);
        } else {
            var mimeTypeLogic = MimeTypeLogic.getInstance();
            var policyMimeTypeName = edit.getPolicyMimeTypeName();
            var policy = edit.getPolicy();

            policyMimeType = mimeTypeLogic.checkMimeType(this, policyMimeTypeName, policy, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredPolicyMimeTypeName.name(), ExecutionErrors.MissingRequiredPolicy.name(),
                    ExecutionErrors.UnknownPolicyMimeTypeName.name(), ExecutionErrors.UnknownPolicyMimeTypeUsage.name());
        }
    }

    @Override
    public void doUpdate(CancellationPolicy cancellationPolicy) {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var partyPK = getPartyPK();
        var cancellationPolicyDetailValue = cancellationPolicyControl.getCancellationPolicyDetailValueForUpdate(cancellationPolicy);
        var cancellationPolicyTranslation = cancellationPolicyControl.getCancellationPolicyTranslationForUpdate(cancellationPolicy, getPreferredLanguage());
        var description = edit.getDescription();
        var policy = edit.getPolicy();

        cancellationPolicyDetailValue.setCancellationPolicyName(edit.getCancellationPolicyName());
        cancellationPolicyDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        cancellationPolicyDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        cancellationPolicyControl.updateCancellationPolicyFromValue(cancellationPolicyDetailValue, partyPK);

        if(cancellationPolicyTranslation == null && (description != null || policy != null)) {
            cancellationPolicyControl.createCancellationPolicyTranslation(cancellationPolicy, getPreferredLanguage(), description, policyMimeType, policy,
                    partyPK);
        } else if(cancellationPolicyTranslation != null && (description == null && policy == null)) {
            cancellationPolicyControl.deleteCancellationPolicyTranslation(cancellationPolicyTranslation, partyPK);
        } else if(cancellationPolicyTranslation != null && (description != null || policy != null)) {
            var cancellationPolicyTranslationValue = cancellationPolicyControl.getCancellationPolicyTranslationValue(cancellationPolicyTranslation);

            cancellationPolicyTranslationValue.setDescription(description);
            cancellationPolicyTranslationValue.setPolicyMimeTypePK(policyMimeType == null? null: policyMimeType.getPrimaryKey());
            cancellationPolicyTranslationValue.setPolicy(policy);
            cancellationPolicyControl.updateCancellationPolicyTranslationFromValue(cancellationPolicyTranslationValue, partyPK);
        }
    }

}
