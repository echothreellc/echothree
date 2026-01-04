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

package com.echothree.model.control.vendor.server.logic;

import com.echothree.control.user.vendor.common.spec.VendorItemUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.vendor.common.exception.UnknownVendorItemNameException;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class VendorItemLogic
        extends BaseLogic {

    protected VendorItemLogic() {
        super();
    }

    public static VendorItemLogic getInstance() {
        return CDI.current().select(VendorItemLogic.class).get();
    }

    public VendorItem getVendorItemByUniversalSpec(final ExecutionErrorAccumulator eea, final VendorItemUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = universalSpec.getVendorName();
        var partyName = universalSpec.getPartyName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(vendorName, partyName);
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        VendorItem vendorItem = null;

        if(nameParameterCount == 1 && possibleEntitySpecs == 0) {
            var vendorItemName = universalSpec.getVendorItemName();

            if(vendorItemName != null) {
                var vendor = VendorLogic.getInstance().getVendorByName(eea, vendorName, partyName, null);
                var vendorParty = vendor.getParty();

                vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName, entityPermission);

                if(vendorItem == null) {
                    handleExecutionError(UnknownVendorItemNameException.class, eea, ExecutionErrors.UnknownVendorItemName.name(),
                            vendor.getVendorName(), vendorItemName);

                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.WorkflowStep.name());

            if(!eea.hasExecutionErrors()) {
                vendorItem = vendorControl.getVendorItemByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return vendorItem;
    }

    public VendorItem getVendorItemByUniversalSpec(final ExecutionErrorAccumulator eea, final VendorItemUniversalSpec universalSpec) {
        return getVendorItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public VendorItem getVendorItemByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final VendorItemUniversalSpec universalSpec) {
        return getVendorItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
