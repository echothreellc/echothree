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
import com.echothree.control.user.offer.common.result.EditOfferResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.OfferSpec;
import com.echothree.model.control.filter.common.FilterKinds;
import com.echothree.model.control.filter.common.FilterTypes;
import com.echothree.model.control.filter.server.logic.FilterLogic;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditOfferCommand
        extends BaseAbstractEditCommand<OfferSpec, OfferEdit, EditOfferResult, Offer, Offer> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Offer.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SalesOrderSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OfferItemPriceFilterName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    OfferControl offerControl;

    @Inject
    FilterLogic filterLogic;

    @Inject
    OfferLogic offerLogic;

    @Inject
    SelectorLogic selectorLogic;

    @Inject
    SequenceLogic sequenceLogic;

    /** Creates a new instance of EditOfferCommand */
    public EditOfferCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOfferResult getResult() {
        return OfferResultFactory.getEditOfferResult();
    }

    @Override
    public OfferEdit getEdit() {
        return OfferEditFactory.getOfferEdit();
    }

    @Override
    public Offer getEntity(EditOfferResult result) {
        return offerLogic.getOfferByName(this, spec.getOfferName(), editModeToEntityPermission(editMode));
    }

    @Override
    public Offer getLockEntity(Offer offer) {
        return offer;
    }

    @Override
    public void fillInResult(EditOfferResult result, Offer offer) {
        result.setOffer(offerControl.getOfferTransfer(getUserVisit(), offer));
    }

    private Sequence salesOrderSequence;
    private Selector offerItemSelector;
    private Filter offerItemPriceFilter;

    @Override
    public void doLock(OfferEdit edit, Offer offer) {
        var offerDescription = offerControl.getOfferDescription(offer, getPreferredLanguage());
        var offerDetail = offer.getLastDetail();
        var salesOrderSequence = offerDetail.getSalesOrderSequence();
        var offerItemSelector = offerDetail.getOfferItemSelector();
        var offerItemPriceFilter = offerDetail.getOfferItemPriceFilter();

        edit.setOfferName(offerDetail.getOfferName());
        edit.setSalesOrderSequenceName(salesOrderSequence == null? null: salesOrderSequence.getLastDetail().getSequenceName());
        edit.setOfferItemSelectorName(offerItemSelector == null? null: offerItemSelector.getLastDetail().getSelectorName());
        edit.setOfferItemPriceFilterName(offerItemPriceFilter == null? null: offerItemPriceFilter.getLastDetail().getFilterName());
        edit.setIsDefault(offerDetail.getIsDefault().toString());
        edit.setSortOrder(offerDetail.getSortOrder().toString());

        if(offerDescription != null) {
            edit.setDescription(offerDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Offer offer) {
        var offerName = edit.getOfferName();
        var duplicateOffer = offerControl.getOfferByName(offerName);

        if(duplicateOffer == null || offer.equals(duplicateOffer)) {
            var salesOrderSequenceName = edit.getSalesOrderSequenceName();

            salesOrderSequence = salesOrderSequenceName == null? null: sequenceLogic.getSequenceByName(this,
                    SequenceTypes.SALES_ORDER.name(), salesOrderSequenceName);

            if(!hasExecutionErrors()) {
                var offerItemSelectorName = edit.getOfferItemSelectorName();

                offerItemSelector = offerItemSelectorName == null? null: selectorLogic.getSelectorByName(this,
                        SelectorKinds.ITEM.name(), SelectorTypes.OFFER.name(), offerItemSelectorName);

                if(!hasExecutionErrors()) {
                    var offerItemPriceFilterName = edit.getOfferItemPriceFilterName();

                    offerItemPriceFilter = offerItemPriceFilterName == null? null: filterLogic.getFilterByName(this,
                            FilterKinds.PRICE.name(), FilterTypes.OFFER_ITEM_PRICE.name(), offerItemPriceFilterName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateOfferName.name(), offerName);
        }
    }

    @Override
    public void doUpdate(Offer offer) {
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

        offerLogic.updateOfferFromValue(offerDetailValue, partyPK);

        if(offerDescription == null && description != null) {
            offerControl.createOfferDescription(offer, getPreferredLanguage(), description, partyPK);
        } else if(offerDescription != null && description == null) {
            offerControl.deleteOfferDescription(offerDescription, partyPK);
        } else if(offerDescription != null && description != null) {
            var offerDescriptionValue = offerControl.getOfferDescriptionValue(offerDescription);

            offerDescriptionValue.setDescription(description);
            offerControl.updateOfferDescriptionFromValue(offerDescriptionValue, partyPK);
        }
    }

}
