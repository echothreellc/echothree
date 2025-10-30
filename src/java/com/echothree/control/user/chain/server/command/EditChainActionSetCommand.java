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

import com.echothree.control.user.chain.common.edit.ChainActionSetEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionSetForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionSetResult;
import com.echothree.control.user.chain.common.spec.ChainActionSetSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.Chain;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditChainActionSetCommand
        extends BaseAbstractEditCommand<ChainActionSetSpec, ChainActionSetEdit, EditChainActionSetResult, ChainActionSet, ChainActionSet> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainActionSet.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainActionSetCommand */
    public EditChainActionSetCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionSetResult getResult() {
        return ChainResultFactory.getEditChainActionSetResult();
    }

    @Override
    public ChainActionSetEdit getEdit() {
        return ChainEditFactory.getChainActionSetEdit();
    }

    Chain chain;

    @Override
    public ChainActionSet getEntity(EditChainActionSetResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainActionSet chainActionSet = null;
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var chainName = spec.getChainName();
                
                chain = chainControl.getChainByName(chainType, chainName);

                if(chain != null) {
                    var chainActionSetName = spec.getChainActionSetName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);
                    } else { // EditMode.UPDATE
                        chainActionSet = chainControl.getChainActionSetByNameForUpdate(chain, chainActionSetName);
                    }

                    if(chainActionSet == null) {
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

        return chainActionSet;
    }

    @Override
    public ChainActionSet getLockEntity(ChainActionSet chainActionSet) {
        return chainActionSet;
    }

    @Override
    public void fillInResult(EditChainActionSetResult result, ChainActionSet chainActionSet) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainActionSet(chainControl.getChainActionSetTransfer(getUserVisit(), chainActionSet));
    }

    @Override
    public void doLock(ChainActionSetEdit edit, ChainActionSet chainActionSet) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionSetDescription = chainControl.getChainActionSetDescription(chainActionSet, getPreferredLanguage());
        var chainActionSetDetail = chainActionSet.getLastDetail();

        edit.setChainActionSetName(chainActionSetDetail.getChainActionSetName());
        edit.setIsDefault(chainActionSetDetail.getIsDefault().toString());
        edit.setSortOrder(chainActionSetDetail.getSortOrder().toString());

        if(chainActionSetDescription != null) {
            edit.setDescription(chainActionSetDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ChainActionSet chainActionSet) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionSetName = edit.getChainActionSetName();
        var duplicateChainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);

        if(duplicateChainActionSet != null && !chainActionSet.equals(duplicateChainActionSet)) {
            var chainDetail = chain.getLastDetail();
            var chainTypeDetail = chainDetail.getChainType().getLastDetail();
            var chainKindDetail = chainTypeDetail.getChainKind().getLastDetail();
            
            addExecutionError(ExecutionErrors.DuplicateChainActionSetName.name(), chainKindDetail.getChainKindName(), chainTypeDetail.getChainTypeName(),
                    chainDetail.getChainName(), chainActionSetName);
        }
    }

    @Override
    public void doUpdate(ChainActionSet chainActionSet) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainActionSetDetailValue = chainControl.getChainActionSetDetailValueForUpdate(chainActionSet);
        var chainActionSetDescription = chainControl.getChainActionSetDescriptionForUpdate(chainActionSet, getPreferredLanguage());
        var description = edit.getDescription();

        chainActionSetDetailValue.setChainActionSetName(edit.getChainActionSetName());
        chainActionSetDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        chainActionSetDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainActionSetFromValue(chainActionSetDetailValue, partyPK);

        if(chainActionSetDescription == null && description != null) {
            chainControl.createChainActionSetDescription(chainActionSet, getPreferredLanguage(), description, partyPK);
        } else if(chainActionSetDescription != null && description == null) {
            chainControl.deleteChainActionSetDescription(chainActionSetDescription, partyPK);
        } else if(chainActionSetDescription != null && description != null) {
            var chainActionSetDescriptionValue = chainControl.getChainActionSetDescriptionValue(chainActionSetDescription);

            chainActionSetDescriptionValue.setDescription(description);
            chainControl.updateChainActionSetDescriptionFromValue(chainActionSetDescriptionValue, partyPK);
        }
    }

}
