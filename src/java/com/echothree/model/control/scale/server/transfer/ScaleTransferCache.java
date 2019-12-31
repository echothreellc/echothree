// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.ServerServiceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.scale.common.transfer.ScaleTransfer;
import com.echothree.model.control.scale.common.transfer.ScaleTypeTransfer;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.entity.ScaleDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ScaleTransferCache
        extends BaseScaleTransferCache<Scale, ScaleTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

    /** Creates a new instance of ScaleTransferCache */
    public ScaleTransferCache(UserVisit userVisit, ScaleControl scaleControl) {
        super(userVisit, scaleControl);

        setIncludeEntityInstance(true);
    }
    
    public ScaleTransfer getScaleTransfer(Scale scale) {
        ScaleTransfer scaleTransfer = get(scale);
        
        if(scaleTransfer == null) {
            ScaleDetail scaleDetail = scale.getLastDetail();
            String scaleName = scaleDetail.getScaleName();
            ScaleTypeTransfer scaleType = scaleControl.getScaleTypeTransfer(userVisit, scaleDetail.getScaleType());
            ServerServiceTransfer serverService = coreControl.getServerServiceTransfer(userVisit, scaleDetail.getServerService());
            Boolean isDefault = scaleDetail.getIsDefault();
            Integer sortOrder = scaleDetail.getSortOrder();
            String description = scaleControl.getBestScaleDescription(scale, getLanguage());
            
            scaleTransfer = new ScaleTransfer(scaleName, scaleType, serverService, isDefault, sortOrder, description);
            put(scale, scaleTransfer);
        }
        
        return scaleTransfer;
    }
    
}
