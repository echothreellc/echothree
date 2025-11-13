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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityVisitTransfer;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityVisitTransferCache
        extends BaseCoreTransferCache<EntityVisit, EntityVisitTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    boolean includeEntityInstance;
    boolean includeVisitedEntityInstance;
    boolean includeVisitedTime;

    /** Creates a new instance of EntityVisitTransferCache */
    public EntityVisitTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeEntityInstance = options.contains(CoreOptions.EntityVisitIncludeEntityInstance);
            includeVisitedEntityInstance = options.contains(CoreOptions.EntityVisitIncludeVisitedEntityInstance);
            includeVisitedTime = options.contains(CoreOptions.EntityVisitIncludeVisitedTime);
        }
    }
    
    public EntityVisitTransfer getEntityVisitTransfer(UserVisit userVisit, EntityVisit entityVisit) {
        var entityVisitTransfer = get(entityVisit);
        
        if(entityVisitTransfer == null) {
            var unformattedVisitedTime = entityVisit.getVisitedTime();

            entityVisitTransfer = new EntityVisitTransfer(unformattedVisitedTime);
            put(userVisit, entityVisit, entityVisitTransfer);

            if(includeEntityInstance) {
                entityVisitTransfer.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityVisit.getEntityInstance(), false, false, false, false));
            }

            if(includeVisitedEntityInstance) {
                entityVisitTransfer.setVisitedEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityVisit.getVisitedEntityInstance(), false, false, false, false));
            }

            if(includeVisitedTime) {
                entityVisitTransfer.setVisitedTime(formatTypicalDateTime(userVisit, unformattedVisitedTime));
            }
        }

        return entityVisitTransfer;
    }
    
}
