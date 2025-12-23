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
import com.echothree.control.user.chain.common.edit.ChainKindDescriptionEdit;
import com.echothree.control.user.chain.common.form.EditChainKindDescriptionForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.control.user.chain.common.result.EditChainKindDescriptionResult;
import com.echothree.control.user.chain.common.spec.ChainKindDescriptionSpec;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainKindDescription;
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
public class EditChainKindDescriptionCommand
        extends BaseAbstractEditCommand<ChainKindDescriptionSpec, ChainKindDescriptionEdit, EditChainKindDescriptionResult, ChainKindDescription, ChainKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditChainKindDescriptionCommand */
    public EditChainKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditChainKindDescriptionResult getResult() {
        return ChainResultFactory.getEditChainKindDescriptionResult();
    }

    @Override
    public ChainKindDescriptionEdit getEdit() {
        return ChainEditFactory.getChainKindDescriptionEdit();
    }

    @Override
    public ChainKindDescription getEntity(EditChainKindDescriptionResult result) {
        var chainControl = Session.getModelController(ChainControl.class);
        ChainKindDescription chainKindDescription = null;
        var chainKindName = spec.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    chainKindDescription = chainControl.getChainKindDescription(chainKind, language);
                } else { // EditMode.UPDATE
                    chainKindDescription = chainControl.getChainKindDescriptionForUpdate(chainKind, language);
                }

                if(chainKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownChainKindDescription.name(), chainKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return chainKindDescription;
    }

    @Override
    public ChainKind getLockEntity(ChainKindDescription chainKindDescription) {
        return chainKindDescription.getChainKind();
    }

    @Override
    public void fillInResult(EditChainKindDescriptionResult result, ChainKindDescription chainKindDescription) {
        var chainControl = Session.getModelController(ChainControl.class);

        result.setChainKindDescription(chainControl.getChainKindDescriptionTransfer(getUserVisit(), chainKindDescription));
    }

    @Override
    public void doLock(ChainKindDescriptionEdit edit, ChainKindDescription chainKindDescription) {
        edit.setDescription(chainKindDescription.getDescription());
    }

    @Override
    public void doUpdate(ChainKindDescription chainKindDescription) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindDescriptionValue = chainControl.getChainKindDescriptionValue(chainKindDescription);

        chainKindDescriptionValue.setDescription(edit.getDescription());

        chainControl.updateChainKindDescriptionFromValue(chainKindDescriptionValue, getPartyPK());
    }

}
