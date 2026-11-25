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

package com.echothree.model.control.comment.server.logic;

import com.echothree.control.user.comment.common.spec.CommentTypeUniversalSpec;
import com.echothree.model.control.comment.common.exception.UnknownCommentTypeNameException;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommentTypeLogic
        extends BaseLogic {

    @Inject
    CommentControl commentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    protected CommentTypeLogic() {
        super();
    }

    public CommentType getCommentTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String commentTypeName,
            final EntityPermission entityPermission) {
        var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName, entityPermission);

        if(commentType == null) {
            var entityTypeDetail = entityType.getLastDetail();

            handleExecutionError(UnknownCommentTypeNameException.class, eea, ExecutionErrors.UnknownCommentTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), commentTypeName);
        }

        return commentType;
    }

    public CommentType getCommentTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String commentTypeName) {
        return getCommentTypeByName(eea, entityType, commentTypeName, EntityPermission.READ_ONLY);
    }

    public CommentType getCommentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String commentTypeName) {
        return getCommentTypeByName(eea, entityType, commentTypeName, EntityPermission.READ_WRITE);
    }

    public CommentType getCommentTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName, final EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);
        CommentType commentType = null;

        if(!hasExecutionErrors(eea)) {
            commentType = getCommentTypeByName(eea, entityType, commentTypeName, entityPermission);
        }

        return commentType;
    }

    public CommentType getCommentTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName) {
        return getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName, EntityPermission.READ_ONLY);
    }

    public CommentType getCommentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName) {
        return getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName, EntityPermission.READ_WRITE);
    }

    public CommentType getCommentTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final CommentTypeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        CommentType commentType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var commentTypeName = universalSpec.getCommentTypeName();
        var nameParameterCount = (componentVendorName == null ? 0 : 1) + (entityTypeName == null ? 0 : 1)
                + (commentTypeName == null ? 0 : 1);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(nameParameterCount == 3 && possibleEntitySpecs == 0) {
            commentType = getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName, entityPermission);
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.CommentType.name());

            if(!hasExecutionErrors(eea)) {
                commentType = commentControl.getCommentTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return commentType;
    }

    public CommentType getCommentTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final CommentTypeUniversalSpec universalSpec) {
        return getCommentTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public CommentType getCommentTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final CommentTypeUniversalSpec universalSpec) {
        return getCommentTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
