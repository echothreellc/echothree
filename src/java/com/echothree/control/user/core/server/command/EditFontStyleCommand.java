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
import com.echothree.control.user.core.common.edit.FontStyleEdit;
import com.echothree.control.user.core.common.form.EditFontStyleForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontStyleResult;
import com.echothree.control.user.core.common.spec.FontStyleSpec;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFontStyleCommand */
    public EditFontStyleCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var fontControl = Session.getModelController(FontControl.class);
        FontStyle fontStyle;
        var fontStyleName = spec.getFontStyleName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            fontStyle = fontControl.getFontStyleByName(fontStyleName);
        } else { // EditMode.UPDATE
            fontStyle = fontControl.getFontStyleByNameForUpdate(fontStyleName);
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
        var fontControl = Session.getModelController(FontControl.class);

        result.setFontStyle(fontControl.getFontStyleTransfer(getUserVisit(), fontStyle));
    }

    @Override
    public void doLock(FontStyleEdit edit, FontStyle fontStyle) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontStyleDescription = fontControl.getFontStyleDescription(fontStyle, getPreferredLanguage());
        var fontStyleDetail = fontStyle.getLastDetail();

        edit.setFontStyleName(fontStyleDetail.getFontStyleName());
        edit.setIsDefault(fontStyleDetail.getIsDefault().toString());
        edit.setSortOrder(fontStyleDetail.getSortOrder().toString());

        if(fontStyleDescription != null) {
            edit.setDescription(fontStyleDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FontStyle fontStyle) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontStyleName = edit.getFontStyleName();
        var duplicateFontStyle = fontControl.getFontStyleByName(fontStyleName);

        if(duplicateFontStyle != null && !fontStyle.equals(duplicateFontStyle)) {
            addExecutionError(ExecutionErrors.DuplicateFontStyleName.name(), fontStyleName);
        }
    }

    @Override
    public void doUpdate(FontStyle fontStyle) {
        var fontControl = Session.getModelController(FontControl.class);
        var partyPK = getPartyPK();
        var fontStyleDetailValue = fontControl.getFontStyleDetailValueForUpdate(fontStyle);
        var fontStyleDescription = fontControl.getFontStyleDescriptionForUpdate(fontStyle, getPreferredLanguage());
        var description = edit.getDescription();

        fontStyleDetailValue.setFontStyleName(edit.getFontStyleName());
        fontStyleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        fontStyleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        fontControl.updateFontStyleFromValue(fontStyleDetailValue, partyPK);

        if(fontStyleDescription == null && description != null) {
            fontControl.createFontStyleDescription(fontStyle, getPreferredLanguage(), description, partyPK);
        } else {
            if(fontStyleDescription != null && description == null) {
                fontControl.deleteFontStyleDescription(fontStyleDescription, partyPK);
            } else {
                if(fontStyleDescription != null && description != null) {
                    var fontStyleDescriptionValue = fontControl.getFontStyleDescriptionValue(fontStyleDescription);

                    fontStyleDescriptionValue.setDescription(description);
                    fontControl.updateFontStyleDescriptionFromValue(fontStyleDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
