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

package com.echothree.model.control.queue.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.queue.common.choice.QueueTypeChoicesBean;
import com.echothree.model.control.queue.common.transfer.QueueTypeDescriptionTransfer;
import com.echothree.model.control.queue.common.transfer.QueueTypeTransfer;
import com.echothree.model.control.queue.common.transfer.QueuedEntityTransfer;
import com.echothree.model.control.queue.server.transfer.QueueTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDescription;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class QueueControl
        extends BaseModelControl {
    
    /** Creates a new instance of QueueControl */
    protected QueueControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Transfer Caches
    // --------------------------------------------------------------------------------
    
    private QueueTransferCaches queueTransferCaches;
    
    public QueueTransferCaches getQueueTransferCaches() {
        if(queueTransferCaches == null) {
            queueTransferCaches = new QueueTransferCaches(this);
        }
        
        return queueTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------

    public QueueType createQueueType(String queueTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultQueueType = getDefaultQueueType();
        var defaultFound = defaultQueueType != null;

        if(defaultFound && isDefault) {
            var defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

            defaultQueueTypeDetailValue.setIsDefault(false);
            updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var queueType = QueueTypeFactory.getInstance().create();
        var queueTypeDetail = QueueTypeDetailFactory.getInstance().create(queueType, queueTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        queueType = QueueTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, queueType.getPrimaryKey());
        queueType.setActiveDetail(queueTypeDetail);
        queueType.setLastDetail(queueTypeDetail);
        queueType.store();

        sendEvent(queueType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return queueType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.QueueType */
    public QueueType getQueueTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new QueueTypePK(entityInstance.getEntityUniqueId());

        return QueueTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public QueueType getQueueTypeByEntityInstance(EntityInstance entityInstance) {
        return getQueueTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public QueueType getQueueTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getQueueTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countQueueTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM queuetypes, queuetypedetails
                WHERE qtyp_activedetailid = qtypdt_queuetypedetailid
                """);
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
        return getQueueTransferCaches().getQueueTypeTransferCache().getQueueTypeTransfer(userVisit, queueType);
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit, Collection<QueueType> queueTypes) {
        List<QueueTypeTransfer> queueTypeTransfers = new ArrayList<>(queueTypes.size());
        var queueTypeTransferCache = getQueueTransferCaches(userVisit).getQueueTypeTransferCache();

        queueTypes.forEach((queueType) ->
                queueTypeTransfers.add(queueTypeTransferCache.getQueueTypeTransfer(queueType))
        );

        return queueTypeTransfers;
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit) {
        return getQueueTypeTransfers(userVisit, getQueueTypes());
    }

    public QueueTypeChoicesBean getQueueTypeChoices(String defaultQueueTypeChoice, Language language, boolean allowNullChoice) {
        var queueTypes = getQueueTypes();
        var size = queueTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultQueueTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var queueType : queueTypes) {
            var queueTypeDetail = queueType.getLastDetail();

            var label = getBestQueueTypeDescription(queueType, language);
            var value = queueTypeDetail.getQueueTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultQueueTypeChoice != null && defaultQueueTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && queueTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new QueueTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(queueTypeDetailValue.hasBeenModified()) {
            var queueType = QueueTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     queueTypeDetailValue.getQueueTypePK());
            var queueTypeDetail = queueType.getActiveDetailForUpdate();

            queueTypeDetail.setThruTime(session.START_TIME_LONG);
            queueTypeDetail.store();

            var queueTypePK = queueTypeDetail.getQueueTypePK(); // Not updated
            var queueTypeName = queueTypeDetailValue.getQueueTypeName();
            var isDefault = queueTypeDetailValue.getIsDefault();
            var sortOrder = queueTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultQueueType = getDefaultQueueType();
                var defaultFound = defaultQueueType != null && !defaultQueueType.equals(queueType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

                    defaultQueueTypeDetailValue.setIsDefault(false);
                    updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            queueTypeDetail = QueueTypeDetailFactory.getInstance().create(queueTypePK, queueTypeName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            queueType.setActiveDetail(queueTypeDetail);
            queueType.setLastDetail(queueTypeDetail);

            sendEvent(queueTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, BasePK updatedBy) {
        updateQueueTypeFromValue(queueTypeDetailValue, true, updatedBy);
    }

    private void deleteQueueType(QueueType queueType, boolean checkDefault, BasePK deletedBy) {
        var queueTypeDetail = queueType.getLastDetailForUpdate();

        removeQueuedEntitiesByQueueType(queueType);
        deleteQueueTypeDescriptionsByQueueType(queueType, deletedBy);

        queueTypeDetail.setThruTime(session.START_TIME_LONG);
        queueType.setActiveDetail(null);
        queueType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultQueueType = getDefaultQueueType();

            if(defaultQueueType == null) {
                var queueTypes = getQueueTypesForUpdate();

                if(!queueTypes.isEmpty()) {
                    var iter = queueTypes.iterator();
                    if(iter.hasNext()) {
                        defaultQueueType = iter.next();
                    }
                    var queueTypeDetailValue = Objects.requireNonNull(defaultQueueType).getLastDetailForUpdate().getQueueTypeDetailValue().clone();

                    queueTypeDetailValue.setIsDefault(true);
                    updateQueueTypeFromValue(queueTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(queueType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteQueueType(QueueType queueType, BasePK deletedBy) {
        deleteQueueType(queueType, true, deletedBy);
    }

    private void deleteQueueTypes(List<QueueType> queueTypes, boolean checkDefault, BasePK deletedBy) {
        queueTypes.forEach((queueType) -> deleteQueueType(queueType, checkDefault, deletedBy));
    }

    public void deleteQueueTypes(List<QueueType> queueTypes, BasePK deletedBy) {
        deleteQueueTypes(queueTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------

    public QueueTypeDescription createQueueTypeDescription(QueueType queueType, Language language, String description, BasePK createdBy) {
        var queueTypeDescription = QueueTypeDescriptionFactory.getInstance().create(queueType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(queueType.getPrimaryKey(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var queueTypeDescription = getQueueTypeDescription(queueType, language);

        if(queueTypeDescription == null && !language.getIsDefault()) {
            queueTypeDescription = getQueueTypeDescription(queueType, partyControl.getDefaultLanguage());
        }

        if(queueTypeDescription == null) {
            description = queueType.getLastDetail().getQueueTypeName();
        } else {
            description = queueTypeDescription.getDescription();
        }

        return description;
    }

    public QueueTypeDescriptionTransfer getQueueTypeDescriptionTransfer(UserVisit userVisit, QueueTypeDescription queueTypeDescription) {
        return getQueueTransferCaches().getQueueTypeDescriptionTransferCache().getQueueTypeDescriptionTransfer(userVisit, queueTypeDescription);
    }

    public List<QueueTypeDescriptionTransfer> getQueueTypeDescriptionTransfersByQueueType(UserVisit userVisit, QueueType queueType) {
        var queueTypeDescriptions = getQueueTypeDescriptionsByQueueType(queueType);
        List<QueueTypeDescriptionTransfer> queueTypeDescriptionTransfers = new ArrayList<>(queueTypeDescriptions.size());
        var queueTypeDescriptionTransferCache = getQueueTransferCaches(userVisit).getQueueTypeDescriptionTransferCache();

        queueTypeDescriptions.forEach((queueTypeDescription) ->
                queueTypeDescriptionTransfers.add(queueTypeDescriptionTransferCache.getQueueTypeDescriptionTransfer(queueTypeDescription))
        );

        return queueTypeDescriptionTransfers;
    }

    public void updateQueueTypeDescriptionFromValue(QueueTypeDescriptionValue queueTypeDescriptionValue, BasePK updatedBy) {
        if(queueTypeDescriptionValue.hasBeenModified()) {
            var queueTypeDescription = QueueTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    queueTypeDescriptionValue.getPrimaryKey());

            queueTypeDescription.setThruTime(session.START_TIME_LONG);
            queueTypeDescription.store();

            var queueType = queueTypeDescription.getQueueType();
            var language = queueTypeDescription.getLanguage();
            var description = queueTypeDescriptionValue.getDescription();

            queueTypeDescription = QueueTypeDescriptionFactory.getInstance().create(queueType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(queueType.getPrimaryKey(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteQueueTypeDescription(QueueTypeDescription queueTypeDescription, BasePK deletedBy) {
        queueTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(queueTypeDescription.getQueueTypePK(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteQueueTypeDescriptionsByQueueType(QueueType queueType, BasePK deletedBy) {
        var queueTypeDescriptions = getQueueTypeDescriptionsByQueueTypeForUpdate(queueType);

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
        List<QueuedEntity> queuedEntities;
        
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

            var ps = QueuedEntityFactory.getInstance().prepareStatement(query);
            
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
        List<QueuedEntity> queuedEntities;
        
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

            var ps = QueuedEntityFactory.getInstance().prepareStatement(query);
            
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
        return getQueueTransferCaches().getQueuedEntityTransferCache().getQueuedEntityTransfer(userVisit, queuedEntity);
    }
    
    public List<QueuedEntityTransfer> getQueuedEntityTransfers(UserVisit userVisit, Collection<QueuedEntity> queuedEntities) {
        List<QueuedEntityTransfer> queuedEntityTransfers = new ArrayList<>(queuedEntities.size());
        var queuedEntityTransferCache = getQueueTransferCaches(userVisit).getQueuedEntityTransferCache();

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
        queuedentities.forEach((queuedEntity) -> {
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
