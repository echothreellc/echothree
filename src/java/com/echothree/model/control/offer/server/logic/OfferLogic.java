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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.OfferUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.offer.common.exception.CannotDeleteOfferInUseException;
import com.echothree.model.control.offer.common.exception.DuplicateOfferNameException;
import com.echothree.model.control.offer.common.exception.UnknownDefaultOfferException;
import com.echothree.model.control.offer.common.exception.UnknownOfferNameException;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.offer.server.value.OfferDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OfferLogic
        extends BaseLogic {

    protected OfferLogic() {
        super();
    }

    public static OfferLogic getInstance() {
        return CDI.current().select(OfferLogic.class).get();
    }

    public Offer createOffer(final ExecutionErrorAccumulator eea, final String offerName, final Sequence salesOrderSequence,
            final Party departmentParty, final Selector offerItemSelector, final Filter offerItemPriceFilter, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var offerControl = Session.getModelController(OfferControl.class);
        var offer = offerControl.getOfferByName(offerName);

        if(offer == null) {
            offer = offerControl.createOffer(offerName, salesOrderSequence, departmentParty, offerItemSelector,
                    offerItemPriceFilter, isDefault, sortOrder, createdBy);

            if(description != null) {
                offerControl.createOfferDescription(offer, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateOfferNameException.class, eea, ExecutionErrors.DuplicateOfferName.name(), offerName);
        }

        return offer;
    }

    public Offer getOfferByName(final ExecutionErrorAccumulator eea, final String offerName,
            final EntityPermission entityPermission) {
        var offerControl = Session.getModelController(OfferControl.class);
        var offer = offerControl.getOfferByName(offerName, entityPermission);

        if(offer == null) {
            handleExecutionError(UnknownOfferNameException.class, eea, ExecutionErrors.UnknownOfferName.name(), offerName);
        }

        return offer;
    }

    public Offer getOfferByName(final ExecutionErrorAccumulator eea, final String offerName) {
        return getOfferByName(eea, offerName, EntityPermission.READ_ONLY);
    }

    public Offer getOfferByNameForUpdate(final ExecutionErrorAccumulator eea, final String offerName) {
        return getOfferByName(eea, offerName, EntityPermission.READ_WRITE);
    }

    public Offer getOfferByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OfferUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        Offer offer = null;
        var offerControl = Session.getModelController(OfferControl.class);
        var offerName = universalSpec.getOfferName();
        var parameterCount = (offerName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    offer = offerControl.getDefaultOffer(entityPermission);

                    if(offer == null) {
                        handleExecutionError(UnknownDefaultOfferException.class, eea, ExecutionErrors.UnknownDefaultOffer.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(offerName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Offer.name());

                    if(!eea.hasExecutionErrors()) {
                        offer = offerControl.getOfferByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    offer = getOfferByName(eea, offerName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return offer;
    }

    public Offer getOfferByUniversalSpec(final ExecutionErrorAccumulator eea,
            final OfferUniversalSpec universalSpec, boolean allowDefault) {
        return getOfferByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Offer getOfferByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final OfferUniversalSpec universalSpec, boolean allowDefault) {
        return getOfferByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateOfferFromValue(OfferDetailValue offerDetailValue, BasePK updatedBy) {
        var offerControl = Session.getModelController(OfferControl.class);

        if(offerDetailValue.getOfferItemSelectorPKHasBeenModified() && offerDetailValue.getOfferItemSelectorPK() != null) {
            var offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offerDetailValue.getOfferPK());

            OfferItemLogic.getInstance().deleteOfferItemsByOffer(offer, updatedBy);
        } else if(offerDetailValue.getOfferItemPriceFilterPKHasBeenModified() && offerDetailValue.getOfferItemPriceFilterPK() != null) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var offer = OfferFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offerDetailValue.getOfferPK());

            var offerItems = offerItemControl.getOfferItemsByOffer(offer);
            for(var offerItem : offerItems) {
                var offerItemPrices = offerItemControl.getOfferItemPricesByOfferItemForUpdate(offerItem);

                for(var offerItemPrice : offerItemPrices) {
                    offerItemControl.deleteOfferItemPrice(offerItemPrice, updatedBy);
                }
            }
        }

        offerControl.updateOfferFromValue(offerDetailValue, updatedBy);
    }

    public void deleteOffer(final ExecutionErrorAccumulator eea, final Offer offer, final BasePK deletedBy) {
        var offerUseControl = Session.getModelController(OfferUseControl.class);

        if(offerUseControl.countOfferUsesByOffer(offer) == 0) {
            var offerControl = Session.getModelController(OfferControl.class);

            offerControl.deleteOffer(offer, deletedBy);
        } else {
            handleExecutionError(CannotDeleteOfferInUseException.class, eea, ExecutionErrors.CannotDeleteOfferInUse.name());
        }
    }

}