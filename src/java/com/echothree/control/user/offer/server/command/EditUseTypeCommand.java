// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.offer.common.edit.UseTypeEdit;
import com.echothree.control.user.offer.common.form.EditUseTypeForm;
import com.echothree.control.user.offer.common.result.EditUseTypeResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.UseTypeSpec;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeDetailValue;
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

public class EditUseTypeCommand
        extends BaseEditCommand<UseTypeSpec, UseTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditUseTypeCommand */
    public EditUseTypeCommand(UserVisitPK userVisitPK, EditUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditUseTypeResult result = OfferResultFactory.getEditUseTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String useTypeName = spec.getUseTypeName();
            UseType useType = offerControl.getUseTypeByName(useTypeName);
            
            if(useType != null) {
                result.setUseType(offerControl.getUseTypeTransfer(getUserVisit(), useType));
                
                if(lockEntity(useType)) {
                    UseTypeDescription useTypeDescription = offerControl.getUseTypeDescription(useType, getPreferredLanguage());
                    UseTypeEdit edit = OfferEditFactory.getUseTypeEdit();
                    UseTypeDetail useTypeDetail = useType.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setUseTypeName(useTypeDetail.getUseTypeName());
                    edit.setIsDefault(useTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(useTypeDetail.getSortOrder().toString());
                    
                    if(useTypeDescription != null) {
                        edit.setDescription(useTypeDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(useType));
            } else {
                addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String useTypeName = spec.getUseTypeName();
            UseType useType = offerControl.getUseTypeByNameForUpdate(useTypeName);
            
            if(useType != null) {
                useTypeName = edit.getUseTypeName();
                UseType duplicateUseType = offerControl.getUseTypeByName(useTypeName);
                
                if(duplicateUseType == null || useType.equals(duplicateUseType)) {
                    if(lockEntityForUpdate(useType)) {
                        try {
                            PartyPK partyPK = getPartyPK();
                            UseTypeDetailValue useTypeDetailValue = offerControl.getUseTypeDetailValueForUpdate(useType);
                            UseTypeDescription useTypeDescription = offerControl.getUseTypeDescriptionForUpdate(useType, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            useTypeDetailValue.setUseTypeName(edit.getUseTypeName());
                            useTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            useTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            offerControl.updateUseTypeFromValue(useTypeDetailValue, partyPK);
                            
                            if(useTypeDescription == null && description != null) {
                                offerControl.createUseTypeDescription(useType, getPreferredLanguage(), description, partyPK);
                            } else if(useTypeDescription != null && description == null) {
                                offerControl.deleteUseTypeDescription(useTypeDescription, partyPK);
                            } else if(useTypeDescription != null && description != null) {
                                UseTypeDescriptionValue useTypeDescriptionValue = offerControl.getUseTypeDescriptionValue(useTypeDescription);
                                
                                useTypeDescriptionValue.setDescription(description);
                                offerControl.updateUseTypeDescriptionFromValue(useTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(useType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateUseTypeName.name(), useTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
            }
        }
        
        return result;
    }
    
}
