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

import com.echothree.model.control.scale.common.transfer.ScaleUseTypeTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ScaleUseTypeTransferCache
        extends BaseScaleTransferCache<ScaleUseType, ScaleUseTypeTransfer> {

    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);

    /** Creates a new instance of ScaleUseTypeTransferCache */
    public ScaleUseTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    public ScaleUseTypeTransfer getScaleUseTypeTransfer(UserVisit userVisit, ScaleUseType scaleUseType) {
        var scaleUseTypeTransfer = get(scaleUseType);

        if(scaleUseTypeTransfer == null) {
            var scaleUseTypeDetail = scaleUseType.getLastDetail();
            var scaleUseTypeName = scaleUseTypeDetail.getScaleUseTypeName();
            var isDefault = scaleUseTypeDetail.getIsDefault();
            var sortOrder = scaleUseTypeDetail.getSortOrder();
            var description = scaleControl.getBestScaleUseTypeDescription(scaleUseType, getLanguage(userVisit));

            scaleUseTypeTransfer = new ScaleUseTypeTransfer(scaleUseTypeName, isDefault, sortOrder, description);
            put(userVisit, scaleUseType, scaleUseTypeTransfer);
        }

        return scaleUseTypeTransfer;
    }

}
