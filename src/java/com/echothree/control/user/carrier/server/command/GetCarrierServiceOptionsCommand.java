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

import com.echothree.control.user.carrier.common.form.GetCarrierServiceOptionsForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceOption;
import com.echothree.model.data.carrier.server.factory.CarrierServiceOptionFactory;
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
public class GetCarrierServiceOptionsCommand
        extends BasePaginatedMultipleEntitiesCommand<CarrierServiceOption, GetCarrierServiceOptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierServiceOption.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    CarrierControl carrierControl;

    /** Creates a new instance of GetCarrierServiceOptionsCommand */
    public GetCarrierServiceOptionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private CarrierService carrierService;
    private CarrierOption carrierOption;

    @Override
    protected void handleForm() {
        var carrierName = form.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);

        if(carrier != null) {
            var carrierParty = carrier.getParty();
            var carrierServiceName = form.getCarrierServiceName();
            var carrierOptionName = form.getCarrierOptionName();
            var parameterCount = (carrierServiceName == null ? 0 : 1) + (carrierOptionName == null ? 0 : 1);

            if(parameterCount == 1) {
                if(carrierServiceName != null) {
                    carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

                    if(carrierService == null) {
                        addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
                    }
                }

                if(carrierOptionName != null) {
                    carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);

                    if(carrierOption == null) {
                        addExecutionError(ExecutionErrors.UnknownCarrierOptionName.name(), carrierName, carrierOptionName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(carrierService != null) {
                total = carrierControl.countCarrierServiceOptionsByCarrierService(carrierService);
            } else if(carrierOption != null) {
                total = carrierControl.countCarrierServiceOptionsByCarrierOption(carrierOption);
            }
        }

        return total;
    }

    @Override
    protected Collection<CarrierServiceOption> getEntities() {
        Collection<CarrierServiceOption> entities = null;

        if(!hasExecutionErrors()) {
            if(carrierService != null) {
                entities = carrierControl.getCarrierServiceOptionsByCarrierService(carrierService);
            } else if(carrierOption != null) {
                entities = carrierControl.getCarrierServiceOptionsByCarrierOption(carrierOption);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<CarrierServiceOption> entities) {
        var result = CarrierResultFactory.getGetCarrierServiceOptionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(CarrierServiceOptionFactory.class)) {
                result.setCarrierServiceOptionCount(getTotalEntities());
            }

            if(carrierService != null) {
                result.setCarrierService(carrierControl.getCarrierServiceTransfer(userVisit, carrierService));
            } else if(carrierOption != null) {
                result.setCarrierOption(carrierControl.getCarrierOptionTransfer(userVisit, carrierOption));
            }

            result.setCarrierServiceOptions(carrierControl.getCarrierServiceOptionTransfers(userVisit, entities));
        }

        return result;
    }

}
