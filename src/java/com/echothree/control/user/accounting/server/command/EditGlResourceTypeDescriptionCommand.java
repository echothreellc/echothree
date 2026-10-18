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
import com.echothree.control.user.accounting.common.edit.GlResourceTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlResourceTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.GlResourceTypeDescriptionSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.GlResourceTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDescription;
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
public class EditGlResourceTypeDescriptionCommand
        extends BaseAbstractEditCommand<GlResourceTypeDescriptionSpec, GlResourceTypeDescriptionEdit, EditGlResourceTypeDescriptionResult, GlResourceTypeDescription, GlResourceType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlResourceType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    AccountingControl accountingControl;

    @Inject
    GlResourceTypeLogic glResourceTypeLogic;

    /** Creates a new instance of EditGlResourceTypeDescriptionCommand */
    public EditGlResourceTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGlResourceTypeDescriptionResult getResult() {
        return AccountingResultFactory.getEditGlResourceTypeDescriptionResult();
    }

    @Override
    public GlResourceTypeDescriptionEdit getEdit() {
        return AccountingEditFactory.getGlResourceTypeDescriptionEdit();
    }

    @Override
    public GlResourceTypeDescription getEntity(EditGlResourceTypeDescriptionResult result) {
        return glResourceTypeLogic.getGlResourceTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public GlResourceType getLockEntity(GlResourceTypeDescription glResourceTypeDescription) {
        return glResourceTypeDescription.getGlResourceType();
    }

    @Override
    public void fillInResult(EditGlResourceTypeDescriptionResult result, GlResourceTypeDescription glResourceTypeDescription) {
        result.setGlResourceTypeDescription(accountingControl.getGlResourceTypeDescriptionTransfer(getUserVisit(), glResourceTypeDescription));
    }

    @Override
    public void doLock(GlResourceTypeDescriptionEdit edit, GlResourceTypeDescription glResourceTypeDescription) {
        edit.setDescription(glResourceTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(GlResourceTypeDescription glResourceTypeDescription) {
        var glResourceTypeDescriptionValue = accountingControl.getGlResourceTypeDescriptionValue(glResourceTypeDescription);

        glResourceTypeDescriptionValue.setDescription(edit.getDescription());

        accountingControl.updateGlResourceTypeDescriptionFromValue(glResourceTypeDescriptionValue, getPartyPK());
    }

}
