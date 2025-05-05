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

import com.echothree.control.user.chain.common.edit.ChainActionDescriptionEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainActionDescriptionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionDescription;
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

public class EditChainActionDescriptionCommand
        extends BaseAbstractEditCommand<ChainActionDescriptionSpec, ChainActionDescriptionEdit, EditChainActionDescriptionResult, ChainActionDescription, ChainAction> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainAction.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainActionDescriptionCommand */
    public EditChainActionDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionDescriptionResult getResult() {
        return ChainResultFactory.getEditChainActionDescriptionResult();
    }

    @Override
    public ChainActionDescriptionEdit getEdit() {
        return ChainEditFactory.getChainActionDescriptionEdit();
    }

    @Override
    public ChainActionDescription getEntity(EditChainActionDescriptionResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainActionDescription chainActionDescription = null;
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
                        var chainActionName = spec.getChainActionName();
                        var chainAction = chainControl.getChainActionByName(chainActionSet, chainActionName);

                        if(chainAction != null) {
                            var partyControl = Session.getModelController(PartyControl.class);
                            var languageIsoName = spec.getLanguageIsoName();
                            var language = partyControl.getLanguageByIsoName(languageIsoName);

                            if(language != null) {
                                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                    chainActionDescription = chainControl.getChainActionDescription(chainAction, language);
                                } else { // EditMode.UPDATE
                                    chainActionDescription = chainControl.getChainActionDescriptionForUpdate(chainAction, language);
                                }

                                if(chainActionDescription == null) {
                                    addExecutionError(ExecutionErrors.UnknownChainActionDescription.name(), chainKindName, chainTypeName, chainName,
                                            chainActionSetName, chainActionName, languageIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownChainActionName.name(), chainKindName, chainTypeName, chainName, chainActionSetName,
                                    chainActionName);
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

        return chainActionDescription;
    }

    @Override
    public ChainAction getLockEntity(ChainActionDescription chainActionDescription) {
        return chainActionDescription.getChainAction();
    }

    @Override
    public void fillInResult(EditChainActionDescriptionResult result, ChainActionDescription chainActionDescription) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainActionDescription(chainControl.getChainActionDescriptionTransfer(getUserVisit(), chainActionDescription));
    }

    @Override
    public void doLock(ChainActionDescriptionEdit edit, ChainActionDescription chainActionDescription) {
        edit.setDescription(chainActionDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainActionDescription chainActionDescription) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionDescriptionValue = chainControl.getChainActionDescriptionValue(chainActionDescription);

        chainActionDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainActionDescriptionFromValue(chainActionDescriptionValue, getPartyPK());
    }

}
