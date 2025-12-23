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

package com.echothree.model.control.training.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyTrainingClassSessionQuestionTransfer
        extends BaseTransfer {
    
    private PartyTrainingClassSessionTransfer partyTrainingClassSession;
    private TrainingClassQuestionTransfer trainingClassQuestion;
    private Integer sortOrder;
    
    private ListWrapper<PartyTrainingClassSessionAnswerTransfer> partyTrainingClassSessionAnswers;
    
    /** Creates a new instance of PartyTrainingClassSessionQuestionTransfer */
    public PartyTrainingClassSessionQuestionTransfer(PartyTrainingClassSessionTransfer partyTrainingClassSession,
            TrainingClassQuestionTransfer trainingClassQuestion, Integer sortOrder) {
        this.partyTrainingClassSession = partyTrainingClassSession;
        this.trainingClassQuestion = trainingClassQuestion;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the partyTrainingClassSession.
     * @return the partyTrainingClassSession
     */
    public PartyTrainingClassSessionTransfer getPartyTrainingClassSession() {
        return partyTrainingClassSession;
    }

    /**
     * Sets the partyTrainingClassSession.
     * @param partyTrainingClassSession the partyTrainingClassSession to set
     */
    public void setPartyTrainingClassSession(PartyTrainingClassSessionTransfer partyTrainingClassSession) {
        this.partyTrainingClassSession = partyTrainingClassSession;
    }

    /**
     * Returns the trainingClassQuestion.
     * @return the trainingClassQuestion
     */
    public TrainingClassQuestionTransfer getTrainingClassQuestion() {
        return trainingClassQuestion;
    }

    /**
     * Sets the trainingClassQuestion.
     * @param trainingClassQuestion the trainingClassQuestion to set
     */
    public void setTrainingClassQuestion(TrainingClassQuestionTransfer trainingClassQuestion) {
        this.trainingClassQuestion = trainingClassQuestion;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the partyTrainingClassSessionAnswers.
     * @return the partyTrainingClassSessionAnswers
     */
    public ListWrapper<PartyTrainingClassSessionAnswerTransfer> getPartyTrainingClassSessionAnswers() {
        return partyTrainingClassSessionAnswers;
    }

    /**
     * Sets the partyTrainingClassSessionAnswers.
     * @param partyTrainingClassSessionAnswers the partyTrainingClassSessionAnswers to set
     */
    public void setPartyTrainingClassSessionAnswers(ListWrapper<PartyTrainingClassSessionAnswerTransfer> partyTrainingClassSessionAnswers) {
        this.partyTrainingClassSessionAnswers = partyTrainingClassSessionAnswers;
    }

}
