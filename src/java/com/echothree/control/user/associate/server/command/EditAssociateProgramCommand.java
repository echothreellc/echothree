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

import com.echothree.control.user.associate.common.edit.AssociateEditFactory;
import com.echothree.control.user.associate.common.edit.AssociateProgramEdit;
import com.echothree.control.user.associate.common.form.EditAssociateProgramForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.control.user.associate.common.spec.AssociateProgramSpec;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditAssociateProgramCommand
        extends BaseEditCommand<AssociateProgramSpec, AssociateProgramEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("AssociateSequenceName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("AssociatePartyContactMechanismSequenceName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("AssociateReferralSequenceName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("ItemIndirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null));
        temp.add(new FieldDefinition("ItemDirectSalePercent", FieldType.FRACTIONAL_PERCENT, false, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditAssociateProgramCommand */
    public EditAssociateProgramCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        var result = AssociateResultFactory.getEditAssociateProgramResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var associateProgramName = spec.getAssociateProgramName();
            var associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
            
            if(associateProgram != null) {
                result.setAssociateProgram(associateControl.getAssociateProgramTransfer(getUserVisit(), associateProgram));
                
                if(lockEntity(associateProgram)) {
                    var associateProgramDescription = associateControl.getAssociateProgramDescription(associateProgram, getPreferredLanguage());
                    var edit = AssociateEditFactory.getAssociateProgramEdit();
                    var associateProgramDetail = associateProgram.getLastDetail();
                    var associateSequence = associateProgramDetail.getAssociateSequence();
                    var associatePartyContactMechanismSequence = associateProgramDetail.getAssociatePartyContactMechanismSequence();
                    var associateReferralSequence = associateProgramDetail.getAssociateReferralSequence();
                    
                    result.setEdit(edit);
                    edit.setAssociateProgramName(associateProgramDetail.getAssociateProgramName());
                    edit.setAssociateSequenceName(associateSequence == null? null: associateSequence.getLastDetail().getSequenceName());
                    edit.setAssociatePartyContactMechanismSequenceName(associatePartyContactMechanismSequence == null? null: associatePartyContactMechanismSequence.getLastDetail().getSequenceName());
                    edit.setAssociateReferralSequenceName(associateReferralSequence == null? null: associateReferralSequence.getLastDetail().getSequenceName());
                    edit.setItemIndirectSalePercent(PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemIndirectSalePercent()));
                    edit.setItemDirectSalePercent(PercentUtils.getInstance().formatFractionalPercent(associateProgramDetail.getItemDirectSalePercent()));
                    edit.setIsDefault(associateProgramDetail.getIsDefault().toString());
                    edit.setSortOrder(associateProgramDetail.getSortOrder().toString());
                    
                    if(associateProgramDescription != null)
                        edit.setDescription(associateProgramDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(associateProgram));
            } else {
                addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var associateProgramName = spec.getAssociateProgramName();
            var associateProgram = associateControl.getAssociateProgramByNameForUpdate(associateProgramName);
            
            if(associateProgram != null) {
                associateProgramName = edit.getAssociateProgramName();
                var duplicateAssociateProgram = associateControl.getAssociateProgramByName(associateProgramName);
                
                if(duplicateAssociateProgram == null || associateProgram.equals(duplicateAssociateProgram)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var associateSequenceName = edit.getAssociateSequenceName();
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
                        var associatePartyContactMechanismSequenceName = edit.getAssociatePartyContactMechanismSequenceName();
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
                            var associateReferralSequenceName = edit.getAssociateReferralSequenceName();
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
                                if(lockEntityForUpdate(associateProgram)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var strItemIndirectSalePercent = edit.getItemIndirectSalePercent();
                                        var strItemDirectSalePercent = edit.getItemDirectSalePercent();
                                        var associateProgramDetailValue = associateControl.getAssociateProgramDetailValueForUpdate(associateProgram);
                                        var associateProgramDescription = associateControl.getAssociateProgramDescriptionForUpdate(associateProgram, getPreferredLanguage());
                                        var description = edit.getDescription();
                                        
                                        associateProgramDetailValue.setAssociateProgramName(edit.getAssociateProgramName());
                                        associateProgramDetailValue.setAssociateSequencePK(associateSequence == null? null: associateSequence.getPrimaryKey());
                                        associateProgramDetailValue.setAssociatePartyContactMechanismSequencePK(associatePartyContactMechanismSequence == null? null: associatePartyContactMechanismSequence.getPrimaryKey());
                                        associateProgramDetailValue.setAssociateReferralSequencePK(associateReferralSequence == null? null: associateReferralSequence.getPrimaryKey());
                                        associateProgramDetailValue.setItemIndirectSalePercent(strItemIndirectSalePercent == null? null: Integer.valueOf(strItemIndirectSalePercent));
                                        associateProgramDetailValue.setItemDirectSalePercent(strItemDirectSalePercent == null? null: Integer.valueOf(strItemDirectSalePercent));
                                        associateProgramDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        associateProgramDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        associateControl.updateAssociateProgramFromValue(associateProgramDetailValue, partyPK);
                                        
                                        if(associateProgramDescription == null && description != null) {
                                            associateControl.createAssociateProgramDescription(associateProgram, getPreferredLanguage(), description, partyPK);
                                        } else if(associateProgramDescription != null && description == null) {
                                            associateControl.deleteAssociateProgramDescription(associateProgramDescription, partyPK);
                                        } else if(associateProgramDescription != null && description != null) {
                                            var associateProgramDescriptionValue = associateControl.getAssociateProgramDescriptionValue(associateProgramDescription);
                                            
                                            associateProgramDescriptionValue.setDescription(description);
                                            associateControl.updateAssociateProgramDescriptionFromValue(associateProgramDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(associateProgram);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
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
            } else {
                addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
            }
        }
        
        return result;
    }
    
}
