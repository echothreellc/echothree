// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.offer.common.form.CreateOfferUseForm;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateOfferUseCommand
        extends BaseSimpleCommand<CreateOfferUseForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferUse.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateOfferUseCommand */
    public CreateOfferUseCommand(UserVisitPK userVisitPK, CreateOfferUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Offer offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            String useName = form.getUseName();
            Use use = offerControl.getUseByName(useName);
            
            if(use != null) {
                OfferUse offerUse = offerControl.getOfferUse(offer, use);
                
                if(offerUse == null) {
                    String salesOrderSequenceName = form.getSalesOrderSequenceName();
                    Sequence salesOrderSequence = null;
                    
                    if(salesOrderSequenceName != null) {
                        SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_SALES_ORDER);
                        
                        if(sequenceType != null) {
                            salesOrderSequence = sequenceControl.getSequenceByName(sequenceType, salesOrderSequenceName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceConstants.SequenceType_SALES_ORDER);
                        }
                    }
                    
                    if(salesOrderSequenceName == null || salesOrderSequence != null) {
                        String sourceName = new StringBuilder(offerName).append(useName).toString();
                        Source source = offerControl.getSourceByName(sourceName);
                        
                        if(source == null) {
                            BasePK partyPK = getPartyPK();
                            
                            offerUse = offerControl.createOfferUse(offer, use, salesOrderSequence, partyPK);
                            offerControl.createSource(sourceName, offerUse, Boolean.FALSE, 1, partyPK);
                        }  else {
                            addExecutionError(ExecutionErrors.DuplicateSourceName.name(), sourceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSalesOrderSequenceName.name(), salesOrderSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateOfferUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        }  else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return null;
    }
    
}
