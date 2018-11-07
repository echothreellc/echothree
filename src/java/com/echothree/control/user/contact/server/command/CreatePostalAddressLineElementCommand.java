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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.CreatePostalAddressLineElementForm;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
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

public class CreatePostalAddressLineElementCommand
        extends BaseSimpleCommand<CreatePostalAddressLineElementForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("PostalAddressLineElementSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("PostalAddressElementTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePostalAddressLineElementCommand */
    public CreatePostalAddressLineElementCommand(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        String postalAddressFormatName = form.getPostalAddressFormatName();
        PostalAddressFormat postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
        
        if(postalAddressFormat != null) {
            Integer postalAddressLineSortOrder = Integer.valueOf(form.getPostalAddressLineSortOrder());
            PostalAddressLine postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);
            
            if(postalAddressLine != null) {
                Integer postalAddressLineElementSortOrder = Integer.valueOf(form.getPostalAddressLineElementSortOrder());
                PostalAddressLineElement postalAddressLineElement = contactControl.getPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder);
                
                if(postalAddressLineElement == null) {
                    String postalAddressElementTypeName = form.getPostalAddressElementTypeName();
                    PostalAddressElementType postalAddressElementType = contactControl.getPostalAddressElementTypeByName(postalAddressElementTypeName);
                    
                    if(postalAddressElementType != null) {
                        String prefix = form.getPrefix();
                        Boolean alwaysIncludePrefix = Boolean.valueOf(form.getAlwaysIncludePrefix());
                        
                        if(!alwaysIncludePrefix || (alwaysIncludePrefix && prefix != null)) {
                            String suffix = form.getSuffix();
                            Boolean alwaysIncludeSuffix = Boolean.valueOf(form.getAlwaysIncludeSuffix());

                            if(!alwaysIncludeSuffix || (alwaysIncludeSuffix && suffix != null)) {
                                contactControl.createPostalAddressLineElement(postalAddressLine, postalAddressLineElementSortOrder, postalAddressElementType, prefix, alwaysIncludePrefix, suffix,
                                        alwaysIncludeSuffix, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.CannotAlwaysIncludeEmptySuffix.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.CannotAlwaysIncludeEmptyPrefix.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPostalAddressElementTypeName.name(), postalAddressElementTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePostalAddressLineElement.name(), postalAddressLineElementSortOrder);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPostalAddressLine.name(), postalAddressLineSortOrder);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
        
        return null;
    }
    
}
