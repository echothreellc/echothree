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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.GetCommentUsageTypeForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommentUsageTypeCommand
        extends BaseSingleEntityCommand<CommentUsageType, GetCommentUsageTypeForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CommentControl commentControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetCommentUsageTypeCommand */
    public GetCommentUsageTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommentUsageType getEntity() {
        CommentUsageType commentUsageType = null;
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

        if(!hasExecutionErrors()) {
            var commentTypeName = form.getCommentTypeName();
            var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);

            if(commentType != null) {
                var commentUsageTypeName = form.getCommentUsageTypeName();
                commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);

                if(commentUsageType != null) {
                    sendEvent(commentUsageType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), componentVendorName, entityTypeName,
                            commentTypeName, commentUsageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), componentVendorName, entityTypeName, commentTypeName);
            }
        }

        return commentUsageType;
    }

    @Override
    protected BaseResult getResult(CommentUsageType commentUsageType) {
        var result = CommentResultFactory.getGetCommentUsageTypeResult();

        if(commentUsageType != null) {
            result.setCommentUsageType(commentControl.getCommentUsageTypeTransfer(getUserVisit(), commentUsageType));
        }

        return result;
    }

}
