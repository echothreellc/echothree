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

package com.echothree.model.control.content.server.logic;

import com.echothree.control.user.content.common.spec.ContentPageLayoutUniversalSpec;
import com.echothree.model.control.content.common.exception.DuplicateContentPageLayoutNameException;
import com.echothree.model.control.content.common.exception.UnknownContentPageLayoutNameException;
import com.echothree.model.control.content.common.exception.UnknownDefaultContentPageLayoutException;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ContentPageLayoutLogic
    extends BaseLogic {

    protected ContentPageLayoutLogic() {
        super();
    }

    public static ContentPageLayoutLogic getInstance() {
        return CDI.current().select(ContentPageLayoutLogic.class).get();
    }

    public ContentPageLayout createContentPageLayout(final ExecutionErrorAccumulator eea, final String contentPageLayoutName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName);

        if(contentPageLayout == null) {
            contentPageLayout = contentControl.createContentPageLayout(contentPageLayoutName, isDefault, sortOrder, createdBy);

            if(description != null) {
                contentControl.createContentPageLayoutDescription(contentPageLayout, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateContentPageLayoutNameException.class, eea, ExecutionErrors.DuplicateContentPageLayoutName.name(), contentPageLayoutName);
        }

        return contentPageLayout;
    }

    public ContentPageLayout getContentPageLayoutByName(final ExecutionErrorAccumulator eea, final String contentPageLayoutName,
            final EntityPermission entityPermission) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageLayout = contentControl.getContentPageLayoutByName(contentPageLayoutName, entityPermission);

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

    public ContentPageLayout getContentPageLayoutByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContentPageLayoutUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ContentPageLayout contentPageLayout = null;
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageLayoutName = universalSpec.getContentPageLayoutName();
        var parameterCount = (contentPageLayoutName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    contentPageLayout = contentControl.getDefaultContentPageLayout(entityPermission);

                    if(contentPageLayout == null) {
                        handleExecutionError(UnknownDefaultContentPageLayoutException.class, eea, ExecutionErrors.UnknownDefaultContentPageLayout.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(contentPageLayoutName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContentPageLayout.name());

                    if(!eea.hasExecutionErrors()) {
                        contentPageLayout = contentControl.getContentPageLayoutByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contentPageLayout = getContentPageLayoutByName(eea, contentPageLayoutName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return contentPageLayout;
    }

    public ContentPageLayout getContentPageLayoutByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContentPageLayoutUniversalSpec universalSpec, boolean allowDefault) {
        return getContentPageLayoutByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContentPageLayout getContentPageLayoutByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContentPageLayoutUniversalSpec universalSpec, boolean allowDefault) {
        return getContentPageLayoutByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteContentPageLayout(final ExecutionErrorAccumulator eea, final ContentPageLayout contentPageLayout,
            final BasePK deletedBy) {
        var contentControl = Session.getModelController(ContentControl.class);

        contentControl.deleteContentPageLayout(contentPageLayout, deletedBy);
    }
}
