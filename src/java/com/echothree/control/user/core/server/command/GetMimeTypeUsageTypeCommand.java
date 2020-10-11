// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypeUsageTypeResult;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetMimeTypeUsageTypeCommand
        extends BaseSingleEntityCommand<MimeTypeUsageType, GetMimeTypeUsageTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetMimeTypeUsageTypeCommand */
    public GetMimeTypeUsageTypeCommand(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected MimeTypeUsageType getEntity() {
        var coreControl = getCoreControl();
        String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
        MimeTypeUsageType mimeTypeUsageType = coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);

        if(mimeTypeUsageType == null) {
            addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
        }

        return mimeTypeUsageType;
    }

    @Override
    protected BaseResult getTransfer(MimeTypeUsageType mimeTypeUsageType) {
        var coreControl = getCoreControl();
        GetMimeTypeUsageTypeResult result = CoreResultFactory.getGetMimeTypeUsageTypeResult();

        if(mimeTypeUsageType != null) {
            UserVisit userVisit = getUserVisit();

            result.setMimeTypeUsageType(coreControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType));
        }

        return result;
    }

}
