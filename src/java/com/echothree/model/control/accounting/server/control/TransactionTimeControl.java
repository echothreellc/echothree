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

package com.echothree.model.control.accounting.server.control;

import com.echothree.model.control.accounting.common.choice.TransactionTimeTypeChoicesBean;
import com.echothree.model.control.accounting.common.transfer.TransactionTimeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTimeTypeDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTimeTypeTransfer;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.common.pk.TransactionTimeTypePK;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionTime;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.accounting.server.entity.TransactionTimeTypeDescription;
import com.echothree.model.data.accounting.server.factory.TransactionTimeFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTimeTypeDescriptionFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTimeTypeDetailFactory;
import com.echothree.model.data.accounting.server.factory.TransactionTimeTypeFactory;
import com.echothree.model.data.accounting.server.value.TransactionTimeTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.TransactionTimeTypeDetailValue;
import com.echothree.model.data.accounting.server.value.TransactionTimeValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransactionTimeControl
        extends BaseAccountingControl {

    /** Creates a new instance of TransactionControl */
    public TransactionTimeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Transaction Time Types
    // --------------------------------------------------------------------------------

    public TransactionTimeType createTransactionTimeType(String transactionTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultTransactionTimeType = getDefaultTransactionTimeType();
        var defaultFound = defaultTransactionTimeType != null;

        if(defaultFound && isDefault) {
            var defaultTransactionTimeTypeDetailValue = getDefaultTransactionTimeTypeDetailValueForUpdate();

            defaultTransactionTimeTypeDetailValue.setIsDefault(false);
            updateTransactionTimeTypeFromValue(defaultTransactionTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var transactionTimeType = TransactionTimeTypeFactory.getInstance().create();
        var transactionTimeTypeDetail = TransactionTimeTypeDetailFactory.getInstance().create(transactionTimeType, transactionTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        transactionTimeType = TransactionTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                transactionTimeType.getPrimaryKey());
        transactionTimeType.setActiveDetail(transactionTimeTypeDetail);
        transactionTimeType.setLastDetail(transactionTimeTypeDetail);
        transactionTimeType.store();

        sendEvent(transactionTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return transactionTimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TransactionTimeType */
    public TransactionTimeType getTransactionTimeTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new TransactionTimeTypePK(entityInstance.getEntityUniqueId());

        return TransactionTimeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TransactionTimeType getTransactionTimeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getTransactionTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TransactionTimeType getTransactionTimeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getTransactionTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public TransactionTimeType getTransactionTimeTypeByPK(TransactionTimeTypePK pk) {
        return TransactionTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countTransactionTimeTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM transactiontimetypes, transactiontimetypedetails
                WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid
                """);
    }

    private static final Map<EntityPermission, String> getTransactionTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "AND txntimtypdt_transactiontimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "AND txntimtypdt_transactiontimetypename = ? " +
                "FOR UPDATE");
        getTransactionTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public TransactionTimeType getTransactionTimeTypeByName(String transactionTimeTypeName, EntityPermission entityPermission) {
        return TransactionTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getTransactionTimeTypeByNameQueries,
                transactionTimeTypeName);
    }

    public TransactionTimeType getTransactionTimeTypeByName(String transactionTimeTypeName) {
        return getTransactionTimeTypeByName(transactionTimeTypeName, EntityPermission.READ_ONLY);
    }

    public TransactionTimeType getTransactionTimeTypeByNameForUpdate(String transactionTimeTypeName) {
        return getTransactionTimeTypeByName(transactionTimeTypeName, EntityPermission.READ_WRITE);
    }

    public TransactionTimeTypeDetailValue getTransactionTimeTypeDetailValueForUpdate(TransactionTimeType transactionTimeType) {
        return transactionTimeType == null? null: transactionTimeType.getLastDetailForUpdate().getTransactionTimeTypeDetailValue().clone();
    }

    public TransactionTimeTypeDetailValue getTransactionTimeTypeDetailValueByNameForUpdate(String transactionTimeTypeName) {
        return getTransactionTimeTypeDetailValueForUpdate(getTransactionTimeTypeByNameForUpdate(transactionTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultTransactionTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "AND txntimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "AND txntimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTransactionTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public TransactionTimeType getDefaultTransactionTimeType(EntityPermission entityPermission) {
        return TransactionTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTransactionTimeTypeQueries);
    }

    public TransactionTimeType getDefaultTransactionTimeType() {
        return getDefaultTransactionTimeType(EntityPermission.READ_ONLY);
    }

    public TransactionTimeType getDefaultTransactionTimeTypeForUpdate() {
        return getDefaultTransactionTimeType(EntityPermission.READ_WRITE);
    }

    public TransactionTimeTypeDetailValue getDefaultTransactionTimeTypeDetailValueForUpdate() {
        return getDefaultTransactionTimeTypeForUpdate().getLastDetailForUpdate().getTransactionTimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getTransactionTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "ORDER BY txntimtypdt_sortorder, txntimtypdt_transactiontimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM transactiontimetypes, transactiontimetypedetails " +
                "WHERE txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                "FOR UPDATE");
        getTransactionTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TransactionTimeType> getTransactionTimeTypes(EntityPermission entityPermission) {
        return TransactionTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getTransactionTimeTypesQueries);
    }

    public List<TransactionTimeType> getTransactionTimeTypes() {
        return getTransactionTimeTypes(EntityPermission.READ_ONLY);
    }

    public List<TransactionTimeType> getTransactionTimeTypesForUpdate() {
        return getTransactionTimeTypes(EntityPermission.READ_WRITE);
    }

    public TransactionTimeTypeTransfer getTransactionTimeTypeTransfer(UserVisit userVisit, TransactionTimeType transactionTimeType) {
        return getAccountingTransferCaches(userVisit).getTransactionTimeTypeTransferCache().getTransfer(transactionTimeType);
    }

    public List<TransactionTimeTypeTransfer> getTransactionTimeTypeTransfers(UserVisit userVisit, Collection<TransactionTimeType> transactionTimeTypes) {
        List<TransactionTimeTypeTransfer> transactionTimeTypeTransfers = new ArrayList<>(transactionTimeTypes.size());
        var transactionTimeTypeTransferCache = getAccountingTransferCaches(userVisit).getTransactionTimeTypeTransferCache();

        transactionTimeTypes.forEach((transactionTimeType) ->
                transactionTimeTypeTransfers.add(transactionTimeTypeTransferCache.getTransfer(transactionTimeType))
        );

        return transactionTimeTypeTransfers;
    }

    public List<TransactionTimeTypeTransfer> getTransactionTimeTypeTransfers(UserVisit userVisit) {
        return getTransactionTimeTypeTransfers(userVisit, getTransactionTimeTypes());
    }

    public TransactionTimeTypeChoicesBean getTransactionTimeTypeChoices(String defaultTransactionTimeTypeChoice, Language language, boolean allowNullChoice) {
        var transactionTimeTypes = getTransactionTimeTypes();
        var size = transactionTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTransactionTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var transactionTimeType : transactionTimeTypes) {
            var transactionTimeTypeDetail = transactionTimeType.getLastDetail();

            var label = getBestTransactionTimeTypeDescription(transactionTimeType, language);
            var value = transactionTimeTypeDetail.getTransactionTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultTransactionTimeTypeChoice != null && defaultTransactionTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && transactionTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TransactionTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateTransactionTimeTypeFromValue(TransactionTimeTypeDetailValue transactionTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(transactionTimeTypeDetailValue.hasBeenModified()) {
            var transactionTimeType = TransactionTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     transactionTimeTypeDetailValue.getTransactionTimeTypePK());
            var transactionTimeTypeDetail = transactionTimeType.getActiveDetailForUpdate();

            transactionTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            transactionTimeTypeDetail.store();

            var transactionTimeTypePK = transactionTimeTypeDetail.getTransactionTimeTypePK(); // Not updated
            var transactionTimeTypeName = transactionTimeTypeDetailValue.getTransactionTimeTypeName();
            var isDefault = transactionTimeTypeDetailValue.getIsDefault();
            var sortOrder = transactionTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultTransactionTimeType = getDefaultTransactionTimeType();
                var defaultFound = defaultTransactionTimeType != null && !defaultTransactionTimeType.equals(transactionTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTransactionTimeTypeDetailValue = getDefaultTransactionTimeTypeDetailValueForUpdate();

                    defaultTransactionTimeTypeDetailValue.setIsDefault(false);
                    updateTransactionTimeTypeFromValue(defaultTransactionTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            transactionTimeTypeDetail = TransactionTimeTypeDetailFactory.getInstance().create(transactionTimeTypePK, transactionTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            transactionTimeType.setActiveDetail(transactionTimeTypeDetail);
            transactionTimeType.setLastDetail(transactionTimeTypeDetail);

            sendEvent(transactionTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateTransactionTimeTypeFromValue(TransactionTimeTypeDetailValue transactionTimeTypeDetailValue, BasePK updatedBy) {
        updateTransactionTimeTypeFromValue(transactionTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteTransactionTimeType(TransactionTimeType transactionTimeType, BasePK deletedBy) {
        deleteTransactionTimesByTransactionTimeType(transactionTimeType, deletedBy);
        deleteTransactionTimeTypeDescriptionsByTransactionTimeType(transactionTimeType, deletedBy);

        var transactionTimeTypeDetail = transactionTimeType.getLastDetailForUpdate();
        transactionTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        transactionTimeType.setActiveDetail(null);
        transactionTimeType.store();

        // Check for default, and pick one if necessary
        var defaultTransactionTimeType = getDefaultTransactionTimeType();
        if(defaultTransactionTimeType == null) {
            var transactionTimeTypes = getTransactionTimeTypesForUpdate();

            if(!transactionTimeTypes.isEmpty()) {
                var iter = transactionTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultTransactionTimeType = iter.next();
                }
                var transactionTimeTypeDetailValue = Objects.requireNonNull(defaultTransactionTimeType).getLastDetailForUpdate().getTransactionTimeTypeDetailValue().clone();

                transactionTimeTypeDetailValue.setIsDefault(true);
                updateTransactionTimeTypeFromValue(transactionTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(transactionTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Transaction Time Type Descriptions
    // --------------------------------------------------------------------------------

    public TransactionTimeTypeDescription createTransactionTimeTypeDescription(TransactionTimeType transactionTimeType, Language language, String description, BasePK createdBy) {
        var transactionTimeTypeDescription = TransactionTimeTypeDescriptionFactory.getInstance().create(transactionTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(transactionTimeType.getPrimaryKey(), EventTypes.MODIFY, transactionTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return transactionTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getTransactionTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM transactiontimetypedescriptions " +
                "WHERE txntimtypd_txntimtyp_transactiontimetypeid = ? AND txntimtypd_lang_languageid = ? AND txntimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM transactiontimetypedescriptions " +
                "WHERE txntimtypd_txntimtyp_transactiontimetypeid = ? AND txntimtypd_lang_languageid = ? AND txntimtypd_thrutime = ? " +
                "FOR UPDATE");
        getTransactionTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TransactionTimeTypeDescription getTransactionTimeTypeDescription(TransactionTimeType transactionTimeType, Language language, EntityPermission entityPermission) {
        return TransactionTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTransactionTimeTypeDescriptionQueries,
                transactionTimeType, language, Session.MAX_TIME);
    }

    public TransactionTimeTypeDescription getTransactionTimeTypeDescription(TransactionTimeType transactionTimeType, Language language) {
        return getTransactionTimeTypeDescription(transactionTimeType, language, EntityPermission.READ_ONLY);
    }

    public TransactionTimeTypeDescription getTransactionTimeTypeDescriptionForUpdate(TransactionTimeType transactionTimeType, Language language) {
        return getTransactionTimeTypeDescription(transactionTimeType, language, EntityPermission.READ_WRITE);
    }

    public TransactionTimeTypeDescriptionValue getTransactionTimeTypeDescriptionValue(TransactionTimeTypeDescription transactionTimeTypeDescription) {
        return transactionTimeTypeDescription == null? null: transactionTimeTypeDescription.getTransactionTimeTypeDescriptionValue().clone();
    }

    public TransactionTimeTypeDescriptionValue getTransactionTimeTypeDescriptionValueForUpdate(TransactionTimeType transactionTimeType, Language language) {
        return getTransactionTimeTypeDescriptionValue(getTransactionTimeTypeDescriptionForUpdate(transactionTimeType, language));
    }

    private static final Map<EntityPermission, String> getTransactionTimeTypeDescriptionsByTransactionTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM transactiontimetypedescriptions, languages " +
                "WHERE txntimtypd_txntimtyp_transactiontimetypeid = ? AND txntimtypd_thrutime = ? AND txntimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sorttransaction, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM transactiontimetypedescriptions " +
                "WHERE txntimtypd_txntimtyp_transactiontimetypeid = ? AND txntimtypd_thrutime = ? " +
                "FOR UPDATE");
        getTransactionTimeTypeDescriptionsByTransactionTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TransactionTimeTypeDescription> getTransactionTimeTypeDescriptionsByTransactionTimeType(TransactionTimeType transactionTimeType, EntityPermission entityPermission) {
        return TransactionTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTransactionTimeTypeDescriptionsByTransactionTimeTypeQueries,
                transactionTimeType, Session.MAX_TIME);
    }

    public List<TransactionTimeTypeDescription> getTransactionTimeTypeDescriptionsByTransactionTimeType(TransactionTimeType transactionTimeType) {
        return getTransactionTimeTypeDescriptionsByTransactionTimeType(transactionTimeType, EntityPermission.READ_ONLY);
    }

    public List<TransactionTimeTypeDescription> getTransactionTimeTypeDescriptionsByTransactionTimeTypeForUpdate(TransactionTimeType transactionTimeType) {
        return getTransactionTimeTypeDescriptionsByTransactionTimeType(transactionTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestTransactionTimeTypeDescription(TransactionTimeType transactionTimeType, Language language) {
        String description;
        var transactionTimeTypeDescription = getTransactionTimeTypeDescription(transactionTimeType, language);

        if(transactionTimeTypeDescription == null && !language.getIsDefault()) {
            transactionTimeTypeDescription = getTransactionTimeTypeDescription(transactionTimeType, getPartyControl().getDefaultLanguage());
        }

        if(transactionTimeTypeDescription == null) {
            description = transactionTimeType.getLastDetail().getTransactionTimeTypeName();
        } else {
            description = transactionTimeTypeDescription.getDescription();
        }

        return description;
    }

    public TransactionTimeTypeDescriptionTransfer getTransactionTimeTypeDescriptionTransfer(UserVisit userVisit, TransactionTimeTypeDescription transactionTimeTypeDescription) {
        return getAccountingTransferCaches(userVisit).getTransactionTimeTypeDescriptionTransferCache().getTransfer(transactionTimeTypeDescription);
    }

    public List<TransactionTimeTypeDescriptionTransfer> getTransactionTimeTypeDescriptionTransfersByTransactionTimeType(UserVisit userVisit, TransactionTimeType transactionTimeType) {
        var transactionTimeTypeDescriptions = getTransactionTimeTypeDescriptionsByTransactionTimeType(transactionTimeType);
        List<TransactionTimeTypeDescriptionTransfer> transactionTimeTypeDescriptionTransfers = new ArrayList<>(transactionTimeTypeDescriptions.size());
        var transactionTimeTypeDescriptionTransferCache = getAccountingTransferCaches(userVisit).getTransactionTimeTypeDescriptionTransferCache();

        transactionTimeTypeDescriptions.forEach((transactionTimeTypeDescription) ->
                transactionTimeTypeDescriptionTransfers.add(transactionTimeTypeDescriptionTransferCache.getTransfer(transactionTimeTypeDescription))
        );

        return transactionTimeTypeDescriptionTransfers;
    }

    public void updateTransactionTimeTypeDescriptionFromValue(TransactionTimeTypeDescriptionValue transactionTimeTypeDescriptionValue, BasePK updatedBy) {
        if(transactionTimeTypeDescriptionValue.hasBeenModified()) {
            var transactionTimeTypeDescription = TransactionTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    transactionTimeTypeDescriptionValue.getPrimaryKey());

            transactionTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            transactionTimeTypeDescription.store();

            var transactionTimeType = transactionTimeTypeDescription.getTransactionTimeType();
            var language = transactionTimeTypeDescription.getLanguage();
            var description = transactionTimeTypeDescriptionValue.getDescription();

            transactionTimeTypeDescription = TransactionTimeTypeDescriptionFactory.getInstance().create(transactionTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(transactionTimeType.getPrimaryKey(), EventTypes.MODIFY, transactionTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTransactionTimeTypeDescription(TransactionTimeTypeDescription transactionTimeTypeDescription, BasePK deletedBy) {
        transactionTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(transactionTimeTypeDescription.getTransactionTimeTypePK(), EventTypes.MODIFY, transactionTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTransactionTimeTypeDescriptionsByTransactionTimeType(TransactionTimeType transactionTimeType, BasePK deletedBy) {
        var transactionTimeTypeDescriptions = getTransactionTimeTypeDescriptionsByTransactionTimeTypeForUpdate(transactionTimeType);

        transactionTimeTypeDescriptions.forEach((transactionTimeTypeDescription) -> 
                deleteTransactionTimeTypeDescription(transactionTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Transaction Times
    // --------------------------------------------------------------------------------

    public TransactionTime createTransactionTime(Transaction transaction, TransactionTimeType transactionTimeType, Long time, BasePK createdBy) {
        var transactionTime = TransactionTimeFactory.getInstance().create(transaction, transactionTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(transaction.getPrimaryKey(), EventTypes.MODIFY, transactionTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return transactionTime;
    }

    public long countTransactionTimesByTransaction(Transaction transaction) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM transactiontimes " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_thrutime = ?",
                transaction, Session.MAX_TIME_LONG);
    }

    public long countTransactionTimesByTransactionTimeType(TransactionTimeType transactionTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM transactiontimes " +
                        "WHERE txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ?",
                transactionTimeType, Session.MAX_TIME_LONG);
    }

    public boolean transactionTimeExists(Transaction transaction, TransactionTimeType transactionTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM transactiontimes " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ?",
                transaction, transactionTimeType, Session.MAX_TIME_LONG) == 1;
    }

    private static final Map<EntityPermission, String> getTransactionTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM transactiontimes " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM transactiontimes " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ? " +
                        "FOR UPDATE");
        getTransactionTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private TransactionTime getTransactionTime(Transaction transaction, TransactionTimeType transactionTimeType, EntityPermission entityPermission) {
        return TransactionTimeFactory.getInstance().getEntityFromQuery(entityPermission, getTransactionTimeQueries, transaction, transactionTimeType, Session.MAX_TIME);
    }

    public TransactionTime getTransactionTime(Transaction transaction, TransactionTimeType transactionTimeType) {
        return getTransactionTime(transaction, transactionTimeType, EntityPermission.READ_ONLY);
    }

    public TransactionTime getTransactionTimeForUpdate(Transaction transaction, TransactionTimeType transactionTimeType) {
        return getTransactionTime(transaction, transactionTimeType, EntityPermission.READ_WRITE);
    }

    public TransactionTimeValue getTransactionTimeValue(TransactionTime transactionTime) {
        return transactionTime == null? null: transactionTime.getTransactionTimeValue().clone();
    }

    public TransactionTimeValue getTransactionTimeValueForUpdate(Transaction transaction, TransactionTimeType transactionTimeType) {
        return getTransactionTimeValue(getTransactionTimeForUpdate(transaction, transactionTimeType));
    }

    private static final Map<EntityPermission, String> getTransactionTimesByTransactionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM transactiontimes, transactiontimetypes, transactiontimetypedetails " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_thrutime = ? " +
                        "AND txntim_txntimtyp_transactiontimetypeid = txntimtyp_transactiontimetypeid AND txntimtyp_activedetailid = txntimtypdt_transactiontimetypedetailid " +
                        "ORDER BY txntimtypdt_sortorder, txntimtypdt_transactiontimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM transactiontimes " +
                        "WHERE txntim_trx_transactionid = ? AND txntim_thrutime = ? " +
                        "FOR UPDATE");
        getTransactionTimesByTransactionQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TransactionTime> getTransactionTimesByTransaction(Transaction transaction, EntityPermission entityPermission) {
        return TransactionTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getTransactionTimesByTransactionQueries, transaction, Session.MAX_TIME);
    }

    public List<TransactionTime> getTransactionTimesByTransaction(Transaction transaction) {
        return getTransactionTimesByTransaction(transaction, EntityPermission.READ_ONLY);
    }

    public List<TransactionTime> getTransactionTimesByTransactionForUpdate(Transaction transaction) {
        return getTransactionTimesByTransaction(transaction, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTransactionTimesByTransactionTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM transactiontimes, transactions, transactiondetails " +
                        "WHERE txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ? " +
                        "AND txntim_trx_transactionid = txntim_trx_transactionid AND trx_transactionid = trxdt_transactiondetailid " +
                        "ORDER BY trxdt_transactionname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM transactiontimes " +
                        "WHERE txntim_txntimtyp_transactiontimetypeid = ? AND txntim_thrutime = ? " +
                        "FOR UPDATE");
        getTransactionTimesByTransactionTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TransactionTime> getTransactionTimesByTransactionTimeType(TransactionTimeType transactionTimeType, EntityPermission entityPermission) {
        return TransactionTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getTransactionTimesByTransactionTimeTypeQueries, transactionTimeType, Session.MAX_TIME);
    }

    public List<TransactionTime> getTransactionTimesByTransactionTimeType(TransactionTimeType transactionTimeType) {
        return getTransactionTimesByTransactionTimeType(transactionTimeType, EntityPermission.READ_ONLY);
    }

    public List<TransactionTime> getTransactionTimesByTransactionTimeTypeForUpdate(TransactionTimeType transactionTimeType) {
        return getTransactionTimesByTransactionTimeType(transactionTimeType, EntityPermission.READ_WRITE);
    }

    public TransactionTimeTransfer getTransactionTimeTransfer(UserVisit userVisit, TransactionTime transactionTime) {
        return getAccountingTransferCaches(userVisit).getTransactionTimeTransferCache().getTransfer(transactionTime);
    }

    public List<TransactionTimeTransfer> getTransactionTimeTransfers(UserVisit userVisit, Collection<TransactionTime> transactionTimes) {
        List<TransactionTimeTransfer> transactionTimeTransfers = new ArrayList<>(transactionTimes.size());
        var transactionTimeTransferCache = getAccountingTransferCaches(userVisit).getTransactionTimeTransferCache();

        transactionTimes.forEach((transactionTime) ->
                transactionTimeTransfers.add(transactionTimeTransferCache.getTransfer(transactionTime))
        );

        return transactionTimeTransfers;
    }

    public List<TransactionTimeTransfer> getTransactionTimeTransfersByTransaction(UserVisit userVisit, Transaction transaction) {
        return getTransactionTimeTransfers(userVisit, getTransactionTimesByTransaction(transaction));
    }

    public List<TransactionTimeTransfer> getTransactionTimeTransfersByTransactionTimeType(UserVisit userVisit, TransactionTimeType transactionTimeType) {
        return getTransactionTimeTransfers(userVisit, getTransactionTimesByTransactionTimeType(transactionTimeType));
    }

    public void updateTransactionTimeFromValue(TransactionTimeValue transactionTimeValue, BasePK updatedBy) {
        if(transactionTimeValue.hasBeenModified()) {
            var transactionTime = TransactionTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    transactionTimeValue.getPrimaryKey());

            transactionTime.setThruTime(session.START_TIME_LONG);
            transactionTime.store();

            var transactionPK = transactionTime.getTransactionPK(); // Not updated
            var transactionTimeTypePK = transactionTime.getTransactionTimeTypePK(); // Not updated
            var time = transactionTimeValue.getTime();

            transactionTime = TransactionTimeFactory.getInstance().create(transactionPK, transactionTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(transactionPK, EventTypes.MODIFY, transactionTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTransactionTime(TransactionTime transactionTime, BasePK deletedBy) {
        transactionTime.setThruTime(session.START_TIME_LONG);

        sendEvent(transactionTime.getTransactionTimeTypePK(), EventTypes.MODIFY, transactionTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTransactionTimes(List<TransactionTime> transactionTimes, BasePK deletedBy) {
        transactionTimes.forEach((transactionTime) -> 
                deleteTransactionTime(transactionTime, deletedBy)
        );
    }

    public void deleteTransactionTimesByTransaction(Transaction transaction, BasePK deletedBy) {
        deleteTransactionTimes(getTransactionTimesByTransactionForUpdate(transaction), deletedBy);
    }

    public void deleteTransactionTimesByTransactionTimeType(TransactionTimeType transactionTimeType, BasePK deletedBy) {
        deleteTransactionTimes(getTransactionTimesByTransactionTimeTypeForUpdate(transactionTimeType), deletedBy);
    }

}
