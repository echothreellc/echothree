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
import com.echothree.control.user.core.common.edit.TextTransformationEdit;
import com.echothree.control.user.core.common.form.EditTextTransformationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditTextTransformationResult;
import com.echothree.control.user.core.common.spec.TextTransformationSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.TextTransformation;
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

public class EditTextTransformationCommand
        extends BaseAbstractEditCommand<TextTransformationSpec, TextTransformationEdit, EditTextTransformationResult, TextTransformation, TextTransformation> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TextTransformation.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTextTransformationCommand */
    public EditTextTransformationCommand(UserVisitPK userVisitPK, EditTextTransformationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTextTransformationResult getResult() {
        return CoreResultFactory.getEditTextTransformationResult();
    }

    @Override
    public TextTransformationEdit getEdit() {
        return CoreEditFactory.getTextTransformationEdit();
    }

    @Override
    public TextTransformation getEntity(EditTextTransformationResult result) {
        var coreControl = getCoreControl();
        TextTransformation textTransformation;
        var textTransformationName = spec.getTextTransformationName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            textTransformation = coreControl.getTextTransformationByName(textTransformationName);
        } else { // EditMode.UPDATE
            textTransformation = coreControl.getTextTransformationByNameForUpdate(textTransformationName);
        }

        if(textTransformation == null) {
            addExecutionError(ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformation;
    }

    @Override
    public TextTransformation getLockEntity(TextTransformation textTransformation) {
        return textTransformation;
    }

    @Override
    public void fillInResult(EditTextTransformationResult result, TextTransformation textTransformation) {
        var coreControl = getCoreControl();

        result.setTextTransformation(coreControl.getTextTransformationTransfer(getUserVisit(), textTransformation));
    }

    @Override
    public void doLock(TextTransformationEdit edit, TextTransformation textTransformation) {
        var coreControl = getCoreControl();
        var textTransformationDescription = coreControl.getTextTransformationDescription(textTransformation, getPreferredLanguage());
        var textTransformationDetail = textTransformation.getLastDetail();

        edit.setTextTransformationName(textTransformationDetail.getTextTransformationName());
        edit.setIsDefault(textTransformationDetail.getIsDefault().toString());
        edit.setSortOrder(textTransformationDetail.getSortOrder().toString());

        if(textTransformationDescription != null) {
            edit.setDescription(textTransformationDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(TextTransformation textTransformation) {
        var coreControl = getCoreControl();
        var textTransformationName = edit.getTextTransformationName();
        var duplicateTextTransformation = coreControl.getTextTransformationByName(textTransformationName);

        if(duplicateTextTransformation != null && !textTransformation.equals(duplicateTextTransformation)) {
            addExecutionError(ExecutionErrors.DuplicateTextTransformationName.name(), textTransformationName);
        }
    }

    @Override
    public void doUpdate(TextTransformation textTransformation) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        var textTransformationDetailValue = coreControl.getTextTransformationDetailValueForUpdate(textTransformation);
        var textTransformationDescription = coreControl.getTextTransformationDescriptionForUpdate(textTransformation, getPreferredLanguage());
        var description = edit.getDescription();

        textTransformationDetailValue.setTextTransformationName(edit.getTextTransformationName());
        textTransformationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        textTransformationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateTextTransformationFromValue(textTransformationDetailValue, partyPK);

        if(textTransformationDescription == null && description != null) {
            coreControl.createTextTransformationDescription(textTransformation, getPreferredLanguage(), description, partyPK);
        } else {
            if(textTransformationDescription != null && description == null) {
                coreControl.deleteTextTransformationDescription(textTransformationDescription, partyPK);
            } else {
                if(textTransformationDescription != null && description != null) {
                    var textTransformationDescriptionValue = coreControl.getTextTransformationDescriptionValue(textTransformationDescription);

                    textTransformationDescriptionValue.setDescription(description);
                    coreControl.updateTextTransformationDescriptionFromValue(textTransformationDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
