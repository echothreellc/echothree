// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.cancellationpolicy.remote.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.remote.edit.CancellationReasonEdit;
import com.echothree.control.user.cancellationpolicy.remote.form.EditCancellationReasonForm;
import com.echothree.control.user.cancellationpolicy.remote.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.remote.result.EditCancellationReasonResult;
import com.echothree.control.user.cancellationpolicy.remote.spec.CancellationReasonSpec;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDescription;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDetail;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDescriptionValue;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationReasonDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCancellationReasonCommand
        extends BaseEditCommand<CancellationReasonSpec, CancellationReasonEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReason.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCancellationReasonCommand */
    public EditCancellationReasonCommand(UserVisitPK userVisitPK, EditCancellationReasonForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
        EditCancellationReasonResult result = CancellationPolicyResultFactory.getEditCancellationReasonResult();
        String cancellationKindName = spec.getCancellationKindName();
        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                String cancellationReasonName = spec.getCancellationReasonName();
                CancellationReason cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
                
                if(cancellationReason != null) {
                    result.setCancellationReason(cancellationPolicyControl.getCancellationReasonTransfer(getUserVisit(), cancellationReason));
                    
                    if(lockEntity(cancellationReason)) {
                        CancellationReasonDescription cancellationReasonDescription = cancellationPolicyControl.getCancellationReasonDescription(cancellationReason, getPreferredLanguage());
                        CancellationReasonEdit edit = CancellationPolicyEditFactory.getCancellationReasonEdit();
                        CancellationReasonDetail cancellationReasonDetail = cancellationReason.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setCancellationReasonName(cancellationReasonDetail.getCancellationReasonName());
                        edit.setIsDefault(cancellationReasonDetail.getIsDefault().toString());
                        edit.setSortOrder(cancellationReasonDetail.getSortOrder().toString());
                        
                        if(cancellationReasonDescription != null)
                            edit.setDescription(cancellationReasonDescription.getDescription());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(cancellationReason));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String cancellationReasonName = spec.getCancellationReasonName();
                CancellationReason cancellationReason = cancellationPolicyControl.getCancellationReasonByNameForUpdate(cancellationKind, cancellationReasonName);
                
                if(cancellationReason != null) {
                    cancellationReasonName = edit.getCancellationReasonName();
                    CancellationReason duplicateCancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
                    
                    if(duplicateCancellationReason == null || cancellationReason.equals(duplicateCancellationReason)) {
                        if(lockEntityForUpdate(cancellationReason)) {
                            try {
                                PartyPK partyPK = getPartyPK();
                                CancellationReasonDetailValue cancellationReasonDetailValue = cancellationPolicyControl.getCancellationReasonDetailValueForUpdate(cancellationReason);
                                CancellationReasonDescription cancellationReasonDescription = cancellationPolicyControl.getCancellationReasonDescriptionForUpdate(cancellationReason, getPreferredLanguage());
                                String description = edit.getDescription();
                                
                                cancellationReasonDetailValue.setCancellationReasonName(edit.getCancellationReasonName());
                                cancellationReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                cancellationReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                cancellationPolicyControl.updateCancellationReasonFromValue(cancellationReasonDetailValue, partyPK);
                                
                                if(cancellationReasonDescription == null && description != null) {
                                    cancellationPolicyControl.createCancellationReasonDescription(cancellationReason, getPreferredLanguage(), description, partyPK);
                                } else if(cancellationReasonDescription != null && description == null) {
                                    cancellationPolicyControl.deleteCancellationReasonDescription(cancellationReasonDescription, partyPK);
                                } else if(cancellationReasonDescription != null && description != null) {
                                    CancellationReasonDescriptionValue cancellationReasonDescriptionValue = cancellationPolicyControl.getCancellationReasonDescriptionValue(cancellationReasonDescription);
                                    
                                    cancellationReasonDescriptionValue.setDescription(description);
                                    cancellationPolicyControl.updateCancellationReasonDescriptionFromValue(cancellationReasonDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(cancellationReason);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateCancellationReasonName.name(), cancellationReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return result;
    }
    
}
