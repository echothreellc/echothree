// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.comment.server;

import com.echothree.control.user.comment.common.CommentRemote;
import com.echothree.control.user.comment.common.form.*;
import com.echothree.control.user.comment.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CommentBean
        extends CommentFormsImpl
        implements CommentRemote, CommentLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CommentBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Comment Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentType(UserVisitPK userVisitPK, CreateCommentTypeForm form) {
        return new CreateCommentTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentType(UserVisitPK userVisitPK, GetCommentTypeForm form) {
        return new GetCommentTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentTypes(UserVisitPK userVisitPK, GetCommentTypesForm form) {
        return new GetCommentTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommentType(UserVisitPK userVisitPK, EditCommentTypeForm form) {
        return new EditCommentTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommentType(UserVisitPK userVisitPK, DeleteCommentTypeForm form) {
        return new DeleteCommentTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Comment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentTypeDescription(UserVisitPK userVisitPK, CreateCommentTypeDescriptionForm form) {
        return new CreateCommentTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentTypeDescriptions(UserVisitPK userVisitPK, GetCommentTypeDescriptionsForm form) {
        return new GetCommentTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommentTypeDescription(UserVisitPK userVisitPK, EditCommentTypeDescriptionForm form) {
        return new EditCommentTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommentTypeDescription(UserVisitPK userVisitPK, DeleteCommentTypeDescriptionForm form) {
        return new DeleteCommentTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageType(UserVisitPK userVisitPK, CreateCommentUsageTypeForm form) {
        return new CreateCommentUsageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentUsageType(UserVisitPK userVisitPK, GetCommentUsageTypeForm form) {
        return new GetCommentUsageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentUsageTypes(UserVisitPK userVisitPK, GetCommentUsageTypesForm form) {
        return new GetCommentUsageTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommentUsageType(UserVisitPK userVisitPK, EditCommentUsageTypeForm form) {
        return new EditCommentUsageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommentUsageType(UserVisitPK userVisitPK, DeleteCommentUsageTypeForm form) {
        return new DeleteCommentUsageTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageTypeDescription(UserVisitPK userVisitPK, CreateCommentUsageTypeDescriptionForm form) {
        return new CreateCommentUsageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentUsageTypeDescriptions(UserVisitPK userVisitPK, GetCommentUsageTypeDescriptionsForm form) {
        return new GetCommentUsageTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommentUsageTypeDescription(UserVisitPK userVisitPK, EditCommentUsageTypeDescriptionForm form) {
        return new EditCommentUsageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommentUsageTypeDescription(UserVisitPK userVisitPK, DeleteCommentUsageTypeDescriptionForm form) {
        return new DeleteCommentUsageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Comments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComment(UserVisitPK userVisitPK, CreateCommentForm form) {
        return new CreateCommentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editComment(UserVisitPK userVisitPK, EditCommentForm form) {
        return new EditCommentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommentStatusChoices(UserVisitPK userVisitPK, GetCommentStatusChoicesForm form) {
        return new GetCommentStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCommentStatus(UserVisitPK userVisitPK, SetCommentStatusForm form) {
        return new SetCommentStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getComment(UserVisitPK userVisitPK, GetCommentForm form) {
        return new GetCommentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteComment(UserVisitPK userVisitPK, DeleteCommentForm form) {
        return new DeleteCommentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsage(UserVisitPK userVisitPK, CreateCommentUsageForm form) {
        return new CreateCommentUsageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommentUsage(UserVisitPK userVisitPK, DeleteCommentUsageForm form) {
        return new DeleteCommentUsageCommand(userVisitPK, form).run();
    }
    
}
