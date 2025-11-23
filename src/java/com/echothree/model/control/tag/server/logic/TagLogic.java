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

package com.echothree.model.control.tag.server.logic;

import com.echothree.control.user.tag.common.spec.TagUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.tag.common.exception.DuplicateTagNameException;
import com.echothree.model.control.tag.common.exception.UnknownTagNameException;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.Tag;
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
public class TagLogic
    extends BaseLogic {

    protected TagLogic() {
        super();
    }

    public static TagLogic getInstance() {
        return CDI.current().select(TagLogic.class).get();
    }

    public Tag createTag(final ExecutionErrorAccumulator eea, final TagScope tagScope, final String tagName,
            final BasePK createdBy) {
        var tagControl = Session.getModelController(TagControl.class);
        var tag = tagControl.getTagByName(tagScope, tagName);

        if(tag == null) {
            tag = tagControl.createTag(tagScope, tagName, createdBy);
        } else {
            handleExecutionError(DuplicateTagNameException.class, eea, ExecutionErrors.DuplicateTagName.name(),
                    tagScope.getLastDetail().getTagScopeName(), tagName);
        }

        return tag;
    }

    public Tag getTagByName(final ExecutionErrorAccumulator eea, final TagScope tagScope, final String tagName,
            final EntityPermission entityPermission) {
        var tagControl = Session.getModelController(TagControl.class);
        var tag = tagControl.getTagByName(tagScope, tagName, entityPermission);

        if(tag == null) {
            handleExecutionError(UnknownTagNameException.class, eea, ExecutionErrors.UnknownTagName.name(),
                    tagScope.getLastDetail().getTagScopeName(), tagName);
        }

        return tag;
    }

    public Tag getTagByName(final ExecutionErrorAccumulator eea, final TagScope tagScope, final String tagName) {
        return getTagByName(eea, tagScope, tagName, EntityPermission.READ_ONLY);
    }

    public Tag getTagByNameForUpdate(final ExecutionErrorAccumulator eea, final TagScope tagScope, final String tagName) {
        return getTagByName(eea, tagScope, tagName, EntityPermission.READ_WRITE);
    }

    public Tag getTagByName(final ExecutionErrorAccumulator eea, final String tagScopeName, final String tagName,
            final EntityPermission entityPermission) {
        var tagScope = TagScopeLogic.getInstance().getTagScopeByName(eea, tagScopeName);

        return eea.hasExecutionErrors() ? null : getTagByName(eea, tagScope, tagName, entityPermission);
    }

    public Tag getTagByName(final ExecutionErrorAccumulator eea, final String tagScopeName, final String tagName) {
        return getTagByName(eea, tagScopeName, tagName, EntityPermission.READ_ONLY);
    }

    public Tag getTagByNameForUpdate(final ExecutionErrorAccumulator eea, final String tagScopeName, final String tagName) {
        return getTagByName(eea, tagScopeName, tagName, EntityPermission.READ_WRITE);
    }

    public Tag getTagByUniversalSpec(final ExecutionErrorAccumulator eea, final TagUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        var tagControl = Session.getModelController(TagControl.class);
        var tagScopeName = universalSpec.getTagScopeName();
        var tagName = universalSpec.getTagName();
        var possibleEntitySpecs = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        var parameterCount = (tagScopeName == null ? 0 : 1) + (tagName == null ? 0 : 1) + possibleEntitySpecs;
        Tag tag = null;

        switch(parameterCount) {
            case 1 -> {
                if(possibleEntitySpecs == 1) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Tag.name());

                    if(!eea.hasExecutionErrors()) {
                        tag = tagControl.getTagByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 2 -> {
                if(tagScopeName != null && tagName != null) {
                    tag = getTagByName(eea, tagScopeName, tagName, entityPermission);
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return tag;
    }

    public Tag getTagByUniversalSpec(final ExecutionErrorAccumulator eea, final TagUniversalSpec universalSpec) {
        return getTagByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Tag getTagByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final TagUniversalSpec universalSpec) {
        return getTagByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteTag(final ExecutionErrorAccumulator eea, final Tag tag,
            final BasePK deletedBy) {
        var tagControl = Session.getModelController(TagControl.class);

        tagControl.deleteTag(tag, deletedBy);
    }

}
