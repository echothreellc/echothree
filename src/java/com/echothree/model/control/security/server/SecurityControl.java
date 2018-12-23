// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.security.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.choice.PartySecurityRoleTemplateChoicesBean;
import com.echothree.model.control.security.common.choice.SecurityRoleChoicesBean;
import com.echothree.model.control.security.common.choice.SecurityRoleGroupChoicesBean;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateRoleTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTrainingClassTransfer;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupDescriptionTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRolePartyTypeTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.control.security.server.transfer.PartySecurityRoleTemplateDescriptionTransferCache;
import com.echothree.model.control.security.server.transfer.PartySecurityRoleTemplateRoleTransferCache;
import com.echothree.model.control.security.server.transfer.PartySecurityRoleTemplateTrainingClassTransferCache;
import com.echothree.model.control.security.server.transfer.PartySecurityRoleTemplateTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityRoleDescriptionTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityRoleGroupDescriptionTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityRoleGroupTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityRolePartyTypeTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityRoleTransferCache;
import com.echothree.model.control.security.server.transfer.SecurityTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.security.common.pk.PartySecurityRoleTemplatePK;
import com.echothree.model.data.security.common.pk.PartySecurityRoleTemplateUsePK;
import com.echothree.model.data.security.common.pk.SecurityRoleGroupPK;
import com.echothree.model.data.security.common.pk.SecurityRolePK;
import com.echothree.model.data.security.server.entity.PartyEntitySecurityRole;
import com.echothree.model.data.security.server.entity.PartySecurityRole;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDetail;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateUse;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDescription;
import com.echothree.model.data.security.server.entity.SecurityRoleDetail;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDescription;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDetail;
import com.echothree.model.data.security.server.entity.SecurityRolePartyType;
import com.echothree.model.data.security.server.factory.PartyEntitySecurityRoleFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateDescriptionFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateDetailFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateRoleFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateTrainingClassFactory;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateUseFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleDescriptionFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleDetailFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleGroupDescriptionFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleGroupDetailFactory;
import com.echothree.model.data.security.server.factory.SecurityRoleGroupFactory;
import com.echothree.model.data.security.server.factory.SecurityRolePartyTypeFactory;
import com.echothree.model.data.security.server.value.PartySecurityRoleTemplateDescriptionValue;
import com.echothree.model.data.security.server.value.PartySecurityRoleTemplateDetailValue;
import com.echothree.model.data.security.server.value.PartySecurityRoleTemplateUseValue;
import com.echothree.model.data.security.server.value.SecurityRoleDescriptionValue;
import com.echothree.model.data.security.server.value.SecurityRoleDetailValue;
import com.echothree.model.data.security.server.value.SecurityRoleGroupDescriptionValue;
import com.echothree.model.data.security.server.value.SecurityRoleGroupDetailValue;
import com.echothree.model.data.security.server.value.SecurityRolePartyTypeValue;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityControl
        extends BaseModelControl {
    
    /** Creates a new instance of SecurityControl */
    public SecurityControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Security Transfer Caches
    // --------------------------------------------------------------------------------
    
    private SecurityTransferCaches securityTransferCaches = null;
    
    public SecurityTransferCaches getSecurityTransferCaches(UserVisit userVisit) {
        if(securityTransferCaches == null) {
            securityTransferCaches = new SecurityTransferCaches(userVisit, this);
        }
        
        return securityTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    public SecurityRoleGroup createSecurityRoleGroup(String securityRoleGroupName, SecurityRoleGroup parentSecurityRoleGroup,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        SecurityRoleGroup defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();
        boolean defaultFound = defaultSecurityRoleGroup != null;
        
        if(defaultFound && isDefault) {
            SecurityRoleGroupDetailValue defaultSecurityRoleGroupDetailValue = getDefaultSecurityRoleGroupDetailValueForUpdate();
            
            defaultSecurityRoleGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateSecurityRoleGroupFromValue(defaultSecurityRoleGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        SecurityRoleGroup securityRoleGroup = SecurityRoleGroupFactory.getInstance().create();
        SecurityRoleGroupDetail securityRoleGroupDetail = SecurityRoleGroupDetailFactory.getInstance().create(session,
                securityRoleGroup, securityRoleGroupName, parentSecurityRoleGroup, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        securityRoleGroup = SecurityRoleGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                securityRoleGroup.getPrimaryKey());
        securityRoleGroup.setActiveDetail(securityRoleGroupDetail);
        securityRoleGroup.setLastDetail(securityRoleGroupDetail);
        securityRoleGroup.store();
        
        sendEventUsingNames(securityRoleGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return securityRoleGroup;
    }
    
    public long countSecurityRoleGroups() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SecurityRoleGroup */
    public SecurityRoleGroup getSecurityRoleGroupByEntityInstance(EntityInstance entityInstance) {
        SecurityRoleGroupPK pk = new SecurityRoleGroupPK(entityInstance.getEntityUniqueId());
        SecurityRoleGroup securityRoleGroup = SecurityRoleGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return securityRoleGroup;
    }
    
    private static final Map<EntityPermission, String> getSecurityRoleGroupByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "AND srgdt_securityrolegroupname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "AND srgdt_securityrolegroupname = ? " +
                "FOR UPDATE");
        getSecurityRoleGroupByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private SecurityRoleGroup getSecurityRoleGroupByName(String securityRoleGroupName, EntityPermission entityPermission) {
        return SecurityRoleGroupFactory.getInstance().getEntityFromQuery(entityPermission, getSecurityRoleGroupByNameQueries, securityRoleGroupName);
    }

    public SecurityRoleGroup getSecurityRoleGroupByName(String securityRoleGroupName) {
        return getSecurityRoleGroupByName(securityRoleGroupName, EntityPermission.READ_ONLY);
    }

    public SecurityRoleGroup getSecurityRoleGroupByNameForUpdate(String securityRoleGroupName) {
        return getSecurityRoleGroupByName(securityRoleGroupName, EntityPermission.READ_WRITE);
    }

    public SecurityRoleGroupDetailValue getSecurityRoleGroupDetailValueForUpdate(SecurityRoleGroup securityRoleGroup) {
        return securityRoleGroup == null? null: securityRoleGroup.getLastDetailForUpdate().getSecurityRoleGroupDetailValue().clone();
    }

    public SecurityRoleGroupDetailValue getSecurityRoleGroupDetailValueByNameForUpdate(String securityRoleGroupName) {
        return getSecurityRoleGroupDetailValueForUpdate(getSecurityRoleGroupByNameForUpdate(securityRoleGroupName));
    }

    private static final Map<EntityPermission, String> getDefaultSecurityRoleGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "AND srgdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "AND srgdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultSecurityRoleGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private SecurityRoleGroup getDefaultSecurityRoleGroup(EntityPermission entityPermission) {
        return SecurityRoleGroupFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultSecurityRoleGroupQueries);
    }

    public SecurityRoleGroup getDefaultSecurityRoleGroup() {
        return getDefaultSecurityRoleGroup(EntityPermission.READ_ONLY);
    }

    public SecurityRoleGroup getDefaultSecurityRoleGroupForUpdate() {
        return getDefaultSecurityRoleGroup(EntityPermission.READ_WRITE);
    }

    public SecurityRoleGroupDetailValue getDefaultSecurityRoleGroupDetailValueForUpdate() {
        return getDefaultSecurityRoleGroupForUpdate().getLastDetailForUpdate().getSecurityRoleGroupDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getSecurityRoleGroupsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "ORDER BY srgdt_sortorder, srgdt_securityrolegroupname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                "FOR UPDATE");
        getSecurityRoleGroupsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SecurityRoleGroup> getSecurityRoleGroups(EntityPermission entityPermission) {
        return SecurityRoleGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getSecurityRoleGroupsQueries);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroups() {
        return getSecurityRoleGroups(EntityPermission.READ_ONLY);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsForUpdate() {
        return getSecurityRoleGroups(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getSecurityRoleGroupsByParentSecurityRoleGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid AND srgdt_parentsecurityrolegroupid = ? " +
                "ORDER BY srgdt_sortorder, srgdt_securityrolegroupname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolegroups, securityrolegroupdetails " +
                "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid AND srgdt_parentsecurityrolegroupid = ? " +
                "FOR UPDATE");
        getSecurityRoleGroupsByParentSecurityRoleGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup,
            EntityPermission entityPermission) {
        return SecurityRoleGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getSecurityRoleGroupsByParentSecurityRoleGroupQueries,
                parentSecurityRoleGroup);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup) {
        return getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup, EntityPermission.READ_ONLY);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroupForUpdate(SecurityRoleGroup parentSecurityRoleGroup) {
        return getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup, EntityPermission.READ_WRITE);
    }

    public SecurityRoleGroupTransfer getSecurityRoleGroupTransfer(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        return getSecurityTransferCaches(userVisit).getSecurityRoleGroupTransferCache().getSecurityRoleGroupTransfer(securityRoleGroup);
    }
    
    public List<SecurityRoleGroupTransfer> getSecurityRoleGroupTransfers(UserVisit userVisit, List<SecurityRoleGroup> securityRoleGroups) {
        List<SecurityRoleGroupTransfer> securityRoleGroupTransfers = new ArrayList<>(securityRoleGroups.size());
        SecurityRoleGroupTransferCache securityRoleGroupTransferCache = getSecurityTransferCaches(userVisit).getSecurityRoleGroupTransferCache();
        
        securityRoleGroups.stream().forEach((securityRoleGroup) -> {
            securityRoleGroupTransfers.add(securityRoleGroupTransferCache.getSecurityRoleGroupTransfer(securityRoleGroup));
        });
        
        return securityRoleGroupTransfers;
    }
    
    public List<SecurityRoleGroupTransfer> getSecurityRoleGroupTransfers(UserVisit userVisit) {
        return getSecurityRoleGroupTransfers(userVisit, getSecurityRoleGroups());
    }
    
    public List<SecurityRoleGroupTransfer> getSecurityRoleGroupTransfersByParentSecurityRoleGroup(UserVisit userVisit,
            SecurityRoleGroup parentSecurityRoleGroup) {
        return getSecurityRoleGroupTransfers(userVisit, getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup));
    }
    
    public SecurityRoleGroupChoicesBean getSecurityRoleGroupChoices(String defaultSecurityRoleGroupChoice,
            Language language, boolean allowNullChoice) {
        List<SecurityRoleGroup> securityRoleGroups = getSecurityRoleGroups();
        int size = securityRoleGroups.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSecurityRoleGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(SecurityRoleGroup securityRoleGroup: securityRoleGroups) {
            SecurityRoleGroupDetail securityRoleGroupDetail = securityRoleGroup.getLastDetail();
            String value = securityRoleGroupDetail.getSecurityRoleGroupName();
            
            if(!value.equals(SecurityRoleGroups.ROOT.name())) {
                String label = getBestSecurityRoleGroupDescription(securityRoleGroup, language);

                labels.add(label == null? value: label);
                values.add(value);

                boolean usingDefaultChoice = defaultSecurityRoleGroupChoice == null? false: defaultSecurityRoleGroupChoice.equals(value);
                if(usingDefaultChoice || (defaultValue == null && securityRoleGroupDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new SecurityRoleGroupChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentSecurityRoleGroupSafe(SecurityRoleGroup securityRoleGroup,
            SecurityRoleGroup parentSecurityRoleGroup) {
        boolean safe = true;
        
        if(parentSecurityRoleGroup != null) {
            Set<SecurityRoleGroup> parentSecurityRoleGroups = new HashSet<>();
            
            parentSecurityRoleGroups.add(securityRoleGroup);
            do {
                if(parentSecurityRoleGroups.contains(parentSecurityRoleGroup)) {
                    safe = false;
                    break;
                }
                
                parentSecurityRoleGroups.add(parentSecurityRoleGroup);
                parentSecurityRoleGroup = parentSecurityRoleGroup.getLastDetail().getParentSecurityRoleGroup();
            } while(parentSecurityRoleGroup != null);
        }
        
        return safe;
    }
    
    private void updateSecurityRoleGroupFromValue(SecurityRoleGroupDetailValue securityRoleGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(securityRoleGroupDetailValue.hasBeenModified()) {
            SecurityRoleGroup securityRoleGroup = SecurityRoleGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     securityRoleGroupDetailValue.getSecurityRoleGroupPK());
            SecurityRoleGroupDetail securityRoleGroupDetail = securityRoleGroup.getActiveDetailForUpdate();
            
            securityRoleGroupDetail.setThruTime(session.START_TIME_LONG);
            securityRoleGroupDetail.store();
            
            SecurityRoleGroupPK securityRoleGroupPK = securityRoleGroupDetail.getSecurityRoleGroupPK();
            String securityRoleGroupName = securityRoleGroupDetailValue.getSecurityRoleGroupName();
            SecurityRoleGroupPK parentSecurityRoleGroupPK = securityRoleGroupDetailValue.getParentSecurityRoleGroupPK();
            Boolean isDefault = securityRoleGroupDetailValue.getIsDefault();
            Integer sortOrder = securityRoleGroupDetailValue.getSortOrder();
            
            if(checkDefault) {
                SecurityRoleGroup defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();
                boolean defaultFound = defaultSecurityRoleGroup != null && !defaultSecurityRoleGroup.equals(securityRoleGroup);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SecurityRoleGroupDetailValue defaultSecurityRoleGroupDetailValue = getDefaultSecurityRoleGroupDetailValueForUpdate();
                    
                    defaultSecurityRoleGroupDetailValue.setIsDefault(Boolean.FALSE);
                    updateSecurityRoleGroupFromValue(defaultSecurityRoleGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            securityRoleGroupDetail = SecurityRoleGroupDetailFactory.getInstance().create(securityRoleGroupPK,
                    securityRoleGroupName, parentSecurityRoleGroupPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            securityRoleGroup.setActiveDetail(securityRoleGroupDetail);
            securityRoleGroup.setLastDetail(securityRoleGroupDetail);
            
            sendEventUsingNames(securityRoleGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateSecurityRoleGroupFromValue(SecurityRoleGroupDetailValue securityRoleGroupDetailValue, BasePK updatedBy) {
        updateSecurityRoleGroupFromValue(securityRoleGroupDetailValue, true, updatedBy);
    }
    
    private void deleteSecurityRoleGroup(SecurityRoleGroup securityRoleGroup, boolean checkDefault, BasePK deletedBy) {
        SecurityRoleGroupDetail securityRoleGroupDetail = securityRoleGroup.getLastDetailForUpdate();

        deleteSecurityRoleGroupsByParentSecurityRoleGroup(securityRoleGroup, deletedBy);
        deleteSecurityRolesBySecurityRoleGroup(securityRoleGroup, deletedBy);
        deleteSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup, deletedBy);
        
        securityRoleGroupDetail.setThruTime(session.START_TIME_LONG);
        securityRoleGroup.setActiveDetail(null);
        securityRoleGroup.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            SecurityRoleGroup defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();

            if(defaultSecurityRoleGroup == null) {
                List<SecurityRoleGroup> securityRoleGroups = getSecurityRoleGroupsForUpdate();

                if(!securityRoleGroups.isEmpty()) {
                    Iterator<SecurityRoleGroup> iter = securityRoleGroups.iterator();
                    if(iter.hasNext()) {
                        defaultSecurityRoleGroup = iter.next();
                    }
                    SecurityRoleGroupDetailValue securityRoleGroupDetailValue = defaultSecurityRoleGroup.getLastDetailForUpdate().getSecurityRoleGroupDetailValue().clone();

                    securityRoleGroupDetailValue.setIsDefault(Boolean.TRUE);
                    updateSecurityRoleGroupFromValue(securityRoleGroupDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(securityRoleGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteSecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        deleteSecurityRoleGroup(securityRoleGroup, true, deletedBy);
    }

    private void deleteSecurityRoleGroups(List<SecurityRoleGroup> securityRoleGroups, boolean checkDefault, BasePK deletedBy) {
        securityRoleGroups.stream().forEach((securityRoleGroup) -> {
            deleteSecurityRoleGroup(securityRoleGroup, checkDefault, deletedBy);
        });
    }

    public void deleteSecurityRoleGroups(List<SecurityRoleGroup> securityRoleGroups, BasePK deletedBy) {
        deleteSecurityRoleGroups(securityRoleGroups, true, deletedBy);
    }

    private void deleteSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup, BasePK deletedBy) {
        deleteSecurityRoleGroups(getSecurityRoleGroupsByParentSecurityRoleGroupForUpdate(parentSecurityRoleGroup), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Security Role Group Descriptions
    // --------------------------------------------------------------------------------
    
    public SecurityRoleGroupDescription createSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, String description, BasePK createdBy) {
        SecurityRoleGroupDescription securityRoleGroupDescription = SecurityRoleGroupDescriptionFactory.getInstance().create(securityRoleGroup, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(securityRoleGroup.getPrimaryKey(), EventTypes.MODIFY.name(), securityRoleGroupDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return securityRoleGroupDescription;
    }
    
    private SecurityRoleGroupDescription getSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, EntityPermission entityPermission) {
        SecurityRoleGroupDescription securityRoleGroupDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityrolegroupdescriptions " +
                        "WHERE srgd_srg_securityrolegroupid = ? AND srgd_lang_languageid = ? AND srgd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityrolegroupdescriptions " +
                        "WHERE srgd_srg_securityrolegroupid = ? AND srgd_lang_languageid = ? AND srgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            securityRoleGroupDescription = SecurityRoleGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoleGroupDescription;
    }
    
    public SecurityRoleGroupDescription getSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language) {
        return getSecurityRoleGroupDescription(securityRoleGroup, language, EntityPermission.READ_ONLY);
    }
    
    public SecurityRoleGroupDescription getSecurityRoleGroupDescriptionForUpdate(SecurityRoleGroup securityRoleGroup, Language language) {
        return getSecurityRoleGroupDescription(securityRoleGroup, language, EntityPermission.READ_WRITE);
    }
    
    public SecurityRoleGroupDescriptionValue getSecurityRoleGroupDescriptionValue(SecurityRoleGroupDescription securityRoleGroupDescription) {
        return securityRoleGroupDescription == null? null: securityRoleGroupDescription.getSecurityRoleGroupDescriptionValue().clone();
    }
    
    public SecurityRoleGroupDescriptionValue getSecurityRoleGroupDescriptionValueForUpdate(SecurityRoleGroup securityRoleGroup, Language language) {
        return getSecurityRoleGroupDescriptionValue(getSecurityRoleGroupDescriptionForUpdate(securityRoleGroup, language));
    }
    
    private List<SecurityRoleGroupDescription> getSecurityRoleGroupDescriptionsBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        List<SecurityRoleGroupDescription> securityRoleGroupDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityrolegroupdescriptions, languages " +
                        "WHERE srgd_srg_securityrolegroupid = ? AND srgd_thrutime = ? AND srgd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityrolegroupdescriptions " +
                        "WHERE srgd_srg_securityrolegroupid = ? AND srgd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            securityRoleGroupDescriptions = SecurityRoleGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoleGroupDescriptions;
    }
    
    public List<SecurityRoleGroupDescription> getSecurityRoleGroupDescriptionsBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRoleGroupDescription> getSecurityRoleGroupDescriptionsBySecurityRoleGroupForUpdate(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language) {
        String description;
        SecurityRoleGroupDescription securityRoleGroupDescription = getSecurityRoleGroupDescription(securityRoleGroup, language);
        
        if(securityRoleGroupDescription == null && !language.getIsDefault()) {
            securityRoleGroupDescription = getSecurityRoleGroupDescription(securityRoleGroup, getPartyControl().getDefaultLanguage());
        }
        
        if(securityRoleGroupDescription == null) {
            description = securityRoleGroup.getLastDetail().getSecurityRoleGroupName();
        } else {
            description = securityRoleGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public SecurityRoleGroupDescriptionTransfer getSecurityRoleGroupDescriptionTransfer(UserVisit userVisit, SecurityRoleGroupDescription securityRoleGroupDescription) {
        return getSecurityTransferCaches(userVisit).getSecurityRoleGroupDescriptionTransferCache().getSecurityRoleGroupDescriptionTransfer(securityRoleGroupDescription);
    }
    
    public List<SecurityRoleGroupDescriptionTransfer> getSecurityRoleGroupDescriptionTransfersBySecurityRoleGroup(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        List<SecurityRoleGroupDescription> securityRoleGroupDescriptions = getSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup);
        List<SecurityRoleGroupDescriptionTransfer> securityRoleGroupDescriptionTransfers = new ArrayList<>(securityRoleGroupDescriptions.size());
        SecurityRoleGroupDescriptionTransferCache securityRoleGroupDescriptionTransferCache = getSecurityTransferCaches(userVisit).getSecurityRoleGroupDescriptionTransferCache();
        
        securityRoleGroupDescriptions.stream().forEach((securityRoleGroupDescription) -> {
            securityRoleGroupDescriptionTransfers.add(securityRoleGroupDescriptionTransferCache.getSecurityRoleGroupDescriptionTransfer(securityRoleGroupDescription));
        });
        
        return securityRoleGroupDescriptionTransfers;
    }
    
    public void updateSecurityRoleGroupDescriptionFromValue(SecurityRoleGroupDescriptionValue securityRoleGroupDescriptionValue, BasePK updatedBy) {
        if(securityRoleGroupDescriptionValue.hasBeenModified()) {
            SecurityRoleGroupDescription securityRoleGroupDescription = SecurityRoleGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, securityRoleGroupDescriptionValue.getPrimaryKey());
            
            securityRoleGroupDescription.setThruTime(session.START_TIME_LONG);
            securityRoleGroupDescription.store();
            
            SecurityRoleGroup securityRoleGroup = securityRoleGroupDescription.getSecurityRoleGroup();
            Language language = securityRoleGroupDescription.getLanguage();
            String description = securityRoleGroupDescriptionValue.getDescription();
            
            securityRoleGroupDescription = SecurityRoleGroupDescriptionFactory.getInstance().create(securityRoleGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(securityRoleGroup.getPrimaryKey(), EventTypes.MODIFY.name(), securityRoleGroupDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSecurityRoleGroupDescription(SecurityRoleGroupDescription securityRoleGroupDescription, BasePK deletedBy) {
        securityRoleGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(securityRoleGroupDescription.getSecurityRoleGroupPK(), EventTypes.MODIFY.name(), securityRoleGroupDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSecurityRoleGroupDescriptionsBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        List<SecurityRoleGroupDescription> securityRoleGroupDescriptions = getSecurityRoleGroupDescriptionsBySecurityRoleGroupForUpdate(securityRoleGroup);
        
        securityRoleGroupDescriptions.stream().forEach((securityRoleGroupDescription) -> {
            deleteSecurityRoleGroupDescription(securityRoleGroupDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Security Roles
    // --------------------------------------------------------------------------------
    
    public SecurityRole createSecurityRole(SecurityRoleGroup securityRoleGroup, String securityRoleName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        SecurityRole defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
        boolean defaultFound = defaultSecurityRole != null;
        
        if(defaultFound && isDefault) {
            SecurityRoleDetailValue defaultSecurityRoleDetailValue = getDefaultSecurityRoleDetailValueForUpdate(securityRoleGroup);
            
            defaultSecurityRoleDetailValue.setIsDefault(Boolean.FALSE);
            updateSecurityRoleFromValue(defaultSecurityRoleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        SecurityRole securityRole = SecurityRoleFactory.getInstance().create();
        SecurityRoleDetail securityRoleDetail = SecurityRoleDetailFactory.getInstance().create(session,
                securityRole, securityRoleGroup, securityRoleName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        securityRole = SecurityRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                securityRole.getPrimaryKey());
        securityRole.setActiveDetail(securityRoleDetail);
        securityRole.setLastDetail(securityRoleDetail);
        securityRole.store();
        
        sendEventUsingNames(securityRole.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return securityRole;
    }
    
    public long countSecurityRoles() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM securityroles, securityroledetails " +
                "WHERE srol_activedetailid = sroldt_securityroledetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.SecurityRole */
    public SecurityRole getSecurityRoleByEntityInstance(EntityInstance entityInstance) {
        SecurityRolePK pk = new SecurityRolePK(entityInstance.getEntityUniqueId());
        SecurityRole securityRole = SecurityRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
        
        return securityRole;
    }
    
    private List<SecurityRole> getSecurityRoles(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        List<SecurityRole> securityRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ? " +
                        "ORDER BY sroldt_sortorder, sroldt_securityrolename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            
            securityRoles = SecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoles;
    }
    
    public List<SecurityRole> getSecurityRoles(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoles(securityRoleGroup, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRole> getSecurityRolesForUpdate(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoles(securityRoleGroup, EntityPermission.READ_WRITE);
    }
    
    public boolean securityRoleExists(SecurityRoleGroup securityRoleGroup, String securityRoleName) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM securityroles, securityroledetails "
                + "WHERE srol_activedetailid = sroldt_securityroledetailid "
                + "AND sroldt_srg_securityrolegroupid = ? AND sroldt_securityrolename = ?",
                securityRoleGroup, securityRoleName) == 1;
    }
    
    public long countSecurityRolesBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM securityroles, securityroledetails "
                + "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ?",
                securityRoleGroup);
    }

    private SecurityRole getDefaultSecurityRole(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        SecurityRole securityRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = ? AND sroldt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = ? AND sroldt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            
            securityRole = SecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRole;
    }
    
    public SecurityRole getDefaultSecurityRole(SecurityRoleGroup securityRoleGroup) {
        return getDefaultSecurityRole(securityRoleGroup, EntityPermission.READ_ONLY);
    }
    
    public SecurityRole getDefaultSecurityRoleForUpdate(SecurityRoleGroup securityRoleGroup) {
        return getDefaultSecurityRole(securityRoleGroup, EntityPermission.READ_WRITE);
    }
    
    public SecurityRoleDetailValue getDefaultSecurityRoleDetailValueForUpdate(SecurityRoleGroup securityRoleGroup) {
        return getDefaultSecurityRoleForUpdate(securityRoleGroup).getLastDetailForUpdate().getSecurityRoleDetailValue().clone();
    }
    
    private SecurityRole getSecurityRoleByName(SecurityRoleGroup securityRoleGroup, String securityRoleName, EntityPermission entityPermission) {
        SecurityRole securityRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = ? AND sroldt_securityrolename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = ? AND sroldt_securityrolename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setString(2, securityRoleName);
            
            securityRole = SecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRole;
    }
    
    public SecurityRole getSecurityRoleByName(SecurityRoleGroup securityRoleGroup, String securityRoleName) {
        return getSecurityRoleByName(securityRoleGroup, securityRoleName, EntityPermission.READ_ONLY);
    }
    
    public SecurityRole getSecurityRoleByNameForUpdate(SecurityRoleGroup securityRoleGroup, String securityRoleName) {
        return getSecurityRoleByName(securityRoleGroup, securityRoleName, EntityPermission.READ_WRITE);
    }
    
    public SecurityRoleDetailValue getSecurityRoleDetailValueForUpdate(SecurityRole securityRole) {
        return securityRole == null? null: securityRole.getLastDetailForUpdate().getSecurityRoleDetailValue().clone();
    }
    
    public SecurityRoleDetailValue getSecurityRoleDetailValueByNameForUpdate(SecurityRoleGroup securityRoleGroup, String securityRoleName) {
        return getSecurityRoleDetailValueForUpdate(getSecurityRoleByNameForUpdate(securityRoleGroup, securityRoleName));
    }
    
    public SecurityRoleChoicesBean getSecurityRoleChoices(String defaultSecurityRoleChoice, Language language,
            boolean allowNullChoice, SecurityRoleGroup securityRoleGroup) {
        List<SecurityRole> securityRoles = getSecurityRoles(securityRoleGroup);
        int size = securityRoles.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSecurityRoleChoice == null) {
                defaultValue = "";
            }
        }
        
        for(SecurityRole securityRole: securityRoles) {
            SecurityRoleDetail securityRoleDetail = securityRole.getLastDetail();
            String label = getBestSecurityRoleDescription(securityRole, language);
            String value = securityRoleDetail.getSecurityRoleName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultSecurityRoleChoice == null? false: defaultSecurityRoleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && securityRoleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SecurityRoleChoicesBean(labels, values, defaultValue);
    }
    
    public SecurityRoleTransfer getSecurityRoleTransfer(UserVisit userVisit, SecurityRole securityRole) {
        return getSecurityTransferCaches(userVisit).getSecurityRoleTransferCache().getSecurityRoleTransfer(securityRole);
    }
    
    public List<SecurityRoleTransfer> getSecurityRoleTransfersBySecurityRoleGroup(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        List<SecurityRole> securityRoles = getSecurityRoles(securityRoleGroup);
        List<SecurityRoleTransfer> securityRoleTransfers = new ArrayList<>(securityRoles.size());
        SecurityRoleTransferCache securityRoleTransferCache = getSecurityTransferCaches(userVisit).getSecurityRoleTransferCache();
        
        securityRoles.stream().forEach((securityRole) -> {
            securityRoleTransfers.add(securityRoleTransferCache.getSecurityRoleTransfer(securityRole));
        });
        
        return securityRoleTransfers;
    }
    
    private void updateSecurityRoleFromValue(SecurityRoleDetailValue securityRoleDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(securityRoleDetailValue.hasBeenModified()) {
            SecurityRole securityRole = SecurityRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     securityRoleDetailValue.getSecurityRolePK());
            SecurityRoleDetail securityRoleDetail = securityRole.getActiveDetailForUpdate();
            
            securityRoleDetail.setThruTime(session.START_TIME_LONG);
            securityRoleDetail.store();
            
            SecurityRolePK securityRolePK = securityRoleDetail.getSecurityRolePK();
            SecurityRoleGroup securityRoleGroup = securityRoleDetail.getSecurityRoleGroup();
            SecurityRoleGroupPK securityRoleGroupPK = securityRoleGroup.getPrimaryKey();
            String securityRoleName = securityRoleDetailValue.getSecurityRoleName();
            Boolean isDefault = securityRoleDetailValue.getIsDefault();
            Integer sortOrder = securityRoleDetailValue.getSortOrder();
            
            if(checkDefault) {
                SecurityRole defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
                boolean defaultFound = defaultSecurityRole != null && !defaultSecurityRole.equals(securityRole);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    SecurityRoleDetailValue defaultSecurityRoleDetailValue = getDefaultSecurityRoleDetailValueForUpdate(securityRoleGroup);
                    
                    defaultSecurityRoleDetailValue.setIsDefault(Boolean.FALSE);
                    updateSecurityRoleFromValue(defaultSecurityRoleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            securityRoleDetail = SecurityRoleDetailFactory.getInstance().create(securityRolePK, securityRoleGroupPK,
                    securityRoleName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            securityRole.setActiveDetail(securityRoleDetail);
            securityRole.setLastDetail(securityRoleDetail);
            
            sendEventUsingNames(securityRolePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateSecurityRoleFromValue(SecurityRoleDetailValue securityRoleDetailValue, BasePK updatedBy) {
        updateSecurityRoleFromValue(securityRoleDetailValue, true, updatedBy);
    }
    
    public void deleteSecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateRolesBySecurityRole(securityRole, deletedBy);
        deletePartySecurityRolesBySecurityRole(securityRole, deletedBy);
        deletePartyEntitySecurityRolesBySecurityRole(securityRole, deletedBy);
        deleteSecurityRolePartyTypesBySecurityRole(securityRole, deletedBy);
        deleteSecurityRoleDescriptionsBySecurityRole(securityRole, deletedBy);
        
        SecurityRoleDetail securityRoleDetail = securityRole.getLastDetailForUpdate();
        securityRoleDetail.setThruTime(session.START_TIME_LONG);
        securityRole.setActiveDetail(null);
        securityRole.store();
        
        // Check for default, and pick one if necessary
        SecurityRoleGroup securityRoleGroup = securityRoleDetail.getSecurityRoleGroup();
        SecurityRole defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
        if(defaultSecurityRole == null) {
            List<SecurityRole> securityRoles = getSecurityRolesForUpdate(securityRoleGroup);
            
            if(!securityRoles.isEmpty()) {
                Iterator<SecurityRole> iter = securityRoles.iterator();
                if(iter.hasNext()) {
                    defaultSecurityRole = iter.next();
                }
                SecurityRoleDetailValue securityRoleDetailValue = defaultSecurityRole.getLastDetailForUpdate().getSecurityRoleDetailValue().clone();
                
                securityRoleDetailValue.setIsDefault(Boolean.TRUE);
                updateSecurityRoleFromValue(securityRoleDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(securityRole.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteSecurityRolesBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        List<SecurityRole> securityRoles = getSecurityRolesForUpdate(securityRoleGroup);
        
        securityRoles.stream().forEach((securityRole) -> {
            deleteSecurityRole(securityRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Descriptions
    // --------------------------------------------------------------------------------
    
    public SecurityRoleDescription createSecurityRoleDescription(SecurityRole securityRole, Language language, String description, BasePK createdBy) {
        SecurityRoleDescription securityRoleDescription = SecurityRoleDescriptionFactory.getInstance().create(securityRole, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(securityRole.getPrimaryKey(), EventTypes.MODIFY.name(), securityRoleDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return securityRoleDescription;
    }
    
    private SecurityRoleDescription getSecurityRoleDescription(SecurityRole securityRole, Language language, EntityPermission entityPermission) {
        SecurityRoleDescription securityRoleDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroledescriptions " +
                        "WHERE srold_srol_securityroleid = ? AND srold_lang_languageid = ? AND srold_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroledescriptions " +
                        "WHERE srold_srol_securityroleid = ? AND srold_lang_languageid = ? AND srold_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            securityRoleDescription = SecurityRoleDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoleDescription;
    }
    
    public SecurityRoleDescription getSecurityRoleDescription(SecurityRole securityRole, Language language) {
        return getSecurityRoleDescription(securityRole, language, EntityPermission.READ_ONLY);
    }
    
    public SecurityRoleDescription getSecurityRoleDescriptionForUpdate(SecurityRole securityRole, Language language) {
        return getSecurityRoleDescription(securityRole, language, EntityPermission.READ_WRITE);
    }
    
    public SecurityRoleDescriptionValue getSecurityRoleDescriptionValue(SecurityRoleDescription securityRoleDescription) {
        return securityRoleDescription == null? null: securityRoleDescription.getSecurityRoleDescriptionValue().clone();
    }
    
    public SecurityRoleDescriptionValue getSecurityRoleDescriptionValueForUpdate(SecurityRole securityRole, Language language) {
        return getSecurityRoleDescriptionValue(getSecurityRoleDescriptionForUpdate(securityRole, language));
    }
    
    private List<SecurityRoleDescription> getSecurityRoleDescriptionsBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        List<SecurityRoleDescription> securityRoleDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroledescriptions, languages " +
                        "WHERE srold_srol_securityroleid = ? AND srold_thrutime = ? AND srold_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroledescriptions " +
                        "WHERE srold_srol_securityroleid = ? AND srold_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = SecurityRoleDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            securityRoleDescriptions = SecurityRoleDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return securityRoleDescriptions;
    }
    
    public List<SecurityRoleDescription> getSecurityRoleDescriptionsBySecurityRole(SecurityRole securityRole) {
        return getSecurityRoleDescriptionsBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRoleDescription> getSecurityRoleDescriptionsBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getSecurityRoleDescriptionsBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    public String getBestSecurityRoleDescription(SecurityRole securityRole, Language language) {
        String description;
        SecurityRoleDescription securityRoleDescription = getSecurityRoleDescription(securityRole, language);
        
        if(securityRoleDescription == null && !language.getIsDefault()) {
            securityRoleDescription = getSecurityRoleDescription(securityRole, getPartyControl().getDefaultLanguage());
        }
        
        if(securityRoleDescription == null) {
            description = securityRole.getLastDetail().getSecurityRoleName();
        } else {
            description = securityRoleDescription.getDescription();
        }
        
        return description;
    }
    
    public SecurityRoleDescriptionTransfer getSecurityRoleDescriptionTransfer(UserVisit userVisit, SecurityRoleDescription securityRoleDescription) {
        return getSecurityTransferCaches(userVisit).getSecurityRoleDescriptionTransferCache().getSecurityRoleDescriptionTransfer(securityRoleDescription);
    }
    
    public List<SecurityRoleDescriptionTransfer> getSecurityRoleDescriptionTransfersBySecurityRole(UserVisit userVisit, SecurityRole securityRole) {
        List<SecurityRoleDescription> securityRoleDescriptions = getSecurityRoleDescriptionsBySecurityRole(securityRole);
        List<SecurityRoleDescriptionTransfer> securityRoleDescriptionTransfers = new ArrayList<>(securityRoleDescriptions.size());
        SecurityRoleDescriptionTransferCache securityRoleDescriptionTransferCache = getSecurityTransferCaches(userVisit).getSecurityRoleDescriptionTransferCache();
        
        securityRoleDescriptions.stream().forEach((securityRoleDescription) -> {
            securityRoleDescriptionTransfers.add(securityRoleDescriptionTransferCache.getSecurityRoleDescriptionTransfer(securityRoleDescription));
        });
        
        return securityRoleDescriptionTransfers;
    }
    
    public void updateSecurityRoleDescriptionFromValue(SecurityRoleDescriptionValue securityRoleDescriptionValue, BasePK updatedBy) {
        if(securityRoleDescriptionValue.hasBeenModified()) {
            SecurityRoleDescription securityRoleDescription = SecurityRoleDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, securityRoleDescriptionValue.getPrimaryKey());
            
            securityRoleDescription.setThruTime(session.START_TIME_LONG);
            securityRoleDescription.store();
            
            SecurityRole securityRole = securityRoleDescription.getSecurityRole();
            Language language = securityRoleDescription.getLanguage();
            String description = securityRoleDescriptionValue.getDescription();
            
            securityRoleDescription = SecurityRoleDescriptionFactory.getInstance().create(securityRole, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(securityRole.getPrimaryKey(), EventTypes.MODIFY.name(), securityRoleDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSecurityRoleDescription(SecurityRoleDescription securityRoleDescription, BasePK deletedBy) {
        securityRoleDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(securityRoleDescription.getSecurityRolePK(), EventTypes.MODIFY.name(), securityRoleDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSecurityRoleDescriptionsBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        List<SecurityRoleDescription> securityRoleDescriptions = getSecurityRoleDescriptionsBySecurityRoleForUpdate(securityRole);
        
        securityRoleDescriptions.stream().forEach((securityRoleDescription) -> {
            deleteSecurityRoleDescription(securityRoleDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class PartyTypes
    // --------------------------------------------------------------------------------
    
    public SecurityRolePartyType createSecurityRolePartyType(SecurityRole securityRole, PartyType partyType, Selector partySelector, BasePK createdBy) {
        SecurityRolePartyType securityRolePartyType = SecurityRolePartyTypeFactory.getInstance().create(securityRole, partyType, partySelector,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(securityRole.getPrimaryKey(), EventTypes.MODIFY.name(), securityRolePartyType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return securityRolePartyType;
    }
    
    private static final Map<EntityPermission, String> getSecurityRolePartyTypeQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_ptyp_partytypeid = ? AND srolptyp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_ptyp_partytypeid = ? AND srolptyp_thrutime = ? " +
                "FOR UPDATE");
        getSecurityRolePartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private SecurityRolePartyType getSecurityRolePartyType(SecurityRole securityRole, PartyType partyType, EntityPermission entityPermission) {
        return SecurityRolePartyTypeFactory.getInstance().getEntityFromQuery(entityPermission, getSecurityRolePartyTypeQueries, securityRole, partyType,
                Session.MAX_TIME);
    }
    
    public SecurityRolePartyType getSecurityRolePartyType(SecurityRole securityRole, PartyType partyType) {
        return getSecurityRolePartyType(securityRole, partyType, EntityPermission.READ_ONLY);
    }
    
    public SecurityRolePartyType getSecurityRolePartyTypeForUpdate(SecurityRole securityRole, PartyType partyType) {
        return getSecurityRolePartyType(securityRole, partyType, EntityPermission.READ_WRITE);
    }
    
    public SecurityRolePartyTypeValue getSecurityRolePartyTypeValue(SecurityRolePartyType securityRolePartyType) {
        return securityRolePartyType == null? null: securityRolePartyType.getSecurityRolePartyTypeValue().clone();
    }
    
    public SecurityRolePartyTypeValue getSecurityRolePartyTypeValueForUpdate(SecurityRole securityRole, PartyType partyType) {
        return getSecurityRolePartyTypeValue(getSecurityRolePartyTypeForUpdate(securityRole, partyType));
    }
    
    private static final Map<EntityPermission, String> getSecurityRolePartyTypesBySecurityRoleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes, partytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_thrutime = ? AND srolptyp_ptyp_partytypeid = ptyp_partytypeid " +
                "ORDER BY ptyp_partytypename, ptyp_partytypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_thrutime = ? " +
                "FOR UPDATE");
        getSecurityRolePartyTypesBySecurityRoleQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        return SecurityRolePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSecurityRolePartyTypesBySecurityRoleQueries,
                securityRole, Session.MAX_TIME);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRole(SecurityRole securityRole) {
        return getSecurityRolePartyTypesBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getSecurityRolePartyTypesBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getSecurityRolePartyTypesByPartySelectorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes, partytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_thrutime = ? AND srolptyp_ptyp_partytypeid = ptyp_partytypeid " +
                "ORDER BY ptyp_partytypename, ptyp_partytypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM securityrolepartytypes " +
                "WHERE srolptyp_srol_securityroleid = ? AND srolptyp_thrutime = ? " +
                "FOR UPDATE");
        getSecurityRolePartyTypesByPartySelectorQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelector(Selector partySelector, EntityPermission entityPermission) {
        return SecurityRolePartyTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getSecurityRolePartyTypesByPartySelectorQueries,
                partySelector, Session.MAX_TIME);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelector(Selector partySelector) {
        return getSecurityRolePartyTypesByPartySelector(partySelector, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelectorForUpdate(Selector partySelector) {
        return getSecurityRolePartyTypesByPartySelector(partySelector, EntityPermission.READ_WRITE);
    }
    
    public SecurityRolePartyTypeTransfer getSecurityRolePartyTypeTransfer(UserVisit userVisit, SecurityRolePartyType securityRolePartyType) {
        return getSecurityTransferCaches(userVisit).getSecurityRolePartyTypeTransferCache().getSecurityRolePartyTypeTransfer(securityRolePartyType);
    }
    
    public List<SecurityRolePartyTypeTransfer> getSecurityRolePartyTypeTransfers(UserVisit userVisit, List<SecurityRolePartyType> securityRolePartyTypes) {
        List<SecurityRolePartyTypeTransfer> securityRolePartyTypeTransfers = new ArrayList<>(securityRolePartyTypes.size());
        SecurityRolePartyTypeTransferCache securityRolePartyTypeTransferCache = getSecurityTransferCaches(userVisit).getSecurityRolePartyTypeTransferCache();
        
        securityRolePartyTypes.stream().forEach((securityRolePartyType) -> {
            securityRolePartyTypeTransfers.add(securityRolePartyTypeTransferCache.getSecurityRolePartyTypeTransfer(securityRolePartyType));
        });
        
        return securityRolePartyTypeTransfers;
    }
    
    public List<SecurityRolePartyTypeTransfer> getSecurityRolePartyTypeTransfersBySecurityRole(UserVisit userVisit, SecurityRole securityRole) {
        return getSecurityRolePartyTypeTransfers(userVisit, getSecurityRolePartyTypesBySecurityRole(securityRole));
    }
    
    public void updateSecurityRolePartyTypeFromValue(SecurityRolePartyTypeValue securityRolePartyTypeValue, BasePK updatedBy) {
        if(securityRolePartyTypeValue.hasBeenModified()) {
            SecurityRolePartyType securityRolePartyType = SecurityRolePartyTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    securityRolePartyTypeValue.getPrimaryKey());
            
            securityRolePartyType.setThruTime(session.START_TIME_LONG);
            securityRolePartyType.store();
            
            SecurityRolePK securityRolePK = securityRolePartyType.getSecurityRolePK();
            PartyTypePK partyTypePK = securityRolePartyType.getPartyTypePK();
            SelectorPK partySelectorPK = securityRolePartyTypeValue.getPartySelectorPK();
            
            securityRolePartyType = SecurityRolePartyTypeFactory.getInstance().create(securityRolePK, partyTypePK, partySelectorPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(securityRolePK, EventTypes.MODIFY.name(), securityRolePartyType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteSecurityRolePartyType(SecurityRolePartyType securityRolePartyType, BasePK deletedBy) {
        securityRolePartyType.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(securityRolePartyType.getSecurityRolePK(), EventTypes.MODIFY.name(), securityRolePartyType.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteSecurityRolePartyTypes(List<SecurityRolePartyType> securityRolePartyTypes, BasePK deletedBy) {
        securityRolePartyTypes.stream().forEach((securityRolePartyType) -> {
            deleteSecurityRolePartyType(securityRolePartyType, deletedBy);
        });
    }
    
    public void deleteSecurityRolePartyTypesBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        deleteSecurityRolePartyTypes(getSecurityRolePartyTypesBySecurityRoleForUpdate(securityRole), deletedBy);
    }
    
    public void deleteSecurityRolePartyTypesByPartySelector(Selector partySelector, BasePK deletedBy) {
        deleteSecurityRolePartyTypes(getSecurityRolePartyTypesByPartySelectorForUpdate(partySelector), deletedBy);
    }
    
    public void deleteSecurityRolePartyTypesBySelector(Selector selector, BasePK deletedBy) {
        deleteSecurityRolePartyTypesByPartySelector(selector, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Templates
    // --------------------------------------------------------------------------------
    
    public PartySecurityRoleTemplate createPartySecurityRoleTemplate(String partySecurityRoleTemplateName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PartySecurityRoleTemplate defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
        boolean defaultFound = defaultPartySecurityRoleTemplate != null;
        
        if(defaultFound && isDefault) {
            PartySecurityRoleTemplateDetailValue defaultPartySecurityRoleTemplateDetailValue = getDefaultPartySecurityRoleTemplateDetailValueForUpdate();
            
            defaultPartySecurityRoleTemplateDetailValue.setIsDefault(Boolean.FALSE);
            updatePartySecurityRoleTemplateFromValue(defaultPartySecurityRoleTemplateDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        PartySecurityRoleTemplate partySecurityRoleTemplate = PartySecurityRoleTemplateFactory.getInstance().create();
        PartySecurityRoleTemplateDetail partySecurityRoleTemplateDetail = PartySecurityRoleTemplateDetailFactory.getInstance().create(partySecurityRoleTemplate,
                partySecurityRoleTemplateName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        partySecurityRoleTemplate = PartySecurityRoleTemplateFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                partySecurityRoleTemplate.getPrimaryKey());
        partySecurityRoleTemplate.setActiveDetail(partySecurityRoleTemplateDetail);
        partySecurityRoleTemplate.setLastDetail(partySecurityRoleTemplateDetail);
        partySecurityRoleTemplate.store();
        
        sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return partySecurityRoleTemplate;
    }
    
    private List<PartySecurityRoleTemplate> getPartySecurityRoleTemplates(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                    "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid " +
                    "ORDER BY psrtdt_sortorder, psrtdt_partysecurityroletemplatename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                    "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PartySecurityRoleTemplateFactory.getInstance().prepareStatement(query);
        
        return PartySecurityRoleTemplateFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<PartySecurityRoleTemplate> getPartySecurityRoleTemplates() {
        return getPartySecurityRoleTemplates(EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplate> getPartySecurityRoleTemplatesForUpdate() {
        return getPartySecurityRoleTemplates(EntityPermission.READ_WRITE);
    }
    
    private PartySecurityRoleTemplate getDefaultPartySecurityRoleTemplate(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                    "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid AND psrtdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                    "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid AND psrtdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = PartySecurityRoleTemplateFactory.getInstance().prepareStatement(query);
        
        return PartySecurityRoleTemplateFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public PartySecurityRoleTemplate getDefaultPartySecurityRoleTemplate() {
        return getDefaultPartySecurityRoleTemplate(EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplate getDefaultPartySecurityRoleTemplateForUpdate() {
        return getDefaultPartySecurityRoleTemplate(EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateDetailValue getDefaultPartySecurityRoleTemplateDetailValueForUpdate() {
        return getDefaultPartySecurityRoleTemplateForUpdate().getLastDetailForUpdate().getPartySecurityRoleTemplateDetailValue().clone();
    }
    
    private PartySecurityRoleTemplate getPartySecurityRoleTemplateByName(String partySecurityRoleTemplateName, EntityPermission entityPermission) {
        PartySecurityRoleTemplate partySecurityRoleTemplate = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                        "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid " +
                        "AND psrtdt_partysecurityroletemplatename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplates, partysecurityroletemplatedetails " +
                        "WHERE psrt_activedetailid = psrtdt_partysecurityroletemplatedetailid " +
                        "AND psrtdt_partysecurityroletemplatename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, partySecurityRoleTemplateName);
            
            partySecurityRoleTemplate = PartySecurityRoleTemplateFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplate;
    }
    
    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByName(String partySecurityRoleTemplateName) {
        return getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByNameForUpdate(String partySecurityRoleTemplateName) {
        return getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName, EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateDetailValue getPartySecurityRoleTemplateDetailValueForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return partySecurityRoleTemplate == null? null: partySecurityRoleTemplate.getLastDetailForUpdate().getPartySecurityRoleTemplateDetailValue().clone();
    }
    
    public PartySecurityRoleTemplateDetailValue getPartySecurityRoleTemplateDetailValueByNameForUpdate(String partySecurityRoleTemplateName) {
        return getPartySecurityRoleTemplateDetailValueForUpdate(getPartySecurityRoleTemplateByNameForUpdate(partySecurityRoleTemplateName));
    }
    
    public PartySecurityRoleTemplate getPartySecurityRoleTemplateFromPK(PartySecurityRoleTemplatePK partySecurityRoleTemplatePK) {
        return PartySecurityRoleTemplateFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, partySecurityRoleTemplatePK);
    }
    
    public PartySecurityRoleTemplateChoicesBean getPartySecurityRoleTemplateChoices(String defaultPartySecurityRoleTemplateChoice, Language language,
            boolean allowNullChoice) {
        List<PartySecurityRoleTemplate> partySecurityRoleTemplates = getPartySecurityRoleTemplates();
        int size = partySecurityRoleTemplates.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPartySecurityRoleTemplateChoice == null) {
                defaultValue = "";
            }
        }
        
        for(PartySecurityRoleTemplate partySecurityRoleTemplate: partySecurityRoleTemplates) {
            PartySecurityRoleTemplateDetail partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetail();
            String label = getBestPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language);
            String value = partySecurityRoleTemplateDetail.getPartySecurityRoleTemplateName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultPartySecurityRoleTemplateChoice == null? false: defaultPartySecurityRoleTemplateChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partySecurityRoleTemplateDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PartySecurityRoleTemplateChoicesBean(labels, values, defaultValue);
    }
    
    public PartySecurityRoleTemplateTransfer getPartySecurityRoleTemplateTransfer(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTransferCache().getPartySecurityRoleTemplateTransfer(partySecurityRoleTemplate);
    }
    
    public List<PartySecurityRoleTemplateTransfer> getPartySecurityRoleTemplateTransfers(UserVisit userVisit) {
        List<PartySecurityRoleTemplate> partySecurityRoleTemplates = getPartySecurityRoleTemplates();
        List<PartySecurityRoleTemplateTransfer> partySecurityRoleTemplateTransfers = new ArrayList<>(partySecurityRoleTemplates.size());
        PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache = getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTransferCache();
        
        partySecurityRoleTemplates.stream().forEach((partySecurityRoleTemplate) -> {
            partySecurityRoleTemplateTransfers.add(partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(partySecurityRoleTemplate));
        });
        
        return partySecurityRoleTemplateTransfers;
    }
    
    private void updatePartySecurityRoleTemplateFromValue(PartySecurityRoleTemplateDetailValue partySecurityRoleTemplateDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partySecurityRoleTemplateDetailValue.hasBeenModified()) {
            PartySecurityRoleTemplate partySecurityRoleTemplate = PartySecurityRoleTemplateFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateDetailValue.getPartySecurityRoleTemplatePK());
            PartySecurityRoleTemplateDetail partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getActiveDetailForUpdate();
            
            partySecurityRoleTemplateDetail.setThruTime(session.START_TIME_LONG);
            partySecurityRoleTemplateDetail.store();
            
            PartySecurityRoleTemplatePK partySecurityRoleTemplatePK = partySecurityRoleTemplateDetail.getPartySecurityRoleTemplatePK();
            String partySecurityRoleTemplateName = partySecurityRoleTemplateDetailValue.getPartySecurityRoleTemplateName();
            Boolean isDefault = partySecurityRoleTemplateDetailValue.getIsDefault();
            Integer sortOrder = partySecurityRoleTemplateDetailValue.getSortOrder();
            
            if(checkDefault) {
                PartySecurityRoleTemplate defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
                boolean defaultFound = defaultPartySecurityRoleTemplate != null && !defaultPartySecurityRoleTemplate.equals(partySecurityRoleTemplate);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartySecurityRoleTemplateDetailValue defaultPartySecurityRoleTemplateDetailValue = getDefaultPartySecurityRoleTemplateDetailValueForUpdate();
                    
                    defaultPartySecurityRoleTemplateDetailValue.setIsDefault(Boolean.FALSE);
                    updatePartySecurityRoleTemplateFromValue(defaultPartySecurityRoleTemplateDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            partySecurityRoleTemplateDetail = PartySecurityRoleTemplateDetailFactory.getInstance().create(partySecurityRoleTemplatePK, partySecurityRoleTemplateName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            partySecurityRoleTemplate.setActiveDetail(partySecurityRoleTemplateDetail);
            partySecurityRoleTemplate.setLastDetail(partySecurityRoleTemplateDetail);
            
            sendEventUsingNames(partySecurityRoleTemplatePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updatePartySecurityRoleTemplateFromValue(PartySecurityRoleTemplateDetailValue partySecurityRoleTemplateDetailValue, BasePK updatedBy) {
        updatePartySecurityRoleTemplateFromValue(partySecurityRoleTemplateDetailValue, true, updatedBy);
    }
    
    public void deletePartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK deletedBy) {
        deletePartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateRoleByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateTrainingClassByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        
        PartySecurityRoleTemplateDetail partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetailForUpdate();
        partySecurityRoleTemplateDetail.setThruTime(session.START_TIME_LONG);
        partySecurityRoleTemplate.setActiveDetail(null);
        partySecurityRoleTemplate.store();
        
        // Check for default, and pick one if necessary
        PartySecurityRoleTemplate defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
        if(defaultPartySecurityRoleTemplate == null) {
            List<PartySecurityRoleTemplate> partySecurityRoleTemplates = getPartySecurityRoleTemplatesForUpdate();
            
            if(!partySecurityRoleTemplates.isEmpty()) {
                Iterator<PartySecurityRoleTemplate> iter = partySecurityRoleTemplates.iterator();
                if(iter.hasNext()) {
                    defaultPartySecurityRoleTemplate = iter.next();
                }
                PartySecurityRoleTemplateDetailValue partySecurityRoleTemplateDetailValue = defaultPartySecurityRoleTemplate.getLastDetailForUpdate().getPartySecurityRoleTemplateDetailValue().clone();
                
                partySecurityRoleTemplateDetailValue.setIsDefault(Boolean.TRUE);
                updatePartySecurityRoleTemplateFromValue(partySecurityRoleTemplateDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // --------------------------------------------------------------------------------
    
    public PartySecurityRoleTemplateDescription createPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, String description, BasePK createdBy) {
        PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = PartySecurityRoleTemplateDescriptionFactory.getInstance().create(session,
                partySecurityRoleTemplate, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partySecurityRoleTemplateDescription;
    }
    
    private PartySecurityRoleTemplateDescription getPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, EntityPermission entityPermission) {
        PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatedescriptions " +
                        "WHERE psrtd_psrt_partysecurityroletemplateid = ? AND psrtd_lang_languageid = ? AND psrtd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatedescriptions " +
                        "WHERE psrtd_psrt_partysecurityroletemplateid = ? AND psrtd_lang_languageid = ? AND psrtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateDescription = PartySecurityRoleTemplateDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateDescription;
    }
    
    public PartySecurityRoleTemplateDescription getPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate, Language language) {
        return getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplateDescription getPartySecurityRoleTemplateDescriptionForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate, Language language) {
        return getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language, EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateDescriptionValue getPartySecurityRoleTemplateDescriptionValue(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        return partySecurityRoleTemplateDescription == null? null: partySecurityRoleTemplateDescription.getPartySecurityRoleTemplateDescriptionValue().clone();
    }
    
    public PartySecurityRoleTemplateDescriptionValue getPartySecurityRoleTemplateDescriptionValueForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate, Language language) {
        return getPartySecurityRoleTemplateDescriptionValue(getPartySecurityRoleTemplateDescriptionForUpdate(partySecurityRoleTemplate, language));
    }
    
    private List<PartySecurityRoleTemplateDescription> getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateDescription> partySecurityRoleTemplateDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatedescriptions, languages " +
                        "WHERE psrtd_psrt_partysecurityroletemplateid = ? AND psrtd_thrutime = ? AND psrtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatedescriptions " +
                        "WHERE psrtd_psrt_partysecurityroletemplateid = ? AND psrtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateDescriptions = PartySecurityRoleTemplateDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateDescriptions;
    }
    
    public List<PartySecurityRoleTemplateDescription> getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateDescription> getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplateForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    public String getBestPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate, Language language) {
        String description;
        PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language);
        
        if(partySecurityRoleTemplateDescription == null && !language.getIsDefault()) {
            partySecurityRoleTemplateDescription = getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, getPartyControl().getDefaultLanguage());
        }
        
        if(partySecurityRoleTemplateDescription == null) {
            description = partySecurityRoleTemplate.getLastDetail().getPartySecurityRoleTemplateName();
        } else {
            description = partySecurityRoleTemplateDescription.getDescription();
        }
        
        return description;
    }
    
    public PartySecurityRoleTemplateDescriptionTransfer getPartySecurityRoleTemplateDescriptionTransfer(UserVisit userVisit,
            PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        return getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateDescriptionTransferCache().getPartySecurityRoleTemplateDescriptionTransfer(partySecurityRoleTemplateDescription);
    }
    
    public List<PartySecurityRoleTemplateDescriptionTransfer> getPartySecurityRoleTemplateDescriptionTransfersByPartySecurityRoleTemplate(UserVisit userVisit,
            PartySecurityRoleTemplate partySecurityRoleTemplate) {
        List<PartySecurityRoleTemplateDescription> partySecurityRoleTemplateDescriptions = getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        List<PartySecurityRoleTemplateDescriptionTransfer> partySecurityRoleTemplateDescriptionTransfers = new ArrayList<>(partySecurityRoleTemplateDescriptions.size());
        PartySecurityRoleTemplateDescriptionTransferCache partySecurityRoleTemplateDescriptionTransferCache = getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateDescriptionTransferCache();
        
        partySecurityRoleTemplateDescriptions.stream().forEach((partySecurityRoleTemplateDescription) -> {
            partySecurityRoleTemplateDescriptionTransfers.add(partySecurityRoleTemplateDescriptionTransferCache.getPartySecurityRoleTemplateDescriptionTransfer(partySecurityRoleTemplateDescription));
        });
        
        return partySecurityRoleTemplateDescriptionTransfers;
    }
    
    public void updatePartySecurityRoleTemplateDescriptionFromValue(PartySecurityRoleTemplateDescriptionValue partySecurityRoleTemplateDescriptionValue, BasePK updatedBy) {
        if(partySecurityRoleTemplateDescriptionValue.hasBeenModified()) {
            PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = PartySecurityRoleTemplateDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateDescriptionValue.getPrimaryKey());
            
            partySecurityRoleTemplateDescription.setThruTime(session.START_TIME_LONG);
            partySecurityRoleTemplateDescription.store();
            
            PartySecurityRoleTemplate partySecurityRoleTemplate = partySecurityRoleTemplateDescription.getPartySecurityRoleTemplate();
            Language language = partySecurityRoleTemplateDescription.getLanguage();
            String description = partySecurityRoleTemplateDescriptionValue.getDescription();
            
            partySecurityRoleTemplateDescription = PartySecurityRoleTemplateDescriptionFactory.getInstance().create(session,
                    partySecurityRoleTemplate, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartySecurityRoleTemplateDescription(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription, BasePK deletedBy) {
        partySecurityRoleTemplateDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partySecurityRoleTemplateDescription.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY.name(), partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK deletedBy) {
        List<PartySecurityRoleTemplateDescription> partySecurityRoleTemplateDescriptions = getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplateForUpdate(partySecurityRoleTemplate);
        
        partySecurityRoleTemplateDescriptions.stream().forEach((partySecurityRoleTemplateDescription) -> {
            deletePartySecurityRoleTemplateDescription(partySecurityRoleTemplateDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // --------------------------------------------------------------------------------
    
    public PartySecurityRoleTemplateRole createPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate, SecurityRole securityRole,
            BasePK createdBy) {
        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = PartySecurityRoleTemplateRoleFactory.getInstance().create(partySecurityRoleTemplate,
                securityRole, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRoleTemplateRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partySecurityRoleTemplateRole;
    }
    
    private PartySecurityRoleTemplateRole getPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate,
            SecurityRole securityRole, EntityPermission entityPermission) {
        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles " +
                        "WHERE psrtr_psrt_partysecurityroletemplateid = ? AND psrtr_srol_securityroleid = ? AND psrtr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles " +
                        "WHERE psrtr_psrt_partysecurityroletemplateid = ? AND psrtr_srol_securityroleid = ? AND psrtr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateRole = PartySecurityRoleTemplateRoleFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateRole;
    }
    
    
    public PartySecurityRoleTemplateRole getPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate,
            SecurityRole securityRole) {
        return getPartySecurityRoleTemplateRole(partySecurityRoleTemplate, securityRole, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplateRole getPartySecurityRoleTemplateRoleForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate,
            SecurityRole securityRole) {
        return getPartySecurityRoleTemplateRole(partySecurityRoleTemplate, securityRole, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRoles(PartySecurityRoleTemplate partySecurityRoleTemplate,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                        "WHERE psrtr_psrt_partysecurityroletemplateid = ? AND psrtr_thrutime = ? " +
                        "AND psrtr_srol_securityroleid = srol_securityroleid AND srol_lastdetailid = sroldt_securityroledetailid " +
                        "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                        "ORDER BY srgdt_sortorder, srgdt_securityrolegroupname, sroldt_sortorder, sroldt_securityrolename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles " +
                        "WHERE psrtr_psrt_partysecurityroletemplateid = ? AND psrtr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateRoles = PartySecurityRoleTemplateRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateRoles;
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRoles(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesBySecurityRole(SecurityRole securityRole,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles " +
                        "WHERE psrtr_srol_securityroleid = ? AND psrtr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplateroles " +
                        "WHERE psrtr_srol_securityroleid = ? AND psrtr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateRoles = PartySecurityRoleTemplateRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateRoles;
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesBySecurityRole(SecurityRole securityRole) {
        return getPartySecurityRoleTemplateRolesBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getPartySecurityRoleTemplateRolesBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateRoleTransfer getPartySecurityRoleTemplateRoleTransfer(UserVisit userVisit, PartySecurityRoleTemplateRole partySecurityRoleTemplateRole) {
        return getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateRoleTransferCache().getPartySecurityRoleTemplateRoleTransfer(partySecurityRoleTemplateRole);
    }
    
    public List<PartySecurityRoleTemplateRoleTransfer> getPartySecurityRoleTemplateRoleTransfers(UserVisit userVisit, List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles) {
        List<PartySecurityRoleTemplateRoleTransfer> partySecurityRoleTemplateRoleTransfers = new ArrayList<>(partySecurityRoleTemplateRoles.size());
        PartySecurityRoleTemplateRoleTransferCache partySecurityRoleTemplateRoleTransferCache = getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateRoleTransferCache();

        partySecurityRoleTemplateRoles.stream().forEach((partySecurityRoleTemplateRole) -> {
            partySecurityRoleTemplateRoleTransfers.add(partySecurityRoleTemplateRoleTransferCache.getPartySecurityRoleTemplateRoleTransfer(partySecurityRoleTemplateRole));
        });

        return partySecurityRoleTemplateRoleTransfers;
    }
    
    public List<PartySecurityRoleTemplateRoleTransfer> getPartySecurityRoleTemplateRoleTransfers(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoleTransfers(userVisit, getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate));
    }
    
    public void deletePartySecurityRoleTemplateRole(PartySecurityRoleTemplateRole partySecurityRoleTemplateRole, BasePK deletedBy) {
        partySecurityRoleTemplateRole.setThruTime(session.START_TIME_LONG);
        partySecurityRoleTemplateRole.store();
        
        sendEventUsingNames(partySecurityRoleTemplateRole.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY.name(), partySecurityRoleTemplateRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // --------------------------------------------------------------------------------
    
    public PartySecurityRoleTemplateTrainingClass createPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, BasePK createdBy) {
        PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass = PartySecurityRoleTemplateTrainingClassFactory.getInstance().create(partySecurityRoleTemplate,
                trainingClass, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRoleTemplateTrainingClass.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partySecurityRoleTemplateTrainingClass;
    }
    
    private PartySecurityRoleTemplateTrainingClass getPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, EntityPermission entityPermission) {
        PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses " +
                        "WHERE psrtrncls_psrt_partysecurityroletemplateid = ? AND psrtrncls_trncls_trainingclassid = ? AND psrtrncls_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses " +
                        "WHERE psrtrncls_psrt_partysecurityroletemplateid = ? AND psrtrncls_trncls_trainingclassid = ? AND psrtrncls_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateTrainingClassFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, trainingClass.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClass = PartySecurityRoleTemplateTrainingClassFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateTrainingClass;
    }
    
    
    public PartySecurityRoleTemplateTrainingClass getPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplate, trainingClass, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplateTrainingClass getPartySecurityRoleTemplateTrainingClassForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplate, trainingClass, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClasses(PartySecurityRoleTemplate partySecurityRoleTemplate,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses, trainingclasses, trainingclassdetails " +
                        "WHERE psrtrncls_psrt_partysecurityroletemplateid = ? AND psrtrncls_thrutime = ? " +
                        "AND psrtrncls_trncls_trainingclassid = trncls_trainingclassid AND trncls_lastdetailid = trnclsdt_trainingclassdetailid " +
                        "ORDER BY trnclsdt_sortorder, trnclsdt_trainingclassname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses " +
                        "WHERE psrtrncls_psrt_partysecurityroletemplateid = ? AND psrtrncls_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateTrainingClassFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClasses = PartySecurityRoleTemplateTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateTrainingClasses;
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClasses(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesByTrainingClass(TrainingClass trainingClass,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses, partysecurityroletemplates, partysecurityroletemplatedetails " +
                        "WHERE psrtrncls_trncls_trainingclassid = ? AND psrtrncls_thrutime = ? " +
                        "AND psrtrncls_psrt_partysecurityroletemplateid = psrt_partysecurityroletemplateid AND psrt_lastdetailid = psrtdt_partysecurityroletemplatedetailid " +
                        "ORDER BY psrtdt_sortorder, psrtdt_partysecurityroletemplatename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partysecurityroletemplatetrainingclasses " +
                        "WHERE psrtrncls_trncls_trainingclassid = ? AND psrtrncls_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartySecurityRoleTemplateTrainingClassFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, trainingClass.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClasses = PartySecurityRoleTemplateTrainingClassFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateTrainingClasses;
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesByTrainingClass(TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesByTrainingClassForUpdate(TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass, EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateTrainingClassTransfer getPartySecurityRoleTemplateTrainingClassTransfer(UserVisit userVisit, PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass) {
        return getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTrainingClassTransferCache().getPartySecurityRoleTemplateTrainingClassTransfer(partySecurityRoleTemplateTrainingClass);
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfers(UserVisit userVisit, List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses) {
        List<PartySecurityRoleTemplateTrainingClassTransfer> partySecurityRoleTemplateTrainingClassTransfers = new ArrayList<>(partySecurityRoleTemplateTrainingClasses.size());
        PartySecurityRoleTemplateTrainingClassTransferCache partySecurityRoleTemplateTrainingClassTransferCache = getSecurityTransferCaches(userVisit).getPartySecurityRoleTemplateTrainingClassTransferCache();

        partySecurityRoleTemplateTrainingClasses.stream().forEach((partySecurityRoleTemplateTrainingClass) -> {
            partySecurityRoleTemplateTrainingClassTransfers.add(partySecurityRoleTemplateTrainingClassTransferCache.getPartySecurityRoleTemplateTrainingClassTransfer(partySecurityRoleTemplateTrainingClass));
        });

        return partySecurityRoleTemplateTrainingClassTransfers;
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfers(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClassTransfers(userVisit, getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate));
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfersByTrainingClass(UserVisit userVisit, TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClassTransfers(userVisit, getPartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass));
    }
    
    public void deletePartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass, BasePK deletedBy) {
        partySecurityRoleTemplateTrainingClass.setThruTime(session.START_TIME_LONG);
        partySecurityRoleTemplateTrainingClass.store();
        
        sendEventUsingNames(partySecurityRoleTemplateTrainingClass.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY.name(), partySecurityRoleTemplateTrainingClass.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Uses
    // --------------------------------------------------------------------------------
    
    public PartySecurityRoleTemplateUse createPartySecurityRoleTemplateUse(Party party, PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK createdBy) {
        PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = PartySecurityRoleTemplateUseFactory.getInstance().create(party, partySecurityRoleTemplate,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRoleTemplate.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        syncPartySecurityRoles(party, partySecurityRoleTemplate, createdBy);
        
        return partySecurityRoleTemplateUse;
    }
    
    private PartySecurityRoleTemplateUse getPartySecurityRoleTemplateUse(Party party, EntityPermission entityPermission) {
        PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_par_partyid = ? AND psrtu_thrutime = ?";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_par_partyid = ? AND psrtu_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartySecurityRoleTemplateUseFactory.getInstance().prepareStatement(
                    entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateUse = PartySecurityRoleTemplateUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateUse;
    }
    
    public PartySecurityRoleTemplateUse getPartySecurityRoleTemplateUse(Party party) {
        return getPartySecurityRoleTemplateUse(party, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRoleTemplateUse getPartySecurityRoleTemplateUseForUpdate(Party party) {
        return getPartySecurityRoleTemplateUse(party, EntityPermission.READ_WRITE);
    }
    
    public PartySecurityRoleTemplateUseValue getPartySecurityRoleTemplateUseValueForUpdate(Party party) {
        return getPartySecurityRoleTemplateUse(party, EntityPermission.READ_WRITE).getPartySecurityRoleTemplateUseValue().clone();
    }
    
    public void updatePartySecurityRoleTemplateUseFromValue(PartySecurityRoleTemplateUseValue partySecurityRoleTemplateUseValue, BasePK updatedBy) {
        if(partySecurityRoleTemplateUseValue.hasBeenModified()) {
            PartySecurityRoleTemplateUsePK partySecurityRoleTemplateUsePK = partySecurityRoleTemplateUseValue.getPrimaryKey();
            PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = PartySecurityRoleTemplateUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateUsePK);

            partySecurityRoleTemplateUse.setThruTime(session.START_TIME_LONG);
            partySecurityRoleTemplateUse.store();

            PartyPK partyPK = partySecurityRoleTemplateUse.getPartyPK();
            PartySecurityRoleTemplatePK partySecurityRoleTemplatePK = partySecurityRoleTemplateUseValue.getPartySecurityRoleTemplatePK();

            partySecurityRoleTemplateUse = PartySecurityRoleTemplateUseFactory.getInstance().create(partyPK,
                    partySecurityRoleTemplatePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partySecurityRoleTemplateUse.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);

            syncPartySecurityRoles(partyPK, partySecurityRoleTemplatePK, updatedBy);
        }
    }
    
    private List<PartySecurityRoleTemplateUse> getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_psrt_partysecurityroletemplateid = ? AND psrtu_thrutime = ?";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_psrt_partysecurityroletemplateid = ? AND psrtu_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartySecurityRoleTemplateUseFactory.getInstance().prepareStatement(
                    entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateUses = PartySecurityRoleTemplateUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoleTemplateUses;
    }
    
    public List<PartySecurityRoleTemplateUse> getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateUse> getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplateForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    public void deletePartySecurityRoleTemplateUse(PartySecurityRoleTemplateUse partySecurityRoleTemplateUse, BasePK deletedBy) {
        partySecurityRoleTemplateUse.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partySecurityRoleTemplateUse.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY.name(), partySecurityRoleTemplateUse.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartySecurityRoleTemplateUseByParty(Party party, BasePK deletedBy) {
        PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = getPartySecurityRoleTemplateUseForUpdate(party);
        
        if(partySecurityRoleTemplateUse != null) {
            deletePartySecurityRoleTemplateUse(partySecurityRoleTemplateUse, deletedBy);
        }
    }
    
    private void syncPartySecurityRoles(PartyPK partyPK, PartySecurityRoleTemplatePK partySecurityRoleTemplatePK, BasePK syncedBy) {
        Party party = getPartyControl().getPartyByPK(partyPK);
        PartySecurityRoleTemplate partySecurityRoleTemplate = getPartySecurityRoleTemplateFromPK(partySecurityRoleTemplatePK);
        
        syncPartySecurityRoles(party, partySecurityRoleTemplate, syncedBy);
    }
    
    private void syncPartySecurityRoles(Party party, PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK syncedBy) {
        List<PartySecurityRole> partySecurityRoles = getPartySecurityRolesForUpdate(party);
        List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles = getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate);
        
        List<SecurityRole> currentSecurityRoles = new ArrayList<>(partySecurityRoles.size());
        partySecurityRoles.stream().forEach((partySecurityRole) -> {
            currentSecurityRoles.add(partySecurityRole.getSecurityRole());
        });
        
        List<SecurityRole> targetSecurityRoles = new ArrayList<>(partySecurityRoleTemplateRoles.size());
        partySecurityRoleTemplateRoles.stream().forEach((partySecurityRoleTemplateRole) -> {
            targetSecurityRoles.add(partySecurityRoleTemplateRole.getSecurityRole());
        });
        
        Set<SecurityRole> securityRolesToDelete = new HashSet<>(currentSecurityRoles);
        securityRolesToDelete.removeAll(targetSecurityRoles);
        securityRolesToDelete.stream().map((securityRole) -> getPartySecurityRoleForUpdate(party, securityRole)).filter((partySecurityRole) -> (partySecurityRole != null)).forEach((partySecurityRole) -> {
            deletePartySecurityRole(partySecurityRole, syncedBy);
        });
        
        Set<SecurityRole> securityRolesToAdd = new HashSet<>(targetSecurityRoles);
        securityRolesToAdd.removeAll(currentSecurityRoles);
        securityRolesToAdd.stream().forEach((securityRole) -> {
            createPartySecurityRole(party, securityRole, syncedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Roles
    // --------------------------------------------------------------------------------
    
    public PartySecurityRole createPartySecurityRole(Party party, SecurityRole securityRole, BasePK createdBy) {
        PartySecurityRole partySecurityRole = PartySecurityRoleFactory.getInstance().create(party, securityRole,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partySecurityRole;
    }
    
    private PartySecurityRole getPartySecurityRole(Party party, SecurityRole securityRole, EntityPermission entityPermission) {
        PartySecurityRole partySecurityRole = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ?";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";
            PreparedStatement ps = PartySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRole = PartySecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRole;
    }
    
    public PartySecurityRole getPartySecurityRole(Party party, SecurityRole securityRole) {
        return getPartySecurityRole(party, securityRole, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRole getPartySecurityRoleForUpdate(Party party, SecurityRole securityRole) {
        return getPartySecurityRole(party, securityRole, EntityPermission.READ_WRITE);
    }
    
    public boolean partySecurityRoleExists(PartyPK partyPK, SecurityRolePK securityRolePK) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM partysecurityroles " +
                "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ?",
                partyPK, securityRolePK, Session.MAX_TIME_LONG) == 1;
    }
    
    private List<PartySecurityRole> getPartySecurityRoles(Party party, EntityPermission entityPermission) {
        List<PartySecurityRole> partySecurityRoles = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                    "WHERE psrol_par_partyid = ? AND psrol_thrutime = ? " +
                    "AND psrol_srol_securityroleid = srol_securityroleid AND srol_lastdetailid = sroldt_securityroledetailid " +
                    "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                    "ORDER BY srgdt_sortorder, srgdt_securityrolegroupname, sroldt_sortorder, sroldt_securityrolename";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoles = PartySecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoles;
    }
    
    public List<PartySecurityRole> getPartySecurityRoles(Party party) {
        return getPartySecurityRoles(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRole> getPartySecurityRolesForUpdate(Party party) {
        return getPartySecurityRoles(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRole> getPartySecurityRolesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        List<PartySecurityRole> partySecurityRoles = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles, parties, partydetails, partytypes " +
                    "WHERE psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "AND psrol_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                    "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                    "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoles = PartySecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partySecurityRoles;
    }
    
    public List<PartySecurityRole> getPartySecurityRolesBySecurityRole(SecurityRole securityRole) {
        return getPartySecurityRolesBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRole> getPartySecurityRolesBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getPartySecurityRolesBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    public void deletePartySecurityRole(PartySecurityRole partySecurityRole, BasePK deletedBy) {
        partySecurityRole.setThruTime(session.START_TIME_LONG);
        partySecurityRole.store();
        
        sendEventUsingNames(partySecurityRole.getParty().getPrimaryKey(), EventTypes.MODIFY.name(), partySecurityRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartySecurityRoles(List<PartySecurityRole> partySecurityRoles, BasePK deletedBy) {
        partySecurityRoles.stream().forEach((partySecurityRole) -> {
            deletePartySecurityRole(partySecurityRole, deletedBy);
        });
    }
    
    public void deletePartySecurityRolesBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        deletePartySecurityRoles(getPartySecurityRolesBySecurityRoleForUpdate(securityRole), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Entity Security Roles
    // --------------------------------------------------------------------------------
    
    public PartyEntitySecurityRole createPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole,
            BasePK createdBy) {
        PartyEntitySecurityRole partyEntitySecurityRole = PartyEntitySecurityRoleFactory.getInstance().create(party, entityInstance,
                securityRole, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyEntitySecurityRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyEntitySecurityRole;
    }
    
    private PartyEntitySecurityRole getPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole,
            EntityPermission entityPermission) {
        PartyEntitySecurityRole partyEntitySecurityRole = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ?";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";
            PreparedStatement ps = PartyEntitySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            partyEntitySecurityRole = PartyEntitySecurityRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEntitySecurityRole;
    }
    
    public PartyEntitySecurityRole getPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole) {
        return getPartyEntitySecurityRole(party, entityInstance, securityRole, EntityPermission.READ_ONLY);
    }
    
    public PartyEntitySecurityRole getPartyEntitySecurityRoleForUpdate(Party party, EntityInstance entityInstance, SecurityRole securityRole) {
        return getPartyEntitySecurityRole(party, entityInstance, securityRole, EntityPermission.READ_WRITE);
    }
    
    public boolean partyEntitySecurityRoleExists(PartyPK partyPK, EntityInstance entityInstancePK, SecurityRolePK securityRolePK) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM partyentitysecurityroles " +
                "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ?",
                partyPK, entityInstancePK, securityRolePK, Session.MAX_TIME_LONG) == 1;
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByParty(Party party, EntityPermission entityPermission) {
        List<PartyEntitySecurityRole> partyEntitySecurityRoles = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_thrutime = ? " +
                    "AND pensrol_eni_entityinstanceid = ? AND pensrol_thrutime = ? " +
                    "AND pensrol_eni_entityinstanceid = eni_entityinstanceid " +
                    "AND eni_ent_entitytypeid = ent_entitytypeid " +
                    "AND ent_lastdetailid = entdt_entitytypedetailid " +
                    "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid " +
                    "AND cvnd_lastdetailid = cvndd_componentvendordetailid " + 
                    "AND pensrol_srol_securityroleid = srol_securityroleid " +
                    "AND srol_lastdetailid = sroldt_securityroledetailid " +
                    "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid " +
                    "AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                    "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, sroldt_sortorder, sroldt_securityrolename, srgdt_sortorder, srgdt_securityrolegroupname";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartyEntitySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = PartyEntitySecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEntitySecurityRoles;
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByParty(Party party) {
        return getPartyEntitySecurityRolesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByPartyForUpdate(Party party) {
        return getPartyEntitySecurityRolesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<PartyEntitySecurityRole> partyEntitySecurityRoles = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles, parties, partydetails, partytypes, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                    "WHERE pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "AND pensrol_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                    "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                    "AND pensrol_srol_securityroleid = srol_securityroleid " +
                    "AND srol_lastdetailid = sroldt_securityroledetailid " +
                    "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid " +
                    "AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                    "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, sroldt_sortorder, sroldt_securityrolename, srgdt_sortorder, srgdt_securityrolegroupname";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_eni_entityinstanceid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartyEntitySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = PartyEntitySecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEntitySecurityRoles;
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstance(EntityInstance entityInstance) {
        return getPartyEntitySecurityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyEntitySecurityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        List<PartyEntitySecurityRole> partyEntitySecurityRoles = null;
        
        try {
            final String queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles, parties, partydetails, partytypes, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails " +
                    "WHERE pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "AND pensrol_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                    "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                    "AND pensrol_eni_entityinstanceid = eni_entityinstanceid " +
                    "AND eni_ent_entitytypeid = ent_entitytypeid " +
                    "AND ent_lastdetailid = entdt_entitytypedetailid " +
                    "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid " +
                    "AND cvnd_lastdetailid = cvndd_componentvendordetailid " + 
                    "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename";
            final String queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";
            
            PreparedStatement ps = PartyEntitySecurityRoleFactory.getInstance().prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = PartyEntitySecurityRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyEntitySecurityRoles;
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesBySecurityRole(SecurityRole securityRole) {
        return getPartyEntitySecurityRolesBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getPartyEntitySecurityRolesBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    public void deletePartyEntitySecurityRole(PartyEntitySecurityRole partyEntitySecurityRole, BasePK deletedBy) {
        partyEntitySecurityRole.setThruTime(session.START_TIME_LONG);
        partyEntitySecurityRole.store();
        
        sendEventUsingNames(partyEntitySecurityRole.getParty().getPrimaryKey(), EventTypes.MODIFY.name(), partyEntitySecurityRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyEntitySecurityRoles(List<PartyEntitySecurityRole> partyEntitySecurityRoles, BasePK deletedBy) {
        partyEntitySecurityRoles.stream().forEach((partyEntitySecurityRole) -> {
            deletePartyEntitySecurityRole(partyEntitySecurityRole, deletedBy);
        });
    }
    
    public void deletePartyEntitySecurityRolesByParty(Party party, BasePK deletedBy) {
        deletePartyEntitySecurityRoles(getPartyEntitySecurityRolesByPartyForUpdate(party), deletedBy);
    }
    
    public void deletePartyEntitySecurityRolesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deletePartyEntitySecurityRoles(getPartyEntitySecurityRolesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }
    
    public void deletePartyEntitySecurityRolesBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        deletePartyEntitySecurityRoles(getPartyEntitySecurityRolesBySecurityRoleForUpdate(securityRole), deletedBy);
    }
    
}
