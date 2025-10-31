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

import com.echothree.control.user.chain.common.edit.ChainActionTypeEdit;
import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.form.EditChainActionTypeForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainActionTypeResult;
import com.echothree.control.user.chain.common.spec.ChainActionTypeSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainActionType;
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
public class EditChainActionTypeCommand
        extends BaseAbstractEditCommand<ChainActionTypeSpec, ChainActionTypeEdit, EditChainActionTypeResult, ChainActionType, ChainActionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainActionType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainActionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AllowMultiple", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainActionTypeCommand */
    public EditChainActionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainActionTypeResult getResult() {
        return ChainResultFactory.getEditChainActionTypeResult();
    }

    @Override
    public ChainActionTypeEdit getEdit() {
        return ChainEditFactory.getChainActionTypeEdit();
    }

    @Override
    public ChainActionType getEntity(EditChainActionTypeResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainActionType chainActionType;
        var chainActionTypeName = spec.getChainActionTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);
        } else { // EditMode.UPDATE
            chainActionType = chainControl.getChainActionTypeByNameForUpdate(chainActionTypeName);
        }

        if(chainActionType == null) {
            addExecutionError(ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
        }

        return chainActionType;
    }

    @Override
    public ChainActionType getLockEntity(ChainActionType chainActionType) {
        return chainActionType;
    }

    @Override
    public void fillInResult(EditChainActionTypeResult result, ChainActionType chainActionType) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainActionType(chainControl.getChainActionTypeTransfer(getUserVisit(), chainActionType));
    }

    @Override
    public void doLock(ChainActionTypeEdit edit, ChainActionType chainActionType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionTypeDescription = chainControl.getChainActionTypeDescription(chainActionType, getPreferredLanguage());
        var chainActionTypeDetail = chainActionType.getLastDetail();

        edit.setChainActionTypeName(chainActionTypeDetail.getChainActionTypeName());
        edit.setAllowMultiple(chainActionTypeDetail.getAllowMultiple().toString());
        edit.setIsDefault(chainActionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(chainActionTypeDetail.getSortOrder().toString());

        if(chainActionTypeDescription != null) {
            edit.setDescription(chainActionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ChainActionType chainActionType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainActionTypeName = edit.getChainActionTypeName();
        var duplicateChainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);

        if(duplicateChainActionType != null && !chainActionType.equals(duplicateChainActionType)) {
            addExecutionError(ExecutionErrors.DuplicateChainActionTypeName.name(), chainActionTypeName);
        }
    }

    @Override
    public void doUpdate(ChainActionType chainActionType) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainActionTypeDetailValue = chainControl.getChainActionTypeDetailValueForUpdate(chainActionType);
        var chainActionTypeDescription = chainControl.getChainActionTypeDescriptionForUpdate(chainActionType, getPreferredLanguage());
        var description = edit.getDescription();

        chainActionTypeDetailValue.setChainActionTypeName(edit.getChainActionTypeName());
        chainActionTypeDetailValue.setAllowMultiple(Boolean.valueOf(edit.getAllowMultiple()));
        chainActionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        chainActionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainActionTypeFromValue(chainActionTypeDetailValue, partyPK);

        if(chainActionTypeDescription == null && description != null) {
            chainControl.createChainActionTypeDescription(chainActionType, getPreferredLanguage(), description, partyPK);
        } else if(chainActionTypeDescription != null && description == null) {
            chainControl.deleteChainActionTypeDescription(chainActionTypeDescription, partyPK);
        } else if(chainActionTypeDescription != null && description != null) {
            var chainActionTypeDescriptionValue = chainControl.getChainActionTypeDescriptionValue(chainActionTypeDescription);

            chainActionTypeDescriptionValue.setDescription(description);
            chainControl.updateChainActionTypeDescriptionFromValue(chainActionTypeDescriptionValue, partyPK);
        }
    }

}
