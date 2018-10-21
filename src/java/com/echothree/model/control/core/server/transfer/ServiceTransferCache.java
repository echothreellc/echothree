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

import com.echothree.model.control.core.remote.transfer.ProtocolTransfer;
import com.echothree.model.control.core.remote.transfer.ServiceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.core.server.entity.ServiceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ServiceTransferCache
        extends BaseCoreTransferCache<Service, ServiceTransfer> {
    
    /** Creates a new instance of ServiceTransferCache */
    public ServiceTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ServiceTransfer getServiceTransfer(Service service) {
        ServiceTransfer serviceTransfer = get(service);
        
        if(serviceTransfer == null) {
            ServiceDetail serviceDetail = service.getLastDetail();
            String serviceName = serviceDetail.getServiceName();
            Integer port = serviceDetail.getPort();
            ProtocolTransfer protocol = coreControl.getProtocolTransfer(userVisit, serviceDetail.getProtocol());
            Boolean isDefault = serviceDetail.getIsDefault();
            Integer sortOrder = serviceDetail.getSortOrder();
            String description = coreControl.getBestServiceDescription(service, getLanguage());
    
            serviceTransfer = new ServiceTransfer(serviceName, port, protocol, isDefault, sortOrder, description);
            put(service, serviceTransfer);
        }
        
        return serviceTransfer;
    }
    
}
