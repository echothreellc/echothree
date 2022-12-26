// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.tag.server;

import com.echothree.control.user.tag.common.TagRemote;
import com.echothree.control.user.tag.common.form.*;
import com.echothree.control.user.tag.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class TagBean
        extends TagFormsImpl
        implements TagRemote, TagLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "TagBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScope(UserVisitPK userVisitPK, CreateTagScopeForm form) {
        return new CreateTagScopeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScope(UserVisitPK userVisitPK, GetTagScopeForm form) {
        return new GetTagScopeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopes(UserVisitPK userVisitPK, GetTagScopesForm form) {
        return new GetTagScopesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopeChoices(UserVisitPK userVisitPK, GetTagScopeChoicesForm form) {
        return new GetTagScopeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTagScope(UserVisitPK userVisitPK, SetDefaultTagScopeForm form) {
        return new SetDefaultTagScopeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTagScope(UserVisitPK userVisitPK, EditTagScopeForm form) {
        return new EditTagScopeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTagScope(UserVisitPK userVisitPK, DeleteTagScopeForm form) {
        return new DeleteTagScopeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeDescription(UserVisitPK userVisitPK, CreateTagScopeDescriptionForm form) {
        return new CreateTagScopeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopeDescription(UserVisitPK userVisitPK, GetTagScopeDescriptionForm form) {
        return new GetTagScopeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopeDescriptions(UserVisitPK userVisitPK, GetTagScopeDescriptionsForm form) {
        return new GetTagScopeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTagScopeDescription(UserVisitPK userVisitPK, EditTagScopeDescriptionForm form) {
        return new EditTagScopeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTagScopeDescription(UserVisitPK userVisitPK, DeleteTagScopeDescriptionForm form) {
        return new DeleteTagScopeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeEntityType(UserVisitPK userVisitPK, CreateTagScopeEntityTypeForm form) {
        return new CreateTagScopeEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopeEntityType(UserVisitPK userVisitPK, GetTagScopeEntityTypeForm form) {
        return new GetTagScopeEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagScopeEntityTypes(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form) {
        return new GetTagScopeEntityTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTagScopeEntityType(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form) {
        return new DeleteTagScopeEntityTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTag(UserVisitPK userVisitPK, CreateTagForm form) {
        return new CreateTagCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTag(UserVisitPK userVisitPK, GetTagForm form) {
        return new GetTagCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTags(UserVisitPK userVisitPK, GetTagsForm form) {
        return new GetTagsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTagChoices(UserVisitPK userVisitPK, GetTagChoicesForm form) {
        return new GetTagChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTag(UserVisitPK userVisitPK, EditTagForm form) {
        return new EditTagCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTag(UserVisitPK userVisitPK, DeleteTagForm form) {
        return new DeleteTagCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTag(UserVisitPK userVisitPK, CreateEntityTagForm form) {
        return new CreateEntityTagCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTag(UserVisitPK userVisitPK, GetEntityTagForm form) {
        return new GetEntityTagCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTags(UserVisitPK userVisitPK, GetEntityTagsForm form) {
        return new GetEntityTagsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityTag(UserVisitPK userVisitPK, DeleteEntityTagForm form) {
        return new DeleteEntityTagCommand(userVisitPK, form).run();
    }
    
}
