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

package com.echothree.util.common.command;

import com.echothree.util.common.message.Messages;
import java.io.Serializable;

public class SecurityResult
        implements Serializable {
    
    private Messages securityMessages;
    
    /** Creates a new instance of SecurityResult */
    public SecurityResult(Messages securityMessages) {
        this.securityMessages = securityMessages;
    }
    
    public Messages getSecurityMessages() {
        return securityMessages;
    }
    
    public Boolean getHasMessages() {
        return securityMessages == null? false: securityMessages.size(Messages.SECURITY_MESSAGE) != 0;
    }
    
    @Override
    public String toString() {
        return "{ securityMessages = " + securityMessages + " }";
    }
    
}
