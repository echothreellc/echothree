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

package com.echothree.control.user.track.server.command;

import com.echothree.control.user.track.common.edit.TrackDescriptionEdit;
import com.echothree.control.user.track.common.edit.TrackEditFactory;
import com.echothree.control.user.track.common.form.EditTrackDescriptionForm;
import com.echothree.control.user.track.common.result.EditTrackDescriptionResult;
import com.echothree.control.user.track.common.result.TrackResultFactory;
import com.echothree.control.user.track.common.spec.TrackDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.track.server.entity.TrackDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditTrackDescriptionCommand
        extends BaseAbstractEditCommand<TrackDescriptionSpec, TrackDescriptionEdit, EditTrackDescriptionResult, TrackDescription, Track> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Track.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrackName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTrackDescriptionCommand */
    public EditTrackDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrackDescriptionResult getResult() {
        return TrackResultFactory.getEditTrackDescriptionResult();
    }

    @Override
    public TrackDescriptionEdit getEdit() {
        return TrackEditFactory.getTrackDescriptionEdit();
    }

    @Override
    public TrackDescription getEntity(EditTrackDescriptionResult result) {
        var trackControl = Session.getModelController(TrackControl.class);
        TrackDescription trackDescription = null;
        var trackName = spec.getTrackName();
        var track = trackControl.getTrackByName(trackName);

        if(track != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    trackDescription = trackControl.getTrackDescription(track, language);
                } else { // EditMode.UPDATE
                    trackDescription = trackControl.getTrackDescriptionForUpdate(track, language);
                }

                if(trackDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTrackDescription.name(), trackName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrackName.name(), trackName);
        }

        return trackDescription;
    }

    @Override
    public Track getLockEntity(TrackDescription trackDescription) {
        return trackDescription.getTrack();
    }

    @Override
    public void fillInResult(EditTrackDescriptionResult result, TrackDescription trackDescription) {
        var trackControl = Session.getModelController(TrackControl.class);

        result.setTrackDescription(trackControl.getTrackDescriptionTransfer(getUserVisit(), trackDescription));
    }

    @Override
    public void doLock(TrackDescriptionEdit edit, TrackDescription trackDescription) {
        edit.setDescription(trackDescription.getDescription());
    }

    @Override
    public void doUpdate(TrackDescription trackDescription) {
        var trackControl = Session.getModelController(TrackControl.class);
        var trackDescriptionValue = trackControl.getTrackDescriptionValue(trackDescription);
        trackDescriptionValue.setDescription(edit.getDescription());

        trackControl.updateTrackDescriptionFromValue(trackDescriptionValue, getPartyPK());
    }
    
}
