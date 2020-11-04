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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.transfer.UseNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
import com.echothree.model.control.offer.server.transfer.UseNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.OfferNameElementPK;
import com.echothree.model.data.offer.common.pk.UseNameElementPK;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
import com.echothree.model.data.offer.server.entity.UseNameElementDetail;
import com.echothree.model.data.offer.server.factory.OfferNameElementFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementFactory;
import com.echothree.model.data.offer.server.value.UseNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.UseNameElementDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UseNameElementControl
        extends BaseOfferControl {

    /** Creates a new instance of UseNameElementControl */
    public UseNameElementControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Use Name Elements
    // --------------------------------------------------------------------------------

    public UseNameElement createUseNameElement(String useNameElementName, Integer offset,
            Integer length, String validationPattern, BasePK createdBy) {
        UseNameElement useNameElement = UseNameElementFactory.getInstance().create();
        UseNameElementDetail useNameElementDetail = UseNameElementDetailFactory.getInstance().create(session,
                useNameElement, useNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        useNameElement = UseNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                useNameElement.getPrimaryKey());
        useNameElement.setActiveDetail(useNameElementDetail);
        useNameElement.setLastDetail(useNameElementDetail);
        useNameElement.store();

        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return useNameElement;
    }

    public long countUseNameElements() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM usenameelements, usenameelementdetails " +
                "WHERE usene_activedetailid = usenedt_usenameelementdetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UseNameElement */
    public UseNameElement getUseNameElementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UseNameElementPK(entityInstance.getEntityUniqueId());
        var useNameElement = UseNameElementFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return useNameElement;
    }

    public UseNameElement getUseNameElementByEntityInstance(EntityInstance entityInstance) {
        return getUseNameElementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UseNameElement getUseNameElementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseNameElementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public UseNameElement getUseNameElementByName(String useNameElementName, EntityPermission entityPermission) {
        UseNameElement useNameElement = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelements, usenameelementdetails " +
                        "WHERE usene_activedetailid = usenedt_usenameelementdetailid AND usenedt_usenameelementname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelements, usenameelementdetails " +
                        "WHERE usene_activedetailid = usenedt_usenameelementdetailid AND usenedt_usenameelementname = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = UseNameElementFactory.getInstance().prepareStatement(query);

            ps.setString(1, useNameElementName);

            useNameElement = UseNameElementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useNameElement;
    }

    public UseNameElement getUseNameElementByName(String useNameElementName) {
        return getUseNameElementByName(useNameElementName, EntityPermission.READ_ONLY);
    }

    public UseNameElement getUseNameElementByNameForUpdate(String useNameElementName) {
        return getUseNameElementByName(useNameElementName, EntityPermission.READ_WRITE);
    }

    public UseNameElementDetailValue getUseNameElementDetailValueForUpdate(UseNameElement useNameElement) {
        return useNameElement == null? null: useNameElement.getLastDetailForUpdate().getUseNameElementDetailValue().clone();
    }

    public UseNameElementDetailValue getUseNameElementDetailValueByNameForUpdate(String useNameElementName) {
        return getUseNameElementDetailValueForUpdate(getUseNameElementByNameForUpdate(useNameElementName));
    }

    private List<UseNameElement> getUseNameElements(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM usenameelements, usenameelementdetails " +
                    "WHERE usene_activedetailid = usenedt_usenameelementdetailid " +
                    "ORDER BY usenedt_offset";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM usenameelements, usenameelementdetails " +
                    "WHERE usene_activedetailid = usenedt_usenameelementdetailid " +
                    "FOR UPDATE";
        }

        PreparedStatement ps = UseNameElementFactory.getInstance().prepareStatement(query);

        return UseNameElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<UseNameElement> getUseNameElements() {
        return getUseNameElements(EntityPermission.READ_ONLY);
    }

    public List<UseNameElement> getUseNameElementsForUpdate() {
        return getUseNameElements(EntityPermission.READ_WRITE);
    }

    public UseNameElementTransfer getUseNameElementTransfer(UserVisit userVisit, UseNameElement useNameElement) {
        return getOfferTransferCaches(userVisit).getUseNameElementTransferCache().getUseNameElementTransfer(useNameElement);
    }

    public List<UseNameElementTransfer> getUseNameElementTransfers(UserVisit userVisit, Collection<UseNameElement> useNameElements) {
        List<UseNameElementTransfer> useNameElementTransfers = new ArrayList<>(useNameElements.size());
        UseNameElementTransferCache useNameElementTransferCache = getOfferTransferCaches(userVisit).getUseNameElementTransferCache();

        useNameElements.forEach((useNameElement) ->
                useNameElementTransfers.add(useNameElementTransferCache.getUseNameElementTransfer(useNameElement))
        );

        return useNameElementTransfers;
    }

    public List<UseNameElementTransfer> getUseNameElementTransfers(UserVisit userVisit) {
        return getUseNameElementTransfers(userVisit, getUseNameElements());
    }

    public void updateUseNameElementFromValue(UseNameElementDetailValue useNameElementDetailValue, BasePK updatedBy) {
        if(useNameElementDetailValue.hasBeenModified()) {
            UseNameElement useNameElement = UseNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useNameElementDetailValue.getUseNameElementPK());
            UseNameElementDetail useNameElementDetail = useNameElement.getActiveDetailForUpdate();

            useNameElementDetail.setThruTime(session.START_TIME_LONG);
            useNameElementDetail.store();

            UseNameElementPK useNameElementPK = useNameElementDetail.getUseNameElementPK();
            String useNameElementName = useNameElementDetailValue.getUseNameElementName();
            Integer offset = useNameElementDetailValue.getOffset();
            Integer length = useNameElementDetailValue.getLength();
            String validationPattern = useNameElementDetailValue.getValidationPattern();

            useNameElementDetail = UseNameElementDetailFactory.getInstance().create(useNameElementPK,
                    useNameElementName, offset, length, validationPattern, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            useNameElement.setActiveDetail(useNameElementDetail);
            useNameElement.setLastDetail(useNameElementDetail);

            sendEventUsingNames(useNameElementPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deleteUseNameElement(UseNameElement useNameElement, BasePK deletedBy) {
        deleteUseNameElementDescriptionsByUseNameElement(useNameElement, deletedBy);

        UseNameElementDetail useNameElementDetail = useNameElement.getLastDetailForUpdate();
        useNameElementDetail.setThruTime(session.START_TIME_LONG);
        useNameElement.setActiveDetail(null);
        useNameElement.store();

        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // --------------------------------------------------------------------------------

    public UseNameElementDescription createUseNameElementDescription(UseNameElement useNameElement, Language language,
            String description, BasePK createdBy) {
        UseNameElementDescription useNameElementDescription = UseNameElementDescriptionFactory.getInstance().create(session,
                useNameElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                useNameElementDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return useNameElementDescription;
    }

    private UseNameElementDescription getUseNameElementDescription(UseNameElement useNameElement, Language language,
            EntityPermission entityPermission) {
        UseNameElementDescription useNameElementDescription = null;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_lang_languageid = ? AND usened_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_lang_languageid = ? AND usened_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = UseNameElementDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, useNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            useNameElementDescription = UseNameElementDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useNameElementDescription;
    }

    public UseNameElementDescription getUseNameElementDescription(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescription(useNameElement, language, EntityPermission.READ_ONLY);
    }

    public UseNameElementDescription getUseNameElementDescriptionForUpdate(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescription(useNameElement, language, EntityPermission.READ_WRITE);
    }

    public UseNameElementDescriptionValue getUseNameElementDescriptionValue(UseNameElementDescription useNameElementDescription) {
        return useNameElementDescription == null? null: useNameElementDescription.getUseNameElementDescriptionValue().clone();
    }

    public UseNameElementDescriptionValue getUseNameElementDescriptionValueForUpdate(UseNameElement useNameElement, Language language) {
        return getUseNameElementDescriptionValue(getUseNameElementDescriptionForUpdate(useNameElement, language));
    }

    private List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement, EntityPermission entityPermission) {
        List<UseNameElementDescription> useNameElementDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions, languages " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_thrutime = ? AND usened_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usenameelementdescriptions " +
                        "WHERE usened_usene_usenameelementid = ? AND usened_thrutime = ? " +
                        "FOR UPDATE";
            }

            PreparedStatement ps = UseNameElementDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, useNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            useNameElementDescriptions = UseNameElementDescriptionFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useNameElementDescriptions;
    }

    public List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement) {
        return getUseNameElementDescriptionsByUseNameElement(useNameElement, EntityPermission.READ_ONLY);
    }

    public List<UseNameElementDescription> getUseNameElementDescriptionsByUseNameElementForUpdate(UseNameElement useNameElement) {
        return getUseNameElementDescriptionsByUseNameElement(useNameElement, EntityPermission.READ_WRITE);
    }

    public String getBestUseNameElementDescription(UseNameElement useNameElement, Language language) {
        String description;
        UseNameElementDescription useNameElementDescription = getUseNameElementDescription(useNameElement, language);

        if(useNameElementDescription == null && !language.getIsDefault()) {
            useNameElementDescription = getUseNameElementDescription(useNameElement, getPartyControl().getDefaultLanguage());
        }

        if(useNameElementDescription == null) {
            description = useNameElement.getLastDetail().getUseNameElementName();
        } else {
            description = useNameElementDescription.getDescription();
        }

        return description;
    }

    public UseNameElementDescriptionTransfer getUseNameElementDescriptionTransfer(UserVisit userVisit,
            UseNameElementDescription useNameElementDescription) {
        return getOfferTransferCaches(userVisit).getUseNameElementDescriptionTransferCache().getUseNameElementDescriptionTransfer(useNameElementDescription);
    }

    public List<UseNameElementDescriptionTransfer> getUseNameElementDescriptionTransfersByUseNameElement(UserVisit userVisit, UseNameElement useNameElement) {
        List<UseNameElementDescription> useNameElementDescriptions = getUseNameElementDescriptionsByUseNameElement(useNameElement);
        List<UseNameElementDescriptionTransfer> useNameElementDescriptionTransfers = new ArrayList<>(useNameElementDescriptions.size());
        UseNameElementDescriptionTransferCache useNameElementDescriptionTransferCache = getOfferTransferCaches(userVisit).getUseNameElementDescriptionTransferCache();

        useNameElementDescriptions.forEach((useNameElementDescription) ->
                useNameElementDescriptionTransfers.add(useNameElementDescriptionTransferCache.getUseNameElementDescriptionTransfer(useNameElementDescription))
        );

        return useNameElementDescriptionTransfers;
    }

    public void updateUseNameElementDescriptionFromValue(UseNameElementDescriptionValue useNameElementDescriptionValue,
            BasePK updatedBy) {
        if(useNameElementDescriptionValue.hasBeenModified()) {
            UseNameElementDescription useNameElementDescription = UseNameElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useNameElementDescriptionValue.getPrimaryKey());

            useNameElementDescription.setThruTime(session.START_TIME_LONG);
            useNameElementDescription.store();

            UseNameElement useNameElement = useNameElementDescription.getUseNameElement();
            Language language = useNameElementDescription.getLanguage();
            String description = useNameElementDescriptionValue.getDescription();

            useNameElementDescription = UseNameElementDescriptionFactory.getInstance().create(useNameElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(useNameElement.getPrimaryKey(), EventTypes.MODIFY.name(),
                    useNameElementDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteUseNameElementDescription(UseNameElementDescription useNameElementDescription, BasePK deletedBy) {
        useNameElementDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(useNameElementDescription.getUseNameElementPK(), EventTypes.MODIFY.name(),
                useNameElementDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteUseNameElementDescriptionsByUseNameElement(UseNameElement useNameElement, BasePK deletedBy) {
        List<UseNameElementDescription> useNameElementDescriptions = getUseNameElementDescriptionsByUseNameElementForUpdate(useNameElement);

        useNameElementDescriptions.forEach((useNameElementDescription) -> 
                deleteUseNameElementDescription(useNameElementDescription, deletedBy)
        );
    }

}
