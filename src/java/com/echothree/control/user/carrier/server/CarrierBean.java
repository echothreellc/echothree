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

package com.echothree.control.user.carrier.server;

import com.echothree.control.user.carrier.common.CarrierRemote;
import com.echothree.control.user.carrier.common.form.*;
import com.echothree.control.user.carrier.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
        return new CreateCarrierTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierTypes(UserVisitPK userVisitPK, GetCarrierTypesForm form) {
        return new GetCarrierTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierTypeChoices(UserVisitPK userVisitPK, GetCarrierTypeChoicesForm form) {
        return new GetCarrierTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierTypeDescription(UserVisitPK userVisitPK, CreateCarrierTypeDescriptionForm form) {
        return new CreateCarrierTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carriers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrier(UserVisitPK userVisitPK, CreateCarrierForm form) {
        return new CreateCarrierCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrier(UserVisitPK userVisitPK, GetCarrierForm form) {
        return new GetCarrierCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarriers(UserVisitPK userVisitPK, GetCarriersForm form) {
        return new GetCarriersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierChoices(UserVisitPK userVisitPK, GetCarrierChoicesForm form) {
        return new GetCarrierChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultCarrier(UserVisitPK userVisitPK, SetDefaultCarrierForm form) {
        return new SetDefaultCarrierCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrier(UserVisitPK userVisitPK, DeleteCarrierForm form) {
        return new DeleteCarrierCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierService(UserVisitPK userVisitPK, CreateCarrierServiceForm form) {
        return new CreateCarrierServiceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierService(UserVisitPK userVisitPK, GetCarrierServiceForm form) {
        return new GetCarrierServiceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServices(UserVisitPK userVisitPK, GetCarrierServicesForm form) {
        return new GetCarrierServicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceChoices(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form) {
        return new GetCarrierServiceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCarrierService(UserVisitPK userVisitPK, SetDefaultCarrierServiceForm form) {
        return new SetDefaultCarrierServiceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierService(UserVisitPK userVisitPK, EditCarrierServiceForm form) {
        return new EditCarrierServiceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierService(UserVisitPK userVisitPK, DeleteCarrierServiceForm form) {
        return new DeleteCarrierServiceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceDescription(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form) {
        return new CreateCarrierServiceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceDescription(UserVisitPK userVisitPK, GetCarrierServiceDescriptionForm form) {
        return new GetCarrierServiceDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCarrierServiceDescriptions(UserVisitPK userVisitPK, GetCarrierServiceDescriptionsForm form) {
        return new GetCarrierServiceDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCarrierServiceDescription(UserVisitPK userVisitPK, EditCarrierServiceDescriptionForm form) {
        return new EditCarrierServiceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierServiceDescription(UserVisitPK userVisitPK, DeleteCarrierServiceDescriptionForm form) {
        return new DeleteCarrierServiceDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOption(UserVisitPK userVisitPK, CreateCarrierOptionForm form) {
        return new CreateCarrierOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOption(UserVisitPK userVisitPK, GetCarrierOptionForm form) {
        return new GetCarrierOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptions(UserVisitPK userVisitPK, GetCarrierOptionsForm form) {
        return new GetCarrierOptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptionChoices(UserVisitPK userVisitPK, GetCarrierOptionChoicesForm form) {
        return new GetCarrierOptionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCarrierOption(UserVisitPK userVisitPK, SetDefaultCarrierOptionForm form) {
        return new SetDefaultCarrierOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierOption(UserVisitPK userVisitPK, EditCarrierOptionForm form) {
        return new EditCarrierOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierOption(UserVisitPK userVisitPK, DeleteCarrierOptionForm form) {
        return new DeleteCarrierOptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOptionDescription(UserVisitPK userVisitPK, CreateCarrierOptionDescriptionForm form) {
        return new CreateCarrierOptionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierOptionDescription(UserVisitPK userVisitPK, GetCarrierOptionDescriptionForm form) {
        return new GetCarrierOptionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCarrierOptionDescriptions(UserVisitPK userVisitPK, GetCarrierOptionDescriptionsForm form) {
        return new GetCarrierOptionDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCarrierOptionDescription(UserVisitPK userVisitPK, EditCarrierOptionDescriptionForm form) {
        return new EditCarrierOptionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierOptionDescription(UserVisitPK userVisitPK, DeleteCarrierOptionDescriptionForm form) {
        return new DeleteCarrierOptionDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceOption(UserVisitPK userVisitPK, CreateCarrierServiceOptionForm form) {
        return new CreateCarrierServiceOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceOption(UserVisitPK userVisitPK, GetCarrierServiceOptionForm form) {
        return new GetCarrierServiceOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCarrierServiceOptions(UserVisitPK userVisitPK, GetCarrierServiceOptionsForm form) {
        return new GetCarrierServiceOptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCarrierServiceOption(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form) {
        return new EditCarrierServiceOptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCarrierServiceOption(UserVisitPK userVisitPK, DeleteCarrierServiceOptionForm form) {
        return new DeleteCarrierServiceOptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrier(UserVisitPK userVisitPK, CreatePartyCarrierForm form) {
        return new CreatePartyCarrierCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrier(UserVisitPK userVisitPK, GetPartyCarrierForm form) {
        return new GetPartyCarrierCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarriers(UserVisitPK userVisitPK, GetPartyCarriersForm form) {
        return new GetPartyCarriersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyCarrier(UserVisitPK userVisitPK, DeletePartyCarrierForm form) {
        return new DeletePartyCarrierCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrierAccount(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form) {
        return new CreatePartyCarrierAccountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrierAccount(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form) {
        return new GetPartyCarrierAccountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCarrierAccounts(UserVisitPK userVisitPK, GetPartyCarrierAccountsForm form) {
        return new GetPartyCarrierAccountsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyCarrierAccount(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form) {
        return new EditPartyCarrierAccountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyCarrierAccount(UserVisitPK userVisitPK, DeletePartyCarrierAccountForm form) {
        return new DeletePartyCarrierAccountCommand().run(userVisitPK, form);
    }

}
