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

package com.echothree.control.user.core.common.result;

import com.echothree.control.user.core.common.edit.EntityGeoPointDefaultEdit;
import com.echothree.model.control.core.common.transfer.EntityGeoPointDefaultTransfer;
import com.echothree.util.common.command.BaseEditResult;

public interface EditEntityGeoPointDefaultResult
        extends BaseEditResult<EntityGeoPointDefaultEdit> {
    
    EntityGeoPointDefaultTransfer getEntityGeoPointDefault();
    void setEntityGeoPointDefault(EntityGeoPointDefaultTransfer entityGeoPointDefault);
    
}
