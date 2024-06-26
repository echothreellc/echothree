// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.core.common.form.GetMimeTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypesResult;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetMimeTypesCommand
        extends BaseMultipleEntitiesCommand<MimeType, GetMimeTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of GetMimeTypesCommand */
    public GetMimeTypesCommand(UserVisitPK userVisitPK, GetMimeTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<MimeType> getEntities() {
        var coreControl = getCoreControl();
        String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
        MimeTypeUsageType mimeTypeUsageType = mimeTypeUsageTypeName == null ? null : MimeTypeLogic.getInstance().getMimeTypeUsageTypeByName(this, mimeTypeUsageTypeName);
        Collection<MimeType> mimeTypes = null;

        if(!hasExecutionErrors()) {
            if(mimeTypeUsageType == null) {
                mimeTypes = coreControl.getMimeTypes();
            } else {
                mimeTypes = coreControl.getMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
            }
        }

        return mimeTypes;
    }

    @Override
    protected BaseResult getResult(Collection<MimeType> entities) {
        var coreControl = getCoreControl();
        GetMimeTypesResult result = CoreResultFactory.getGetMimeTypesResult();

        result.setMimeTypes(coreControl.getMimeTypeTransfers(getUserVisit(), entities));

        return result;
    }

}
