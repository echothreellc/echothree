// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.training.common.form.GetTrainingClassQuestionForm;
import com.echothree.control.user.training.common.result.GetTrainingClassQuestionResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassSessionLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionStatus;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTrainingClassQuestionCommand
        extends BaseSimpleCommand<GetTrainingClassQuestionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassQuestion.name(), SecurityRoles.Review.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTrainingClassQuestionCommand */
    public GetTrainingClassQuestionCommand(UserVisitPK userVisitPK, GetTrainingClassQuestionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = Session.getModelController(TrainingControl.class);
        GetTrainingClassQuestionResult result = TrainingResultFactory.getGetTrainingClassQuestionResult();
        String trainingClassName = form.getTrainingClassName();
        TrainingClass trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            String trainingClassSectionName = form.getTrainingClassSectionName();
            TrainingClassSection trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                String trainingClassQuestionName = form.getTrainingClassQuestionName();
                TrainingClassQuestion trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);

                if(trainingClassQuestion != null) {
                    String partyTrainingClassName = form.getPartyTrainingClassName();
                    PartyTrainingClassSessionStatus partyTrainingClassSessionStatus = partyTrainingClassName == null ? null
                            : PartyTrainingClassSessionLogic.getInstance().getLatestPartyTrainingClassSessionStatusForUpdate(this, partyTrainingClassName);
                    
                    if(!hasExecutionErrors()) {
                        PartyTrainingClassSession partyTrainingClassSession = partyTrainingClassSessionStatus == null ? null
                                : partyTrainingClassSessionStatus.getPartyTrainingClassSession();
                        
                        // Verify that the TrainingClass from above is same as the one being used by the PartyTrainingClassSession.
                        if(partyTrainingClassSession != null) {
                            if(!trainingClass.equals(partyTrainingClassSession.getLastDetail().getPartyTrainingClass().getLastDetail().getTrainingClass())) {
                                addExecutionError(ExecutionErrors.InvalidPartyTrainingClass.name(), partyTrainingClassName);
                            }
                        }
                        
                        if(!hasExecutionErrors()) {
                            UserVisit userVisit = getUserVisit();
                            var partyPK = getPartyPK();

                            result.setTrainingClassQuestion(trainingControl.getTrainingClassQuestionTransfer(userVisit, trainingClassQuestion));

                            sendEventUsingNames(trainingClassQuestion.getPrimaryKey(), EventTypes.READ.name(), null, null, partyPK);

                            if(partyTrainingClassSessionStatus != null) {
                                PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion = trainingControl.getPartyTrainingClassSessionQuestion(partyTrainingClassSession, trainingClassQuestion);

                                // If there isn't an existing PartyTrainingClassSessionQuestion, then we'll create one to attach the Answer to.
                                if(partyTrainingClassSessionQuestion == null) {
                                    partyTrainingClassSessionQuestion = trainingControl.createPartyTrainingClassSessionQuestion(partyTrainingClassSessionStatus.getPartyTrainingClassSession(),
                                            trainingClassQuestion, null, partyPK);
                                }

                                PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer = trainingControl.createPartyTrainingClassSessionAnswer(partyTrainingClassSessionQuestion,
                                        null, session.START_TIME_LONG, null, partyPK);

                                PartyTrainingClassSessionLogic.getInstance().updatePartyTrainingClassSessionStatus(session, partyTrainingClassSessionStatus,
                                        null, null, partyTrainingClassSessionQuestion);
                                
                                result.setPartyTrainingClassSessionAnswer(trainingControl.getPartyTrainingClassSessionAnswerTransfer(userVisit, partyTrainingClassSessionAnswer));
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassQuestionName.name(), trainingClassName, trainingClassSectionName,
                            trainingClassQuestionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return result;
    }
    
}
