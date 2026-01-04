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

package com.echothree.model.control.cancellationpolicy.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CancellationPolicyTranslationTransfer
        extends BaseTransfer {
    
    private CancellationPolicyTransfer cancellationPolicy;
    private LanguageTransfer language;
    private String description;
    private MimeTypeTransfer policyMimeType;
    private String policy;
    
    /** Creates a new instance of CancellationPolicyTranslationTransfer */
    public CancellationPolicyTranslationTransfer(CancellationPolicyTransfer cancellationPolicy, LanguageTransfer language, String description,
            MimeTypeTransfer policyMimeType, String policy) {
        this.cancellationPolicy = cancellationPolicy;
        this.language = language;
        this.description = description;
        this.policyMimeType = policyMimeType;
        this.policy = policy;
    }

    public CancellationPolicyTransfer getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(CancellationPolicyTransfer cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public LanguageTransfer getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MimeTypeTransfer getPolicyMimeType() {
        return policyMimeType;
    }

    public void setPolicyMimeType(MimeTypeTransfer policyMimeType) {
        this.policyMimeType = policyMimeType;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

}
