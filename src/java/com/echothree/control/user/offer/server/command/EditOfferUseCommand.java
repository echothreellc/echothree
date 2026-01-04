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

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.OfferUseEdit;
import com.echothree.control.user.offer.common.form.EditOfferUseForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferUseSpec;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditOfferUseCommand
        extends BaseEditCommand<OfferUseSpec, OfferUseEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferUse.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditOfferUseCommand */
    public EditOfferUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getEditOfferUseResult();
        var offerName = spec.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var useControl = Session.getModelController(UseControl.class);
            var useName = spec.getUseName();
            var use = useControl.getUseByName(useName);
            
            if(use != null) {
                var offerUseControl = Session.getModelController(OfferUseControl.class);

                if(editMode.equals(EditMode.LOCK)) {
                    var offerUse = offerUseControl.getOfferUse(offer, use);
                    
                    if(offerUse != null) {
                        result.setOfferUse(offerUseControl.getOfferUseTransfer(getUserVisit(), offerUse));
                        
                        if(lockEntity(offerUse)) {
                            var edit = OfferEditFactory.getOfferUseEdit();
                            var offerUseDetail = offerUse.getLastDetail();
                            var salesOrderSequence = offerUseDetail.getSalesOrderSequence();
                            
                            result.setEdit(edit);
                            edit.setSalesOrderSequenceName(salesOrderSequence == null? null: salesOrderSequence.getLastDetail().getSequenceName());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(offerUse));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferUse.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var offerUse = offerUseControl.getOfferUseForUpdate(offer, use);
                    
                    if(offerUse != null) {
                        var salesOrderSequenceName = edit.getSalesOrderSequenceName();
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
                            if(lockEntityForUpdate(offerUse)) {
                                try {
                                    var offerUseDetailValue = offerUseControl.getOfferUseDetailValueForUpdate(offerUse);
                                    
                                    offerUseDetailValue.setSalesOrderSequencePK(salesOrderSequence == null? null: salesOrderSequence.getPrimaryKey());

                                    offerUseControl.updateOfferUseFromValue(offerUseDetailValue, getPartyPK());
                                } finally {
                                    unlockEntity(offerUse);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSalesOrderSequenceName.name(), salesOrderSequenceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownOfferUse.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
            }
        }  else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return result;
    }
    
}
