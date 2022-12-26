// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.ServiceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ServiceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.ServiceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ServiceDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ServiceDescription, ServiceDescriptionTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of ServiceDescriptionTransferCache */
    public ServiceDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public ServiceDescriptionTransfer getServiceDescriptionTransfer(ServiceDescription serviceDescription) {
        ServiceDescriptionTransfer serviceDescriptionTransfer = get(serviceDescription);
        
        if(serviceDescriptionTransfer == null) {
            ServiceTransfer serviceTransfer = coreControl.getServiceTransfer(userVisit, serviceDescription.getService());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, serviceDescription.getLanguage());
            
            serviceDescriptionTransfer = new ServiceDescriptionTransfer(languageTransfer, serviceTransfer,
                    serviceDescription.getDescription());
            put(serviceDescription, serviceDescriptionTransfer);
        }
        return serviceDescriptionTransfer;
    }
    
}
