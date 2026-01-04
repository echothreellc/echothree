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

package com.echothree.control.user.carrier.common;

import com.echothree.control.user.carrier.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CarrierService
        extends CarrierForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Carrier Types
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierType(UserVisitPK userVisitPK, CreateCarrierTypeForm form);
    
    CommandResult getCarrierTypes(UserVisitPK userVisitPK, GetCarrierTypesForm form);
    
    CommandResult getCarrierTypeChoices(UserVisitPK userVisitPK, GetCarrierTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierTypeDescription(UserVisitPK userVisitPK, CreateCarrierTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Carriers
    // -------------------------------------------------------------------------
    
    CommandResult createCarrier(UserVisitPK userVisitPK, CreateCarrierForm form);
    
    CommandResult getCarrier(UserVisitPK userVisitPK, GetCarrierForm form);
    
    CommandResult getCarriers(UserVisitPK userVisitPK, GetCarriersForm form);
    
    CommandResult getCarrierChoices(UserVisitPK userVisitPK, GetCarrierChoicesForm form);

    CommandResult setDefaultCarrier(UserVisitPK userVisitPK, SetDefaultCarrierForm form);
    
    CommandResult deleteCarrier(UserVisitPK userVisitPK, DeleteCarrierForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Services
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierService(UserVisitPK userVisitPK, CreateCarrierServiceForm form);
    
    CommandResult getCarrierService(UserVisitPK userVisitPK, GetCarrierServiceForm form);
    
    CommandResult getCarrierServices(UserVisitPK userVisitPK, GetCarrierServicesForm form);
    
    CommandResult getCarrierServiceChoices(UserVisitPK userVisitPK, GetCarrierServiceChoicesForm form);
    
    CommandResult setDefaultCarrierService(UserVisitPK userVisitPK, SetDefaultCarrierServiceForm form);
    
    CommandResult editCarrierService(UserVisitPK userVisitPK, EditCarrierServiceForm form);
    
    CommandResult deleteCarrierService(UserVisitPK userVisitPK, DeleteCarrierServiceForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Service Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierServiceDescription(UserVisitPK userVisitPK, CreateCarrierServiceDescriptionForm form);
    
    CommandResult getCarrierServiceDescription(UserVisitPK userVisitPK, GetCarrierServiceDescriptionForm form);

    CommandResult getCarrierServiceDescriptions(UserVisitPK userVisitPK, GetCarrierServiceDescriptionsForm form);

    CommandResult editCarrierServiceDescription(UserVisitPK userVisitPK, EditCarrierServiceDescriptionForm form);
    
    CommandResult deleteCarrierServiceDescription(UserVisitPK userVisitPK, DeleteCarrierServiceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Options
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierOption(UserVisitPK userVisitPK, CreateCarrierOptionForm form);
    
    CommandResult getCarrierOption(UserVisitPK userVisitPK, GetCarrierOptionForm form);
    
    CommandResult getCarrierOptions(UserVisitPK userVisitPK, GetCarrierOptionsForm form);
    
    CommandResult getCarrierOptionChoices(UserVisitPK userVisitPK, GetCarrierOptionChoicesForm form);
    
    CommandResult setDefaultCarrierOption(UserVisitPK userVisitPK, SetDefaultCarrierOptionForm form);
    
    CommandResult editCarrierOption(UserVisitPK userVisitPK, EditCarrierOptionForm form);
    
    CommandResult deleteCarrierOption(UserVisitPK userVisitPK, DeleteCarrierOptionForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Option Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierOptionDescription(UserVisitPK userVisitPK, CreateCarrierOptionDescriptionForm form);
    
    CommandResult getCarrierOptionDescription(UserVisitPK userVisitPK, GetCarrierOptionDescriptionForm form);

    CommandResult getCarrierOptionDescriptions(UserVisitPK userVisitPK, GetCarrierOptionDescriptionsForm form);

    CommandResult editCarrierOptionDescription(UserVisitPK userVisitPK, EditCarrierOptionDescriptionForm form);
    
    CommandResult deleteCarrierOptionDescription(UserVisitPK userVisitPK, DeleteCarrierOptionDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Carrier Service Options
    // -------------------------------------------------------------------------
    
    CommandResult createCarrierServiceOption(UserVisitPK userVisitPK, CreateCarrierServiceOptionForm form);
    
    CommandResult getCarrierServiceOption(UserVisitPK userVisitPK, GetCarrierServiceOptionForm form);
    
    CommandResult getCarrierServiceOptions(UserVisitPK userVisitPK, GetCarrierServiceOptionsForm form);
    
    CommandResult editCarrierServiceOption(UserVisitPK userVisitPK, EditCarrierServiceOptionForm form);
    
    CommandResult deleteCarrierServiceOption(UserVisitPK userVisitPK, DeleteCarrierServiceOptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Carriers
    // --------------------------------------------------------------------------------

    CommandResult createPartyCarrier(UserVisitPK userVisitPK, CreatePartyCarrierForm form);

    CommandResult getPartyCarrier(UserVisitPK userVisitPK, GetPartyCarrierForm form);

    CommandResult getPartyCarriers(UserVisitPK userVisitPK, GetPartyCarriersForm form);

    CommandResult deletePartyCarrier(UserVisitPK userVisitPK, DeletePartyCarrierForm form);

    // --------------------------------------------------------------------------------
    //   Party Carrier Accounts
    // --------------------------------------------------------------------------------

    CommandResult createPartyCarrierAccount(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form);

    CommandResult getPartyCarrierAccount(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form);

    CommandResult getPartyCarrierAccounts(UserVisitPK userVisitPK, GetPartyCarrierAccountsForm form);

    CommandResult editPartyCarrierAccount(UserVisitPK userVisitPK, EditPartyCarrierAccountForm form);

    CommandResult deletePartyCarrierAccount(UserVisitPK userVisitPK, DeletePartyCarrierAccountForm form);

}
