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

import com.echothree.control.user.contact.common.form.CreateContactMechanismTypeForm;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateContactMechanismTypeCommand
        extends BaseSimpleCommand<CreateContactMechanismTypeForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentContactMechanismTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateContactMechanismTypeCommand */
    public CreateContactMechanismTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismTypeName = form.getContactMechanismTypeName();
        var contactMechanismType = contactControl.getContactMechanismTypeByName(contactMechanismTypeName);
        
        if(contactMechanismType == null) {
            var parentContactMechanismTypeName = form.getParentContactMechanismTypeName();
            ContactMechanismType parentContactMechanismType = null;
            
            if(parentContactMechanismTypeName != null)
                parentContactMechanismType = contactControl.getContactMechanismTypeByName(parentContactMechanismTypeName);
            
            if(parentContactMechanismTypeName == null || parentContactMechanismType != null) {
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                
                contactControl.createContactMechanismType(contactMechanismTypeName, parentContactMechanismType, isDefault, sortOrder);
            } else {
                addExecutionError(ExecutionErrors.UnknownParentContactMechanismTypeName.name(), parentContactMechanismTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateContactMechanismTypeName.name(), contactMechanismTypeName);
        }
        
        return null;
    }
    
}
