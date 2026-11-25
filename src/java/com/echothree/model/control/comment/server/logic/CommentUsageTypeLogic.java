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

import com.echothree.control.user.comment.common.spec.CommentUsageTypeUniversalSpec;
import com.echothree.model.control.comment.common.exception.UnknownCommentUsageTypeNameException;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommentUsageTypeLogic
        extends BaseLogic {

    @Inject
    CommentControl commentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    CommentTypeLogic commentTypeLogic;

    protected CommentUsageTypeLogic() {
        super();
    }

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final CommentType commentType, final String commentUsageTypeName,
            final EntityPermission entityPermission) {
        var commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName, entityPermission);

        if(commentUsageType == null) {
            var commentTypeDetail = commentType.getLastDetail();
            var entityTypeDetail = commentTypeDetail.getEntityType().getLastDetail();

            handleExecutionError(UnknownCommentUsageTypeNameException.class, eea, ExecutionErrors.UnknownCommentUsageTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    commentTypeDetail.getCommentTypeName(), commentUsageTypeName);
        }

        return commentUsageType;
    }

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final CommentType commentType,
            final String commentUsageTypeName) {
        return getCommentUsageTypeByName(eea, commentType, commentUsageTypeName, EntityPermission.READ_ONLY);
    }

    public CommentUsageType getCommentUsageTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final CommentType commentType,
            final String commentUsageTypeName) {
        return getCommentUsageTypeByName(eea, commentType, commentUsageTypeName, EntityPermission.READ_WRITE);
    }

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName,
            final String commentTypeName, final String commentUsageTypeName, final EntityPermission entityPermission) {
        var commentType = commentTypeLogic.getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName);
        CommentUsageType commentUsageType = null;

        if(!hasExecutionErrors(eea)) {
            commentUsageType = getCommentUsageTypeByName(eea, commentType, commentUsageTypeName, entityPermission);
        }

        return commentUsageType;
    }

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName, final String commentUsageTypeName) {
        return getCommentUsageTypeByName(eea, componentVendorName, entityTypeName, commentTypeName, commentUsageTypeName, EntityPermission.READ_ONLY);
    }

    public CommentUsageType getCommentUsageTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName, final String commentUsageTypeName) {
        return getCommentUsageTypeByName(eea, componentVendorName, entityTypeName, commentTypeName, commentUsageTypeName, EntityPermission.READ_WRITE);
    }

    public CommentUsageType getCommentUsageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CommentUsageTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        CommentUsageType commentUsageType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var commentTypeName = universalSpec.getCommentTypeName();
        var commentUsageTypeName = universalSpec.getCommentUsageTypeName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(componentVendorName != null && entityTypeName != null && commentTypeName != null && possibleEntitySpecs == 0
                && commentUsageTypeName != null) {
            var commentType = commentTypeLogic.getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName);

            if(!hasExecutionErrors(eea)) {
                commentUsageType = getCommentUsageTypeByName(eea, commentType, commentUsageTypeName, entityPermission);
            }
        } else if(componentVendorName == null && entityTypeName == null && commentTypeName == null && commentUsageTypeName == null
                && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.CommentUsageType.name());

            if(!hasExecutionErrors(eea)) {
                commentUsageType = commentControl.getCommentUsageTypeByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return commentUsageType;
    }

    public CommentUsageType getCommentUsageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final CommentUsageTypeUniversalSpec universalSpec) {
        return getCommentUsageTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public CommentUsageType getCommentUsageTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final CommentUsageTypeUniversalSpec universalSpec) {
        return getCommentUsageTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
