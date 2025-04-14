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

import com.echothree.model.control.core.common.transfer.AppearanceTextDecorationTransfer;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.AppearanceTextDecoration;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AppearanceTextDecorationTransferCache
        extends BaseCoreTransferCache<AppearanceTextDecoration, AppearanceTextDecorationTransfer> {

    AppearanceControl appearanceControl = Session.getModelController(AppearanceControl.class);
    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of AppearanceTextDecorationTransferCache */
    public AppearanceTextDecorationTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    public AppearanceTextDecorationTransfer getAppearanceTextDecorationTransfer(AppearanceTextDecoration appearanceTextDecoration) {
        var appearanceTextDecorationTransfer = get(appearanceTextDecoration);

        if(appearanceTextDecorationTransfer == null) {
            var appearance = appearanceControl.getAppearanceTransfer(userVisit, appearanceTextDecoration.getAppearance());
            var textDecoration = coreControl.getTextDecorationTransfer(userVisit, appearanceTextDecoration.getTextDecoration());

            appearanceTextDecorationTransfer = new AppearanceTextDecorationTransfer(appearance, textDecoration);
            put(appearanceTextDecoration, appearanceTextDecorationTransfer);
        }

        return appearanceTextDecorationTransfer;
    }

}
