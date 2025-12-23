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

package com.echothree.model.control.core.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class BaseEncryptionKeyTransfer
        extends BaseTransfer {

    private String baseEncryptionKeyName;
    private WorkflowEntityStatusTransfer baseEncryptionKeyStatus;

    /** Creates a new instance of BaseEncryptionKeyTransfer */
    public BaseEncryptionKeyTransfer(String baseEncryptionKeyName,
            WorkflowEntityStatusTransfer baseEncryptionKeyStatus) {
        this.baseEncryptionKeyName = baseEncryptionKeyName;
        this.baseEncryptionKeyStatus = baseEncryptionKeyStatus;
    }

    public String getBaseEncryptionKeyName() {
        return baseEncryptionKeyName;
    }

    public void setBaseEncryptionKeyName(String baseEncryptionKeyName) {
        this.baseEncryptionKeyName = baseEncryptionKeyName;
    }

    public WorkflowEntityStatusTransfer getBaseEncryptionKeyStatus() {
        return baseEncryptionKeyStatus;
    }

    public void setBaseEncryptionKeyStatus(WorkflowEntityStatusTransfer baseEncryptionKeyStatus) {
        this.baseEncryptionKeyStatus = baseEncryptionKeyStatus;
    }
    
}