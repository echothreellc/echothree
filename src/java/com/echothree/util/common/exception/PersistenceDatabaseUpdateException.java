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

import com.echothree.util.common.message.Message;

public class PersistenceDatabaseUpdateException
        extends PersistenceDatabaseException {
    
    /** Creates a new instance of PersistenceDatabaseUpdateException */
    public PersistenceDatabaseUpdateException() {
        super();
    }
    
    /** Creates a new instance of PersistenceDatabaseUpdateException */
    public PersistenceDatabaseUpdateException(String message) {
        super(message);
    }
    
    /** Creates a new instance of PersistenceDatabaseUpdateException */
    public PersistenceDatabaseUpdateException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of PersistenceDatabaseUpdateException */
    public PersistenceDatabaseUpdateException(Message message) {
        super(message);
    }

}
