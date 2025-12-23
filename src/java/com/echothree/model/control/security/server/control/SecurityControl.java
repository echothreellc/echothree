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

package com.echothree.model.control.security.server.control;

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
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.security.common.pk.PartySecurityRoleTemplatePK;
import com.echothree.model.data.security.common.pk.SecurityRoleGroupPK;
import com.echothree.model.data.security.common.pk.SecurityRolePK;
import com.echothree.model.data.security.server.entity.PartyEntitySecurityRole;
import com.echothree.model.data.security.server.entity.PartySecurityRole;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateUse;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDescription;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDescription;
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
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.training.server.entity.TrainingClass;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class SecurityControl
        extends BaseModelControl {
    
    /** Creates a new instance of SecurityControl */
    protected SecurityControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Security Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    SecurityRoleGroupTransferCache securityRoleGroupTransferCache;

    @Inject
    SecurityRoleGroupDescriptionTransferCache securityRoleGroupDescriptionTransferCache;

    @Inject
    SecurityRoleTransferCache securityRoleTransferCache;

    @Inject
    SecurityRoleDescriptionTransferCache securityRoleDescriptionTransferCache;

    @Inject
    SecurityRolePartyTypeTransferCache securityRolePartyTypeTransferCache;

    @Inject
    PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache;

    @Inject
    PartySecurityRoleTemplateDescriptionTransferCache partySecurityRoleTemplateDescriptionTransferCache;

    @Inject
    PartySecurityRoleTemplateRoleTransferCache partySecurityRoleTemplateRoleTransferCache;

    @Inject
    PartySecurityRoleTemplateTrainingClassTransferCache partySecurityRoleTemplateTrainingClassTransferCache;

    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected SecurityRoleGroupFactory securityRoleGroupFactory;

    @Inject
    protected SecurityRoleGroupDetailFactory securityRoleGroupDetailFactory;

    public SecurityRoleGroup createSecurityRoleGroup(String securityRoleGroupName, SecurityRoleGroup parentSecurityRoleGroup,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();
        var defaultFound = defaultSecurityRoleGroup != null;
        
        if(defaultFound && isDefault) {
            var defaultSecurityRoleGroupDetailValue = getDefaultSecurityRoleGroupDetailValueForUpdate();
            
            defaultSecurityRoleGroupDetailValue.setIsDefault(false);
            updateSecurityRoleGroupFromValue(defaultSecurityRoleGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var securityRoleGroup = securityRoleGroupFactory.create();
        var securityRoleGroupDetail = securityRoleGroupDetailFactory.create(
                securityRoleGroup, securityRoleGroupName, parentSecurityRoleGroup, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        securityRoleGroup = securityRoleGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                securityRoleGroup.getPrimaryKey());
        securityRoleGroup.setActiveDetail(securityRoleGroupDetail);
        securityRoleGroup.setLastDetail(securityRoleGroupDetail);
        securityRoleGroup.store();
        
        sendEvent(securityRoleGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return securityRoleGroup;
    }

    public long countSecurityRoleGroups() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM securityrolegroups, securityrolegroupdetails " +
                        "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid");
    }

    public long countSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM securityrolegroups, securityrolegroupdetails " +
                        "WHERE srg_activedetailid = srgdt_securityrolegroupdetailid " +
                        "AND srgdt_parentsecurityrolegroupid = ?",
                parentSecurityRoleGroup);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SecurityRoleGroup */
    public SecurityRoleGroup getSecurityRoleGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SecurityRoleGroupPK(entityInstance.getEntityUniqueId());

        return securityRoleGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public SecurityRoleGroup getSecurityRoleGroupByEntityInstance(EntityInstance entityInstance) {
        return getSecurityRoleGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SecurityRoleGroup getSecurityRoleGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSecurityRoleGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
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

    public SecurityRoleGroup getSecurityRoleGroupByName(String securityRoleGroupName, EntityPermission entityPermission) {
        return securityRoleGroupFactory.getEntityFromQuery(entityPermission, getSecurityRoleGroupByNameQueries, securityRoleGroupName);
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

    public SecurityRoleGroup getDefaultSecurityRoleGroup(EntityPermission entityPermission) {
        return securityRoleGroupFactory.getEntityFromQuery(entityPermission, getDefaultSecurityRoleGroupQueries);
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
        return securityRoleGroupFactory.getEntitiesFromQuery(entityPermission, getSecurityRoleGroupsQueries);
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
        return securityRoleGroupFactory.getEntitiesFromQuery(entityPermission, getSecurityRoleGroupsByParentSecurityRoleGroupQueries,
                parentSecurityRoleGroup);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup) {
        return getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup, EntityPermission.READ_ONLY);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroupForUpdate(SecurityRoleGroup parentSecurityRoleGroup) {
        return getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup, EntityPermission.READ_WRITE);
    }

    public SecurityRoleGroupTransfer getSecurityRoleGroupTransfer(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        return securityRoleGroupTransferCache.getSecurityRoleGroupTransfer(userVisit, securityRoleGroup);
    }
    
    public List<SecurityRoleGroupTransfer> getSecurityRoleGroupTransfers(UserVisit userVisit, Collection<SecurityRoleGroup> securityRoleGroups) {
        List<SecurityRoleGroupTransfer> securityRoleGroupTransfers = new ArrayList<>(securityRoleGroups.size());
        
        securityRoleGroups.forEach((securityRoleGroup) ->
                securityRoleGroupTransfers.add(securityRoleGroupTransferCache.getSecurityRoleGroupTransfer(userVisit, securityRoleGroup))
        );
        
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
        var securityRoleGroups = getSecurityRoleGroups();
        var size = securityRoleGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSecurityRoleGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var securityRoleGroup : securityRoleGroups) {
            var securityRoleGroupDetail = securityRoleGroup.getLastDetail();
            var value = securityRoleGroupDetail.getSecurityRoleGroupName();
            
            if(!value.equals(SecurityRoleGroups.ROOT.name())) {
                var label = getBestSecurityRoleGroupDescription(securityRoleGroup, language);

                labels.add(label == null? value: label);
                values.add(value);

                var usingDefaultChoice = Objects.equals(defaultSecurityRoleGroupChoice, value);
                if(usingDefaultChoice || (defaultValue == null && securityRoleGroupDetail.getIsDefault())) {
                    defaultValue = value;
                }
            }
        }
        
        return new SecurityRoleGroupChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentSecurityRoleGroupSafe(SecurityRoleGroup securityRoleGroup,
            SecurityRoleGroup parentSecurityRoleGroup) {
        var safe = true;
        
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
            var securityRoleGroup = securityRoleGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     securityRoleGroupDetailValue.getSecurityRoleGroupPK());
            var securityRoleGroupDetail = securityRoleGroup.getActiveDetailForUpdate();
            
            securityRoleGroupDetail.setThruTime(session.getStartTime());
            securityRoleGroupDetail.store();

            var securityRoleGroupPK = securityRoleGroupDetail.getSecurityRoleGroupPK();
            var securityRoleGroupName = securityRoleGroupDetailValue.getSecurityRoleGroupName();
            var parentSecurityRoleGroupPK = securityRoleGroupDetailValue.getParentSecurityRoleGroupPK();
            var isDefault = securityRoleGroupDetailValue.getIsDefault();
            var sortOrder = securityRoleGroupDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();
                var defaultFound = defaultSecurityRoleGroup != null && !defaultSecurityRoleGroup.equals(securityRoleGroup);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSecurityRoleGroupDetailValue = getDefaultSecurityRoleGroupDetailValueForUpdate();
                    
                    defaultSecurityRoleGroupDetailValue.setIsDefault(false);
                    updateSecurityRoleGroupFromValue(defaultSecurityRoleGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            securityRoleGroupDetail = securityRoleGroupDetailFactory.create(securityRoleGroupPK,
                    securityRoleGroupName, parentSecurityRoleGroupPK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            securityRoleGroup.setActiveDetail(securityRoleGroupDetail);
            securityRoleGroup.setLastDetail(securityRoleGroupDetail);
            
            sendEvent(securityRoleGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateSecurityRoleGroupFromValue(SecurityRoleGroupDetailValue securityRoleGroupDetailValue, BasePK updatedBy) {
        updateSecurityRoleGroupFromValue(securityRoleGroupDetailValue, true, updatedBy);
    }
    
    private void deleteSecurityRoleGroup(SecurityRoleGroup securityRoleGroup, boolean checkDefault, BasePK deletedBy) {
        var securityRoleGroupDetail = securityRoleGroup.getLastDetailForUpdate();

        deleteSecurityRoleGroupsByParentSecurityRoleGroup(securityRoleGroup, deletedBy);
        deleteSecurityRolesBySecurityRoleGroup(securityRoleGroup, deletedBy);
        deleteSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup, deletedBy);
        
        securityRoleGroupDetail.setThruTime(session.getStartTime());
        securityRoleGroup.setActiveDetail(null);
        securityRoleGroup.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultSecurityRoleGroup = getDefaultSecurityRoleGroup();

            if(defaultSecurityRoleGroup == null) {
                var securityRoleGroups = getSecurityRoleGroupsForUpdate();

                if(!securityRoleGroups.isEmpty()) {
                    var iter = securityRoleGroups.iterator();
                    if(iter.hasNext()) {
                        defaultSecurityRoleGroup = iter.next();
                    }
                    var securityRoleGroupDetailValue = Objects.requireNonNull(defaultSecurityRoleGroup).getLastDetailForUpdate().getSecurityRoleGroupDetailValue().clone();

                    securityRoleGroupDetailValue.setIsDefault(true);
                    updateSecurityRoleGroupFromValue(securityRoleGroupDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(securityRoleGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        deleteSecurityRoleGroup(securityRoleGroup, true, deletedBy);
    }

    private void deleteSecurityRoleGroups(List<SecurityRoleGroup> securityRoleGroups, boolean checkDefault, BasePK deletedBy) {
        securityRoleGroups.forEach((securityRoleGroup) -> deleteSecurityRoleGroup(securityRoleGroup, checkDefault, deletedBy));
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
    
    @Inject
    protected SecurityRoleGroupDescriptionFactory securityRoleGroupDescriptionFactory;
    
    public SecurityRoleGroupDescription createSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, String description, BasePK createdBy) {
        var securityRoleGroupDescription = securityRoleGroupDescriptionFactory.create(securityRoleGroup, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(securityRoleGroup.getPrimaryKey(), EventTypes.MODIFY, securityRoleGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return securityRoleGroupDescription;
    }
    
    private SecurityRoleGroupDescription getSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, EntityPermission entityPermission) {
        SecurityRoleGroupDescription securityRoleGroupDescription;
        
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

            var ps = securityRoleGroupDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            securityRoleGroupDescription = securityRoleGroupDescriptionFactory.getEntityFromQuery(entityPermission, ps);
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
        List<SecurityRoleGroupDescription> securityRoleGroupDescriptions;
        
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

            var ps = securityRoleGroupDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            securityRoleGroupDescriptions = securityRoleGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
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
        var securityRoleGroupDescription = getSecurityRoleGroupDescription(securityRoleGroup, language);
        
        if(securityRoleGroupDescription == null && !language.getIsDefault()) {
            securityRoleGroupDescription = getSecurityRoleGroupDescription(securityRoleGroup, partyControl.getDefaultLanguage());
        }
        
        if(securityRoleGroupDescription == null) {
            description = securityRoleGroup.getLastDetail().getSecurityRoleGroupName();
        } else {
            description = securityRoleGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public SecurityRoleGroupDescriptionTransfer getSecurityRoleGroupDescriptionTransfer(UserVisit userVisit, SecurityRoleGroupDescription securityRoleGroupDescription) {
        return securityRoleGroupDescriptionTransferCache.getSecurityRoleGroupDescriptionTransfer(userVisit, securityRoleGroupDescription);
    }
    
    public List<SecurityRoleGroupDescriptionTransfer> getSecurityRoleGroupDescriptionTransfersBySecurityRoleGroup(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        var securityRoleGroupDescriptions = getSecurityRoleGroupDescriptionsBySecurityRoleGroup(securityRoleGroup);
        List<SecurityRoleGroupDescriptionTransfer> securityRoleGroupDescriptionTransfers = new ArrayList<>(securityRoleGroupDescriptions.size());
        
        securityRoleGroupDescriptions.forEach((securityRoleGroupDescription) ->
                securityRoleGroupDescriptionTransfers.add(securityRoleGroupDescriptionTransferCache.getSecurityRoleGroupDescriptionTransfer(userVisit, securityRoleGroupDescription))
        );
        
        return securityRoleGroupDescriptionTransfers;
    }
    
    public void updateSecurityRoleGroupDescriptionFromValue(SecurityRoleGroupDescriptionValue securityRoleGroupDescriptionValue, BasePK updatedBy) {
        if(securityRoleGroupDescriptionValue.hasBeenModified()) {
            var securityRoleGroupDescription = securityRoleGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, securityRoleGroupDescriptionValue.getPrimaryKey());
            
            securityRoleGroupDescription.setThruTime(session.getStartTime());
            securityRoleGroupDescription.store();

            var securityRoleGroup = securityRoleGroupDescription.getSecurityRoleGroup();
            var language = securityRoleGroupDescription.getLanguage();
            var description = securityRoleGroupDescriptionValue.getDescription();
            
            securityRoleGroupDescription = securityRoleGroupDescriptionFactory.create(securityRoleGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(securityRoleGroup.getPrimaryKey(), EventTypes.MODIFY, securityRoleGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSecurityRoleGroupDescription(SecurityRoleGroupDescription securityRoleGroupDescription, BasePK deletedBy) {
        securityRoleGroupDescription.setThruTime(session.getStartTime());
        
        sendEvent(securityRoleGroupDescription.getSecurityRoleGroupPK(), EventTypes.MODIFY, securityRoleGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSecurityRoleGroupDescriptionsBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        var securityRoleGroupDescriptions = getSecurityRoleGroupDescriptionsBySecurityRoleGroupForUpdate(securityRoleGroup);
        
        securityRoleGroupDescriptions.forEach((securityRoleGroupDescription) -> 
                deleteSecurityRoleGroupDescription(securityRoleGroupDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Security Roles
    // --------------------------------------------------------------------------------
    
    @Inject
    protected SecurityRoleFactory securityRoleFactory;
    
    @Inject
    protected SecurityRoleDetailFactory securityRoleDetailFactory;
    
    public SecurityRole createSecurityRole(SecurityRoleGroup securityRoleGroup, String securityRoleName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
        var defaultFound = defaultSecurityRole != null;
        
        if(defaultFound && isDefault) {
            var defaultSecurityRoleDetailValue = getDefaultSecurityRoleDetailValueForUpdate(securityRoleGroup);
            
            defaultSecurityRoleDetailValue.setIsDefault(false);
            updateSecurityRoleFromValue(defaultSecurityRoleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var securityRole = securityRoleFactory.create();
        var securityRoleDetail = securityRoleDetailFactory.create(
                securityRole, securityRoleGroup, securityRoleName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        securityRole = securityRoleFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                securityRole.getPrimaryKey());
        securityRole.setActiveDetail(securityRoleDetail);
        securityRole.setLastDetail(securityRoleDetail);
        securityRole.store();
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return securityRole;
    }
    
    public long countSecurityRoles() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM securityroles, securityroledetails " +
                "WHERE srol_activedetailid = sroldt_securityroledetailid");
    }

    public long countSecurityRolesBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM securityroles, securityroledetails "
                + "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ?",
                securityRoleGroup);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SecurityRole */
    public SecurityRole getSecurityRoleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SecurityRolePK(entityInstance.getEntityUniqueId());

        return securityRoleFactory.getEntityFromPK(entityPermission, pk);
    }

    public SecurityRole getSecurityRoleByEntityInstance(EntityInstance entityInstance) {
        return getSecurityRoleByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SecurityRole getSecurityRoleByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSecurityRoleByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<SecurityRole> getSecurityRoles(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        List<SecurityRole> securityRoles;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ? " +
                        "ORDER BY sroldt_sortorder, sroldt_securityrolename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM securityroles, securityroledetails " +
                        "WHERE srol_activedetailid = sroldt_securityroledetailid AND sroldt_srg_securityrolegroupid = ? " +
                        "FOR UPDATE";
            }

            var ps = securityRoleFactory.prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            
            securityRoles = securityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
    
    public SecurityRole getDefaultSecurityRole(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        SecurityRole securityRole;
        
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

            var ps = securityRoleFactory.prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            
            securityRole = securityRoleFactory.getEntityFromQuery(entityPermission, ps);
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
    
    public SecurityRole getSecurityRoleByName(SecurityRoleGroup securityRoleGroup, String securityRoleName, EntityPermission entityPermission) {
        SecurityRole securityRole;
        
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

            var ps = securityRoleFactory.prepareStatement(query);
            
            ps.setLong(1, securityRoleGroup.getPrimaryKey().getEntityId());
            ps.setString(2, securityRoleName);
            
            securityRole = securityRoleFactory.getEntityFromQuery(entityPermission, ps);
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
        var securityRoles = getSecurityRoles(securityRoleGroup);
        var size = securityRoles.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSecurityRoleChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var securityRole : securityRoles) {
            var securityRoleDetail = securityRole.getLastDetail();
            var label = getBestSecurityRoleDescription(securityRole, language);
            var value = securityRoleDetail.getSecurityRoleName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSecurityRoleChoice != null && defaultSecurityRoleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && securityRoleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SecurityRoleChoicesBean(labels, values, defaultValue);
    }
    
    public SecurityRoleTransfer getSecurityRoleTransfer(UserVisit userVisit, SecurityRole securityRole) {
        return securityRoleTransferCache.getSecurityRoleTransfer(userVisit, securityRole);
    }

    public List<SecurityRoleTransfer> getSecurityRoleTransfers(UserVisit userVisit, Collection<SecurityRole> securityRoles) {
        List<SecurityRoleTransfer> securityRoleTransfers = new ArrayList<>(securityRoles.size());

        securityRoles.forEach((securityRole) ->
                securityRoleTransfers.add(securityRoleTransferCache.getSecurityRoleTransfer(userVisit, securityRole))
        );

        return securityRoleTransfers;
    }

    public List<SecurityRoleTransfer> getSecurityRoleTransfersBySecurityRoleGroup(UserVisit userVisit, SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoleTransfers(userVisit, getSecurityRoles(securityRoleGroup));
    }

    private void updateSecurityRoleFromValue(SecurityRoleDetailValue securityRoleDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(securityRoleDetailValue.hasBeenModified()) {
            var securityRole = securityRoleFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     securityRoleDetailValue.getSecurityRolePK());
            var securityRoleDetail = securityRole.getActiveDetailForUpdate();
            
            securityRoleDetail.setThruTime(session.getStartTime());
            securityRoleDetail.store();

            var securityRolePK = securityRoleDetail.getSecurityRolePK();
            var securityRoleGroup = securityRoleDetail.getSecurityRoleGroup();
            var securityRoleGroupPK = securityRoleGroup.getPrimaryKey();
            var securityRoleName = securityRoleDetailValue.getSecurityRoleName();
            var isDefault = securityRoleDetailValue.getIsDefault();
            var sortOrder = securityRoleDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
                var defaultFound = defaultSecurityRole != null && !defaultSecurityRole.equals(securityRole);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultSecurityRoleDetailValue = getDefaultSecurityRoleDetailValueForUpdate(securityRoleGroup);
                    
                    defaultSecurityRoleDetailValue.setIsDefault(false);
                    updateSecurityRoleFromValue(defaultSecurityRoleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            securityRoleDetail = securityRoleDetailFactory.create(securityRolePK, securityRoleGroupPK,
                    securityRoleName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            securityRole.setActiveDetail(securityRoleDetail);
            securityRole.setLastDetail(securityRoleDetail);
            
            sendEvent(securityRolePK, EventTypes.MODIFY, null, null, updatedBy);
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

        var securityRoleDetail = securityRole.getLastDetailForUpdate();
        securityRoleDetail.setThruTime(session.getStartTime());
        securityRole.setActiveDetail(null);
        securityRole.store();
        
        // Check for default, and pick one if necessary
        var securityRoleGroup = securityRoleDetail.getSecurityRoleGroup();
        var defaultSecurityRole = getDefaultSecurityRole(securityRoleGroup);
        if(defaultSecurityRole == null) {
            var securityRoles = getSecurityRolesForUpdate(securityRoleGroup);
            
            if(!securityRoles.isEmpty()) {
                var iter = securityRoles.iterator();
                if(iter.hasNext()) {
                    defaultSecurityRole = iter.next();
                }
                var securityRoleDetailValue = Objects.requireNonNull(defaultSecurityRole).getLastDetailForUpdate().getSecurityRoleDetailValue().clone();
                
                securityRoleDetailValue.setIsDefault(true);
                updateSecurityRoleFromValue(securityRoleDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSecurityRolesBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup, BasePK deletedBy) {
        var securityRoles = getSecurityRolesForUpdate(securityRoleGroup);
        
        securityRoles.forEach((securityRole) -> 
                deleteSecurityRole(securityRole, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected SecurityRoleDescriptionFactory securityRoleDescriptionFactory;
    
    public SecurityRoleDescription createSecurityRoleDescription(SecurityRole securityRole, Language language, String description, BasePK createdBy) {
        var securityRoleDescription = securityRoleDescriptionFactory.create(securityRole, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.MODIFY, securityRoleDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return securityRoleDescription;
    }
    
    private SecurityRoleDescription getSecurityRoleDescription(SecurityRole securityRole, Language language, EntityPermission entityPermission) {
        SecurityRoleDescription securityRoleDescription;
        
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

            var ps = securityRoleDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            securityRoleDescription = securityRoleDescriptionFactory.getEntityFromQuery(entityPermission, ps);
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
        List<SecurityRoleDescription> securityRoleDescriptions;
        
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

            var ps = securityRoleDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            securityRoleDescriptions = securityRoleDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
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
        var securityRoleDescription = getSecurityRoleDescription(securityRole, language);
        
        if(securityRoleDescription == null && !language.getIsDefault()) {
            securityRoleDescription = getSecurityRoleDescription(securityRole, partyControl.getDefaultLanguage());
        }
        
        if(securityRoleDescription == null) {
            description = securityRole.getLastDetail().getSecurityRoleName();
        } else {
            description = securityRoleDescription.getDescription();
        }
        
        return description;
    }
    
    public SecurityRoleDescriptionTransfer getSecurityRoleDescriptionTransfer(UserVisit userVisit, SecurityRoleDescription securityRoleDescription) {
        return securityRoleDescriptionTransferCache.getSecurityRoleDescriptionTransfer(userVisit, securityRoleDescription);
    }
    
    public List<SecurityRoleDescriptionTransfer> getSecurityRoleDescriptionTransfersBySecurityRole(UserVisit userVisit, SecurityRole securityRole) {
        var securityRoleDescriptions = getSecurityRoleDescriptionsBySecurityRole(securityRole);
        List<SecurityRoleDescriptionTransfer> securityRoleDescriptionTransfers = new ArrayList<>(securityRoleDescriptions.size());
        
        securityRoleDescriptions.forEach((securityRoleDescription) ->
                securityRoleDescriptionTransfers.add(securityRoleDescriptionTransferCache.getSecurityRoleDescriptionTransfer(userVisit, securityRoleDescription))
        );
        
        return securityRoleDescriptionTransfers;
    }
    
    public void updateSecurityRoleDescriptionFromValue(SecurityRoleDescriptionValue securityRoleDescriptionValue, BasePK updatedBy) {
        if(securityRoleDescriptionValue.hasBeenModified()) {
            var securityRoleDescription = securityRoleDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, securityRoleDescriptionValue.getPrimaryKey());
            
            securityRoleDescription.setThruTime(session.getStartTime());
            securityRoleDescription.store();

            var securityRole = securityRoleDescription.getSecurityRole();
            var language = securityRoleDescription.getLanguage();
            var description = securityRoleDescriptionValue.getDescription();
            
            securityRoleDescription = securityRoleDescriptionFactory.create(securityRole, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(securityRole.getPrimaryKey(), EventTypes.MODIFY, securityRoleDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSecurityRoleDescription(SecurityRoleDescription securityRoleDescription, BasePK deletedBy) {
        securityRoleDescription.setThruTime(session.getStartTime());
        
        sendEvent(securityRoleDescription.getSecurityRolePK(), EventTypes.MODIFY, securityRoleDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSecurityRoleDescriptionsBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        var securityRoleDescriptions = getSecurityRoleDescriptionsBySecurityRoleForUpdate(securityRole);
        
        securityRoleDescriptions.forEach((securityRoleDescription) -> 
                deleteSecurityRoleDescription(securityRoleDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Training Class Party Types
    // --------------------------------------------------------------------------------
    
    @Inject
    protected SecurityRolePartyTypeFactory securityRolePartyTypeFactory;
    
    public SecurityRolePartyType createSecurityRolePartyType(SecurityRole securityRole, PartyType partyType, Selector partySelector, BasePK createdBy) {
        var securityRolePartyType = securityRolePartyTypeFactory.create(securityRole, partyType, partySelector,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.MODIFY, securityRolePartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return securityRolePartyTypeFactory.getEntityFromQuery(entityPermission, getSecurityRolePartyTypeQueries, securityRole, partyType,
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
        return securityRolePartyTypeFactory.getEntitiesFromQuery(entityPermission, getSecurityRolePartyTypesBySecurityRoleQueries,
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
        return securityRolePartyTypeFactory.getEntitiesFromQuery(entityPermission, getSecurityRolePartyTypesByPartySelectorQueries,
                partySelector, Session.MAX_TIME);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelector(Selector partySelector) {
        return getSecurityRolePartyTypesByPartySelector(partySelector, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelectorForUpdate(Selector partySelector) {
        return getSecurityRolePartyTypesByPartySelector(partySelector, EntityPermission.READ_WRITE);
    }
    
    public SecurityRolePartyTypeTransfer getSecurityRolePartyTypeTransfer(UserVisit userVisit, SecurityRolePartyType securityRolePartyType) {
        return securityRolePartyTypeTransferCache.getSecurityRolePartyTypeTransfer(userVisit, securityRolePartyType);
    }
    
    public List<SecurityRolePartyTypeTransfer> getSecurityRolePartyTypeTransfers(UserVisit userVisit, Collection<SecurityRolePartyType> securityRolePartyTypes) {
        List<SecurityRolePartyTypeTransfer> securityRolePartyTypeTransfers = new ArrayList<>(securityRolePartyTypes.size());
        
        securityRolePartyTypes.forEach((securityRolePartyType) ->
                securityRolePartyTypeTransfers.add(securityRolePartyTypeTransferCache.getSecurityRolePartyTypeTransfer(userVisit, securityRolePartyType))
        );
        
        return securityRolePartyTypeTransfers;
    }
    
    public List<SecurityRolePartyTypeTransfer> getSecurityRolePartyTypeTransfersBySecurityRole(UserVisit userVisit, SecurityRole securityRole) {
        return getSecurityRolePartyTypeTransfers(userVisit, getSecurityRolePartyTypesBySecurityRole(securityRole));
    }
    
    public void updateSecurityRolePartyTypeFromValue(SecurityRolePartyTypeValue securityRolePartyTypeValue, BasePK updatedBy) {
        if(securityRolePartyTypeValue.hasBeenModified()) {
            var securityRolePartyType = securityRolePartyTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    securityRolePartyTypeValue.getPrimaryKey());
            
            securityRolePartyType.setThruTime(session.getStartTime());
            securityRolePartyType.store();

            var securityRolePK = securityRolePartyType.getSecurityRolePK();
            var partyTypePK = securityRolePartyType.getPartyTypePK();
            var partySelectorPK = securityRolePartyTypeValue.getPartySelectorPK();
            
            securityRolePartyType = securityRolePartyTypeFactory.create(securityRolePK, partyTypePK, partySelectorPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(securityRolePK, EventTypes.MODIFY, securityRolePartyType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSecurityRolePartyType(SecurityRolePartyType securityRolePartyType, BasePK deletedBy) {
        securityRolePartyType.setThruTime(session.getStartTime());
        
        sendEvent(securityRolePartyType.getSecurityRolePK(), EventTypes.MODIFY, securityRolePartyType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSecurityRolePartyTypes(List<SecurityRolePartyType> securityRolePartyTypes, BasePK deletedBy) {
        securityRolePartyTypes.forEach((securityRolePartyType) -> 
                deleteSecurityRolePartyType(securityRolePartyType, deletedBy)
        );
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
    
    @Inject
    protected PartySecurityRoleTemplateFactory partySecurityRoleTemplateFactory;
    
    @Inject
    PartySecurityRoleTemplateDetailFactory partySecurityRoleTemplateDetailFactory;
    
    public PartySecurityRoleTemplate createPartySecurityRoleTemplate(String partySecurityRoleTemplateName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
        var defaultFound = defaultPartySecurityRoleTemplate != null;
        
        if(defaultFound && isDefault) {
            var defaultPartySecurityRoleTemplateDetailValue = getDefaultPartySecurityRoleTemplateDetailValueForUpdate();
            
            defaultPartySecurityRoleTemplateDetailValue.setIsDefault(false);
            updatePartySecurityRoleTemplateFromValue(defaultPartySecurityRoleTemplateDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partySecurityRoleTemplate = partySecurityRoleTemplateFactory.create();
        var partySecurityRoleTemplateDetail = partySecurityRoleTemplateDetailFactory.create(partySecurityRoleTemplate,
                partySecurityRoleTemplateName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        partySecurityRoleTemplate = partySecurityRoleTemplateFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                partySecurityRoleTemplate.getPrimaryKey());
        partySecurityRoleTemplate.setActiveDetail(partySecurityRoleTemplateDetail);
        partySecurityRoleTemplate.setLastDetail(partySecurityRoleTemplateDetail);
        partySecurityRoleTemplate.store();
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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

        var ps = partySecurityRoleTemplateFactory.prepareStatement(query);
        
        return partySecurityRoleTemplateFactory.getEntitiesFromQuery(entityPermission, ps);
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

        var ps = partySecurityRoleTemplateFactory.prepareStatement(query);
        
        return partySecurityRoleTemplateFactory.getEntityFromQuery(entityPermission, ps);
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
        PartySecurityRoleTemplate partySecurityRoleTemplate;
        
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

            var ps = partySecurityRoleTemplateFactory.prepareStatement(query);
            
            ps.setString(1, partySecurityRoleTemplateName);
            
            partySecurityRoleTemplate = partySecurityRoleTemplateFactory.getEntityFromQuery(entityPermission, ps);
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
        return partySecurityRoleTemplateFactory.getEntityFromPK(EntityPermission.READ_ONLY, partySecurityRoleTemplatePK);
    }
    
    public PartySecurityRoleTemplateChoicesBean getPartySecurityRoleTemplateChoices(String defaultPartySecurityRoleTemplateChoice, Language language,
            boolean allowNullChoice) {
        var partySecurityRoleTemplates = getPartySecurityRoleTemplates();
        var size = partySecurityRoleTemplates.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultPartySecurityRoleTemplateChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var partySecurityRoleTemplate : partySecurityRoleTemplates) {
            var partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetail();
            var label = getBestPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language);
            var value = partySecurityRoleTemplateDetail.getPartySecurityRoleTemplateName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultPartySecurityRoleTemplateChoice != null && defaultPartySecurityRoleTemplateChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partySecurityRoleTemplateDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new PartySecurityRoleTemplateChoicesBean(labels, values, defaultValue);
    }
    
    public PartySecurityRoleTemplateTransfer getPartySecurityRoleTemplateTransfer(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplate);
    }
    
    public List<PartySecurityRoleTemplateTransfer> getPartySecurityRoleTemplateTransfers(UserVisit userVisit) {
        var partySecurityRoleTemplates = getPartySecurityRoleTemplates();
        List<PartySecurityRoleTemplateTransfer> partySecurityRoleTemplateTransfers = new ArrayList<>(partySecurityRoleTemplates.size());
        
        partySecurityRoleTemplates.forEach((partySecurityRoleTemplate) ->
                partySecurityRoleTemplateTransfers.add(partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplate))
        );
        
        return partySecurityRoleTemplateTransfers;
    }
    
    private void updatePartySecurityRoleTemplateFromValue(PartySecurityRoleTemplateDetailValue partySecurityRoleTemplateDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(partySecurityRoleTemplateDetailValue.hasBeenModified()) {
            var partySecurityRoleTemplate = partySecurityRoleTemplateFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateDetailValue.getPartySecurityRoleTemplatePK());
            var partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getActiveDetailForUpdate();
            
            partySecurityRoleTemplateDetail.setThruTime(session.getStartTime());
            partySecurityRoleTemplateDetail.store();

            var partySecurityRoleTemplatePK = partySecurityRoleTemplateDetail.getPartySecurityRoleTemplatePK();
            var partySecurityRoleTemplateName = partySecurityRoleTemplateDetailValue.getPartySecurityRoleTemplateName();
            var isDefault = partySecurityRoleTemplateDetailValue.getIsDefault();
            var sortOrder = partySecurityRoleTemplateDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
                var defaultFound = defaultPartySecurityRoleTemplate != null && !defaultPartySecurityRoleTemplate.equals(partySecurityRoleTemplate);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartySecurityRoleTemplateDetailValue = getDefaultPartySecurityRoleTemplateDetailValueForUpdate();
                    
                    defaultPartySecurityRoleTemplateDetailValue.setIsDefault(false);
                    updatePartySecurityRoleTemplateFromValue(defaultPartySecurityRoleTemplateDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            partySecurityRoleTemplateDetail = partySecurityRoleTemplateDetailFactory.create(partySecurityRoleTemplatePK, partySecurityRoleTemplateName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            partySecurityRoleTemplate.setActiveDetail(partySecurityRoleTemplateDetail);
            partySecurityRoleTemplate.setLastDetail(partySecurityRoleTemplateDetail);
            
            sendEvent(partySecurityRoleTemplatePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updatePartySecurityRoleTemplateFromValue(PartySecurityRoleTemplateDetailValue partySecurityRoleTemplateDetailValue, BasePK updatedBy) {
        updatePartySecurityRoleTemplateFromValue(partySecurityRoleTemplateDetailValue, true, updatedBy);
    }
    
    public void deletePartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK deletedBy) {
        deletePartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateRoleByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        PartySecurityRoleTemplateLogic.getInstance().deletePartySecurityRoleTemplateTrainingClassByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);

        var partySecurityRoleTemplateDetail = partySecurityRoleTemplate.getLastDetailForUpdate();
        partySecurityRoleTemplateDetail.setThruTime(session.getStartTime());
        partySecurityRoleTemplate.setActiveDetail(null);
        partySecurityRoleTemplate.store();
        
        // Check for default, and pick one if necessary
        var defaultPartySecurityRoleTemplate = getDefaultPartySecurityRoleTemplate();
        if(defaultPartySecurityRoleTemplate == null) {
            var partySecurityRoleTemplates = getPartySecurityRoleTemplatesForUpdate();
            
            if(!partySecurityRoleTemplates.isEmpty()) {
                var iter = partySecurityRoleTemplates.iterator();
                if(iter.hasNext()) {
                    defaultPartySecurityRoleTemplate = iter.next();
                }
                var partySecurityRoleTemplateDetailValue = Objects.requireNonNull(defaultPartySecurityRoleTemplate).getLastDetailForUpdate().getPartySecurityRoleTemplateDetailValue().clone();
                
                partySecurityRoleTemplateDetailValue.setIsDefault(true);
                updatePartySecurityRoleTemplateFromValue(partySecurityRoleTemplateDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartySecurityRoleTemplateDescriptionFactory partySecurityRoleTemplateDescriptionFactory;
    
    public PartySecurityRoleTemplateDescription createPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, String description, BasePK createdBy) {
        var partySecurityRoleTemplateDescription = partySecurityRoleTemplateDescriptionFactory.create(
                partySecurityRoleTemplate, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateDescription;
    }
    
    private PartySecurityRoleTemplateDescription getPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, EntityPermission entityPermission) {
        PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription;
        
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

            var ps = partySecurityRoleTemplateDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateDescription = partySecurityRoleTemplateDescriptionFactory.getEntityFromQuery(entityPermission, ps);
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
        List<PartySecurityRoleTemplateDescription> partySecurityRoleTemplateDescriptions;
        
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

            var ps = partySecurityRoleTemplateDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateDescriptions = partySecurityRoleTemplateDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
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
        var partySecurityRoleTemplateDescription = getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language);
        
        if(partySecurityRoleTemplateDescription == null && !language.getIsDefault()) {
            partySecurityRoleTemplateDescription = getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, partyControl.getDefaultLanguage());
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
        return partySecurityRoleTemplateDescriptionTransferCache.getPartySecurityRoleTemplateDescriptionTransfer(userVisit, partySecurityRoleTemplateDescription);
    }
    
    public List<PartySecurityRoleTemplateDescriptionTransfer> getPartySecurityRoleTemplateDescriptionTransfersByPartySecurityRoleTemplate(UserVisit userVisit,
            PartySecurityRoleTemplate partySecurityRoleTemplate) {
        var partySecurityRoleTemplateDescriptions = getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        List<PartySecurityRoleTemplateDescriptionTransfer> partySecurityRoleTemplateDescriptionTransfers = new ArrayList<>(partySecurityRoleTemplateDescriptions.size());
        
        partySecurityRoleTemplateDescriptions.forEach((partySecurityRoleTemplateDescription) ->
                partySecurityRoleTemplateDescriptionTransfers.add(partySecurityRoleTemplateDescriptionTransferCache.getPartySecurityRoleTemplateDescriptionTransfer(userVisit, partySecurityRoleTemplateDescription))
        );
        
        return partySecurityRoleTemplateDescriptionTransfers;
    }
    
    public void updatePartySecurityRoleTemplateDescriptionFromValue(PartySecurityRoleTemplateDescriptionValue partySecurityRoleTemplateDescriptionValue, BasePK updatedBy) {
        if(partySecurityRoleTemplateDescriptionValue.hasBeenModified()) {
            var partySecurityRoleTemplateDescription = partySecurityRoleTemplateDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateDescriptionValue.getPrimaryKey());
            
            partySecurityRoleTemplateDescription.setThruTime(session.getStartTime());
            partySecurityRoleTemplateDescription.store();

            var partySecurityRoleTemplate = partySecurityRoleTemplateDescription.getPartySecurityRoleTemplate();
            var language = partySecurityRoleTemplateDescription.getLanguage();
            var description = partySecurityRoleTemplateDescriptionValue.getDescription();
            
            partySecurityRoleTemplateDescription = partySecurityRoleTemplateDescriptionFactory.create(
                    partySecurityRoleTemplate, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartySecurityRoleTemplateDescription(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription, BasePK deletedBy) {
        partySecurityRoleTemplateDescription.setThruTime(session.getStartTime());
        
        sendEvent(partySecurityRoleTemplateDescription.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY, partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK deletedBy) {
        var partySecurityRoleTemplateDescriptions = getPartySecurityRoleTemplateDescriptionsByPartySecurityRoleTemplateForUpdate(partySecurityRoleTemplate);
        
        partySecurityRoleTemplateDescriptions.forEach((partySecurityRoleTemplateDescription) -> 
                deletePartySecurityRoleTemplateDescription(partySecurityRoleTemplateDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartySecurityRoleTemplateRoleFactory partySecurityRoleTemplateRoleFactory;
    
    public PartySecurityRoleTemplateRole createPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate, SecurityRole securityRole,
            BasePK createdBy) {
        var partySecurityRoleTemplateRole = partySecurityRoleTemplateRoleFactory.create(partySecurityRoleTemplate,
                securityRole, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateRole;
    }
    
    private PartySecurityRoleTemplateRole getPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate,
            SecurityRole securityRole, EntityPermission entityPermission) {
        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole;
        
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

            var ps = partySecurityRoleTemplateRoleFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateRole = partySecurityRoleTemplateRoleFactory.getEntityFromQuery(
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
        List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles;
        
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

            var ps = partySecurityRoleTemplateRoleFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateRoles = partySecurityRoleTemplateRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles;
        
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

            var ps = partySecurityRoleTemplateRoleFactory.prepareStatement(query);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateRoles = partySecurityRoleTemplateRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        return partySecurityRoleTemplateRoleTransferCache.getPartySecurityRoleTemplateRoleTransfer(userVisit, partySecurityRoleTemplateRole);
    }
    
    public List<PartySecurityRoleTemplateRoleTransfer> getPartySecurityRoleTemplateRoleTransfers(UserVisit userVisit, Collection<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles) {
        List<PartySecurityRoleTemplateRoleTransfer> partySecurityRoleTemplateRoleTransfers = new ArrayList<>(partySecurityRoleTemplateRoles.size());

        partySecurityRoleTemplateRoles.forEach((partySecurityRoleTemplateRole) ->
                partySecurityRoleTemplateRoleTransfers.add(partySecurityRoleTemplateRoleTransferCache.getPartySecurityRoleTemplateRoleTransfer(userVisit, partySecurityRoleTemplateRole))
        );

        return partySecurityRoleTemplateRoleTransfers;
    }
    
    public List<PartySecurityRoleTemplateRoleTransfer> getPartySecurityRoleTemplateRoleTransfers(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoleTransfers(userVisit, getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate));
    }
    
    public void deletePartySecurityRoleTemplateRole(PartySecurityRoleTemplateRole partySecurityRoleTemplateRole, BasePK deletedBy) {
        partySecurityRoleTemplateRole.setThruTime(session.getStartTime());
        partySecurityRoleTemplateRole.store();
        
        sendEvent(partySecurityRoleTemplateRole.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY, partySecurityRoleTemplateRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartySecurityRoleTemplateTrainingClassFactory partySecurityRoleTemplateTrainingClassFactory;
    
    public PartySecurityRoleTemplateTrainingClass createPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, BasePK createdBy) {
        var partySecurityRoleTemplateTrainingClass = partySecurityRoleTemplateTrainingClassFactory.create(partySecurityRoleTemplate,
                trainingClass, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateTrainingClass.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateTrainingClass;
    }
    
    private PartySecurityRoleTemplateTrainingClass getPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, EntityPermission entityPermission) {
        PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass;
        
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

            var ps = partySecurityRoleTemplateTrainingClassFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, trainingClass.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClass = partySecurityRoleTemplateTrainingClassFactory.getEntityFromQuery(
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
        List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses;
        
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

            var ps = partySecurityRoleTemplateTrainingClassFactory.prepareStatement(query);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClasses = partySecurityRoleTemplateTrainingClassFactory.getEntitiesFromQuery(entityPermission, ps);
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
        List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses;
        
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

            var ps = partySecurityRoleTemplateTrainingClassFactory.prepareStatement(query);
            
            ps.setLong(1, trainingClass.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateTrainingClasses = partySecurityRoleTemplateTrainingClassFactory.getEntitiesFromQuery(entityPermission, ps);
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
        return partySecurityRoleTemplateTrainingClassTransferCache.getPartySecurityRoleTemplateTrainingClassTransfer(userVisit, partySecurityRoleTemplateTrainingClass);
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfers(UserVisit userVisit, Collection<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses) {
        List<PartySecurityRoleTemplateTrainingClassTransfer> partySecurityRoleTemplateTrainingClassTransfers = new ArrayList<>(partySecurityRoleTemplateTrainingClasses.size());

        partySecurityRoleTemplateTrainingClasses.forEach((partySecurityRoleTemplateTrainingClass) ->
                partySecurityRoleTemplateTrainingClassTransfers.add(partySecurityRoleTemplateTrainingClassTransferCache.getPartySecurityRoleTemplateTrainingClassTransfer(userVisit, partySecurityRoleTemplateTrainingClass))
        );

        return partySecurityRoleTemplateTrainingClassTransfers;
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfers(UserVisit userVisit, PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClassTransfers(userVisit, getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate));
    }
    
    public List<PartySecurityRoleTemplateTrainingClassTransfer> getPartySecurityRoleTemplateTrainingClassTransfersByTrainingClass(UserVisit userVisit, TrainingClass trainingClass) {
        return getPartySecurityRoleTemplateTrainingClassTransfers(userVisit, getPartySecurityRoleTemplateTrainingClassesByTrainingClass(trainingClass));
    }
    
    public void deletePartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass, BasePK deletedBy) {
        partySecurityRoleTemplateTrainingClass.setThruTime(session.getStartTime());
        partySecurityRoleTemplateTrainingClass.store();
        
        sendEvent(partySecurityRoleTemplateTrainingClass.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY, partySecurityRoleTemplateTrainingClass.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Role Template Uses
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartySecurityRoleTemplateUseFactory partySecurityRoleTemplateUseFactory;
    
    public PartySecurityRoleTemplateUse createPartySecurityRoleTemplateUse(Party party, PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK createdBy) {
        var partySecurityRoleTemplateUse = partySecurityRoleTemplateUseFactory.create(party, partySecurityRoleTemplate,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplate.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        syncPartySecurityRoles(party, partySecurityRoleTemplate, createdBy);
        
        return partySecurityRoleTemplateUse;
    }
    
    private PartySecurityRoleTemplateUse getPartySecurityRoleTemplateUse(Party party, EntityPermission entityPermission) {
        PartySecurityRoleTemplateUse partySecurityRoleTemplateUse;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_par_partyid = ? AND psrtu_thrutime = ?";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_par_partyid = ? AND psrtu_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partySecurityRoleTemplateUseFactory.prepareStatement(
                    entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateUse = partySecurityRoleTemplateUseFactory.getEntityFromQuery(entityPermission, ps);
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
            var partySecurityRoleTemplateUsePK = partySecurityRoleTemplateUseValue.getPrimaryKey();
            var partySecurityRoleTemplateUse = partySecurityRoleTemplateUseFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partySecurityRoleTemplateUsePK);

            partySecurityRoleTemplateUse.setThruTime(session.getStartTime());
            partySecurityRoleTemplateUse.store();

            var partyPK = partySecurityRoleTemplateUse.getPartyPK();
            var partySecurityRoleTemplatePK = partySecurityRoleTemplateUseValue.getPartySecurityRoleTemplatePK();

            partySecurityRoleTemplateUse = partySecurityRoleTemplateUseFactory.create(partyPK,
                    partySecurityRoleTemplatePK, session.getStartTime(), Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partySecurityRoleTemplateUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);

            syncPartySecurityRoles(partyPK, partySecurityRoleTemplatePK, updatedBy);
        }
    }
    
    private List<PartySecurityRoleTemplateUse> getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate,
            EntityPermission entityPermission) {
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_psrt_partysecurityroletemplateid = ? AND psrtu_thrutime = ?";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroletemplateuses " +
                    "WHERE psrtu_psrt_partysecurityroletemplateid = ? AND psrtu_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partySecurityRoleTemplateUseFactory.prepareStatement(
                    entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, partySecurityRoleTemplate.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoleTemplateUses = partySecurityRoleTemplateUseFactory.getEntitiesFromQuery(entityPermission, ps);
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
        partySecurityRoleTemplateUse.setThruTime(session.getStartTime());
        
        sendEvent(partySecurityRoleTemplateUse.getPartySecurityRoleTemplatePK(), EventTypes.MODIFY, partySecurityRoleTemplateUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartySecurityRoleTemplateUseByParty(Party party, BasePK deletedBy) {
        var partySecurityRoleTemplateUse = getPartySecurityRoleTemplateUseForUpdate(party);
        
        if(partySecurityRoleTemplateUse != null) {
            deletePartySecurityRoleTemplateUse(partySecurityRoleTemplateUse, deletedBy);
        }
    }
    
    private void syncPartySecurityRoles(PartyPK partyPK, PartySecurityRoleTemplatePK partySecurityRoleTemplatePK, BasePK syncedBy) {
        var party = partyControl.getPartyByPK(partyPK);
        var partySecurityRoleTemplate = getPartySecurityRoleTemplateFromPK(partySecurityRoleTemplatePK);
        
        syncPartySecurityRoles(party, partySecurityRoleTemplate, syncedBy);
    }
    
    private void syncPartySecurityRoles(Party party, PartySecurityRoleTemplate partySecurityRoleTemplate, BasePK syncedBy) {
        var partySecurityRoles = getPartySecurityRolesForUpdate(party);
        var partySecurityRoleTemplateRoles = getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate);
        
        List<SecurityRole> currentSecurityRoles = new ArrayList<>(partySecurityRoles.size());
        partySecurityRoles.forEach((partySecurityRole) -> {
            currentSecurityRoles.add(partySecurityRole.getSecurityRole());
        });
        
        List<SecurityRole> targetSecurityRoles = new ArrayList<>(partySecurityRoleTemplateRoles.size());
        partySecurityRoleTemplateRoles.forEach((partySecurityRoleTemplateRole) -> {
            targetSecurityRoles.add(partySecurityRoleTemplateRole.getSecurityRole());
        });
        
        Set<SecurityRole> securityRolesToDelete = new HashSet<>(currentSecurityRoles);
        securityRolesToDelete.removeAll(targetSecurityRoles);
        securityRolesToDelete.stream().map((securityRole) -> getPartySecurityRoleForUpdate(party, securityRole)).filter((partySecurityRole) -> (partySecurityRole != null)).forEach((partySecurityRole) -> {
            deletePartySecurityRole(partySecurityRole, syncedBy);
        });
        
        Set<SecurityRole> securityRolesToAdd = new HashSet<>(targetSecurityRoles);
        securityRolesToAdd.removeAll(currentSecurityRoles);
        securityRolesToAdd.forEach((securityRole) -> {
            createPartySecurityRole(party, securityRole, syncedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Party Security Roles
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartySecurityRoleFactory partySecurityRoleFactory;
    
    public PartySecurityRole createPartySecurityRole(Party party, SecurityRole securityRole, BasePK createdBy) {
        var partySecurityRole = partySecurityRoleFactory.create(party, securityRole,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partySecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRole;
    }
    
    private PartySecurityRole getPartySecurityRole(Party party, SecurityRole securityRole, EntityPermission entityPermission) {
        PartySecurityRole partySecurityRole;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ?";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";
            var ps = partySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partySecurityRole = partySecurityRoleFactory.getEntityFromQuery(entityPermission, ps);
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
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partysecurityroles " +
                "WHERE psrol_par_partyid = ? AND psrol_srol_securityroleid = ? AND psrol_thrutime = ?",
                partyPK, securityRolePK, Session.MAX_TIME) == 1;
    }
    
    private List<PartySecurityRole> getPartySecurityRoles(Party party, EntityPermission entityPermission) {
        List<PartySecurityRole> partySecurityRoles;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                    "WHERE psrol_par_partyid = ? AND psrol_thrutime = ? " +
                    "AND psrol_srol_securityroleid = srol_securityroleid AND srol_lastdetailid = sroldt_securityroledetailid " +
                    "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                    "ORDER BY srgdt_sortorder, srgdt_securityrolegroupname, sroldt_sortorder, sroldt_securityrolename";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_par_partyid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoles = partySecurityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        List<PartySecurityRole> partySecurityRoles;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partysecurityroles, parties, partydetails, partytypes " +
                    "WHERE psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "AND psrol_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                    "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                    "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partysecurityroles " +
                    "WHERE psrol_srol_securityroleid = ? AND psrol_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partySecurityRoles = partySecurityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        partySecurityRole.setThruTime(session.getStartTime());
        partySecurityRole.store();
        
        sendEvent(partySecurityRole.getParty().getPrimaryKey(), EventTypes.MODIFY, partySecurityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartySecurityRoles(List<PartySecurityRole> partySecurityRoles, BasePK deletedBy) {
        partySecurityRoles.forEach((partySecurityRole) -> 
                deletePartySecurityRole(partySecurityRole, deletedBy)
        );
    }
    
    public void deletePartySecurityRolesBySecurityRole(SecurityRole securityRole, BasePK deletedBy) {
        deletePartySecurityRoles(getPartySecurityRolesBySecurityRoleForUpdate(securityRole), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Entity Security Roles
    // --------------------------------------------------------------------------------
    
    @Inject
    protected PartyEntitySecurityRoleFactory partyEntitySecurityRoleFactory;
    
    public PartyEntitySecurityRole createPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole,
            BasePK createdBy) {
        var partyEntitySecurityRole = partyEntitySecurityRoleFactory.create(party, entityInstance,
                securityRole, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyEntitySecurityRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyEntitySecurityRole;
    }
    
    private PartyEntitySecurityRole getPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole,
            EntityPermission entityPermission) {
        PartyEntitySecurityRole partyEntitySecurityRole;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ?";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";
            var ps = partyEntitySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(3, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            partyEntitySecurityRole = partyEntitySecurityRoleFactory.getEntityFromQuery(entityPermission, ps);
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
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM partyentitysecurityroles " +
                "WHERE pensrol_par_partyid = ? AND pensrol_eni_entityinstanceid = ? AND pensrol_srol_securityroleid = ? AND pensrol_thrutime = ?",
                partyPK, entityInstancePK, securityRolePK, Session.MAX_TIME) == 1;
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByParty(Party party, EntityPermission entityPermission) {
        List<PartyEntitySecurityRole> partyEntitySecurityRoles;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
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
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_par_partyid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partyEntitySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        List<PartyEntitySecurityRole> partyEntitySecurityRoles;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles, parties, partydetails, partytypes, securityroles, securityroledetails, securityrolegroups, securityrolegroupdetails " +
                    "WHERE pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "AND pensrol_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                    "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                    "AND pensrol_srol_securityroleid = srol_securityroleid " +
                    "AND srol_lastdetailid = sroldt_securityroledetailid " +
                    "AND sroldt_srg_securityrolegroupid = srg_securityrolegroupid " +
                    "AND srg_lastdetailid = srgdt_securityrolegroupdetailid " +
                    "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, sroldt_sortorder, sroldt_securityrolename, srgdt_sortorder, srgdt_securityrolegroupname";
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_eni_entityinstanceid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partyEntitySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        List<PartyEntitySecurityRole> partyEntitySecurityRoles;
        
        try {
            final var queryReadOnly = "SELECT _ALL_ " +
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
            final var queryReadWrite = "SELECT _ALL_ " +
                    "FROM partyentitysecurityroles " +
                    "WHERE pensrol_srol_securityroleid = ? AND pensrol_thrutime = ? " +
                    "FOR UPDATE";

            var ps = partyEntitySecurityRoleFactory.prepareStatement(entityPermission.equals(EntityPermission.READ_ONLY)? queryReadOnly: queryReadWrite);
            
            ps.setLong(1, securityRole.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyEntitySecurityRoles = partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, ps);
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
        partyEntitySecurityRole.setThruTime(session.getStartTime());
        partyEntitySecurityRole.store();
        
        sendEvent(partyEntitySecurityRole.getParty().getPrimaryKey(), EventTypes.MODIFY, partyEntitySecurityRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyEntitySecurityRoles(List<PartyEntitySecurityRole> partyEntitySecurityRoles, BasePK deletedBy) {
        partyEntitySecurityRoles.forEach((partyEntitySecurityRole) -> 
                deletePartyEntitySecurityRole(partyEntitySecurityRole, deletedBy)
        );
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
