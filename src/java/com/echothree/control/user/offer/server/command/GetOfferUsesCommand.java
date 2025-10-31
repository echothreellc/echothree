// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetOfferUsesCommand
        extends BaseMultipleEntitiesCommand<OfferUse, GetOfferUsesForm> {

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

    private Offer offer;
    private Use use;

    @Override
    protected Collection<OfferUse> getEntities() {
        Collection<OfferUse> offerUses = null;
        var offerName = form.getOfferName();
        var useName = form.getUseName();

        if(ParameterUtils.getInstance().isExactlyOneBooleanTrue(this, offerName != null && useName == null,
                offerName == null && useName != null, offerName == null && useName == null)) {
            var offerUseControl = Session.getModelController(OfferUseControl.class);

            if(offerName != null) {
                offer = OfferLogic.getInstance().getOfferByName(this, offerName);

                if(!hasExecutionErrors()) {
                    offerUses = offerUseControl.getOfferUsesByOffer(offer);
                }
            } if(useName != null) {
                use = UseLogic.getInstance().getUseByName(this, useName);

                if(!hasExecutionErrors()) {
                    offerUses = offerUseControl.getOfferUsesByUse(use);
                }
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
            var offerUseControl = Session.getModelController(OfferUseControl.class);
            var userVisit = getUserVisit();

            if(offer != null) {
                var offerControl = Session.getModelController(OfferControl.class);

                result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            }

            if(use != null) {
                var useControl = Session.getModelController(UseControl.class);

                result.setUse(useControl.getUseTransfer(userVisit, use));
            }

            result.setOfferUses(offerUseControl.getOfferUseTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
