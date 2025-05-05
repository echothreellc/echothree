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
        return new CreateCommentTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentType(UserVisitPK userVisitPK, GetCommentTypeForm form) {
        return new GetCommentTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentTypes(UserVisitPK userVisitPK, GetCommentTypesForm form) {
        return new GetCommentTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentType(UserVisitPK userVisitPK, EditCommentTypeForm form) {
        return new EditCommentTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentType(UserVisitPK userVisitPK, DeleteCommentTypeForm form) {
        return new DeleteCommentTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentTypeDescription(UserVisitPK userVisitPK, CreateCommentTypeDescriptionForm form) {
        return new CreateCommentTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentTypeDescriptions(UserVisitPK userVisitPK, GetCommentTypeDescriptionsForm form) {
        return new GetCommentTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentTypeDescription(UserVisitPK userVisitPK, EditCommentTypeDescriptionForm form) {
        return new EditCommentTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentTypeDescription(UserVisitPK userVisitPK, DeleteCommentTypeDescriptionForm form) {
        return new DeleteCommentTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageType(UserVisitPK userVisitPK, CreateCommentUsageTypeForm form) {
        return new CreateCommentUsageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageType(UserVisitPK userVisitPK, GetCommentUsageTypeForm form) {
        return new GetCommentUsageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageTypes(UserVisitPK userVisitPK, GetCommentUsageTypesForm form) {
        return new GetCommentUsageTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentUsageType(UserVisitPK userVisitPK, EditCommentUsageTypeForm form) {
        return new EditCommentUsageTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsageType(UserVisitPK userVisitPK, DeleteCommentUsageTypeForm form) {
        return new DeleteCommentUsageTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageTypeDescription(UserVisitPK userVisitPK, CreateCommentUsageTypeDescriptionForm form) {
        return new CreateCommentUsageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageTypeDescriptions(UserVisitPK userVisitPK, GetCommentUsageTypeDescriptionsForm form) {
        return new GetCommentUsageTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentUsageTypeDescription(UserVisitPK userVisitPK, EditCommentUsageTypeDescriptionForm form) {
        return new EditCommentUsageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsageTypeDescription(UserVisitPK userVisitPK, DeleteCommentUsageTypeDescriptionForm form) {
        return new DeleteCommentUsageTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComment(UserVisitPK userVisitPK, CreateCommentForm form) {
        return new CreateCommentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editComment(UserVisitPK userVisitPK, EditCommentForm form) {
        return new EditCommentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentStatusChoices(UserVisitPK userVisitPK, GetCommentStatusChoicesForm form) {
        return new GetCommentStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCommentStatus(UserVisitPK userVisitPK, SetCommentStatusForm form) {
        return new SetCommentStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComment(UserVisitPK userVisitPK, GetCommentForm form) {
        return new GetCommentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteComment(UserVisitPK userVisitPK, DeleteCommentForm form) {
        return new DeleteCommentCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsage(UserVisitPK userVisitPK, CreateCommentUsageForm form) {
        return new CreateCommentUsageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsage(UserVisitPK userVisitPK, DeleteCommentUsageForm form) {
        return new DeleteCommentUsageCommand().run(userVisitPK, form);
    }
    
}
