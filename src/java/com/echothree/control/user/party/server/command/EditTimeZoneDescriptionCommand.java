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

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.TimeZoneDescriptionEdit;
import com.echothree.control.user.party.common.result.EditTimeZoneDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.TimeZoneDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.party.server.entity.TimeZoneDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditTimeZoneDescriptionCommand
        extends BaseEditCommand<TimeZoneDescriptionSpec, TimeZoneDescriptionEdit, EditTimeZoneDescriptionResult, TimeZoneDescription, TimeZone> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TimeZoneLogic timeZoneLogic;

    /** Creates a new instance of EditTimeZoneDescriptionCommand */
    public EditTimeZoneDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTimeZoneDescriptionResult getResult() {
        return PartyResultFactory.getEditTimeZoneDescriptionResult();
    }

    @Override
    public TimeZoneDescriptionEdit getEdit() {
        return PartyEditFactory.getTimeZoneDescriptionEdit();
    }

    @Override
    public TimeZoneDescription getEntity(EditTimeZoneDescriptionResult result) {
        TimeZoneDescription timeZoneDescription = null;
        var javaTimeZoneName = spec.getJavaTimeZoneName();
        var languageIsoName = spec.getLanguageIsoName();
        var timeZone = timeZoneLogic.getTimeZoneByName(this, javaTimeZoneName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            timeZoneDescription = partyControl.getTimeZoneDescription(timeZone, language,
                    editModeToEntityPermission(editMode));

            if(timeZoneDescription == null) {
                addExecutionError(ExecutionErrors.UnknownTimeZoneDescription.name(), javaTimeZoneName, languageIsoName);
            }
        }

        return timeZoneDescription;
    }

    @Override
    public TimeZone getLockEntity(TimeZoneDescription timeZoneDescription) {
        return timeZoneDescription.getTimeZone();
    }

    @Override
    public void fillInResult(EditTimeZoneDescriptionResult result, TimeZoneDescription timeZoneDescription) {
        result.setTimeZoneDescription(partyControl.getTimeZoneDescriptionTransfer(getUserVisit(), timeZoneDescription));
    }

    @Override
    public void doLock(TimeZoneDescriptionEdit edit, TimeZoneDescription timeZoneDescription) {
        edit.setDescription(timeZoneDescription.getDescription());
    }

    @Override
    public void doUpdate(TimeZoneDescription timeZoneDescription) {
        var timeZoneDescriptionValue = partyControl.getTimeZoneDescriptionValue(timeZoneDescription);

        timeZoneDescriptionValue.setDescription(edit.getDescription());

        partyControl.updateTimeZoneDescriptionFromValue(timeZoneDescriptionValue, getPartyPK());
    }

}
