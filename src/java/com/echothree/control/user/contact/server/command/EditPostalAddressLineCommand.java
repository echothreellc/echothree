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
import com.echothree.control.user.contact.common.edit.PostalAddressLineEdit;
import com.echothree.control.user.contact.common.form.EditPostalAddressLineForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.spec.PostalAddressLineSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
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

public class EditPostalAddressLineCommand
        extends BaseEditCommand<PostalAddressLineSpec, PostalAddressLineEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(6);
        temp.add(new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L));
        temp.add(new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L));
        temp.add(new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("CollapseIfEmpty", FieldType.BOOLEAN, true, null, null));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditPostalAddressLineCommand */
    public EditPostalAddressLineCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var result = ContactResultFactory.getEditPostalAddressLineResult();
        var postalAddressFormatName = spec.getPostalAddressFormatName();
        var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
        
        if(postalAddressFormat != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var postalAddressLineSortOrder = Integer.valueOf(spec.getPostalAddressLineSortOrder());
                var postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);
                
                if(postalAddressLine != null) {
                    result.setPostalAddressLine(contactControl.getPostalAddressLineTransfer(getUserVisit(), postalAddressLine));
                    
                    if(lockEntity(postalAddressLine)) {
                        var edit = ContactEditFactory.getPostalAddressLineEdit();
                        var postalAddressLineDetail = postalAddressLine.getLastDetail();
                        
                        result.setEdit(edit);
                        edit.setPostalAddressLineSortOrder(postalAddressLineDetail.getPostalAddressLineSortOrder().toString());
                        edit.setPrefix(postalAddressLineDetail.getPrefix());
                        edit.setAlwaysIncludePrefix(postalAddressLineDetail.getAlwaysIncludePrefix().toString());
                        edit.setSuffix(postalAddressLineDetail.getSuffix());
                        edit.setAlwaysIncludeSuffix(postalAddressLineDetail.getAlwaysIncludeSuffix().toString());
                        edit.setCollapseIfEmpty(postalAddressLineDetail.getCollapseIfEmpty().toString());
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(postalAddressLine));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPostalAddressLine.name(), postalAddressLineSortOrder.toString());
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var postalAddressLineSortOrder = Integer.valueOf(spec.getPostalAddressLineSortOrder());
                var postalAddressLine = contactControl.getPostalAddressLineForUpdate(postalAddressFormat, postalAddressLineSortOrder);
                
                if(postalAddressLine != null) {
                    postalAddressLineSortOrder = Integer.valueOf(edit.getPostalAddressLineSortOrder());
                    var duplicatePostalAddressLine = contactControl.getPostalAddressLineForUpdate(postalAddressFormat, postalAddressLineSortOrder);
                    
                    if(duplicatePostalAddressLine == null || postalAddressLine.equals(duplicatePostalAddressLine)) {
                        if(lockEntityForUpdate(postalAddressLine)) {
                            try {
                                var postalAddressLineDetailValue = contactControl.getPostalAddressLineDetailValueForUpdate(postalAddressLine);
                                
                                postalAddressLineDetailValue.setPostalAddressLineSortOrder(Integer.valueOf(edit.getPostalAddressLineSortOrder()));
                                postalAddressLineDetailValue.setPrefix(edit.getPrefix());
                                postalAddressLineDetailValue.setAlwaysIncludePrefix(Boolean.valueOf(edit.getAlwaysIncludePrefix()));
                                postalAddressLineDetailValue.setSuffix(edit.getSuffix());
                                postalAddressLineDetailValue.setAlwaysIncludeSuffix(Boolean.valueOf(edit.getAlwaysIncludeSuffix()));
                                postalAddressLineDetailValue.setCollapseIfEmpty(Boolean.valueOf(edit.getCollapseIfEmpty()));
                                
                                contactControl.updatePostalAddressLineFromValue(postalAddressLineDetailValue, getPartyPK());
                            } finally {
                                unlockEntity(postalAddressLine);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePostalAddressLine.name(), postalAddressLineSortOrder.toString());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPostalAddressLine.name(), postalAddressLineSortOrder.toString());
                }
                
                if(hasExecutionErrors()) {
                    result.setPostalAddressLine(contactControl.getPostalAddressLineTransfer(getUserVisit(), postalAddressLine));
                    result.setEntityLock(getEntityLockTransfer(postalAddressLine));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
        
        return result;
    }
    
}
