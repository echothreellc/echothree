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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferCustomerTypesForm;
import com.echothree.control.user.offer.common.result.GetOfferCustomerTypesResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetOfferCustomerTypesCommand
        extends BaseMultipleEntitiesCommand<OfferCustomerType, GetOfferCustomerTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferCustomerType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferCustomerTypesCommand */
    public GetOfferCustomerTypesCommand(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Offer offer;
    private CustomerType customerType;
    
    @Override
    protected Collection<OfferCustomerType> getEntities() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        String customerTypeName = form.getCustomerTypeName();
        int parameterCount = (offerName != null ? 1 : 0) + (customerTypeName != null ? 1 : 0);
        Collection<OfferCustomerType> offerCustomerTypes = null;

        if(parameterCount == 1) {
            if(offerName != null) {
                offer = offerControl.getOfferByName(offerName);

                if(offer != null) {
                    offerCustomerTypes = offerControl.getOfferCustomerTypesByOffer(offer);
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
                }
            } else if(customerTypeName != null) {
                var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);

                customerType = customerControl.getCustomerTypeByName(customerTypeName);

                if(customerType != null) {
                    offerCustomerTypes = offerControl.getOfferCustomerTypesByCustomerType(customerType);
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return offerCustomerTypes;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<OfferCustomerType> entities) {
        GetOfferCustomerTypesResult result = OfferResultFactory.getGetOfferCustomerTypesResult();
        
        if(entities != null) {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);

            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(getUserVisit(), offer));
            }

            if(customerType != null) {
                var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);

                result.setCustomerType(customerControl.getCustomerTypeTransfer(getUserVisit(), customerType));
            }
            
            result.setOfferCustomerTypes(offerControl.getOfferCustomerTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
