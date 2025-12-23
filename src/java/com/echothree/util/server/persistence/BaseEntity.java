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

import com.echothree.util.common.exception.PersistenceException;
import com.echothree.util.common.exception.PersistenceReadOnlyException;
import com.echothree.util.common.persistence.BasePK;

public abstract class BaseEntity {
    
    static private final String INVALID_CONSTRUCTOR_EXCEPTION = "Invalid Constructor";
    static private final String READ_ONLY_EXCEPTION = "Entity is Read-Only";
    
    protected EntityPermission _entityPermission;
    
    /** Creates a new instance of BaseEntity */
    protected BaseEntity() {
        throw new PersistenceException(INVALID_CONSTRUCTOR_EXCEPTION);
    }
    
    /** Creates a new instance of BaseEntity */
    protected BaseEntity(EntityPermission entityPermission) {
        _entityPermission = entityPermission;
    }
    
    public EntityPermission getEntityPermission() {
        return _entityPermission;
    }
    
    protected void checkReadWrite()
    throws PersistenceReadOnlyException {
        if(_entityPermission.equals(EntityPermission.READ_ONLY))
            throw new PersistenceReadOnlyException(READ_ONLY_EXCEPTION);
    }
    
    public abstract void store();
    
    public abstract void remove();
    
    public abstract BasePK getPrimaryKey();
    
    public abstract BaseFactory getBaseFactoryInstance();
    
    public abstract boolean hasBeenModified();
    
}
