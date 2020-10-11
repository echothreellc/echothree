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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class ApplicationTransferCache
        extends BaseCoreTransferCache<Application, ApplicationTransfer> {

    /** Creates a new instance of ApplicationTransferCache */
    public ApplicationTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CoreOptions.ApplicationIncludeKey));
            setIncludeGuid(options.contains(CoreOptions.ApplicationIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public ApplicationTransfer getApplicationTransfer(Application application) {
        ApplicationTransfer applicationTransfer = get(application);

        if(applicationTransfer == null) {
            ApplicationDetail applicationDetail = application.getLastDetail();
            String applicationName = applicationDetail.getApplicationName();
            Boolean isDefault = applicationDetail.getIsDefault();
            Integer sortOrder = applicationDetail.getSortOrder();
            String description = coreControl.getBestApplicationDescription(application, getLanguage());

            applicationTransfer = new ApplicationTransfer(applicationName, isDefault, sortOrder, description);
            put(application, applicationTransfer);
        }

        return applicationTransfer;
    }

}
