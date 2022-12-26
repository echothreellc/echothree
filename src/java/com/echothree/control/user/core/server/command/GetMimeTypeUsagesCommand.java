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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetMimeTypeUsagesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypeUsagesResult;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetMimeTypeUsagesCommand
        extends BaseMultipleEntitiesCommand<MimeTypeUsage, GetMimeTypeUsagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetMimeTypeUsagesCommand */
    public GetMimeTypeUsagesCommand(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    MimeType mimeType;
    
    @Override
    protected Collection<MimeTypeUsage> getEntities() {
        Collection<MimeTypeUsage> mimeTypeUsages = null;
        var coreControl = getCoreControl();
        String mimeTypeName = form.getMimeTypeName();
        
        mimeType = coreControl.getMimeTypeByName(mimeTypeName);
        
        if(mimeType != null) {
            mimeTypeUsages = coreControl.getMimeTypeUsagesByMimeType(mimeType);
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }
        
        return mimeTypeUsages;
    }

    @Override
    protected BaseResult getTransfers(Collection<MimeTypeUsage> entities) {
        GetMimeTypeUsagesResult result = CoreResultFactory.getGetMimeTypeUsagesResult();

        if(entities != null) {
            var coreControl = getCoreControl();
            UserVisit userVisit = getUserVisit();

            result.setMimeType(coreControl.getMimeTypeTransfer(userVisit, mimeType));
            result.setMimeTypeUsages(coreControl.getMimeTypeUsageTransfersByMimeType(userVisit, entities));
        }
        
        return result;
    }
    
}
