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

import com.echothree.control.user.core.common.form.GetMimeTypesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetMimeTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<MimeType, GetMimeTypesForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    /** Creates a new instance of GetMimeTypesCommand */
    public GetMimeTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    MimeTypeUsageType mimeTypeUsageType;

    @Override
    protected void handleForm() {
        var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();

        mimeTypeUsageType = mimeTypeUsageTypeName == null ? null :
                MimeTypeLogic.getInstance().getMimeTypeUsageTypeByName(this, mimeTypeUsageTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

        return hasExecutionErrors() ? null :
                mimeTypeUsageType == null ? mimeTypeControl.countMimeTypes() :
                        mimeTypeControl.countMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
    }

    @Override
    protected Collection<MimeType> getEntities() {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        Collection<MimeType> mimeTypes = null;

        if(!hasExecutionErrors()) {
            if(mimeTypeUsageType == null) {
                mimeTypes = mimeTypeControl.getMimeTypes();
            } else {
                mimeTypes = mimeTypeControl.getMimeTypesByMimeTypeUsageType(mimeTypeUsageType);
            }
        }

        return mimeTypes;
    }

    @Override
    protected BaseResult getResult(Collection<MimeType> entities) {
        var result = CoreResultFactory.getGetMimeTypesResult();

        if(entities != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

            result.setMimeTypes(mimeTypeControl.getMimeTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
