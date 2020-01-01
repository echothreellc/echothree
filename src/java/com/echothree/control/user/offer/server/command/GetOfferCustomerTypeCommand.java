// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.offer.common.form.GetOfferCustomerTypeForm;
import com.echothree.control.user.offer.common.result.GetOfferCustomerTypeResult;
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
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetOfferCustomerTypeCommand
        extends BaseSingleEntityCommand<OfferCustomerType, GetOfferCustomerTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferCustomerType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferCustomerTypeCommand */
    public GetOfferCustomerTypeCommand(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected OfferCustomerType getEntity() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Offer offer = offerControl.getOfferByName(offerName);
        OfferCustomerType offerCustomerType = null;

        if(offer != null) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            String customerTypeName = form.getCustomerTypeName();
            CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);

            if(customerType != null) {
                offerCustomerType = offerControl.getOfferCustomerType(offer, customerType);

                if(offerCustomerType == null) {
                    addExecutionError(ExecutionErrors.UnknownOfferCustomerType.name(), offer.getLastDetail().getOfferName(),
                            customerType.getLastDetail().getCustomerTypeName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }

        return offerCustomerType;
    }
    
    @Override
    protected BaseResult getTransfer(OfferCustomerType offerCustomerType) {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        GetOfferCustomerTypeResult result = OfferResultFactory.getGetOfferCustomerTypeResult();

        if(offerCustomerType != null) {
            result.setOfferCustomerType(offerControl.getOfferCustomerTypeTransfer(getUserVisit(), offerCustomerType));
        }

        return result;
    }
    
}
