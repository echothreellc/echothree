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
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.offer.common.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.factory.UseDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseDetailFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
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
import java.util.Iterator;
import java.util.List;

public class UseControl
        extends BaseOfferControl {

    /** Creates a new instance of UseControl */
    public UseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Uses
    // --------------------------------------------------------------------------------

    public Use createUse(String useName, UseType useType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Use defaultUse = getDefaultUse();
        boolean defaultFound = defaultUse != null;

        if(defaultFound && isDefault) {
            UseDetailValue defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

            defaultUseDetailValue.setIsDefault(Boolean.FALSE);
            updateUseFromValue(defaultUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        Use use = UseFactory.getInstance().create();
        UseDetail useDetail = UseDetailFactory.getInstance().create(use, useName, useType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, use.getPrimaryKey());
        use.setActiveDetail(useDetail);
        use.setLastDetail(useDetail);
        use.store();

        sendEventUsingNames(use.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return use;
    }

    public long countUses() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM uses, usedetails " +
                        "WHERE use_activedetailid = usedt_usedetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Use */
    public Use getUseByEntityInstance(EntityInstance entityInstance) {
        UsePK pk = new UsePK(entityInstance.getEntityUniqueId());
        Use use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return use;
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

        PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);

        return UseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<Use> getUses() {
        return getUses(EntityPermission.READ_ONLY);
    }

    public List<Use> getUsesForUpdate() {
        return getUses(EntityPermission.READ_WRITE);
    }

    private Use getUseByName(String useName, EntityPermission entityPermission) {
        Use use = null;

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

            PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);

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

    private Use getDefaultUse(EntityPermission entityPermission) {
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

        PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);

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
        List<Use> uses = null;

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

            PreparedStatement ps = UseFactory.getInstance().prepareStatement(query);

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

        uses.stream().forEach((use) -> {
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
        List<Use> uses = getUses();
        int size = uses.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultUseChoice == null) {
                defaultValue = "";
            }
        }

        for(Use use : uses) {
            UseDetail useDetail = use.getLastDetail();

            String label = getBestUseDescription(use, language);
            String value = useDetail.getUseName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultUseChoice == null? false: defaultUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new UseChoicesBean(labels, values, defaultValue);
    }

    private void updateUseFromValue(UseDetailValue useDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(useDetailValue.hasBeenModified()) {
            Use use = UseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useDetailValue.getUsePK());
            UseDetail useDetail = use.getActiveDetailForUpdate();

            useDetail.setThruTime(session.START_TIME_LONG);
            useDetail.store();

            UsePK usePK = useDetail.getUsePK();
            String useName = useDetailValue.getUseName();
            UseTypePK useTypePK = useDetailValue.getUseTypePK();
            Boolean isDefault = useDetailValue.getIsDefault();
            Integer sortOrder = useDetailValue.getSortOrder();

            if(checkDefault) {
                Use defaultUse = getDefaultUse();
                boolean defaultFound = defaultUse != null && !defaultUse.equals(use);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UseDetailValue defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

                    defaultUseDetailValue.setIsDefault(Boolean.FALSE);
                    updateUseFromValue(defaultUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            useDetail = UseDetailFactory.getInstance().create(usePK, useName, useTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            use.setActiveDetail(useDetail);
            use.setLastDetail(useDetail);

            sendEventUsingNames(usePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateUseFromValue(UseDetailValue useDetailValue, BasePK updatedBy) {
        updateUseFromValue(useDetailValue, true, updatedBy);
    }

    public void deleteUse(Use use, BasePK deletedBy) {
        var useControl = (UseControl)Session.getModelController(UseControl.class);
        var offerUseControl = (OfferUseControl)Session.getModelController(OfferUseControl.class);

        useControl.deleteUseDescriptionsByUse(use, deletedBy);
        offerUseControl.deleteOfferUsesByUse(use, deletedBy);

        UseDetail useDetail = use.getLastDetailForUpdate();
        useDetail.setThruTime(session.START_TIME_LONG);
        use.setActiveDetail(null);
        use.store();

        // Check for default, and pick one if necessary
        Use defaultUse = getDefaultUse();
        if(defaultUse == null) {
            List<Use> uses = getUsesForUpdate();

            if(!uses.isEmpty()) {
                Iterator<Use> iter = uses.iterator();
                if(iter.hasNext()) {
                    defaultUse = iter.next();
                }
                UseDetailValue useDetailValue = defaultUse.getLastDetailForUpdate().getUseDetailValue().clone();

                useDetailValue.setIsDefault(Boolean.TRUE);
                updateUseFromValue(useDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(use.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteUses(List<Use> uses, BasePK deletedBy) {
        uses.stream().forEach((use) -> {
            deleteUse(use, deletedBy);
        });
    }

    public void deleteUsesByUseType(UseType useType, BasePK deletedBy) {
        deleteUses(getUsesByUseTypeForUpdate(useType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Use Descriptions
    // --------------------------------------------------------------------------------

    public UseDescription createUseDescription(Use use, Language language, String description, BasePK createdBy) {
        UseDescription useDescription = UseDescriptionFactory.getInstance().create(use, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(use.getPrimaryKey(), EventTypes.MODIFY.name(), useDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);

        return useDescription;
    }

    private UseDescription getUseDescription(Use use, Language language, EntityPermission entityPermission) {
        UseDescription useDescription = null;

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

            PreparedStatement ps = UseDescriptionFactory.getInstance().prepareStatement(query);

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
        List<UseDescription> useDescriptions = null;

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

            PreparedStatement ps = UseDescriptionFactory.getInstance().prepareStatement(query);

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
        UseDescription useDescription = getUseDescription(use, language);

        if(useDescription == null && !language.getIsDefault()) {
            useDescription = getUseDescription(use, getPartyControl().getDefaultLanguage());
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
        List<UseDescription> useDescriptions = getUseDescriptionsByUse(use);
        List<UseDescriptionTransfer> useDescriptionTransfers = new ArrayList<>(useDescriptions.size());

        useDescriptions.stream().forEach((useDescription) -> {
            useDescriptionTransfers.add(getOfferTransferCaches(userVisit).getUseDescriptionTransferCache().getUseDescriptionTransfer(useDescription));
        });

        return useDescriptionTransfers;
    }

    public void updateUseDescriptionFromValue(UseDescriptionValue useDescriptionValue, BasePK updatedBy) {
        if(useDescriptionValue.hasBeenModified()) {
            UseDescription useDescription = UseDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    useDescriptionValue.getPrimaryKey());

            useDescription.setThruTime(session.START_TIME_LONG);
            useDescription.store();

            Use use = useDescription.getUse();
            Language language = useDescription.getLanguage();
            String description = useDescriptionValue.getDescription();

            useDescription = UseDescriptionFactory.getInstance().create(use, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(use.getPrimaryKey(), EventTypes.MODIFY.name(), useDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteUseDescription(UseDescription useDescription, BasePK deletedBy) {
        useDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(useDescription.getUsePK(), EventTypes.MODIFY.name(),
                useDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteUseDescriptionsByUse(Use use, BasePK deletedBy) {
        List<UseDescription> useDescriptions = getUseDescriptionsByUseForUpdate(use);

        useDescriptions.stream().forEach((useDescription) -> {
            deleteUseDescription(useDescription, deletedBy);
        });
    }

}
