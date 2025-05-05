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

import com.echothree.control.user.core.common.form.CreateColorForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.ColorControl;
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

public class CreateColorCommand
        extends BaseSimpleCommand<CreateColorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Color.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ColorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Red", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("Green", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("Blue", FieldType.UNSIGNED_INTEGER, true, 0L, 255L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateColorCommand */
    public CreateColorCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getCreateColorResult();
        var colorControl = Session.getModelController(ColorControl.class);
        var colorName = form.getColorName();
        
        if(colorName == null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.COLOR.name());
            
            colorName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        }

        var color = colorControl.getColorByName(colorName);
        
        if(color == null) {
            var partyPK = getPartyPK();
            var red = Integer.valueOf(form.getRed());
            var green = Integer.valueOf(form.getGreen());
            var blue = Integer.valueOf(form.getBlue());
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            
            color = colorControl.createColor(colorName, red, green, blue, isDefault, sortOrder, partyPK);
            
            if(description != null) {
                colorControl.createColorDescription(color, getPreferredLanguage(), description, partyPK);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateColorName.name(), colorName);
        }
                
        if(color != null) {
            result.setColorName(color.getLastDetail().getColorName());
            result.setEntityRef(color.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
