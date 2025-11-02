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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateMimeTypeForm;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateMimeTypeCommand
        extends BaseSimpleCommand<CreateMimeTypeForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("EntityAttributeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of CreateMimeTypeCommand */
    public CreateMimeTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
       var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
       var mimeTypeName = form.getMimeTypeName();
       var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

        if(mimeType == null) {
            var entityAttributeTypeName = form.getEntityAttributeTypeName();
            var entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

            if(entityAttributeType != null) {
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                var createdBy = getPartyPK();

                mimeType = mimeTypeControl.createMimeType(mimeTypeName, entityAttributeType, isDefault, sortOrder, createdBy);

                if(description != null) {
                    var language = getPreferredLanguage();

                    mimeTypeControl.createMimeTypeDescription(mimeType, language, description, createdBy);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateMimeTypeName.name(), mimeTypeName);
        }

        return null;
    }
    
}
