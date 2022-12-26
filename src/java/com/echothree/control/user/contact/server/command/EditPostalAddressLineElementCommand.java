// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.contact.common.edit.PostalAddressLineElementEdit;
import com.echothree.control.user.contact.common.form.EditPostalAddressLineElementForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditPostalAddressLineElementResult;
import com.echothree.control.user.contact.common.spec.PostalAddressLineElementSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.model.data.contact.server.value.PostalAddressLineElementValue;
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

public class EditPostalAddressLineElementCommand
        extends BaseEditCommand<PostalAddressLineElementSpec, PostalAddressLineElementEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(3);
        temp.add(new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("PostalAddressLineElementSortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(6);
        temp.add(new FieldDefinition("PostalAddressLineElementSortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("PostalAddressElementTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L));
        temp.add(new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L));
        temp.add(new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditPostalAddressLineElementCommand */
    public EditPostalAddressLineElementCommand(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        EditPostalAddressLineElementResult result = ContactResultFactory.getEditPostalAddressLineElementResult();
        String postalAddressFormatName = spec.getPostalAddressFormatName();
        PostalAddressFormat postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
        
        if(postalAddressFormat != null) {
            Integer postalAddressLineSortOrder = Integer.valueOf(spec.getPostalAddressLineSortOrder());
            PostalAddressLine postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);
            
            if(postalAddressLine != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    Integer postalAddressLineElementSortOrder = Integer.valueOf(spec.getPostalAddressLineElementSortOrder());
                    PostalAddressLineElement postalAddressLineElement = contactControl.getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder);
                    
                    if(postalAddressLineElement != null) {
                        result.setPostalAddressLineElement(contactControl.getPostalAddressLineElementTransfer(getUserVisit(), postalAddressLineElement));
                        
                        if(lockEntity(postalAddressLineElement)) {
                            PostalAddressLineElementEdit edit = ContactEditFactory.getPostalAddressLineElementEdit();
                            
                            result.setEdit(edit);
                            edit.setPostalAddressLineElementSortOrder(postalAddressLineElement.getPostalAddressLineElementSortOrder().toString());
                            edit.setPostalAddressElementTypeName(postalAddressLineElement.getPostalAddressElementType().getPostalAddressElementTypeName());
                            edit.setPrefix(postalAddressLineElement.getPrefix());
                            edit.setAlwaysIncludePrefix(postalAddressLineElement.getAlwaysIncludePrefix().toString());
                            edit.setSuffix(postalAddressLineElement.getSuffix());
                            edit.setAlwaysIncludeSuffix(postalAddressLineElement.getAlwaysIncludeSuffix().toString());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(postalAddressLineElement));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalAddressLineElement.name(), postalAddressLineElementSortOrder.toString());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    Integer postalAddressLineElementSortOrder = Integer.valueOf(spec.getPostalAddressLineElementSortOrder());
                    PostalAddressLineElement postalAddressLineElement = contactControl.getPostalAddressLineElementForUpdate(postalAddressLine,
                            postalAddressLineElementSortOrder);
                    
                    if(postalAddressLineElement != null) {
                        postalAddressLineElementSortOrder = Integer.valueOf(edit.getPostalAddressLineElementSortOrder());
                        PostalAddressLineElement duplicatePostalAddressLineElement = contactControl.getPostalAddressLineElementForUpdate(postalAddressLine,
                                postalAddressLineElementSortOrder);
                        
                        if(duplicatePostalAddressLineElement == null || postalAddressLineElement.equals(duplicatePostalAddressLineElement)) {
                            String postalAddressElementTypeName = edit.getPostalAddressElementTypeName();
                            PostalAddressElementType postalAddressElementType = contactControl.getPostalAddressElementTypeByName(postalAddressElementTypeName);
                            
                            if(postalAddressElementType != null) {
                                if(lockEntityForUpdate(postalAddressLineElement)) {
                                    try {
                                        PostalAddressLineElementValue postalAddressLineElementValue = contactControl.getPostalAddressLineElementValueForUpdate(postalAddressLineElement);
                                        
                                        postalAddressLineElementValue.setPostalAddressLineElementSortOrder(Integer.valueOf(edit.getPostalAddressLineElementSortOrder()));
                                        postalAddressLineElementValue.setPostalAddressElementTypePK(postalAddressElementType.getPrimaryKey());
                                        postalAddressLineElementValue.setPrefix(edit.getPrefix());
                                        postalAddressLineElementValue.setAlwaysIncludePrefix(Boolean.valueOf(edit.getAlwaysIncludePrefix()));
                                        postalAddressLineElementValue.setSuffix(edit.getSuffix());
                                        postalAddressLineElementValue.setAlwaysIncludeSuffix(Boolean.valueOf(edit.getAlwaysIncludeSuffix()));
                                        
                                        contactControl.updatePostalAddressLineElementFromValue(postalAddressLineElementValue, getPartyPK());
                                    } finally {
                                        unlockEntity(postalAddressLineElement);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownPostalAddressElementTypeName.name(), postalAddressElementTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicatePostalAddressLineElement.name(), postalAddressLineElementSortOrder.toString());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalAddressLineElement.name(), postalAddressLineElementSortOrder.toString());
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setPostalAddressLineElement(contactControl.getPostalAddressLineElementTransfer(getUserVisit(), postalAddressLineElement));
                        result.setEntityLock(getEntityLockTransfer(postalAddressLineElement));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPostalAddressLine.name(), postalAddressLineSortOrder.toString());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
        
        return result;
    }
    
}
