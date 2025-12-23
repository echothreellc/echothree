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

import com.echothree.control.user.track.common.form.CreateUserVisitTrackForm;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateUserVisitTrackCommand
        extends BaseSimpleCommand<CreateUserVisitTrackForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrackValue", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrackCommand */
    public CreateUserVisitTrackCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var trackValue = form.getTrackValue();
        var parameterCount = trackValue == null ? 0 : 1;

        if(parameterCount > 0) {
            var trackControl = Session.getModelController(TrackControl.class);
            Track track = null;
            
            if(trackValue != null) {
                track = trackControl.getTrackByValue(trackValue);
                
                if(track == null) {
                    try {
                        track = trackControl.createTrack(trackValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            track = trackControl.getTrackByValue(trackValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            trackControl.createUserVisitTrack(getUserVisit(), session.getStartTime(), track);
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
