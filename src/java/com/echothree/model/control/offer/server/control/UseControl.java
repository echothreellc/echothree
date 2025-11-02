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
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.offer.common.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseResultTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.factory.UseDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseDetailFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UseControl
        extends BaseOfferControl {

    /** Creates a new instance of UseControl */
    protected UseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Uses
    // --------------------------------------------------------------------------------

    public Use createUse(String useName, UseType useType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUse = getDefaultUse();
        var defaultFound = defaultUse != null;

        if(defaultFound && isDefault) {
            var defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

            defaultUseDetailValue.setIsDefault(false);
            updateUseFromValue(defaultUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var use = UseFactory.getInstance().create();
        var useDetail = UseDetailFactory.getInstance().create(use, useName, useType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, use.getPrimaryKey());
        use.setActiveDetail(useDetail);
        use.setLastDetail(useDetail);
        use.store();

        sendEvent(use.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return use;
    }

    public long countUses() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM uses, usedetails " +
                "WHERE use_activedetailid = usedt_usedetailid");
    }

    public long countUsesByUseType(final UseType useType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM uses, usedetails " +
                "WHERE use_activedetailid = usedt_usedetailid AND usedt_usetyp_usetypeid = ?",
                useType);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Use */
    public Use getUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UsePK(entityInstance.getEntityUniqueId());
        var use = UseFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return use;
    }

    public Use getUseByEntityInstance(EntityInstance entityInstance) {
        return getUseByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Use getUseByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Use> getUses(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ "
                    + "FROM uses, usedetails "
                    + "WHERE use_activedetailid = usedt_usedetailid "
                    + "ORDER BY usedt_sortorder, usedt_usename "
                    + "_LIMIT_";
        } else if (entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ "
                    + "FROM uses, usedetails "
                    + "WHERE use_activedetailid = usedt_usedetailid "
                    + "FOR UPDATE";
        }

        var ps = UseFactory.getInstance().prepareStatement(query);

        return UseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<Use> getUses() {
        return getUses(EntityPermission.READ_ONLY);
    }

    public List<Use> getUsesForUpdate() {
        return getUses(EntityPermission.READ_WRITE);
    }

    public Use getUseByName(String useName, EntityPermission entityPermission) {
        Use use;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usename = ? " +
                        "FOR UPDATE";
            }

            var ps = UseFactory.getInstance().prepareStatement(query);

            ps.setString(1, useName);

            use = UseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return use;
    }

    public Use getUseByName(String useName) {
        return getUseByName(useName, EntityPermission.READ_ONLY);
    }

    public Use getUseByNameForUpdate(String useName) {
        return getUseByName(useName, EntityPermission.READ_WRITE);
    }

    public UseDetailValue getUseDetailValueForUpdate(Use use) {
        return use == null? null: use.getLastDetailForUpdate().getUseDetailValue().clone();
    }

    public UseDetailValue getUseDetailValueByNameForUpdate(String useName) {
        return getUseDetailValueForUpdate(getUseByNameForUpdate(useName));
    }

    public Use getDefaultUse(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM uses, usedetails " +
                    "WHERE use_activedetailid = usedt_usedetailid AND usedt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM uses, usedetails " +
                    "WHERE use_activedetailid = usedt_usedetailid AND usedt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = UseFactory.getInstance().prepareStatement(query);

        return UseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }

    public Use getDefaultUse() {
        return getDefaultUse(EntityPermission.READ_ONLY);
    }

    public Use getDefaultUseForUpdate() {
        return getDefaultUse(EntityPermission.READ_WRITE);
    }

    public UseDetailValue getDefaultUseDetailValueForUpdate() {
        return getDefaultUseForUpdate().getLastDetailForUpdate().getUseDetailValue().clone();
    }

    private List<Use> getUsesByUseType(UseType useType, EntityPermission entityPermission) {
        List<Use> uses;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usetyp_usetypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid AND usedt_usetyp_usetypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = UseFactory.getInstance().prepareStatement(query);

            ps.setLong(1, useType.getPrimaryKey().getEntityId());

            uses = UseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return uses;
    }

    public List<Use> getUsesByUseType(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_ONLY);
    }

    public List<Use> getUsesByUseTypeForUpdate(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_WRITE);
    }

    public List<UseTransfer> getUseTransfers(UserVisit userVisit, Collection<Use> uses) {
        List<UseTransfer> useTransfers = new ArrayList<>(uses.size());

        uses.forEach((use) -> {
            useTransfers.add(getOfferTransferCaches(userVisit).getUseTransferCache().getUseTransfer(use));
        });

        return useTransfers;
    }

    public List<UseTransfer> getUseTransfers(UserVisit userVisit) {
        return getUseTransfers(userVisit, getUses());
    }

    public UseTransfer getUseTransfer(UserVisit userVisit, Use use) {
        return getOfferTransferCaches(userVisit).getUseTransferCache().getUseTransfer(use);
    }

    public UseChoicesBean getUseChoices(String defaultUseChoice, Language language, boolean allowNullChoice) {
        var uses = getUses();
        var size = uses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultUseChoice == null) {
                defaultValue = "";
            }
        }

        for(var use : uses) {
            var useDetail = use.getLastDetail();

            var label = getBestUseDescription(use, language);
            var value = useDetail.getUseName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultUseChoice != null && defaultUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new UseChoicesBean(labels, values, defaultValue);
    }

    private void updateUseFromValue(UseDetailValue useDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(useDetailValue.hasBeenModified()) {
            var use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useDetailValue.getUsePK());
            var useDetail = use.getActiveDetailForUpdate();

            useDetail.setThruTime(session.START_TIME_LONG);
            useDetail.store();

            var usePK = useDetail.getUsePK();
            var useName = useDetailValue.getUseName();
            var useTypePK = useDetailValue.getUseTypePK();
            var isDefault = useDetailValue.getIsDefault();
            var sortOrder = useDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultUse = getDefaultUse();
                var defaultFound = defaultUse != null && !defaultUse.equals(use);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

                    defaultUseDetailValue.setIsDefault(false);
                    updateUseFromValue(defaultUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            useDetail = UseDetailFactory.getInstance().create(usePK, useName, useTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            use.setActiveDetail(useDetail);
            use.setLastDetail(useDetail);

            sendEvent(usePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateUseFromValue(UseDetailValue useDetailValue, BasePK updatedBy) {
        updateUseFromValue(useDetailValue, true, updatedBy);
    }

    public void deleteUse(Use use, BasePK deletedBy) {
        var useControl = Session.getModelController(UseControl.class);
        var offerUseControl = Session.getModelController(OfferUseControl.class);

        useControl.deleteUseDescriptionsByUse(use, deletedBy);
        offerUseControl.deleteOfferUsesByUse(use, deletedBy);

        var useDetail = use.getLastDetailForUpdate();
        useDetail.setThruTime(session.START_TIME_LONG);
        use.setActiveDetail(null);
        use.store();

        // Check for default, and pick one if necessary
        var defaultUse = getDefaultUse();
        if(defaultUse == null) {
            var uses = getUsesForUpdate();

            if(!uses.isEmpty()) {
                var iter = uses.iterator();
                if(iter.hasNext()) {
                    defaultUse = iter.next();
                }
                var useDetailValue = Objects.requireNonNull(defaultUse).getLastDetailForUpdate().getUseDetailValue().clone();

                useDetailValue.setIsDefault(true);
                updateUseFromValue(useDetailValue, false, deletedBy);
            }
        }

        sendEvent(use.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteUses(List<Use> uses, BasePK deletedBy) {
        uses.forEach((use) -> 
                deleteUse(use, deletedBy)
        );
    }

    public void deleteUsesByUseType(UseType useType, BasePK deletedBy) {
        deleteUses(getUsesByUseTypeForUpdate(useType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Use Descriptions
    // --------------------------------------------------------------------------------

    public UseDescription createUseDescription(Use use, Language language, String description, BasePK createdBy) {
        var useDescription = UseDescriptionFactory.getInstance().create(use, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(use.getPrimaryKey(), EventTypes.MODIFY, useDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return useDescription;
    }

    private UseDescription getUseDescription(Use use, Language language, EntityPermission entityPermission) {
        UseDescription useDescription;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_lang_languageid = ? AND used_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_lang_languageid = ? AND used_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UseDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, use.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            useDescription = UseDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useDescription;
    }

    public UseDescription getUseDescription(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_ONLY);
    }

    public UseDescription getUseDescriptionForUpdate(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_WRITE);
    }

    public UseDescriptionValue getUseDescriptionValue(UseDescription useDescription) {
        return useDescription == null? null: useDescription.getUseDescriptionValue().clone();
    }

    public UseDescriptionValue getUseDescriptionValueForUpdate(Use use, Language language) {
        return getUseDescriptionValue(getUseDescriptionForUpdate(use, language));
    }

    private List<UseDescription> getUseDescriptionsByUse(Use use, EntityPermission entityPermission) {
        List<UseDescription> useDescriptions;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions, languages " +
                        "WHERE used_use_useid = ? AND used_thrutime = ? AND used_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usedescriptions " +
                        "WHERE used_use_useid = ? AND used_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UseDescriptionFactory.getInstance().prepareStatement(query);

            ps.setLong(1, use.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            useDescriptions = UseDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useDescriptions;
    }

    public List<UseDescription> getUseDescriptionsByUse(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_ONLY);
    }

    public List<UseDescription> getUseDescriptionsByUseForUpdate(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_WRITE);
    }

    public String getBestUseDescription(Use use, Language language) {
        String description;
        var useDescription = getUseDescription(use, language);

        if(useDescription == null && !language.getIsDefault()) {
            useDescription = getUseDescription(use, partyControl.getDefaultLanguage());
        }

        if(useDescription == null) {
            description = use.getLastDetail().getUseName();
        } else {
            description = useDescription.getDescription();
        }

        return description;
    }

    public UseDescriptionTransfer getUseDescriptionTransfer(UserVisit userVisit, UseDescription useDescription) {
        return getOfferTransferCaches(userVisit).getUseDescriptionTransferCache().getUseDescriptionTransfer(useDescription);
    }

    public List<UseDescriptionTransfer> getUseDescriptionTransfers(UserVisit userVisit, Use use) {
        var useDescriptions = getUseDescriptionsByUse(use);
        List<UseDescriptionTransfer> useDescriptionTransfers = new ArrayList<>(useDescriptions.size());

        useDescriptions.forEach((useDescription) -> {
            useDescriptionTransfers.add(getOfferTransferCaches(userVisit).getUseDescriptionTransferCache().getUseDescriptionTransfer(useDescription));
        });

        return useDescriptionTransfers;
    }

    public void updateUseDescriptionFromValue(UseDescriptionValue useDescriptionValue, BasePK updatedBy) {
        if(useDescriptionValue.hasBeenModified()) {
            var useDescription = UseDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useDescriptionValue.getPrimaryKey());

            useDescription.setThruTime(session.START_TIME_LONG);
            useDescription.store();

            var use = useDescription.getUse();
            var language = useDescription.getLanguage();
            var description = useDescriptionValue.getDescription();

            useDescription = UseDescriptionFactory.getInstance().create(use, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(use.getPrimaryKey(), EventTypes.MODIFY, useDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteUseDescription(UseDescription useDescription, BasePK deletedBy) {
        useDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(useDescription.getUsePK(), EventTypes.MODIFY,
                useDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteUseDescriptionsByUse(Use use, BasePK deletedBy) {
        var useDescriptions = getUseDescriptionsByUseForUpdate(use);

        useDescriptions.forEach((useDescription) -> 
                deleteUseDescription(useDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Use Searches
    // --------------------------------------------------------------------------------

    public List<UseResultTransfer> getUseResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var useResultTransfers = new ArrayList<UseResultTransfer>();
        var includeUse = false;

        var options = session.getOptions();
        if(options != null) {
            includeUse = options.contains(SearchOptions.UseResultIncludeUse);
        }

        try {
            var useControl = Session.getModelController(UseControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new UsePK(rs.getLong(1)));
                    var useDetail = use.getLastDetail();

                    useResultTransfers.add(new UseResultTransfer(useDetail.getUseName(),
                            includeUse ? useControl.getUseTransfer(userVisit, use) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return useResultTransfers;
    }

}
