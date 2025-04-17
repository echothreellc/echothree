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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ApplicationEditorTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EditorControl;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ApplicationEditorTransferCache
        extends BaseCoreTransferCache<ApplicationEditor, ApplicationEditorTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EditorControl editorControl = Session.getModelController(EditorControl.class);

    /** Creates a new instance of ApplicationEditorTransferCache */
    public ApplicationEditorTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    public ApplicationEditorTransfer getApplicationEditorTransfer(ApplicationEditor applicationEditor) {
        var applicationEditorTransfer = get(applicationEditor);

        if(applicationEditorTransfer == null) {
            var applicationEditorDetail = applicationEditor.getLastDetail();
            var application = coreControl.getApplicationTransfer(userVisit, applicationEditorDetail.getApplication());
            var editor = editorControl.getEditorTransfer(userVisit, applicationEditorDetail.getEditor());
            var isDefault = applicationEditorDetail.getIsDefault();
            var sortOrder = applicationEditorDetail.getSortOrder();

            applicationEditorTransfer = new ApplicationEditorTransfer(application, editor, isDefault, sortOrder);
            put(applicationEditor, applicationEditorTransfer);
        }

        return applicationEditorTransfer;
    }

}
