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
import com.echothree.control.user.carrier.common.result.*;
import com.echothree.control.user.carrier.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
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
    public CommandResult<VoidResult> createCarrierType(UserVisitPK userVisitPK, CreateCarrierTypeForm form) {
        return CDI.current().select(CreateCarrierTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierTypesResult> getCarrierTypes(UserVisitPK userVisitPK, GetCarrierTypesForm form) {
        return CDI.current().select(GetCarrierTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierTypeChoicesResult> getCarrierTypeChoices(UserVisitPK userVisitPK, GetCarrierTypeChoicesForm form) {
        return CDI.current().select(GetCarrierTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierTypeDescription(UserVisitPK userVisitPK, CreateCarrierTypeDescriptionForm form) {
        return CDI.current().select(CreateCarrierTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carriers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateCarrierResult> createCarrier(UserVisitPK userVisitPK, CreateCarrierForm form) {
        return CDI.current().select(CreateCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierResult> getCarrier(UserVisitPK userVisitPK, GetCarrierForm form) {
        return CDI.current().select(GetCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarriersResult> getCarriers(UserVisitPK userVisitPK, GetCarriersForm form) {
        return CDI.current().select(GetCarriersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierChoicesResult> getCarrierChoices(UserVisitPK userVisitPK, GetCarrierChoicesForm form) {
        return CDI.current().select(GetCarrierChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultCarrier(UserVisitPK userVisitPK, SetDefaultCarrierForm form) {
        return CDI.current().select(SetDefaultCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrier(UserVisitPK userVisitPK, DeleteCarrierForm form) {
        return CDI.current().select(DeleteCarrierCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Services
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierService(UserVisitPK userVisitPK, CreateCarrierServiceForm form) {
        return CDI.current().select(CreateCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServiceResult> getCarrierService(UserVisitPK userVisitPK, GetCarrierServiceForm form) {
        return CDI.current().select(GetCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServicesResult> getCarrierServices(UserVisitPK userVisitPK, GetCarrierServicesForm form) {
        return CDI.current().select(GetCarrierServicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServiceChoicesResult> getCarrierServiceChoices(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form) {
        return CDI.current().select(GetCarrierServiceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultCarrierService(UserVisitPK userVisitPK, SetDefaultCarrierServiceForm form) {
        return CDI.current().select(SetDefaultCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditCarrierServiceResult> editCarrierService(UserVisitPK userVisitPK, EditCarrierServiceForm form) {
        return CDI.current().select(EditCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrierService(UserVisitPK userVisitPK, DeleteCarrierServiceForm form) {
        return CDI.current().select(DeleteCarrierServiceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierServiceDescription(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form) {
        return CDI.current().select(CreateCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServiceDescriptionResult> getCarrierServiceDescription(UserVisitPK userVisitPK, GetCarrierServiceDescriptionForm form) {
        return CDI.current().select(GetCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetCarrierServiceDescriptionsResult> getCarrierServiceDescriptions(UserVisitPK userVisitPK, GetCarrierServiceDescriptionsForm form) {
        return CDI.current().select(GetCarrierServiceDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditCarrierServiceDescriptionResult> editCarrierServiceDescription(UserVisitPK userVisitPK, EditCarrierServiceDescriptionForm form) {
        return CDI.current().select(EditCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrierServiceDescription(UserVisitPK userVisitPK, DeleteCarrierServiceDescriptionForm form) {
        return CDI.current().select(DeleteCarrierServiceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierOption(UserVisitPK userVisitPK, CreateCarrierOptionForm form) {
        return CDI.current().select(CreateCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierOptionResult> getCarrierOption(UserVisitPK userVisitPK, GetCarrierOptionForm form) {
        return CDI.current().select(GetCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierOptionsResult> getCarrierOptions(UserVisitPK userVisitPK, GetCarrierOptionsForm form) {
        return CDI.current().select(GetCarrierOptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierOptionChoicesResult> getCarrierOptionChoices(UserVisitPK userVisitPK, GetCarrierOptionChoicesForm form) {
        return CDI.current().select(GetCarrierOptionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultCarrierOption(UserVisitPK userVisitPK, SetDefaultCarrierOptionForm form) {
        return CDI.current().select(SetDefaultCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditCarrierOptionResult> editCarrierOption(UserVisitPK userVisitPK, EditCarrierOptionForm form) {
        return CDI.current().select(EditCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrierOption(UserVisitPK userVisitPK, DeleteCarrierOptionForm form) {
        return CDI.current().select(DeleteCarrierOptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierOptionDescription(UserVisitPK userVisitPK, CreateCarrierOptionDescriptionForm form) {
        return CDI.current().select(CreateCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierOptionDescriptionResult> getCarrierOptionDescription(UserVisitPK userVisitPK, GetCarrierOptionDescriptionForm form) {
        return CDI.current().select(GetCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetCarrierOptionDescriptionsResult> getCarrierOptionDescriptions(UserVisitPK userVisitPK, GetCarrierOptionDescriptionsForm form) {
        return CDI.current().select(GetCarrierOptionDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditCarrierOptionDescriptionResult> editCarrierOptionDescription(UserVisitPK userVisitPK, EditCarrierOptionDescriptionForm form) {
        return CDI.current().select(EditCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrierOptionDescription(UserVisitPK userVisitPK, DeleteCarrierOptionDescriptionForm form) {
        return CDI.current().select(DeleteCarrierOptionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Carrier Service Options
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createCarrierServiceOption(UserVisitPK userVisitPK, CreateCarrierServiceOptionForm form) {
        return CDI.current().select(CreateCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServiceOptionResult> getCarrierServiceOption(UserVisitPK userVisitPK, GetCarrierServiceOptionForm form) {
        return CDI.current().select(GetCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetCarrierServiceOptionsResult> getCarrierServiceOptions(UserVisitPK userVisitPK, GetCarrierServiceOptionsForm form) {
        return CDI.current().select(GetCarrierServiceOptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditCarrierServiceOptionResult> editCarrierServiceOption(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form) {
        return CDI.current().select(EditCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteCarrierServiceOption(UserVisitPK userVisitPK, DeleteCarrierServiceOptionForm form) {
        return CDI.current().select(DeleteCarrierServiceOptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyCarrier(UserVisitPK userVisitPK, CreatePartyCarrierForm form) {
        return CDI.current().select(CreatePartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyCarrierResult> getPartyCarrier(UserVisitPK userVisitPK, GetPartyCarrierForm form) {
        return CDI.current().select(GetPartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyCarriersResult> getPartyCarriers(UserVisitPK userVisitPK, GetPartyCarriersForm form) {
        return CDI.current().select(GetPartyCarriersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyCarrier(UserVisitPK userVisitPK, DeletePartyCarrierForm form) {
        return CDI.current().select(DeletePartyCarrierCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyCarrierAccount(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form) {
        return CDI.current().select(CreatePartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyCarrierAccountResult> getPartyCarrierAccount(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form) {
        return CDI.current().select(GetPartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyCarrierAccountsResult> getPartyCarrierAccounts(UserVisitPK userVisitPK, GetPartyCarrierAccountsForm form) {
        return CDI.current().select(GetPartyCarrierAccountsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyCarrierAccountResult> editPartyCarrierAccount(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form) {
        return CDI.current().select(EditPartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyCarrierAccount(UserVisitPK userVisitPK, DeletePartyCarrierAccountForm form) {
        return CDI.current().select(DeletePartyCarrierAccountCommand.class).get().run(userVisitPK, form);
    }

}
