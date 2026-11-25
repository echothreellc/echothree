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

import com.echothree.model.control.comment.common.exception.UnknownCommentTypeNameException;
import com.echothree.model.control.comment.server.control.CommentControl;
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
    EntityTypeLogic entityTypeLogic;

    protected CommentTypeLogic() {
        super();
    }

    public CommentType getCommentTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String commentTypeName, final EntityPermission entityPermission) {
        var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName, entityPermission);

        if(commentType == null) {
            handleExecutionError(UnknownCommentTypeNameException.class, eea, ExecutionErrors.UnknownCommentTypeName.name(),
                    entityType.getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityType.getLastDetail().getEntityTypeName(), commentTypeName);
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

        if(eea == null || !eea.hasExecutionErrors()) {
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

}
