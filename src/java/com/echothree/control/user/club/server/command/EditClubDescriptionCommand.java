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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.edit.ClubDescriptionEdit;
import com.echothree.control.user.club.common.edit.ClubEditFactory;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.result.EditClubDescriptionResult;
import com.echothree.control.user.club.common.spec.ClubDescriptionSpec;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.club.server.logic.ClubLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.entity.ClubDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditClubDescriptionCommand
        extends BaseEditCommand<ClubDescriptionSpec, ClubDescriptionEdit, EditClubDescriptionResult, ClubDescription, Club> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Club.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    ClubControl clubControl;

    @Inject
    ClubLogic clubLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditClubDescriptionCommand */
    public EditClubDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditClubDescriptionResult getResult() {
        return ClubResultFactory.getEditClubDescriptionResult();
    }

    @Override
    public ClubDescriptionEdit getEdit() {
        return ClubEditFactory.getClubDescriptionEdit();
    }

    @Override
    public ClubDescription getEntity(EditClubDescriptionResult result) {
        ClubDescription clubDescription = null;
        var clubName = spec.getClubName();
        var languageIsoName = spec.getLanguageIsoName();
        var club = clubLogic.getClubByName(this, clubName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            clubDescription = clubControl.getClubDescription(club, language,
                    editModeToEntityPermission(editMode));

            if(clubDescription == null) {
                addExecutionError(ExecutionErrors.UnknownClubDescription.name(), clubName, languageIsoName);
            }
        }

        return clubDescription;
    }

    @Override
    public Club getLockEntity(ClubDescription clubDescription) {
        return clubDescription.getClub();
    }

    @Override
    public void fillInResult(EditClubDescriptionResult result, ClubDescription clubDescription) {
        result.setClubDescription(clubControl.getClubDescriptionTransfer(getUserVisit(), clubDescription));
    }

    @Override
    public void doLock(ClubDescriptionEdit edit, ClubDescription clubDescription) {
        edit.setDescription(clubDescription.getDescription());
    }

    @Override
    public void doUpdate(ClubDescription clubDescription) {
        var clubDescriptionValue = clubControl.getClubDescriptionValue(clubDescription);

        clubDescriptionValue.setDescription(edit.getDescription());

        clubControl.updateClubDescriptionFromValue(clubDescriptionValue, getPartyPK());
    }

}
