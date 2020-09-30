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
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.offer.server.entity.Offer;
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

public class OfferLogic
        extends BaseLogic {

    private OfferLogic() {
        super();
    }

    private static class OfferLogicHolder {

        static OfferLogic instance = new OfferLogic();
    }

    public static OfferLogic getInstance() {
        return OfferLogicHolder.instance;
    }

    public Offer createOffer(final ExecutionErrorAccumulator eea, final String offerName, final Sequence salesOrderSequence,
            final Party departmentParty, final Selector offerItemSelector, final Filter offerItemPriceFilter, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        Offer offer = offerControl.getOfferByName(offerName);

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
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        Offer offer = offerControl.getOfferByName(offerName, entityPermission);

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
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = universalSpec.getOfferName();
        int parameterCount = (offerName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    offer = offerControl.getDefaultOffer(entityPermission);

                    if(offer == null) {
                        handleExecutionError(UnknownDefaultOfferException.class, eea, ExecutionErrors.UnknownDefaultOffer.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(offerName == null) {
                    EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.Offer.name());

                    if(!eea.hasExecutionErrors()) {
                        offer = offerControl.getOfferByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    offer = getOfferByName(eea, offerName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
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
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        offerControl.updateOfferFromValue(offerDetailValue, updatedBy);
    }

    public void deleteOffer(final ExecutionErrorAccumulator eea, final Offer offer, final BasePK deletedBy) {
        var offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);

        if(offerUseControl.countOfferUsesByOffer(offer) == 0) {
            var offerControl = (OfferControl)Session.getModelController(OfferControl.class);

            offerControl.deleteOffer(offer, deletedBy);
        } else {
            handleExecutionError(CannotDeleteOfferInUseException.class, eea, ExecutionErrors.CannotDeleteOfferInUse.name());
        }
    }

}