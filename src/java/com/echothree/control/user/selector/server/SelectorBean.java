// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
        return new CreateSelectorKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorKinds(UserVisitPK userVisitPK, GetSelectorKindsForm form) {
        return new GetSelectorKindsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorKind(UserVisitPK userVisitPK, GetSelectorKindForm form) {
        return new GetSelectorKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorKindChoices(UserVisitPK userVisitPK, GetSelectorKindChoicesForm form) {
        return new GetSelectorKindChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSelectorKind(UserVisitPK userVisitPK, SetDefaultSelectorKindForm form) {
        return new SetDefaultSelectorKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSelectorKind(UserVisitPK userVisitPK, EditSelectorKindForm form) {
        return new EditSelectorKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSelectorKind(UserVisitPK userVisitPK, DeleteSelectorKindForm form) {
        return new DeleteSelectorKindCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorKindDescription(UserVisitPK userVisitPK, CreateSelectorKindDescriptionForm form) {
        return new CreateSelectorKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorKindDescriptions(UserVisitPK userVisitPK, GetSelectorKindDescriptionsForm form) {
        return new GetSelectorKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorKindDescription(UserVisitPK userVisitPK, GetSelectorKindDescriptionForm form) {
        return new GetSelectorKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSelectorKindDescription(UserVisitPK userVisitPK, EditSelectorKindDescriptionForm form) {
        return new EditSelectorKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSelectorKindDescription(UserVisitPK userVisitPK, DeleteSelectorKindDescriptionForm form) {
        return new DeleteSelectorKindDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorType(UserVisitPK userVisitPK, CreateSelectorTypeForm form) {
        return new CreateSelectorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorTypes(UserVisitPK userVisitPK, GetSelectorTypesForm form) {
        return new GetSelectorTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorType(UserVisitPK userVisitPK, GetSelectorTypeForm form) {
        return new GetSelectorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorTypeChoices(UserVisitPK userVisitPK, GetSelectorTypeChoicesForm form) {
        return new GetSelectorTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSelectorType(UserVisitPK userVisitPK, SetDefaultSelectorTypeForm form) {
        return new SetDefaultSelectorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSelectorType(UserVisitPK userVisitPK, EditSelectorTypeForm form) {
        return new EditSelectorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSelectorType(UserVisitPK userVisitPK, DeleteSelectorTypeForm form) {
        return new DeleteSelectorTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createSelectorTypeDescription(UserVisitPK userVisitPK, CreateSelectorTypeDescriptionForm form) {
        return new CreateSelectorTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorTypeDescriptions(UserVisitPK userVisitPK, GetSelectorTypeDescriptionsForm form) {
        return new GetSelectorTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSelectorTypeDescription(UserVisitPK userVisitPK, GetSelectorTypeDescriptionForm form) {
        return new GetSelectorTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSelectorTypeDescription(UserVisitPK userVisitPK, EditSelectorTypeDescriptionForm form) {
        return new EditSelectorTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSelectorTypeDescription(UserVisitPK userVisitPK, DeleteSelectorTypeDescriptionForm form) {
        return new DeleteSelectorTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorBooleanType(UserVisitPK userVisitPK, CreateSelectorBooleanTypeForm form) {
        return new CreateSelectorBooleanTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorBooleanTypeChoices(UserVisitPK userVisitPK, GetSelectorBooleanTypeChoicesForm form) {
        return new GetSelectorBooleanTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorBooleanTypeDescription(UserVisitPK userVisitPK, CreateSelectorBooleanTypeDescriptionForm form) {
        return new CreateSelectorBooleanTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorComparisonType(UserVisitPK userVisitPK, CreateSelectorComparisonTypeForm form) {
        return new CreateSelectorComparisonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorComparisonTypeChoices(UserVisitPK userVisitPK, GetSelectorComparisonTypeChoicesForm form) {
        return new GetSelectorComparisonTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorComparisonTypeDescription(UserVisitPK userVisitPK, CreateSelectorComparisonTypeDescriptionForm form) {
        return new CreateSelectorComparisonTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeType(UserVisitPK userVisitPK, CreateSelectorNodeTypeForm form) {
        return new CreateSelectorNodeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNodeType(UserVisitPK userVisitPK, GetSelectorNodeTypeForm form) {
        return new GetSelectorNodeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNodeTypes(UserVisitPK userVisitPK, GetSelectorNodeTypesForm form) {
        return new GetSelectorNodeTypesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeTypeUse(UserVisitPK userVisitPK, CreateSelectorNodeTypeUseForm form) {
        return new CreateSelectorNodeTypeUseCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeTypeDescription(UserVisitPK userVisitPK, CreateSelectorNodeTypeDescriptionForm form) {
        return new CreateSelectorNodeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorTextSearchType(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeForm form) {
        return new CreateSelectorTextSearchTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorTextSearchTypeChoices(UserVisitPK userVisitPK, GetSelectorTextSearchTypeChoicesForm form) {
        return new GetSelectorTextSearchTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorTextSearchTypeDescription(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeDescriptionForm form) {
        return new CreateSelectorTextSearchTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelector(UserVisitPK userVisitPK, CreateSelectorForm form) {
        return new CreateSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorChoices(UserVisitPK userVisitPK, GetSelectorChoicesForm form) {
        return new GetSelectorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectors(UserVisitPK userVisitPK, GetSelectorsForm form) {
        return new GetSelectorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelector(UserVisitPK userVisitPK, GetSelectorForm form) {
        return new GetSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSelector(UserVisitPK userVisitPK, SetDefaultSelectorForm form) {
        return new SetDefaultSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSelector(UserVisitPK userVisitPK, EditSelectorForm form) {
        return new EditSelectorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSelector(UserVisitPK userVisitPK, DeleteSelectorForm form) {
        return new DeleteSelectorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorDescription(UserVisitPK userVisitPK, CreateSelectorDescriptionForm form) {
        return new CreateSelectorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorDescriptions(UserVisitPK userVisitPK, GetSelectorDescriptionsForm form) {
        return new GetSelectorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSelectorDescription(UserVisitPK userVisitPK, EditSelectorDescriptionForm form) {
        return new EditSelectorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSelectorDescription(UserVisitPK userVisitPK, DeleteSelectorDescriptionForm form) {
        return new DeleteSelectorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNode(UserVisitPK userVisitPK, CreateSelectorNodeForm form) {
        return new CreateSelectorNodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNodeChoices(UserVisitPK userVisitPK, GetSelectorNodeChoicesForm form) {
        return new GetSelectorNodeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNode(UserVisitPK userVisitPK, GetSelectorNodeForm form) {
        return new GetSelectorNodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNodes(UserVisitPK userVisitPK, GetSelectorNodesForm form) {
        return new GetSelectorNodesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setRootSelectorNode(UserVisitPK userVisitPK, SetRootSelectorNodeForm form) {
        return new SetRootSelectorNodeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSelectorNode(UserVisitPK userVisitPK, DeleteSelectorNodeForm form) {
        return new DeleteSelectorNodeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSelectorNodeDescription(UserVisitPK userVisitPK, CreateSelectorNodeDescriptionForm form) {
        return new CreateSelectorNodeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSelectorNodeDescriptions(UserVisitPK userVisitPK, GetSelectorNodeDescriptionsForm form) {
        return new GetSelectorNodeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSelectorNodeDescription(UserVisitPK userVisitPK, EditSelectorNodeDescriptionForm form) {
        return new EditSelectorNodeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSelectorNodeDescription(UserVisitPK userVisitPK, DeleteSelectorNodeDescriptionForm form) {
        return new DeleteSelectorNodeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getSelectorParties(UserVisitPK userVisitPK, GetSelectorPartiesForm form) {
        return new GetSelectorPartiesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult evaluateSelectors(UserVisitPK userVisitPK) {
        return new EvaluateSelectorsCommand(userVisitPK).run();
    }
    
    // -------------------------------------------------------------------------
}
