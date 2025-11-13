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

import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ApplicationEditorUseTransferCache
        extends BaseCoreTransferCache<ApplicationEditorUse, ApplicationEditorUseTransfer> {

    ApplicationControl applicationControl = Session.getModelController(ApplicationControl.class);

    /** Creates a new instance of ApplicationEditorUseTransferCache */
    public ApplicationEditorUseTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    public ApplicationEditorUseTransfer getApplicationEditorUseTransfer(ApplicationEditorUse applicationEditorUse) {
        var applicationEditorUseTransfer = get(applicationEditorUse);

        if(applicationEditorUseTransfer == null) {
            var applicationEditorUseDetail = applicationEditorUse.getLastDetail();
            var applicationTransfer = applicationControl.getApplicationTransfer(userVisit, applicationEditorUseDetail.getApplication());
            var applicationEditorUseName = applicationEditorUseDetail.getApplicationEditorUseName();
            var defaultApplicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();
            var defaultApplicationEditorTransfer = defaultApplicationEditor == null ? null : applicationControl.getApplicationEditorTransfer(userVisit, defaultApplicationEditor);
            var defaultHeight = applicationEditorUseDetail.getDefaultHeight();
            var defaultWidth = applicationEditorUseDetail.getDefaultWidth();
            var isDefault = applicationEditorUseDetail.getIsDefault();
            var sortOrder = applicationEditorUseDetail.getSortOrder();
            var description = applicationControl.getBestApplicationEditorUseDescription(applicationEditorUse, getLanguage(userVisit));

            applicationEditorUseTransfer = new ApplicationEditorUseTransfer(applicationTransfer, applicationEditorUseName, defaultApplicationEditorTransfer,
                    defaultHeight, defaultWidth, isDefault, sortOrder, description);
            put(userVisit, applicationEditorUse, applicationEditorUseTransfer);
        }

        return applicationEditorUseTransfer;
    }

}
