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

package com.echothree.model.control.tag.server.logic;

import com.echothree.control.user.tag.common.spec.TagScopeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.tag.common.exception.DuplicateTagScopeNameException;
import com.echothree.model.control.tag.common.exception.UnknownDefaultTagScopeException;
import com.echothree.model.control.tag.common.exception.UnknownTagScopeNameException;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TagScopeLogic
    extends BaseLogic {

    protected TagScopeLogic() {
        super();
    }

    public static TagScopeLogic getInstance() {
        return CDI.current().select(TagScopeLogic.class).get();
    }

    public TagScope createTagScope(final ExecutionErrorAccumulator eea, final String tagScopeName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScope = tagControl.getTagScopeByName(tagScopeName);

        if(tagScope == null) {
            tagScope = tagControl.createTagScope(tagScopeName, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                tagControl.createTagScopeDescription(tagScope, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateTagScopeNameException.class, eea, ExecutionErrors.DuplicateTagScopeName.name(), tagScopeName);
        }

        return tagScope;
    }

    public TagScope getTagScopeByName(final ExecutionErrorAccumulator eea, final String tagScopeName,
            final EntityPermission entityPermission) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScope = tagControl.getTagScopeByName(tagScopeName, entityPermission);

        if(tagScope == null) {
            handleExecutionError(UnknownTagScopeNameException.class, eea, ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }

        return tagScope;
    }

    public TagScope getTagScopeByName(final ExecutionErrorAccumulator eea, final String tagScopeName) {
        return getTagScopeByName(eea, tagScopeName, EntityPermission.READ_ONLY);
    }

    public TagScope getTagScopeByNameForUpdate(final ExecutionErrorAccumulator eea, final String tagScopeName) {
        return getTagScopeByName(eea, tagScopeName, EntityPermission.READ_WRITE);
    }

    public TagScope getTagScopeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TagScopeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        TagScope tagScope = null;
        var tagControl = Session.getModelController(TagControl.class);
        var tagScopeName = universalSpec.getTagScopeName();
        var parameterCount = (tagScopeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    tagScope = tagControl.getDefaultTagScope(entityPermission);

                    if(tagScope == null) {
                        handleExecutionError(UnknownDefaultTagScopeException.class, eea, ExecutionErrors.UnknownDefaultTagScope.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(tagScopeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.TagScope.name());

                    if(!eea.hasExecutionErrors()) {
                        tagScope = tagControl.getTagScopeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    tagScope = getTagScopeByName(eea, tagScopeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return tagScope;
    }

    public TagScope getTagScopeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TagScopeUniversalSpec universalSpec, boolean allowDefault) {
        return getTagScopeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public TagScope getTagScopeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TagScopeUniversalSpec universalSpec, boolean allowDefault) {
        return getTagScopeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteTagScope(final ExecutionErrorAccumulator eea, final TagScope tagScope,
            final BasePK deletedBy) {
        var tagControl = Session.getModelController(TagControl.class);

        tagControl.deleteTagScope(tagScope, deletedBy);
    }

}
