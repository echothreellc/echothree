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
import com.echothree.control.user.tag.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface TagService
        extends TagForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Tag Scopes
    // -------------------------------------------------------------------------
    
    CommandResult<CreateTagScopeResult> createTagScope(UserVisitPK userVisitPK, CreateTagScopeForm form);
    
    CommandResult<GetTagScopeResult> getTagScope(UserVisitPK userVisitPK, GetTagScopeForm form);
    
    CommandResult<GetTagScopesResult> getTagScopes(UserVisitPK userVisitPK, GetTagScopesForm form);
    
    CommandResult<GetTagScopeChoicesResult> getTagScopeChoices(UserVisitPK userVisitPK, GetTagScopeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultTagScope(UserVisitPK userVisitPK, SetDefaultTagScopeForm form);
    
    CommandResult<EditTagScopeResult> editTagScope(UserVisitPK userVisitPK, EditTagScopeForm form);
    
    CommandResult<VoidResult> deleteTagScope(UserVisitPK userVisitPK, DeleteTagScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Tag Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTagScopeDescription(UserVisitPK userVisitPK, CreateTagScopeDescriptionForm form);
    
    CommandResult<GetTagScopeDescriptionResult> getTagScopeDescription(UserVisitPK userVisitPK, GetTagScopeDescriptionForm form);
    
    CommandResult<GetTagScopeDescriptionsResult> getTagScopeDescriptions(UserVisitPK userVisitPK, GetTagScopeDescriptionsForm form);
    
    CommandResult<EditTagScopeDescriptionResult> editTagScopeDescription(UserVisitPK userVisitPK, EditTagScopeDescriptionForm form);
    
    CommandResult<VoidResult> deleteTagScopeDescription(UserVisitPK userVisitPK, DeleteTagScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Tag Scope Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTagScopeEntityType(UserVisitPK userVisitPK, CreateTagScopeEntityTypeForm form);
    
    CommandResult<GetTagScopeEntityTypeResult> getTagScopeEntityType(UserVisitPK userVisitPK, GetTagScopeEntityTypeForm form);
    
    CommandResult<GetTagScopeEntityTypesResult> getTagScopeEntityTypes(UserVisitPK userVisitPK, GetTagScopeEntityTypesForm form);
    
    CommandResult<VoidResult> deleteTagScopeEntityType(UserVisitPK userVisitPK, DeleteTagScopeEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Tags
    // -------------------------------------------------------------------------
    
    CommandResult<CreateTagResult> createTag(UserVisitPK userVisitPK, CreateTagForm form);
    
    CommandResult<GetTagResult> getTag(UserVisitPK userVisitPK, GetTagForm form);
    
    CommandResult<GetTagsResult> getTags(UserVisitPK userVisitPK, GetTagsForm form);
    
    CommandResult<GetTagChoicesResult> getTagChoices(UserVisitPK userVisitPK, GetTagChoicesForm form);
    
    CommandResult<EditTagResult> editTag(UserVisitPK userVisitPK, EditTagForm form);
    
    CommandResult<VoidResult> deleteTag(UserVisitPK userVisitPK, DeleteTagForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Tags
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createEntityTag(UserVisitPK userVisitPK, CreateEntityTagForm form);
    
    CommandResult<GetEntityTagResult> getEntityTag(UserVisitPK userVisitPK, GetEntityTagForm form);
    
    CommandResult<GetEntityTagsResult> getEntityTags(UserVisitPK userVisitPK, GetEntityTagsForm form);
    
    CommandResult<VoidResult> deleteEntityTag(UserVisitPK userVisitPK, DeleteEntityTagForm form);
    
}
