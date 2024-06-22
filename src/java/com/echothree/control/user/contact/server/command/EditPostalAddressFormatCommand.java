// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.contact.common.edit.PostalAddressFormatEdit;
import com.echothree.control.user.contact.common.form.EditPostalAddressFormatForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressFormatResult;
import com.echothree.control.user.contact.common.spec.PostalAddressFormatSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDescription;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDetail;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDescriptionValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDetailValue;
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

public class EditPostalAddressFormatCommand
        extends BaseEditCommand<PostalAddressFormatSpec, PostalAddressFormatEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditPostalAddressFormatCommand */
    public EditPostalAddressFormatCommand(UserVisitPK userVisitPK, EditPostalAddressFormatForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        EditPostalAddressFormatResult result = ContactResultFactory.getEditPostalAddressFormatResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String postalAddressFormatName = spec.getPostalAddressFormatName();
            PostalAddressFormat postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
            
            if(postalAddressFormat != null) {
                result.setPostalAddressFormat(contactControl.getPostalAddressFormatTransfer(getUserVisit(), postalAddressFormat));
                
                if(lockEntity(postalAddressFormat)) {
                    PostalAddressFormatDescription postalAddressFormatDescription = contactControl.getPostalAddressFormatDescription(postalAddressFormat, getPreferredLanguage());
                    PostalAddressFormatEdit edit = ContactEditFactory.getPostalAddressFormatEdit();
                    PostalAddressFormatDetail postalAddressFormatDetail = postalAddressFormat.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setPostalAddressFormatName(postalAddressFormatDetail.getPostalAddressFormatName());
                    edit.setIsDefault(postalAddressFormatDetail.getIsDefault().toString());
                    edit.setSortOrder(postalAddressFormatDetail.getSortOrder().toString());
                    
                    if(postalAddressFormatDescription != null)
                        edit.setDescription(postalAddressFormatDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(postalAddressFormat));
            } else {
                addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String postalAddressFormatName = spec.getPostalAddressFormatName();
            PostalAddressFormat postalAddressFormat = contactControl.getPostalAddressFormatByNameForUpdate(postalAddressFormatName);
            
            if(postalAddressFormat != null) {
                postalAddressFormatName = edit.getPostalAddressFormatName();
                PostalAddressFormat duplicatePostalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
                
                if(duplicatePostalAddressFormat == null || postalAddressFormat.equals(duplicatePostalAddressFormat)) {
                    if(lockEntityForUpdate(postalAddressFormat)) {
                        try {
                            var partyPK = getPartyPK();
                            PostalAddressFormatDetailValue postalAddressFormatDetailValue = contactControl.getPostalAddressFormatDetailValueForUpdate(postalAddressFormat);
                            PostalAddressFormatDescription postalAddressFormatDescription = contactControl.getPostalAddressFormatDescriptionForUpdate(postalAddressFormat, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            postalAddressFormatDetailValue.setPostalAddressFormatName(edit.getPostalAddressFormatName());
                            postalAddressFormatDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            postalAddressFormatDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            contactControl.updatePostalAddressFormatFromValue(postalAddressFormatDetailValue, partyPK);
                            
                            if(postalAddressFormatDescription == null && description != null) {
                                contactControl.createPostalAddressFormatDescription(postalAddressFormat, getPreferredLanguage(), description, partyPK);
                            } else if(postalAddressFormatDescription != null && description == null) {
                                contactControl.deletePostalAddressFormatDescription(postalAddressFormatDescription, partyPK);
                            } else if(postalAddressFormatDescription != null && description != null) {
                                PostalAddressFormatDescriptionValue postalAddressFormatDescriptionValue = contactControl.getPostalAddressFormatDescriptionValue(postalAddressFormatDescription);
                                
                                postalAddressFormatDescriptionValue.setDescription(description);
                                contactControl.updatePostalAddressFormatDescriptionFromValue(postalAddressFormatDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(postalAddressFormat);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePostalAddressFormatName.name(), postalAddressFormatName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
            }
            
            if(hasExecutionErrors()) {
                result.setPostalAddressFormat(contactControl.getPostalAddressFormatTransfer(getUserVisit(), postalAddressFormat));
                result.setEntityLock(getEntityLockTransfer(postalAddressFormat));
            }
        }
        
        return result;
    }
    
}
