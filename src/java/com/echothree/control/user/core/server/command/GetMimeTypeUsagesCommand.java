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

import com.echothree.control.user.core.common.form.GetMimeTypeUsagesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.factory.MimeTypeUsageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMimeTypeUsagesCommand
        extends BasePaginatedMultipleEntitiesCommand<MimeTypeUsage, GetMimeTypeUsagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
        );
    }

    @Inject
    MimeTypeControl mimeTypeControl;

    @Inject
    MimeTypeLogic mimeTypeLogic;

    /** Creates a new instance of GetMimeTypeUsagesCommand */
    public GetMimeTypeUsagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private MimeType mimeType;

    @Override
    protected void handleForm() {
        mimeType = mimeTypeLogic.getMimeTypeByName(this, form.getMimeTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : mimeTypeControl.countMimeTypeUsagesByMimeType(mimeType);
    }

    @Override
    protected Collection<MimeTypeUsage> getEntities() {
        return hasExecutionErrors() ? null : mimeTypeControl.getMimeTypeUsagesByMimeType(mimeType);
    }

    @Override
    protected BaseResult getResult(Collection<MimeTypeUsage> entities) {
        var result = CoreResultFactory.getGetMimeTypeUsagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setMimeType(mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType));

            if(session.hasLimit(MimeTypeUsageFactory.class)) {
                result.setMimeTypeUsageCount(getTotalEntities());
            }

            result.setMimeTypeUsages(mimeTypeControl.getMimeTypeUsageTransfersByMimeType(userVisit, entities));
        }
        
        return result;
    }
    
}
