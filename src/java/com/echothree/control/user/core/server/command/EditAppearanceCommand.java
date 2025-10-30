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

import com.echothree.control.user.core.common.edit.AppearanceEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditAppearanceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditAppearanceResult;
import com.echothree.control.user.core.common.spec.AppearanceSpec;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.control.core.server.logic.FontLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.FontStyle;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditAppearanceCommand
        extends BaseAbstractEditCommand<AppearanceSpec, AppearanceEdit, EditAppearanceResult, Appearance, Appearance> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TextColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BackgroundColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FontWeightName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditAppearanceCommand */
    public EditAppearanceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditAppearanceResult getResult() {
        return CoreResultFactory.getEditAppearanceResult();
    }

    @Override
    public AppearanceEdit getEdit() {
        return CoreEditFactory.getAppearanceEdit();
    }

    @Override
    public Appearance getEntity(EditAppearanceResult result) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        Appearance appearance;
        var appearanceName = spec.getAppearanceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            appearance = appearanceControl.getAppearanceByName(appearanceName);
        } else { // EditMode.UPDATE
            appearance = appearanceControl.getAppearanceByNameForUpdate(appearanceName);
        }

        if(appearance == null) {
            addExecutionError(ExecutionErrors.UnknownAppearanceName.name(), appearanceName);
        }

        return appearance;
    }

    @Override
    public Appearance getLockEntity(Appearance appearance) {
        return appearance;
    }

    @Override
    public void fillInResult(EditAppearanceResult result, Appearance appearance) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);

        result.setAppearance(appearanceControl.getAppearanceTransfer(getUserVisit(), appearance));
    }

    Color textColor;
    Color backgroundColor;
    FontStyle fontStyle;
    FontWeight fontWeight;

    @Override
    public void doLock(AppearanceEdit edit, Appearance appearance) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var appearanceDescription = appearanceControl.getAppearanceDescription(appearance, getPreferredLanguage());
        var appearanceDetail = appearance.getLastDetail();

        textColor = appearanceDetail.getTextColor();
        backgroundColor = appearanceDetail.getBackgroundColor();
        fontStyle = appearanceDetail.getFontStyle();
        fontWeight = appearanceDetail.getFontWeight();
        
        edit.setAppearanceName(appearanceDetail.getAppearanceName());
        edit.setTextColorName(textColor == null ? null : textColor.getLastDetail().getColorName());
        edit.setBackgroundColorName(backgroundColor == null ? null : backgroundColor.getLastDetail().getColorName());
        edit.setFontStyleName(fontStyle == null ? null : fontStyle.getLastDetail().getFontStyleName());
        edit.setFontWeightName(fontWeight == null ? null : fontWeight.getLastDetail().getFontWeightName());
        edit.setIsDefault(appearanceDetail.getIsDefault().toString());
        edit.setSortOrder(appearanceDetail.getSortOrder().toString());

        if(appearanceDescription != null) {
            edit.setDescription(appearanceDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Appearance appearance) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var appearanceName = edit.getAppearanceName();
        var duplicateAppearance = appearanceControl.getAppearanceByName(appearanceName);

        if(duplicateAppearance != null && !appearance.equals(duplicateAppearance)) {
            addExecutionError(ExecutionErrors.DuplicateAppearanceName.name(), appearanceName);
        } else {
            var colorControl = Session.getModelController(ColorControl.class);
            var textColorName = edit.getTextColorName();
            
            textColor = textColorName == null ? null : colorControl.getColorByName(textColorName);
            
            if(textColorName == null || textColor != null) {
                var backgroundColorName = edit.getBackgroundColorName();
                
                backgroundColor = backgroundColorName == null ? null : colorControl.getColorByName(backgroundColorName);

                if(backgroundColorName == null || backgroundColor != null) {
                    var fontStyleName = edit.getFontStyleName();
                    
                    fontStyle = fontStyleName == null ? null : FontLogic.getInstance().getFontStyleByName(this, fontStyleName);
                    
                    if(!hasExecutionErrors()) {
                        var fontWeightName = edit.getFontWeightName();
                        
                        fontWeight = fontWeightName == null ? null : FontLogic.getInstance().getFontWeightByName(this, fontWeightName);
                    }
                } else {
                addExecutionError(ExecutionErrors.UnknownBackgroundColorName.name(), backgroundColorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTextColorName.name(), textColorName);
            }
        }
    }

    @Override
    public void doUpdate(Appearance appearance) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var partyPK = getPartyPK();
        var appearanceDetailValue = appearanceControl.getAppearanceDetailValueForUpdate(appearance);
        var appearanceDescription = appearanceControl.getAppearanceDescriptionForUpdate(appearance, getPreferredLanguage());
        var description = edit.getDescription();

        appearanceDetailValue.setAppearanceName(edit.getAppearanceName());
        appearanceDetailValue.setTextColorPK(textColor == null ? null : textColor.getPrimaryKey());
        appearanceDetailValue.setBackgroundColorPK(backgroundColor == null ? null : backgroundColor.getPrimaryKey());
        appearanceDetailValue.setFontStylePK(fontStyle == null ? null : fontStyle.getPrimaryKey());
        appearanceDetailValue.setFontWeightPK(fontWeight == null ? null : fontWeight.getPrimaryKey());
        appearanceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        appearanceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        appearanceControl.updateAppearanceFromValue(appearanceDetailValue, partyPK);

        if(appearanceDescription == null && description != null) {
            appearanceControl.createAppearanceDescription(appearance, getPreferredLanguage(), description, partyPK);
        } else {
            if(appearanceDescription != null && description == null) {
                appearanceControl.deleteAppearanceDescription(appearanceDescription, partyPK);
            } else {
                if(appearanceDescription != null && description != null) {
                    var appearanceDescriptionValue = appearanceControl.getAppearanceDescriptionValue(appearanceDescription);

                    appearanceDescriptionValue.setDescription(description);
                    appearanceControl.updateAppearanceDescriptionFromValue(appearanceDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
