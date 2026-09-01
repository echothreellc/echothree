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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationReasonTypeEdit;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationReasonTypeResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationReasonTypeSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonType;
import com.echothree.util.common.message.ExecutionErrors;
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
public class EditCancellationReasonTypeCommand
        extends BaseAbstractEditCommand<CancellationReasonTypeSpec, CancellationReasonTypeEdit, EditCancellationReasonTypeResult,
                CancellationReasonType, CancellationReason> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReasonType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;
    
    /** Creates a new instance of EditCancellationReasonTypeCommand */
    public EditCancellationReasonTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationReasonTypeResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationReasonTypeResult();
    }

    @Override
    public CancellationReasonTypeEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationReasonTypeEdit();
    }

    @Override
    public CancellationReasonType getEntity(EditCancellationReasonTypeResult result) {
        CancellationReasonType cancellationReasonType = null;
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);
        
        if(!hasExecutionErrors()) {
            var cancellationReasonName = spec.getCancellationReasonName();
            var cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
            
            if(cancellationReason != null) {
                var cancellationTypeName = spec.getCancellationTypeName();
                var cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
                
                if(cancellationType != null) {
                    cancellationReasonType = cancellationPolicyControl.getCancellationReasonType(cancellationReason, cancellationType,
                            editModeToEntityPermission(editMode));

                    if(cancellationReasonType == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationReasonType.name(), cancellationKindName, cancellationReasonName,
                                cancellationTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationKindName, cancellationReasonName);
            }
        }

        return cancellationReasonType;
    }

    @Override
    public CancellationReason getLockEntity(CancellationReasonType cancellationReasonType) {
        return cancellationReasonType.getCancellationReason();
    }

    @Override
    public void fillInResult(EditCancellationReasonTypeResult result, CancellationReasonType cancellationReasonType) {
        result.setCancellationReasonType(cancellationPolicyControl.getCancellationReasonTypeTransfer(getUserVisit(), cancellationReasonType));
    }

    @Override
    public void doLock(CancellationReasonTypeEdit edit, CancellationReasonType cancellationReasonType) {
        edit.setIsDefault(cancellationReasonType.getIsDefault().toString());
        edit.setSortOrder(cancellationReasonType.getSortOrder().toString());
    }

    @Override
    public void doUpdate(CancellationReasonType cancellationReasonType) {
        var cancellationReasonTypeValue = cancellationReasonType.getCancellationReasonTypeValue().clone();

        cancellationReasonTypeValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        cancellationReasonTypeValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        cancellationPolicyControl.updateCancellationReasonTypeFromValue(cancellationReasonTypeValue, getPartyPK());
    }
    
}
