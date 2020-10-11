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

package com.echothree.model.control.returnpolicy.server.logic;

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnKindNameException;
import com.echothree.model.control.returnpolicy.common.exception.UnknownReturnPolicyNameException;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ReturnPolicyLogic
        extends BaseLogic {
    
    private ReturnPolicyLogic() {
        super();
    }
    
    private static class ReturnPolicyLogicHolder {
        static ReturnPolicyLogic instance = new ReturnPolicyLogic();
    }
    
    public static ReturnPolicyLogic getInstance() {
        return ReturnPolicyLogicHolder.instance;
    }

    public ReturnKind getReturnKindByName(final ExecutionErrorAccumulator eea, final String returnKindName) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

        if(returnKind == null) {
            handleExecutionError(UnknownReturnKindNameException.class, eea, ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }

        return returnKind;
    }

    public ReturnPolicy getReturnPolicyByName(final ExecutionErrorAccumulator eea, final String returnKindName, final String returnPolicyName) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        ReturnKind returnKind = getReturnKindByName(eea, returnKindName);
        ReturnPolicy returnPolicy = null;
        
        if(returnKind != null) {
            returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
            
            if(returnPolicy == null) {
                handleExecutionError(UnknownReturnPolicyNameException.class, eea, ExecutionErrors.UnknownReturnPolicyName.name(),
                        returnKind.getLastDetail().getReturnKindName(), returnPolicyName);
            }
        }
        
        return returnPolicy;
    }
    
    public ReturnPolicy getDefaultReturnPolicyByKind(final ExecutionErrorAccumulator eea, final String returnKindName,
            final ReturnPolicy returnPolicies[]) {
        ReturnPolicy returnPolicy = null;

        for(int i = 0 ; returnPolicy == null && i < returnPolicies.length ; i++) {
            returnPolicy = returnPolicies[i];
        }

        if(returnPolicy == null) {
            var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
            ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);

            if(returnKind != null) {
                returnPolicy = returnPolicyControl.getDefaultReturnPolicy(returnKind);

                if(returnPolicy == null) {
                    eea.addExecutionError(ExecutionErrors.UnknownDefaultReturnPolicy.name(), returnKindName);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
            }
        }

        return returnPolicy;
    }

    public void checkDeleteReturnPolicy(final ExecutionErrorAccumulator eea, final ReturnPolicy returnPolicy) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        // Both CUSTOMERs and VENDORs use Orders and OrderLines, so check for ReturnPolicy use there first.
        boolean inUse = orderControl.countOrdersByReturnPolicy(returnPolicy) != 0;

        if(!inUse) {
            var orderLineControl = (OrderLineControl)Session.getModelController(OrderLineControl.class);

            inUse |= orderLineControl.countOrderLinesByReturnPolicy(returnPolicy) != 0;
        }

        if(!inUse) {
            String returnKindName = returnPolicy.getLastDetail().getReturnKind().getLastDetail().getReturnKindName();

            if(returnKindName.equals(ReturnKinds.CUSTOMER_RETURN.name())) {
                var itemControl = (ItemControl)Session.getModelController(ItemControl.class);

                inUse |= itemControl.countItemsByReturnPolicy(returnPolicy) != 0;
            } else if(returnKindName.equals(ReturnKinds.VENDOR_RETURN.name())) {
                var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);

                inUse |= vendorControl.countVendorItemsByReturnPolicy(returnPolicy) != 0;
            }
        }

        if(inUse) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteReturnPolicyInUse.name(), returnPolicy.getLastDetail().getReturnPolicyName());
        }
    }

    public void deleteReturnPolicy(final ReturnPolicy returnPolicy, final BasePK deletedBy) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);

        returnPolicyControl.deleteReturnPolicy(returnPolicy, deletedBy);
    }

}
