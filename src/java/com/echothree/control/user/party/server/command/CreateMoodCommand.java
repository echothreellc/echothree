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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateMoodForm;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateMoodCommand
        extends BaseSimpleCommand<CreateMoodForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MoodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of CreateMoodCommand */
    public CreateMoodCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var moodName = form.getMoodName();
        var mood = partyControl.getMoodByName(moodName);

        if(mood == null) {
            var iconControl = Session.getModelController(IconControl.class);
            var iconName = form.getIconName();
            var icon = iconName == null? null: iconControl.getIconByName(iconName);
            
            if(iconName == null || icon != null) {
                if(icon != null) {
                    var iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_MOOD);
                    var iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                    
                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var partyPK = getPartyPK();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();

                    mood = partyControl.createMood(moodName, icon, isDefault, sortOrder, getPartyPK());

                    if(description != null) {
                        partyControl.createMoodDescription(mood, getPreferredLanguage(), description, partyPK);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateMoodName.name(), moodName);
        }

        return null;
    }

}
