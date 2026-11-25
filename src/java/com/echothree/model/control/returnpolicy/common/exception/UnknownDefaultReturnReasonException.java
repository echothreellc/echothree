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

package com.echothree.model.control.returnpolicy.common.exception;

import com.echothree.util.common.message.Message;

public class UnknownDefaultReturnReasonException
        extends BaseReturnPolicyException {
    
    /** Creates a new instance of UnknownDefaultReturnReasonException */
    public UnknownDefaultReturnReasonException() {
        super();
    }
    
    /** Creates a new instance of UnknownDefaultReturnReasonException */
    public UnknownDefaultReturnReasonException(String message) {
        super(message);
    }
    
    /** Creates a new instance of UnknownDefaultReturnReasonException */
    public UnknownDefaultReturnReasonException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of UnknownDefaultReturnReasonException */
    public UnknownDefaultReturnReasonException(Message message) {
        super(message);
    }

}
