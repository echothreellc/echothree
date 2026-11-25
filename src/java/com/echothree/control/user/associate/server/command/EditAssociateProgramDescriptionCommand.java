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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.edit.AssociateEditFactory;
import com.echothree.control.user.associate.common.edit.AssociateProgramDescriptionEdit;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.control.user.associate.common.result.EditAssociateProgramDescriptionResult;
import com.echothree.control.user.associate.common.spec.AssociateProgramDescriptionSpec;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.associate.server.logic.AssociateProgramLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.entity.AssociateProgramDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditAssociateProgramDescriptionCommand
        extends BaseAbstractEditCommand<AssociateProgramDescriptionSpec, AssociateProgramDescriptionEdit, EditAssociateProgramDescriptionResult, AssociateProgramDescription, AssociateProgram> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AssociateControl associateControl;

    @Inject
    AssociateProgramLogic associateProgramLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditAssociateProgramDescriptionCommand */
    public EditAssociateProgramDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditAssociateProgramDescriptionResult getResult() {
        return AssociateResultFactory.getEditAssociateProgramDescriptionResult();
    }

    @Override
    public AssociateProgramDescriptionEdit getEdit() {
        return AssociateEditFactory.getAssociateProgramDescriptionEdit();
    }

    @Override
    public AssociateProgramDescription getEntity(EditAssociateProgramDescriptionResult result) {
        AssociateProgramDescription associateProgramDescription = null;
        var associateProgramName = spec.getAssociateProgramName();
        var languageIsoName = spec.getLanguageIsoName();
        var associateProgram = associateProgramLogic.getAssociateProgramByName(this, associateProgramName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            associateProgramDescription = associateControl.getAssociateProgramDescription(associateProgram, language,
                    editModeToEntityPermission(editMode));

            if(associateProgramDescription == null) {
                addExecutionError(ExecutionErrors.UnknownAssociateProgramDescription.name(), associateProgramName, languageIsoName);
            }
        }

        return associateProgramDescription;
    }

    @Override
    public AssociateProgram getLockEntity(AssociateProgramDescription associateProgramDescription) {
        return associateProgramDescription.getAssociateProgram();
    }

    @Override
    public void fillInResult(EditAssociateProgramDescriptionResult result, AssociateProgramDescription associateProgramDescription) {
        result.setAssociateProgramDescription(associateControl.getAssociateProgramDescriptionTransfer(getUserVisit(), associateProgramDescription));
    }

    @Override
    public void doLock(AssociateProgramDescriptionEdit edit, AssociateProgramDescription associateProgramDescription) {
        edit.setDescription(associateProgramDescription.getDescription());
    }

    @Override
    public void doUpdate(AssociateProgramDescription associateProgramDescription) {
        var associateProgramDescriptionValue = associateControl.getAssociateProgramDescriptionValue(associateProgramDescription);

        associateProgramDescriptionValue.setDescription(edit.getDescription());

        associateControl.updateAssociateProgramDescriptionFromValue(associateProgramDescriptionValue, getPartyPK());
    }

}
