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

import com.echothree.control.user.contact.common.form.CreatePostalAddressLineForm;
import com.echothree.model.control.contact.server.control.ContactControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreatePostalAddressLineCommand
        extends BaseSimpleCommand<CreatePostalAddressLineForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PostalAddressFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PostalAddressLineSortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Prefix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludePrefix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Suffix", FieldType.STRING, false, 1L, 10L),
                new FieldDefinition("AlwaysIncludeSuffix", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CollapseIfEmpty", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePostalAddressLineCommand */
    public CreatePostalAddressLineCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var postalAddressFormatName = form.getPostalAddressFormatName();
        var postalAddressFormat = contactControl.getPostalAddressFormatByName(postalAddressFormatName);
        
        if(postalAddressFormat != null) {
            var postalAddressLineSortOrder = Integer.valueOf(form.getPostalAddressLineSortOrder());
            var postalAddressLine = contactControl.getPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder);
            
            if(postalAddressLine == null) {
                var prefix = form.getPrefix();
                var alwaysIncludePrefix = Boolean.valueOf(form.getAlwaysIncludePrefix());
                var suffix = form.getSuffix();
                var alwaysIncludeSuffix = Boolean.valueOf(form.getAlwaysIncludeSuffix());
                var collapseIfEmpty = Boolean.valueOf(form.getCollapseIfEmpty());
                
                contactControl.createPostalAddressLine(postalAddressFormat, postalAddressLineSortOrder, prefix, alwaysIncludePrefix,
                        suffix, alwaysIncludeSuffix, collapseIfEmpty, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.DuplicatePostalAddressLine.name(), postalAddressLineSortOrder);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
        }
        
        return null;
    }
    
}
