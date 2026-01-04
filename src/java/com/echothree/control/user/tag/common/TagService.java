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

package com.echothree.control.user.tag.common;

import com.echothree.control.user.tag.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TagService
        extends TagForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Tag Scopes
    // -------------------------------------------------------------------------
    
    CommandResult createTagScope(UserVisitPK userVisitPK, CreateTagScopeForm form);
    
    CommandResult getTagScope(UserVisitPK userVisitPK, GetTagScopeForm form);
    
    CommandResult getTagScopes(UserVisitPK userVisitPK, GetTagScopesForm form);
    
    CommandResult getTagScopeChoices(UserVisitPK userVisitPK, GetTagScopeChoicesForm form);
    
    CommandResult setDefaultTagScope(UserVisitPK userVisitPK, SetDefaultTagScopeForm form);
    
    CommandResult editTagScope(UserVisitPK userVisitPK, EditTagScopeForm form);
    
    CommandResult deleteTagScope(UserVisitPK userVisitPK, DeleteTagScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTagScopeDescription(UserVisitPK userVisitPK, CreateTagScopeDescriptionForm form);
    
    CommandResult getTagScopeDescription(UserVisitPK userVisitPK, GetTagScopeDescriptionForm form);
    
    CommandResult getTagScopeDescriptions(UserVisitPK userVisitPK, GetTagScopeDescriptionsForm form);
    
    CommandResult editTagScopeDescription(UserVisitPK userVisitPK, EditTagScopeDescriptionForm form);
    
    CommandResult deleteTagScopeDescription(UserVisitPK userVisitPK, DeleteTagScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult createTagScopeEntityType(UserVisitPK userVisitPK, CreateTagScopeEntityTypeForm form);
    
    CommandResult getTagScopeEntityType(UserVisitPK userVisitPK, GetTagScopeEntityTypeForm form);
    
    CommandResult getTagScopeEntityTypes(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form);
    
    CommandResult deleteTagScopeEntityType(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Tags
    // -------------------------------------------------------------------------
    
    CommandResult createTag(UserVisitPK userVisitPK, CreateTagForm form);
    
    CommandResult getTag(UserVisitPK userVisitPK, GetTagForm form);
    
    CommandResult getTags(UserVisitPK userVisitPK, GetTagsForm form);
    
    CommandResult getTagChoices(UserVisitPK userVisitPK, GetTagChoicesForm form);
    
    CommandResult editTag(UserVisitPK userVisitPK, EditTagForm form);
    
    CommandResult deleteTag(UserVisitPK userVisitPK, DeleteTagForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Tags
    // -------------------------------------------------------------------------
    
    CommandResult createEntityTag(UserVisitPK userVisitPK, CreateEntityTagForm form);
    
    CommandResult getEntityTag(UserVisitPK userVisitPK, GetEntityTagForm form);
    
    CommandResult getEntityTags(UserVisitPK userVisitPK, GetEntityTagsForm form);
    
    CommandResult deleteEntityTag(UserVisitPK userVisitPK, DeleteEntityTagForm form);
    
}
