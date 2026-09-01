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
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyReasonEdit;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationPolicyReasonResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationPolicyReasonSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationPolicyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicyReason;
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
public class EditCancellationPolicyReasonCommand
        extends BaseAbstractEditCommand<CancellationPolicyReasonSpec, CancellationPolicyReasonEdit, EditCancellationPolicyReasonResult,
                CancellationPolicyReason, CancellationPolicy> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationPolicyReason.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, true, null, null)
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

    @Inject
    CancellationPolicyLogic cancellationPolicyLogic;
    
    /** Creates a new instance of EditCancellationPolicyReasonCommand */
    public EditCancellationPolicyReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationPolicyReasonResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationPolicyReasonResult();
    }

    @Override
    public CancellationPolicyReasonEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationPolicyReasonEdit();
    }

    @Override
    public CancellationPolicyReason getEntity(EditCancellationPolicyReasonResult result) {
        CancellationPolicyReason cancellationPolicyReason = null;
        var cancellationPolicyName = spec.getCancellationPolicyName();
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);
        
        if(!hasExecutionErrors()) {
            var cancellationPolicy = cancellationPolicyLogic.getCancellationPolicyByName(this, cancellationKind, cancellationPolicyName);
            
            if(!hasExecutionErrors()) {
                var cancellationReasonName = spec.getCancellationReasonName();
                var cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
                
                if(cancellationReason != null) {
                    cancellationPolicyReason = cancellationPolicyControl.getCancellationPolicyReason(cancellationPolicy, cancellationReason,
                            editModeToEntityPermission(editMode));

                    if(cancellationPolicyReason == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationPolicyReason.name(), cancellationKindName, cancellationPolicyName,
                                cancellationReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationKindName, cancellationReasonName);
                }
            }
        }

        return cancellationPolicyReason;
    }

    @Override
    public CancellationPolicy getLockEntity(CancellationPolicyReason cancellationPolicyReason) {
        return cancellationPolicyReason.getCancellationPolicy();
    }

    @Override
    public void fillInResult(EditCancellationPolicyReasonResult result, CancellationPolicyReason cancellationPolicyReason) {
        result.setCancellationPolicyReason(cancellationPolicyControl.getCancellationPolicyReasonTransfer(getUserVisit(), cancellationPolicyReason));
    }

    @Override
    public void doLock(CancellationPolicyReasonEdit edit, CancellationPolicyReason cancellationPolicyReason) {
        edit.setIsDefault(cancellationPolicyReason.getIsDefault().toString());
        edit.setSortOrder(cancellationPolicyReason.getSortOrder().toString());
    }

    @Override
    public void doUpdate(CancellationPolicyReason cancellationPolicyReason) {
        var cancellationPolicyReasonValue = cancellationPolicyReason.getCancellationPolicyReasonValue().clone();

        cancellationPolicyReasonValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        cancellationPolicyReasonValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        cancellationPolicyControl.updateCancellationPolicyReasonFromValue(cancellationPolicyReasonValue, getPartyPK());
    }
    
}
