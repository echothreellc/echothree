// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.offer.common.result.EditOfferResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferSpec;
import com.echothree.model.control.filter.common.FilterConstants;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.model.data.offer.server.value.OfferDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOfferCommand */
    public EditOfferCommand(UserVisitPK userVisitPK, EditOfferForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        EditOfferResult result = OfferResultFactory.getEditOfferResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String offerName = spec.getOfferName();
            Offer offer = offerControl.getOfferByName(offerName);
            
            if(offer != null) {
                result.setOffer(offerControl.getOfferTransfer(getUserVisit(), offer));
                
                if(lockEntity(offer)) {
                    OfferDescription offerDescription = offerControl.getOfferDescription(offer, getPreferredLanguage());
                    OfferEdit edit = OfferEditFactory.getOfferEdit();
                    OfferDetail offerDetail = offer.getLastDetail();
                    Sequence salesOrderSequence = offerDetail.getSalesOrderSequence();
                    Selector offerItemSelector = offerDetail.getOfferItemSelector();
                    Filter offerItemPriceFilter = offerDetail.getOfferItemPriceFilter();
                    
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
            String offerName = spec.getOfferName();
            Offer offer = offerControl.getOfferByNameForUpdate(offerName);
            
            if(offer != null) {
                offerName = edit.getOfferName();
                Offer duplicateOffer = offerControl.getOfferByName(offerName);
                
                if(duplicateOffer == null || offer.equals(duplicateOffer)) {
                    String salesOrderSequenceName = edit.getSalesOrderSequenceName();
                    Sequence salesOrderSequence = null;
                    
                    if(salesOrderSequenceName != null) {
                        var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                        SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.SALES_ORDER.name());
                        
                        if(sequenceType != null) {
                            salesOrderSequence = sequenceControl.getSequenceByName(sequenceType, salesOrderSequenceName);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.SALES_ORDER.name());
                        }
                    }
                    
                    if(salesOrderSequenceName == null || salesOrderSequence != null) {
                        String offerItemSelectorName = edit.getOfferItemSelectorName();
                        Selector offerItemSelector = null;
                        
                        if(offerItemSelectorName != null) {
                            var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                            SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_ITEM);
                            
                            if(selectorKind != null) {
                                SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind,
                                        SelectorConstants.SelectorType_OFFER);
                                
                                if(selectorType != null) {
                                    offerItemSelector = selectorControl.getSelectorByName(selectorType, offerItemSelectorName);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorType_OFFER);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_ITEM);
                            }
                        }
                        
                        if(offerItemSelectorName == null || offerItemSelector != null) {
                            String offerItemPriceFilterName = edit.getOfferItemPriceFilterName();
                            Filter offerItemPriceFilter = null;
                            
                            if(offerItemPriceFilterName != null) {
                                var filterControl = (FilterControl)Session.getModelController(FilterControl.class);
                                FilterKind filterKind = filterControl.getFilterKindByName(FilterConstants.FilterKind_PRICE);
                                FilterType filterType = filterControl.getFilterTypeByName(filterKind, FilterConstants.FilterType_OFFER_ITEM_PRICE);
                                
                                if(filterType != null) {
                                    offerItemPriceFilter = filterControl.getFilterByName(filterType, offerItemPriceFilterName);
                                }
                            }
                            
                            if(offerItemPriceFilterName == null || offerItemPriceFilter != null) {
                                if(lockEntityForUpdate(offer)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        OfferDetailValue offerDetailValue = offerControl.getOfferDetailValueForUpdate(offer);
                                        OfferDescription offerDescription = offerControl.getOfferDescriptionForUpdate(offer, getPreferredLanguage());
                                        String description = edit.getDescription();

                                        offerDetailValue.setOfferName(edit.getOfferName());
                                        offerDetailValue.setSalesOrderSequencePK(salesOrderSequence == null? null: salesOrderSequence.getPrimaryKey());
                                        offerDetailValue.setOfferItemSelectorPK(offerItemSelector == null? null: offerItemSelector.getPrimaryKey());
                                        offerDetailValue.setOfferItemPriceFilterPK(offerItemPriceFilter == null? null: offerItemPriceFilter.getPrimaryKey());
                                        offerDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        offerDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                        offerControl.updateOfferFromValue(offerDetailValue, partyPK);

                                        if(offerDescription == null && description != null) {
                                            offerControl.createOfferDescription(offer, getPreferredLanguage(), description, partyPK);
                                        } else if(offerDescription != null && description == null) {
                                            offerControl.deleteOfferDescription(offerDescription, partyPK);
                                        } else if(offerDescription != null && description != null) {
                                            OfferDescriptionValue offerDescriptionValue = offerControl.getOfferDescriptionValue(offerDescription);

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
