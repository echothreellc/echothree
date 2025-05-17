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

package com.echothree.control.user.selector.server;

import com.echothree.control.user.selector.common.SelectorRemote;
import com.echothree.control.user.selector.common.form.*;
import com.echothree.control.user.selector.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SelectorBean
        extends SelectorFormsImpl
        implements SelectorRemote, SelectorLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SelectorBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Kinds
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorKind(UserVisitPK userVisitPK, CreateSelectorKindForm form) {
        return new CreateSelectorKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorKinds(UserVisitPK userVisitPK, GetSelectorKindsForm form) {
        return new GetSelectorKindsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorKind(UserVisitPK userVisitPK, GetSelectorKindForm form) {
        return new GetSelectorKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorKindChoices(UserVisitPK userVisitPK, GetSelectorKindChoicesForm form) {
        return new GetSelectorKindChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSelectorKind(UserVisitPK userVisitPK, SetDefaultSelectorKindForm form) {
        return new SetDefaultSelectorKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSelectorKind(UserVisitPK userVisitPK, EditSelectorKindForm form) {
        return new EditSelectorKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSelectorKind(UserVisitPK userVisitPK, DeleteSelectorKindForm form) {
        return new DeleteSelectorKindCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorKindDescription(UserVisitPK userVisitPK, CreateSelectorKindDescriptionForm form) {
        return new CreateSelectorKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorKindDescriptions(UserVisitPK userVisitPK, GetSelectorKindDescriptionsForm form) {
        return new GetSelectorKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorKindDescription(UserVisitPK userVisitPK, GetSelectorKindDescriptionForm form) {
        return new GetSelectorKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSelectorKindDescription(UserVisitPK userVisitPK, EditSelectorKindDescriptionForm form) {
        return new EditSelectorKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSelectorKindDescription(UserVisitPK userVisitPK, DeleteSelectorKindDescriptionForm form) {
        return new DeleteSelectorKindDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorType(UserVisitPK userVisitPK, CreateSelectorTypeForm form) {
        return new CreateSelectorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorTypes(UserVisitPK userVisitPK, GetSelectorTypesForm form) {
        return new GetSelectorTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorType(UserVisitPK userVisitPK, GetSelectorTypeForm form) {
        return new GetSelectorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorTypeChoices(UserVisitPK userVisitPK, GetSelectorTypeChoicesForm form) {
        return new GetSelectorTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSelectorType(UserVisitPK userVisitPK, SetDefaultSelectorTypeForm form) {
        return new SetDefaultSelectorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSelectorType(UserVisitPK userVisitPK, EditSelectorTypeForm form) {
        return new EditSelectorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSelectorType(UserVisitPK userVisitPK, DeleteSelectorTypeForm form) {
        return new DeleteSelectorTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorTypeDescription(UserVisitPK userVisitPK, CreateSelectorTypeDescriptionForm form) {
        return new CreateSelectorTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorTypeDescriptions(UserVisitPK userVisitPK, GetSelectorTypeDescriptionsForm form) {
        return new GetSelectorTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSelectorTypeDescription(UserVisitPK userVisitPK, GetSelectorTypeDescriptionForm form) {
        return new GetSelectorTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSelectorTypeDescription(UserVisitPK userVisitPK, EditSelectorTypeDescriptionForm form) {
        return new EditSelectorTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSelectorTypeDescription(UserVisitPK userVisitPK, DeleteSelectorTypeDescriptionForm form) {
        return new DeleteSelectorTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorBooleanType(UserVisitPK userVisitPK, CreateSelectorBooleanTypeForm form) {
        return new CreateSelectorBooleanTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorBooleanTypeChoices(UserVisitPK userVisitPK, GetSelectorBooleanTypeChoicesForm form) {
        return new GetSelectorBooleanTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorBooleanTypeDescription(UserVisitPK userVisitPK, CreateSelectorBooleanTypeDescriptionForm form) {
        return new CreateSelectorBooleanTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorComparisonType(UserVisitPK userVisitPK, CreateSelectorComparisonTypeForm form) {
        return new CreateSelectorComparisonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorComparisonTypeChoices(UserVisitPK userVisitPK, GetSelectorComparisonTypeChoicesForm form) {
        return new GetSelectorComparisonTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorComparisonTypeDescription(UserVisitPK userVisitPK, CreateSelectorComparisonTypeDescriptionForm form) {
        return new CreateSelectorComparisonTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeType(UserVisitPK userVisitPK, CreateSelectorNodeTypeForm form) {
        return new CreateSelectorNodeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNodeType(UserVisitPK userVisitPK, GetSelectorNodeTypeForm form) {
        return new GetSelectorNodeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNodeTypes(UserVisitPK userVisitPK, GetSelectorNodeTypesForm form) {
        return new GetSelectorNodeTypesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeTypeUse(UserVisitPK userVisitPK, CreateSelectorNodeTypeUseForm form) {
        return new CreateSelectorNodeTypeUseCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeTypeDescription(UserVisitPK userVisitPK, CreateSelectorNodeTypeDescriptionForm form) {
        return new CreateSelectorNodeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorTextSearchType(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeForm form) {
        return new CreateSelectorTextSearchTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorTextSearchTypeChoices(UserVisitPK userVisitPK, GetSelectorTextSearchTypeChoicesForm form) {
        return new GetSelectorTextSearchTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorTextSearchTypeDescription(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeDescriptionForm form) {
        return new CreateSelectorTextSearchTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelector(UserVisitPK userVisitPK, CreateSelectorForm form) {
        return new CreateSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorChoices(UserVisitPK userVisitPK, GetSelectorChoicesForm form) {
        return new GetSelectorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectors(UserVisitPK userVisitPK, GetSelectorsForm form) {
        return new GetSelectorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelector(UserVisitPK userVisitPK, GetSelectorForm form) {
        return new GetSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSelector(UserVisitPK userVisitPK, SetDefaultSelectorForm form) {
        return new SetDefaultSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSelector(UserVisitPK userVisitPK, EditSelectorForm form) {
        return new EditSelectorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSelector(UserVisitPK userVisitPK, DeleteSelectorForm form) {
        return new DeleteSelectorCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorDescription(UserVisitPK userVisitPK, CreateSelectorDescriptionForm form) {
        return new CreateSelectorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorDescriptions(UserVisitPK userVisitPK, GetSelectorDescriptionsForm form) {
        return new GetSelectorDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSelectorDescription(UserVisitPK userVisitPK, EditSelectorDescriptionForm form) {
        return new EditSelectorDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSelectorDescription(UserVisitPK userVisitPK, DeleteSelectorDescriptionForm form) {
        return new DeleteSelectorDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNode(UserVisitPK userVisitPK, CreateSelectorNodeForm form) {
        return new CreateSelectorNodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNodeChoices(UserVisitPK userVisitPK, GetSelectorNodeChoicesForm form) {
        return new GetSelectorNodeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNode(UserVisitPK userVisitPK, GetSelectorNodeForm form) {
        return new GetSelectorNodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNodes(UserVisitPK userVisitPK, GetSelectorNodesForm form) {
        return new GetSelectorNodesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setRootSelectorNode(UserVisitPK userVisitPK, SetRootSelectorNodeForm form) {
        return new SetRootSelectorNodeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSelectorNode(UserVisitPK userVisitPK, DeleteSelectorNodeForm form) {
        return new DeleteSelectorNodeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeDescription(UserVisitPK userVisitPK, CreateSelectorNodeDescriptionForm form) {
        return new CreateSelectorNodeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSelectorNodeDescriptions(UserVisitPK userVisitPK, GetSelectorNodeDescriptionsForm form) {
        return new GetSelectorNodeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSelectorNodeDescription(UserVisitPK userVisitPK, EditSelectorNodeDescriptionForm form) {
        return new EditSelectorNodeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSelectorNodeDescription(UserVisitPK userVisitPK, DeleteSelectorNodeDescriptionForm form) {
        return new DeleteSelectorNodeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getSelectorParties(UserVisitPK userVisitPK, GetSelectorPartiesForm form) {
        return new GetSelectorPartiesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult evaluateSelectors(UserVisitPK userVisitPK, EvaluateSelectorsForm form) {
        return new EvaluateSelectorsCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
}
