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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.PostalAddressFormatDescriptionEdit;
import com.echothree.control.user.contact.common.form.EditPostalAddressFormatDescriptionForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.spec.PostalAddressFormatDescriptionSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditPostalAddressFormatDescriptionCommand
        extends BaseEditCommand<PostalAddressFormatDescriptionSpec, PostalAddressFormatDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditPostalAddressFormatDescriptionCommand */
    public EditPostalAddressFormatDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var result = ContactResultFactory.getEditPostalAddressFormatDescriptionResult();
        var postalAddressFormatName = spec.getPostalAddressFormatName();
        var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
        
        if(postalAddressFormat != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            result.setPostalAddressFormat(contactControl.getPostalAddressFormatTransfer(getUserVisit(), postalAddressFormat));
            
            if(language != null) {
                result.setLanguage(partyControl.getLanguageTransfer(getUserVisit(), language));
                
                if(editMode.equals(EditMode.LOCK)) {
                    var postalAddressFormatDescription = contactControl.getPostalAddressFormatDescription(postalAddressFormat, language);
                    
                    if(postalAddressFormatDescription != null) {
                        result.setPostalAddressFormatDescription(contactControl.getPostalAddressFormatDescriptionTransfer(getUserVisit(), postalAddressFormatDescription));
                        
                        if(lockEntity(postalAddressFormat)) {
                            var edit = ContactEditFactory.getPostalAddressFormatDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(postalAddressFormatDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(postalAddressFormat));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalAddressFormatDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var postalAddressFormatDescriptionValue = contactControl.getPostalAddressFormatDescriptionValueForUpdate(postalAddressFormat, language);
                    
                    if(postalAddressFormatDescriptionValue != null) {
                        if(lockEntityForUpdate(postalAddressFormat)) {
                            try {
                                var description = edit.getDescription();
                                
                                postalAddressFormatDescriptionValue.setDescription(description);
                                
                                contactControl.updatePostalAddressFormatDescriptionFromValue(postalAddressFormatDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(postalAddressFormat);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalAddressFormatDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
        
        return result;
    }
    
}
