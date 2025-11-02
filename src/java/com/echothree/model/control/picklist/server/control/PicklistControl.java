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

package com.echothree.model.control.picklist.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.picklist.common.choice.PicklistAliasTypeChoicesBean;
import com.echothree.model.control.picklist.common.choice.PicklistTimeTypeChoicesBean;
import com.echothree.model.control.picklist.common.choice.PicklistTypeChoicesBean;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTypeTransfer;
import com.echothree.model.control.picklist.server.transfer.PicklistTransferCaches;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistAlias;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistTime;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.entity.PicklistTypeDescription;
import com.echothree.model.data.picklist.server.factory.PicklistAliasFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeFactory;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDetailValue;
import com.echothree.model.data.picklist.server.value.PicklistAliasValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDetailValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeValue;
import com.echothree.model.data.picklist.server.value.PicklistTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistTypeDetailValue;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PicklistControl
        extends BaseModelControl {
    
    /** Creates a new instance of PicklistControl */
    protected PicklistControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Picklist Transfer Caches
    // --------------------------------------------------------------------------------

    private PicklistTransferCaches picklistTransferCaches;

    public PicklistTransferCaches getPicklistTransferCaches(UserVisit userVisit) {
        if(picklistTransferCaches == null) {
            picklistTransferCaches = new PicklistTransferCaches(userVisit, this);
        }

        return picklistTransferCaches;
    }

    // --------------------------------------------------------------------------------
    //   Picklist Types
    // --------------------------------------------------------------------------------

    public PicklistType createPicklistType(String picklistTypeName, PicklistType parentPicklistType, SequenceType picklistSequenceType, Workflow picklistWorkflow,
            WorkflowEntrance picklistWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPicklistType = getDefaultPicklistType();
        var defaultFound = defaultPicklistType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistTypeDetailValue = getDefaultPicklistTypeDetailValueForUpdate();

            defaultPicklistTypeDetailValue.setIsDefault(false);
            updatePicklistTypeFromValue(defaultPicklistTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistType = PicklistTypeFactory.getInstance().create();
        var picklistTypeDetail = PicklistTypeDetailFactory.getInstance().create(picklistType, picklistTypeName, parentPicklistType, picklistSequenceType,
                picklistWorkflow, picklistWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        picklistType = PicklistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                picklistType.getPrimaryKey());
        picklistType.setActiveDetail(picklistTypeDetail);
        picklistType.setLastDetail(picklistTypeDetail);
        picklistType.store();

        sendEvent(picklistType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistType;
    }

    private static final Map<EntityPermission, String> getPicklistTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "AND pcklsttypdt_picklisttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "AND pcklsttypdt_picklisttypename = ? " +
                "FOR UPDATE");
        getPicklistTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistType getPicklistTypeByName(String picklistTypeName, EntityPermission entityPermission) {
        return PicklistTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistTypeByNameQueries, picklistTypeName);
    }

    public PicklistType getPicklistTypeByName(String picklistTypeName) {
        return getPicklistTypeByName(picklistTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistType getPicklistTypeByNameForUpdate(String picklistTypeName) {
        return getPicklistTypeByName(picklistTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistTypeDetailValue getPicklistTypeDetailValueForUpdate(PicklistType picklistType) {
        return picklistType == null? null: picklistType.getLastDetailForUpdate().getPicklistTypeDetailValue().clone();
    }

    public PicklistTypeDetailValue getPicklistTypeDetailValueByNameForUpdate(String picklistTypeName) {
        return getPicklistTypeDetailValueForUpdate(getPicklistTypeByNameForUpdate(picklistTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPicklistTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "AND pcklsttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "AND pcklsttypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPicklistTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistType getDefaultPicklistType(EntityPermission entityPermission) {
        return PicklistTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPicklistTypeQueries);
    }

    public PicklistType getDefaultPicklistType() {
        return getDefaultPicklistType(EntityPermission.READ_ONLY);
    }

    public PicklistType getDefaultPicklistTypeForUpdate() {
        return getDefaultPicklistType(EntityPermission.READ_WRITE);
    }

    public PicklistTypeDetailValue getDefaultPicklistTypeDetailValueForUpdate() {
        return getDefaultPicklistTypeForUpdate().getLastDetailForUpdate().getPicklistTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPicklistTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "ORDER BY pcklsttypdt_sortorder, pcklsttypdt_picklisttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid " +
                "FOR UPDATE");
        getPicklistTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistType> getPicklistTypes(EntityPermission entityPermission) {
        return PicklistTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTypesQueries);
    }

    public List<PicklistType> getPicklistTypes() {
        return getPicklistTypes(EntityPermission.READ_ONLY);
    }

    public List<PicklistType> getPicklistTypesForUpdate() {
        return getPicklistTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPicklistTypesByParentPicklistTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid AND pcklsttypdt_parentpicklisttypeid = ? " +
                "ORDER BY pcklsttypdt_sortorder, pcklsttypdt_picklisttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypes, picklisttypedetails " +
                "WHERE pcklsttyp_activedetailid = pcklsttypdt_picklisttypedetailid AND pcklsttypdt_parentpicklisttypeid = ? " +
                "FOR UPDATE");
        getPicklistTypesByParentPicklistTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistType> getPicklistTypesByParentPicklistType(PicklistType parentPicklistType,
            EntityPermission entityPermission) {
        return PicklistTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTypesByParentPicklistTypeQueries,
                parentPicklistType);
    }

    public List<PicklistType> getPicklistTypesByParentPicklistType(PicklistType parentPicklistType) {
        return getPicklistTypesByParentPicklistType(parentPicklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistType> getPicklistTypesByParentPicklistTypeForUpdate(PicklistType parentPicklistType) {
        return getPicklistTypesByParentPicklistType(parentPicklistType, EntityPermission.READ_WRITE);
    }

   public PicklistTypeTransfer getPicklistTypeTransfer(UserVisit userVisit, PicklistType picklistType) {
        return getPicklistTransferCaches(userVisit).getPicklistTypeTransferCache().getPicklistTypeTransfer(picklistType);
    }

    public List<PicklistTypeTransfer> getPicklistTypeTransfers(UserVisit userVisit) {
        var picklistTypes = getPicklistTypes();
        List<PicklistTypeTransfer> picklistTypeTransfers = new ArrayList<>(picklistTypes.size());
        var picklistTypeTransferCache = getPicklistTransferCaches(userVisit).getPicklistTypeTransferCache();

        picklistTypes.forEach((picklistType) ->
                picklistTypeTransfers.add(picklistTypeTransferCache.getPicklistTypeTransfer(picklistType))
        );

        return picklistTypeTransfers;
    }

    public PicklistTypeChoicesBean getPicklistTypeChoices(String defaultPicklistTypeChoice,
            Language language, boolean allowNullChoice) {
        var picklistTypes = getPicklistTypes();
        var size = picklistTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistType : picklistTypes) {
            var picklistTypeDetail = picklistType.getLastDetail();

            var label = getBestPicklistTypeDescription(picklistType, language);
            var value = picklistTypeDetail.getPicklistTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistTypeChoice != null && defaultPicklistTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentPicklistTypeSafe(PicklistType picklistType,
            PicklistType parentPicklistType) {
        var safe = true;

        if(parentPicklistType != null) {
            Set<PicklistType> parentPicklistTypes = new HashSet<>();

            parentPicklistTypes.add(picklistType);
            do {
                if(parentPicklistTypes.contains(parentPicklistType)) {
                    safe = false;
                    break;
                }

                parentPicklistTypes.add(parentPicklistType);
                parentPicklistType = parentPicklistType.getLastDetail().getParentPicklistType();
            } while(parentPicklistType != null);
        }

        return safe;
    }

    private void updatePicklistTypeFromValue(PicklistTypeDetailValue picklistTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistTypeDetailValue.hasBeenModified()) {
            var picklistType = PicklistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistTypeDetailValue.getPicklistTypePK());
            var picklistTypeDetail = picklistType.getActiveDetailForUpdate();

            picklistTypeDetail.setThruTime(session.START_TIME_LONG);
            picklistTypeDetail.store();

            var picklistTypePK = picklistTypeDetail.getPicklistTypePK(); // Not updated
            var picklistTypeName = picklistTypeDetailValue.getPicklistTypeName();
            var parentPicklistTypePK = picklistTypeDetailValue.getParentPicklistTypePK();
            var picklistSequenceTypePK = picklistTypeDetailValue.getPicklistSequenceTypePK();
            var picklistWorkflowPK = picklistTypeDetailValue.getPicklistWorkflowPK();
            var picklistWorkflowEntrancePK = picklistTypeDetailValue.getPicklistWorkflowEntrancePK();
            var isDefault = picklistTypeDetailValue.getIsDefault();
            var sortOrder = picklistTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistType = getDefaultPicklistType();
                var defaultFound = defaultPicklistType != null && !defaultPicklistType.equals(picklistType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistTypeDetailValue = getDefaultPicklistTypeDetailValueForUpdate();

                    defaultPicklistTypeDetailValue.setIsDefault(false);
                    updatePicklistTypeFromValue(defaultPicklistTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistTypeDetail = PicklistTypeDetailFactory.getInstance().create(picklistTypePK, picklistTypeName, parentPicklistTypePK, picklistSequenceTypePK,
                    picklistWorkflowPK, picklistWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            picklistType.setActiveDetail(picklistTypeDetail);
            picklistType.setLastDetail(picklistTypeDetail);

            sendEvent(picklistTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistTypeFromValue(PicklistTypeDetailValue picklistTypeDetailValue, BasePK updatedBy) {
        updatePicklistTypeFromValue(picklistTypeDetailValue, true, updatedBy);
    }

    private void deletePicklistType(PicklistType picklistType, boolean checkDefault, BasePK deletedBy) {
        var picklistTypeDetail = picklistType.getLastDetailForUpdate();
        
        deletePicklistTypesByParentPicklistType(picklistType, deletedBy);
        deletePicklistTypeDescriptionsByPicklistType(picklistType, deletedBy);
        deletePicklistAliasTypesByPicklistType(picklistType, deletedBy);
        // TODO: deletePicklistsByPicklistType(picklistType, deletedBy);

        picklistTypeDetail.setThruTime(session.START_TIME_LONG);
        picklistType.setActiveDetail(null);
        picklistType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultPicklistType = getDefaultPicklistType();

            if(defaultPicklistType == null) {
                var picklistTypes = getPicklistTypesForUpdate();

                if(!picklistTypes.isEmpty()) {
                    var iter = picklistTypes.iterator();
                    if(iter.hasNext()) {
                        defaultPicklistType = iter.next();
                    }
                    var picklistTypeDetailValue = Objects.requireNonNull(defaultPicklistType).getLastDetailForUpdate().getPicklistTypeDetailValue().clone();

                    picklistTypeDetailValue.setIsDefault(true);
                    updatePicklistTypeFromValue(picklistTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(picklistType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePicklistType(PicklistType picklistType, BasePK deletedBy) {
        deletePicklistType(picklistType, true, deletedBy);
    }

    private void deletePicklistTypes(List<PicklistType> picklistTypes, boolean checkDefault, BasePK deletedBy) {
        picklistTypes.forEach((picklistType) -> deletePicklistType(picklistType, checkDefault, deletedBy));
    }

    public void deletePicklistTypes(List<PicklistType> picklistTypes, BasePK deletedBy) {
        deletePicklistTypes(picklistTypes, true, deletedBy);
    }

    private void deletePicklistTypesByParentPicklistType(PicklistType parentPicklistType, BasePK deletedBy) {
        deletePicklistTypes(getPicklistTypesByParentPicklistTypeForUpdate(parentPicklistType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    public PicklistTypeDescription createPicklistTypeDescription(PicklistType picklistType, Language language, String description, BasePK createdBy) {
        var picklistTypeDescription = PicklistTypeDescriptionFactory.getInstance().create(picklistType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(picklistType.getPrimaryKey(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTypeDescription;
    }

    private static final Map<EntityPermission, String> getPicklistTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypedescriptions " +
                "WHERE pcklsttypd_pcklsttyp_picklisttypeid = ? AND pcklsttypd_lang_languageid = ? AND pcklsttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypedescriptions " +
                "WHERE pcklsttypd_pcklsttyp_picklisttypeid = ? AND pcklsttypd_lang_languageid = ? AND pcklsttypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistTypeDescription getPicklistTypeDescription(PicklistType picklistType, Language language, EntityPermission entityPermission) {
        return PicklistTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistTypeDescriptionQueries,
                picklistType, language, Session.MAX_TIME);
    }

    public PicklistTypeDescription getPicklistTypeDescription(PicklistType picklistType, Language language) {
        return getPicklistTypeDescription(picklistType, language, EntityPermission.READ_ONLY);
    }

    public PicklistTypeDescription getPicklistTypeDescriptionForUpdate(PicklistType picklistType, Language language) {
        return getPicklistTypeDescription(picklistType, language, EntityPermission.READ_WRITE);
    }

    public PicklistTypeDescriptionValue getPicklistTypeDescriptionValue(PicklistTypeDescription picklistTypeDescription) {
        return picklistTypeDescription == null? null: picklistTypeDescription.getPicklistTypeDescriptionValue().clone();
    }

    public PicklistTypeDescriptionValue getPicklistTypeDescriptionValueForUpdate(PicklistType picklistType, Language language) {
        return getPicklistTypeDescriptionValue(getPicklistTypeDescriptionForUpdate(picklistType, language));
    }

    private static final Map<EntityPermission, String> getPicklistTypeDescriptionsByPicklistTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttypedescriptions, languages " +
                "WHERE pcklsttypd_pcklsttyp_picklisttypeid = ? AND pcklsttypd_thrutime = ? AND pcklsttypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttypedescriptions " +
                "WHERE pcklsttypd_pcklsttyp_picklisttypeid = ? AND pcklsttypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTypeDescriptionsByPicklistTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistType(PicklistType picklistType, EntityPermission entityPermission) {
        return PicklistTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTypeDescriptionsByPicklistTypeQueries,
                picklistType, Session.MAX_TIME);
    }

    public List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistType(PicklistType picklistType) {
        return getPicklistTypeDescriptionsByPicklistType(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistTypeForUpdate(PicklistType picklistType) {
        return getPicklistTypeDescriptionsByPicklistType(picklistType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistTypeDescription(PicklistType picklistType, Language language) {
        String description;
        var picklistTypeDescription = getPicklistTypeDescription(picklistType, language);

        if(picklistTypeDescription == null && !language.getIsDefault()) {
            picklistTypeDescription = getPicklistTypeDescription(picklistType, partyControl.getDefaultLanguage());
        }

        if(picklistTypeDescription == null) {
            description = picklistType.getLastDetail().getPicklistTypeName();
        } else {
            description = picklistTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistTypeDescriptionTransfer getPicklistTypeDescriptionTransfer(UserVisit userVisit, PicklistTypeDescription picklistTypeDescription) {
        return getPicklistTransferCaches(userVisit).getPicklistTypeDescriptionTransferCache().getPicklistTypeDescriptionTransfer(picklistTypeDescription);
    }

    public List<PicklistTypeDescriptionTransfer> getPicklistTypeDescriptionTransfersByPicklistType(UserVisit userVisit, PicklistType picklistType) {
        var picklistTypeDescriptions = getPicklistTypeDescriptionsByPicklistType(picklistType);
        List<PicklistTypeDescriptionTransfer> picklistTypeDescriptionTransfers = new ArrayList<>(picklistTypeDescriptions.size());
        var picklistTypeDescriptionTransferCache = getPicklistTransferCaches(userVisit).getPicklistTypeDescriptionTransferCache();

        picklistTypeDescriptions.forEach((picklistTypeDescription) ->
                picklistTypeDescriptionTransfers.add(picklistTypeDescriptionTransferCache.getPicklistTypeDescriptionTransfer(picklistTypeDescription))
        );

        return picklistTypeDescriptionTransfers;
    }

    public void updatePicklistTypeDescriptionFromValue(PicklistTypeDescriptionValue picklistTypeDescriptionValue, BasePK updatedBy) {
        if(picklistTypeDescriptionValue.hasBeenModified()) {
            var picklistTypeDescription = PicklistTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTypeDescriptionValue.getPrimaryKey());

            picklistTypeDescription.setThruTime(session.START_TIME_LONG);
            picklistTypeDescription.store();

            var picklistType = picklistTypeDescription.getPicklistType();
            var language = picklistTypeDescription.getLanguage();
            var description = picklistTypeDescriptionValue.getDescription();

            picklistTypeDescription = PicklistTypeDescriptionFactory.getInstance().create(picklistType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(picklistType.getPrimaryKey(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTypeDescription(PicklistTypeDescription picklistTypeDescription, BasePK deletedBy) {
        picklistTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(picklistTypeDescription.getPicklistTypePK(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTypeDescriptionsByPicklistType(PicklistType picklistType, BasePK deletedBy) {
        var picklistTypeDescriptions = getPicklistTypeDescriptionsByPicklistTypeForUpdate(picklistType);

        picklistTypeDescriptions.forEach((picklistTypeDescription) -> 
                deletePicklistTypeDescription(picklistTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    public PicklistTimeType createPicklistTimeType(PicklistType picklistType, String picklistTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
        var defaultFound = defaultPicklistTimeType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistTimeTypeDetailValue = getDefaultPicklistTimeTypeDetailValueForUpdate(picklistType);

            defaultPicklistTimeTypeDetailValue.setIsDefault(false);
            updatePicklistTimeTypeFromValue(defaultPicklistTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistTimeType = PicklistTimeTypeFactory.getInstance().create();
        var picklistTimeTypeDetail = PicklistTimeTypeDetailFactory.getInstance().create(picklistTimeType, picklistType, picklistTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        picklistTimeType = PicklistTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                picklistTimeType.getPrimaryKey());
        picklistTimeType.setActiveDetail(picklistTimeTypeDetail);
        picklistTimeType.setLastDetail(picklistTimeTypeDetail);
        picklistTimeType.store();

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistTimeType;
    }

    private static final Map<EntityPermission, String> getPicklistTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? AND pcklsttimtypdt_picklisttimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? AND pcklsttimtypdt_picklisttimetypename = ? " +
                "FOR UPDATE");
        getPicklistTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistTimeType getPicklistTimeTypeByName(PicklistType picklistType, String picklistTimeTypeName, EntityPermission entityPermission) {
        return PicklistTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistTimeTypeByNameQueries,
                picklistType, picklistTimeTypeName);
    }

    public PicklistTimeType getPicklistTimeTypeByName(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeByName(picklistType, picklistTimeTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistTimeType getPicklistTimeTypeByNameForUpdate(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeByName(picklistType, picklistTimeTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDetailValue getPicklistTimeTypeDetailValueForUpdate(PicklistTimeType picklistTimeType) {
        return picklistTimeType == null? null: picklistTimeType.getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();
    }

    public PicklistTimeTypeDetailValue getPicklistTimeTypeDetailValueByNameForUpdate(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeDetailValueForUpdate(getPicklistTimeTypeByNameForUpdate(picklistType, picklistTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPicklistTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? AND pcklsttimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? AND pcklsttimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPicklistTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistTimeType getDefaultPicklistTimeType(PicklistType picklistType, EntityPermission entityPermission) {
        return PicklistTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPicklistTimeTypeQueries,
                picklistType);
    }

    public PicklistTimeType getDefaultPicklistTimeType(PicklistType picklistType) {
        return getDefaultPicklistTimeType(picklistType, EntityPermission.READ_ONLY);
    }

    public PicklistTimeType getDefaultPicklistTimeTypeForUpdate(PicklistType picklistType) {
        return getDefaultPicklistTimeType(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDetailValue getDefaultPicklistTimeTypeDetailValueForUpdate(PicklistType picklistType) {
        return getDefaultPicklistTimeTypeForUpdate(picklistType).getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPicklistTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? " +
                "ORDER BY pcklsttimtypdt_sortorder, pcklsttimtypdt_picklisttimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "AND pcklsttimtypdt_pcklsttyp_picklisttypeid = ? " +
                "FOR UPDATE");
        getPicklistTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistTimeType> getPicklistTimeTypes(PicklistType picklistType, EntityPermission entityPermission) {
        return PicklistTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTimeTypesQueries,
                picklistType);
    }

    public List<PicklistTimeType> getPicklistTimeTypes(PicklistType picklistType) {
        return getPicklistTimeTypes(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTimeType> getPicklistTimeTypesForUpdate(PicklistType picklistType) {
        return getPicklistTimeTypes(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeTransfer getPicklistTimeTypeTransfer(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        return getPicklistTransferCaches(userVisit).getPicklistTimeTypeTransferCache().getPicklistTimeTypeTransfer(picklistTimeType);
    }

    public List<PicklistTimeTypeTransfer> getPicklistTimeTypeTransfers(UserVisit userVisit, PicklistType picklistType) {
        var picklistTimeTypes = getPicklistTimeTypes(picklistType);
        List<PicklistTimeTypeTransfer> picklistTimeTypeTransfers = new ArrayList<>(picklistTimeTypes.size());
        var picklistTimeTypeTransferCache = getPicklistTransferCaches(userVisit).getPicklistTimeTypeTransferCache();

        picklistTimeTypes.forEach((picklistTimeType) ->
                picklistTimeTypeTransfers.add(picklistTimeTypeTransferCache.getPicklistTimeTypeTransfer(picklistTimeType))
        );

        return picklistTimeTypeTransfers;
    }

    public PicklistTimeTypeChoicesBean getPicklistTimeTypeChoices(String defaultPicklistTimeTypeChoice, Language language, boolean allowNullChoice,
            PicklistType picklistType) {
        var picklistTimeTypes = getPicklistTimeTypes(picklistType);
        var size = picklistTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistTimeType : picklistTimeTypes) {
            var picklistTimeTypeDetail = picklistTimeType.getLastDetail();

            var label = getBestPicklistTimeTypeDescription(picklistTimeType, language);
            var value = picklistTimeTypeDetail.getPicklistTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistTimeTypeChoice != null && defaultPicklistTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePicklistTimeTypeFromValue(PicklistTimeTypeDetailValue picklistTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistTimeTypeDetailValue.hasBeenModified()) {
            var picklistTimeType = PicklistTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistTimeTypeDetailValue.getPicklistTimeTypePK());
            var picklistTimeTypeDetail = picklistTimeType.getActiveDetailForUpdate();

            picklistTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            picklistTimeTypeDetail.store();

            var picklistType = picklistTimeTypeDetail.getPicklistType(); // Not updated
            var picklistTypePK = picklistType.getPrimaryKey(); // Not updated
            var picklistTimeTypePK = picklistTimeTypeDetail.getPicklistTimeTypePK(); // Not updated
            var picklistTimeTypeName = picklistTimeTypeDetailValue.getPicklistTimeTypeName();
            var isDefault = picklistTimeTypeDetailValue.getIsDefault();
            var sortOrder = picklistTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
                var defaultFound = defaultPicklistTimeType != null && !defaultPicklistTimeType.equals(picklistTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistTimeTypeDetailValue = getDefaultPicklistTimeTypeDetailValueForUpdate(picklistType);

                    defaultPicklistTimeTypeDetailValue.setIsDefault(false);
                    updatePicklistTimeTypeFromValue(defaultPicklistTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistTimeTypeDetail = PicklistTimeTypeDetailFactory.getInstance().create(picklistTimeTypePK, picklistTypePK, picklistTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            picklistTimeType.setActiveDetail(picklistTimeTypeDetail);
            picklistTimeType.setLastDetail(picklistTimeTypeDetail);

            sendEvent(picklistTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistTimeTypeFromValue(PicklistTimeTypeDetailValue picklistTimeTypeDetailValue, BasePK updatedBy) {
        updatePicklistTimeTypeFromValue(picklistTimeTypeDetailValue, true, updatedBy);
    }

    public void deletePicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        deletePicklistTimesByPicklistTimeType(picklistTimeType, deletedBy);
        deletePicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, deletedBy);

        var picklistTimeTypeDetail = picklistTimeType.getLastDetailForUpdate();
        picklistTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        picklistTimeType.setActiveDetail(null);
        picklistTimeType.store();

        // Check for default, and pick one if necessary
        var picklistType = picklistTimeTypeDetail.getPicklistType();
        var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
        if(defaultPicklistTimeType == null) {
            var picklistTimeTypes = getPicklistTimeTypesForUpdate(picklistType);

            if(!picklistTimeTypes.isEmpty()) {
                var iter = picklistTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultPicklistTimeType = iter.next();
                }
                var picklistTimeTypeDetailValue = Objects.requireNonNull(defaultPicklistTimeType).getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();

                picklistTimeTypeDetailValue.setIsDefault(true);
                updatePicklistTimeTypeFromValue(picklistTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    public PicklistTimeTypeDescription createPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language, String description, BasePK createdBy) {
        var picklistTimeTypeDescription = PicklistTimeTypeDescriptionFactory.getInstance().create(picklistTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getPicklistTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimetypedescriptions " +
                "WHERE pcklsttimtypd_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttimtypd_lang_languageid = ? AND pcklsttimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimetypedescriptions " +
                "WHERE pcklsttimtypd_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttimtypd_lang_languageid = ? AND pcklsttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistTimeTypeDescription getPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language, EntityPermission entityPermission) {
        return PicklistTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistTimeTypeDescriptionQueries,
                picklistTimeType, language, Session.MAX_TIME);
    }

    public PicklistTimeTypeDescription getPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescription(picklistTimeType, language, EntityPermission.READ_ONLY);
    }

    public PicklistTimeTypeDescription getPicklistTimeTypeDescriptionForUpdate(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescription(picklistTimeType, language, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDescriptionValue getPicklistTimeTypeDescriptionValue(PicklistTimeTypeDescription picklistTimeTypeDescription) {
        return picklistTimeTypeDescription == null? null: picklistTimeTypeDescription.getPicklistTimeTypeDescriptionValue().clone();
    }

    public PicklistTimeTypeDescriptionValue getPicklistTimeTypeDescriptionValueForUpdate(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescriptionValue(getPicklistTimeTypeDescriptionForUpdate(picklistTimeType, language));
    }

    private static final Map<EntityPermission, String> getPicklistTimeTypeDescriptionsByPicklistTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimetypedescriptions, languages " +
                "WHERE pcklsttimtypd_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttimtypd_thrutime = ? AND pcklsttimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimetypedescriptions " +
                "WHERE pcklsttimtypd_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTimeTypeDescriptionsByPicklistTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        return PicklistTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTimeTypeDescriptionsByPicklistTimeTypeQueries,
                picklistTimeType, Session.MAX_TIME);
    }

    public List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeTypeForUpdate(PicklistTimeType picklistTimeType) {
        return getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language) {
        String description;
        var picklistTimeTypeDescription = getPicklistTimeTypeDescription(picklistTimeType, language);

        if(picklistTimeTypeDescription == null && !language.getIsDefault()) {
            picklistTimeTypeDescription = getPicklistTimeTypeDescription(picklistTimeType, partyControl.getDefaultLanguage());
        }

        if(picklistTimeTypeDescription == null) {
            description = picklistTimeType.getLastDetail().getPicklistTimeTypeName();
        } else {
            description = picklistTimeTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistTimeTypeDescriptionTransfer getPicklistTimeTypeDescriptionTransfer(UserVisit userVisit, PicklistTimeTypeDescription picklistTimeTypeDescription) {
        return getPicklistTransferCaches(userVisit).getPicklistTimeTypeDescriptionTransferCache().getPicklistTimeTypeDescriptionTransfer(picklistTimeTypeDescription);
    }

    public List<PicklistTimeTypeDescriptionTransfer> getPicklistTimeTypeDescriptionTransfersByPicklistTimeType(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        var picklistTimeTypeDescriptions = getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType);
        List<PicklistTimeTypeDescriptionTransfer> picklistTimeTypeDescriptionTransfers = new ArrayList<>(picklistTimeTypeDescriptions.size());
        var picklistTimeTypeDescriptionTransferCache = getPicklistTransferCaches(userVisit).getPicklistTimeTypeDescriptionTransferCache();

        picklistTimeTypeDescriptions.forEach((picklistTimeTypeDescription) ->
                picklistTimeTypeDescriptionTransfers.add(picklistTimeTypeDescriptionTransferCache.getPicklistTimeTypeDescriptionTransfer(picklistTimeTypeDescription))
        );

        return picklistTimeTypeDescriptionTransfers;
    }

    public void updatePicklistTimeTypeDescriptionFromValue(PicklistTimeTypeDescriptionValue picklistTimeTypeDescriptionValue, BasePK updatedBy) {
        if(picklistTimeTypeDescriptionValue.hasBeenModified()) {
            var picklistTimeTypeDescription = PicklistTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTimeTypeDescriptionValue.getPrimaryKey());

            picklistTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            picklistTimeTypeDescription.store();

            var picklistTimeType = picklistTimeTypeDescription.getPicklistTimeType();
            var language = picklistTimeTypeDescription.getLanguage();
            var description = picklistTimeTypeDescriptionValue.getDescription();

            picklistTimeTypeDescription = PicklistTimeTypeDescriptionFactory.getInstance().create(picklistTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTimeTypeDescription(PicklistTimeTypeDescription picklistTimeTypeDescription, BasePK deletedBy) {
        picklistTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(picklistTimeTypeDescription.getPicklistTimeTypePK(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        var picklistTimeTypeDescriptions = getPicklistTimeTypeDescriptionsByPicklistTimeTypeForUpdate(picklistTimeType);

        picklistTimeTypeDescriptions.forEach((picklistTimeTypeDescription) -> 
                deletePicklistTimeTypeDescription(picklistTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    public PicklistAliasType createPicklistAliasType(PicklistType picklistType, String picklistAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
        var defaultFound = defaultPicklistAliasType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistAliasTypeDetailValue = getDefaultPicklistAliasTypeDetailValueForUpdate(picklistType);

            defaultPicklistAliasTypeDetailValue.setIsDefault(false);
            updatePicklistAliasTypeFromValue(defaultPicklistAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistAliasType = PicklistAliasTypeFactory.getInstance().create();
        var picklistAliasTypeDetail = PicklistAliasTypeDetailFactory.getInstance().create(picklistAliasType, picklistType, picklistAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        picklistAliasType = PicklistAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, picklistAliasType.getPrimaryKey());
        picklistAliasType.setActiveDetail(picklistAliasTypeDetail);
        picklistAliasType.setLastDetail(picklistAliasTypeDetail);
        picklistAliasType.store();

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistAliasType;
    }

    private static final Map<EntityPermission, String> getPicklistAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "AND pcklstaltypdt_picklistaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "AND pcklstaltypdt_picklistaliastypename = ? " +
                "FOR UPDATE");
        getPicklistAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistAliasType getPicklistAliasTypeByName(PicklistType picklistType, String picklistAliasTypeName, EntityPermission entityPermission) {
        return PicklistAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistAliasTypeByNameQueries,
                picklistType, picklistAliasTypeName);
    }

    public PicklistAliasType getPicklistAliasTypeByName(PicklistType picklistType, String picklistAliasTypeName) {
        return getPicklistAliasTypeByName(picklistType, picklistAliasTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistAliasType getPicklistAliasTypeByNameForUpdate(PicklistType picklistType, String picklistAliasTypeName) {
        return getPicklistAliasTypeByName(picklistType, picklistAliasTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDetailValue getPicklistAliasTypeDetailValueForUpdate(PicklistAliasType picklistAliasType) {
        return picklistAliasType == null? null: picklistAliasType.getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();
    }

    public PicklistAliasTypeDetailValue getPicklistAliasTypeDetailValueByNameForUpdate(PicklistType picklistType,
            String picklistAliasTypeName) {
        return getPicklistAliasTypeDetailValueForUpdate(getPicklistAliasTypeByNameForUpdate(picklistType, picklistAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultPicklistAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "AND pcklstaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "AND pcklstaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultPicklistAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistAliasType getDefaultPicklistAliasType(PicklistType picklistType, EntityPermission entityPermission) {
        return PicklistAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPicklistAliasTypeQueries, picklistType);
    }

    public PicklistAliasType getDefaultPicklistAliasType(PicklistType picklistType) {
        return getDefaultPicklistAliasType(picklistType, EntityPermission.READ_ONLY);
    }

    public PicklistAliasType getDefaultPicklistAliasTypeForUpdate(PicklistType picklistType) {
        return getDefaultPicklistAliasType(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDetailValue getDefaultPicklistAliasTypeDetailValueForUpdate(PicklistType picklistType) {
        return getDefaultPicklistAliasTypeForUpdate(picklistType).getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getPicklistAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "ORDER BY pcklstaltypdt_sortorder, pcklstaltypdt_picklistaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstaltyp_activedetailid = pcklstaltypdt_picklistaliastypedetailid AND pcklstaltypdt_pcklsttyp_picklisttypeid = ? " +
                "FOR UPDATE");
        getPicklistAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistAliasType> getPicklistAliasTypes(PicklistType picklistType, EntityPermission entityPermission) {
        return PicklistAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistAliasTypesQueries, picklistType);
    }

    public List<PicklistAliasType> getPicklistAliasTypes(PicklistType picklistType) {
        return getPicklistAliasTypes(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAliasType> getPicklistAliasTypesForUpdate(PicklistType picklistType) {
        return getPicklistAliasTypes(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeTransfer getPicklistAliasTypeTransfer(UserVisit userVisit, PicklistAliasType picklistAliasType) {
        return getPicklistTransferCaches(userVisit).getPicklistAliasTypeTransferCache().getPicklistAliasTypeTransfer(picklistAliasType);
    }

    public List<PicklistAliasTypeTransfer> getPicklistAliasTypeTransfers(UserVisit userVisit, PicklistType picklistType) {
        var picklistAliasTypes = getPicklistAliasTypes(picklistType);
        List<PicklistAliasTypeTransfer> picklistAliasTypeTransfers = new ArrayList<>(picklistAliasTypes.size());
        var picklistAliasTypeTransferCache = getPicklistTransferCaches(userVisit).getPicklistAliasTypeTransferCache();

        picklistAliasTypes.forEach((picklistAliasType) ->
                picklistAliasTypeTransfers.add(picklistAliasTypeTransferCache.getPicklistAliasTypeTransfer(picklistAliasType))
        );

        return picklistAliasTypeTransfers;
    }

    public PicklistAliasTypeChoicesBean getPicklistAliasTypeChoices(String defaultPicklistAliasTypeChoice, Language language,
            boolean allowNullChoice, PicklistType picklistType) {
        var picklistAliasTypes = getPicklistAliasTypes(picklistType);
        var size = picklistAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistAliasType : picklistAliasTypes) {
            var picklistAliasTypeDetail = picklistAliasType.getLastDetail();

            var label = getBestPicklistAliasTypeDescription(picklistAliasType, language);
            var value = picklistAliasTypeDetail.getPicklistAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistAliasTypeChoice != null && defaultPicklistAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePicklistAliasTypeFromValue(PicklistAliasTypeDetailValue picklistAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistAliasTypeDetailValue.hasBeenModified()) {
            var picklistAliasType = PicklistAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistAliasTypeDetailValue.getPicklistAliasTypePK());
            var picklistAliasTypeDetail = picklistAliasType.getActiveDetailForUpdate();

            picklistAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            picklistAliasTypeDetail.store();

            var picklistAliasTypePK = picklistAliasTypeDetail.getPicklistAliasTypePK();
            var picklistType = picklistAliasTypeDetail.getPicklistType();
            var picklistTypePK = picklistType.getPrimaryKey();
            var picklistAliasTypeName = picklistAliasTypeDetailValue.getPicklistAliasTypeName();
            var validationPattern = picklistAliasTypeDetailValue.getValidationPattern();
            var isDefault = picklistAliasTypeDetailValue.getIsDefault();
            var sortOrder = picklistAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
                var defaultFound = defaultPicklistAliasType != null && !defaultPicklistAliasType.equals(picklistAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistAliasTypeDetailValue = getDefaultPicklistAliasTypeDetailValueForUpdate(picklistType);

                    defaultPicklistAliasTypeDetailValue.setIsDefault(false);
                    updatePicklistAliasTypeFromValue(defaultPicklistAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistAliasTypeDetail = PicklistAliasTypeDetailFactory.getInstance().create(picklistAliasTypePK, picklistTypePK, picklistAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            picklistAliasType.setActiveDetail(picklistAliasTypeDetail);
            picklistAliasType.setLastDetail(picklistAliasTypeDetail);

            sendEvent(picklistAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistAliasTypeFromValue(PicklistAliasTypeDetailValue picklistAliasTypeDetailValue, BasePK updatedBy) {
        updatePicklistAliasTypeFromValue(picklistAliasTypeDetailValue, true, updatedBy);
    }

    public void deletePicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        deletePicklistAliasesByPicklistAliasType(picklistAliasType, deletedBy);
        deletePicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, deletedBy);

        var picklistAliasTypeDetail = picklistAliasType.getLastDetailForUpdate();
        picklistAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        picklistAliasType.setActiveDetail(null);
        picklistAliasType.store();

        // Check for default, and pick one if necessary
        var picklistType = picklistAliasTypeDetail.getPicklistType();
        var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
        if(defaultPicklistAliasType == null) {
            var picklistAliasTypes = getPicklistAliasTypesForUpdate(picklistType);

            if(!picklistAliasTypes.isEmpty()) {
                var iter = picklistAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultPicklistAliasType = iter.next();
                }
                var picklistAliasTypeDetailValue = Objects.requireNonNull(defaultPicklistAliasType).getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();

                picklistAliasTypeDetailValue.setIsDefault(true);
                updatePicklistAliasTypeFromValue(picklistAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePicklistAliasTypes(List<PicklistAliasType> picklistAliasTypes, BasePK deletedBy) {
        picklistAliasTypes.forEach((picklistAliasType) -> 
                deletePicklistAliasType(picklistAliasType, deletedBy)
        );
    }

    public void deletePicklistAliasTypesByPicklistType(PicklistType picklistType, BasePK deletedBy) {
        deletePicklistAliasTypes(getPicklistAliasTypesForUpdate(picklistType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public PicklistAliasTypeDescription createPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language, String description, BasePK createdBy) {
        var picklistAliasTypeDescription = PicklistAliasTypeDescriptionFactory.getInstance().create(picklistAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getPicklistAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliastypedescriptions " +
                "WHERE pcklstaltypd_pcklstaltyp_picklistaliastypeid = ? AND pcklstaltypd_lang_languageid = ? AND pcklstaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliastypedescriptions " +
                "WHERE pcklstaltypd_pcklstaltyp_picklistaliastypeid = ? AND pcklstaltypd_lang_languageid = ? AND pcklstaltypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistAliasTypeDescription getPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language, EntityPermission entityPermission) {
        return PicklistAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistAliasTypeDescriptionQueries,
                picklistAliasType, language, Session.MAX_TIME);
    }

    public PicklistAliasTypeDescription getPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescription(picklistAliasType, language, EntityPermission.READ_ONLY);
    }

    public PicklistAliasTypeDescription getPicklistAliasTypeDescriptionForUpdate(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescription(picklistAliasType, language, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDescriptionValue getPicklistAliasTypeDescriptionValue(PicklistAliasTypeDescription picklistAliasTypeDescription) {
        return picklistAliasTypeDescription == null? null: picklistAliasTypeDescription.getPicklistAliasTypeDescriptionValue().clone();
    }

    public PicklistAliasTypeDescriptionValue getPicklistAliasTypeDescriptionValueForUpdate(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescriptionValue(getPicklistAliasTypeDescriptionForUpdate(picklistAliasType, language));
    }

    private static final Map<EntityPermission, String> getPicklistAliasTypeDescriptionsByPicklistAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliastypedescriptions, languages " +
                "WHERE pcklstaltypd_pcklstaltyp_picklistaliastypeid = ? AND pcklstaltypd_thrutime = ? AND pcklstaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliastypedescriptions " +
                "WHERE pcklstaltypd_pcklstaltyp_picklistaliastypeid = ? AND pcklstaltypd_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasTypeDescriptionsByPicklistAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        return PicklistAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistAliasTypeDescriptionsByPicklistAliasTypeQueries,
                picklistAliasType, Session.MAX_TIME);
    }

    public List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType) {
        return getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasTypeForUpdate(PicklistAliasType picklistAliasType) {
        return getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language) {
        String description;
        var picklistAliasTypeDescription = getPicklistAliasTypeDescription(picklistAliasType, language);

        if(picklistAliasTypeDescription == null && !language.getIsDefault()) {
            picklistAliasTypeDescription = getPicklistAliasTypeDescription(picklistAliasType, partyControl.getDefaultLanguage());
        }

        if(picklistAliasTypeDescription == null) {
            description = picklistAliasType.getLastDetail().getPicklistAliasTypeName();
        } else {
            description = picklistAliasTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistAliasTypeDescriptionTransfer getPicklistAliasTypeDescriptionTransfer(UserVisit userVisit, PicklistAliasTypeDescription picklistAliasTypeDescription) {
        return getPicklistTransferCaches(userVisit).getPicklistAliasTypeDescriptionTransferCache().getPicklistAliasTypeDescriptionTransfer(picklistAliasTypeDescription);
    }

    public List<PicklistAliasTypeDescriptionTransfer> getPicklistAliasTypeDescriptionTransfersByPicklistAliasType(UserVisit userVisit, PicklistAliasType picklistAliasType) {
        var picklistAliasTypeDescriptions = getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType);
        List<PicklistAliasTypeDescriptionTransfer> picklistAliasTypeDescriptionTransfers = new ArrayList<>(picklistAliasTypeDescriptions.size());
        var picklistAliasTypeDescriptionTransferCache = getPicklistTransferCaches(userVisit).getPicklistAliasTypeDescriptionTransferCache();

        picklistAliasTypeDescriptions.forEach((picklistAliasTypeDescription) ->
                picklistAliasTypeDescriptionTransfers.add(picklistAliasTypeDescriptionTransferCache.getPicklistAliasTypeDescriptionTransfer(picklistAliasTypeDescription))
        );

        return picklistAliasTypeDescriptionTransfers;
    }

    public void updatePicklistAliasTypeDescriptionFromValue(PicklistAliasTypeDescriptionValue picklistAliasTypeDescriptionValue, BasePK updatedBy) {
        if(picklistAliasTypeDescriptionValue.hasBeenModified()) {
            var picklistAliasTypeDescription = PicklistAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistAliasTypeDescriptionValue.getPrimaryKey());

            picklistAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            picklistAliasTypeDescription.store();

            var picklistAliasType = picklistAliasTypeDescription.getPicklistAliasType();
            var language = picklistAliasTypeDescription.getLanguage();
            var description = picklistAliasTypeDescriptionValue.getDescription();

            picklistAliasTypeDescription = PicklistAliasTypeDescriptionFactory.getInstance().create(picklistAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistAliasTypeDescription(PicklistAliasTypeDescription picklistAliasTypeDescription, BasePK deletedBy) {
        picklistAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(picklistAliasTypeDescription.getPicklistAliasTypePK(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        var picklistAliasTypeDescriptions = getPicklistAliasTypeDescriptionsByPicklistAliasTypeForUpdate(picklistAliasType);

        picklistAliasTypeDescriptions.forEach((picklistAliasTypeDescription) -> 
                deletePicklistAliasTypeDescription(picklistAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Times
    // --------------------------------------------------------------------------------

    public PicklistTime createPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType, Long time, BasePK createdBy) {
        var picklistTime = PicklistTimeFactory.getInstance().create(picklist, picklistTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(picklist.getPrimaryKey(), EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTime;
    }

    public long countPicklistTimesByPicklist(Picklist picklist) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklst_picklistid = ? AND pcklsttim_thrutime = ?",
                picklist, Session.MAX_TIME_LONG);
    }

    public long countPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttim_thrutime = ?",
                picklistTimeType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getPicklistTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklst_picklistid = ? AND pcklsttim_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklst_picklistid = ? AND pcklsttim_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttim_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistTime getPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        return PicklistTimeFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistTimeQueries, picklist, picklistTimeType, Session.MAX_TIME);
    }

    public PicklistTime getPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTime(picklist, picklistTimeType, EntityPermission.READ_ONLY);
    }

    public PicklistTime getPicklistTimeForUpdate(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTime(picklist, picklistTimeType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeValue getPicklistTimeValue(PicklistTime picklistTime) {
        return picklistTime == null? null: picklistTime.getPicklistTimeValue().clone();
    }

    public PicklistTimeValue getPicklistTimeValueForUpdate(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTimeValue(getPicklistTimeForUpdate(picklist, picklistTimeType));
    }

    private static final Map<EntityPermission, String> getPicklistTimesByPicklistQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimes, picklisttimetypes, picklisttimetypedetails " +
                "WHERE pcklsttim_pcklst_picklistid = ? AND pcklsttim_thrutime = ? " +
                "AND pcklsttim_pcklsttimtyp_picklisttimetypeid = pcklsttimtyp_picklisttimetypeid AND pcklsttimtyp_activedetailid = pcklsttimtypdt_picklisttimetypedetailid " +
                "ORDER BY pcklsttimtypdt_sortorder, pcklsttimtypdt_picklisttimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklst_picklistid = ? AND pcklsttim_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTimesByPicklistQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistTime> getPicklistTimesByPicklist(Picklist picklist, EntityPermission entityPermission) {
        return PicklistTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTimesByPicklistQueries, picklist, Session.MAX_TIME);
    }

    public List<PicklistTime> getPicklistTimesByPicklist(Picklist picklist) {
        return getPicklistTimesByPicklist(picklist, EntityPermission.READ_ONLY);
    }

    public List<PicklistTime> getPicklistTimesByPicklistForUpdate(Picklist picklist) {
        return getPicklistTimesByPicklist(picklist, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPicklistTimesByPicklistTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklisttimes, picklists, picklistdetails " +
                "WHERE pcklsttim_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttim_thrutime = ? " +
                "AND pcklsttim_pcklst_picklistid = pcklsttim_pcklst_picklistid AND pcklst_activedetailid = pcklstdt_picklistdetailid " +
                "ORDER BY pcklstdt_picklistname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklisttimes " +
                "WHERE pcklsttim_pcklsttimtyp_picklisttimetypeid = ? AND pcklsttim_thrutime = ? " +
                "FOR UPDATE");
        getPicklistTimesByPicklistTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistTime> getPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        return PicklistTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistTimesByPicklistTimeTypeQueries, picklistTimeType, Session.MAX_TIME);
    }

    public List<PicklistTime> getPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return getPicklistTimesByPicklistTimeType(picklistTimeType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTime> getPicklistTimesByPicklistTimeTypeForUpdate(PicklistTimeType picklistTimeType) {
        return getPicklistTimesByPicklistTimeType(picklistTimeType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTransfer getPicklistTimeTransfer(UserVisit userVisit, PicklistTime picklistTime) {
        return getPicklistTransferCaches(userVisit).getPicklistTimeTransferCache().getPicklistTimeTransfer(picklistTime);
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfers(UserVisit userVisit, Collection<PicklistTime> picklistTimes) {
        List<PicklistTimeTransfer> picklistTimeTransfers = new ArrayList<>(picklistTimes.size());
        var picklistTimeTransferCache = getPicklistTransferCaches(userVisit).getPicklistTimeTransferCache();

        picklistTimes.forEach((picklistTime) ->
                picklistTimeTransfers.add(picklistTimeTransferCache.getPicklistTimeTransfer(picklistTime))
        );

        return picklistTimeTransfers;
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfersByPicklist(UserVisit userVisit, Picklist picklist) {
        return getPicklistTimeTransfers(userVisit, getPicklistTimesByPicklist(picklist));
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfersByPicklistTimeType(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        return getPicklistTimeTransfers(userVisit, getPicklistTimesByPicklistTimeType(picklistTimeType));
    }

    public void updatePicklistTimeFromValue(PicklistTimeValue picklistTimeValue, BasePK updatedBy) {
        if(picklistTimeValue.hasBeenModified()) {
            var picklistTime = PicklistTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTimeValue.getPrimaryKey());

            picklistTime.setThruTime(session.START_TIME_LONG);
            picklistTime.store();

            var picklistPK = picklistTime.getPicklistPK(); // Not updated
            var picklistTimeTypePK = picklistTime.getPicklistTimeTypePK(); // Not updated
            var time = picklistTimeValue.getTime();

            picklistTime = PicklistTimeFactory.getInstance().create(picklistPK, picklistTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(picklistPK, EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTime(PicklistTime picklistTime, BasePK deletedBy) {
        picklistTime.setThruTime(session.START_TIME_LONG);

        sendEvent(picklistTime.getPicklistTimeTypePK(), EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTimes(List<PicklistTime> picklistTimes, BasePK deletedBy) {
        picklistTimes.forEach((picklistTime) -> 
                deletePicklistTime(picklistTime, deletedBy)
        );
    }

    public void deletePicklistTimesByPicklist(Picklist picklist, BasePK deletedBy) {
        deletePicklistTimes(getPicklistTimesByPicklistForUpdate(picklist), deletedBy);
    }

    public void deletePicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        deletePicklistTimes(getPicklistTimesByPicklistTimeTypeForUpdate(picklistTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    public PicklistAlias createPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType, String alias, BasePK createdBy) {
        var picklistAlias = PicklistAliasFactory.getInstance().create(picklist, picklistAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(picklist.getPrimaryKey(), EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistAlias;
    }

    private static final Map<EntityPermission, String> getPicklistAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklst_picklistid = ? AND pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklst_picklistid = ? AND pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistAlias getPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        return PicklistAliasFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistAliasQueries,
                picklist, picklistAliasType, Session.MAX_TIME);
    }

    public PicklistAlias getPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAlias(picklist, picklistAliasType, EntityPermission.READ_ONLY);
    }

    public PicklistAlias getPicklistAliasForUpdate(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAlias(picklist, picklistAliasType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasValue getPicklistAliasValue(PicklistAlias picklistAlias) {
        return picklistAlias == null? null: picklistAlias.getPicklistAliasValue().clone();
    }

    public PicklistAliasValue getPicklistAliasValueForUpdate(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAliasValue(getPicklistAliasForUpdate(picklist, picklistAliasType));
    }

    private static final Map<EntityPermission, String> getPicklistAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_alias = ? AND pcklstal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_alias = ? AND pcklstal_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private PicklistAlias getPicklistAliasByAlias(PicklistAliasType picklistAliasType, String alias, EntityPermission entityPermission) {
        return PicklistAliasFactory.getInstance().getEntityFromQuery(entityPermission, getPicklistAliasByAliasQueries, picklistAliasType, alias, Session.MAX_TIME);
    }

    public PicklistAlias getPicklistAliasByAlias(PicklistAliasType picklistAliasType, String alias) {
        return getPicklistAliasByAlias(picklistAliasType, alias, EntityPermission.READ_ONLY);
    }

    public PicklistAlias getPicklistAliasByAliasForUpdate(PicklistAliasType picklistAliasType, String alias) {
        return getPicklistAliasByAlias(picklistAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPicklistAliasesByPicklistQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliases, picklistaliastypes, picklistaliastypedetails " +
                "WHERE pcklstal_pcklst_picklistid = ? AND pcklstal_thrutime = ? " +
                "AND pcklstal_pcklstaltyp_picklistaliastypeid = pcklstaltyp_picklistaliastypeid AND pcklstaltyp_lastdetailid = pcklstaltypdt_picklistaliastypedetailid" +
                "ORDER BY pcklstaltypdt_sortorder, pcklstaltypdt_picklistaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklst_picklistid = ? AND pcklstal_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasesByPicklistQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistAlias> getPicklistAliasesByPicklist(Picklist picklist, EntityPermission entityPermission) {
        return PicklistAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistAliasesByPicklistQueries,
                picklist, Session.MAX_TIME);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklist(Picklist picklist) {
        return getPicklistAliasesByPicklist(picklist, EntityPermission.READ_ONLY);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistForUpdate(Picklist picklist) {
        return getPicklistAliasesByPicklist(picklist, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPicklistAliasesByPicklistAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM picklistaliases, picklistes, picklistdetails " +
                "WHERE pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_thrutime = ? " +
                "AND pcklstal_pcklst_picklistid = pcklst_picklistid AND pcklst_lastdetailid = pcklstdt_picklistdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM picklistaliases " +
                "WHERE pcklstal_pcklstaltyp_picklistaliastypeid = ? AND pcklstal_thrutime = ? " +
                "FOR UPDATE");
        getPicklistAliasesByPicklistAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PicklistAlias> getPicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        return PicklistAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getPicklistAliasesByPicklistAliasTypeQueries,
                picklistAliasType, Session.MAX_TIME);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType) {
        return getPicklistAliasesByPicklistAliasType(picklistAliasType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistAliasTypeForUpdate(PicklistAliasType picklistAliasType) {
        return getPicklistAliasesByPicklistAliasType(picklistAliasType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTransfer getPicklistAliasTransfer(UserVisit userVisit, PicklistAlias picklistAlias) {
        return getPicklistTransferCaches(userVisit).getPicklistAliasTransferCache().getPicklistAliasTransfer(picklistAlias);
    }

    public List<PicklistAliasTransfer> getPicklistAliasTransfersByPicklist(UserVisit userVisit, Picklist picklist) {
        var picklistaliases = getPicklistAliasesByPicklist(picklist);
        List<PicklistAliasTransfer> picklistAliasTransfers = new ArrayList<>(picklistaliases.size());
        var picklistAliasTransferCache = getPicklistTransferCaches(userVisit).getPicklistAliasTransferCache();

        picklistaliases.forEach((picklistAlias) ->
                picklistAliasTransfers.add(picklistAliasTransferCache.getPicklistAliasTransfer(picklistAlias))
        );

        return picklistAliasTransfers;
    }

    public void updatePicklistAliasFromValue(PicklistAliasValue picklistAliasValue, BasePK updatedBy) {
        if(picklistAliasValue.hasBeenModified()) {
            var picklistAlias = PicklistAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, picklistAliasValue.getPrimaryKey());

            picklistAlias.setThruTime(session.START_TIME_LONG);
            picklistAlias.store();

            var picklistPK = picklistAlias.getPicklistPK();
            var picklistAliasTypePK = picklistAlias.getPicklistAliasTypePK();
            var alias  = picklistAliasValue.getAlias();

            picklistAlias = PicklistAliasFactory.getInstance().create(picklistPK, picklistAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(picklistPK, EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistAlias(PicklistAlias picklistAlias, BasePK deletedBy) {
        picklistAlias.setThruTime(session.START_TIME_LONG);

        sendEvent(picklistAlias.getPicklistPK(), EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        var picklistaliases = getPicklistAliasesByPicklistAliasTypeForUpdate(picklistAliasType);

        picklistaliases.forEach((picklistAlias) -> 
                deletePicklistAlias(picklistAlias, deletedBy)
        );
    }

    public void deletePicklistAliasesByPicklist(Picklist picklist, BasePK deletedBy) {
        var picklistaliases = getPicklistAliasesByPicklistForUpdate(picklist);

        picklistaliases.forEach((picklistAlias) -> 
                deletePicklistAlias(picklistAlias, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklists
    // --------------------------------------------------------------------------------

    public Picklist getPicklistByName(PicklistType picklistType, String picklistName) {
        // TODO
        return null;
    }

    public PicklistTransfer getPicklistTransfer(UserVisit userVisit, Picklist picklist) {
        // TODO
        return null;
    }

}
