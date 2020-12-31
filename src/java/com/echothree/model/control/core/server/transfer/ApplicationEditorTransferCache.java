// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.core.common.transfer.EditorTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ApplicationEditorTransferCache
        extends BaseCoreTransferCache<ApplicationEditor, ApplicationEditorTransfer> {

    /** Creates a new instance of ApplicationEditorTransferCache */
    public ApplicationEditorTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }

    public ApplicationEditorTransfer getApplicationEditorTransfer(ApplicationEditor applicationEditor) {
        ApplicationEditorTransfer applicationEditorTransfer = get(applicationEditor);

        if(applicationEditorTransfer == null) {
            ApplicationEditorDetail applicationEditorDetail = applicationEditor.getLastDetail();
            ApplicationTransfer application = coreControl.getApplicationTransfer(userVisit, applicationEditorDetail.getApplication());
            EditorTransfer editor = coreControl.getEditorTransfer(userVisit, applicationEditorDetail.getEditor());
            Boolean isDefault = applicationEditorDetail.getIsDefault();
            Integer sortOrder = applicationEditorDetail.getSortOrder();

            applicationEditorTransfer = new ApplicationEditorTransfer(application, editor, isDefault, sortOrder);
            put(applicationEditor, applicationEditorTransfer);
        }

        return applicationEditorTransfer;
    }

}
