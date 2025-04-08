// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import java.sql.SQLException;

public class PersistenceUtils {
    
    private PersistenceUtils() {
        super();
    }
    
    private static class PersistenceUtilsHolder {
        static PersistenceUtils instance = new PersistenceUtils();
    }
    
    public static PersistenceUtils getInstance() {
        return PersistenceUtilsHolder.instance;
    }

    private final static String SqlState_IntegrityConstraintViolation = "23000";
    
    public boolean isIntegrityConstraintViolation(PersistenceDatabaseException pde) {
        var result = false;
        var cause = pde.getCause();

        if(cause instanceof SQLException se) {
            if(se.getSQLState().equals(SqlState_IntegrityConstraintViolation)) {
                result = true;
            }
        }
        
        return result;
    }
    
    public BasePK getBasePKFromEntityInstance(EntityInstance entityInstance) {
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

        return new BasePK(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                entityTypeDetail.getEntityTypeName(), entityInstance.getEntityUniqueId());
    }
    
}
