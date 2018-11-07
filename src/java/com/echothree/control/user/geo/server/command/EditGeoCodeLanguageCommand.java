// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.geo.common.edit.GeoCodeLanguageEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeLanguageForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeLanguageResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeLanguageSpec;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.model.data.geo.server.value.GeoCodeLanguageValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditGeoCodeLanguageCommand
        extends BaseEditCommand<GeoCodeLanguageSpec, GeoCodeLanguageEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeLanguage.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeLanguageCommand */
    public EditGeoCodeLanguageCommand(UserVisitPK userVisitPK, EditGeoCodeLanguageForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        EditGeoCodeLanguageResult result = GeoResultFactory.getEditGeoCodeLanguageResult();
        String geoCodeName = spec.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    GeoCodeLanguage geoCodeLanguage = geoControl.getGeoCodeLanguage(geoCode, language);
                    
                    if(geoCodeLanguage != null) {
                        result.setGeoCodeLanguage(geoControl.getGeoCodeLanguageTransfer(getUserVisit(), geoCodeLanguage));
                        
                        if(lockEntity(geoCode)) {
                            GeoCodeLanguageEdit edit = GeoEditFactory.getGeoCodeLanguageEdit();
                            
                            result.setEdit(edit);
                            edit.setIsDefault(geoCodeLanguage.getIsDefault().toString());
                            edit.setSortOrder(geoCodeLanguage.getSortOrder().toString());
                            
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeLanguage.name(), geoCodeName, languageIsoName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    GeoCodeLanguage geoCodeLanguage = geoControl.getGeoCodeLanguageForUpdate(geoCode, language);
                    
                    if(geoCodeLanguage != null) {
                        GeoCodeLanguageValue geoCodeLanguageValue = geoControl.getGeoCodeLanguageValue(geoCodeLanguage);
                        
                        if(lockEntityForUpdate(geoCode)) {
                            try {
                                geoCodeLanguageValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                geoCodeLanguageValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                geoControl.updateGeoCodeLanguageFromValue(geoCodeLanguageValue, getPartyPK());
                            } finally {
                                unlockEntity(geoCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeLanguage.name(), geoCodeName, languageIsoName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setGeoCodeLanguage(geoControl.getGeoCodeLanguageTransfer(getUserVisit(), geoCodeLanguage));
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
