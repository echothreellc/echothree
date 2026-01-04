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

package com.echothree.model.control.employee.common.transfer;

import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LeaveResultTransfer
        extends BaseTransfer {
    
    private String leaveName;
    private LeaveTransfer leave;
    
    /** Creates a new instance of LeaveResultTransfer */
    public LeaveResultTransfer(String leaveName, LeaveTransfer leave) {
        this.leaveName = leaveName;
        this.leave = leave;
    }

    /**
     * Returns the leaveName.
     * @return the leaveName
     */
    public String getLeaveName() {
        return leaveName;
    }

    /**
     * Sets the leaveName.
     * @param leaveName the leaveName to set
     */
    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    /**
     * Returns the leave.
     * @return the leave
     */
    public LeaveTransfer getLeave() {
        return leave;
    }

    /**
     * Sets the leave.
     * @param leave the leave to set
     */
    public void setLeave(LeaveTransfer leave) {
        this.leave = leave;
    }

}
