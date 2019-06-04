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

import com.echothree.control.user.chain.common.edit.ChainEditFactory;
import com.echothree.control.user.chain.common.edit.ChainEntityRoleTypeDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainEntityRoleTypeDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainEntityRoleTypeDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainEntityRoleTypeDescriptionSpec;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDescription;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.value.ChainEntityRoleTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditChainEntityRoleTypeDescriptionCommand
        extends BaseAbstractEditCommand<ChainEntityRoleTypeDescriptionSpec, ChainEntityRoleTypeDescriptionEdit, EditChainEntityRoleTypeDescriptionResult, ChainEntityRoleTypeDescription, ChainEntityRoleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainEntityRoleType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditChainEntityRoleTypeDescriptionCommand */
    public EditChainEntityRoleTypeDescriptionCommand(UserVisitPK userVisitPK, EditChainEntityRoleTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainEntityRoleTypeDescriptionResult getResult() {
        return ChainResultFactory.getEditChainEntityRoleTypeDescriptionResult();
    }

    @Override
    public ChainEntityRoleTypeDescriptionEdit getEdit() {
        return ChainEditFactory.getChainEntityRoleTypeDescriptionEdit();
    }

    @Override
    public ChainEntityRoleTypeDescription getEntity(EditChainEntityRoleTypeDescriptionResult result) {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainEntityRoleTypeDescription chainEntityRoleTypeDescription = null;
        String chainKindName = spec.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            String chainTypeName = spec.getChainTypeName();
            ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                String chainEntityRoleTypeName = spec.getChainEntityRoleTypeName();
                ChainEntityRoleType chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);

                if(chainEntityRoleType != null) {
                    var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                    String languageIsoName = spec.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            chainEntityRoleTypeDescription = chainControl.getChainEntityRoleTypeDescription(chainEntityRoleType, language);
                        } else { // EditMode.UPDATE
                            chainEntityRoleTypeDescription = chainControl.getChainEntityRoleTypeDescriptionForUpdate(chainEntityRoleType, language);
                        }

                        if(chainEntityRoleTypeDescription == null) {
                            addExecutionError(ExecutionErrors.UnknownChainEntityRoleTypeDescription.name(), chainKindName, chainTypeName, chainEntityRoleTypeName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainEntityRoleTypeName.name(), chainKindName, chainTypeName, chainEntityRoleTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainEntityRoleTypeDescription;
    }

    @Override
    public ChainEntityRoleType getLockEntity(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        return chainEntityRoleTypeDescription.getChainEntityRoleType();
    }

    @Override
    public void fillInResult(EditChainEntityRoleTypeDescriptionResult result, ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);

        result.setChainEntityRoleTypeDescription(chainControl.getChainEntityRoleTypeDescriptionTransfer(getUserVisit(), chainEntityRoleTypeDescription));
    }

    @Override
    public void doLock(ChainEntityRoleTypeDescriptionEdit edit, ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        edit.setDescription(chainEntityRoleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        ChainEntityRoleTypeDescriptionValue chainEntityRoleTypeDescriptionValue = chainControl.getChainEntityRoleTypeDescriptionValue(chainEntityRoleTypeDescription);

        chainEntityRoleTypeDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainEntityRoleTypeDescriptionFromValue(chainEntityRoleTypeDescriptionValue, getPartyPK());
    }

}
