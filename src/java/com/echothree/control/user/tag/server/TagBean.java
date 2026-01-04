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

package com.echothree.control.user.tag.server;

import com.echothree.control.user.tag.common.TagRemote;
import com.echothree.control.user.tag.common.form.*;
import com.echothree.control.user.tag.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateTagScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScope(UserVisitPK userVisitPK, GetTagScopeForm form) {
        return CDI.current().select(GetTagScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopes(UserVisitPK userVisitPK, GetTagScopesForm form) {
        return CDI.current().select(GetTagScopesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeChoices(UserVisitPK userVisitPK, GetTagScopeChoicesForm form) {
        return CDI.current().select(GetTagScopeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTagScope(UserVisitPK userVisitPK, SetDefaultTagScopeForm form) {
        return CDI.current().select(SetDefaultTagScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTagScope(UserVisitPK userVisitPK, EditTagScopeForm form) {
        return CDI.current().select(EditTagScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScope(UserVisitPK userVisitPK, DeleteTagScopeForm form) {
        return CDI.current().select(DeleteTagScopeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeDescription(UserVisitPK userVisitPK, CreateTagScopeDescriptionForm form) {
        return CDI.current().select(CreateTagScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeDescription(UserVisitPK userVisitPK, GetTagScopeDescriptionForm form) {
        return CDI.current().select(GetTagScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeDescriptions(UserVisitPK userVisitPK, GetTagScopeDescriptionsForm form) {
        return CDI.current().select(GetTagScopeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTagScopeDescription(UserVisitPK userVisitPK, EditTagScopeDescriptionForm form) {
        return CDI.current().select(EditTagScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScopeDescription(UserVisitPK userVisitPK, DeleteTagScopeDescriptionForm form) {
        return CDI.current().select(DeleteTagScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTagScopeEntityType(UserVisitPK userVisitPK, CreateTagScopeEntityTypeForm form) {
        return CDI.current().select(CreateTagScopeEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeEntityType(UserVisitPK userVisitPK, GetTagScopeEntityTypeForm form) {
        return CDI.current().select(GetTagScopeEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagScopeEntityTypes(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form) {
        return CDI.current().select(GetTagScopeEntityTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTagScopeEntityType(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form) {
        return CDI.current().select(DeleteTagScopeEntityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTag(UserVisitPK userVisitPK, CreateTagForm form) {
        return CDI.current().select(CreateTagCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTag(UserVisitPK userVisitPK, GetTagForm form) {
        return CDI.current().select(GetTagCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTags(UserVisitPK userVisitPK, GetTagsForm form) {
        return CDI.current().select(GetTagsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTagChoices(UserVisitPK userVisitPK, GetTagChoicesForm form) {
        return CDI.current().select(GetTagChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTag(UserVisitPK userVisitPK, EditTagForm form) {
        return CDI.current().select(EditTagCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTag(UserVisitPK userVisitPK, DeleteTagForm form) {
        return CDI.current().select(DeleteTagCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Entity Tags
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTag(UserVisitPK userVisitPK, CreateEntityTagForm form) {
        return CDI.current().select(CreateEntityTagCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTag(UserVisitPK userVisitPK, GetEntityTagForm form) {
        return CDI.current().select(GetEntityTagCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEntityTags(UserVisitPK userVisitPK, GetEntityTagsForm form) {
        return CDI.current().select(GetEntityTagsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEntityTag(UserVisitPK userVisitPK, DeleteEntityTagForm form) {
        return CDI.current().select(DeleteEntityTagCommand.class).get().run(userVisitPK, form);
    }
    
}
