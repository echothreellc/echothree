// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.content.server.logic;

import com.echothree.model.control.content.common.exception.UnknownContentPageLayoutNameException;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class ContentPageLayoutLogic
    extends BaseLogic {
    
    private ContentPageLayoutLogic() {
        super();
    }
    
    private static class ContentPageLayoutLogicHolder {
        static ContentPageLayoutLogic instance = new ContentPageLayoutLogic();
    }
    
    public static ContentPageLayoutLogic getInstance() {
        return ContentPageLayoutLogicHolder.instance;
    }

    public ContentPageLayout getContentPageLayoutByName(final ExecutionErrorAccumulator eea, final String contentPageLayoutName,
            final EntityPermission entityPermission) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentPageLayout contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName, entityPermission);

        if(contentPageLayout == null) {
            handleExecutionError(UnknownContentPageLayoutNameException.class, eea, ExecutionErrors.UnknownContentPageLayoutName.name(), contentPageLayoutName);
        }

        return contentPageLayout;
    }

    public ContentPageLayout getContentPageLayoutByName(final ExecutionErrorAccumulator eea, final String contentPageLayoutName) {
        return getContentPageLayoutByName(eea, contentPageLayoutName, EntityPermission.READ_ONLY);
    }

    public ContentPageLayout getContentPageLayoutByNameForUpdate(final ExecutionErrorAccumulator eea, final String contentPageLayoutName) {
        return getContentPageLayoutByName(eea, contentPageLayoutName, EntityPermission.READ_WRITE);
    }

    public ContentPageLayout getContentPageLayoutByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        ContentPageLayout contentPageLayout = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.ContentPageLayout.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
            
            contentPageLayout = contentControl.getContentPageLayoutByEntityInstance(entityInstance, entityPermission);
        }

        return contentPageLayout;
    }
    
    public ContentPageLayout getContentPageLayoutByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getContentPageLayoutByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public ContentPageLayout getContentPageLayoutByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getContentPageLayoutByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }

}
