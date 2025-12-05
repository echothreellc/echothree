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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.factory.OfferUseDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferUseFactory;
import com.echothree.model.data.offer.server.value.OfferUseDetailValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class OfferUseControl
        extends BaseOfferControl {

    /** Creates a new instance of OfferUseControl */
    protected OfferUseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Offer Uses
    // --------------------------------------------------------------------------------

    public OfferUse createOfferUse(Offer offer, Use use, Sequence salesOrderSequence, BasePK createdBy) {
        var offerUse = OfferUseFactory.getInstance().create();
        var offerUseDetail = OfferUseDetailFactory.getInstance().create(offerUse, offer, use, salesOrderSequence,
                session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        offerUse = OfferUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, offerUse.getPrimaryKey());
        offerUse.setActiveDetail(offerUseDetail);
        offerUse.setLastDetail(offerUseDetail);
        offerUse.store();

        sendEvent(offerUse.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return offerUse;
    }

    public long countOfferUsesByOffer(Offer offer) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM offeruses, offerusedetails "
                + "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ?",
                offer);
    }

    public long countOfferUsesByUse(Use use) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM offeruses, offerusedetails "
                + "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_use_useid = ?",
                use);
    }

    private List<OfferUse> getOfferUses(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM offeruses, offerusedetails, offers, offerdetails, uses, usedetails " +
                    "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid " +
                    "AND ofrusedt_use_useid = use_useid AND use_activedetailid = usedt_usedetailid " +
                    "AND ofrusedt_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid " +
                    "ORDER BY ofrdt_sortorder, ofrdt_offername, usedt_sortorder, usedt_usename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM offeruses, offerusedetails " +
                    "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? " +
                    "FOR UPDATE";
        }

        var ps = OfferUseFactory.getInstance().prepareStatement(query);

        return OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<OfferUse> getOfferUses() {
        return getOfferUses(EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesForUpdate() {
        return getOfferUses(EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesByOffer(Offer offer, EntityPermission entityPermission) {
        List<OfferUse> offerUses;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, uses, usedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? " +
                        "AND ofrusedt_use_useid = use_useid AND use_activedetailid = usedt_usedetailid " +
                        "ORDER BY usedt_sortorder, usedt_usename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? " +
                        "FOR UPDATE";
            }

            var ps = OfferUseFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offer.getPrimaryKey().getEntityId());

            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerUses;
    }

    public List<OfferUse> getOfferUsesByOffer(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesByOfferForUpdate(Offer offer) {
        return getOfferUsesByOffer(offer, EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesByUse(Use use, EntityPermission entityPermission) {
        List<OfferUse> offerUses;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, offers, offerdetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_use_useid = ? " +
                        "AND ofrusedt_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid " +
                        "ORDER BY ofrdt_sortorder, ofrdt_offername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_use_useid = ? " +
                        "FOR UPDATE";
            }

            var ps = OfferUseFactory.getInstance().prepareStatement(query);

            ps.setLong(1, use.getPrimaryKey().getEntityId());

            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerUses;
    }

    public List<OfferUse> getOfferUsesByUse(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesByUseForUpdate(Use use) {
        return getOfferUsesByUse(use, EntityPermission.READ_WRITE);
    }

    private List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence, EntityPermission entityPermission) {
        List<OfferUse> offerUses;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails, sequences, sequencedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_salesordersequenceid = ? " +
                        "AND ofrusedt_salesordersequenceid = sq_sequenceid AND sq_activedetailid = sqdt_sequencedetailid " +
                        "ORDER BY sqdt_sortorder, sqdt_sequencename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_salesordersequenceid = ? " +
                        "FOR UPDATE";
            }

            var ps = OfferUseFactory.getInstance().prepareStatement(query);

            ps.setLong(1, salesOrderSequence.getPrimaryKey().getEntityId());

            offerUses = OfferUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerUses;
    }

    public List<OfferUse> getOfferUsesBySalesOrderSequence(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_ONLY);
    }

    public List<OfferUse> getOfferUsesBySalesOrderSequenceForUpdate(Sequence salesOrderSequence) {
        return getOfferUsesBySalesOrderSequence(salesOrderSequence, EntityPermission.READ_WRITE);
    }

    public OfferUse getOfferUse(Offer offer, Use use, EntityPermission entityPermission) {
        OfferUse offerUse;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? AND ofrusedt_use_useid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM offeruses, offerusedetails " +
                        "WHERE ofruse_activedetailid = ofrusedt_offerusedetailid AND ofrusedt_ofr_offerid = ? AND ofrusedt_use_useid = ? " +
                        "FOR UPDATE";
            }

            var ps = OfferUseFactory.getInstance().prepareStatement(query);

            ps.setLong(1, offer.getPrimaryKey().getEntityId());
            ps.setLong(2, use.getPrimaryKey().getEntityId());

            offerUse = OfferUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return offerUse;
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
            var offerUse = OfferUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    offerUseDetailValue.getOfferUsePK());
            var offerUseDetail = offerUse.getActiveDetailForUpdate();

            offerUseDetail.setThruTime(session.getStartTime());
            offerUseDetail.store();

            var offerUsePK = offerUseDetail.getOfferUsePK();
            var offerPK = offerUseDetail.getOfferPK();
            var usePK = offerUseDetail.getUsePK();
            var sequencePK = offerUseDetailValue.getSalesOrderSequencePK();

            offerUseDetail = OfferUseDetailFactory.getInstance().create(offerUsePK, offerPK, usePK, sequencePK,
                    session.getStartTime(), Session.MAX_TIME_LONG);

            offerUse.setActiveDetail(offerUseDetail);
            offerUse.setLastDetail(offerUseDetail);

            sendEvent(offerUsePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteOfferUse(OfferUse offerUse, BasePK deletedBy) {
        var sourceControl = Session.getModelController(SourceControl.class);

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
