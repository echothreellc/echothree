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

import com.echothree.control.user.core.common.form.CreateMimeTypeFileExtensionForm;
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
public class CreateMimeTypeFileExtensionCommand
        extends BaseSimpleCommand<CreateMimeTypeFileExtensionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("FileExtension", FieldType.STRING, true, 1L, 10L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateMimeTypeFileExtensionCommand */
    public CreateMimeTypeFileExtensionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeName = form.getMimeTypeName();
        var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);
        
        if(mimeType != null) {
            var fileExtension = form.getFileExtension();
            var mimeTypeFileExtension = mimeTypeControl.getMimeTypeFileExtension(fileExtension);
            
            if(mimeTypeFileExtension == null) {
                var isDefault = Boolean.valueOf(form.getIsDefault());
                
                mimeTypeControl.createMimeTypeFileExtension(mimeType, fileExtension, isDefault);
            } else {
                addExecutionError(ExecutionErrors.DuplicateFileExtension.name(), fileExtension);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }
        
        return null;
    }
    
}
