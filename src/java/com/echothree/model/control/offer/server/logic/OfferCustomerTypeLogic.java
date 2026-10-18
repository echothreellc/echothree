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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.OfferCustomerTypeSpec;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.offer.common.exception.UnknownOfferCustomerTypeException;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OfferCustomerTypeLogic
        extends BaseLogic {

    @Inject
    OfferControl offerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    OfferLogic offerLogic;

    protected OfferCustomerTypeLogic() {
        super();
    }

    public OfferCustomerType getOfferCustomerTypeBySpec(final ExecutionErrorAccumulator eea,
            final OfferCustomerTypeSpec spec, final EntityPermission entityPermission) {
        OfferCustomerType offerCustomerType = null;
        var offerName = spec.getOfferName();
        var offer = offerLogic.getOfferByName(eea, offerName);

        if(!hasExecutionErrors(eea)) {
            var customerTypeName = spec.getCustomerTypeName();
            var customerType = customerTypeLogic.getCustomerTypeByName(eea, customerTypeName);

            if(!hasExecutionErrors(eea)) {
                offerCustomerType = offerControl.getOfferCustomerType(offer, customerType, entityPermission);

                if(offerCustomerType == null) {
                    handleExecutionError(UnknownOfferCustomerTypeException.class, eea, ExecutionErrors.UnknownOfferCustomerType.name(), offerName, customerTypeName);
                }
            }
        }

        return offerCustomerType;
    }

}
