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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.edit.SelectorEditFactory;
import com.echothree.control.user.selector.common.edit.SelectorNodeDescriptionEdit;
import com.echothree.control.user.selector.common.result.EditSelectorNodeDescriptionResult;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.control.user.selector.common.spec.SelectorNodeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeDescription;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSelectorNodeDescriptionCommand
        extends BaseAbstractEditCommand<SelectorNodeDescriptionSpec, SelectorNodeDescriptionEdit, EditSelectorNodeDescriptionResult, SelectorNodeDescription, SelectorNode> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorNodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    SelectorControl selectorControl;

    /** Creates a new instance of EditSelectorNodeDescriptionCommand */
    public EditSelectorNodeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSelectorNodeDescriptionResult getResult() {
        return SelectorResultFactory.getEditSelectorNodeDescriptionResult();
    }

    @Override
    public SelectorNodeDescriptionEdit getEdit() {
        return SelectorEditFactory.getSelectorNodeDescriptionEdit();
    }

    @Override
    public SelectorNodeDescription getEntity(EditSelectorNodeDescriptionResult result) {
        SelectorNodeDescription selectorNodeDescription = null;
        var selectorKindName = spec.getSelectorKindName();
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);

        if(selectorKind != null) {
            var selectorTypeName = spec.getSelectorTypeName();
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

            if(selectorType != null) {
                var selectorName = spec.getSelectorName();
                var selector = selectorControl.getSelectorByName(selectorType, selectorName);

                if(selector != null) {
                    var selectorNodeName = spec.getSelectorNodeName();
                    var selectorNode = selectorControl.getSelectorNodeByName(selector, selectorNodeName);

                    if(selectorNode != null) {
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                selectorNodeDescription = selectorControl.getSelectorNodeDescription(selectorNode, language);
                            } else { // EditMode.UPDATE
                                selectorNodeDescription = selectorControl.getSelectorNodeDescriptionForUpdate(selectorNode, language);
                            }

                            if(selectorNodeDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownSelectorNodeDescription.name(), selectorKindName, selectorTypeName, selectorName, selectorNodeName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorNodeName.name(), selectorKindName, selectorTypeName, selectorName, selectorNodeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorKindName, selectorTypeName, selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorKindName, selectorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
        }

        return selectorNodeDescription;
    }

    @Override
    public SelectorNode getLockEntity(SelectorNodeDescription selectorNodeDescription) {
        return selectorNodeDescription.getSelectorNode();
    }

    @Override
    public void fillInResult(EditSelectorNodeDescriptionResult result, SelectorNodeDescription selectorNodeDescription) {
        result.setSelectorNodeDescription(selectorControl.getSelectorNodeDescriptionTransfer(getUserVisit(), selectorNodeDescription));
    }

    @Override
    public void doLock(SelectorNodeDescriptionEdit edit, SelectorNodeDescription selectorNodeDescription) {
        edit.setDescription(selectorNodeDescription.getDescription());
    }

    @Override
    public void doUpdate(SelectorNodeDescription selectorNodeDescription) {
        var selectorNodeDescriptionValue = selectorControl.getSelectorNodeDescriptionValue(selectorNodeDescription);

        selectorNodeDescriptionValue.setDescription(edit.getDescription());

        selectorControl.updateSelectorNodeDescriptionFromValue(selectorNodeDescriptionValue, getPartyPK());
    }

}
