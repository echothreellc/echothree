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

import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.scale.common.transfer.ScaleTransfer;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ScaleTransferCache
        extends BaseScaleTransferCache<Scale, ScaleTransfer> {

    ScaleControl scaleControl = Session.getModelController(ScaleControl.class);
    ServerControl serverControl = Session.getModelController(ServerControl.class);

    /** Creates a new instance of ScaleTransferCache */
    protected ScaleTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }
    
    public ScaleTransfer getScaleTransfer(UserVisit userVisit, Scale scale) {
        var scaleTransfer = get(scale);
        
        if(scaleTransfer == null) {
            var scaleDetail = scale.getLastDetail();
            var scaleName = scaleDetail.getScaleName();
            var scaleType = scaleControl.getScaleTypeTransfer(userVisit, scaleDetail.getScaleType());
            var serverService = serverControl.getServerServiceTransfer(userVisit, scaleDetail.getServerService());
            var isDefault = scaleDetail.getIsDefault();
            var sortOrder = scaleDetail.getSortOrder();
            var description = scaleControl.getBestScaleDescription(scale, getLanguage(userVisit));
            
            scaleTransfer = new ScaleTransfer(scaleName, scaleType, serverService, isDefault, sortOrder, description);
            put(userVisit, scale, scaleTransfer);
        }
        
        return scaleTransfer;
    }
    
}
