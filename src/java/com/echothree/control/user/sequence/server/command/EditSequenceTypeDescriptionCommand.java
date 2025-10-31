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

package com.echothree.control.user.sequence.server.command;

import com.echothree.control.user.sequence.common.edit.SequenceEditFactory;
import com.echothree.control.user.sequence.common.edit.SequenceTypeDescriptionEdit;
import com.echothree.control.user.sequence.common.form.EditSequenceTypeDescriptionForm;
import com.echothree.control.user.sequence.common.result.EditSequenceTypeDescriptionResult;
import com.echothree.control.user.sequence.common.result.SequenceResultFactory;
import com.echothree.control.user.sequence.common.spec.SequenceTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDescription;
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
public class EditSequenceTypeDescriptionCommand
        extends BaseAbstractEditCommand<SequenceTypeDescriptionSpec, SequenceTypeDescriptionEdit, EditSequenceTypeDescriptionResult, SequenceTypeDescription, SequenceType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SequenceType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SequenceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSequenceTypeDescriptionCommand */
    public EditSequenceTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSequenceTypeDescriptionResult getResult() {
        return SequenceResultFactory.getEditSequenceTypeDescriptionResult();
    }

    @Override
    public SequenceTypeDescriptionEdit getEdit() {
        return SequenceEditFactory.getSequenceTypeDescriptionEdit();
    }

    @Override
    public SequenceTypeDescription getEntity(EditSequenceTypeDescriptionResult result) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        SequenceTypeDescription sequenceTypeDescription = null;
        var sequenceTypeName = spec.getSequenceTypeName();
        var sequenceType = sequenceControl.getSequenceTypeByName(sequenceTypeName);

        if(sequenceType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    sequenceTypeDescription = sequenceControl.getSequenceTypeDescription(sequenceType, language);
                } else { // EditMode.UPDATE
                    sequenceTypeDescription = sequenceControl.getSequenceTypeDescriptionForUpdate(sequenceType, language);
                }

                if(sequenceTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSequenceTypeDescription.name(), sequenceTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), sequenceTypeName);
        }

        return sequenceTypeDescription;
    }

    @Override
    public SequenceType getLockEntity(SequenceTypeDescription sequenceTypeDescription) {
        return sequenceTypeDescription.getSequenceType();
    }

    @Override
    public void fillInResult(EditSequenceTypeDescriptionResult result, SequenceTypeDescription sequenceTypeDescription) {
        var sequenceControl = Session.getModelController(SequenceControl.class);

        result.setSequenceTypeDescription(sequenceControl.getSequenceTypeDescriptionTransfer(getUserVisit(), sequenceTypeDescription));
    }

    @Override
    public void doLock(SequenceTypeDescriptionEdit edit, SequenceTypeDescription sequenceTypeDescription) {
        edit.setDescription(sequenceTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SequenceTypeDescription sequenceTypeDescription) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequenceTypeDescriptionValue = sequenceControl.getSequenceTypeDescriptionValue(sequenceTypeDescription);
        
        sequenceTypeDescriptionValue.setDescription(edit.getDescription());
        
        sequenceControl.updateSequenceTypeDescriptionFromValue(sequenceTypeDescriptionValue, getPartyPK());
    }
    
}
