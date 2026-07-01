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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.GetCarrierServicesForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.factory.CarrierServiceFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCarrierServicesCommand
        extends BasePaginatedMultipleEntitiesCommand<CarrierService, GetCarrierServicesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierService.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CarrierControl carrierControl;

    /** Creates a new instance of GetCarrierServicesCommand */
    public GetCarrierServicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Carrier carrier;

    @Override
    protected void handleForm() {
        var carrierName = form.getCarrierName();

        carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier == null) {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return carrier == null ? null : carrierControl.countCarrierServicesByCarrierParty(carrier.getParty());
    }

    @Override
    protected Collection<CarrierService> getEntities() {
        return carrier == null ? null : carrierControl.getCarrierServices(carrier.getParty());
    }

    @Override
    protected BaseResult getResult(Collection<CarrierService> entities) {
        var result = CarrierResultFactory.getGetCarrierServicesResult();

        if(carrier != null) {
            var userVisit = getUserVisit();

            result.setCarrier(carrierControl.getCarrierTransfer(userVisit, carrier));

            if(session.hasLimit(CarrierServiceFactory.class)) {
                result.setCarrierServiceCount(getTotalEntities());
            }

            result.setCarrierServices(carrierControl.getCarrierServiceTransfers(userVisit, entities));
        }

        return result;
    }

}
