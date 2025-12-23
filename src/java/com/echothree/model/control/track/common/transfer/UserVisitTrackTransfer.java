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

package com.echothree.model.control.track.common.transfer;

import com.echothree.model.control.user.common.transfer.UserVisitTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UserVisitTrackTransfer
        extends BaseTransfer {
    
    private UserVisitTransfer userVisit;
    private Integer userVisitTrackSequence;
    private Long unformattedTime;
    private String time;
    private TrackTransfer track;
    
    /** Creates a new instance of UserVisitTrackTransfer */
    public UserVisitTrackTransfer(UserVisitTransfer userVisit, Integer userVisitTrackSequence, Long unformattedTime, String time, TrackTransfer track) {
        this.userVisit = userVisit;
        this.userVisitTrackSequence = userVisitTrackSequence;
        this.unformattedTime = unformattedTime;
        this.time = time;
        this.track = track;
    }

    /**
     * Returns the userVisit.
     * @return the userVisit
     */
    public UserVisitTransfer getUserVisit() {
        return userVisit;
    }

    /**
     * Sets the userVisit.
     * @param userVisit the userVisit to set
     */
    public void setUserVisit(UserVisitTransfer userVisit) {
        this.userVisit = userVisit;
    }

    /**
     * Returns the userVisitTrackSequence.
     * @return the userVisitTrackSequence
     */
    public Integer getUserVisitTrackSequence() {
        return userVisitTrackSequence;
    }

    /**
     * Sets the userVisitTrackSequence.
     * @param userVisitTrackSequence the userVisitTrackSequence to set
     */
    public void setUserVisitTrackSequence(Integer userVisitTrackSequence) {
        this.userVisitTrackSequence = userVisitTrackSequence;
    }

    /**
     * Returns the unformattedTime.
     * @return the unformattedTime
     */
    public Long getUnformattedTime() {
        return unformattedTime;
    }

    /**
     * Sets the unformattedTime.
     * @param unformattedTime the unformattedTime to set
     */
    public void setUnformattedTime(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    /**
     * Returns the time.
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time.
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Returns the track.
     * @return the track
     */
    public TrackTransfer getTrack() {
        return track;
    }

    /**
     * Sets the track.
     * @param track the track to set
     */
    public void setTrack(TrackTransfer track) {
        this.track = track;
    }

}
