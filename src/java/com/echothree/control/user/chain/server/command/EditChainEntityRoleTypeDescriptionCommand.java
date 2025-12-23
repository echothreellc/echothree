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
import com.echothree.control.user.chain.common.edit.ChainEntityRoleTypeDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainEntityRoleTypeDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainEntityRoleTypeDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainEntityRoleTypeDescriptionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDescription;
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
public class EditChainEntityRoleTypeDescriptionCommand
        extends BaseAbstractEditCommand<ChainEntityRoleTypeDescriptionSpec, ChainEntityRoleTypeDescriptionEdit, EditChainEntityRoleTypeDescriptionResult, ChainEntityRoleTypeDescription, ChainEntityRoleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainEntityRoleTypeDescriptionCommand */
    public EditChainEntityRoleTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var chainControl = Session.getModelController(ChainControl.class);
        ChainEntityRoleTypeDescription chainEntityRoleTypeDescription = null;
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = spec.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var chainEntityRoleTypeName = spec.getChainEntityRoleTypeName();
                var chainEntityRoleType = chainControl.getChainEntityRoleTypeByName(chainType, chainEntityRoleTypeName);

                if(chainEntityRoleType != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

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
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainEntityRoleTypeDescription(chainControl.getChainEntityRoleTypeDescriptionTransfer(getUserVisit(), chainEntityRoleTypeDescription));
    }

    @Override
    public void doLock(ChainEntityRoleTypeDescriptionEdit edit, ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        edit.setDescription(chainEntityRoleTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainEntityRoleTypeDescriptionValue = chainControl.getChainEntityRoleTypeDescriptionValue(chainEntityRoleTypeDescription);

        chainEntityRoleTypeDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainEntityRoleTypeDescriptionFromValue(chainEntityRoleTypeDescriptionValue, getPartyPK());
    }

}
