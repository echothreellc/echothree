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
import com.echothree.control.user.chain.common.edit.ChainKindEdit;
import com.echothree.control.user.chain.common.form.EditChainKindForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainKindResult;
import com.echothree.control.user.chain.common.spec.ChainKindSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
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
public class EditChainKindCommand
        extends BaseAbstractEditCommand<ChainKindSpec, ChainKindEdit, EditChainKindResult, ChainKind, ChainKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainKindCommand */
    public EditChainKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainKindResult getResult() {
        return ChainResultFactory.getEditChainKindResult();
    }

    @Override
    public ChainKindEdit getEdit() {
        return ChainEditFactory.getChainKindEdit();
    }

    @Override
    public ChainKind getEntity(EditChainKindResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainKind chainKind;
        var chainKindName = spec.getChainKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            chainKind = chainControl.getChainKindByName(chainKindName);
        } else { // EditMode.UPDATE
            chainKind = chainControl.getChainKindByNameForUpdate(chainKindName);
        }

        if(chainKind == null) {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainKind;
    }

    @Override
    public ChainKind getLockEntity(ChainKind chainKind) {
        return chainKind;
    }

    @Override
    public void fillInResult(EditChainKindResult result, ChainKind chainKind) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainKind(chainControl.getChainKindTransfer(getUserVisit(), chainKind));
    }

    @Override
    public void doLock(ChainKindEdit edit, ChainKind chainKind) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindDescription = chainControl.getChainKindDescription(chainKind, getPreferredLanguage());
        var chainKindDetail = chainKind.getLastDetail();

        edit.setChainKindName(chainKindDetail.getChainKindName());
        edit.setIsDefault(chainKindDetail.getIsDefault().toString());
        edit.setSortOrder(chainKindDetail.getSortOrder().toString());

        if(chainKindDescription != null) {
            edit.setDescription(chainKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ChainKind chainKind) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindName = edit.getChainKindName();
        var duplicateChainKind = chainControl.getChainKindByName(chainKindName);

        if(duplicateChainKind != null && !chainKind.equals(duplicateChainKind)) {
            addExecutionError(ExecutionErrors.DuplicateChainKindName.name(), chainKindName);
        }
    }

    @Override
    public void doUpdate(ChainKind chainKind) {
        var chainControl = Session.getModelController(ChainControl.class);
        var partyPK = getPartyPK();
        var chainKindDetailValue = chainControl.getChainKindDetailValueForUpdate(chainKind);
        var chainKindDescription = chainControl.getChainKindDescriptionForUpdate(chainKind, getPreferredLanguage());
        var description = edit.getDescription();

        chainKindDetailValue.setChainKindName(edit.getChainKindName());
        chainKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        chainKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        chainControl.updateChainKindFromValue(chainKindDetailValue, partyPK);

        if(chainKindDescription == null && description != null) {
            chainControl.createChainKindDescription(chainKind, getPreferredLanguage(), description, partyPK);
        } else if(chainKindDescription != null && description == null) {
            chainControl.deleteChainKindDescription(chainKindDescription, partyPK);
        } else if(chainKindDescription != null && description != null) {
            var chainKindDescriptionValue = chainControl.getChainKindDescriptionValue(chainKindDescription);

            chainKindDescriptionValue.setDescription(description);
            chainControl.updateChainKindDescriptionFromValue(chainKindDescriptionValue, partyPK);
        }
    }

}
