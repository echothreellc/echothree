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

public class PartyTrainingClassSessionPageTransfer
        extends BaseTransfer {
    
    private PartyTrainingClassSessionTransfer partyTrainingClassSession;
    private Integer partyTrainingClassSessionPageSequence;
    private TrainingClassPageTransfer trainingClassPage;
    private Long unformattedReadingStartTime;
    private String readingStartTime;
    private Long unformattedReadingEndTime;
    private String readingEndTime;
    
    /** Creates a new instance of PartyTrainingClassSessionPageTransfer */
    public PartyTrainingClassSessionPageTransfer(PartyTrainingClassSessionTransfer partyTrainingClassSession, Integer partyTrainingClassSessionPageSequence,
            TrainingClassPageTransfer trainingClassPage, Long unformattedReadingStartTime, String readingStartTime, Long unformattedReadingEndTime,
            String readingEndTime) {
        this.partyTrainingClassSession = partyTrainingClassSession;
        this.partyTrainingClassSessionPageSequence = partyTrainingClassSessionPageSequence;
        this.trainingClassPage = trainingClassPage;
        this.unformattedReadingStartTime = unformattedReadingStartTime;
        this.readingStartTime = readingStartTime;
        this.unformattedReadingEndTime = unformattedReadingEndTime;
        this.readingEndTime = readingEndTime;
    }

    public PartyTrainingClassSessionTransfer getPartyTrainingClassSession() {
        return partyTrainingClassSession;
    }

    public void setPartyTrainingClassSession(PartyTrainingClassSessionTransfer partyTrainingClassSession) {
        this.partyTrainingClassSession = partyTrainingClassSession;
    }

    public Integer getPartyTrainingClassSessionPageSequence() {
        return partyTrainingClassSessionPageSequence;
    }

    public void setPartyTrainingClassSessionPageSequence(Integer partyTrainingClassSessionPageSequence) {
        this.partyTrainingClassSessionPageSequence = partyTrainingClassSessionPageSequence;
    }

    public TrainingClassPageTransfer getTrainingClassPage() {
        return trainingClassPage;
    }

    public void setTrainingClassPage(TrainingClassPageTransfer trainingClassPage) {
        this.trainingClassPage = trainingClassPage;
    }

    public Long getUnformattedReadingStartTime() {
        return unformattedReadingStartTime;
    }

    public void setUnformattedReadingStartTime(Long unformattedReadingStartTime) {
        this.unformattedReadingStartTime = unformattedReadingStartTime;
    }

    public String getReadingStartTime() {
        return readingStartTime;
    }

    public void setReadingStartTime(String readingStartTime) {
        this.readingStartTime = readingStartTime;
    }

    public Long getUnformattedReadingEndTime() {
        return unformattedReadingEndTime;
    }

    public void setUnformattedReadingEndTime(Long unformattedReadingEndTime) {
        this.unformattedReadingEndTime = unformattedReadingEndTime;
    }

    public String getReadingEndTime() {
        return readingEndTime;
    }

    public void setReadingEndTime(String readingEndTime) {
        this.readingEndTime = readingEndTime;
    }

}
