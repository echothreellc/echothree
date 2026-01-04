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

package com.echothree.control.user.shipping.common;

import com.echothree.control.user.shipping.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ShippingService
        extends ShippingForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Shipping Methods
    // -------------------------------------------------------------------------
    
    CommandResult createShippingMethod(UserVisitPK userVisitPK, CreateShippingMethodForm form);
    
    CommandResult getShippingMethod(UserVisitPK userVisitPK, GetShippingMethodForm form);
    
    CommandResult getShippingMethods(UserVisitPK userVisitPK, GetShippingMethodsForm form);
    
    CommandResult getShippingMethodChoices(UserVisitPK userVisitPK, GetShippingMethodChoicesForm form);
    
    CommandResult editShippingMethod(UserVisitPK userVisitPK, EditShippingMethodForm form);
    
    CommandResult deleteShippingMethod(UserVisitPK userVisitPK, DeleteShippingMethodForm form);
    
    // -------------------------------------------------------------------------
    //   Shipping Method Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createShippingMethodDescription(UserVisitPK userVisitPK, CreateShippingMethodDescriptionForm form);
    
    CommandResult getShippingMethodDescription(UserVisitPK userVisitPK, GetShippingMethodDescriptionForm form);

    CommandResult getShippingMethodDescriptions(UserVisitPK userVisitPK, GetShippingMethodDescriptionsForm form);

    CommandResult editShippingMethodDescription(UserVisitPK userVisitPK, EditShippingMethodDescriptionForm form);
    
    CommandResult deleteShippingMethodDescription(UserVisitPK userVisitPK, DeleteShippingMethodDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Shipping Method Carrier Services
    // -------------------------------------------------------------------------
    
    CommandResult createShippingMethodCarrierService(UserVisitPK userVisitPK, CreateShippingMethodCarrierServiceForm form);
    
    CommandResult getShippingMethodCarrierService(UserVisitPK userVisitPK, GetShippingMethodCarrierServiceForm form);

    CommandResult getShippingMethodCarrierServices(UserVisitPK userVisitPK, GetShippingMethodCarrierServicesForm form);

    CommandResult deleteShippingMethodCarrierService(UserVisitPK userVisitPK, DeleteShippingMethodCarrierServiceForm form);
    
}
