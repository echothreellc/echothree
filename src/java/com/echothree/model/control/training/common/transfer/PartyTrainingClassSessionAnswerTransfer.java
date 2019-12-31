// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

public class PartyTrainingClassSessionAnswerTransfer
        extends BaseTransfer {
    
    private PartyTrainingClassSessionQuestionTransfer partyTrainingClassSessionQuestion;
    private Integer partyTrainingClassSessionAnswerSequence;
    private TrainingClassAnswerTransfer trainingClassAnswer;
    private Long unformattedQuestionStartTime;
    private String questionStartTime;
    private Long unformattedQuestionEndTime;
    private String questionEndTime;
    
    /** Creates a new instance of PartyTrainingClassSessionAnswerTransfer */
    public PartyTrainingClassSessionAnswerTransfer(PartyTrainingClassSessionQuestionTransfer partyTrainingClassSessionQuestion,
            Integer partyTrainingClassSessionAnswerSequence, TrainingClassAnswerTransfer trainingClassAnswer,
            Long unformattedQuestionStartTime, String questionStartTime, Long unformattedQuestionEndTime, String questionEndTime) {
        this.partyTrainingClassSessionQuestion = partyTrainingClassSessionQuestion;
        this.partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswerSequence;
        this.trainingClassAnswer = trainingClassAnswer;
        this.unformattedQuestionStartTime = unformattedQuestionStartTime;
        this.questionStartTime = questionStartTime;
        this.unformattedQuestionEndTime = unformattedQuestionEndTime;
        this.questionEndTime = questionEndTime;
    }

    /**
     * @return the partyTrainingClassSessionQuestion
     */
    public PartyTrainingClassSessionQuestionTransfer getPartyTrainingClassSessionQuestion() {
        return partyTrainingClassSessionQuestion;
    }

    /**
     * @param partyTrainingClassSessionQuestion the partyTrainingClassSessionQuestion to set
     */
    public void setPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestionTransfer partyTrainingClassSessionQuestion) {
        this.partyTrainingClassSessionQuestion = partyTrainingClassSessionQuestion;
    }

    /**
     * @return the partyTrainingClassSessionAnswerSequence
     */
    public Integer getPartyTrainingClassSessionAnswerSequence() {
        return partyTrainingClassSessionAnswerSequence;
    }

    /**
     * @param partyTrainingClassSessionAnswerSequence the partyTrainingClassSessionAnswerSequence to set
     */
    public void setPartyTrainingClassSessionAnswerSequence(Integer partyTrainingClassSessionAnswerSequence) {
        this.partyTrainingClassSessionAnswerSequence = partyTrainingClassSessionAnswerSequence;
    }

    /**
     * @return the trainingClassAnswer
     */
    public TrainingClassAnswerTransfer getTrainingClassAnswer() {
        return trainingClassAnswer;
    }

    /**
     * @param trainingClassAnswer the trainingClassAnswer to set
     */
    public void setTrainingClassAnswer(TrainingClassAnswerTransfer trainingClassAnswer) {
        this.trainingClassAnswer = trainingClassAnswer;
    }

    /**
     * @return the unformattedQuestionStartTime
     */
    public Long getUnformattedQuestionStartTime() {
        return unformattedQuestionStartTime;
    }

    /**
     * @param unformattedQuestionStartTime the unformattedQuestionStartTime to set
     */
    public void setUnformattedQuestionStartTime(Long unformattedQuestionStartTime) {
        this.unformattedQuestionStartTime = unformattedQuestionStartTime;
    }

    /**
     * @return the questionStartTime
     */
    public String getQuestionStartTime() {
        return questionStartTime;
    }

    /**
     * @param questionStartTime the questionStartTime to set
     */
    public void setQuestionStartTime(String questionStartTime) {
        this.questionStartTime = questionStartTime;
    }

    /**
     * @return the unformattedQuestionEndTime
     */
    public Long getUnformattedQuestionEndTime() {
        return unformattedQuestionEndTime;
    }

    /**
     * @param unformattedQuestionEndTime the unformattedQuestionEndTime to set
     */
    public void setUnformattedQuestionEndTime(Long unformattedQuestionEndTime) {
        this.unformattedQuestionEndTime = unformattedQuestionEndTime;
    }

    /**
     * @return the questionEndTime
     */
    public String getQuestionEndTime() {
        return questionEndTime;
    }

    /**
     * @param questionEndTime the questionEndTime to set
     */
    public void setQuestionEndTime(String questionEndTime) {
        this.questionEndTime = questionEndTime;
    }

}
