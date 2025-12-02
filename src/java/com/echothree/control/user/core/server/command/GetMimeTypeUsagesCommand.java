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

import com.echothree.control.user.core.common.form.GetMimeTypeUsagesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetMimeTypeUsagesCommand
        extends BaseMultipleEntitiesCommand<MimeTypeUsage, GetMimeTypeUsagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetMimeTypeUsagesCommand */
    public GetMimeTypeUsagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    MimeType mimeType;
    
    @Override
    protected Collection<MimeTypeUsage> getEntities() {
        Collection<MimeTypeUsage> mimeTypeUsages = null;
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeName = form.getMimeTypeName();
        
        mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);
        
        if(mimeType != null) {
            mimeTypeUsages = mimeTypeControl.getMimeTypeUsagesByMimeType(mimeType);
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
        }
        
        return mimeTypeUsages;
    }

    @Override
    protected BaseResult getResult(Collection<MimeTypeUsage> entities) {
        var result = CoreResultFactory.getGetMimeTypeUsagesResult();

        if(entities != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var userVisit = getUserVisit();

            result.setMimeType(mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType));
            result.setMimeTypeUsages(mimeTypeControl.getMimeTypeUsageTransfersByMimeType(userVisit, entities));
        }
        
        return result;
    }
    
}
