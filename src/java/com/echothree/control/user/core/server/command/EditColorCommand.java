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

import com.echothree.control.user.core.common.edit.ColorEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditColorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditColorResult;
import com.echothree.control.user.core.common.spec.ColorSpec;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Color;
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

public class EditColorCommand
        extends BaseAbstractEditCommand<ColorSpec, ColorEdit, EditColorResult, Color, Color> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Color.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ColorName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ColorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Red", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("Green", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("Blue", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditColorCommand */
    public EditColorCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditColorResult getResult() {
        return CoreResultFactory.getEditColorResult();
    }

    @Override
    public ColorEdit getEdit() {
        return CoreEditFactory.getColorEdit();
    }

    @Override
    public Color getEntity(EditColorResult result) {
        var colorControl = Session.getModelController(ColorControl.class);
        Color color;
        var colorName = spec.getColorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            color = colorControl.getColorByName(colorName);
        } else { // EditMode.UPDATE
            color = colorControl.getColorByNameForUpdate(colorName);
        }

        if(color == null) {
            addExecutionError(ExecutionErrors.UnknownColorName.name(), colorName);
        }

        return color;
    }

    @Override
    public Color getLockEntity(Color color) {
        return color;
    }

    @Override
    public void fillInResult(EditColorResult result, Color color) {
        var colorControl = Session.getModelController(ColorControl.class);

        result.setColor(colorControl.getColorTransfer(getUserVisit(), color));
    }

    @Override
    public void doLock(ColorEdit edit, Color color) {
        var colorControl = Session.getModelController(ColorControl.class);
        var colorDescription = colorControl.getColorDescription(color, getPreferredLanguage());
        var colorDetail = color.getLastDetail();

        edit.setColorName(colorDetail.getColorName());
        edit.setRed(colorDetail.getRed().toString());
        edit.setGreen(colorDetail.getGreen().toString());
        edit.setBlue(colorDetail.getBlue().toString());
        edit.setIsDefault(colorDetail.getIsDefault().toString());
        edit.setSortOrder(colorDetail.getSortOrder().toString());

        if(colorDescription != null) {
            edit.setDescription(colorDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Color color) {
        var colorControl = Session.getModelController(ColorControl.class);
        var colorName = edit.getColorName();
        var duplicateColor = colorControl.getColorByName(colorName);

        if(duplicateColor != null && !color.equals(duplicateColor)) {
            addExecutionError(ExecutionErrors.DuplicateColorName.name(), colorName);
        }
    }

    @Override
    public void doUpdate(Color color) {
        var colorControl = Session.getModelController(ColorControl.class);
        var partyPK = getPartyPK();
        var colorDetailValue = colorControl.getColorDetailValueForUpdate(color);
        var colorDescription = colorControl.getColorDescriptionForUpdate(color, getPreferredLanguage());
        var description = edit.getDescription();

        colorDetailValue.setColorName(edit.getColorName());
        colorDetailValue.setRed(Integer.valueOf(edit.getRed()));
        colorDetailValue.setGreen(Integer.valueOf(edit.getGreen()));
        colorDetailValue.setBlue(Integer.valueOf(edit.getBlue()));
        colorDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        colorDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        colorControl.updateColorFromValue(colorDetailValue, partyPK);

        if(colorDescription == null && description != null) {
            colorControl.createColorDescription(color, getPreferredLanguage(), description, partyPK);
        } else {
            if(colorDescription != null && description == null) {
                colorControl.deleteColorDescription(colorDescription, partyPK);
            } else {
                if(colorDescription != null && description != null) {
                    var colorDescriptionValue = colorControl.getColorDescriptionValue(colorDescription);

                    colorDescriptionValue.setDescription(description);
                    colorControl.updateColorDescriptionFromValue(colorDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
