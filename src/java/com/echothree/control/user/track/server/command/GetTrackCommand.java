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

import com.echothree.control.user.track.common.form.GetTrackForm;
import com.echothree.control.user.track.common.result.TrackResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.control.track.server.logic.TrackLogic;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTrackCommand
        extends BaseSingleEntityCommand<Track, GetTrackForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Track.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TrackName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    @Inject
    TrackControl trackControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    TrackLogic trackLogic;
    
    /** Creates a new instance of GetTrackCommand */
    public GetTrackCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Track getEntity() {
        Track track = null;
        var trackName = form.getTrackName();
        var parameterCount = (trackName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(trackName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Track.name());
                
                if(!hasExecutionErrors()) {
                    track = trackControl.getTrackByEntityInstance(entityInstance);
                }
            } else {
                track = trackLogic.getTrackByName(this, trackName);
            }

            if(!hasExecutionErrors()) {
                sendEvent(track.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return track;
    }

    @Override
    protected BaseResult getResult(Track track) {
        var result = TrackResultFactory.getGetTrackResult();

        if(track != null) {
            result.setTrack(trackControl.getTrackTransfer(getUserVisit(), track));
        }
        
        return result;
    }
    
}
