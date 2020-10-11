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
import com.echothree.control.user.core.common.edit.FontWeightEdit;
import com.echothree.control.user.core.common.form.EditFontWeightForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontWeightResult;
import com.echothree.control.user.core.common.spec.FontWeightSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDescription;
import com.echothree.model.data.core.server.entity.FontWeightDetail;
import com.echothree.model.data.core.server.value.FontWeightDescriptionValue;
import com.echothree.model.data.core.server.value.FontWeightDetailValue;
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

public class EditFontWeightCommand
        extends BaseAbstractEditCommand<FontWeightSpec, FontWeightEdit, EditFontWeightResult, FontWeight, FontWeight> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FontWeight.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontWeightName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontWeightName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditFontWeightCommand */
    public EditFontWeightCommand(UserVisitPK userVisitPK, EditFontWeightForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFontWeightResult getResult() {
        return CoreResultFactory.getEditFontWeightResult();
    }

    @Override
    public FontWeightEdit getEdit() {
        return CoreEditFactory.getFontWeightEdit();
    }

    @Override
    public FontWeight getEntity(EditFontWeightResult result) {
        var coreControl = getCoreControl();
        FontWeight fontWeight;
        String fontWeightName = spec.getFontWeightName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            fontWeight = coreControl.getFontWeightByName(fontWeightName);
        } else { // EditMode.UPDATE
            fontWeight = coreControl.getFontWeightByNameForUpdate(fontWeightName);
        }

        if(fontWeight == null) {
            addExecutionError(ExecutionErrors.UnknownFontWeightName.name(), fontWeightName);
        }

        return fontWeight;
    }

    @Override
    public FontWeight getLockEntity(FontWeight fontWeight) {
        return fontWeight;
    }

    @Override
    public void fillInResult(EditFontWeightResult result, FontWeight fontWeight) {
        var coreControl = getCoreControl();

        result.setFontWeight(coreControl.getFontWeightTransfer(getUserVisit(), fontWeight));
    }

    @Override
    public void doLock(FontWeightEdit edit, FontWeight fontWeight) {
        var coreControl = getCoreControl();
        FontWeightDescription fontWeightDescription = coreControl.getFontWeightDescription(fontWeight, getPreferredLanguage());
        FontWeightDetail fontWeightDetail = fontWeight.getLastDetail();

        edit.setFontWeightName(fontWeightDetail.getFontWeightName());
        edit.setIsDefault(fontWeightDetail.getIsDefault().toString());
        edit.setSortOrder(fontWeightDetail.getSortOrder().toString());

        if(fontWeightDescription != null) {
            edit.setDescription(fontWeightDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FontWeight fontWeight) {
        var coreControl = getCoreControl();
        String fontWeightName = edit.getFontWeightName();
        FontWeight duplicateFontWeight = coreControl.getFontWeightByName(fontWeightName);

        if(duplicateFontWeight != null && !fontWeight.equals(duplicateFontWeight)) {
            addExecutionError(ExecutionErrors.DuplicateFontWeightName.name(), fontWeightName);
        }
    }

    @Override
    public void doUpdate(FontWeight fontWeight) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        FontWeightDetailValue fontWeightDetailValue = coreControl.getFontWeightDetailValueForUpdate(fontWeight);
        FontWeightDescription fontWeightDescription = coreControl.getFontWeightDescriptionForUpdate(fontWeight, getPreferredLanguage());
        String description = edit.getDescription();

        fontWeightDetailValue.setFontWeightName(edit.getFontWeightName());
        fontWeightDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        fontWeightDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateFontWeightFromValue(fontWeightDetailValue, partyPK);

        if(fontWeightDescription == null && description != null) {
            coreControl.createFontWeightDescription(fontWeight, getPreferredLanguage(), description, partyPK);
        } else {
            if(fontWeightDescription != null && description == null) {
                coreControl.deleteFontWeightDescription(fontWeightDescription, partyPK);
            } else {
                if(fontWeightDescription != null && description != null) {
                    FontWeightDescriptionValue fontWeightDescriptionValue = coreControl.getFontWeightDescriptionValue(fontWeightDescription);

                    fontWeightDescriptionValue.setDescription(description);
                    coreControl.updateFontWeightDescriptionFromValue(fontWeightDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
