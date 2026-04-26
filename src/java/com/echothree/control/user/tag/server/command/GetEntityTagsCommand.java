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

package com.echothree.control.user.tag.server.command;

import com.echothree.control.user.tag.common.form.GetEntityTagsForm;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.logic.TagLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.factory.EntityTagFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEntityTagsCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityTag, GetEntityTagsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityTag.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TagName", FieldType.TAG, false, null, null)
        );
    }
    
    /** Creates a new instance of GetEntityTagsCommand */
    public GetEntityTagsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    TagControl tagControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    TagLogic tagLogic;

    private EntityInstance taggedEntityInstance;
    private Tag tag;

    @Override
    protected void handleForm() {
        var tagScopeName = form.getTagScopeName();
        var tagName = form.getTagName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(form);
        var traditionalParameterCount = ParameterUtils.getInstance().countNonNullParameters(tagScopeName, tagName);

        if(possibleEntitySpecs == 1 && traditionalParameterCount == 0) {
            taggedEntityInstance = entityInstanceLogic.getEntityInstance(this, form);
        } else if(possibleEntitySpecs == 0 && traditionalParameterCount == 2) {
            tag = tagLogic.getTagByName(this, tagScopeName, tagName);
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(taggedEntityInstance != null) {
                total = tagControl.countEntityTagsByTaggedEntityInstance(taggedEntityInstance);
            } else if(tag != null) {
                total = tagControl.countEntityTagsByTag(tag);
            }
        }

        return total;
    }

    @Override
    protected Collection<EntityTag> getEntities() {
        Collection<EntityTag> entityTags = null;

        if(!hasExecutionErrors()) {
            if(taggedEntityInstance != null) {
                entityTags = tagControl.getEntityTagsByTaggedEntityInstance(taggedEntityInstance);
            } else if(tag != null) {
                entityTags = tagControl.getEntityTagsByTag(tag);
            }
        }

        return entityTags;
    }

    @Override
    protected BaseResult getResult(Collection<EntityTag> entities) {
        var result = TagResultFactory.getGetEntityTagsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(taggedEntityInstance != null) {
                result.setTaggedEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, taggedEntityInstance, false, false, false, false));
            } else if(tag != null) {
                result.setTag(tagControl.getTagTransfer(userVisit, tag));
            }

            if(session.hasLimit(EntityTagFactory.class)) {
                result.setEntityTagCount(getTotalEntities());
            }

            result.setEntityTags(tagControl.getEntityTagTransfers(userVisit, entities));
        }

        return result;
    }

}
