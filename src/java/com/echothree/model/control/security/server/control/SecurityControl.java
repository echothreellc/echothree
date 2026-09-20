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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTY_DETAILS_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_ENTITY_SECURITY_ROLES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_ENTITY_SECURITY_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_ENTITY_SECURITY_ROLES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_ROLES_PARTY_SECURITY_ROLE_TEMPLATE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_ROLES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_TRAINING_CLASSES_PARTY_SECURITY_ROLE_TEMPLATE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_TRAINING_CLASSES_TRAINING_CLASS_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.PARTY_SECURITY_ROLE_TEMPLATE_USES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_GROUPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_GROUP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_PARTY_TYPES_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.security.SecurityForeignKeys.SECURITY_ROLE_PARTY_TYPES_SECURITY_ROLE_FK;
import static com.echothree.model.jooq.server.keys.training.TrainingForeignKeys.TRAINING_CLASSES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.security.PartyEntitySecurityRoles.PartyEntitySecurityRoles;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplateDescriptions.PartySecurityRoleTemplateDescriptions;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplateDetails.PartySecurityRoleTemplateDetails;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplateRoles.PartySecurityRoleTemplateRoles;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplateTrainingClasses.PartySecurityRoleTemplateTrainingClasses;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplateUses.PartySecurityRoleTemplateUses;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoleTemplates.PartySecurityRoleTemplates;
import static com.echothree.model.jooq.server.tables.security.PartySecurityRoles.PartySecurityRoles;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleDescriptions.SecurityRoleDescriptions;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleDetails.SecurityRoleDetails;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleGroupDescriptions.SecurityRoleGroupDescriptions;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleGroupDetails.SecurityRoleGroupDetails;
import static com.echothree.model.jooq.server.tables.security.SecurityRoleGroups.SecurityRoleGroups;
import static com.echothree.model.jooq.server.tables.security.SecurityRolePartyTypes.SecurityRolePartyTypes;
import static com.echothree.model.jooq.server.tables.security.SecurityRoles.SecurityRoles;
import static com.echothree.model.jooq.server.tables.training.TrainingClassDetails.TrainingClassDetails;
import static com.echothree.model.jooq.server.tables.training.TrainingClasses.TrainingClasses;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class SecurityControl
        extends BaseModelControl {
    
    @Inject
    protected PartySecurityRoleTemplateLogic partySecurityRoleTemplateLogic;

    /** Creates a new instance of SecurityControl */
    protected SecurityControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected SecurityRoleGroupDetailFactory securityRoleGroupDetailFactory;

    @Inject
    protected SecurityRoleGroupFactory securityRoleGroupFactory;

    @Inject
    private SecurityRoleGroupTransferCache securityRoleGroupTransferCache;

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
        return session.getDslContext()
                .selectCount()
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                .where(SecurityRoleGroupDetails.PARENT_SECURITY_ROLE_GROUP.eq(parentSecurityRoleGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
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

    public SecurityRoleGroup getSecurityRoleGroupByName(String securityRoleGroupName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleGroups.fields())
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                .where(SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME.eq(securityRoleGroupName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleGroupFactory.getEntityFromQuery(entityPermission, query);
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

    public SecurityRoleGroup getDefaultSecurityRoleGroup(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleGroups.fields())
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                .where(SecurityRoleGroupDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleGroupFactory.getEntityFromQuery(entityPermission, query);
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

    private List<SecurityRoleGroup> getSecurityRoleGroups(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleGroups.fields())
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    SecurityRoleGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroups() {
        return getSecurityRoleGroups(EntityPermission.READ_ONLY);
    }

    public List<SecurityRoleGroup> getSecurityRoleGroupsForUpdate() {
        return getSecurityRoleGroups(EntityPermission.READ_WRITE);
    }

    private List<SecurityRoleGroup> getSecurityRoleGroupsByParentSecurityRoleGroup(SecurityRoleGroup parentSecurityRoleGroup,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleGroups.fields())
                .from(SecurityRoleGroups)
                .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_ACTIVE_DETAIL_FK)
                .where(SecurityRoleGroupDetails.PARENT_SECURITY_ROLE_GROUP.eq(parentSecurityRoleGroup.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    SecurityRoleGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleGroupFactory.getEntitiesFromQuery(entityPermission, query);
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
            
            if(!value.equals(com.echothree.model.control.security.common.SecurityRoleGroups.ROOT.name())) {
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

    @Inject
    private SecurityRoleGroupDescriptionTransferCache securityRoleGroupDescriptionTransferCache;

    public SecurityRoleGroupDescription createSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, String description, BasePK createdBy) {
        var securityRoleGroupDescription = securityRoleGroupDescriptionFactory.create(securityRoleGroup, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(securityRoleGroup.getPrimaryKey(), EventTypes.MODIFY, securityRoleGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return securityRoleGroupDescription;
    }
    
    private SecurityRoleGroupDescription getSecurityRoleGroupDescription(SecurityRoleGroup securityRoleGroup, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleGroupDescriptions.fields())
                .from(SecurityRoleGroupDescriptions)
                .where(SecurityRoleGroupDescriptions.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                        SecurityRoleGroupDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SecurityRoleGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleGroupDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SecurityRoleGroupDescriptions.fields())
                    .from(SecurityRoleGroupDescriptions)
                    .join(Languages).onKey(SECURITY_ROLE_GROUP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SecurityRoleGroupDescriptions.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                            SecurityRoleGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SecurityRoleGroupDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SecurityRoleGroupDescriptions.fields())
                    .from(SecurityRoleGroupDescriptions)
                    .where(SecurityRoleGroupDescriptions.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                            SecurityRoleGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return securityRoleGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
    protected SecurityRoleDetailFactory securityRoleDetailFactory;

    @Inject
    protected SecurityRoleFactory securityRoleFactory;

    @Inject
    private SecurityRoleTransferCache securityRoleTransferCache;

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
        return session.getDslContext()
                .selectCount()
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSecurityRolesBySecurityRoleGroup(SecurityRoleGroup securityRoleGroup) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .where(SecurityRoleDetails.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
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
        var baseQuery = session.getDslContext()
                .select(SecurityRoles.fields())
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .where(SecurityRoleDetails.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME),
                    SecurityRoleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SecurityRole> getSecurityRoles(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoles(securityRoleGroup, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRole> getSecurityRolesForUpdate(SecurityRoleGroup securityRoleGroup) {
        return getSecurityRoles(securityRoleGroup, EntityPermission.READ_WRITE);
    }
    
    public boolean securityRoleExists(SecurityRoleGroup securityRoleGroup, String securityRoleName) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .where(SecurityRoleDetails.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                        SecurityRoleDetails.SECURITY_ROLE_NAME.eq(securityRoleName))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }
    
    public SecurityRole getDefaultSecurityRole(SecurityRoleGroup securityRoleGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoles.fields())
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .where(SecurityRoleDetails.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                        SecurityRoleDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(SecurityRoles.fields())
                .from(SecurityRoles)
                .join(SecurityRoleDetails).onKey(SECURITY_ROLES_ACTIVE_DETAIL_FK)
                .where(SecurityRoleDetails.SECURITY_ROLE_GROUP.eq(securityRoleGroup.getPrimaryKey()),
                        SecurityRoleDetails.SECURITY_ROLE_NAME.eq(securityRoleName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleFactory.getEntityFromQuery(entityPermission, query);
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
        partySecurityRoleTemplateLogic.deletePartySecurityRoleTemplateRolesBySecurityRole(securityRole, deletedBy);
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

    @Inject
    private SecurityRoleDescriptionTransferCache securityRoleDescriptionTransferCache;

    public SecurityRoleDescription createSecurityRoleDescription(SecurityRole securityRole, Language language, String description, BasePK createdBy) {
        var securityRoleDescription = securityRoleDescriptionFactory.create(securityRole, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.MODIFY, securityRoleDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return securityRoleDescription;
    }
    
    private SecurityRoleDescription getSecurityRoleDescription(SecurityRole securityRole, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRoleDescriptions.fields())
                .from(SecurityRoleDescriptions)
                .where(SecurityRoleDescriptions.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        SecurityRoleDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SecurityRoleDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRoleDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SecurityRoleDescriptions.fields())
                    .from(SecurityRoleDescriptions)
                    .join(Languages).onKey(SECURITY_ROLE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SecurityRoleDescriptions.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            SecurityRoleDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SecurityRoleDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SecurityRoleDescriptions.fields())
                    .from(SecurityRoleDescriptions)
                    .where(SecurityRoleDescriptions.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            SecurityRoleDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return securityRoleDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
    //   Security Role Party Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SecurityRolePartyTypeFactory securityRolePartyTypeFactory;

    @Inject
    private SecurityRolePartyTypeTransferCache securityRolePartyTypeTransferCache;

    public SecurityRolePartyType createSecurityRolePartyType(SecurityRole securityRole, PartyType partyType, Selector partySelector, BasePK createdBy) {
        var securityRolePartyType = securityRolePartyTypeFactory.create(securityRole, partyType, partySelector,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(securityRole.getPrimaryKey(), EventTypes.MODIFY, securityRolePartyType.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return securityRolePartyType;
    }

    public long countSecurityRolePartyTypesBySecurityRole(final SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRolePartyTypes)
                .where(SecurityRolePartyTypes.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSecurityRolePartyTypesByPartyType(final PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRolePartyTypes)
                .where(SecurityRolePartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSecurityRolePartyTypesByPartySelector(final Selector partySelector) {
        return session.getDslContext()
                .selectCount()
                .from(SecurityRolePartyTypes)
                .where(SecurityRolePartyTypes.PARTY_SELECTOR.eq(partySelector.getPrimaryKey()),
                        SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SecurityRolePartyType getSecurityRolePartyType(SecurityRole securityRole, PartyType partyType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SecurityRolePartyTypes.fields())
                .from(SecurityRolePartyTypes)
                .where(SecurityRolePartyTypes.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        SecurityRolePartyTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return securityRolePartyTypeFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SecurityRolePartyTypes.fields())
                    .from(SecurityRolePartyTypes)
                    .join(PartyTypes).onKey(SECURITY_ROLE_PARTY_TYPES_PARTY_TYPE_FK)
                    .where(SecurityRolePartyTypes.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    SecurityRolePartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SecurityRolePartyTypes.fields())
                    .from(SecurityRolePartyTypes)
                    .where(SecurityRolePartyTypes.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return securityRolePartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRole(SecurityRole securityRole) {
        return getSecurityRolePartyTypesBySecurityRole(securityRole, EntityPermission.READ_ONLY);
    }
    
    public List<SecurityRolePartyType> getSecurityRolePartyTypesBySecurityRoleForUpdate(SecurityRole securityRole) {
        return getSecurityRolePartyTypesBySecurityRole(securityRole, EntityPermission.READ_WRITE);
    }
    
    private List<SecurityRolePartyType> getSecurityRolePartyTypesByPartySelector(Selector partySelector, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SecurityRolePartyTypes.fields())
                    .from(SecurityRolePartyTypes)
                    .join(PartyTypes).onKey(SECURITY_ROLE_PARTY_TYPES_PARTY_TYPE_FK)
                    .join(SecurityRoles).onKey(SECURITY_ROLE_PARTY_TYPES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_LAST_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_LAST_DETAIL_FK)
                    .where(SecurityRolePartyTypes.PARTY_SELECTOR.eq(partySelector.getPrimaryKey()),
                            SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME, SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    SecurityRolePartyTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SecurityRolePartyTypes.fields())
                    .from(SecurityRolePartyTypes)
                    .where(SecurityRolePartyTypes.PARTY_SELECTOR.eq(partySelector.getPrimaryKey()),
                            SecurityRolePartyTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return securityRolePartyTypeFactory.getEntitiesFromQuery(entityPermission, query);
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
    protected PartySecurityRoleTemplateDetailFactory partySecurityRoleTemplateDetailFactory;

    @Inject
    protected PartySecurityRoleTemplateFactory partySecurityRoleTemplateFactory;

    @Inject
    private PartySecurityRoleTemplateTransferCache partySecurityRoleTemplateTransferCache;

    public PartySecurityRoleTemplate createPartySecurityRoleTemplate(String partySecurityRoleTemplateName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
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

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PartySecurityRoleTemplate */
    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PartySecurityRoleTemplatePK(entityInstance.getEntityUniqueId());

        return partySecurityRoleTemplateFactory.getEntityFromPK(entityPermission, pk);
    }

    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByEntityInstance(EntityInstance entityInstance) {
        return getPartySecurityRoleTemplateByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartySecurityRoleTemplateByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPartySecurityRoleTemplates() {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplates)
                .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<PartySecurityRoleTemplate> getPartySecurityRoleTemplates(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplates.fields())
                .from(PartySecurityRoleTemplates)
                .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartySecurityRoleTemplateDetails.SORT_ORDER, PartySecurityRoleTemplateDetails.PARTY_SECURITY_ROLE_TEMPLATE_NAME),
                    PartySecurityRoleTemplateFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySecurityRoleTemplate> getPartySecurityRoleTemplates() {
        return getPartySecurityRoleTemplates(EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplate> getPartySecurityRoleTemplatesForUpdate() {
        return getPartySecurityRoleTemplates(EntityPermission.READ_WRITE);
    }
    
    private PartySecurityRoleTemplate getDefaultPartySecurityRoleTemplate(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplates.fields())
                .from(PartySecurityRoleTemplates)
                .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_ACTIVE_DETAIL_FK)
                .where(PartySecurityRoleTemplateDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateFactory.getEntityFromQuery(entityPermission, query);
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
    
    public PartySecurityRoleTemplate getPartySecurityRoleTemplateByName(String partySecurityRoleTemplateName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplates.fields())
                .from(PartySecurityRoleTemplates)
                .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_ACTIVE_DETAIL_FK)
                .where(PartySecurityRoleTemplateDetails.PARTY_SECURITY_ROLE_TEMPLATE_NAME.eq(partySecurityRoleTemplateName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateFactory.getEntityFromQuery(entityPermission, query);
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
    
    public List<PartySecurityRoleTemplateTransfer> getPartySecurityRoleTemplateTransfers(UserVisit userVisit, Collection<PartySecurityRoleTemplate> partySecurityRoleTemplates) {
        List<PartySecurityRoleTemplateTransfer> partySecurityRoleTemplateTransfers = new ArrayList<>(partySecurityRoleTemplates.size());

        partySecurityRoleTemplates.forEach((partySecurityRoleTemplate) ->
                partySecurityRoleTemplateTransfers.add(partySecurityRoleTemplateTransferCache.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplate))
        );

        return partySecurityRoleTemplateTransfers;
    }

    public List<PartySecurityRoleTemplateTransfer> getPartySecurityRoleTemplateTransfers(UserVisit userVisit) {
        return getPartySecurityRoleTemplateTransfers(userVisit, getPartySecurityRoleTemplates());
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
        partySecurityRoleTemplateLogic.deletePartySecurityRoleTemplateRoleByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);
        partySecurityRoleTemplateLogic.deletePartySecurityRoleTemplateTrainingClassByPartySecurityRoleTemplate(partySecurityRoleTemplate, deletedBy);

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

    @Inject
    private PartySecurityRoleTemplateDescriptionTransferCache partySecurityRoleTemplateDescriptionTransferCache;

    public PartySecurityRoleTemplateDescription createPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, String description, BasePK createdBy) {
        var partySecurityRoleTemplateDescription = partySecurityRoleTemplateDescriptionFactory.create(
                partySecurityRoleTemplate, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateDescription;
    }
    
    private PartySecurityRoleTemplateDescription getPartySecurityRoleTemplateDescription(PartySecurityRoleTemplate partySecurityRoleTemplate,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateDescriptions.fields())
                .from(PartySecurityRoleTemplateDescriptions)
                .where(PartySecurityRoleTemplateDescriptions.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PartySecurityRoleTemplateDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateDescriptions.fields())
                    .from(PartySecurityRoleTemplateDescriptions)
                    .join(Languages).onKey(PARTY_SECURITY_ROLE_TEMPLATE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PartySecurityRoleTemplateDescriptions.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PartySecurityRoleTemplateDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoleTemplateDescriptions.fields())
                    .from(PartySecurityRoleTemplateDescriptions)
                    .where(PartySecurityRoleTemplateDescriptions.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleTemplateDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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

    @Inject
    private PartySecurityRoleTemplateRoleTransferCache partySecurityRoleTemplateRoleTransferCache;

    public PartySecurityRoleTemplateRole createPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate, SecurityRole securityRole,
            BasePK createdBy) {
        var partySecurityRoleTemplateRole = partySecurityRoleTemplateRoleFactory.create(partySecurityRoleTemplate,
                securityRole, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateRole;
    }

    public long countPartySecurityRoleTemplateRolesByPartySecurityRoleTemplate(final PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateRoles)
                .where(PartySecurityRoleTemplateRoles.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartySecurityRoleTemplateRolesBySecurityRole(final SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateRoles)
                .where(PartySecurityRoleTemplateRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartySecurityRoleTemplateRole getPartySecurityRoleTemplateRole(PartySecurityRoleTemplate partySecurityRoleTemplate,
            SecurityRole securityRole, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateRoles.fields())
                .from(PartySecurityRoleTemplateRoles)
                .where(PartySecurityRoleTemplateRoles.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateRoleFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateRoles.fields())
                    .from(PartySecurityRoleTemplateRoles)
                    .join(SecurityRoles).onKey(PARTY_SECURITY_ROLE_TEMPLATE_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_LAST_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_LAST_DETAIL_FK)
                    .where(PartySecurityRoleTemplateRoles.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME, SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME),
                    PartySecurityRoleTemplateRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoleTemplateRoles.fields())
                    .from(PartySecurityRoleTemplateRoles)
                    .where(PartySecurityRoleTemplateRoles.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleTemplateRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRoles(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateRoles(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateRole> getPartySecurityRoleTemplateRolesBySecurityRole(SecurityRole securityRole,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateRoles.fields())
                .from(PartySecurityRoleTemplateRoles)
                .where(PartySecurityRoleTemplateRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateRoles.fields())
                    .from(PartySecurityRoleTemplateRoles)
                    .join(PartySecurityRoleTemplates).onKey(PARTY_SECURITY_ROLE_TEMPLATE_ROLES_PARTY_SECURITY_ROLE_TEMPLATE_FK)
                    .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_LAST_DETAIL_FK)
                    .where(PartySecurityRoleTemplateRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            PartySecurityRoleTemplateRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartySecurityRoleTemplateDetails.SORT_ORDER, PartySecurityRoleTemplateDetails.PARTY_SECURITY_ROLE_TEMPLATE_NAME),
                    PartySecurityRoleTemplateRoleFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateRoleFactory.getEntitiesFromQuery(entityPermission, query);
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

    @Inject
    private PartySecurityRoleTemplateTrainingClassTransferCache partySecurityRoleTemplateTrainingClassTransferCache;

    public PartySecurityRoleTemplateTrainingClass createPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, BasePK createdBy) {
        var partySecurityRoleTemplateTrainingClass = partySecurityRoleTemplateTrainingClassFactory.create(partySecurityRoleTemplate,
                trainingClass, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(partySecurityRoleTemplate.getPrimaryKey(), EventTypes.MODIFY, partySecurityRoleTemplateTrainingClass.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partySecurityRoleTemplateTrainingClass;
    }

    public long countPartySecurityRoleTemplateTrainingClassesByPartySecurityRoleTemplate(final PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateTrainingClasses)
                .where(PartySecurityRoleTemplateTrainingClasses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartySecurityRoleTemplateTrainingClassesByByTrainingClass(final TrainingClass trainingClass) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateTrainingClasses)
                .where(PartySecurityRoleTemplateTrainingClasses.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartySecurityRoleTemplateTrainingClass getPartySecurityRoleTemplateTrainingClass(PartySecurityRoleTemplate partySecurityRoleTemplate,
            TrainingClass trainingClass, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateTrainingClasses.fields())
                .from(PartySecurityRoleTemplateTrainingClasses)
                .where(PartySecurityRoleTemplateTrainingClasses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateTrainingClasses.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                        PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateTrainingClassFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateTrainingClasses.fields())
                    .from(PartySecurityRoleTemplateTrainingClasses)
                    .join(TrainingClasses).onKey(PARTY_SECURITY_ROLE_TEMPLATE_TRAINING_CLASSES_TRAINING_CLASS_FK)
                    .join(TrainingClassDetails).onKey(TRAINING_CLASSES_LAST_DETAIL_FK)
                    .where(PartySecurityRoleTemplateTrainingClasses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TrainingClassDetails.SORT_ORDER, TrainingClassDetails.TRAINING_CLASS_NAME),
                    PartySecurityRoleTemplateTrainingClassFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoleTemplateTrainingClasses.fields())
                    .from(PartySecurityRoleTemplateTrainingClasses)
                    .where(PartySecurityRoleTemplateTrainingClasses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleTemplateTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClasses(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesForUpdate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRoleTemplateTrainingClass> getPartySecurityRoleTemplateTrainingClassesByTrainingClass(TrainingClass trainingClass,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateTrainingClasses.fields())
                    .from(PartySecurityRoleTemplateTrainingClasses)
                    .join(PartySecurityRoleTemplates).onKey(PARTY_SECURITY_ROLE_TEMPLATE_TRAINING_CLASSES_PARTY_SECURITY_ROLE_TEMPLATE_FK)
                    .join(PartySecurityRoleTemplateDetails).onKey(PARTY_SECURITY_ROLE_TEMPLATES_LAST_DETAIL_FK)
                    .where(PartySecurityRoleTemplateTrainingClasses.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                            PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartySecurityRoleTemplateDetails.SORT_ORDER, PartySecurityRoleTemplateDetails.PARTY_SECURITY_ROLE_TEMPLATE_NAME),
                    PartySecurityRoleTemplateTrainingClassFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoleTemplateTrainingClasses.fields())
                    .from(PartySecurityRoleTemplateTrainingClasses)
                    .where(PartySecurityRoleTemplateTrainingClasses.TRAINING_CLASS.eq(trainingClass.getPrimaryKey()),
                            PartySecurityRoleTemplateTrainingClasses.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleTemplateTrainingClassFactory.getEntitiesFromQuery(entityPermission, query);
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

    public long countPartySecurityRoleTemplateUsesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateUses)
                .where(PartySecurityRoleTemplateUses.PARTY.eq(party.getPrimaryKey()),
                        PartySecurityRoleTemplateUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(final PartySecurityRoleTemplate partySecurityRoleTemplate) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoleTemplateUses)
                .where(PartySecurityRoleTemplateUses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateUses.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartySecurityRoleTemplateUse getPartySecurityRoleTemplateUse(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateUses.fields())
                .from(PartySecurityRoleTemplateUses)
                .where(PartySecurityRoleTemplateUses.PARTY.eq(party.getPrimaryKey()),
                        PartySecurityRoleTemplateUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateUseFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoleTemplateUses.fields())
                .from(PartySecurityRoleTemplateUses)
                .where(PartySecurityRoleTemplateUses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                        PartySecurityRoleTemplateUses.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoleTemplateUses.fields())
                    .from(PartySecurityRoleTemplateUses)
                    .join(Parties).onKey(PARTY_SECURITY_ROLE_TEMPLATE_USES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartySecurityRoleTemplateUses.PARTY_SECURITY_ROLE_TEMPLATE.eq(partySecurityRoleTemplate.getPrimaryKey()),
                            PartySecurityRoleTemplateUses.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartySecurityRoleTemplateUseFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleTemplateUseFactory.getEntitiesFromQuery(entityPermission, query);
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

    public long countPartySecurityRolesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoles)
                .where(PartySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                        PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartySecurityRolesBySecurityRole(final SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoles)
                .where(PartySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartySecurityRole getPartySecurityRole(Party party, SecurityRole securityRole, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartySecurityRoles.fields())
                .from(PartySecurityRoles)
                .where(PartySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                        PartySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partySecurityRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartySecurityRole getPartySecurityRole(Party party, SecurityRole securityRole) {
        return getPartySecurityRole(party, securityRole, EntityPermission.READ_ONLY);
    }
    
    public PartySecurityRole getPartySecurityRoleForUpdate(Party party, SecurityRole securityRole) {
        return getPartySecurityRole(party, securityRole, EntityPermission.READ_WRITE);
    }
    
    public boolean partySecurityRoleExists(PartyPK partyPK, SecurityRolePK securityRolePK) {
        return session.getDslContext()
                .selectCount()
                .from(PartySecurityRoles)
                .where(PartySecurityRoles.PARTY.eq(partyPK),
                        PartySecurityRoles.SECURITY_ROLE.eq(securityRolePK),
                        PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }
    
    private List<PartySecurityRole> getPartySecurityRoles(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoles.fields())
                    .from(PartySecurityRoles)
                    .join(SecurityRoles).onKey(PARTY_SECURITY_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_LAST_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_LAST_DETAIL_FK)
                    .where(PartySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                            PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME, SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME),
                    PartySecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoles.fields())
                    .from(PartySecurityRoles)
                    .where(PartySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                            PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartySecurityRole> getPartySecurityRoles(Party party) {
        return getPartySecurityRoles(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartySecurityRole> getPartySecurityRolesForUpdate(Party party) {
        return getPartySecurityRoles(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartySecurityRole> getPartySecurityRolesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartySecurityRoles.fields())
                    .from(PartySecurityRoles)
                    .join(Parties).onKey(PARTY_SECURITY_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .where(PartySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME),
                    PartySecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartySecurityRoles.fields())
                    .from(PartySecurityRoles)
                    .where(PartySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            PartySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partySecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
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

    public long countPartyEntitySecurityRolesByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntitySecurityRoles)
                .where(PartyEntitySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                        PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyEntitySecurityRolesByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntitySecurityRoles)
                .where(PartyEntitySecurityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyEntitySecurityRolesBySecurityRole(final SecurityRole securityRole) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntitySecurityRoles)
                .where(PartyEntitySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyEntitySecurityRole getPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyEntitySecurityRoles.fields())
                .from(PartyEntitySecurityRoles)
                .where(PartyEntitySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                        PartyEntitySecurityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        PartyEntitySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                        PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyEntitySecurityRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public PartyEntitySecurityRole getPartyEntitySecurityRole(Party party, EntityInstance entityInstance, SecurityRole securityRole) {
        return getPartyEntitySecurityRole(party, entityInstance, securityRole, EntityPermission.READ_ONLY);
    }
    
    public PartyEntitySecurityRole getPartyEntitySecurityRoleForUpdate(Party party, EntityInstance entityInstance, SecurityRole securityRole) {
        return getPartyEntitySecurityRole(party, entityInstance, securityRole, EntityPermission.READ_WRITE);
    }
    
    public boolean partyEntitySecurityRoleExists(PartyPK partyPK, EntityInstance entityInstancePK, SecurityRolePK securityRolePK) {
        return session.getDslContext()
                .selectCount()
                .from(PartyEntitySecurityRoles)
                .where(PartyEntitySecurityRoles.PARTY.eq(partyPK),
                        PartyEntitySecurityRoles.ENTITY_INSTANCE.eq(entityInstancePK.getPrimaryKey()),
                        PartyEntitySecurityRoles.SECURITY_ROLE.eq(securityRolePK),
                        PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .join(EntityInstances).onKey(PARTY_ENTITY_SECURITY_ROLES_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .join(SecurityRoles).onKey(PARTY_ENTITY_SECURITY_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_LAST_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_LAST_DETAIL_FK)
                    .where(PartyEntitySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME, SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    PartyEntitySecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .where(PartyEntitySecurityRoles.PARTY.eq(party.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByParty(Party party) {
        return getPartyEntitySecurityRolesByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByPartyForUpdate(Party party) {
        return getPartyEntitySecurityRolesByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .join(Parties).onKey(PARTY_ENTITY_SECURITY_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .join(SecurityRoles).onKey(PARTY_ENTITY_SECURITY_ROLES_SECURITY_ROLE_FK)
                    .join(SecurityRoleDetails).onKey(SECURITY_ROLES_LAST_DETAIL_FK)
                    .join(SecurityRoleGroups).onKey(SECURITY_ROLE_DETAILS_SECURITY_ROLE_GROUP_FK)
                    .join(SecurityRoleGroupDetails).onKey(SECURITY_ROLE_GROUPS_LAST_DETAIL_FK)
                    .where(PartyEntitySecurityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, SecurityRoleDetails.SORT_ORDER, SecurityRoleDetails.SECURITY_ROLE_NAME, SecurityRoleGroupDetails.SORT_ORDER, SecurityRoleGroupDetails.SECURITY_ROLE_GROUP_NAME),
                    PartyEntitySecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .where(PartyEntitySecurityRoles.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstance(EntityInstance entityInstance) {
        return getPartyEntitySecurityRolesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<PartyEntitySecurityRole> getPartyEntitySecurityRolesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPartyEntitySecurityRolesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<PartyEntitySecurityRole> getPartyEntitySecurityRolesBySecurityRole(SecurityRole securityRole, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .join(Parties).onKey(PARTY_ENTITY_SECURITY_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .join(PartyTypes).onKey(PARTY_DETAILS_PARTY_TYPE_FK)
                    .join(EntityInstances).onKey(PARTY_ENTITY_SECURITY_ROLES_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(PartyEntitySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                    PartyEntitySecurityRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyEntitySecurityRoles.fields())
                    .from(PartyEntitySecurityRoles)
                    .where(PartyEntitySecurityRoles.SECURITY_ROLE.eq(securityRole.getPrimaryKey()),
                            PartyEntitySecurityRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyEntitySecurityRoleFactory.getEntitiesFromQuery(entityPermission, query);
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
