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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.DateTimeFormatDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditDateTimeFormatDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.DateTimeFormatDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditDateTimeFormatDescriptionCommand
        extends BaseEditCommand<DateTimeFormatDescriptionSpec, DateTimeFormatDescriptionEdit, EditDateTimeFormatDescriptionResult, DateTimeFormatDescription, DateTimeFormat> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    DateTimeFormatLogic dateTimeFormatLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditDateTimeFormatDescriptionCommand */
    public EditDateTimeFormatDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditDateTimeFormatDescriptionResult getResult() {
        return PartyResultFactory.getEditDateTimeFormatDescriptionResult();
    }

    @Override
    public DateTimeFormatDescriptionEdit getEdit() {
        return PartyEditFactory.getDateTimeFormatDescriptionEdit();
    }

    @Override
    public DateTimeFormatDescription getEntity(EditDateTimeFormatDescriptionResult result) {
        DateTimeFormatDescription dateTimeFormatDescription = null;
        var dateTimeFormatName = spec.getDateTimeFormatName();
        var languageIsoName = spec.getLanguageIsoName();
        var dateTimeFormat = dateTimeFormatLogic.getDateTimeFormatByName(this, dateTimeFormatName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            dateTimeFormatDescription = partyControl.getDateTimeFormatDescription(dateTimeFormat, language,
                    editModeToEntityPermission(editMode));

            if(dateTimeFormatDescription == null) {
                addExecutionError(ExecutionErrors.UnknownDateTimeFormatDescription.name(), dateTimeFormatName, languageIsoName);
            }
        }

        return dateTimeFormatDescription;
    }

    @Override
    public DateTimeFormat getLockEntity(DateTimeFormatDescription dateTimeFormatDescription) {
        return dateTimeFormatDescription.getDateTimeFormat();
    }

    @Override
    public void fillInResult(EditDateTimeFormatDescriptionResult result, DateTimeFormatDescription dateTimeFormatDescription) {
        result.setDateTimeFormatDescription(partyControl.getDateTimeFormatDescriptionTransfer(getUserVisit(), dateTimeFormatDescription));
    }

    @Override
    public void doLock(DateTimeFormatDescriptionEdit edit, DateTimeFormatDescription dateTimeFormatDescription) {
        edit.setDescription(dateTimeFormatDescription.getDescription());
    }

    @Override
    public void doUpdate(DateTimeFormatDescription dateTimeFormatDescription) {
        var dateTimeFormatDescriptionValue = partyControl.getDateTimeFormatDescriptionValue(dateTimeFormatDescription);

        dateTimeFormatDescriptionValue.setDescription(edit.getDescription());

        partyControl.updateDateTimeFormatDescriptionFromValue(dateTimeFormatDescriptionValue, getPartyPK());
    }

}
