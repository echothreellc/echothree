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

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.common.exception.CannotDeleteOfferUseInUseException;
import com.echothree.model.control.offer.common.exception.UnknownOfferUseException;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.sales.server.control.SalesOrderControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OfferUseLogic
        extends BaseLogic {

    protected OfferUseLogic() {
        super();
    }

    public static OfferUseLogic getInstance() {
        return CDI.current().select(OfferUseLogic.class).get();
    }
    
    public OfferUse getOfferUseByName(final ExecutionErrorAccumulator eea, final String offerName, final String useName,
            final EntityPermission entityPermission) {
        var offer = OfferLogic.getInstance().getOfferByName(eea, offerName);
        var use = UseLogic.getInstance().getUseByName(eea, useName);
        OfferUse offerUse = null;

        if(!eea.hasExecutionErrors()) {
            var offerUseControl = Session.getModelController(OfferUseControl.class);

            offerUse = offerUseControl.getOfferUse(offer, use, entityPermission);

            if(offerUse == null) {
                handleExecutionError(UnknownOfferUseException.class, eea, ExecutionErrors.UnknownOfferUse.name(),
                        offerName, useName);
            }
        }

        return offerUse;
    }

    public OfferUse getOfferUseByName(final ExecutionErrorAccumulator eea, final String offerName, final String useName) {
        return getOfferUseByName(eea, offerName, useName, EntityPermission.READ_ONLY);
    }

    public OfferUse getOfferUseByNameForUpdate(final ExecutionErrorAccumulator eea, final String offerName, final String useName) {
        return getOfferUseByName(eea, offerName, useName, EntityPermission.READ_WRITE);
    }

    public void deleteOfferUse(final ExecutionErrorAccumulator eea, final OfferUse offerUse, final BasePK deletedBy) {
        var contentControl = Session.getModelController(ContentControl.class);
        var customerControl = Session.getModelController(CustomerControl.class);
        var salesOrderControl = Session.getModelController(SalesOrderControl.class);
        var userControl = Session.getModelController(UserControl.class);
        var wishlistControl = Session.getModelController(WishlistControl.class);

        if(contentControl.countContentCollectionsByDefaultOfferUse(offerUse) == 0
                && contentControl.countContentCatalogsByDefaultOfferUse(offerUse) == 0
                && contentControl.countContentCategoriesByDefaultOfferUse(offerUse) == 0
                && customerControl.countCustomerTypesByDefaultOfferUse(offerUse) == 0
                && customerControl.countCustomersByInitialOfferUse(offerUse) == 0
                && salesOrderControl.countSalesOrdersByOfferUse(offerUse) == 0
                && salesOrderControl.countSalesOrderLinesByOfferUse(offerUse) == 0
                && userControl.countUserVisitsByOfferUse(offerUse) == 0
                && wishlistControl.countWishlistsByOfferUse(offerUse) == 0
                && wishlistControl.countWishlistLinesByOfferUse(offerUse) == 0) {
            var offerUseControl = Session.getModelController(OfferUseControl.class);

            offerUseControl.deleteOfferUse(offerUse, deletedBy);
        } else {
            handleExecutionError(CannotDeleteOfferUseInUseException.class, eea, ExecutionErrors.CannotDeleteOfferUseInUse.name());
        }
    }

}
