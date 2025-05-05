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

import com.echothree.control.user.offer.common.edit.OfferChainTypeEdit;
import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.form.EditOfferChainTypeForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferChainTypeSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditOfferChainTypeCommand
        extends BaseEditCommand<OfferChainTypeSpec, OfferChainTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferChainType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditOfferChainTypeCommand */
    public EditOfferChainTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getEditOfferChainTypeResult();
        var offerName = spec.getOfferName();
        var offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainKindName = spec.getChainKindName();
            var chainKind = chainControl.getChainKindByName(chainKindName);
            
            if(chainKind != null) {
                var chainTypeName = spec.getChainTypeName();
                var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
                
                if(chainType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var offerChainType = offerControl.getOfferChainTypeForUpdate(offer, chainType);
                        
                        if(offerChainType != null) {
                            result.setOfferChainType(offerControl.getOfferChainTypeTransfer(getUserVisit(), offerChainType));
                            
                            if(lockEntity(offer)) {
                                var edit = OfferEditFactory.getOfferChainTypeEdit();
                                var chain = offerChainType.getChain();
                                
                                result.setEdit(edit);
                                edit.setChainName(chain == null? null: chain.getLastDetail().getChainName());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(offer));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOfferChainType.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var offerChainType = offerControl.getOfferChainTypeForUpdate(offer, chainType);
                        
                        if(offerChainType != null) {
                            var chainName = edit.getChainName();
                            var chain = chainName == null? null: chainControl.getChainByName(chainType, chainName);
                            
                            if(chainName == null || chain != null) {
                                if(lockEntityForUpdate(offer)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var offerChainTypeValue = offerControl.getOfferChainTypeValue(offerChainType);
                                        
                                        offerChainTypeValue.setChainPK(chain == null? null: chain.getPrimaryKey());
                                        
                                        offerControl.updateOfferChainTypeFromValue(offerChainTypeValue, partyPK);
                                    } finally {
                                        unlockEntity(offer);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownChainName.name(), chainName);
                            }
                            
                            if(hasExecutionErrors()) {
                                result.setOfferChainType(offerControl.getOfferChainTypeTransfer(getUserVisit(), offerChainType));
                                result.setEntityLock(getEntityLockTransfer(offer));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOfferChainType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return result;
    }
    
}
