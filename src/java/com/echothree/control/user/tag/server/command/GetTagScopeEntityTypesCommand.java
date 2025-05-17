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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.form.GetTagScopeEntityTypesForm;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.logic.TagScopeLogic;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetTagScopeEntityTypesCommand
        extends BaseMultipleEntitiesCommand<TagScopeEntityType, GetTagScopeEntityTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TagScopeEntityType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTagScopeEntityTypesCommand */
    public GetTagScopeEntityTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    TagScope tagScope;
    EntityType entityType;

    @Override
    protected Collection<TagScopeEntityType> getEntities() {
        var tagScopeName = form.getTagScopeName();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var parameterCount = (tagScopeName == null ? 0 : 1) + (componentVendorName == null && entityTypeName == null ? 0 : 1);
        Collection<TagScopeEntityType> entities = null;

        if(parameterCount == 1) {
            var tagControl = Session.getModelController(TagControl.class);

            if(tagScopeName != null) {
                tagScope = TagScopeLogic.getInstance().getTagScopeByName(this, tagScopeName);

                if(!hasExecutionErrors()) {
                    entities = tagControl.getTagScopeEntityTypesByTagScope(tagScope);
                }
            } else {
                entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);

                if(!hasExecutionErrors()) {
                    entities = tagControl.getTagScopeEntityTypesByEntityType(entityType);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<TagScopeEntityType> entities) {
        var result = TagResultFactory.getGetTagScopeEntityTypesResult();

        if(entities != null) {
            var tagControl = Session.getModelController(TagControl.class);
            var userVisit = getUserVisit();

            if(tagScope != null) {
                result.setTagScope(tagControl.getTagScopeTransfer(userVisit, tagScope));
            } else {
                result.setEntityType(getEntityTypeControl().getEntityTypeTransfer(userVisit, entityType));
            }

            result.setTagScopeEntityTypes(tagControl.getTagScopeEntityTypeTransfers(userVisit, entities));
        }

        return result;
    }

}
