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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.server.command.GetEntityAliasTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupCommand;
import com.echothree.control.user.tag.common.form.TagFormFactory;
import com.echothree.control.user.tag.server.command.GetTagScopeCommand;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityAliasTypeObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeObject;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import static com.echothree.model.control.graphql.server.util.BaseGraphQl.getUserVisitPK;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.graphql.TagScopeObject;
import com.echothree.model.control.tag.server.graphql.TagSecurityUtils;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.core.common.EntityAliasTypeConstants;
import com.echothree.model.data.core.common.EntityAttributeGroupConstants;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.tag.common.TagScopeConstants;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import javax.enterprise.inject.spi.CDI;

public abstract class BaseEntityInstanceObject
        extends BaseObject {

    protected BaseEntityInstanceObject(BasePK basePrimaryKey) {
        init(basePrimaryKey, null);
    }

    protected BaseEntityInstanceObject(EntityInstance entityInstance) {
        init(null, entityInstance);
    }

    private void init(final BasePK basePrimaryKey, final EntityInstance entityInstance) {
        this.basePrimaryKey = basePrimaryKey == null ? PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance) : basePrimaryKey;
        this.entityInstance = entityInstance;
    }

    protected BasePK basePrimaryKey; // Always Present
    private EntityInstance entityInstance; // Optional, use getEntityInstanceByBasePK()
    
    protected EntityInstance getEntityInstanceByBasePK() {
        if(entityInstance == null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

            entityInstance = entityInstanceControl.getEntityInstanceByBasePK(basePrimaryKey);
        }
        
        return entityInstance;
    }

    protected WorkflowEntityStatusObject getWorkflowEntityStatusObject(final DataFetchingEnvironment env,
            final String workflowName) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, workflowName);
    }

    @GraphQLField
    @GraphQLDescription("id")
    @GraphQLNonNull
    @GraphQLID
    public String getId() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

        entityInstance = entityInstanceControl.ensureUuidForEntityInstance(getEntityInstanceByBasePK(), false);

        return entityInstance.getUuid();
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        // Allow user to see the EntityInstanceObject regardless of permissions for the GetEntityInstance UC.
        return new EntityInstanceObject(getEntityInstanceByBasePK());
    }

    @GraphQLField
    @GraphQLDescription("entity alias types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityAliasTypeObject> getEntityAliasTypes(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityAliasTypesAccess(env)) {
            var entityAliasControl = Session.getModelController(EntityAliasControl.class);
            var totalCount = entityAliasControl.countEntityAliasTypesByEntityType(getEntityInstanceByBasePK().getEntityType());

            try(var objectLimiter = new ObjectLimiter(env, EntityAliasTypeConstants.COMPONENT_VENDOR_NAME, EntityAliasTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = entityAliasControl.getEntityAliasTypesByEntityType(getEntityInstanceByBasePK().getEntityType());
                var entityAliasTypes = new ArrayList<EntityAliasTypeObject>(entities.size());

                for(var entity : entities) {
                    var entityAliasTypeObject = new EntityAliasTypeObject(entity, entityInstance);

                    entityAliasTypes.add(entityAliasTypeObject);
                }

                return new CountedObjects<>(objectLimiter, entityAliasTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("entity alias type")
    public EntityAliasTypeObject getEntityAliasType(final DataFetchingEnvironment env,
            @GraphQLName("entityAliasTypeName") final String entityAliasTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var entityInstance = getEntityInstanceByBasePK();
        var commandForm = CoreFormFactory.getGetEntityAliasTypeForm();

        if(entityAliasTypeName != null) {
            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

            commandForm.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            commandForm.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            commandForm.setEntityAliasTypeName(entityAliasTypeName);
        }

        commandForm.setUuid(id);

        var entityAliasType = CDI.current().select(GetEntityAliasTypeCommand.class).get().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        if(entityInstance != null && entityAliasType != null) {
            return new EntityAliasTypeObject(entityAliasType, entityInstance);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("entity attribute groups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityAttributeGroupObject> getEntityAttributeGroups(final DataFetchingEnvironment env) {
        var entityInstance = getEntityInstanceByBasePK();

        if(entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityType = entityInstance.getEntityType();
            var totalCount = coreControl.countEntityAttributeGroupsByEntityType(entityType);

            // Allow user to see all EntityAttributeGroups that have Entity Attributes in them
            // for the current object, regardless of permissions for the GetEntityAttributeGroups UC.
            try(var objectLimiter = new ObjectLimiter(env, EntityAttributeGroupConstants.COMPONENT_VENDOR_NAME, EntityAttributeGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = coreControl.getEntityAttributeGroupsByEntityType(entityType);
                var entityAttributeGroups = new ArrayList<EntityAttributeGroupObject>(entities.size());

                entities.forEach((entity) -> entityAttributeGroups.add(new EntityAttributeGroupObject(entity, entityInstance)));

                return new CountedObjects<>(objectLimiter, entityAttributeGroups);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("entity attribute group")
    public EntityAttributeGroupObject getEntityAttributeGroup(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeGroupName") final String entityAttributeGroupName,
            @GraphQLName("id") @GraphQLID final String id) {
        var entityInstance = getEntityInstanceByBasePK();
        var commandForm = CoreFormFactory.getGetEntityAttributeGroupForm();

        commandForm.setEntityAttributeGroupName(entityAttributeGroupName);
        commandForm.setUuid(id);

        var entityAttributeGroup = CDI.current().select(GetEntityAttributeGroupCommand.class).get().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        if(entityInstance != null && entityAttributeGroup != null) {
            return new EntityAttributeGroupObject(entityAttributeGroup, entityInstance);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeName") final String entityAttributeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var entityInstance = getEntityInstanceByBasePK();
        var commandForm = CoreFormFactory.getGetEntityAttributeForm();

        if(entityAttributeName != null) {
            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

            commandForm.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            commandForm.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            commandForm.setEntityAttributeName(entityAttributeName);
        }

        commandForm.setUuid(id);

        var entityAttribute = CDI.current().select(GetEntityAttributeCommand.class).get().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        if(entityInstance != null && entityAttribute != null) {
            return new EntityAttributeObject(entityAttribute, entityInstance);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("tag scopes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TagScopeObject> getTagScopes(final DataFetchingEnvironment env) {
        if(TagSecurityUtils.getHasTagScopesAccess(env)) {
            var tagControl = Session.getModelController(TagControl.class);
            var totalCount = tagControl.countTagScopesByEntityType(getEntityInstanceByBasePK().getEntityType());

            try(var objectLimiter = new ObjectLimiter(env, TagScopeConstants.COMPONENT_VENDOR_NAME, TagScopeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = tagControl.getTagScopesByEntityType(getEntityInstanceByBasePK().getEntityType());
                var tagScopes = new ArrayList<TagScopeObject>(entities.size());

                for(var entity : entities) {
                    var tagScopeObject = new TagScopeObject(entity, entityInstance);

                    tagScopes.add(tagScopeObject);
                }

                return new CountedObjects<>(objectLimiter, tagScopes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("tag scope")
    public TagScopeObject getTagScope(final DataFetchingEnvironment env,
            @GraphQLName("tagScopeName") final String tagScopeName,
            @GraphQLName("id") @GraphQLID final String id) {
        var entityInstance = getEntityInstanceByBasePK();
        var commandForm = TagFormFactory.getGetTagScopeForm();

        commandForm.setTagScopeName(tagScopeName);
        commandForm.setUuid(id);

        var tagScope = CDI.current().select(GetTagScopeCommand.class).get().getEntityForGraphQl(getUserVisitPK(env), commandForm);
        if(entityInstance != null && tagScope != null) {
            return new TagScopeObject(tagScope, entityInstance);
        } else {
            return null;
        }
    }

}
