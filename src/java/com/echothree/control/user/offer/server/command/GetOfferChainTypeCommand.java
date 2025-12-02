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

import com.echothree.control.user.offer.common.form.GetOfferChainTypeForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetOfferChainTypeCommand
        extends BaseSingleEntityCommand<OfferChainType, GetOfferChainTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferChainType.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetOfferChainTypeCommand */
    public GetOfferChainTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected OfferChainType getEntity() {
        var offerControl = Session.getModelController(OfferControl.class);
        var offerName = form.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        OfferChainType offerChainType = null;
        
        if(offer != null) {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainKindName = form.getChainKindName();
            var chainKind = chainControl.getChainKindByName(chainKindName);

            if(chainKind != null) {
                var chainTypeName = form.getChainTypeName();
                var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

                if(chainType != null) {
                    offerChainType = offerControl.getOfferChainType(offer, chainType);

                    if(offerChainType == null) {
                        addExecutionError(ExecutionErrors.UnknownOfferChainType.name(), offer.getLastDetail().getOfferName(),
                                chainKind.getLastDetail().getChainKindName(), chainType.getLastDetail().getChainTypeName());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainTypeName.name(),
                            chainKind.getLastDetail().getChainKindName(), chainTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
            }
        }  else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offerChainType;
    }
    
    @Override
    protected BaseResult getResult(OfferChainType offerChainType) {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getGetOfferChainTypeResult();

        if(offerChainType != null) {
            result.setOfferChainType(offerControl.getOfferChainTypeTransfer(getUserVisit(), offerChainType));
        }
        
        return result;
    }
    
}
