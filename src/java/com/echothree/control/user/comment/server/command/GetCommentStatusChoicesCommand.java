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

import com.echothree.control.user.comment.common.form.GetCommentStatusChoicesForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetCommentStatusChoicesCommand
        extends BaseSimpleCommand<GetCommentStatusChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CommentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultCommentStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCommentStatusChoicesCommand */
    public GetCommentStatusChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommentResultFactory.getGetCommentStatusChoicesResult();
        var commentControl = Session.getModelController(CommentControl.class);
        var commentTypeName = form.getCommentTypeName();
        var commentName = form.getCommentName();
        var parameterCount = (commentTypeName == null ? 0 : 1) + (commentName == null ? 0 : 1);

        if(parameterCount == 1) {
            CommentType commentType = null;
            Comment comment = null;
            
            if(commentTypeName != null) {
                var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, ComponentVendors.ECHO_THREE.name(), EntityTypes.Comment.name());
                
                if(!hasExecutionErrors()) {
                    commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                    
                    if(commentType == null) {
                        addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), ComponentVendors.ECHO_THREE.name(), EntityTypes.Comment.name(),
                                commentTypeName);
                    }
                }
            } else {
                comment = commentControl.getCommentByName(commentName);
                
                if(comment == null) {
                    addExecutionError(ExecutionErrors.UnknownCommentName.name(), commentName);
                } else {
                    commentType = comment.getLastDetail().getCommentType();
                }
            }

            if(!hasExecutionErrors()) {
                var defaultCommentStatusChoice = form.getDefaultCommentStatusChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setCommentStatusChoices(commentControl.getCommentStatusChoices(this, defaultCommentStatusChoice, getPreferredLanguage(),
                        allowNullChoice, commentType, comment, getPartyPK()));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
