// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.util.common.persistence.BasePK;
import java.util.Collection;

public interface BaseFactory<PK extends BasePK, BE extends BaseEntity> {
    
    String getPKColumn();
    
    String getAllColumns();
    
    String getTableName();
    
    String getComponentVendorName();
    
    String getEntityTypeName();

    public void store(Session session, BE entity);
    
    void store(Session session, Collection<BE> entities);
    
    void store(Collection<BE> entities);
    
    public void remove(Session session, BE entity);
    
    public void remove(Session session, PK pk);
    
    public void remove(Session session, Collection<PK> pks);
    
    public void remove(Collection<PK> pks);
    
    public boolean validPK(Session session, PK pk);
    
}
