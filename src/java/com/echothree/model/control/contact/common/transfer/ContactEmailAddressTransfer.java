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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContactEmailAddressTransfer
        extends BaseTransfer {
    
    private String emailAddress;
    private WorkflowEntityStatusTransfer emailAddressStatus;
    private WorkflowEntityStatusTransfer emailAddressVerification;
    
    /** Creates a new instance of ContactEmailAddressTransfer */
    public ContactEmailAddressTransfer(String emailAddress, WorkflowEntityStatusTransfer emailAddressStatus,
            WorkflowEntityStatusTransfer emailAddressVerification) {
        this.emailAddress = emailAddress;
        this.emailAddressStatus = emailAddressStatus;
        this.emailAddressVerification = emailAddressVerification;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public WorkflowEntityStatusTransfer getEmailAddressStatus() {
        return emailAddressStatus;
    }

    public void setEmailAddressStatus(WorkflowEntityStatusTransfer emailAddressStatus) {
        this.emailAddressStatus = emailAddressStatus;
    }

    public WorkflowEntityStatusTransfer getEmailAddressVerification() {
        return emailAddressVerification;
    }

    public void setEmailAddressVerification(WorkflowEntityStatusTransfer emailAddressVerification) {
        this.emailAddressVerification = emailAddressVerification;
    }
    
}
