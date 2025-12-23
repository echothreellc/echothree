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

public class PartyTrainingClassSessionTransfer
        extends BaseTransfer {
    
    private PartyTrainingClassTransfer partyTrainingClass;
    private Integer partyTrainingClassSessionSequence;
    
    private PartyTrainingClassSessionSectionTransfer lastPartyTrainingClassSessionSection;
    private PartyTrainingClassSessionPageTransfer lastPartyTrainingClassSessionPage;
    private PartyTrainingClassSessionQuestionTransfer lastPartyTrainingClassSessionQuestion;
    
    private ListWrapper<PartyTrainingClassSessionPageTransfer> partyTrainingClassSessionPages;
    private ListWrapper<PartyTrainingClassSessionQuestionTransfer> partyTrainingClassSessionQuestions;
    
    /** Creates a new instance of PartyTrainingClassSessionTransfer */
    public PartyTrainingClassSessionTransfer(PartyTrainingClassTransfer partyTrainingClass, Integer partyTrainingClassSessionSequence,
            PartyTrainingClassSessionSectionTransfer lastPartyTrainingClassSessionSection,
            PartyTrainingClassSessionPageTransfer lastPartyTrainingClassSessionPage,
            PartyTrainingClassSessionQuestionTransfer lastPartyTrainingClassSessionQuestion) {
        this.partyTrainingClass = partyTrainingClass;
        this.partyTrainingClassSessionSequence = partyTrainingClassSessionSequence;
        this.lastPartyTrainingClassSessionSection = lastPartyTrainingClassSessionSection;
        this.lastPartyTrainingClassSessionPage = lastPartyTrainingClassSessionPage;
        this.lastPartyTrainingClassSessionQuestion = lastPartyTrainingClassSessionQuestion;
    }

    /**
     * Returns the partyTrainingClass.
     * @return the partyTrainingClass
     */
    public PartyTrainingClassTransfer getPartyTrainingClass() {
        return partyTrainingClass;
    }

    /**
     * Sets the partyTrainingClass.
     * @param partyTrainingClass the partyTrainingClass to set
     */
    public void setPartyTrainingClass(PartyTrainingClassTransfer partyTrainingClass) {
        this.partyTrainingClass = partyTrainingClass;
    }

    /**
     * Returns the partyTrainingClassSessionSequence.
     * @return the partyTrainingClassSessionSequence
     */
    public Integer getPartyTrainingClassSessionSequence() {
        return partyTrainingClassSessionSequence;
    }

    /**
     * Sets the partyTrainingClassSessionSequence.
     * @param partyTrainingClassSessionSequence the partyTrainingClassSessionSequence to set
     */
    public void setPartyTrainingClassSessionSequence(Integer partyTrainingClassSessionSequence) {
        this.partyTrainingClassSessionSequence = partyTrainingClassSessionSequence;
    }

    /**
     * Returns the lastPartyTrainingClassSessionSection.
     * @return the lastPartyTrainingClassSessionSection
     */
    public PartyTrainingClassSessionSectionTransfer getLastPartyTrainingClassSessionSection() {
        return lastPartyTrainingClassSessionSection;
    }

    /**
     * Sets the lastPartyTrainingClassSessionSection.
     * @param lastPartyTrainingClassSessionSection the lastPartyTrainingClassSessionSection to set
     */
    public void setLastPartyTrainingClassSessionSection(PartyTrainingClassSessionSectionTransfer lastPartyTrainingClassSessionSection) {
        this.lastPartyTrainingClassSessionSection = lastPartyTrainingClassSessionSection;
    }

    /**
     * Returns the lastPartyTrainingClassSessionPage.
     * @return the lastPartyTrainingClassSessionPage
     */
    public PartyTrainingClassSessionPageTransfer getLastPartyTrainingClassSessionPage() {
        return lastPartyTrainingClassSessionPage;
    }

    /**
     * Sets the lastPartyTrainingClassSessionPage.
     * @param lastPartyTrainingClassSessionPage the lastPartyTrainingClassSessionPage to set
     */
    public void setLastPartyTrainingClassSessionPage(PartyTrainingClassSessionPageTransfer lastPartyTrainingClassSessionPage) {
        this.lastPartyTrainingClassSessionPage = lastPartyTrainingClassSessionPage;
    }

    /**
     * Returns the lastPartyTrainingClassSessionQuestion.
     * @return the lastPartyTrainingClassSessionQuestion
     */
    public PartyTrainingClassSessionQuestionTransfer getLastPartyTrainingClassSessionQuestion() {
        return lastPartyTrainingClassSessionQuestion;
    }

    /**
     * Sets the lastPartyTrainingClassSessionQuestion.
     * @param lastPartyTrainingClassSessionQuestion the lastPartyTrainingClassSessionQuestion to set
     */
    public void setLastPartyTrainingClassSessionQuestion(PartyTrainingClassSessionQuestionTransfer lastPartyTrainingClassSessionQuestion) {
        this.lastPartyTrainingClassSessionQuestion = lastPartyTrainingClassSessionQuestion;
    }

    /**
     * Returns the partyTrainingClassSessionPages.
     * @return the partyTrainingClassSessionPages
     */
    public ListWrapper<PartyTrainingClassSessionPageTransfer> getPartyTrainingClassSessionPages() {
        return partyTrainingClassSessionPages;
    }

    /**
     * Sets the partyTrainingClassSessionPages.
     * @param partyTrainingClassSessionPages the partyTrainingClassSessionPages to set
     */
    public void setPartyTrainingClassSessionPages(ListWrapper<PartyTrainingClassSessionPageTransfer> partyTrainingClassSessionPages) {
        this.partyTrainingClassSessionPages = partyTrainingClassSessionPages;
    }

    /**
     * Returns the partyTrainingClassSessionQuestions.
     * @return the partyTrainingClassSessionQuestions
     */
    public ListWrapper<PartyTrainingClassSessionQuestionTransfer> getPartyTrainingClassSessionQuestions() {
        return partyTrainingClassSessionQuestions;
    }

    /**
     * Sets the partyTrainingClassSessionQuestions.
     * @param partyTrainingClassSessionQuestions the partyTrainingClassSessionQuestions to set
     */
    public void setPartyTrainingClassSessionQuestions(ListWrapper<PartyTrainingClassSessionQuestionTransfer> partyTrainingClassSessionQuestions) {
        this.partyTrainingClassSessionQuestions = partyTrainingClassSessionQuestions;
    }
    
}
