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

package com.echothree.control.user.shipping.server;

import com.echothree.control.user.shipping.common.ShippingRemote;
import com.echothree.control.user.shipping.common.form.*;
import com.echothree.control.user.shipping.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethod(UserVisitPK userVisitPK, GetShippingMethodForm form) {
        return CDI.current().select(GetShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethods(UserVisitPK userVisitPK, GetShippingMethodsForm form) {
        return CDI.current().select(GetShippingMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodChoices(UserVisitPK userVisitPK, GetShippingMethodChoicesForm form) {
        return CDI.current().select(GetShippingMethodChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editShippingMethod(UserVisitPK userVisitPK, EditShippingMethodForm form) {
        return CDI.current().select(EditShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteShippingMethod(UserVisitPK userVisitPK, DeleteShippingMethodForm form) {
        return CDI.current().select(DeleteShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Shipping Method Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShippingMethodDescription(UserVisitPK userVisitPK, CreateShippingMethodDescriptionForm form) {
        return CDI.current().select(CreateShippingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodDescription(UserVisitPK userVisitPK, GetShippingMethodDescriptionForm form) {
        return CDI.current().select(GetShippingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodDescriptions(UserVisitPK userVisitPK, GetShippingMethodDescriptionsForm form) {
        return CDI.current().select(GetShippingMethodDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editShippingMethodDescription(UserVisitPK userVisitPK, EditShippingMethodDescriptionForm form) {
        return CDI.current().select(EditShippingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteShippingMethodDescription(UserVisitPK userVisitPK, DeleteShippingMethodDescriptionForm form) {
        return CDI.current().select(DeleteShippingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Shipping Method Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createShippingMethodCarrierService(UserVisitPK userVisitPK, CreateShippingMethodCarrierServiceForm form) {
        return CDI.current().select(CreateShippingMethodCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getShippingMethodCarrierService(UserVisitPK userVisitPK, GetShippingMethodCarrierServiceForm form) {
        return CDI.current().select(GetShippingMethodCarrierServiceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getShippingMethodCarrierServices(UserVisitPK userVisitPK, GetShippingMethodCarrierServicesForm form) {
        return CDI.current().select(GetShippingMethodCarrierServicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteShippingMethodCarrierService(UserVisitPK userVisitPK, DeleteShippingMethodCarrierServiceForm form) {
        return CDI.current().select(DeleteShippingMethodCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
}
