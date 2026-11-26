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

import com.echothree.control.user.comment.common.spec.CommentUniversalSpec;
import com.echothree.model.control.comment.common.exception.UnknownCommentNameException;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommentLogic
        extends BaseLogic {

    @Inject
    CommentControl commentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected CommentLogic() {
        super();
    }

    public Comment getCommentByName(final ExecutionErrorAccumulator eea, final String commentName,
            final EntityPermission entityPermission) {
        var comment = commentControl.getCommentByName(commentName, entityPermission);

        if(comment == null) {
            handleExecutionError(UnknownCommentNameException.class, eea, ExecutionErrors.UnknownCommentName.name(), commentName);
        }

        return comment;
    }

    public Comment getCommentByName(final ExecutionErrorAccumulator eea, final String commentName) {
        return getCommentByName(eea, commentName, EntityPermission.READ_ONLY);
    }

    public Comment getCommentByNameForUpdate(final ExecutionErrorAccumulator eea, final String commentName) {
        return getCommentByName(eea, commentName, EntityPermission.READ_WRITE);
    }

    public Comment getCommentByUniversalSpec(final ExecutionErrorAccumulator eea, final CommentUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Comment comment = null;
        var commentName = universalSpec.getCommentName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (commentName == null ? 0 : 1) + possibleEntitySpecs;

        if(parameterCount == 1) {
            if(commentName == null) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Comment.name());

                if(!hasExecutionErrors(eea)) {
                    comment = commentControl.getCommentByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                comment = getCommentByName(eea, commentName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return comment;
    }

    public Comment getCommentByUniversalSpec(final ExecutionErrorAccumulator eea, final CommentUniversalSpec universalSpec) {
        return getCommentByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Comment getCommentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final CommentUniversalSpec universalSpec) {
        return getCommentByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
