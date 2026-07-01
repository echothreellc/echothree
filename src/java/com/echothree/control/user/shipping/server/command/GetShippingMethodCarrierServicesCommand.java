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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.form.GetShippingMethodCarrierServicesForm;
import com.echothree.control.user.shipping.common.result.ShippingResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodCarrierService;
import com.echothree.model.data.shipping.server.factory.ShippingMethodCarrierServiceFactory;
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
public class GetShippingMethodCarrierServicesCommand
        extends BasePaginatedMultipleEntitiesCommand<ShippingMethodCarrierService, GetShippingMethodCarrierServicesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethodCarrierService.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    CarrierControl carrierControl;

    @Inject
    ShippingControl shippingControl;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of GetShippingMethodCarrierServicesCommand */
    public GetShippingMethodCarrierServicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private ShippingMethod shippingMethod;
    private CarrierService carrierService;

    @Override
    protected void handleForm() {
        var shippingMethodName = form.getShippingMethodName();
        var carrierName = form.getCarrierName();
        var carrierServiceName = form.getCarrierServiceName();
        var parameterCount = (shippingMethodName == null ? 0 : 1) + (carrierName == null && carrierServiceName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(shippingMethodName != null) {
                shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);
            } else {
                var carrier = carrierControl.getCarrierByName(carrierName);

                if(carrier != null) {
                    var carrierParty = carrier.getParty();
                    carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);

                    if(carrierService == null) {
                        addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierServiceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(shippingMethod != null) {
                total = shippingControl.countShippingMethodCarrierServicesByShippingMethod(shippingMethod);
            } else {
                total = shippingControl.countShippingMethodCarrierServicesByCarrierService(carrierService);
            }
        }

        return total;
    }

    @Override
    protected Collection<ShippingMethodCarrierService> getEntities() {
        Collection<ShippingMethodCarrierService> entities = null;

        if(!hasExecutionErrors()) {
            if(shippingMethod != null) {
                entities = shippingControl.getShippingMethodCarrierServicesByShippingMethod(shippingMethod);
            } else {
                entities = shippingControl.getShippingMethodCarrierServicesByCarrierService(carrierService);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ShippingMethodCarrierService> entities) {
        var result = ShippingResultFactory.getGetShippingMethodCarrierServicesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(shippingMethod != null) {
                result.setShippingMethod(shippingControl.getShippingMethodTransfer(userVisit, shippingMethod));
            } else {
                result.setCarrierService(carrierControl.getCarrierServiceTransfer(userVisit, carrierService));
            }

            if(session.hasLimit(ShippingMethodCarrierServiceFactory.class)) {
                result.setShippingMethodCarrierServiceCount(getTotalEntities());
            }

            result.setShippingMethodCarrierServices(shippingControl.getShippingMethodCarrierServiceTransfers(userVisit, (List<ShippingMethodCarrierService>)entities));
        }

        return result;
    }

}
