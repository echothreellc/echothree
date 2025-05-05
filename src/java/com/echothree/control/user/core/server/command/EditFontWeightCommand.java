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
import com.echothree.control.user.core.common.edit.FontWeightEdit;
import com.echothree.control.user.core.common.form.EditFontWeightForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontWeightResult;
import com.echothree.control.user.core.common.spec.FontWeightSpec;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontWeight;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFontWeightCommand */
    public EditFontWeightCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var fontControl = Session.getModelController(FontControl.class);
        FontWeight fontWeight;
        var fontWeightName = spec.getFontWeightName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            fontWeight = fontControl.getFontWeightByName(fontWeightName);
        } else { // EditMode.UPDATE
            fontWeight = fontControl.getFontWeightByNameForUpdate(fontWeightName);
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
        var fontControl = Session.getModelController(FontControl.class);

        result.setFontWeight(fontControl.getFontWeightTransfer(getUserVisit(), fontWeight));
    }

    @Override
    public void doLock(FontWeightEdit edit, FontWeight fontWeight) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontWeightDescription = fontControl.getFontWeightDescription(fontWeight, getPreferredLanguage());
        var fontWeightDetail = fontWeight.getLastDetail();

        edit.setFontWeightName(fontWeightDetail.getFontWeightName());
        edit.setIsDefault(fontWeightDetail.getIsDefault().toString());
        edit.setSortOrder(fontWeightDetail.getSortOrder().toString());

        if(fontWeightDescription != null) {
            edit.setDescription(fontWeightDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(FontWeight fontWeight) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontWeightName = edit.getFontWeightName();
        var duplicateFontWeight = fontControl.getFontWeightByName(fontWeightName);

        if(duplicateFontWeight != null && !fontWeight.equals(duplicateFontWeight)) {
            addExecutionError(ExecutionErrors.DuplicateFontWeightName.name(), fontWeightName);
        }
    }

    @Override
    public void doUpdate(FontWeight fontWeight) {
        var fontControl = Session.getModelController(FontControl.class);
        var partyPK = getPartyPK();
        var fontWeightDetailValue = fontControl.getFontWeightDetailValueForUpdate(fontWeight);
        var fontWeightDescription = fontControl.getFontWeightDescriptionForUpdate(fontWeight, getPreferredLanguage());
        var description = edit.getDescription();

        fontWeightDetailValue.setFontWeightName(edit.getFontWeightName());
        fontWeightDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        fontWeightDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        fontControl.updateFontWeightFromValue(fontWeightDetailValue, partyPK);

        if(fontWeightDescription == null && description != null) {
            fontControl.createFontWeightDescription(fontWeight, getPreferredLanguage(), description, partyPK);
        } else {
            if(fontWeightDescription != null && description == null) {
                fontControl.deleteFontWeightDescription(fontWeightDescription, partyPK);
            } else {
                if(fontWeightDescription != null && description != null) {
                    var fontWeightDescriptionValue = fontControl.getFontWeightDescriptionValue(fontWeightDescription);

                    fontWeightDescriptionValue.setDescription(description);
                    fontControl.updateFontWeightDescriptionFromValue(fontWeightDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
