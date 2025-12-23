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

package com.echothree.model.control.contact.common.exception;

import com.echothree.util.common.message.Message;

public class UnknownContactMechanismPurposeNameException
        extends BaseContactException {
    
    /** Creates a new instance of UnknownContactMechanismPurposeNameException */
    public UnknownContactMechanismPurposeNameException() {
        super();
    }
    
    /** Creates a new instance of UnknownContactMechanismPurposeNameException */
    public UnknownContactMechanismPurposeNameException(String message) {
        super(message);
    }
    
    /** Creates a new instance of UnknownContactMechanismPurposeNameException */
    public UnknownContactMechanismPurposeNameException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of UnknownContactMechanismPurposeNameException */
    public UnknownContactMechanismPurposeNameException(Message message) {
        super(message);
    }

}
