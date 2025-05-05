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
        return new CreateTagScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScope(UserVisitPK userVisitPK, GetTagScopeForm form) {
        return new GetTagScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopes(UserVisitPK userVisitPK, GetTagScopesForm form) {
        return new GetTagScopesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeChoices(UserVisitPK userVisitPK, GetTagScopeChoicesForm form) {
        return new GetTagScopeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTagScope(UserVisitPK userVisitPK, SetDefaultTagScopeForm form) {
        return new SetDefaultTagScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTagScope(UserVisitPK userVisitPK, EditTagScopeForm form) {
        return new EditTagScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScope(UserVisitPK userVisitPK, DeleteTagScopeForm form) {
        return new DeleteTagScopeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeDescription(UserVisitPK userVisitPK, CreateTagScopeDescriptionForm form) {
        return new CreateTagScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeDescription(UserVisitPK userVisitPK, GetTagScopeDescriptionForm form) {
        return new GetTagScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeDescriptions(UserVisitPK userVisitPK, GetTagScopeDescriptionsForm form) {
        return new GetTagScopeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTagScopeDescription(UserVisitPK userVisitPK, EditTagScopeDescriptionForm form) {
        return new EditTagScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScopeDescription(UserVisitPK userVisitPK, DeleteTagScopeDescriptionForm form) {
        return new DeleteTagScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeEntityType(UserVisitPK userVisitPK, CreateTagScopeEntityTypeForm form) {
        return new CreateTagScopeEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeEntityType(UserVisitPK userVisitPK, GetTagScopeEntityTypeForm form) {
        return new GetTagScopeEntityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeEntityTypes(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form) {
        return new GetTagScopeEntityTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScopeEntityType(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form) {
        return new DeleteTagScopeEntityTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTag(UserVisitPK userVisitPK, CreateTagForm form) {
        return new CreateTagCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTag(UserVisitPK userVisitPK, GetTagForm form) {
        return new GetTagCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTags(UserVisitPK userVisitPK, GetTagsForm form) {
        return new GetTagsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagChoices(UserVisitPK userVisitPK, GetTagChoicesForm form) {
        return new GetTagChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTag(UserVisitPK userVisitPK, EditTagForm form) {
        return new EditTagCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTag(UserVisitPK userVisitPK, DeleteTagForm form) {
        return new DeleteTagCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTag(UserVisitPK userVisitPK, CreateEntityTagForm form) {
        return new CreateEntityTagCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTag(UserVisitPK userVisitPK, GetEntityTagForm form) {
        return new GetEntityTagCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTags(UserVisitPK userVisitPK, GetEntityTagsForm form) {
        return new GetEntityTagsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityTag(UserVisitPK userVisitPK, DeleteEntityTagForm form) {
        return new DeleteEntityTagCommand().run(userVisitPK, form);
    }
    
}
