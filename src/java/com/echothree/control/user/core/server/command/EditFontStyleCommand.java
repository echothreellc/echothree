// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.core.common.edit.FontStyleEdit;
import com.echothree.control.user.core.common.form.EditFontStyleForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontStyleResult;
import com.echothree.control.user.core.common.spec.FontStyleSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDescription;
import com.echothree.model.data.core.server.entity.FontStyleDetail;
import com.echothree.model.data.core.server.value.FontStyleDescriptionValue;
import com.echothree.model.data.core.server.value.FontStyleDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditFontStyleCommand
        extends BaseAbstractEditCommand<FontStyleSpec, FontStyleEdit, EditFontStyleResult, FontStyle, FontStyle> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FontStyle.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditFontStyleCommand */
    public EditFontStyleCommand(UserVisitPK userVisitPK, EditFontStyleForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFontStyleResult getResult() {
        return CoreResultFactory.getEditFontStyleResult();
    }

    @Override
    public FontStyleEdit getEdit() {
        return CoreEditFactory.getFontStyleEdit();
    }

    @Override
    public FontStyle getEntity(EditFontStyleResult result) {
        var coreControl = getCoreControl();
        FontStyle fontStyle;
        String fontStyleName = spec.getFontStyleName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            fontStyle = coreControl.getFontStyleByName(fontStyleName);
        } else { // EditMode.UPDATE
            fontStyle = coreControl.getFontStyleByNameForUpdate(fontStyleName);
        }

        if(fontStyle == null) {
            addExecutionError(ExecutionErrors.UnknownFontStyleName.name(), fontStyleName);
        }

        return fontStyle;
    }

    @Override
    public FontStyle getLockEntity(FontStyle fontStyle) {
        return fontStyle;
    }

    @Override
    public void fillInResult(EditFontStyleResult result, FontStyle fontStyle) {
        var coreControl = getCoreControl();

        result.setFontStyle(coreControl.getFontStyleTransfer(getUserVisit(), fontStyle));
    }

    @Override
    public void doLock(FontStyleEdit edit, FontStyle fontStyle) {
        var coreControl = getCoreControl();
        FontStyleDescription fontStyleDescription = coreControl.getFontStyleDescription(fontStyle, getPreferredLanguage());
        FontStyleDetail fontStyleDetail = fontStyle.getLastDetail();

        edit.setFontStyleName(fontStyleDetail.getFontStyleName());
        edit.setIsDefault(fontStyleDetail.getIsDefault().toString());
        edit.setSortOrder(fontStyleDetail.getSortOrder().toString());

        if(fontStyleDescription != null) {
            edit.setDescription(fontStyleDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FontStyle fontStyle) {
        var coreControl = getCoreControl();
        String fontStyleName = edit.getFontStyleName();
        FontStyle duplicateFontStyle = coreControl.getFontStyleByName(fontStyleName);

        if(duplicateFontStyle != null && !fontStyle.equals(duplicateFontStyle)) {
            addExecutionError(ExecutionErrors.DuplicateFontStyleName.name(), fontStyleName);
        }
    }

    @Override
    public void doUpdate(FontStyle fontStyle) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        FontStyleDetailValue fontStyleDetailValue = coreControl.getFontStyleDetailValueForUpdate(fontStyle);
        FontStyleDescription fontStyleDescription = coreControl.getFontStyleDescriptionForUpdate(fontStyle, getPreferredLanguage());
        String description = edit.getDescription();

        fontStyleDetailValue.setFontStyleName(edit.getFontStyleName());
        fontStyleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        fontStyleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateFontStyleFromValue(fontStyleDetailValue, partyPK);

        if(fontStyleDescription == null && description != null) {
            coreControl.createFontStyleDescription(fontStyle, getPreferredLanguage(), description, partyPK);
        } else {
            if(fontStyleDescription != null && description == null) {
                coreControl.deleteFontStyleDescription(fontStyleDescription, partyPK);
            } else {
                if(fontStyleDescription != null && description != null) {
                    FontStyleDescriptionValue fontStyleDescriptionValue = coreControl.getFontStyleDescriptionValue(fontStyleDescription);

                    fontStyleDescriptionValue.setDescription(description);
                    coreControl.updateFontStyleDescriptionFromValue(fontStyleDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
