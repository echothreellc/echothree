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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ServerServiceTransfer
        extends BaseTransfer {
    
    private ServerTransfer server;
    private ServiceTransfer service;
    
    /** Creates a new instance of ServerServiceTransfer */
    public ServerServiceTransfer(ServerTransfer server, ServiceTransfer service) {
        this.server = server;
        this.service = service;
    }

    /**
     * Returns the server.
     * @return the server
     */
    public ServerTransfer getServer() {
        return server;
    }

    /**
     * Sets the server.
     * @param server the server to set
     */
    public void setServer(ServerTransfer server) {
        this.server = server;
    }

    /**
     * Returns the service.
     * @return the service
     */
    public ServiceTransfer getService() {
        return service;
    }

    /**
     * Sets the service.
     * @param service the service to set
     */
    public void setService(ServiceTransfer service) {
        this.service = service;
    }
    
}
