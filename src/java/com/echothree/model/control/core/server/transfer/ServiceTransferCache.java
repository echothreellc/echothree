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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ServiceTransfer;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.data.core.server.entity.Service;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ServiceTransferCache
        extends BaseCoreTransferCache<Service, ServiceTransfer> {

    ServerControl serverControl = Session.getModelController(ServerControl.class);

    /** Creates a new instance of ServiceTransferCache */
    protected ServiceTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ServiceTransfer getServiceTransfer(UserVisit userVisit, Service service) {
        var serviceTransfer = get(service);
        
        if(serviceTransfer == null) {
            var serviceDetail = service.getLastDetail();
            var serviceName = serviceDetail.getServiceName();
            var port = serviceDetail.getPort();
            var protocol = serverControl.getProtocolTransfer(userVisit, serviceDetail.getProtocol());
            var isDefault = serviceDetail.getIsDefault();
            var sortOrder = serviceDetail.getSortOrder();
            var description = serverControl.getBestServiceDescription(service, getLanguage(userVisit));
    
            serviceTransfer = new ServiceTransfer(serviceName, port, protocol, isDefault, sortOrder, description);
            put(userVisit, service, serviceTransfer);
        }
        
        return serviceTransfer;
    }
    
}
