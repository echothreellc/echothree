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

package com.echothree.control.user.selector.common;

import com.echothree.control.user.selector.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SelectorService
        extends SelectorForms {
    
    // --------------------------------------------------------------------------------
    //   Testing
    // --------------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Selector Kinds
    // --------------------------------------------------------------------------------

    CommandResult createSelectorKind(UserVisitPK userVisitPK, CreateSelectorKindForm form);

    CommandResult getSelectorKinds(UserVisitPK userVisitPK, GetSelectorKindsForm form);

    CommandResult getSelectorKind(UserVisitPK userVisitPK, GetSelectorKindForm form);

    CommandResult getSelectorKindChoices(UserVisitPK userVisitPK, GetSelectorKindChoicesForm form);

    CommandResult setDefaultSelectorKind(UserVisitPK userVisitPK, SetDefaultSelectorKindForm form);

    CommandResult editSelectorKind(UserVisitPK userVisitPK, EditSelectorKindForm form);

    CommandResult deleteSelectorKind(UserVisitPK userVisitPK, DeleteSelectorKindForm form);

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createSelectorKindDescription(UserVisitPK userVisitPK, CreateSelectorKindDescriptionForm form);

    CommandResult getSelectorKindDescriptions(UserVisitPK userVisitPK, GetSelectorKindDescriptionsForm form);

    CommandResult getSelectorKindDescription(UserVisitPK userVisitPK, GetSelectorKindDescriptionForm form);

    CommandResult editSelectorKindDescription(UserVisitPK userVisitPK, EditSelectorKindDescriptionForm form);

    CommandResult deleteSelectorKindDescription(UserVisitPK userVisitPK, DeleteSelectorKindDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    CommandResult createSelectorType(UserVisitPK userVisitPK, CreateSelectorTypeForm form);

    CommandResult getSelectorTypes(UserVisitPK userVisitPK, GetSelectorTypesForm form);

    CommandResult getSelectorType(UserVisitPK userVisitPK, GetSelectorTypeForm form);

    CommandResult getSelectorTypeChoices(UserVisitPK userVisitPK, GetSelectorTypeChoicesForm form);

    CommandResult setDefaultSelectorType(UserVisitPK userVisitPK, SetDefaultSelectorTypeForm form);

    CommandResult editSelectorType(UserVisitPK userVisitPK, EditSelectorTypeForm form);

    CommandResult deleteSelectorType(UserVisitPK userVisitPK, DeleteSelectorTypeForm form);

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createSelectorTypeDescription(UserVisitPK userVisitPK, CreateSelectorTypeDescriptionForm form);

    CommandResult getSelectorTypeDescriptions(UserVisitPK userVisitPK, GetSelectorTypeDescriptionsForm form);

    CommandResult getSelectorTypeDescription(UserVisitPK userVisitPK, GetSelectorTypeDescriptionForm form);

    CommandResult editSelectorTypeDescription(UserVisitPK userVisitPK, EditSelectorTypeDescriptionForm form);

    CommandResult deleteSelectorTypeDescription(UserVisitPK userVisitPK, DeleteSelectorTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorBooleanType(UserVisitPK userVisitPK, CreateSelectorBooleanTypeForm form);
    
    CommandResult getSelectorBooleanTypeChoices(UserVisitPK userVisitPK, GetSelectorBooleanTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorBooleanTypeDescription(UserVisitPK userVisitPK, CreateSelectorBooleanTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Types
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorComparisonType(UserVisitPK userVisitPK, CreateSelectorComparisonTypeForm form);
    
    CommandResult getSelectorComparisonTypeChoices(UserVisitPK userVisitPK, GetSelectorComparisonTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorComparisonTypeDescription(UserVisitPK userVisitPK, CreateSelectorComparisonTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Node Types
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorNodeType(UserVisitPK userVisitPK, CreateSelectorNodeTypeForm form);
    
    CommandResult getSelectorNodeType(UserVisitPK userVisitPK, GetSelectorNodeTypeForm form);
    
    CommandResult getSelectorNodeTypes(UserVisitPK userVisitPK, GetSelectorNodeTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorNodeTypeDescription(UserVisitPK userVisitPK, CreateSelectorNodeTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorNodeTypeUse(UserVisitPK userVisitPK, CreateSelectorNodeTypeUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorTextSearchType(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeForm form);
    
    CommandResult getSelectorTextSearchTypeChoices(UserVisitPK userVisitPK, GetSelectorTextSearchTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorTextSearchTypeDescription(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selectors
    // --------------------------------------------------------------------------------
    
    CommandResult createSelector(UserVisitPK userVisitPK, CreateSelectorForm form);
    
    CommandResult getSelectorChoices(UserVisitPK userVisitPK, GetSelectorChoicesForm form);
    
    CommandResult getSelectors(UserVisitPK userVisitPK, GetSelectorsForm form);
    
    CommandResult getSelector(UserVisitPK userVisitPK, GetSelectorForm form);
    
    CommandResult setDefaultSelector(UserVisitPK userVisitPK, SetDefaultSelectorForm form);
    
    CommandResult editSelector(UserVisitPK userVisitPK, EditSelectorForm form);
    
    CommandResult deleteSelector(UserVisitPK userVisitPK, DeleteSelectorForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorDescription(UserVisitPK userVisitPK, CreateSelectorDescriptionForm form);
    
    CommandResult getSelectorDescriptions(UserVisitPK userVisitPK, GetSelectorDescriptionsForm form);
    
    CommandResult editSelectorDescription(UserVisitPK userVisitPK, EditSelectorDescriptionForm form);
    
    CommandResult deleteSelectorDescription(UserVisitPK userVisitPK, DeleteSelectorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorNode(UserVisitPK userVisitPK, CreateSelectorNodeForm form);
    
    CommandResult getSelectorNodeChoices(UserVisitPK userVisitPK, GetSelectorNodeChoicesForm form);
    
    CommandResult getSelectorNode(UserVisitPK userVisitPK, GetSelectorNodeForm form);
    
    CommandResult getSelectorNodes(UserVisitPK userVisitPK, GetSelectorNodesForm form);
    
    CommandResult setRootSelectorNode(UserVisitPK userVisitPK, SetRootSelectorNodeForm form);
    
    CommandResult deleteSelectorNode(UserVisitPK userVisitPK, DeleteSelectorNodeForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSelectorNodeDescription(UserVisitPK userVisitPK, CreateSelectorNodeDescriptionForm form);
    
    CommandResult getSelectorNodeDescriptions(UserVisitPK userVisitPK, GetSelectorNodeDescriptionsForm form);
    
    CommandResult editSelectorNodeDescription(UserVisitPK userVisitPK, EditSelectorNodeDescriptionForm form);
    
    CommandResult deleteSelectorNodeDescription(UserVisitPK userVisitPK, DeleteSelectorNodeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------
    
    CommandResult getSelectorParties(UserVisitPK userVisitPK, GetSelectorPartiesForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    CommandResult evaluateSelectors(UserVisitPK userVisitPK, EvaluateSelectorsForm form);
    
}
