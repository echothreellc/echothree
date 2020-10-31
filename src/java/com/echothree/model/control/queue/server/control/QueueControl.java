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

package com.echothree.model.control.queue.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.queue.common.choice.QueueTypeChoicesBean;
import com.echothree.model.control.queue.common.transfer.QueueTypeDescriptionTransfer;
import com.echothree.model.control.queue.common.transfer.QueueTypeTransfer;
import com.echothree.model.control.queue.common.transfer.QueuedEntityTransfer;
import com.echothree.model.control.queue.server.transfer.QueueTransferCaches;
import com.echothree.model.control.queue.server.transfer.QueueTypeDescriptionTransferCache;
import com.echothree.model.control.queue.server.transfer.QueueTypeTransferCache;
import com.echothree.model.control.queue.server.transfer.QueuedEntityTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDescription;
import com.echothree.model.data.queue.server.entity.QueueTypeDetail;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.model.data.queue.server.factory.QueueTypeDescriptionFactory;
import com.echothree.model.data.queue.server.factory.QueueTypeDetailFactory;
import com.echothree.model.data.queue.server.factory.QueueTypeFactory;
import com.echothree.model.data.queue.server.factory.QueuedEntityFactory;
import com.echothree.model.data.queue.server.value.QueueTypeDescriptionValue;
import com.echothree.model.data.queue.server.value.QueueTypeDetailValue;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueueControl
        extends BaseModelControl {
    
    /** Creates a new instance of QueueControl */
    public QueueControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Transfer Caches
    // --------------------------------------------------------------------------------
    
    private QueueTransferCaches queueTransferCaches;
    
    public QueueTransferCaches getQueueTransferCaches(UserVisit userVisit) {
        if(queueTransferCaches == null) {
            queueTransferCaches = new QueueTransferCaches(userVisit, this);
        }
        
        return queueTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------

    public QueueType createQueueType(String queueTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        QueueType defaultQueueType = getDefaultQueueType();
        boolean defaultFound = defaultQueueType != null;

        if(defaultFound && isDefault) {
            QueueTypeDetailValue defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

            defaultQueueTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        QueueType queueType = QueueTypeFactory.getInstance().create();
        QueueTypeDetail queueTypeDetail = QueueTypeDetailFactory.getInstance().create(queueType, queueTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        queueType = QueueTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, queueType.getPrimaryKey());
        queueType.setActiveDetail(queueTypeDetail);
        queueType.setLastDetail(queueTypeDetail);
        queueType.store();

        sendEventUsingNames(queueType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return queueType;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.QueueType */
    public QueueType getQueueTypeByEntityInstance(EntityInstance entityInstance) {
        QueueTypePK pk = new QueueTypePK(entityInstance.getEntityUniqueId());
        QueueType queueType = QueueTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return queueType;
    }

    private static final Map<EntityPermission, String> getQueueTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "AND qtypdt_queuetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "AND qtypdt_queuetypename = ? " +
                "FOR UPDATE");
        getQueueTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private QueueType getQueueTypeByName(String queueTypeName, EntityPermission entityPermission) {
        return QueueTypeFactory.getInstance().getEntityFromQuery(entityPermission, getQueueTypeByNameQueries, queueTypeName);
    }

    public QueueType getQueueTypeByName(String queueTypeName) {
        return getQueueTypeByName(queueTypeName, EntityPermission.READ_ONLY);
    }

    public QueueType getQueueTypeByNameForUpdate(String queueTypeName) {
        return getQueueTypeByName(queueTypeName, EntityPermission.READ_WRITE);
    }

    public QueueTypeDetailValue getQueueTypeDetailValueForUpdate(QueueType queueType) {
        return queueType == null? null: queueType.getLastDetailForUpdate().getQueueTypeDetailValue().clone();
    }

    public QueueTypeDetailValue getQueueTypeDetailValueByNameForUpdate(String queueTypeName) {
        return getQueueTypeDetailValueForUpdate(getQueueTypeByNameForUpdate(queueTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultQueueTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "AND qtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "AND qtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultQueueTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private QueueType getDefaultQueueType(EntityPermission entityPermission) {
        return QueueTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultQueueTypeQueries);
    }

    public QueueType getDefaultQueueType() {
        return getDefaultQueueType(EntityPermission.READ_ONLY);
    }

    public QueueType getDefaultQueueTypeForUpdate() {
        return getDefaultQueueType(EntityPermission.READ_WRITE);
    }

    public QueueTypeDetailValue getDefaultQueueTypeDetailValueForUpdate() {
        return getDefaultQueueTypeForUpdate().getLastDetailForUpdate().getQueueTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getQueueTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "ORDER BY qtypdt_sortorder, qtypdt_queuetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM queuetypes, queuetypedetails " +
                "WHERE qtyp_activedetailid = qtypdt_queuetypedetailid " +
                "FOR UPDATE");
        getQueueTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<QueueType> getQueueTypes(EntityPermission entityPermission) {
        return QueueTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getQueueTypesQueries);
    }

    public List<QueueType> getQueueTypes() {
        return getQueueTypes(EntityPermission.READ_ONLY);
    }

    public List<QueueType> getQueueTypesForUpdate() {
        return getQueueTypes(EntityPermission.READ_WRITE);
    }

   public QueueTypeTransfer getQueueTypeTransfer(UserVisit userVisit, QueueType queueType) {
        return getQueueTransferCaches(userVisit).getQueueTypeTransferCache().getQueueTypeTransfer(queueType);
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit, Collection<QueueType> queueTypes) {
        List<QueueTypeTransfer> queueTypeTransfers = new ArrayList<>(queueTypes.size());
        QueueTypeTransferCache queueTypeTransferCache = getQueueTransferCaches(userVisit).getQueueTypeTransferCache();

        queueTypes.forEach((queueType) ->
                queueTypeTransfers.add(queueTypeTransferCache.getQueueTypeTransfer(queueType))
        );

        return queueTypeTransfers;
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit) {
        return getQueueTypeTransfers(userVisit, getQueueTypes());
    }

    public QueueTypeChoicesBean getQueueTypeChoices(String defaultQueueTypeChoice, Language language, boolean allowNullChoice) {
        List<QueueType> queueTypes = getQueueTypes();
        int size = queueTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultQueueTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(QueueType queueType: queueTypes) {
            QueueTypeDetail queueTypeDetail = queueType.getLastDetail();

            String label = getBestQueueTypeDescription(queueType, language);
            String value = queueTypeDetail.getQueueTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultQueueTypeChoice != null && defaultQueueTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && queueTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new QueueTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(queueTypeDetailValue.hasBeenModified()) {
            QueueType queueType = QueueTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     queueTypeDetailValue.getQueueTypePK());
            QueueTypeDetail queueTypeDetail = queueType.getActiveDetailForUpdate();

            queueTypeDetail.setThruTime(session.START_TIME_LONG);
            queueTypeDetail.store();

            QueueTypePK queueTypePK = queueTypeDetail.getQueueTypePK(); // Not updated
            String queueTypeName = queueTypeDetailValue.getQueueTypeName();
            Boolean isDefault = queueTypeDetailValue.getIsDefault();
            Integer sortOrder = queueTypeDetailValue.getSortOrder();

            if(checkDefault) {
                QueueType defaultQueueType = getDefaultQueueType();
                boolean defaultFound = defaultQueueType != null && !defaultQueueType.equals(queueType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    QueueTypeDetailValue defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

                    defaultQueueTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            queueTypeDetail = QueueTypeDetailFactory.getInstance().create(queueTypePK, queueTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            queueType.setActiveDetail(queueTypeDetail);
            queueType.setLastDetail(queueTypeDetail);

            sendEventUsingNames(queueTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, BasePK updatedBy) {
        updateQueueTypeFromValue(queueTypeDetailValue, true, updatedBy);
    }

    private void deleteQueueType(QueueType queueType, boolean checkDefault, BasePK deletedBy) {
        QueueTypeDetail queueTypeDetail = queueType.getLastDetailForUpdate();

        removeQueuedEntitiesByQueueType(queueType);
        deleteQueueTypeDescriptionsByQueueType(queueType, deletedBy);

        queueTypeDetail.setThruTime(session.START_TIME_LONG);
        queueType.setActiveDetail(null);
        queueType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            QueueType defaultQueueType = getDefaultQueueType();

            if(defaultQueueType == null) {
                List<QueueType> queueTypes = getQueueTypesForUpdate();

                if(!queueTypes.isEmpty()) {
                    Iterator<QueueType> iter = queueTypes.iterator();
                    if(iter.hasNext()) {
                        defaultQueueType = iter.next();
                    }
                    QueueTypeDetailValue queueTypeDetailValue = defaultQueueType.getLastDetailForUpdate().getQueueTypeDetailValue().clone();

                    queueTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateQueueTypeFromValue(queueTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(queueType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteQueueType(QueueType queueType, BasePK deletedBy) {
        deleteQueueType(queueType, true, deletedBy);
    }

    private void deleteQueueTypes(List<QueueType> queueTypes, boolean checkDefault, BasePK deletedBy) {
        queueTypes.stream().forEach((queueType) -> {
            deleteQueueType(queueType, checkDefault, deletedBy);
        });
    }

    public void deleteQueueTypes(List<QueueType> queueTypes, BasePK deletedBy) {
        deleteQueueTypes(queueTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------

    public QueueTypeDescription createQueueTypeDescription(QueueType queueType, Language language, String description, BasePK createdBy) {
        QueueTypeDescription queueTypeDescription = QueueTypeDescriptionFactory.getInstance().create(queueType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(queueType.getPrimaryKey(), EventTypes.MODIFY.name(), queueTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return queueTypeDescription;
    }

    private static final Map<EntityPermission, String> getQueueTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM queuetypedescriptions " +
                "WHERE qtypd_qtyp_queuetypeid = ? AND qtypd_lang_languageid = ? AND qtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM queuetypedescriptions " +
                "WHERE qtypd_qtyp_queuetypeid = ? AND qtypd_lang_languageid = ? AND qtypd_thrutime = ? " +
                "FOR UPDATE");
        getQueueTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private QueueTypeDescription getQueueTypeDescription(QueueType queueType, Language language, EntityPermission entityPermission) {
        return QueueTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getQueueTypeDescriptionQueries,
                queueType, language, Session.MAX_TIME);
    }

    public QueueTypeDescription getQueueTypeDescription(QueueType queueType, Language language) {
        return getQueueTypeDescription(queueType, language, EntityPermission.READ_ONLY);
    }

    public QueueTypeDescription getQueueTypeDescriptionForUpdate(QueueType queueType, Language language) {
        return getQueueTypeDescription(queueType, language, EntityPermission.READ_WRITE);
    }

    public QueueTypeDescriptionValue getQueueTypeDescriptionValue(QueueTypeDescription queueTypeDescription) {
        return queueTypeDescription == null? null: queueTypeDescription.getQueueTypeDescriptionValue().clone();
    }

    public QueueTypeDescriptionValue getQueueTypeDescriptionValueForUpdate(QueueType queueType, Language language) {
        return getQueueTypeDescriptionValue(getQueueTypeDescriptionForUpdate(queueType, language));
    }

    private static final Map<EntityPermission, String> getQueueTypeDescriptionsByQueueTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM queuetypedescriptions, languages " +
                "WHERE qtypd_qtyp_queuetypeid = ? AND qtypd_thrutime = ? AND qtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM queuetypedescriptions " +
                "WHERE qtypd_qtyp_queuetypeid = ? AND qtypd_thrutime = ? " +
                "FOR UPDATE");
        getQueueTypeDescriptionsByQueueTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<QueueTypeDescription> getQueueTypeDescriptionsByQueueType(QueueType queueType, EntityPermission entityPermission) {
        return QueueTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getQueueTypeDescriptionsByQueueTypeQueries,
                queueType, Session.MAX_TIME);
    }

    public List<QueueTypeDescription> getQueueTypeDescriptionsByQueueType(QueueType queueType) {
        return getQueueTypeDescriptionsByQueueType(queueType, EntityPermission.READ_ONLY);
    }

    public List<QueueTypeDescription> getQueueTypeDescriptionsByQueueTypeForUpdate(QueueType queueType) {
        return getQueueTypeDescriptionsByQueueType(queueType, EntityPermission.READ_WRITE);
    }

    public String getBestQueueTypeDescription(QueueType queueType, Language language) {
        String description;
        QueueTypeDescription queueTypeDescription = getQueueTypeDescription(queueType, language);

        if(queueTypeDescription == null && !language.getIsDefault()) {
            queueTypeDescription = getQueueTypeDescription(queueType, getPartyControl().getDefaultLanguage());
        }

        if(queueTypeDescription == null) {
            description = queueType.getLastDetail().getQueueTypeName();
        } else {
            description = queueTypeDescription.getDescription();
        }

        return description;
    }

    public QueueTypeDescriptionTransfer getQueueTypeDescriptionTransfer(UserVisit userVisit, QueueTypeDescription queueTypeDescription) {
        return getQueueTransferCaches(userVisit).getQueueTypeDescriptionTransferCache().getQueueTypeDescriptionTransfer(queueTypeDescription);
    }

    public List<QueueTypeDescriptionTransfer> getQueueTypeDescriptionTransfersByQueueType(UserVisit userVisit, QueueType queueType) {
        List<QueueTypeDescription> queueTypeDescriptions = getQueueTypeDescriptionsByQueueType(queueType);
        List<QueueTypeDescriptionTransfer> queueTypeDescriptionTransfers = new ArrayList<>(queueTypeDescriptions.size());
        QueueTypeDescriptionTransferCache queueTypeDescriptionTransferCache = getQueueTransferCaches(userVisit).getQueueTypeDescriptionTransferCache();

        queueTypeDescriptions.forEach((queueTypeDescription) ->
                queueTypeDescriptionTransfers.add(queueTypeDescriptionTransferCache.getQueueTypeDescriptionTransfer(queueTypeDescription))
        );

        return queueTypeDescriptionTransfers;
    }

    public void updateQueueTypeDescriptionFromValue(QueueTypeDescriptionValue queueTypeDescriptionValue, BasePK updatedBy) {
        if(queueTypeDescriptionValue.hasBeenModified()) {
            QueueTypeDescription queueTypeDescription = QueueTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    queueTypeDescriptionValue.getPrimaryKey());

            queueTypeDescription.setThruTime(session.START_TIME_LONG);
            queueTypeDescription.store();

            QueueType queueType = queueTypeDescription.getQueueType();
            Language language = queueTypeDescription.getLanguage();
            String description = queueTypeDescriptionValue.getDescription();

            queueTypeDescription = QueueTypeDescriptionFactory.getInstance().create(queueType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(queueType.getPrimaryKey(), EventTypes.MODIFY.name(), queueTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteQueueTypeDescription(QueueTypeDescription queueTypeDescription, BasePK deletedBy) {
        queueTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(queueTypeDescription.getQueueTypePK(), EventTypes.MODIFY.name(), queueTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteQueueTypeDescriptionsByQueueType(QueueType queueType, BasePK deletedBy) {
        List<QueueTypeDescription> queueTypeDescriptions = getQueueTypeDescriptionsByQueueTypeForUpdate(queueType);

        queueTypeDescriptions.forEach((queueTypeDescription) -> 
                deleteQueueTypeDescription(queueTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Queued Entities
    // --------------------------------------------------------------------------------
    
    public QueuedEntity createQueuedEntity(QueueType queueType, EntityInstance entityInstance) {
        return QueuedEntityFactory.getInstance().create(queueType, entityInstance, session.START_TIME_LONG);
    }

    public void createQueuedEntities(Collection<QueuedEntityValue> queuedEntities) {
        QueuedEntityFactory.getInstance().create(queuedEntities);
    }
    
    public Long countQueuedEntitiesByQueueType(final QueueType queueType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM queuedentities "
                + "WHERE qeni_qtyp_queuetypeid = ?",
                queueType);
    }

    public Long oldestQueuedEntityTimeByQueueType(final QueueType queueType) {
        return session.queryForLong(
                "SELECT MIN(qeni_time) "
                + "FROM queuedentities "
                + "WHERE qeni_qtyp_queuetypeid = ?",
                queueType);
    }

    public Long latestQueuedEntityTimeByQueueType(final QueueType queueType) {
        return session.queryForLong(
                "SELECT MAX(qeni_time) "
                + "FROM queuedentities "
                + "WHERE qeni_qtyp_queuetypeid = ?",
                queueType);
    }

    public Long countQueuedEntitiesByEntityInstance(final EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM queuedentities "
                + "WHERE qeni_eni_entityinstanceid = ?",
                entityInstance);
    }

    public Long countQueuedEntitiesByEntityType(final QueueType queueType, final EntityType entityType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM queuedentities, entityinstances "
                + "WHERE qeni_qtyp_queuetypeid = ? "
                + "AND qeni_eni_entityinstanceid = eni_entityinstanceid "
                + "AND eni_ent_entitytypeid = ?",
                queueType, entityType);
    }

    public boolean isQueueTypeUsedByQueuedEntities(QueueType queueType) {
        return countQueuedEntitiesByQueueType(queueType) != 0;
    }

    public boolean isEntityInstanceUsedByQueuedEntities(EntityInstance entityInstance) {
        return countQueuedEntitiesByEntityInstance(entityInstance) != 0;
    }

    private static final Map<EntityPermission, String> getQueuedEntitiesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM queuedentities "
                + "WHERE qeni_qtyp_queuetypeid = ? AND qeni_eni_entityinstanceid = ? "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM queuedentities "
                + "WHERE qeni_qtyp_queuetypeid = ? AND qeni_eni_entityinstanceid = ? "
                + "FOR UPDATE");
        getQueuedEntitiesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<QueuedEntity> getQueuedEntities(QueueType queueType, EntityInstance entityInstance, EntityPermission entityPermission) {
        return QueuedEntityFactory.getInstance().getEntitiesFromQuery(entityPermission, getQueuedEntitiesQueries,
                queueType, entityInstance);
    }

    public List<QueuedEntity> getQueuedEntities(QueueType queueType, EntityInstance entityInstance) {
        return getQueuedEntities(queueType, entityInstance, EntityPermission.READ_ONLY);
    }

    public List<QueuedEntity> getQueuedEntitiesForUpdate(QueueType queueType, EntityInstance entityInstance) {
        return getQueuedEntities(queueType, entityInstance, EntityPermission.READ_WRITE);
    }

    private List<QueuedEntity> getQueuedEntitiesByQueueType(QueueType queueType, EntityPermission entityPermission) {
        List<QueuedEntity> queuedEntities = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedentities " +
                        "WHERE qeni_qtyp_queuetypeid = ? " +
                        "ORDER BY qeni_queuedentityid " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedentities " +
                        "WHERE qeni_qtyp_queuetypeid = ? " +
                        "ORDER BY qeni_queuedentityid " +
                        "_LIMIT_ " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = QueuedEntityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, queueType.getPrimaryKey().getEntityId());
            
            queuedEntities = QueuedEntityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedEntities;
    }
    
    public List<QueuedEntity> getQueuedEntitiesByQueueType(QueueType queueType) {
        return getQueuedEntitiesByQueueType(queueType, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByQueueTypeForUpdate(QueueType queueType) {
        return getQueuedEntitiesByQueueType(queueType, EntityPermission.READ_WRITE);
    }
    
    private List<QueuedEntity> getQueuedEntitiesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<QueuedEntity> queuedEntities = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedentities " +
                        "WHERE qeni_eni_entityinstanceid = ? " +
                        "ORDER BY qeni_queuedentityid " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedentities " +
                        "WHERE qeni_eni_entityinstanceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = QueuedEntityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            
            queuedEntities = QueuedEntityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedEntities;
    }
    
    public List<QueuedEntity> getQueuedEntitiesByEntityInstance(EntityInstance entityInstance) {
        return getQueuedEntitiesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getQueuedEntitiesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public QueuedEntityTransfer getQueuedEntityTransfer(UserVisit userVisit, QueuedEntity queuedEntity) {
        return getQueueTransferCaches(userVisit).getQueuedEntityTransferCache().getQueuedEntityTransfer(queuedEntity);
    }
    
    public List<QueuedEntityTransfer> getQueuedEntityTransfers(UserVisit userVisit, List<QueuedEntity> queuedEntities) {
        List<QueuedEntityTransfer> queuedEntityTransfers = new ArrayList<>(queuedEntities.size());
        QueuedEntityTransferCache queuedEntityTransferCache = getQueueTransferCaches(userVisit).getQueuedEntityTransferCache();

        queuedEntities.forEach((queuedEntity) ->
                queuedEntityTransfers.add(queuedEntityTransferCache.getQueuedEntityTransfer(queuedEntity))
        );

        return queuedEntityTransfers;
    }
    
    public List<QueuedEntityTransfer> getQueuedEntityTransfers(UserVisit userVisit, QueueType queueType) {
        return getQueuedEntityTransfers(userVisit, getQueuedEntitiesByQueueType(queueType));
    }

    public List<QueuedEntityTransfer> getQueuedEntityTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getQueuedEntityTransfers(userVisit, getQueuedEntitiesByEntityInstance(entityInstance));
    }

    public void removeQueuedEntity(QueuedEntity queuedEntity) {
        queuedEntity.remove();
    }

    private void removedQueuedEntities(List<QueuedEntity> queuedentities) {
        queuedentities.stream().forEach((queuedEntity) -> {
            removeQueuedEntity(queuedEntity);
        });
    }
    
    public void removeQueuedEntitiesByQueueType(QueueType queueType) {
        removedQueuedEntities(getQueuedEntitiesByQueueTypeForUpdate(queueType));
    }
    
    public void removeQueuedEntitiesByEntityInstance(EntityInstance entityInstance) {
        removedQueuedEntities(getQueuedEntitiesByEntityInstanceForUpdate(entityInstance));
    }
    
}
