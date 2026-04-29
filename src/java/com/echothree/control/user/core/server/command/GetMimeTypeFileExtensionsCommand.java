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

import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.factory.MimeTypeFileExtensionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMimeTypeFileExtensionsCommand
        extends BasePaginatedMultipleEntitiesCommand<MimeTypeFileExtension, GetMimeTypeFileExtensionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetMimeTypeFileExtensionsCommand */
    public GetMimeTypeFileExtensionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    MimeTypeControl mimeTypeControl;

    @Override
    protected void handleForm() {
        // No form fields to handle.
    }

    @Override
    protected Long getTotalEntities() {
        return mimeTypeControl.countMimeTypeFileExtensions();
    }

    @Override
    protected Collection<MimeTypeFileExtension> getEntities() {
        return mimeTypeControl.getMimeTypeFileExtensions();
    }

    @Override
    protected BaseResult getResult(Collection<MimeTypeFileExtension> entities) {
        var result = CoreResultFactory.getGetMimeTypeFileExtensionsResult();

        if(entities != null) {
            if(session.hasLimit(MimeTypeFileExtensionFactory.class)) {
                result.setMimeTypeFileExtensionCount(getTotalEntities());
            }

            result.setMimeTypeFileExtensions(mimeTypeControl.getMimeTypeFileExtensionTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
