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
import com.echothree.control.user.accounting.common.edit.GlAccountClassDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlAccountClassDescriptionResult;
import com.echothree.control.user.accounting.common.spec.GlAccountClassDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlAccountClassLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDescription;
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
public class EditGlAccountClassDescriptionCommand
        extends BaseAbstractEditCommand<GlAccountClassDescriptionSpec, GlAccountClassDescriptionEdit, EditGlAccountClassDescriptionResult, GlAccountClassDescription, GlAccountClass> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccountClass.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlAccountClassLogic glAccountClassLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditGlAccountClassDescriptionCommand */
    public EditGlAccountClassDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlAccountClassDescriptionResult getResult() {
        return AccountingResultFactory.getEditGlAccountClassDescriptionResult();
    }

    @Override
    public GlAccountClassDescriptionEdit getEdit() {
        return AccountingEditFactory.getGlAccountClassDescriptionEdit();
    }

    @Override
    public GlAccountClassDescription getEntity(EditGlAccountClassDescriptionResult result) {
        GlAccountClassDescription glAccountClassDescription = null;
        var glAccountClassName = spec.getGlAccountClassName();
        var glAccountClass = glAccountClassLogic.getGlAccountClassByName(this, glAccountClassName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                glAccountClassDescription = accountingControl.getGlAccountClassDescription(glAccountClass, language, editModeToEntityPermission(editMode));

                if(glAccountClassDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownGlAccountClassDescription.name(), glAccountClassName, languageIsoName);
                }
            }
        }

        return glAccountClassDescription;
    }

    @Override
    public GlAccountClass getLockEntity(GlAccountClassDescription glAccountClassDescription) {
        return glAccountClassDescription.getGlAccountClass();
    }

    @Override
    public void fillInResult(EditGlAccountClassDescriptionResult result, GlAccountClassDescription glAccountClassDescription) {
        result.setGlAccountClassDescription(accountingControl.getGlAccountClassDescriptionTransfer(getUserVisit(), glAccountClassDescription));
    }

    @Override
    public void doLock(GlAccountClassDescriptionEdit edit, GlAccountClassDescription glAccountClassDescription) {
        edit.setDescription(glAccountClassDescription.getDescription());
    }

    @Override
    public void doUpdate(GlAccountClassDescription glAccountClassDescription) {
        var glAccountClassDescriptionValue = accountingControl.getGlAccountClassDescriptionValue(glAccountClassDescription);

        glAccountClassDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateGlAccountClassDescriptionFromValue(glAccountClassDescriptionValue, getPartyPK());
    }

}
