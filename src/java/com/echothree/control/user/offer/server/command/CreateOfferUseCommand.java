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

import com.echothree.control.user.offer.common.form.CreateOfferUseForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
    public CreateOfferUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getCreateOfferUseResult();
        OfferUse offerUse = null;
        var offerName = form.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var useControl = Session.getModelController(UseControl.class);
            var useName = form.getUseName();
            var use = useControl.getUseByName(useName);
            
            if(use != null) {
                var offerUseControl = Session.getModelController(OfferUseControl.class);

                offerUse = offerUseControl.getOfferUse(offer, use);
                
                if(offerUse == null) {
                    var salesOrderSequenceName = form.getSalesOrderSequenceName();
                    Sequence salesOrderSequence = null;
                    
                    if(salesOrderSequenceName != null) {
                        var sequenceControl = Session.getModelController(SequenceControl.class);
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.SALES_ORDER.name());
                        
                        if(sequenceType != null) {
                            salesOrderSequence = sequenceControl.getSequenceByName(sequenceType, salesOrderSequenceName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.SALES_ORDER.name());
                        }
                    }
                    
                    if(salesOrderSequenceName == null || salesOrderSequence != null) {
                        var sourceControl = Session.getModelController(SourceControl.class);
                        var sourceName = offerName + useName;
                        var source = sourceControl.getSourceByName(sourceName);
                        
                        if(source == null) {
                            BasePK partyPK = getPartyPK();
                            
                            offerUse = offerUseControl.createOfferUse(offer, use, salesOrderSequence, partyPK);
                            sourceControl.createSource(sourceName, offerUse, false, 1, partyPK);
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

        if(offerUse != null) {
            var offerUseDetail = offerUse.getLastDetail();

            result.setOfferName(offerUseDetail.getOffer().getLastDetail().getOfferName());
            result.setUseName(offerUseDetail.getUse().getLastDetail().getUseName());
            result.setEntityRef(offerUseDetail.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
