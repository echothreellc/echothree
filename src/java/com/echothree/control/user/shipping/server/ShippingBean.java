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

package com.echothree.control.user.shipping.server;

import com.echothree.control.user.shipping.common.ShippingRemote;
import com.echothree.control.user.shipping.common.form.*;
import com.echothree.control.user.shipping.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ShippingBean
        extends ShippingFormsImpl
        implements ShippingRemote, ShippingLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ShippingBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShippingMethod(UserVisitPK userVisitPK, CreateShippingMethodForm form) {
        return new CreateShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethod(UserVisitPK userVisitPK, GetShippingMethodForm form) {
        return new GetShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethods(UserVisitPK userVisitPK, GetShippingMethodsForm form) {
        return new GetShippingMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodChoices(UserVisitPK userVisitPK, GetShippingMethodChoicesForm form) {
        return new GetShippingMethodChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editShippingMethod(UserVisitPK userVisitPK, EditShippingMethodForm form) {
        return new EditShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteShippingMethod(UserVisitPK userVisitPK, DeleteShippingMethodForm form) {
        return new DeleteShippingMethodCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Shipping Method Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShippingMethodDescription(UserVisitPK userVisitPK, CreateShippingMethodDescriptionForm form) {
        return new CreateShippingMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodDescription(UserVisitPK userVisitPK, GetShippingMethodDescriptionForm form) {
        return new GetShippingMethodDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodDescriptions(UserVisitPK userVisitPK, GetShippingMethodDescriptionsForm form) {
        return new GetShippingMethodDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShippingMethodDescription(UserVisitPK userVisitPK, EditShippingMethodDescriptionForm form) {
        return new EditShippingMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteShippingMethodDescription(UserVisitPK userVisitPK, DeleteShippingMethodDescriptionForm form) {
        return new DeleteShippingMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Shipping Method Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShippingMethodCarrierService(UserVisitPK userVisitPK, CreateShippingMethodCarrierServiceForm form) {
        return new CreateShippingMethodCarrierServiceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodCarrierService(UserVisitPK userVisitPK, GetShippingMethodCarrierServiceForm form) {
        return new GetShippingMethodCarrierServiceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodCarrierServices(UserVisitPK userVisitPK, GetShippingMethodCarrierServicesForm form) {
        return new GetShippingMethodCarrierServicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShippingMethodCarrierService(UserVisitPK userVisitPK, DeleteShippingMethodCarrierServiceForm form) {
        return new DeleteShippingMethodCarrierServiceCommand().run(userVisitPK, form);
    }
    
}
