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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.CreateAssociateProgramForm;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateAssociateProgramCommand
        extends BaseSimpleCommand<CreateAssociateProgramForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("AssociateSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AssociatePartyContactMechanismSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AssociateReferralSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemIndirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null),
            new FieldDefinition("ItemDirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateAssociateProgramCommand */
    public CreateAssociateProgramCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        var associateProgramName = form.getAssociateProgramName();
        var associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        
        if(associateProgram == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var associateSequenceName = form.getAssociateSequenceName();
            Sequence associateSequence = null;
            
            if(associateSequenceName != null) {
                var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.ASSOCIATE.name());
                
                if(sequenceType != null) {
                    associateSequence = sequenceControl.getSequenceByName(sequenceType, associateSequenceName);
                } else {
                    addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.ASSOCIATE.name());
                }
            }
            
            if(associateSequenceName == null || associateSequence != null) {
                var associatePartyContactMechanismSequenceName = form.getAssociatePartyContactMechanismSequenceName();
                Sequence associatePartyContactMechanismSequence = null;
                
                if(associatePartyContactMechanismSequenceName != null) {
                    var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.ASSOCIATE_PARTY_CONTACT_MECHANISM.name());
                    
                    if(sequenceType != null) {
                        associatePartyContactMechanismSequence = sequenceControl.getSequenceByName(sequenceType, associatePartyContactMechanismSequenceName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.ASSOCIATE_PARTY_CONTACT_MECHANISM.name());
                    }
                }
                
                if(associatePartyContactMechanismSequenceName == null || associatePartyContactMechanismSequence != null) {
                    var associateReferralSequenceName = form.getAssociateReferralSequenceName();
                    Sequence associateReferralSequence = null;
                    
                    if(associateReferralSequenceName != null) {
                        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.ASSOCIATE_REFERRAL.name());
                        
                        if(sequenceType != null) {
                            associateReferralSequence = sequenceControl.getSequenceByName(sequenceType, associateReferralSequenceName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.ASSOCIATE_REFERRAL.name());
                        }
                    }
                    
                    if(associateReferralSequenceName == null || associateReferralSequence != null) {
                        var createdBy = getPartyPK();
                        var strItemIndirectSalePercent = form.getItemIndirectSalePercent();
                        var itemIndirectSalePercent = strItemIndirectSalePercent == null? null: Integer.valueOf(strItemIndirectSalePercent);
                        var strItemDirectSalePercent = form.getItemDirectSalePercent();
                        var itemDirectSalePercent = strItemDirectSalePercent == null? null: Integer.valueOf(strItemDirectSalePercent);
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        
                        associateProgram = associateControl.createAssociateProgram(associateProgramName, associateSequence,
                                associatePartyContactMechanismSequence, associateReferralSequence, itemIndirectSalePercent,
                                itemDirectSalePercent, isDefault, sortOrder, createdBy);
                        
                        if(description != null) {
                            associateControl.createAssociateProgramDescription(associateProgram, getPreferredLanguage(), description,
                                    createdBy);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSequenceName.name(), associateReferralSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSequenceName.name(), associatePartyContactMechanismSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceName.name(), associateSequenceName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateAssociateProgramName.name(), associateProgramName);
        }
        
        return null;
    }
    
}
