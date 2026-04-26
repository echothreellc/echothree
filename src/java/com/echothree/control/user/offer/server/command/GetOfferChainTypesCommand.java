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

import com.echothree.control.user.offer.common.form.GetOfferChainTypesForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.offer.server.factory.OfferChainTypeFactory;
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
public class GetOfferChainTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<OfferChainType, GetOfferChainTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferChainType.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetOfferChainTypesCommand */
    public GetOfferChainTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    ChainControl chainControl;

    @Inject
    OfferControl offerControl;

    @Inject
    OfferLogic offerLogic;

    private Offer offer;
    private ChainType chainType;

    @Override
    protected void handleForm() {
        var offerName = form.getOfferName();
        var chainKindName = form.getChainKindName();
        var chainTypeName = form.getChainTypeName();
        var parameterCount = (offerName != null ? 1 : 0) + (chainKindName != null && chainTypeName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(offerName != null) {
                offer = offerLogic.getOfferByName(this, offerName);
            } else {
                var chainKind = chainControl.getChainKindByName(chainKindName);

                if(chainKind != null) {
                    chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

                    if(chainType == null) {
                        addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKind.getLastDetail().getChainKindName(), chainTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
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
            if(offer != null) {
                total = offerControl.countOfferChainTypesByOffer(offer);
            } else {
                total = offerControl.countOfferChainTypesByChainType(chainType);
            }
        }

        return total;
    }

    @Override
    protected Collection<OfferChainType> getEntities() {
        Collection<OfferChainType> entities = null;

        if(!hasExecutionErrors()) {
            if(offer != null) {
                entities = offerControl.getOfferChainTypesByOffer(offer);
            } else {
                entities = offerControl.getOfferChainTypesByChainType(chainType);
            }
        }

        return entities;
    }
    
    @Override
    protected BaseResult getResult(Collection<OfferChainType> entities) {
        var result = OfferResultFactory.getGetOfferChainTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();
         
            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            }
            
            if(chainType != null) {
                result.setChainType(chainControl.getChainTypeTransfer(userVisit, chainType));
            }

            if(session.hasLimit(OfferChainTypeFactory.class)) {
                result.setOfferChainTypeCount(getTotalEntities());
            }

            result.setOfferChainTypes(offerControl.getOfferChainTypeTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
