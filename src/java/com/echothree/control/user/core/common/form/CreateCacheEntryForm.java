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

package com.echothree.control.user.core.common.form;

import com.echothree.control.user.core.common.spec.CacheEntrySpec;
import com.echothree.control.user.core.common.spec.MimeTypeSpec;
import com.echothree.util.common.persistence.type.ByteArray;
import java.util.Set;

public interface CreateCacheEntryForm
        extends CacheEntrySpec, MimeTypeSpec {
    
    String getValidForTime();
    void setValidForTime(String validForTime);

    String getValidForTimeUnitOfMeasureTypeName();
    void setValidForTimeUnitOfMeasureTypeName(String validForTimeUnitOfMeasureTypeName);

    ByteArray getBlob();
    void setBlob(ByteArray blob);

    String getClob();
    void setClob(String clob);

    Set<String> getEntityRefs();
    void setEntityRefs(Set<String> entityRefs);

}
