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
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationTypeDescriptionEdit;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationTypeDescriptionResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationTypeDescriptionSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDescription;
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
public class EditCancellationTypeDescriptionCommand
        extends BaseAbstractEditCommand<CancellationTypeDescriptionSpec, CancellationTypeDescriptionEdit, EditCancellationTypeDescriptionResult,
                CancellationTypeDescription, CancellationType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationType.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    CancellationPolicyControl cancellationPolicyControl;

    @Inject
    CancellationKindLogic cancellationKindLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditCancellationTypeDescriptionCommand */
    public EditCancellationTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationTypeDescriptionResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationTypeDescriptionResult();
    }

    @Override
    public CancellationTypeDescriptionEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationTypeDescriptionEdit();
    }

    @Override
    public CancellationTypeDescription getEntity(EditCancellationTypeDescriptionResult result) {
        CancellationTypeDescription cancellationTypeDescription = null;
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);
        
        if(!hasExecutionErrors()) {
            var cancellationTypeName = spec.getCancellationTypeName();
            var cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
            
            if(cancellationType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);
                
                if(!hasExecutionErrors()) {
                    cancellationTypeDescription = cancellationPolicyControl.getCancellationTypeDescription(cancellationType, language,
                            editModeToEntityPermission(editMode));

                    if(cancellationTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownCancellationTypeDescription.name(), cancellationKindName, cancellationTypeName,
                                languageIsoName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
            }
        }

        return cancellationTypeDescription;
    }

    @Override
    public CancellationType getLockEntity(CancellationTypeDescription cancellationTypeDescription) {
        return cancellationTypeDescription.getCancellationType();
    }

    @Override
    public void fillInResult(EditCancellationTypeDescriptionResult result, CancellationTypeDescription cancellationTypeDescription) {
        result.setCancellationTypeDescription(cancellationPolicyControl.getCancellationTypeDescriptionTransfer(getUserVisit(), cancellationTypeDescription));
    }

    @Override
    public void doLock(CancellationTypeDescriptionEdit edit, CancellationTypeDescription cancellationTypeDescription) {
        edit.setDescription(cancellationTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(CancellationTypeDescription cancellationTypeDescription) {
        var cancellationTypeDescriptionValue = cancellationPolicyControl.getCancellationTypeDescriptionValue(cancellationTypeDescription);

        cancellationTypeDescriptionValue.setDescription(edit.getDescription());

        cancellationPolicyControl.updateCancellationTypeDescriptionFromValue(cancellationTypeDescriptionValue, getPartyPK());
    }
    
}
