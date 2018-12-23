// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.chain.common.edit.ChainEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainResult;
import com.echothree.control.user.chain.common.spec.ChainSpec;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainDescription;
import com.echothree.model.data.chain.server.entity.ChainDetail;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.entity.ChainTypeDetail;
import com.echothree.model.data.chain.server.value.ChainDescriptionValue;
import com.echothree.model.data.chain.server.value.ChainDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
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

public class EditChainCommand
        extends BaseAbstractEditCommand<ChainSpec, ChainEdit, EditChainResult, Chain, Chain> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Chain.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainInstanceSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditChainCommand */
    public EditChainCommand(UserVisitPK userVisitPK, EditChainForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainResult getResult() {
        return ChainResultFactory.getEditChainResult();
    }

    @Override
    public ChainEdit getEdit() {
        return ChainEditFactory.getChainEdit();
    }

    ChainType chainType;

    @Override
    public Chain getEntity(EditChainResult result) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        Chain chain = null;
        String chainKindName = spec.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            String chainTypeName = spec.getChainTypeName();

            chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                String chainName = spec.getChainName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    chain = chainControl.getChainByName(chainType, chainName);
                } else { // EditMode.UPDATE
                    chain = chainControl.getChainByNameForUpdate(chainType, chainName);
                }

                if(chain == null) {
                    addExecutionError(ExecutionErrors.UnknownChainName.name(), chainTypeName, chainTypeName, chainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chain;
    }

    @Override
    public Chain getLockEntity(Chain chain) {
        return chain;
    }

    @Override
    public void fillInResult(EditChainResult result, Chain chain) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);

        result.setChain(chainControl.getChainTransfer(getUserVisit(), chain));
    }

    Sequence chainInstanceSequence;
    
    @Override
    public void doLock(ChainEdit edit, Chain chain) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainDescription chainDescription = chainControl.getChainDescription(chain, getPreferredLanguage());
        ChainDetail chainDetail = chain.getLastDetail();
        
        chainInstanceSequence = chainDetail.getChainInstanceSequence();

        edit.setChainName(chainDetail.getChainName());
        edit.setChainInstanceSequenceName(chainInstanceSequence == null ? null : chainInstanceSequence.getLastDetail().getSequenceName());
        edit.setIsDefault(chainDetail.getIsDefault().toString());
        edit.setSortOrder(chainDetail.getSortOrder().toString());

        if(chainDescription != null) {
            edit.setDescription(chainDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Chain chain) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainTypeDetail chainTypeDetail = chainType.getLastDetail();
        String chainName = edit.getChainName();
        Chain duplicateChain = chainControl.getChainByName(chainType, chainName);

        if(duplicateChain != null && !chain.equals(duplicateChain)) {
            addExecutionError(ExecutionErrors.DuplicateChainName.name(), chainTypeDetail.getChainTypeName(), chainName);
        } else {
            SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_CHAIN_INSTANCE);
            String chainInstanceSequenceName = edit.getChainInstanceSequenceName();

            chainInstanceSequence = sequenceControl.getSequenceByName(sequenceType, chainInstanceSequenceName);
            
            if(chainInstanceSequenceName != null && chainInstanceSequence == null) {
                addExecutionError(ExecutionErrors.UnknownChainInstanceSequenceName.name(), chainInstanceSequenceName);
            }
        }
    }

    @Override
    public void doUpdate(Chain chain) {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        PartyPK partyPK = getPartyPK();
        ChainDetailValue chainDetailValue = chainControl.getChainDetailValueForUpdate(chain);
        ChainDescription chainDescription = chainControl.getChainDescriptionForUpdate(chain, getPreferredLanguage());
        String description = edit.getDescription();

        chainDetailValue.setChainName(edit.getChainName());
        chainDetailValue.setChainInstanceSequencePK(chainInstanceSequence == null ? null : chainInstanceSequence.getPrimaryKey());
        chainDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        chainDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainFromValue(chainDetailValue, partyPK);

        if(chainDescription == null && description != null) {
            chainControl.createChainDescription(chain, getPreferredLanguage(), description, partyPK);
        } else if(chainDescription != null && description == null) {
            chainControl.deleteChainDescription(chainDescription, partyPK);
        } else if(chainDescription != null && description != null) {
            ChainDescriptionValue chainDescriptionValue = chainControl.getChainDescriptionValue(chainDescription);

            chainDescriptionValue.setDescription(description);
            chainControl.updateChainDescriptionFromValue(chainDescriptionValue, partyPK);
        }
    }

}
