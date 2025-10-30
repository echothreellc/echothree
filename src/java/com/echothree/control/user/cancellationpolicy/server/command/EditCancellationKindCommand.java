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

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationKindEdit;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationKindForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationKindSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditCancellationKindCommand
        extends BaseEditCommand<CancellationKindSpec, CancellationKindEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationKind.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCancellationKindCommand */
    public EditCancellationKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        var result = CancellationPolicyResultFactory.getEditCancellationKindResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var cancellationKindName = spec.getCancellationKindName();
            var cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
            
            if(cancellationKind != null) {
                result.setCancellationKind(cancellationPolicyControl.getCancellationKindTransfer(getUserVisit(), cancellationKind));
                
                if(lockEntity(cancellationKind)) {
                    var cancellationKindDescription = cancellationPolicyControl.getCancellationKindDescription(cancellationKind, getPreferredLanguage());
                    var edit = CancellationPolicyEditFactory.getCancellationKindEdit();
                    var cancellationKindDetail = cancellationKind.getLastDetail();
                    var cancellationSequenceType = cancellationKindDetail.getCancellationSequenceType();
                    
                    result.setEdit(edit);
                    edit.setCancellationKindName(cancellationKindDetail.getCancellationKindName());
                    edit.setCancellationSequenceTypeName(cancellationSequenceType == null? null: cancellationSequenceType.getLastDetail().getSequenceTypeName());
                    edit.setIsDefault(cancellationKindDetail.getIsDefault().toString());
                    edit.setSortOrder(cancellationKindDetail.getSortOrder().toString());
                    
                    if(cancellationKindDescription != null)
                        edit.setDescription(cancellationKindDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(cancellationKind));
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var cancellationKindName = spec.getCancellationKindName();
            var cancellationKind = cancellationPolicyControl.getCancellationKindByNameForUpdate(cancellationKindName);
            
            if(cancellationKind != null) {
                cancellationKindName = edit.getCancellationKindName();
                var duplicateCancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
                
                if(duplicateCancellationKind == null || cancellationKind.equals(duplicateCancellationKind)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var cancellationSequenceTypeName = edit.getCancellationSequenceTypeName();
                    SequenceType cancellationSequenceType = null;
                    
                    if(cancellationSequenceTypeName != null) {
                        cancellationSequenceType = sequenceControl.getSequenceTypeByName(cancellationSequenceTypeName);
                    }
                    
                    if(cancellationSequenceTypeName == null || cancellationSequenceType != null) {
                        if(lockEntityForUpdate(cancellationKind)) {
                            try {
                                var partyPK = getPartyPK();
                                var cancellationKindDetailValue = cancellationPolicyControl.getCancellationKindDetailValueForUpdate(cancellationKind);
                                var cancellationKindDescription = cancellationPolicyControl.getCancellationKindDescriptionForUpdate(cancellationKind, getPreferredLanguage());
                                var description = edit.getDescription();
                                
                                cancellationKindDetailValue.setCancellationKindName(edit.getCancellationKindName());
                                cancellationKindDetailValue.setCancellationSequenceTypePK(cancellationSequenceType == null? null: cancellationSequenceType.getPrimaryKey());
                                cancellationKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                cancellationKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                cancellationPolicyControl.updateCancellationKindFromValue(cancellationKindDetailValue, partyPK);
                                
                                if(cancellationKindDescription == null && description != null) {
                                    cancellationPolicyControl.createCancellationKindDescription(cancellationKind, getPreferredLanguage(), description, partyPK);
                                } else if(cancellationKindDescription != null && description == null) {
                                    cancellationPolicyControl.deleteCancellationKindDescription(cancellationKindDescription, partyPK);
                                } else if(cancellationKindDescription != null && description != null) {
                                    var cancellationKindDescriptionValue = cancellationPolicyControl.getCancellationKindDescriptionValue(cancellationKindDescription);
                                    
                                    cancellationKindDescriptionValue.setDescription(description);
                                    cancellationPolicyControl.updateCancellationKindDescriptionFromValue(cancellationKindDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(cancellationKind);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationSequenceTypeName.name(), cancellationSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCancellationKindName.name(), cancellationKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        }
        
        return result;
    }
    
}
