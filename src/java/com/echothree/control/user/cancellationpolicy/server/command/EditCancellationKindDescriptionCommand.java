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

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationKindDescriptionEdit;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationKindDescriptionResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationKindDescriptionSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.cancellationpolicy.server.logic.CancellationKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDescription;
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
public class EditCancellationKindDescriptionCommand
        extends BaseAbstractEditCommand<CancellationKindDescriptionSpec, CancellationKindDescriptionEdit, EditCancellationKindDescriptionResult,
                CancellationKindDescription, CancellationKind> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationKind.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
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
    
    /** Creates a new instance of EditCancellationKindDescriptionCommand */
    public EditCancellationKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCancellationKindDescriptionResult getResult() {
        return CancellationPolicyResultFactory.getEditCancellationKindDescriptionResult();
    }

    @Override
    public CancellationKindDescriptionEdit getEdit() {
        return CancellationPolicyEditFactory.getCancellationKindDescriptionEdit();
    }

    @Override
    public CancellationKindDescription getEntity(EditCancellationKindDescriptionResult result) {
        CancellationKindDescription cancellationKindDescription = null;
        var cancellationKindName = spec.getCancellationKindName();
        var cancellationKind = cancellationKindLogic.getCancellationKindByName(this, cancellationKindName);
        
        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);
            
            if(!hasExecutionErrors()) {
                cancellationKindDescription = cancellationPolicyControl.getCancellationKindDescription(cancellationKind, language,
                        editModeToEntityPermission(editMode));

                if(cancellationKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCancellationKindDescription.name(), cancellationKindName, languageIsoName);
                }
            }
        }

        return cancellationKindDescription;
    }

    @Override
    public CancellationKind getLockEntity(CancellationKindDescription cancellationKindDescription) {
        return cancellationKindDescription.getCancellationKind();
    }

    @Override
    public void fillInResult(EditCancellationKindDescriptionResult result, CancellationKindDescription cancellationKindDescription) {
        result.setCancellationKindDescription(cancellationPolicyControl.getCancellationKindDescriptionTransfer(getUserVisit(), cancellationKindDescription));
    }

    @Override
    public void doLock(CancellationKindDescriptionEdit edit, CancellationKindDescription cancellationKindDescription) {
        edit.setDescription(cancellationKindDescription.getDescription());
    }

    @Override
    public void doUpdate(CancellationKindDescription cancellationKindDescription) {
        var cancellationKindDescriptionValue = cancellationPolicyControl.getCancellationKindDescriptionValue(cancellationKindDescription);

        cancellationKindDescriptionValue.setDescription(edit.getDescription());

        cancellationPolicyControl.updateCancellationKindDescriptionFromValue(cancellationKindDescriptionValue, getPartyPK());
    }
    
}
