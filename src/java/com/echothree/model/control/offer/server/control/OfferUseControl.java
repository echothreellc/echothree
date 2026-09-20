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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.transfer.OfferUseTransferCache;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.factory.OfferUseDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferUseFactory;
import com.echothree.model.data.offer.server.value.OfferUseDetailValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_USES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_USE_DETAILS_OFFER_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_USE_DETAILS_USE_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.USES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.offer.OfferDetails.OfferDetails;
import static com.echothree.model.jooq.server.tables.offer.OfferUseDetails.OfferUseDetails;
import static com.echothree.model.jooq.server.tables.offer.OfferUses.OfferUses;
import static com.echothree.model.jooq.server.tables.offer.Offers.Offers;
import static com.echothree.model.jooq.server.tables.offer.UseDetails.UseDetails;
import static com.echothree.model.jooq.server.tables.offer.Uses.Uses;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class OfferUseControl
        extends BaseModelControl {

    @Inject
    private SourceControl sourceControl;

    /** Creates a new instance of OfferUseControl */
    protected OfferUseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Offer Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected OfferUseDetailFactory offerUseDetailFactory;

    @Inject
    private OfferUseFactory offerUseFactory;

    @Inject
    private OfferUseTransferCache offerUseTransferCache;

    public OfferUse createOfferUse(Offer offer, Use use, Sequence salesOrderSequence, BasePK createdBy) {
        var offerUse = offerUseFactory.create();
        var offerUseDetail = offerUseDetailFactory.create(offerUse, offer, use, salesOrderSequence,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        offerUse = offerUseFactory.getEntityFromPK(EntityPermission.READ_WRITE, offerUse.getPrimaryKey());
        offerUse.setActiveDetail(offerUseDetail);
        offerUse.setLastDetail(offerUseDetail);
        offerUse.store();

        sendEvent(offerUse.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return offerUse;
    }

    public long countOfferUses() {
        return session.getDslContext()
                .selectCount()
                .from(OfferUses)
                .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferUsesByOffer(Offer offer) {
        return session.getDslContext()
                .selectCount()
                .from(OfferUses)
                .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                .where(OfferUseDetails.OFFER.eq(offer.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferUsesByUse(Use use) {
        return session.getDslContext()
                .selectCount()
                .from(OfferUses)
                .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                .where(OfferUseDetails.USE.eq(use.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OfferUse> getOfferUses(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .join(Offers).onKey(OFFER_USE_DETAILS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .join(Uses).onKey(OFFER_USE_DETAILS_USE_FK)
                    .join(UseDetails).onKey(USES_LAST_DETAIL_FK)
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME, UseDetails.SORT_ORDER, UseDetails.USE_NAME),
                    OfferUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .forUpdate();
        };

        return offerUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferUse> getOfferUses() {
        return getOfferUses(EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesForUpdate() {
        return getOfferUses(EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesByOffer(Offer offer, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .join(Uses).onKey(OFFER_USE_DETAILS_USE_FK)
                    .join(UseDetails).onKey(USES_LAST_DETAIL_FK)
                    .where(OfferUseDetails.OFFER.eq(offer.getPrimaryKey()))
                    .orderBy(UseDetails.SORT_ORDER, UseDetails.USE_NAME),
                    OfferUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .where(OfferUseDetails.OFFER.eq(offer.getPrimaryKey()))
                    .forUpdate();
        };

        return offerUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferUse> getOfferUsesByOffer(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesByOfferForUpdate(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesByUse(Use use, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .join(Offers).onKey(OFFER_USE_DETAILS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferUseDetails.USE.eq(use.getPrimaryKey()))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .where(OfferUseDetails.USE.eq(use.getPrimaryKey()))
                    .forUpdate();
        };

        return offerUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferUse> getOfferUsesByUse(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesByUseForUpdate(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .join(Offers).onKey(OFFER_USE_DETAILS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .join(Uses).onKey(OFFER_USE_DETAILS_USE_FK)
                    .join(UseDetails).onKey(USES_LAST_DETAIL_FK)
                    .where(OfferUseDetails.SALES_ORDER_SEQUENCE.eq(salesOrderSequence.getPrimaryKey()))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME, UseDetails.SORT_ORDER, UseDetails.USE_NAME),
                    OfferUseFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferUses.fields())
                    .from(OfferUses)
                    .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                    .where(OfferUseDetails.SALES_ORDER_SEQUENCE.eq(salesOrderSequence.getPrimaryKey()))
                    .forUpdate();
        };

        return offerUseFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesBySalesOrderSequenceForUpdate(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_WRITE);
    }

    public OfferUse getOfferUse(Offer offer, Use use, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferUses.fields())
                .from(OfferUses)
                .join(OfferUseDetails).onKey(OFFER_USES_ACTIVE_DETAIL_FK)
                .where(OfferUseDetails.OFFER.eq(offer.getPrimaryKey()),
                        OfferUseDetails.USE.eq(use.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerUseFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferUse getOfferUse(Offer offer, Use use) {
        return getOfferUse(offer, use, EntityPermission.READ_ONLY);
    }

    public OfferUse getOfferUseForUpdate(Offer offer, Use use) {
        return getOfferUse(offer, use, EntityPermission.READ_WRITE);
    }

    public OfferUseDetailValue getOfferUseDetailValueForUpdate(OfferUse offerUse) {
        return offerUse == null? null: offerUse.getLastDetailForUpdate().getOfferUseDetailValue().clone();
    }

    public OfferUseDetailValue getOfferUseDetailValueByNameForUpdate(Offer offer, Use use) {
        return getOfferUseDetailValueForUpdate(getOfferUseForUpdate(offer, use));
    }

    public List<OfferUseTransfer> getOfferUseTransfers(UserVisit userVisit, Collection<OfferUse> offerUses) {
        List<OfferUseTransfer> offerUseTransfers = new ArrayList<>(offerUses.size());

        offerUses.forEach((offerUse) ->
                offerUseTransfers.add(offerUseTransferCache.getOfferUseTransfer(userVisit, offerUse))
        );

        return offerUseTransfers;
    }

    public List<OfferUseTransfer> getOfferUseTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferUseTransfers(userVisit, getOfferUsesByOffer(offer));
    }

    public OfferUseTransfer getOfferUseTransfer(UserVisit userVisit, OfferUse offerUse) {
        return offerUseTransferCache.getOfferUseTransfer(userVisit, offerUse);
    }

    public void updateOfferUseFromValue(OfferUseDetailValue offerUseDetailValue, BasePK updatedBy) {
        if(offerUseDetailValue.hasBeenModified()) {
            var offerUse = offerUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    offerUseDetailValue.getOfferUsePK());
            var offerUseDetail = offerUse.getActiveDetailForUpdate();

            offerUseDetail.setThruTime(session.getStartTime());
            offerUseDetail.store();

            var offerUsePK = offerUseDetail.getOfferUsePK();
            var offerPK = offerUseDetail.getOfferPK();
            var usePK = offerUseDetail.getUsePK();
            var sequencePK = offerUseDetailValue.getSalesOrderSequencePK();

            offerUseDetail = offerUseDetailFactory.create(offerUsePK, offerPK, usePK, sequencePK,
                    session.getStartTime(), Session.MAX_TIME);

            offerUse.setActiveDetail(offerUseDetail);
            offerUse.setLastDetail(offerUseDetail);

            sendEvent(offerUsePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteOfferUse(OfferUse offerUse, BasePK deletedBy) {

        sourceControl.deleteSourcesByOfferUse(offerUse, deletedBy);

        var offerUseDetail = offerUse.getLastDetailForUpdate();
        offerUseDetail.setThruTime(session.getStartTime());
        offerUse.setActiveDetail(null);
        offerUse.store();

        sendEvent(offerUse.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOfferUses(List<OfferUse> offerUses, BasePK deletedBy) {
        offerUses.forEach((offerUse) -> 
                deleteOfferUse(offerUse, deletedBy)
        );
    }

    public void deleteOfferUsesByOffer(Offer offer, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesByOfferForUpdate(offer), deletedBy);
    }

    public void deleteOfferUsesByUse(Use use, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesByUseForUpdate(use), deletedBy);
    }

    public void deleteOfferUsesBySalesOrderSequence(Sequence salesOrderSequence, BasePK deletedBy) {
        deleteOfferUses(getOfferUsesBySalesOrderSequenceForUpdate(salesOrderSequence), deletedBy);
    }

}
