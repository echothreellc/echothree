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

import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.GetMimeTypeFileExtensionsResult;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetMimeTypeFileExtensionsCommand
        extends BaseMultipleEntitiesCommand<MimeTypeFileExtension, GetMimeTypeFileExtensionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetMimeTypeFileExtensionsCommand */
    public GetMimeTypeFileExtensionsCommand(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<MimeTypeFileExtension> getEntities() {
        var coreControl = getCoreControl();

        return coreControl.getMimeTypeFileExtensions();
    }

    @Override
    protected BaseResult getTransfers(Collection<MimeTypeFileExtension> entities) {
        var coreControl = getCoreControl();
        GetMimeTypeFileExtensionsResult result = CoreResultFactory.getGetMimeTypeFileExtensionsResult();

        result.setMimeTypeFileExtensions(coreControl.getMimeTypeFileExtensionTransfers(getUserVisit(), entities));

        return result;
    }

}
