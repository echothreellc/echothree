// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
        return new CreateCarrierTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierTypes(UserVisitPK userVisitPK, GetCarrierTypesForm form) {
        return new GetCarrierTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierTypeChoices(UserVisitPK userVisitPK, GetCarrierTypeChoicesForm form) {
        return new GetCarrierTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierTypeDescription(UserVisitPK userVisitPK, CreateCarrierTypeDescriptionForm form) {
        return new CreateCarrierTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carriers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrier(UserVisitPK userVisitPK, CreateCarrierForm form) {
        return new CreateCarrierCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrier(UserVisitPK userVisitPK, GetCarrierForm form) {
        return new GetCarrierCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarriers(UserVisitPK userVisitPK, GetCarriersForm form) {
        return new GetCarriersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierChoices(UserVisitPK userVisitPK, GetCarrierChoicesForm form) {
        return new GetCarrierChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultCarrier(UserVisitPK userVisitPK, SetDefaultCarrierForm form) {
        return new SetDefaultCarrierCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrier(UserVisitPK userVisitPK, DeleteCarrierForm form) {
        return new DeleteCarrierCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierService(UserVisitPK userVisitPK, CreateCarrierServiceForm form) {
        return new CreateCarrierServiceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierService(UserVisitPK userVisitPK, GetCarrierServiceForm form) {
        return new GetCarrierServiceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierServices(UserVisitPK userVisitPK, GetCarrierServicesForm form) {
        return new GetCarrierServicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierServiceChoices(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form) {
        return new GetCarrierServiceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCarrierService(UserVisitPK userVisitPK, SetDefaultCarrierServiceForm form) {
        return new SetDefaultCarrierServiceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCarrierService(UserVisitPK userVisitPK, EditCarrierServiceForm form) {
        return new EditCarrierServiceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrierService(UserVisitPK userVisitPK, DeleteCarrierServiceForm form) {
        return new DeleteCarrierServiceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceDescription(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form) {
        return new CreateCarrierServiceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierServiceDescription(UserVisitPK userVisitPK, GetCarrierServiceDescriptionForm form) {
        return new GetCarrierServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCarrierServiceDescriptions(UserVisitPK userVisitPK, GetCarrierServiceDescriptionsForm form) {
        return new GetCarrierServiceDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCarrierServiceDescription(UserVisitPK userVisitPK, EditCarrierServiceDescriptionForm form) {
        return new EditCarrierServiceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrierServiceDescription(UserVisitPK userVisitPK, DeleteCarrierServiceDescriptionForm form) {
        return new DeleteCarrierServiceDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOption(UserVisitPK userVisitPK, CreateCarrierOptionForm form) {
        return new CreateCarrierOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierOption(UserVisitPK userVisitPK, GetCarrierOptionForm form) {
        return new GetCarrierOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierOptions(UserVisitPK userVisitPK, GetCarrierOptionsForm form) {
        return new GetCarrierOptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierOptionChoices(UserVisitPK userVisitPK, GetCarrierOptionChoicesForm form) {
        return new GetCarrierOptionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCarrierOption(UserVisitPK userVisitPK, SetDefaultCarrierOptionForm form) {
        return new SetDefaultCarrierOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCarrierOption(UserVisitPK userVisitPK, EditCarrierOptionForm form) {
        return new EditCarrierOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrierOption(UserVisitPK userVisitPK, DeleteCarrierOptionForm form) {
        return new DeleteCarrierOptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierOptionDescription(UserVisitPK userVisitPK, CreateCarrierOptionDescriptionForm form) {
        return new CreateCarrierOptionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierOptionDescription(UserVisitPK userVisitPK, GetCarrierOptionDescriptionForm form) {
        return new GetCarrierOptionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCarrierOptionDescriptions(UserVisitPK userVisitPK, GetCarrierOptionDescriptionsForm form) {
        return new GetCarrierOptionDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCarrierOptionDescription(UserVisitPK userVisitPK, EditCarrierOptionDescriptionForm form) {
        return new EditCarrierOptionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrierOptionDescription(UserVisitPK userVisitPK, DeleteCarrierOptionDescriptionForm form) {
        return new DeleteCarrierOptionDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCarrierServiceOption(UserVisitPK userVisitPK, CreateCarrierServiceOptionForm form) {
        return new CreateCarrierServiceOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierServiceOption(UserVisitPK userVisitPK, GetCarrierServiceOptionForm form) {
        return new GetCarrierServiceOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCarrierServiceOptions(UserVisitPK userVisitPK, GetCarrierServiceOptionsForm form) {
        return new GetCarrierServiceOptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCarrierServiceOption(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form) {
        return new EditCarrierServiceOptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCarrierServiceOption(UserVisitPK userVisitPK, DeleteCarrierServiceOptionForm form) {
        return new DeleteCarrierServiceOptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrier(UserVisitPK userVisitPK, CreatePartyCarrierForm form) {
        return new CreatePartyCarrierCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCarrier(UserVisitPK userVisitPK, GetPartyCarrierForm form) {
        return new GetPartyCarrierCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCarriers(UserVisitPK userVisitPK, GetPartyCarriersForm form) {
        return new GetPartyCarriersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyCarrier(UserVisitPK userVisitPK, DeletePartyCarrierForm form) {
        return new DeletePartyCarrierCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyCarrierAccount(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form) {
        return new CreatePartyCarrierAccountCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCarrierAccount(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form) {
        return new GetPartyCarrierAccountCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCarrierAccounts(UserVisitPK userVisitPK, GetPartyCarrierAccountsForm form) {
        return new GetPartyCarrierAccountsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyCarrierAccount(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form) {
        return new EditPartyCarrierAccountCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyCarrierAccount(UserVisitPK userVisitPK, DeletePartyCarrierAccountForm form) {
        return new DeletePartyCarrierAccountCommand(userVisitPK, form).run();
    }

}
