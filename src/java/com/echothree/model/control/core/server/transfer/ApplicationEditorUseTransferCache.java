// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.ApplicationEditorUseTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.ApplicationEditor;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ApplicationEditorUseTransferCache
        extends BaseCoreTransferCache<ApplicationEditorUse, ApplicationEditorUseTransfer> {

    /** Creates a new instance of ApplicationEditorUseTransferCache */
    public ApplicationEditorUseTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }

    public ApplicationEditorUseTransfer getApplicationEditorUseTransfer(ApplicationEditorUse applicationEditorUse) {
        ApplicationEditorUseTransfer applicationEditorUseTransfer = get(applicationEditorUse);

        if(applicationEditorUseTransfer == null) {
            ApplicationEditorUseDetail applicationEditorUseDetail = applicationEditorUse.getLastDetail();
            ApplicationTransfer applicationTransfer = coreControl.getApplicationTransfer(userVisit, applicationEditorUseDetail.getApplication());
            String applicationEditorUseName = applicationEditorUseDetail.getApplicationEditorUseName();
            ApplicationEditor defaultApplicationEditor = applicationEditorUseDetail.getDefaultApplicationEditor();
            ApplicationEditorTransfer defaultApplicationEditorTransfer = defaultApplicationEditor == null ? null : coreControl.getApplicationEditorTransfer(userVisit, defaultApplicationEditor);
            Integer defaultHeight = applicationEditorUseDetail.getDefaultHeight();
            Integer defaultWidth = applicationEditorUseDetail.getDefaultWidth();
            Boolean isDefault = applicationEditorUseDetail.getIsDefault();
            Integer sortOrder = applicationEditorUseDetail.getSortOrder();
            String description = coreControl.getBestApplicationEditorUseDescription(applicationEditorUse, getLanguage());

            applicationEditorUseTransfer = new ApplicationEditorUseTransfer(applicationTransfer, applicationEditorUseName, defaultApplicationEditorTransfer,
                    defaultHeight, defaultWidth, isDefault, sortOrder, description);
            put(applicationEditorUse, applicationEditorUseTransfer);
        }

        return applicationEditorUseTransfer;
    }

}
