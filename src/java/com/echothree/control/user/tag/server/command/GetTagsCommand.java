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

import com.echothree.control.user.tag.common.form.GetTagsForm;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.logic.TagScopeLogic;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.factory.TagFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetTagsCommand
        extends BasePaginatedMultipleEntitiesCommand<Tag, GetTagsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Tag.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetTagsCommand */
    public GetTagsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    TagScope tagScope;

    @Override
    protected void handleForm() {
        var tagScopeName = form.getTagScopeName();

        tagScope = TagScopeLogic.getInstance().getTagScopeByName(this, tagScopeName);
    }

    @Override
    protected Long getTotalEntities() {
        var tagControl = Session.getModelController(TagControl.class);

        return hasExecutionErrors() ? null :
                tagControl.countTagsByTagScope(tagScope);
    }

    @Override
    protected Collection<Tag> getEntities() {
        Collection<Tag> entities = null;

        if(!hasExecutionErrors()) {
            var tagControl = Session.getModelController(TagControl.class);

            entities = tagControl.getTags(tagScope);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<Tag> entities) {
        var result = TagResultFactory.getGetTagsResult();

        if(entities != null) {
            var tagControl = Session.getModelController(TagControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(TagFactory.class)) {
                result.setTagCount(getTotalEntities());
            }

            result.setTagScope(tagControl.getTagScopeTransfer(userVisit, tagScope));
            result.setTags(tagControl.getTagTransfers(userVisit, entities));
        }

        return result;
    }

}
