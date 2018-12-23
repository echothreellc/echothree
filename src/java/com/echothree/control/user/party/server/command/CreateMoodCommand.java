// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Mood;
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

public class CreateMoodCommand
        extends BaseSimpleCommand<CreateMoodForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MoodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of CreateMoodCommand */
    public CreateMoodCommand(UserVisitPK userVisitPK, CreateMoodForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String moodName = form.getMoodName();
        Mood mood = partyControl.getMoodByName(moodName);

        if(mood == null) {
            IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
            String iconName = form.getIconName();
            Icon icon = iconName == null? null: iconControl.getIconByName(iconName);
            
            if(iconName == null || icon != null) {
                if(icon != null) {
                    IconUsageType iconUsageType = iconControl.getIconUsageTypeByName(IconConstants.IconUsageType_MOOD);
                    IconUsage iconUsage = iconControl.getIconUsage(iconUsageType, icon);
                    
                    if(iconUsage == null) {
                        addExecutionError(ExecutionErrors.UnknownIconUsage.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    PartyPK partyPK = getPartyPK();
                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                    String description = form.getDescription();

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
