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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyTrainingClassTransfer
        extends BaseTransfer {
    
    private String partyTrainingClassName;
    private PartyTransfer party;
    private TrainingClassTransfer trainingClass;
    private Long unformattedCompletedTime;
    private String completedTime;
    private Long unformattedValidUntilTime;
    private String validUntilTime;
    private WorkflowEntityStatusTransfer partyTrainingClassStatus;
    
    private ListWrapper<PartyTrainingClassSessionTransfer> partyTrainingClassSessions;
    
    /** Creates a new instance of PartyTrainingClassTransfer */
    public PartyTrainingClassTransfer(String partyTrainingClassName, PartyTransfer party, TrainingClassTransfer trainingClass, Long unformattedCompletedTime,
            String completedTime, Long unformattedValidUntilTime, String validUntilTime, WorkflowEntityStatusTransfer partyTrainingClassStatus) {
        this.partyTrainingClassName = partyTrainingClassName;
        this.party = party;
        this.trainingClass = trainingClass;
        this.unformattedCompletedTime = unformattedCompletedTime;
        this.completedTime = completedTime;
        this.unformattedValidUntilTime = unformattedValidUntilTime;
        this.validUntilTime = validUntilTime;
        this.partyTrainingClassStatus = partyTrainingClassStatus;
    }

    public String getPartyTrainingClassName() {
        return partyTrainingClassName;
    }

    public void setPartyTrainingClassName(String partyTrainingClassName) {
        this.partyTrainingClassName = partyTrainingClassName;
    }

    public PartyTransfer getParty() {
        return party;
    }

    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    public TrainingClassTransfer getTrainingClass() {
        return trainingClass;
    }

    public void setTrainingClass(TrainingClassTransfer trainingClass) {
        this.trainingClass = trainingClass;
    }

    public Long getUnformattedCompletedTime() {
        return unformattedCompletedTime;
    }

    public void setUnformattedCompletedTime(Long unformattedCompletedTime) {
        this.unformattedCompletedTime = unformattedCompletedTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public Long getUnformattedValidUntilTime() {
        return unformattedValidUntilTime;
    }

    public void setUnformattedValidUntilTime(Long unformattedValidUntilTime) {
        this.unformattedValidUntilTime = unformattedValidUntilTime;
    }

    public String getValidUntilTime() {
        return validUntilTime;
    }

    public void setValidUntilTime(String validUntilTime) {
        this.validUntilTime = validUntilTime;
    }

    public WorkflowEntityStatusTransfer getPartyTrainingClassStatus() {
        return partyTrainingClassStatus;
    }

    public void setPartyTrainingClassStatus(WorkflowEntityStatusTransfer partyTrainingClassStatus) {
        this.partyTrainingClassStatus = partyTrainingClassStatus;
    }

    public ListWrapper<PartyTrainingClassSessionTransfer> getPartyTrainingClassSessions() {
        return partyTrainingClassSessions;
    }

    public void setPartyTrainingClassSessions(ListWrapper<PartyTrainingClassSessionTransfer> partyTrainingClassSessions) {
        this.partyTrainingClassSessions = partyTrainingClassSessions;
    }

}
