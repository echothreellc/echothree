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

package com.echothree.model.control.scale.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.scale.common.transfer.ScaleTypeTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ScaleTypeTransferCache
        extends BaseScaleTransferCache<ScaleType, ScaleTypeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of ScaleTypeTransferCache */
    public ScaleTypeTransferCache(ScaleControl scaleControl) {
        super(scaleControl);
        
        setIncludeEntityInstance(true);
    }

    public ScaleTypeTransfer getScaleTypeTransfer(UserVisit userVisit, ScaleType scaleType) {
        var scaleTypeTransfer = get(scaleType);

        if(scaleTypeTransfer == null) {
            var scaleTypeDetail = scaleType.getLastDetail();
            var scaleTypeName = scaleTypeDetail.getScaleTypeName();
            var isDefault = scaleTypeDetail.getIsDefault();
            var sortOrder = scaleTypeDetail.getSortOrder();
            var description = scaleControl.getBestScaleTypeDescription(scaleType, getLanguage(userVisit));

            scaleTypeTransfer = new ScaleTypeTransfer(scaleTypeName, isDefault, sortOrder, description);
            put(userVisit, scaleType, scaleTypeTransfer);
        }

        return scaleTypeTransfer;
    }

}
