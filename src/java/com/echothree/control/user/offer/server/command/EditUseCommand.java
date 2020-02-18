// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.UseEdit;
import com.echothree.control.user.offer.common.form.EditUseForm;
import com.echothree.control.user.offer.common.result.EditUseResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseSpec;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditUseCommand
        extends BaseEditCommand<UseSpec, UseEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditUseCommand */
    public EditUseCommand(UserVisitPK userVisitPK, EditUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditUseResult result = OfferResultFactory.getEditUseResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String useName = spec.getUseName();
            Use use = offerControl.getUseByName(useName);
            
            if(use != null) {
                result.setUse(offerControl.getUseTransfer(getUserVisit(), use));
                
                if(lockEntity(use)) {
                    UseDescription useDescription = offerControl.getUseDescription(use, getPreferredLanguage());
                    UseEdit edit = OfferEditFactory.getUseEdit();
                    UseDetail useDetail = use.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setUseName(useDetail.getUseName());
                    edit.setUseTypeName(useDetail.getUseType().getLastDetail().getUseTypeName());
                    edit.setIsDefault(useDetail.getIsDefault().toString());
                    edit.setSortOrder(useDetail.getSortOrder().toString());
                    
                    if(useDescription != null) {
                        edit.setDescription(useDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(use));
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String useName = spec.getUseName();
            Use use = offerControl.getUseByNameForUpdate(useName);
            
            if(use != null) {
                useName = edit.getUseName();
                Use duplicateUse = offerControl.getUseByName(useName);
                
                if(duplicateUse == null || use.equals(duplicateUse)) {
                    String useTypeName = edit.getUseTypeName();
                    UseType useType = offerControl.getUseTypeByName(useTypeName);
                    
                    if(useType != null) {
                        if(lockEntityForUpdate(use)) {
                            try {
                                PartyPK partyPK = getPartyPK();
                                UseDetailValue useDetailValue = offerControl.getUseDetailValueForUpdate(use);
                                UseDescription useDescription = offerControl.getUseDescriptionForUpdate(use, getPreferredLanguage());
                                String description = edit.getDescription();
                                
                                useDetailValue.setUseName(edit.getUseName());
                                useDetailValue.setUseTypePK(useType.getPrimaryKey());
                                useDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                useDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                offerControl.updateUseFromValue(useDetailValue, partyPK);
                                
                                if(useDescription == null && description != null) {
                                    offerControl.createUseDescription(use, getPreferredLanguage(), description, partyPK);
                                } else if(useDescription != null && description == null) {
                                    offerControl.deleteUseDescription(useDescription, partyPK);
                                } else if(useDescription != null && description != null) {
                                    UseDescriptionValue useDescriptionValue = offerControl.getUseDescriptionValue(useDescription);
                                    
                                    useDescriptionValue.setDescription(description);
                                    offerControl.updateUseDescriptionFromValue(useDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(use);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateUseName.name(), useName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        }
        
        return result;
    }
    
}
