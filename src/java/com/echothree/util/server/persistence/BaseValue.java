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

package com.echothree.util.server.persistence;

import com.echothree.util.common.exception.PersistenceNotNullException;
import com.echothree.util.common.persistence.BasePK;

public abstract class BaseValue<PK extends BasePK> {
    
    protected Long entityId;
    protected PK _primaryKey;
    
    /** Creates a new instance of BaseValue */
    protected BaseValue(PK basePK) {
        entityId = basePK.getEntityId();
        _primaryKey = basePK;
    }
    
    /** Creates a new instance of BaseValue */
    protected BaseValue() {
        entityId = null;
        _primaryKey = null;
    }
    
    public abstract BaseFactory getBaseFactoryInstance();
    
    protected boolean hasIdentity() {
        return entityId != null;
    }
    
    public abstract PK getPrimaryKey();
    
    public Long getEntityId() {
        return entityId;
    }
    
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
        _primaryKey = null;
    }
    
    protected void checkForNull(Object o)
            throws PersistenceNotNullException {
        var isNull = false;
        
        if(o == null) {
            isNull = true;
        } else {
            if(o instanceof String) {
                if(((String)o).length() == 0)
                    isNull = true;
            }
        }
        
        if(isNull) {
            throw new PersistenceNotNullException();
        }
    }
    
    public abstract boolean hasBeenModified();
    
    public abstract void clearHasBeenModified();
    
}
