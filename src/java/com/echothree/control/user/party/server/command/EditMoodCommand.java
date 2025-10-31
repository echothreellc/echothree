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

import com.echothree.control.user.party.common.edit.MoodEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditMoodForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.MoodSpec;
import com.echothree.model.control.icon.common.IconConstants;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditMoodCommand
        extends BaseEditCommand<MoodSpec, MoodEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("MoodName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(4);
        temp.add(new FieldDefinition("MoodName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("IconName", FieldType.ENTITY_NAME, false, null, null));
        temp.add(new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null));
        temp.add(new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null));
        temp.add(new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditMoodCommand */
    public EditMoodCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditMoodResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var moodName = spec.getMoodName();
            var mood = partyControl.getMoodByName(moodName);
            
            if(mood != null) {
                result.setMood(partyControl.getMoodTransfer(getUserVisit(), mood));
                
                if(lockEntity(mood)) {
                    var moodDescription = partyControl.getMoodDescription(mood, getPreferredLanguage());
                    var edit = PartyEditFactory.getMoodEdit();
                    var moodDetail = mood.getLastDetail();
                    var icon = moodDetail.getIcon();
                    
                    result.setEdit(edit);
                    edit.setMoodName(moodDetail.getMoodName());
                    edit.setIconName(icon == null? null: icon.getLastDetail().getIconName());
                    edit.setIsDefault(moodDetail.getIsDefault().toString());
                    edit.setSortOrder(moodDetail.getSortOrder().toString());
                    
                    if(moodDescription != null)
                        edit.setDescription(moodDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(mood));
            } else {
                addExecutionError(ExecutionErrors.UnknownMoodName.name(), moodName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var moodName = spec.getMoodName();
            var mood = partyControl.getMoodByNameForUpdate(moodName);
            
            if(mood != null) {
                moodName = edit.getMoodName();
                var duplicateMood = partyControl.getMoodByName(moodName);
                
                if(duplicateMood == null || mood.equals(duplicateMood)) {
                    var iconControl = Session.getModelController(IconControl.class);
                    var iconName = edit.getIconName();
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
                            if(lockEntityForUpdate(mood)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var moodDetailValue = partyControl.getMoodDetailValueForUpdate(mood);
                                    var moodDescription = partyControl.getMoodDescriptionForUpdate(mood, getPreferredLanguage());
                                    var description = edit.getDescription();

                                    moodDetailValue.setMoodName(edit.getMoodName());
                                    moodDetailValue.setIconPK(icon == null? null: icon.getPrimaryKey());
                                    moodDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    moodDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                    partyControl.updateMoodFromValue(moodDetailValue, partyPK);

                                    if(moodDescription == null && description != null) {
                                        partyControl.createMoodDescription(mood, getPreferredLanguage(), description, partyPK);
                                    } else if(moodDescription != null && description == null) {
                                        partyControl.deleteMoodDescription(moodDescription, partyPK);
                                    } else if(moodDescription != null && description != null) {
                                        var moodDescriptionValue = partyControl.getMoodDescriptionValue(moodDescription);

                                        moodDescriptionValue.setDescription(description);
                                        partyControl.updateMoodDescriptionFromValue(moodDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(mood);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownIconName.name(), iconName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateMoodName.name(), moodName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMoodName.name(), moodName);
            }
            
            if(hasExecutionErrors()) {
                result.setMood(partyControl.getMoodTransfer(getUserVisit(), mood));
                result.setEntityLock(getEntityLockTransfer(mood));
            }
        }
        
        return result;
    }
    
}
