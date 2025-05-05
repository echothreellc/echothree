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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.edit.ChainActionSetDescriptionEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionSetDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionSetDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainActionSetDescriptionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionSetDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditChainActionSetDescriptionCommand
        extends BaseAbstractEditCommand<ChainActionSetDescriptionSpec, ChainActionSetDescriptionEdit, EditChainActionSetDescriptionResult, ChainActionSetDescription, ChainActionSet> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainActionSet.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainActionSetDescriptionCommand */
    public EditChainActionSetDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionSetDescriptionResult getResult() {
        return ChainResultFactory.getEditChainActionSetDescriptionResult();
    }

    @Override
    public ChainActionSetDescriptionEdit getEdit() {
        return ChainEditFactory.getChainActionSetDescriptionEdit();
    }

    @Override
    public ChainActionSetDescription getEntity(EditChainActionSetDescriptionResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainActionSetDescription chainActionSetDescription = null;
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var chainName = spec.getChainName();
                var chain = chainControl.getChainByName(chainType, chainName);

                if(chain != null) {
                    var chainActionSetName = spec.getChainActionSetName();
                    var chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);

                    if(chainActionSet != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                chainActionSetDescription = chainControl.getChainActionSetDescription(chainActionSet, language);
                            } else { // EditMode.UPDATE
                                chainActionSetDescription = chainControl.getChainActionSetDescriptionForUpdate(chainActionSet, language);
                            }

                            if(chainActionSetDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownChainActionSetDescription.name(), chainKindName, chainTypeName, chainName, chainActionSetName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownChainActionSetName.name(), chainKindName, chainTypeName, chainName, chainActionSetName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainName.name(), chainKindName, chainTypeName, chainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainActionSetDescription;
    }

    @Override
    public ChainActionSet getLockEntity(ChainActionSetDescription chainActionSetDescription) {
        return chainActionSetDescription.getChainActionSet();
    }

    @Override
    public void fillInResult(EditChainActionSetDescriptionResult result, ChainActionSetDescription chainActionSetDescription) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainActionSetDescription(chainControl.getChainActionSetDescriptionTransfer(getUserVisit(), chainActionSetDescription));
    }

    @Override
    public void doLock(ChainActionSetDescriptionEdit edit, ChainActionSetDescription chainActionSetDescription) {
        edit.setDescription(chainActionSetDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainActionSetDescription chainActionSetDescription) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionSetDescriptionValue = chainControl.getChainActionSetDescriptionValue(chainActionSetDescription);

        chainActionSetDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainActionSetDescriptionFromValue(chainActionSetDescriptionValue, getPartyPK());
    }

}
