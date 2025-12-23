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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.edit.ChainTypeEdit;
import com.echothree.control.user.chain.common.form.EditChainTypeForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainTypeResult;
import com.echothree.control.user.chain.common.spec.ChainTypeSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
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
public class EditChainTypeCommand
        extends BaseAbstractEditCommand<ChainTypeSpec, ChainTypeEdit, EditChainTypeResult, ChainType, ChainType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainTypeCommand */
    public EditChainTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainTypeResult getResult() {
        return ChainResultFactory.getEditChainTypeResult();
    }

    @Override
    public ChainTypeEdit getEdit() {
        return ChainEditFactory.getChainTypeEdit();
    }

    ChainKind chainKind;

    @Override
    public ChainType getEntity(EditChainTypeResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainType chainType = null;
        var chainKindName = spec.getChainKindName();

        chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
            } else { // EditMode.UPDATE
                chainType = chainControl.getChainTypeByNameForUpdate(chainKind, chainTypeName);
            }

            if(chainType == null) {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainType;
    }

    @Override
    public ChainType getLockEntity(ChainType chainType) {
        return chainType;
    }

    @Override
    public void fillInResult(EditChainTypeResult result, ChainType chainType) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainType(chainControl.getChainTypeTransfer(getUserVisit(), chainType));
    }

    @Override
    public void doLock(ChainTypeEdit edit, ChainType chainType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainTypeDescription = chainControl.getChainTypeDescription(chainType, getPreferredLanguage());
        var chainTypeDetail = chainType.getLastDetail();

        edit.setChainTypeName(chainTypeDetail.getChainTypeName());
        edit.setIsDefault(chainTypeDetail.getIsDefault().toString());
        edit.setSortOrder(chainTypeDetail.getSortOrder().toString());

        if(chainTypeDescription != null) {
            edit.setDescription(chainTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ChainType chainType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindDetail = chainKind.getLastDetail();
        var chainTypeName = edit.getChainTypeName();
        var duplicateChainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

        if(duplicateChainType != null && !chainType.equals(duplicateChainType)) {
            addExecutionError(ExecutionErrors.DuplicateChainTypeName.name(), chainKindDetail.getChainKindName(), chainTypeName);
        }
    }

    @Override
    public void doUpdate(ChainType chainType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainTypeDetailValue = chainControl.getChainTypeDetailValueForUpdate(chainType);
        var chainTypeDescription = chainControl.getChainTypeDescriptionForUpdate(chainType, getPreferredLanguage());
        var description = edit.getDescription();

        chainTypeDetailValue.setChainTypeName(edit.getChainTypeName());
        chainTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        chainTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainTypeFromValue(chainTypeDetailValue, partyPK);

        if(chainTypeDescription == null && description != null) {
            chainControl.createChainTypeDescription(chainType, getPreferredLanguage(), description, partyPK);
        } else if(chainTypeDescription != null && description == null) {
            chainControl.deleteChainTypeDescription(chainTypeDescription, partyPK);
        } else if(chainTypeDescription != null && description != null) {
            var chainTypeDescriptionValue = chainControl.getChainTypeDescriptionValue(chainTypeDescription);

            chainTypeDescriptionValue.setDescription(description);
            chainControl.updateChainTypeDescriptionFromValue(chainTypeDescriptionValue, partyPK);
        }
    }

}
