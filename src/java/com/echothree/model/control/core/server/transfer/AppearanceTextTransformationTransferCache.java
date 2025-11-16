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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.AppearanceTextTransformationTransfer;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AppearanceTextTransformationTransferCache
        extends BaseCoreTransferCache<AppearanceTextTransformation, AppearanceTextTransformationTransfer> {

    AppearanceControl appearanceControl = Session.getModelController(AppearanceControl.class);
    TextControl textControl = Session.getModelController(TextControl.class);

    /** Creates a new instance of AppearanceTextTransformationTransferCache */
    protected AppearanceTextTransformationTransferCache() {
        super();
    }

    public AppearanceTextTransformationTransfer getAppearanceTextTransformationTransfer(UserVisit userVisit, AppearanceTextTransformation appearanceTextTransformation) {
        var appearanceTextTransformationTransfer = get(appearanceTextTransformation);

        if(appearanceTextTransformationTransfer == null) {
            var appearance = appearanceControl.getAppearanceTransfer(userVisit, appearanceTextTransformation.getAppearance());
            var textTransformation = textControl.getTextTransformationTransfer(userVisit, appearanceTextTransformation.getTextTransformation());

            appearanceTextTransformationTransfer = new AppearanceTextTransformationTransfer(appearance, textTransformation);
            put(userVisit, appearanceTextTransformation, appearanceTextTransformationTransfer);
        }

        return appearanceTextTransformationTransfer;
    }

}
