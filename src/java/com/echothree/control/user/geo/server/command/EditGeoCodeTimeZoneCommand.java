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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.edit.GeoCodeTimeZoneEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeTimeZoneForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeTimeZoneSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditGeoCodeTimeZoneCommand
        extends BaseEditCommand<GeoCodeTimeZoneSpec, GeoCodeTimeZoneEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeTimeZoneCommand */
    public EditGeoCodeTimeZoneCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var result = GeoResultFactory.getEditGeoCodeTimeZoneResult();
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var javaTimeZoneName = spec.getJavaTimeZoneName();
            var timeZone = partyControl.getTimeZoneByJavaName(javaTimeZoneName);
            
            if(timeZone != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var geoCodeTimeZone = geoControl.getGeoCodeTimeZone(geoCode, timeZone);
                    
                    if(geoCodeTimeZone != null) {
                        result.setGeoCodeTimeZone(geoControl.getGeoCodeTimeZoneTransfer(getUserVisit(), geoCodeTimeZone));
                        
                        if(lockEntity(geoCode)) {
                            var edit = GeoEditFactory.getGeoCodeTimeZoneEdit();
                            
                            result.setEdit(edit);
                            edit.setIsDefault(geoCodeTimeZone.getIsDefault().toString());
                            edit.setSortOrder(geoCodeTimeZone.getSortOrder().toString());
                            
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var geoCodeTimeZone = geoControl.getGeoCodeTimeZoneForUpdate(geoCode, timeZone);
                    
                    if(geoCodeTimeZone != null) {
                        var geoCodeTimeZoneValue = geoControl.getGeoCodeTimeZoneValue(geoCodeTimeZone);
                        
                        if(lockEntityForUpdate(geoCode)) {
                            try {
                                geoCodeTimeZoneValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                geoCodeTimeZoneValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                geoControl.updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, getPartyPK());
                            } finally {
                                unlockEntity(geoCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setGeoCodeTimeZone(geoControl.getGeoCodeTimeZoneTransfer(getUserVisit(), geoCodeTimeZone));
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), javaTimeZoneName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
