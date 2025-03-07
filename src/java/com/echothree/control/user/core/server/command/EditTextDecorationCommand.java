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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.TextDecorationEdit;
import com.echothree.control.user.core.common.form.EditTextDecorationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditTextDecorationResult;
import com.echothree.control.user.core.common.spec.TextDecorationSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTextDecorationCommand
        extends BaseAbstractEditCommand<TextDecorationSpec, TextDecorationEdit, EditTextDecorationResult, TextDecoration, TextDecoration> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TextDecoration.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextDecorationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTextDecorationCommand */
    public EditTextDecorationCommand(UserVisitPK userVisitPK, EditTextDecorationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTextDecorationResult getResult() {
        return CoreResultFactory.getEditTextDecorationResult();
    }

    @Override
    public TextDecorationEdit getEdit() {
        return CoreEditFactory.getTextDecorationEdit();
    }

    @Override
    public TextDecoration getEntity(EditTextDecorationResult result) {
        var coreControl = getCoreControl();
        TextDecoration textDecoration;
        var textDecorationName = spec.getTextDecorationName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            textDecoration = coreControl.getTextDecorationByName(textDecorationName);
        } else { // EditMode.UPDATE
            textDecoration = coreControl.getTextDecorationByNameForUpdate(textDecorationName);
        }

        if(textDecoration == null) {
            addExecutionError(ExecutionErrors.UnknownTextDecorationName.name(), textDecorationName);
        }

        return textDecoration;
    }

    @Override
    public TextDecoration getLockEntity(TextDecoration textDecoration) {
        return textDecoration;
    }

    @Override
    public void fillInResult(EditTextDecorationResult result, TextDecoration textDecoration) {
        var coreControl = getCoreControl();

        result.setTextDecoration(coreControl.getTextDecorationTransfer(getUserVisit(), textDecoration));
    }

    @Override
    public void doLock(TextDecorationEdit edit, TextDecoration textDecoration) {
        var coreControl = getCoreControl();
        var textDecorationDescription = coreControl.getTextDecorationDescription(textDecoration, getPreferredLanguage());
        var textDecorationDetail = textDecoration.getLastDetail();

        edit.setTextDecorationName(textDecorationDetail.getTextDecorationName());
        edit.setIsDefault(textDecorationDetail.getIsDefault().toString());
        edit.setSortOrder(textDecorationDetail.getSortOrder().toString());

        if(textDecorationDescription != null) {
            edit.setDescription(textDecorationDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TextDecoration textDecoration) {
        var coreControl = getCoreControl();
        var textDecorationName = edit.getTextDecorationName();
        var duplicateTextDecoration = coreControl.getTextDecorationByName(textDecorationName);

        if(duplicateTextDecoration != null && !textDecoration.equals(duplicateTextDecoration)) {
            addExecutionError(ExecutionErrors.DuplicateTextDecorationName.name(), textDecorationName);
        }
    }

    @Override
    public void doUpdate(TextDecoration textDecoration) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        var textDecorationDetailValue = coreControl.getTextDecorationDetailValueForUpdate(textDecoration);
        var textDecorationDescription = coreControl.getTextDecorationDescriptionForUpdate(textDecoration, getPreferredLanguage());
        var description = edit.getDescription();

        textDecorationDetailValue.setTextDecorationName(edit.getTextDecorationName());
        textDecorationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        textDecorationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateTextDecorationFromValue(textDecorationDetailValue, partyPK);

        if(textDecorationDescription == null && description != null) {
            coreControl.createTextDecorationDescription(textDecoration, getPreferredLanguage(), description, partyPK);
        } else {
            if(textDecorationDescription != null && description == null) {
                coreControl.deleteTextDecorationDescription(textDecorationDescription, partyPK);
            } else {
                if(textDecorationDescription != null && description != null) {
                    var textDecorationDescriptionValue = coreControl.getTextDecorationDescriptionValue(textDecorationDescription);

                    textDecorationDescriptionValue.setDescription(description);
                    coreControl.updateTextDecorationDescriptionFromValue(textDecorationDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
