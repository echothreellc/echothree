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

package com.echothree.control.user.carrier.server;

import com.echothree.control.user.carrier.common.CarrierRemote;
import com.echothree.control.user.carrier.common.form.*;
import com.echothree.control.user.carrier.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class CarrierBean
        extends CarrierFormsImpl
        implements CarrierRemote, CarrierLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CarrierBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierType(UserVisitPK userVisitPK, CreateCarrierTypeForm form) {
        return CDI.current().select(CreateCarrierTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierTypes(UserVisitPK userVisitPK, GetCarrierTypesForm form) {
        return CDI.current().select(GetCarrierTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierTypeChoices(UserVisitPK userVisitPK, GetCarrierTypeChoicesForm form) {
        return CDI.current().select(GetCarrierTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierTypeDescription(UserVisitPK userVisitPK, CreateCarrierTypeDescriptionForm form) {
        return CDI.current().select(CreateCarrierTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carriers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrier(UserVisitPK userVisitPK, CreateCarrierForm form) {
        return CDI.current().select(CreateCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrier(UserVisitPK userVisitPK, GetCarrierForm form) {
        return CDI.current().select(GetCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarriers(UserVisitPK userVisitPK, GetCarriersForm form) {
        return CDI.current().select(GetCarriersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierChoices(UserVisitPK userVisitPK, GetCarrierChoicesForm form) {
        return CDI.current().select(GetCarrierChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultCarrier(UserVisitPK userVisitPK, SetDefaultCarrierForm form) {
        return CDI.current().select(SetDefaultCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrier(UserVisitPK userVisitPK, DeleteCarrierForm form) {
        return CDI.current().select(DeleteCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierService(UserVisitPK userVisitPK, CreateCarrierServiceForm form) {
        return CDI.current().select(CreateCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierService(UserVisitPK userVisitPK, GetCarrierServiceForm form) {
        return CDI.current().select(GetCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServices(UserVisitPK userVisitPK, GetCarrierServicesForm form) {
        return CDI.current().select(GetCarrierServicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceChoices(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form) {
        return CDI.current().select(GetCarrierServiceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCarrierService(UserVisitPK userVisitPK, SetDefaultCarrierServiceForm form) {
        return CDI.current().select(SetDefaultCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierService(UserVisitPK userVisitPK, EditCarrierServiceForm form) {
        return CDI.current().select(EditCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierService(UserVisitPK userVisitPK, DeleteCarrierServiceForm form) {
        return CDI.current().select(DeleteCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceDescription(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form) {
        return CDI.current().select(CreateCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceDescription(UserVisitPK userVisitPK, GetCarrierServiceDescriptionForm form) {
        return CDI.current().select(GetCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCarrierServiceDescriptions(UserVisitPK userVisitPK, GetCarrierServiceDescriptionsForm form) {
        return CDI.current().select(GetCarrierServiceDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCarrierServiceDescription(UserVisitPK userVisitPK, EditCarrierServiceDescriptionForm form) {
        return CDI.current().select(EditCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierServiceDescription(UserVisitPK userVisitPK, DeleteCarrierServiceDescriptionForm form) {
        return CDI.current().select(DeleteCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOption(UserVisitPK userVisitPK, CreateCarrierOptionForm form) {
        return CDI.current().select(CreateCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOption(UserVisitPK userVisitPK, GetCarrierOptionForm form) {
        return CDI.current().select(GetCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptions(UserVisitPK userVisitPK, GetCarrierOptionsForm form) {
        return CDI.current().select(GetCarrierOptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptionChoices(UserVisitPK userVisitPK, GetCarrierOptionChoicesForm form) {
        return CDI.current().select(GetCarrierOptionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCarrierOption(UserVisitPK userVisitPK, SetDefaultCarrierOptionForm form) {
        return CDI.current().select(SetDefaultCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierOption(UserVisitPK userVisitPK, EditCarrierOptionForm form) {
        return CDI.current().select(EditCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierOption(UserVisitPK userVisitPK, DeleteCarrierOptionForm form) {
        return CDI.current().select(DeleteCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOptionDescription(UserVisitPK userVisitPK, CreateCarrierOptionDescriptionForm form) {
        return CDI.current().select(CreateCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptionDescription(UserVisitPK userVisitPK, GetCarrierOptionDescriptionForm form) {
        return CDI.current().select(GetCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCarrierOptionDescriptions(UserVisitPK userVisitPK, GetCarrierOptionDescriptionsForm form) {
        return CDI.current().select(GetCarrierOptionDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCarrierOptionDescription(UserVisitPK userVisitPK, EditCarrierOptionDescriptionForm form) {
        return CDI.current().select(EditCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierOptionDescription(UserVisitPK userVisitPK, DeleteCarrierOptionDescriptionForm form) {
        return CDI.current().select(DeleteCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceOption(UserVisitPK userVisitPK, CreateCarrierServiceOptionForm form) {
        return CDI.current().select(CreateCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceOption(UserVisitPK userVisitPK, GetCarrierServiceOptionForm form) {
        return CDI.current().select(GetCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceOptions(UserVisitPK userVisitPK, GetCarrierServiceOptionsForm form) {
        return CDI.current().select(GetCarrierServiceOptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierServiceOption(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form) {
        return CDI.current().select(EditCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierServiceOption(UserVisitPK userVisitPK, DeleteCarrierServiceOptionForm form) {
        return CDI.current().select(DeleteCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrier(UserVisitPK userVisitPK, CreatePartyCarrierForm form) {
        return CDI.current().select(CreatePartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrier(UserVisitPK userVisitPK, GetPartyCarrierForm form) {
        return CDI.current().select(GetPartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarriers(UserVisitPK userVisitPK, GetPartyCarriersForm form) {
        return CDI.current().select(GetPartyCarriersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyCarrier(UserVisitPK userVisitPK, DeletePartyCarrierForm form) {
        return CDI.current().select(DeletePartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrierAccount(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form) {
        return CDI.current().select(CreatePartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrierAccount(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form) {
        return CDI.current().select(GetPartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrierAccounts(UserVisitPK userVisitPK, GetPartyCarrierAccountsForm form) {
        return CDI.current().select(GetPartyCarrierAccountsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyCarrierAccount(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form) {
        return CDI.current().select(EditPartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyCarrierAccount(UserVisitPK userVisitPK, DeletePartyCarrierAccountForm form) {
        return CDI.current().select(DeletePartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

}
