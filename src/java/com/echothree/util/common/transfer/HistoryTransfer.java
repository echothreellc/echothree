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

package com.echothree.util.common.transfer;

import java.io.Serializable;

public class HistoryTransfer<T extends BaseTransfer>
        implements Serializable {
    
    private T snapshot;
    private Long unformattedFromTime;
    private String fromTime;
    private Long unformattedThruTime;
    private String thruTime;
    
    /** Creates a new instance of HistoryTransfer */
    public HistoryTransfer(T snapshot, Long unformattedFromTime, String fromTime, Long unformattedThruTime, String thruTime) {
        this.snapshot = snapshot;
        this.unformattedFromTime = unformattedFromTime;
        this.fromTime = fromTime;
        this.unformattedThruTime = unformattedThruTime;
        this.thruTime = thruTime;
    }

    /**
     * Returns the snapshot.
     * @return the snapshot
     */
    public T getSnapshot() {
        return snapshot;
    }

    /**
     * Sets the snapshot.
     * @param snapshot the snapshot to set
     */
    public void setSnapshot(T snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * Returns the unformattedFromTime.
     * @return the unformattedFromTime
     */
    public Long getUnformattedFromTime() {
        return unformattedFromTime;
    }

    /**
     * Sets the unformattedFromTime.
     * @param unformattedFromTime the unformattedFromTime to set
     */
    public void setUnformattedFromTime(Long unformattedFromTime) {
        this.unformattedFromTime = unformattedFromTime;
    }

    /**
     * Returns the fromTime.
     * @return the fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     * Sets the fromTime.
     * @param fromTime the fromTime to set
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * Returns the unformattedThruTime.
     * @return the unformattedThruTime
     */
    public Long getUnformattedThruTime() {
        return unformattedThruTime;
    }

    /**
     * Sets the unformattedThruTime.
     * @param unformattedThruTime the unformattedThruTime to set
     */
    public void setUnformattedThruTime(Long unformattedThruTime) {
        this.unformattedThruTime = unformattedThruTime;
    }

    /**
     * Returns the thruTime.
     * @return the thruTime
     */
    public String getThruTime() {
        return thruTime;
    }

    /**
     * Sets the thruTime.
     * @param thruTime the thruTime to set
     */
    public void setThruTime(String thruTime) {
        this.thruTime = thruTime;
    }
    
}
