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

package com.echothree.control.user.comment.common;

import com.echothree.control.user.comment.common.form.*;
import com.echothree.control.user.comment.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CommentService
        extends CommentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Comment Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommentType(UserVisitPK userVisitPK, CreateCommentTypeForm form);
    
    CommandResult<GetCommentTypeResult> getCommentType(UserVisitPK userVisitPK, GetCommentTypeForm form);
    
    CommandResult<GetCommentTypesResult> getCommentTypes(UserVisitPK userVisitPK, GetCommentTypesForm form);
    
    CommandResult<EditCommentTypeResult> editCommentType(UserVisitPK userVisitPK, EditCommentTypeForm form);
    
    CommandResult<?> deleteCommentType(UserVisitPK userVisitPK, DeleteCommentTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Comment Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommentTypeDescription(UserVisitPK userVisitPK, CreateCommentTypeDescriptionForm form);
    
    CommandResult<GetCommentTypeDescriptionsResult> getCommentTypeDescriptions(UserVisitPK userVisitPK, GetCommentTypeDescriptionsForm form);
    
    CommandResult<EditCommentTypeDescriptionResult> editCommentTypeDescription(UserVisitPK userVisitPK, EditCommentTypeDescriptionForm form);
    
    CommandResult<?> deleteCommentTypeDescription(UserVisitPK userVisitPK, DeleteCommentTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Comment Usage Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommentUsageType(UserVisitPK userVisitPK, CreateCommentUsageTypeForm form);
    
    CommandResult<GetCommentUsageTypeResult> getCommentUsageType(UserVisitPK userVisitPK, GetCommentUsageTypeForm form);
    
    CommandResult<GetCommentUsageTypesResult> getCommentUsageTypes(UserVisitPK userVisitPK, GetCommentUsageTypesForm form);
    
    CommandResult<EditCommentUsageTypeResult> editCommentUsageType(UserVisitPK userVisitPK, EditCommentUsageTypeForm form);
    
    CommandResult<?> deleteCommentUsageType(UserVisitPK userVisitPK, DeleteCommentUsageTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Comment Usage Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommentUsageTypeDescription(UserVisitPK userVisitPK, CreateCommentUsageTypeDescriptionForm form);
    
    CommandResult<GetCommentUsageTypeDescriptionsResult> getCommentUsageTypeDescriptions(UserVisitPK userVisitPK, GetCommentUsageTypeDescriptionsForm form);
    
    CommandResult<EditCommentUsageTypeDescriptionResult> editCommentUsageTypeDescription(UserVisitPK userVisitPK, EditCommentUsageTypeDescriptionForm form);
    
    CommandResult<?> deleteCommentUsageTypeDescription(UserVisitPK userVisitPK, DeleteCommentUsageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Comments
    // -------------------------------------------------------------------------
    
    CommandResult<?> createComment(UserVisitPK userVisitPK, CreateCommentForm form);
    
    CommandResult<GetCommentResult> getComment(UserVisitPK userVisitPK, GetCommentForm form);
    
    CommandResult<EditCommentResult> editComment(UserVisitPK userVisitPK, EditCommentForm form);
    
    CommandResult<GetCommentStatusChoicesResult> getCommentStatusChoices(UserVisitPK userVisitPK, GetCommentStatusChoicesForm form);
    
    CommandResult<?> setCommentStatus(UserVisitPK userVisitPK, SetCommentStatusForm form);
    
    CommandResult<?> deleteComment(UserVisitPK userVisitPK, DeleteCommentForm form);
    
    // -------------------------------------------------------------------------
    //   Comment Usages
    // -------------------------------------------------------------------------
    
    CommandResult<?> createCommentUsage(UserVisitPK userVisitPK, CreateCommentUsageForm form);
    
    CommandResult<?> deleteCommentUsage(UserVisitPK userVisitPK, DeleteCommentUsageForm form);
    
}
