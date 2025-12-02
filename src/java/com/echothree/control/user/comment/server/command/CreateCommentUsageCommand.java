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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.CreateCommentUsageForm;
import com.echothree.model.control.comment.server.control.CommentControl;
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
public class CreateCommentUsageCommand
        extends BaseSimpleCommand<CreateCommentUsageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCommentUsageCommand */
    public CreateCommentUsageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var commentControl = Session.getModelController(CommentControl.class);
        var commentName = form.getCommentName();
        var comment = commentControl.getCommentByName(commentName);
        
        if(comment != null) {
            var commentUsageTypeName = form.getCommentUsageTypeName();
            var commentUsageType = commentControl.getCommentUsageTypeByName(comment.getLastDetail().getCommentType(), commentUsageTypeName);
            
            if(commentUsageType != null) {
                var commentUsage = commentControl.getCommentUsage(comment, commentUsageType);
                
                if(commentUsage == null) {
                    commentControl.createCommentUsage(comment, commentUsageType, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCommentUsage.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), commentUsageTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommentName.name(), commentName);
        }
        
        return null;
    }
    
}
