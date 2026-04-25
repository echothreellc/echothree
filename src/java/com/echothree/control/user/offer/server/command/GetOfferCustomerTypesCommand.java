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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferCustomerTypesForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.factory.OfferCustomerTypeFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetOfferCustomerTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<OfferCustomerType, GetOfferCustomerTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferCustomerType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetOfferCustomerTypesCommand */
    public GetOfferCustomerTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    CustomerControl customerControl;

    @Inject
    OfferControl offerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    OfferLogic offerLogic;

    private Offer offer;
    private CustomerType customerType;

    @Override
    protected void handleForm() {
        var offerName = form.getOfferName();
        var customerTypeName = form.getCustomerTypeName();
        var parameterCount = (offerName != null ? 1 : 0) + (customerTypeName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(offerName != null) {
                offer = offerLogic.getOfferByName(this, offerName);
            } else {
                customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(offer != null) {
                total = offerControl.countOfferCustomerTypesByOffer(offer);
            } else {
                total = offerControl.countOfferCustomerTypesByCustomerType(customerType);
            }
        }

        return total;
    }

    @Override
    protected Collection<OfferCustomerType> getEntities() {
        Collection<OfferCustomerType> entities = null;

        if(!hasExecutionErrors()) {
            if(offer != null) {
                entities = offerControl.getOfferCustomerTypesByOffer(offer);
            } else {
                entities = offerControl.getOfferCustomerTypesByCustomerType(customerType);
            }
        }

        return entities;
    }
    
    @Override
    protected BaseResult getResult(Collection<OfferCustomerType> entities) {
        var result = OfferResultFactory.getGetOfferCustomerTypesResult();
        
        if(entities != null) {
            var userVisit = getUserVisit();

            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            }

            if(customerType != null) {
                result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));
            }

            if(session.hasLimit(OfferCustomerTypeFactory.class)) {
                result.setOfferCustomerTypeCount(getTotalEntities());
            }

            result.setOfferCustomerTypes(offerControl.getOfferCustomerTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
