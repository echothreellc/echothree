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

package com.echothree.util.server.string;

import com.echothree.model.data.core.server.entity.EntityInstance;
import com.fasterxml.uuid.Generators;

public class UuidUtils
        extends BaseIdUtils {
    
    private UuidUtils() {
        super();
    }
    
    private static class UuidUtilsHolder {
        static UuidUtils instance = new UuidUtils();
    }
    
    public static UuidUtils getInstance() {
        return UuidUtilsHolder.instance;
    }

    public String generateUuid(EntityInstance entityInstance) {
        return Generators.timeBasedEpochGenerator().construct(getEntityInstanceCreatedTime(entityInstance)).toString();
    }

}
