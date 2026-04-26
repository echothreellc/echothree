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

import com.echothree.control.user.offer.common.form.GetOfferUsesForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.offer.server.logic.UseLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.factory.OfferUseFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetOfferUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<OfferUse, GetOfferUsesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferUse.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, false, null, 20L),
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, false, null, 20L)
        );
    }
    
    /** Creates a new instance of GetOfferUsesCommand */
    public GetOfferUsesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    OfferControl offerControl;

    @Inject
    OfferUseControl offerUseControl;

    @Inject
    UseControl useControl;

    @Inject
    OfferLogic offerLogic;

    @Inject
    UseLogic useLogic;

    private Offer offer;
    private Use use;

    @Override
    protected void handleForm() {
        var offerName = form.getOfferName();
        var useName = form.getUseName();

        if(ParameterUtils.getInstance().isExactlyOneBooleanTrue(this, offerName != null && useName == null,
                offerName == null && useName != null, offerName == null && useName == null)) {
            if(offerName != null) {
                offer = offerLogic.getOfferByName(this, offerName);
            } else if(useName != null) {
                use = useLogic.getUseByName(this, useName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(offer != null) {
                total = offerUseControl.countOfferUsesByOffer(offer);
            } else if(use != null) {
                total = offerUseControl.countOfferUsesByUse(use);
            } else {
                total = offerUseControl.countOfferUses();
            }
        }

        return total;
    }

    @Override
    protected Collection<OfferUse> getEntities() {
        Collection<OfferUse> offerUses = null;

        if(!hasExecutionErrors()) {
            if(offer != null) {
                offerUses = offerUseControl.getOfferUsesByOffer(offer);
            } else if(use != null) {
                offerUses = offerUseControl.getOfferUsesByUse(use);
            } else {
                offerUses = offerUseControl.getOfferUses();
            }
        }

        return offerUses;
    }
    
    @Override
    protected BaseResult getResult(Collection<OfferUse> entities) {
        var result = OfferResultFactory.getGetOfferUsesResult();
        
        if(entities != null) {
            var userVisit = getUserVisit();

            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            }

            if(use != null) {
                result.setUse(useControl.getUseTransfer(userVisit, use));
            }

            if(session.hasLimit(OfferUseFactory.class)) {
                result.setOfferUseCount(getTotalEntities());
            }

            result.setOfferUses(offerUseControl.getOfferUseTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
