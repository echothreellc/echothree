// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetMimeTypeFileExtensionCommand
        extends BaseSingleEntityCommand<MimeTypeFileExtension, GetMimeTypeFileExtensionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FileExtension", FieldType.STRING, true, 1L, 10L)
                ));
    }
    
    /** Creates a new instance of GetMimeTypeFileExtensionCommand */
    public GetMimeTypeFileExtensionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected MimeTypeFileExtension getEntity() {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var fileExtension = form.getFileExtension();
        var mimeTypeFileExtension = mimeTypeControl.getMimeTypeFileExtension(fileExtension);

        if(mimeTypeFileExtension == null) {
            addExecutionError(ExecutionErrors.UnknownFileExtension.name(), fileExtension);
        }

        return mimeTypeFileExtension;
    }

    @Override
    protected BaseResult getResult(MimeTypeFileExtension mimeTypeFileExtension) {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var result = CoreResultFactory.getGetMimeTypeFileExtensionResult();

        if(mimeTypeFileExtension != null) {
            result.setMimeTypeFileExtension(mimeTypeControl.getMimeTypeFileExtensionTransfer(getUserVisit(), mimeTypeFileExtension));
        }

        return result;
    }

}
