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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateAppearanceForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.control.core.server.logic.FontLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateAppearanceCommand
        extends BaseSimpleCommand<CreateAppearanceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TextColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BackgroundColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FontWeightName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateAppearanceCommand */
    public CreateAppearanceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getCreateAppearanceResult();
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var appearanceName = form.getAppearanceName();
        
        if(appearanceName == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.APPEARANCE.name());
            
            appearanceName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        }

        var appearance = appearanceControl.getAppearanceByName(appearanceName);
        
        if(appearance == null) {
            var colorControl = Session.getModelController(ColorControl.class);
            var textColorName = form.getTextColorName();
            var textColor = textColorName == null ? null : colorControl.getColorByName(textColorName);
            
            if(textColorName == null || textColor != null) {
                var backgroundColorName = form.getBackgroundColorName();
                var backgroundColor = backgroundColorName == null ? null : colorControl.getColorByName(backgroundColorName);

                if(backgroundColorName == null || backgroundColor != null) {
                    var fontStyleName = form.getFontStyleName();
                    var fontStyle = fontStyleName == null ? null : FontLogic.getInstance().getFontStyleByName(this, fontStyleName);
                    
                    if(!hasExecutionErrors()) {
                        var fontWeightName = form.getFontWeightName();
                        var fontWeight = fontWeightName == null ? null : FontLogic.getInstance().getFontWeightByName(this, fontWeightName);

                        if(!hasExecutionErrors()) {
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            appearance = appearanceControl.createAppearance(appearanceName, textColor, backgroundColor, fontStyle, fontWeight, isDefault, sortOrder,
                                    partyPK);

                            if(description != null) {
                                appearanceControl.createAppearanceDescription(appearance, getPreferredLanguage(), description, partyPK);
                            }
                        }
                    }
                } else {
                addExecutionError(ExecutionErrors.UnknownBackgroundColorName.name(), backgroundColorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTextColorName.name(), textColorName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateAppearanceName.name(), appearanceName);
        }
                
        if(appearance != null) {
            result.setAppearanceName(appearance.getLastDetail().getAppearanceName());
            result.setEntityRef(appearance.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
