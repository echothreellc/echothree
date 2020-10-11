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

import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypeUsageTypesResult;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetMimeTypeUsageTypesCommand
        extends BaseMultipleEntitiesCommand<MimeTypeUsageType, GetMimeTypeUsageTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetMimeTypeUsageTypesCommand */
    public GetMimeTypeUsageTypesCommand(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<MimeTypeUsageType> getEntities() {
        var coreControl = getCoreControl();

        return coreControl.getMimeTypeUsageTypes();
    }

    @Override
    protected BaseResult getTransfers(Collection<MimeTypeUsageType> entities) {
        var coreControl = getCoreControl();
        GetMimeTypeUsageTypesResult result = CoreResultFactory.getGetMimeTypeUsageTypesResult();

        result.setMimeTypeUsageTypes(coreControl.getMimeTypeUsageTypeTransfers(getUserVisit(), entities));

        return result;
    }

}
