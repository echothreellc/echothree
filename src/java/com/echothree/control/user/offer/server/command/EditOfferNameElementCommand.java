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
import com.echothree.control.user.offer.common.edit.OfferNameElementEdit;
import com.echothree.control.user.offer.common.form.EditOfferNameElementForm;
import com.echothree.control.user.offer.common.result.EditOfferNameElementResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferNameElementSpec;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.model.data.offer.server.value.OfferNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDetailValue;
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

public class EditOfferNameElementCommand
        extends BaseEditCommand<OfferNameElementSpec, OfferNameElementEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferNameElement.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Offset", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("Length", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOfferNameElementCommand */
    public EditOfferNameElementCommand(UserVisitPK userVisitPK, EditOfferNameElementForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditOfferNameElementResult result = OfferResultFactory.getEditOfferNameElementResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String offerNameElementName = spec.getOfferNameElementName();
            OfferNameElement offerNameElement = offerControl.getOfferNameElementByName(offerNameElementName);
            
            if(offerNameElement != null) {
                result.setOfferNameElement(offerControl.getOfferNameElementTransfer(getUserVisit(), offerNameElement));
                
                if(lockEntity(offerNameElement)) {
                    OfferNameElementDescription offerNameElementDescription = offerControl.getOfferNameElementDescription(offerNameElement, getPreferredLanguage());
                    OfferNameElementEdit edit = OfferEditFactory.getOfferNameElementEdit();
                    OfferNameElementDetail offerNameElementDetail = offerNameElement.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setOfferNameElementName(offerNameElementDetail.getOfferNameElementName());
                    edit.setOffset(offerNameElementDetail.getOffset().toString());
                    edit.setLength(offerNameElementDetail.getLength().toString());
                    edit.setValidationPattern(offerNameElementDetail.getValidationPattern());
                    
                    if(offerNameElementDescription != null) {
                        edit.setDescription(offerNameElementDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(offerNameElement));
            } else {
                addExecutionError(ExecutionErrors.UnknownOfferNameElementName.name(), offerNameElementName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String offerNameElementName = spec.getOfferNameElementName();
            OfferNameElement offerNameElement = offerControl.getOfferNameElementByNameForUpdate(offerNameElementName);
            
            if(offerNameElement != null) {
                offerNameElementName = edit.getOfferNameElementName();
                OfferNameElement duplicateOfferNameElement = offerControl.getOfferNameElementByName(offerNameElementName);
                
                if(duplicateOfferNameElement == null || offerNameElement.equals(duplicateOfferNameElement)) {
                    if(lockEntityForUpdate(offerNameElement)) {
                        try {
                            var partyPK = getPartyPK();
                            OfferNameElementDetailValue offerNameElementDetailValue = offerControl.getOfferNameElementDetailValueForUpdate(offerNameElement);
                            OfferNameElementDescription offerNameElementDescription = offerControl.getOfferNameElementDescriptionForUpdate(offerNameElement, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            offerNameElementDetailValue.setOfferNameElementName(edit.getOfferNameElementName());
                            offerNameElementDetailValue.setOffset(Integer.valueOf(edit.getOffset()));
                            offerNameElementDetailValue.setLength(Integer.valueOf(edit.getLength()));
                            offerNameElementDetailValue.setValidationPattern(edit.getValidationPattern());
                            
                            offerControl.updateOfferNameElementFromValue(offerNameElementDetailValue, partyPK);
                            
                            if(offerNameElementDescription == null && description != null) {
                                offerControl.createOfferNameElementDescription(offerNameElement, getPreferredLanguage(), description, partyPK);
                            } else if(offerNameElementDescription != null && description == null) {
                                offerControl.deleteOfferNameElementDescription(offerNameElementDescription, partyPK);
                            } else if(offerNameElementDescription != null && description != null) {
                                OfferNameElementDescriptionValue offerNameElementDescriptionValue = offerControl.getOfferNameElementDescriptionValue(offerNameElementDescription);
                                
                                offerNameElementDescriptionValue.setDescription(description);
                                offerControl.updateOfferNameElementDescriptionFromValue(offerNameElementDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(offerNameElement);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateOfferNameElementName.name(), offerNameElementName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOfferNameElementName.name(), offerNameElementName);
            }
        }
        
        return result;
    }
    
}
