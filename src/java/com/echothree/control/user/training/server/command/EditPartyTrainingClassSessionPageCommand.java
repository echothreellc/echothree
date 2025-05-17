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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.PartyTrainingClassSessionPageEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditPartyTrainingClassSessionPageForm;
import com.echothree.control.user.training.common.result.EditPartyTrainingClassSessionPageResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.PartyTrainingClassSessionPageSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.PartyTrainingClassSessionLogic;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyTrainingClassSessionPageCommand
        extends BaseAbstractEditCommand<PartyTrainingClassSessionPageSpec, PartyTrainingClassSessionPageEdit, EditPartyTrainingClassSessionPageResult, PartyTrainingClassSessionPage, PartyTrainingClassSession> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClassSessionPage.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassSessionSequence", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("PartyTrainingClassSessionPageSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReadingStartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("ReadingEndTime", FieldType.DATE_TIME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTrainingClassSessionPageCommand */
    public EditPartyTrainingClassSessionPageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyTrainingClassSessionPageResult getResult() {
        return TrainingResultFactory.getEditPartyTrainingClassSessionPageResult();
    }

    @Override
    public PartyTrainingClassSessionPageEdit getEdit() {
        return TrainingEditFactory.getPartyTrainingClassSessionPageEdit();
    }

    @Override
    public PartyTrainingClassSessionPage getEntity(EditPartyTrainingClassSessionPageResult result) {
        PartyTrainingClassSessionPage partyTrainingClassSessionPage = null;
        var partyTrainingClass = PartyTrainingClassLogic.getInstance().getPartyTrainingClassByName(this, spec.getPartyTrainingClassName());
        
        if(!hasExecutionErrors()) {
            var partyTrainingClassSessionSequence = Integer.valueOf(spec.getPartyTrainingClassSessionSequence());
            var partyTrainingClassSession = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSession(this,
                    partyTrainingClass, partyTrainingClassSessionSequence);
            
            if(!hasExecutionErrors()) {
                var partyTrainingClassSessionPageSequence = Integer.valueOf(spec.getPartyTrainingClassSessionPageSequence());
                
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyTrainingClassSessionPage = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSessionPage(this,
                            partyTrainingClassSession, partyTrainingClassSessionPageSequence);
                } else { // EditMode.UPDATE
                    partyTrainingClassSessionPage = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSessionPageForUpdate(this,
                            partyTrainingClassSession, partyTrainingClassSessionPageSequence);
                }
            }
        }

        return partyTrainingClassSessionPage;
    }

    @Override
    public PartyTrainingClassSession getLockEntity(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        return partyTrainingClassSessionPage.getPartyTrainingClassSession();
    }

    @Override
    public void fillInResult(EditPartyTrainingClassSessionPageResult result, PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setPartyTrainingClassSessionPage(trainingControl.getPartyTrainingClassSessionPageTransfer(getUserVisit(), partyTrainingClassSessionPage));
    }
    
    @Override
    public void doLock(PartyTrainingClassSessionPageEdit edit, PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        var dateUtils = DateUtils.getInstance();
        var userVisit = getUserVisit();
        var preferredDateTimeFormat = getPreferredDateTimeFormat();
        
        edit.setTrainingClassPageName(partyTrainingClassSessionPage.getTrainingClassPage().getLastDetail().getTrainingClassPageName());
        edit.setReadingStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassSessionPage.getReadingStartTime()));
        edit.setReadingEndTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassSessionPage.getReadingEndTime()));
    }

    TrainingClassPage trainingClassPage;
    Long readingStartTime;
    Long readingEndTime;
    
    @Override
    public void canUpdate(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        var strReadingEndTime = edit.getReadingEndTime();

        trainingClassPage = TrainingClassLogic.getInstance().getTrainingClassPageByName(this,
                partyTrainingClassSessionPage.getTrainingClassPage().getLastDetail().getTrainingClassSection(), edit.getTrainingClassPageName());
        readingStartTime = Long.valueOf(edit.getReadingStartTime());
        readingEndTime = strReadingEndTime == null ? null : Long.valueOf(strReadingEndTime);
    }

    @Override
    public void doUpdate(PartyTrainingClassSessionPage partyTrainingClassSessionPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var partyTrainingClassSessionPageValue = trainingControl.getPartyTrainingClassSessionPageValue(partyTrainingClassSessionPage);

        partyTrainingClassSessionPageValue.setTrainingClassPagePK(trainingClassPage.getPrimaryKey());
        partyTrainingClassSessionPageValue.setReadingStartTime(readingStartTime);
        partyTrainingClassSessionPageValue.setReadingEndTime(readingEndTime);

        trainingControl.updatePartyTrainingClassSessionPageFromValue(partyTrainingClassSessionPageValue, partyPK);
    }

}
