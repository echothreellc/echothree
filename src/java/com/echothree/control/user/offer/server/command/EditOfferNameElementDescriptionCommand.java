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
import com.echothree.control.user.offer.common.edit.OfferNameElementDescriptionEdit;
import com.echothree.control.user.offer.common.form.EditOfferNameElementDescriptionForm;
import com.echothree.control.user.offer.common.result.EditOfferNameElementDescriptionResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferNameElementDescriptionSpec;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.offer.server.value.OfferNameElementDescriptionValue;
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

public class EditOfferNameElementDescriptionCommand
        extends BaseEditCommand<OfferNameElementDescriptionSpec, OfferNameElementDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferNameElement.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferNameElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOfferNameElementDescriptionCommand */
    public EditOfferNameElementDescriptionCommand(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditOfferNameElementDescriptionResult result = OfferResultFactory.getEditOfferNameElementDescriptionResult();
        String offerNameElementName = spec.getOfferNameElementName();
        OfferNameElement offerNameElement = offerControl.getOfferNameElementByName(offerNameElementName);
        
        if(offerNameElement != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    OfferNameElementDescription offerNameElementDescription = offerControl.getOfferNameElementDescription(offerNameElement, language);
                    
                    if(offerNameElementDescription != null) {
                        result.setOfferNameElementDescription(offerControl.getOfferNameElementDescriptionTransfer(getUserVisit(), offerNameElementDescription));
                        
                        if(lockEntity(offerNameElement)) {
                            OfferNameElementDescriptionEdit edit = OfferEditFactory.getOfferNameElementDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(offerNameElementDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(offerNameElement));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferNameElementDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    OfferNameElementDescriptionValue offerNameElementDescriptionValue = offerControl.getOfferNameElementDescriptionValueForUpdate(offerNameElement, language);
                    
                    if(offerNameElementDescriptionValue != null) {
                        if(lockEntityForUpdate(offerNameElement)) {
                            try {
                                String description = edit.getDescription();
                                
                                offerNameElementDescriptionValue.setDescription(description);
                                
                                offerControl.updateOfferNameElementDescriptionFromValue(offerNameElementDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(offerNameElement);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferNameElementDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferNameElementName.name(), offerNameElementName);
        }
        
        return result;
    }
    
}
