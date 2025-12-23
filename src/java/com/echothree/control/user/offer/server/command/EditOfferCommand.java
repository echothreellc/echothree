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

import com.echothree.control.user.offer.common.edit.OfferEdit;
import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.form.EditOfferForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferSpec;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.selector.server.entity.Selector;
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
public class EditOfferCommand
        extends BaseEditCommand<OfferSpec, OfferEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Offer.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditOfferCommand */
    public EditOfferCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = Session.getModelController(OfferControl.class);
        var result = OfferResultFactory.getEditOfferResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var offerName = spec.getOfferName();
            var offer = offerControl.getOfferByName(offerName);
            
            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(getUserVisit(), offer));
                
                if(lockEntity(offer)) {
                    var offerDescription = offerControl.getOfferDescription(offer, getPreferredLanguage());
                    var edit = OfferEditFactory.getOfferEdit();
                    var offerDetail = offer.getLastDetail();
                    var salesOrderSequence = offerDetail.getSalesOrderSequence();
                    var offerItemSelector = offerDetail.getOfferItemSelector();
                    var offerItemPriceFilter = offerDetail.getOfferItemPriceFilter();
                    
                    result.setEdit(edit);
                    edit.setOfferName(offerDetail.getOfferName());
                    edit.setSalesOrderSequenceName(salesOrderSequence == null? null: salesOrderSequence.getLastDetail().getSequenceName());
                    edit.setOfferItemSelectorName(offerItemSelector == null? null: offerItemSelector.getLastDetail().getSelectorName());
                    edit.setOfferItemPriceFilterName(offerItemPriceFilter == null? null: offerItemPriceFilter.getLastDetail().getFilterName());
                    edit.setIsDefault(offerDetail.getIsDefault().toString());
                    edit.setSortOrder(offerDetail.getSortOrder().toString());
                    
                    if(offerDescription != null) {
                        edit.setDescription(offerDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(offer));
            } else {
                addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var offerName = spec.getOfferName();
            var offer = offerControl.getOfferByNameForUpdate(offerName);
            
            if(offer != null) {
                offerName = edit.getOfferName();
                var duplicateOffer = offerControl.getOfferByName(offerName);
                
                if(duplicateOffer == null || offer.equals(duplicateOffer)) {
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
                        var offerItemSelectorName = edit.getOfferItemSelectorName();
                        Selector offerItemSelector = null;
                        
                        if(offerItemSelectorName != null) {
                            var selectorControl = Session.getModelController(SelectorControl.class);
                            var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());
                            
                            if(selectorKind != null) {
                                var selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                        SelectorTypes.OFFER.name());
                                
                                if(selectorType != null) {
                                    offerItemSelector = selectorControl.getSelectorByName(selectorType, offerItemSelectorName);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.OFFER.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                            }
                        }
                        
                        if(offerItemSelectorName == null || offerItemSelector != null) {
                            var offerItemPriceFilterName = edit.getOfferItemPriceFilterName();
                            Filter offerItemPriceFilter = null;
                            
                            if(offerItemPriceFilterName != null) {
                                var filterControl = Session.getModelController(FilterControl.class);
                                var filterKind = filterControl.getFilterKindByName(FilterKinds.PRICE.name());
                                var filterType = filterControl.getFilterTypeByName(filterKind, FilterTypes.OFFER_ITEM_PRICE.name());
                                
                                if(filterType != null) {
                                    offerItemPriceFilter = filterControl.getFilterByName(filterType, offerItemPriceFilterName);
                                }
                            }
                            
                            if(offerItemPriceFilterName == null || offerItemPriceFilter != null) {
                                if(lockEntityForUpdate(offer)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var offerDetailValue = offerControl.getOfferDetailValueForUpdate(offer);
                                        var offerDescription = offerControl.getOfferDescriptionForUpdate(offer, getPreferredLanguage());
                                        var description = edit.getDescription();

                                        offerDetailValue.setOfferName(edit.getOfferName());
                                        offerDetailValue.setSalesOrderSequencePK(salesOrderSequence == null? null: salesOrderSequence.getPrimaryKey());
                                        offerDetailValue.setOfferItemSelectorPK(offerItemSelector == null? null: offerItemSelector.getPrimaryKey());
                                        offerDetailValue.setOfferItemPriceFilterPK(offerItemPriceFilter == null? null: offerItemPriceFilter.getPrimaryKey());
                                        offerDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        offerDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                        OfferLogic.getInstance().updateOfferFromValue(offerDetailValue, partyPK);

                                        if(offerDescription == null && description != null) {
                                            offerControl.createOfferDescription(offer, getPreferredLanguage(), description, partyPK);
                                        } else if(offerDescription != null && description == null) {
                                            offerControl.deleteOfferDescription(offerDescription, partyPK);
                                        } else if(offerDescription != null && description != null) {
                                            var offerDescriptionValue = offerControl.getOfferDescriptionValue(offerDescription);

                                            offerDescriptionValue.setDescription(description);
                                            offerControl.updateOfferDescriptionFromValue(offerDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(offer);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownOfferItemPriceFilterName.name(), offerItemPriceFilterName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOfferItemSelectorName.name(), offerItemSelectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSalesOrderSequenceName.name(), salesOrderSequenceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateOfferName.name(), offerName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
            }
        }
        
        return result;
    }
    
}
