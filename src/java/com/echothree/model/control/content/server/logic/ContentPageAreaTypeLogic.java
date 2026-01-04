// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.content.common.spec.ContentPageAreaTypeUniversalSpec;
import com.echothree.model.control.content.common.exception.DuplicateContentPageAreaTypeNameException;
import com.echothree.model.control.content.common.exception.UnknownContentPageAreaTypeNameException;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
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
public class ContentPageAreaTypeLogic
    extends BaseLogic {

    protected ContentPageAreaTypeLogic() {
        super();
    }

    public static ContentPageAreaTypeLogic getInstance() {
        return CDI.current().select(ContentPageAreaTypeLogic.class).get();
    }

    public ContentPageAreaType createContentPageAreaType(final ExecutionErrorAccumulator eea, final String contentPageAreaTypeName,
            final Language language, final String description, final BasePK createdBy) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageAreaType = contentControl.getContentPageAreaTypeByName(contentPageAreaTypeName);

        if(contentPageAreaType == null) {
            contentPageAreaType = contentControl.createContentPageAreaType(contentPageAreaTypeName, createdBy);

            if(description != null) {
                contentControl.createContentPageAreaTypeDescription(contentPageAreaType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateContentPageAreaTypeNameException.class, eea, ExecutionErrors.DuplicateContentPageAreaTypeName.name(), contentPageAreaTypeName);
        }

        return contentPageAreaType;
    }

    public ContentPageAreaType getContentPageAreaTypeByName(final ExecutionErrorAccumulator eea, final String contentPageAreaTypeName,
            final EntityPermission entityPermission) {
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageAreaType = contentControl.getContentPageAreaTypeByName(contentPageAreaTypeName, entityPermission);

        if(contentPageAreaType == null) {
            handleExecutionError(UnknownContentPageAreaTypeNameException.class, eea, ExecutionErrors.UnknownContentPageAreaTypeName.name(), contentPageAreaTypeName);
        }

        return contentPageAreaType;
    }

    public ContentPageAreaType getContentPageAreaTypeByName(final ExecutionErrorAccumulator eea, final String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(eea, contentPageAreaTypeName, EntityPermission.READ_ONLY);
    }

    public ContentPageAreaType getContentPageAreaTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String contentPageAreaTypeName) {
        return getContentPageAreaTypeByName(eea, contentPageAreaTypeName, EntityPermission.READ_WRITE);
    }

    public ContentPageAreaType getContentPageAreaTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContentPageAreaTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ContentPageAreaType contentPageAreaType = null;
        var contentControl = Session.getModelController(ContentControl.class);
        var contentPageAreaTypeName = universalSpec.getContentPageAreaTypeName();
        var parameterCount = (contentPageAreaTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(contentPageAreaTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContentPageAreaType.name());

                    if(!eea.hasExecutionErrors()) {
                        contentPageAreaType = contentControl.getContentPageAreaTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contentPageAreaType = getContentPageAreaTypeByName(eea, contentPageAreaTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contentPageAreaType;
    }

    public ContentPageAreaType getContentPageAreaTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContentPageAreaTypeUniversalSpec universalSpec) {
        return getContentPageAreaTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ContentPageAreaType getContentPageAreaTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContentPageAreaTypeUniversalSpec universalSpec) {
        return getContentPageAreaTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }
}
