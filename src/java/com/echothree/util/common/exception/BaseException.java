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

package com.echothree.util.common.exception;

import com.echothree.model.control.core.common.CommandMessageTypes;
import com.echothree.util.common.message.Message;
import com.echothree.util.server.message.MessageUtils;

/**
 *  This is the common superclass for all application exceptions. This
 *  class and its subclasses support the chained exception facility that allows
 *  a root cause Throwable to be wrapped by this class or one of its
 *  descendants.
 */

public abstract class BaseException
        extends RuntimeException {
    
    protected BaseException() {
        super();
    }
    
    protected BaseException(String message) {
        super(message);
    }
    
    protected BaseException(Throwable cause) {
        super(cause);
    }

    private static String getExceptionMessage(Message message) {
        String result = null;

        try {
            // Check if we're running in the app server. If not, a ClassNotFoundException will be thrown.
            if(Class.forName("com.echothree.util.server.message.MessageUtils") != null) {
                result = MessageUtils.getInstance().getExceptionMessage(CommandMessageTypes.Error.name(), message);
            }
        } catch(ClassNotFoundException cnfe) {
            result = "??" + message.getKey() + "??";
        }

        return result;
    }

    protected BaseException(Message message) {
        super(getExceptionMessage(message));
    }

}
