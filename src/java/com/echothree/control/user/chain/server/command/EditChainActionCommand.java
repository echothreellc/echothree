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

import com.echothree.control.user.chain.common.edit.ChainActionEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionResult;
import com.echothree.control.user.chain.common.spec.ChainActionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditChainActionCommand
        extends BaseAbstractEditCommand<ChainActionSpec, ChainActionEdit, EditChainActionResult, ChainAction, ChainAction> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainAction.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainActionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainActionCommand */
    public EditChainActionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionResult getResult() {
        return ChainResultFactory.getEditChainActionResult();
    }

    @Override
    public ChainActionEdit getEdit() {
        return ChainEditFactory.getChainActionEdit();
    }

    ChainActionSet chainActionSet;

    @Override
    public ChainAction getEntity(EditChainActionResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainAction chainAction = null;
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
                    
                    chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);

                    if(chainActionSet != null) {
                        var chainActionName = spec.getChainActionName();

                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            chainAction = chainControl.getChainActionByName(chainActionSet, chainActionName);
                        } else { // EditMode.UPDATE
                            chainAction = chainControl.getChainActionByNameForUpdate(chainActionSet, chainActionName);
                        }

                        if(chainAction == null) {
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

        return chainAction;
    }

    @Override
    public ChainAction getLockEntity(ChainAction chainAction) {
        return chainAction;
    }

    @Override
    public void fillInResult(EditChainActionResult result, ChainAction chainAction) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainAction(chainControl.getChainActionTransfer(getUserVisit(), chainAction));
    }

    @Override
    public void doLock(ChainActionEdit edit, ChainAction chainAction) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionDescription = chainControl.getChainActionDescription(chainAction, getPreferredLanguage());
        var chainActionDetail = chainAction.getLastDetail();

        edit.setChainActionName(chainActionDetail.getChainActionName());
        edit.setSortOrder(chainActionDetail.getSortOrder().toString());

        if(chainActionDescription != null) {
            edit.setDescription(chainActionDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ChainAction chainAction) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionName = edit.getChainActionName();
        var duplicateChainAction = chainControl.getChainActionByName(chainActionSet, chainActionName);

        if(duplicateChainAction != null && !chainAction.equals(duplicateChainAction)) {
            var chainActionSetDetail = chainActionSet.getLastDetail();
            var chainDetail = chainActionSetDetail.getChain().getLastDetail();
            var chainTypeDetail = chainDetail.getChainType().getLastDetail();
            var chainKindDetail = chainTypeDetail.getChainKind().getLastDetail();
            
            addExecutionError(ExecutionErrors.DuplicateChainActionName.name(), chainKindDetail.getChainKindName(), chainTypeDetail.getChainTypeName(),
                    chainDetail.getChainName(), chainActionSetDetail.getChainActionSetName(), chainActionName);
        }
    }

    @Override
    public void doUpdate(ChainAction chainAction) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainActionDetailValue = chainControl.getChainActionDetailValueForUpdate(chainAction);
        var chainActionDescription = chainControl.getChainActionDescriptionForUpdate(chainAction, getPreferredLanguage());
        var description = edit.getDescription();

        chainActionDetailValue.setChainActionName(edit.getChainActionName());
        chainActionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainActionFromValue(chainActionDetailValue, partyPK);

        if(chainActionDescription == null && description != null) {
            chainControl.createChainActionDescription(chainAction, getPreferredLanguage(), description, partyPK);
        } else if(chainActionDescription != null && description == null) {
            chainControl.deleteChainActionDescription(chainActionDescription, partyPK);
        } else if(chainActionDescription != null && description != null) {
            var chainActionDescriptionValue = chainControl.getChainActionDescriptionValue(chainActionDescription);

            chainActionDescriptionValue.setDescription(description);
            chainControl.updateChainActionDescriptionFromValue(chainActionDescriptionValue, partyPK);
        }
    }

}
