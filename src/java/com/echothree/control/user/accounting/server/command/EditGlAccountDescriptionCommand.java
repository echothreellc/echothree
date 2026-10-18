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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.GlAccountDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountDescriptionResult;
import com.echothree.control.user.accounting.common.spec.GlAccountDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGlAccountDescriptionCommand
        extends BaseAbstractEditCommand<GlAccountDescriptionSpec, GlAccountDescriptionEdit, EditGlAccountDescriptionResult, GlAccountDescription, GlAccount> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountLogic glAccountLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditGlAccountDescriptionCommand */
    public EditGlAccountDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlAccountDescriptionResult getResult() {
        return AccountingResultFactory.getEditGlAccountDescriptionResult();
    }

    @Override
    public GlAccountDescriptionEdit getEdit() {
        return AccountingEditFactory.getGlAccountDescriptionEdit();
    }

    @Override
    public GlAccountDescription getEntity(EditGlAccountDescriptionResult result) {
        GlAccountDescription glAccountDescription = null;
        var glAccountName = spec.getGlAccountName();
        var glAccount = glAccountLogic.getGlAccountByName(this, glAccountName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                glAccountDescription = accountingControl.getGlAccountDescription(glAccount, language, editModeToEntityPermission(editMode));

                if(glAccountDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownGlAccountDescription.name(), glAccountName, languageIsoName);
                }
            }
        }

        return glAccountDescription;
    }

    @Override
    public GlAccount getLockEntity(GlAccountDescription glAccountDescription) {
        return glAccountDescription.getGlAccount();
    }

    @Override
    public void fillInResult(EditGlAccountDescriptionResult result, GlAccountDescription glAccountDescription) {
        result.setGlAccountDescription(accountingControl.getGlAccountDescriptionTransfer(getUserVisit(), glAccountDescription));
    }

    @Override
    public void doLock(GlAccountDescriptionEdit edit, GlAccountDescription glAccountDescription) {
        edit.setDescription(glAccountDescription.getDescription());
    }

    @Override
    public void doUpdate(GlAccountDescription glAccountDescription) {
        var glAccountDescriptionValue = accountingControl.getGlAccountDescriptionValue(glAccountDescription);

        glAccountDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateGlAccountDescriptionFromValue(glAccountDescriptionValue, getPartyPK());
    }

}
