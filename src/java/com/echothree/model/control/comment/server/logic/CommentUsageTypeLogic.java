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

import com.echothree.model.control.comment.common.exception.UnknownCommentUsageTypeNameException;
import com.echothree.model.control.comment.server.control.CommentControl;
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
    CommentTypeLogic commentTypeLogic;

    protected CommentUsageTypeLogic() {
        super();
    }

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final CommentType commentType,
            final String commentUsageTypeName, final EntityPermission entityPermission) {
        var commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName, entityPermission);

        if(commentUsageType == null) {
            handleExecutionError(UnknownCommentUsageTypeNameException.class, eea, ExecutionErrors.UnknownCommentUsageTypeName.name(),
                    commentType.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                    commentType.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                    commentType.getLastDetail().getCommentTypeName(), commentUsageTypeName);
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

    public CommentUsageType getCommentUsageTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String commentTypeName, final String commentUsageTypeName, final EntityPermission entityPermission) {
        var commentType = commentTypeLogic.getCommentTypeByName(eea, componentVendorName, entityTypeName, commentTypeName);
        CommentUsageType commentUsageType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
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

}
