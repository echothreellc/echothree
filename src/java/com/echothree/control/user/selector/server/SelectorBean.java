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

package com.echothree.control.user.selector.server;

import com.echothree.control.user.selector.common.SelectorRemote;
import com.echothree.control.user.selector.common.form.*;
import com.echothree.control.user.selector.common.result.*;
import com.echothree.control.user.selector.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<CreateSelectorKindResult> createSelectorKind(UserVisitPK userVisitPK, CreateSelectorKindForm form) {
        return CDI.current().select(CreateSelectorKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorKindsResult> getSelectorKinds(UserVisitPK userVisitPK, GetSelectorKindsForm form) {
        return CDI.current().select(GetSelectorKindsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorKindResult> getSelectorKind(UserVisitPK userVisitPK, GetSelectorKindForm form) {
        return CDI.current().select(GetSelectorKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorKindChoicesResult> getSelectorKindChoices(UserVisitPK userVisitPK, GetSelectorKindChoicesForm form) {
        return CDI.current().select(GetSelectorKindChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultSelectorKind(UserVisitPK userVisitPK, SetDefaultSelectorKindForm form) {
        return CDI.current().select(SetDefaultSelectorKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditSelectorKindResult> editSelectorKind(UserVisitPK userVisitPK, EditSelectorKindForm form) {
        return CDI.current().select(EditSelectorKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteSelectorKind(UserVisitPK userVisitPK, DeleteSelectorKindForm form) {
        return CDI.current().select(DeleteSelectorKindCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Kind Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createSelectorKindDescription(UserVisitPK userVisitPK, CreateSelectorKindDescriptionForm form) {
        return CDI.current().select(CreateSelectorKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorKindDescriptionsResult> getSelectorKindDescriptions(UserVisitPK userVisitPK, GetSelectorKindDescriptionsForm form) {
        return CDI.current().select(GetSelectorKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorKindDescriptionResult> getSelectorKindDescription(UserVisitPK userVisitPK, GetSelectorKindDescriptionForm form) {
        return CDI.current().select(GetSelectorKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditSelectorKindDescriptionResult> editSelectorKindDescription(UserVisitPK userVisitPK, EditSelectorKindDescriptionForm form) {
        return CDI.current().select(EditSelectorKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteSelectorKindDescription(UserVisitPK userVisitPK, DeleteSelectorKindDescriptionForm form) {
        return CDI.current().select(DeleteSelectorKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateSelectorTypeResult> createSelectorType(UserVisitPK userVisitPK, CreateSelectorTypeForm form) {
        return CDI.current().select(CreateSelectorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorTypesResult> getSelectorTypes(UserVisitPK userVisitPK, GetSelectorTypesForm form) {
        return CDI.current().select(GetSelectorTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorTypeResult> getSelectorType(UserVisitPK userVisitPK, GetSelectorTypeForm form) {
        return CDI.current().select(GetSelectorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorTypeChoicesResult> getSelectorTypeChoices(UserVisitPK userVisitPK, GetSelectorTypeChoicesForm form) {
        return CDI.current().select(GetSelectorTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultSelectorType(UserVisitPK userVisitPK, SetDefaultSelectorTypeForm form) {
        return CDI.current().select(SetDefaultSelectorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditSelectorTypeResult> editSelectorType(UserVisitPK userVisitPK, EditSelectorTypeForm form) {
        return CDI.current().select(EditSelectorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteSelectorType(UserVisitPK userVisitPK, DeleteSelectorTypeForm form) {
        return CDI.current().select(DeleteSelectorTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createSelectorTypeDescription(UserVisitPK userVisitPK, CreateSelectorTypeDescriptionForm form) {
        return CDI.current().select(CreateSelectorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorTypeDescriptionsResult> getSelectorTypeDescriptions(UserVisitPK userVisitPK, GetSelectorTypeDescriptionsForm form) {
        return CDI.current().select(GetSelectorTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetSelectorTypeDescriptionResult> getSelectorTypeDescription(UserVisitPK userVisitPK, GetSelectorTypeDescriptionForm form) {
        return CDI.current().select(GetSelectorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditSelectorTypeDescriptionResult> editSelectorTypeDescription(UserVisitPK userVisitPK, EditSelectorTypeDescriptionForm form) {
        return CDI.current().select(EditSelectorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteSelectorTypeDescription(UserVisitPK userVisitPK, DeleteSelectorTypeDescriptionForm form) {
        return CDI.current().select(DeleteSelectorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Selector Boolean Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorBooleanType(UserVisitPK userVisitPK, CreateSelectorBooleanTypeForm form) {
        return CDI.current().select(CreateSelectorBooleanTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorBooleanTypeChoicesResult> getSelectorBooleanTypeChoices(UserVisitPK userVisitPK, GetSelectorBooleanTypeChoicesForm form) {
        return CDI.current().select(GetSelectorBooleanTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Boolean Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorBooleanTypeDescription(UserVisitPK userVisitPK, CreateSelectorBooleanTypeDescriptionForm form) {
        return CDI.current().select(CreateSelectorBooleanTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorComparisonType(UserVisitPK userVisitPK, CreateSelectorComparisonTypeForm form) {
        return CDI.current().select(CreateSelectorComparisonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorComparisonTypeChoicesResult> getSelectorComparisonTypeChoices(UserVisitPK userVisitPK, GetSelectorComparisonTypeChoicesForm form) {
        return CDI.current().select(GetSelectorComparisonTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Comparison Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorComparisonTypeDescription(UserVisitPK userVisitPK, CreateSelectorComparisonTypeDescriptionForm form) {
        return CDI.current().select(CreateSelectorComparisonTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorNodeType(UserVisitPK userVisitPK, CreateSelectorNodeTypeForm form) {
        return CDI.current().select(CreateSelectorNodeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodeTypeResult> getSelectorNodeType(UserVisitPK userVisitPK, GetSelectorNodeTypeForm form) {
        return CDI.current().select(GetSelectorNodeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodeTypesResult> getSelectorNodeTypes(UserVisitPK userVisitPK, GetSelectorNodeTypesForm form) {
        return CDI.current().select(GetSelectorNodeTypesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorNodeTypeUse(UserVisitPK userVisitPK, CreateSelectorNodeTypeUseForm form) {
        return CDI.current().select(CreateSelectorNodeTypeUseCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorNodeTypeDescription(UserVisitPK userVisitPK, CreateSelectorNodeTypeDescriptionForm form) {
        return CDI.current().select(CreateSelectorNodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorTextSearchType(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeForm form) {
        return CDI.current().select(CreateSelectorTextSearchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorTextSearchTypeChoicesResult> getSelectorTextSearchTypeChoices(UserVisitPK userVisitPK, GetSelectorTextSearchTypeChoicesForm form) {
        return CDI.current().select(GetSelectorTextSearchTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Text Search Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorTextSearchTypeDescription(UserVisitPK userVisitPK, CreateSelectorTextSearchTypeDescriptionForm form) {
        return CDI.current().select(CreateSelectorTextSearchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selectors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateSelectorResult> createSelector(UserVisitPK userVisitPK, CreateSelectorForm form) {
        return CDI.current().select(CreateSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorChoicesResult> getSelectorChoices(UserVisitPK userVisitPK, GetSelectorChoicesForm form) {
        return CDI.current().select(GetSelectorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorsResult> getSelectors(UserVisitPK userVisitPK, GetSelectorsForm form) {
        return CDI.current().select(GetSelectorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorResult> getSelector(UserVisitPK userVisitPK, GetSelectorForm form) {
        return CDI.current().select(GetSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultSelector(UserVisitPK userVisitPK, SetDefaultSelectorForm form) {
        return CDI.current().select(SetDefaultSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSelectorResult> editSelector(UserVisitPK userVisitPK, EditSelectorForm form) {
        return CDI.current().select(EditSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSelector(UserVisitPK userVisitPK, DeleteSelectorForm form) {
        return CDI.current().select(DeleteSelectorCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorDescription(UserVisitPK userVisitPK, CreateSelectorDescriptionForm form) {
        return CDI.current().select(CreateSelectorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorDescriptionsResult> getSelectorDescriptions(UserVisitPK userVisitPK, GetSelectorDescriptionsForm form) {
        return CDI.current().select(GetSelectorDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSelectorDescriptionResult> editSelectorDescription(UserVisitPK userVisitPK, EditSelectorDescriptionForm form) {
        return CDI.current().select(EditSelectorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSelectorDescription(UserVisitPK userVisitPK, DeleteSelectorDescriptionForm form) {
        return CDI.current().select(DeleteSelectorDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Nodes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorNode(UserVisitPK userVisitPK, CreateSelectorNodeForm form) {
        return CDI.current().select(CreateSelectorNodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodeChoicesResult> getSelectorNodeChoices(UserVisitPK userVisitPK, GetSelectorNodeChoicesForm form) {
        return CDI.current().select(GetSelectorNodeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodeResult> getSelectorNode(UserVisitPK userVisitPK, GetSelectorNodeForm form) {
        return CDI.current().select(GetSelectorNodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodesResult> getSelectorNodes(UserVisitPK userVisitPK, GetSelectorNodesForm form) {
        return CDI.current().select(GetSelectorNodesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setRootSelectorNode(UserVisitPK userVisitPK, SetRootSelectorNodeForm form) {
        return CDI.current().select(SetRootSelectorNodeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSelectorNode(UserVisitPK userVisitPK, DeleteSelectorNodeForm form) {
        return CDI.current().select(DeleteSelectorNodeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Node Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSelectorNodeDescription(UserVisitPK userVisitPK, CreateSelectorNodeDescriptionForm form) {
        return CDI.current().select(CreateSelectorNodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSelectorNodeDescriptionsResult> getSelectorNodeDescriptions(UserVisitPK userVisitPK, GetSelectorNodeDescriptionsForm form) {
        return CDI.current().select(GetSelectorNodeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSelectorNodeDescriptionResult> editSelectorNodeDescription(UserVisitPK userVisitPK, EditSelectorNodeDescriptionForm form) {
        return CDI.current().select(EditSelectorNodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSelectorNodeDescription(UserVisitPK userVisitPK, DeleteSelectorNodeDescriptionForm form) {
        return CDI.current().select(DeleteSelectorNodeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Selector Parties
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetSelectorPartiesResult> getSelectorParties(UserVisitPK userVisitPK, GetSelectorPartiesForm form) {
        return CDI.current().select(GetSelectorPartiesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<EvaluateSelectorsResult> evaluateSelectors(UserVisitPK userVisitPK, EvaluateSelectorsForm form) {
        return CDI.current().select(EvaluateSelectorsCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
}
