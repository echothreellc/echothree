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

package com.echothree.control.user.comment.server;

import com.echothree.control.user.comment.common.CommentRemote;
import com.echothree.control.user.comment.common.form.*;
import com.echothree.control.user.comment.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateCommentTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentType(UserVisitPK userVisitPK, GetCommentTypeForm form) {
        return CDI.current().select(GetCommentTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentTypes(UserVisitPK userVisitPK, GetCommentTypesForm form) {
        return CDI.current().select(GetCommentTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentType(UserVisitPK userVisitPK, EditCommentTypeForm form) {
        return CDI.current().select(EditCommentTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentType(UserVisitPK userVisitPK, DeleteCommentTypeForm form) {
        return CDI.current().select(DeleteCommentTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentTypeDescription(UserVisitPK userVisitPK, CreateCommentTypeDescriptionForm form) {
        return CDI.current().select(CreateCommentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentTypeDescriptions(UserVisitPK userVisitPK, GetCommentTypeDescriptionsForm form) {
        return CDI.current().select(GetCommentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentTypeDescription(UserVisitPK userVisitPK, EditCommentTypeDescriptionForm form) {
        return CDI.current().select(EditCommentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentTypeDescription(UserVisitPK userVisitPK, DeleteCommentTypeDescriptionForm form) {
        return CDI.current().select(DeleteCommentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageType(UserVisitPK userVisitPK, CreateCommentUsageTypeForm form) {
        return CDI.current().select(CreateCommentUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageType(UserVisitPK userVisitPK, GetCommentUsageTypeForm form) {
        return CDI.current().select(GetCommentUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageTypes(UserVisitPK userVisitPK, GetCommentUsageTypesForm form) {
        return CDI.current().select(GetCommentUsageTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentUsageType(UserVisitPK userVisitPK, EditCommentUsageTypeForm form) {
        return CDI.current().select(EditCommentUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsageType(UserVisitPK userVisitPK, DeleteCommentUsageTypeForm form) {
        return CDI.current().select(DeleteCommentUsageTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usage Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsageTypeDescription(UserVisitPK userVisitPK, CreateCommentUsageTypeDescriptionForm form) {
        return CDI.current().select(CreateCommentUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentUsageTypeDescriptions(UserVisitPK userVisitPK, GetCommentUsageTypeDescriptionsForm form) {
        return CDI.current().select(GetCommentUsageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCommentUsageTypeDescription(UserVisitPK userVisitPK, EditCommentUsageTypeDescriptionForm form) {
        return CDI.current().select(EditCommentUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsageTypeDescription(UserVisitPK userVisitPK, DeleteCommentUsageTypeDescriptionForm form) {
        return CDI.current().select(DeleteCommentUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComment(UserVisitPK userVisitPK, CreateCommentForm form) {
        return CDI.current().select(CreateCommentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editComment(UserVisitPK userVisitPK, EditCommentForm form) {
        return CDI.current().select(EditCommentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCommentStatusChoices(UserVisitPK userVisitPK, GetCommentStatusChoicesForm form) {
        return CDI.current().select(GetCommentStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCommentStatus(UserVisitPK userVisitPK, SetCommentStatusForm form) {
        return CDI.current().select(SetCommentStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getComment(UserVisitPK userVisitPK, GetCommentForm form) {
        return CDI.current().select(GetCommentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteComment(UserVisitPK userVisitPK, DeleteCommentForm form) {
        return CDI.current().select(DeleteCommentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Comment Usages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommentUsage(UserVisitPK userVisitPK, CreateCommentUsageForm form) {
        return CDI.current().select(CreateCommentUsageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCommentUsage(UserVisitPK userVisitPK, DeleteCommentUsageForm form) {
        return CDI.current().select(DeleteCommentUsageCommand.class).get().run(userVisitPK, form);
    }
    
}
