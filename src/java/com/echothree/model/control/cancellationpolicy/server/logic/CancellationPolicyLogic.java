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

package com.echothree.model.control.cancellationpolicy.server.logic;

import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownCancellationKindNameException;
import com.echothree.model.control.cancellationpolicy.common.exception.UnknownCancellationPolicyNameException;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class CancellationPolicyLogic
        extends BaseLogic {
    
    private CancellationPolicyLogic() {
        super();
    }
    
    private static class CancellationPolicyLogicHolder {
        static CancellationPolicyLogic instance = new CancellationPolicyLogic();
    }
    
    public static CancellationPolicyLogic getInstance() {
        return CancellationPolicyLogicHolder.instance;
    }

    public CancellationKind getCancellationKindByName(final ExecutionErrorAccumulator eea, final String cancellationKindName) {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

        if(cancellationKind == null) {
            handleExecutionError(UnknownCancellationKindNameException.class, eea, ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }

        return cancellationKind;
    }

    public CancellationPolicy getCancellationPolicyByName(final ExecutionErrorAccumulator eea, final String cancellationKindName, final String cancellationPolicyName) {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
        CancellationKind cancellationKind = getCancellationKindByName(eea, cancellationKindName);
        CancellationPolicy cancellationPolicy = null;
        
        if(cancellationKind != null) {
            cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(cancellationKind, cancellationPolicyName);
            
            if(cancellationPolicy == null) {
            handleExecutionError(UnknownCancellationPolicyNameException.class, eea, ExecutionErrors.UnknownCancellationPolicyName.name(),
                    cancellationKind.getLastDetail().getCancellationKindName(), cancellationPolicyName);
            }
        }
        
        return cancellationPolicy;
    }
    
    public CancellationPolicy getDefaultCancellationPolicyByKind(final ExecutionErrorAccumulator eea, final String cancellationKindName,
            final CancellationPolicy cancellationPolicies[]) {
        CancellationPolicy cancellationPolicy = null;

        for(int i = 0 ; cancellationPolicy == null && i < cancellationPolicies.length ; i++) {
            cancellationPolicy = cancellationPolicies[i];
        }

        if(cancellationPolicy == null) {
            CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
            CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);

            if(cancellationKind != null) {
                cancellationPolicy = cancellationPolicyControl.getDefaultCancellationPolicy(cancellationKind);

                if(cancellationPolicy == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownDefaultCancellationPolicy.name(), cancellationKindName);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        }

        return cancellationPolicy;
    }

    public void checkDeleteCancellationPolicy(final ExecutionErrorAccumulator eea, final CancellationPolicy cancellationPolicy) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        // Both CUSTOMERs and VENDORs use Orders and OrderLines, so check for CancellationPolicy use there first.
        boolean inUse = orderControl.countOrdersByCancellationPolicy(cancellationPolicy) != 0;

        if(!inUse) {
            inUse |= orderControl.countOrderLinesByCancellationPolicy(cancellationPolicy) != 0;
        }

        if(!inUse) {
            String cancellationKindName = cancellationPolicy.getLastDetail().getCancellationKind().getLastDetail().getCancellationKindName();
            
            if(cancellationKindName.equals(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION)) {
                ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

                inUse |= itemControl.countItemsByCancellationPolicy(cancellationPolicy) != 0;
            } else if(cancellationKindName.equals(CancellationPolicyConstants.CancellationKind_VENDOR_CANCELLATION)) {
                VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);

                inUse |= vendorControl.countVendorItemsByCancellationPolicy(cancellationPolicy) != 0;
            }
        }

        if(inUse) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteCancellationPolicyInUse.name(), cancellationPolicy.getLastDetail().getCancellationPolicyName());
        }
    }

    public void deleteCancellationPolicy(final CancellationPolicy cancellationPolicy, final BasePK deletedBy) {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);

        cancellationPolicyControl.deleteCancellationPolicy(cancellationPolicy, deletedBy);
    }

}
