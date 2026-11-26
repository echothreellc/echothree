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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.edit.TermDescriptionEdit;
import com.echothree.control.user.term.common.edit.TermEditFactory;
import com.echothree.control.user.term.common.result.EditTermDescriptionResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.control.user.term.common.spec.TermDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.control.term.server.logic.TermLogic;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditTermDescriptionCommand
        extends BaseEditCommand<TermDescriptionSpec, TermDescriptionEdit, EditTermDescriptionResult, TermDescription, Term> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    TermControl termControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TermLogic termLogic;

    /** Creates a new instance of EditTermDescriptionCommand */
    public EditTermDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTermDescriptionResult getResult() {
        return TermResultFactory.getEditTermDescriptionResult();
    }

    @Override
    public TermDescriptionEdit getEdit() {
        return TermEditFactory.getTermDescriptionEdit();
    }

    @Override
    public TermDescription getEntity(EditTermDescriptionResult result) {
        TermDescription termDescription = null;
        var termName = spec.getTermName();
        var languageIsoName = spec.getLanguageIsoName();
        var term = termLogic.getTermByName(this, termName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            termDescription = termControl.getTermDescription(term, language,
                    editModeToEntityPermission(editMode));

            if(termDescription == null) {
                addExecutionError(ExecutionErrors.UnknownTermDescription.name(), termName, languageIsoName);
            }
        }

        return termDescription;
    }

    @Override
    public Term getLockEntity(TermDescription termDescription) {
        return termDescription.getTerm();
    }

    @Override
    public void fillInResult(EditTermDescriptionResult result, TermDescription termDescription) {
        result.setTermDescription(termControl.getTermDescriptionTransfer(getUserVisit(), termDescription));
    }

    @Override
    public void doLock(TermDescriptionEdit edit, TermDescription termDescription) {
        edit.setDescription(termDescription.getDescription());
    }

    @Override
    public void doUpdate(TermDescription termDescription) {
        var termDescriptionValue = termControl.getTermDescriptionValue(termDescription);

        termDescriptionValue.setDescription(edit.getDescription());

        termControl.updateTermDescriptionFromValue(termDescriptionValue, getPartyPK());
    }

}
