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

package com.echothree.control.user.queue.common;

import com.echothree.control.user.queue.common.form.*;
import com.echothree.control.user.queue.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface QueueService
        extends QueueForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createQueueType(UserVisitPK userVisitPK, CreateQueueTypeForm form);
    
    CommandResult<GetQueueTypeChoicesResult> getQueueTypeChoices(UserVisitPK userVisitPK, GetQueueTypeChoicesForm form);
    
    CommandResult<GetQueueTypeResult> getQueueType(UserVisitPK userVisitPK, GetQueueTypeForm form);
    
    CommandResult<GetQueueTypesResult> getQueueTypes(UserVisitPK userVisitPK, GetQueueTypesForm form);
    
    CommandResult<?> setDefaultQueueType(UserVisitPK userVisitPK, SetDefaultQueueTypeForm form);
    
    CommandResult<EditQueueTypeResult> editQueueType(UserVisitPK userVisitPK, EditQueueTypeForm form);
    
    CommandResult<?> deleteQueueType(UserVisitPK userVisitPK, DeleteQueueTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createQueueTypeDescription(UserVisitPK userVisitPK, CreateQueueTypeDescriptionForm form);
    
    CommandResult<GetQueueTypeDescriptionResult> getQueueTypeDescription(UserVisitPK userVisitPK, GetQueueTypeDescriptionForm form);
    
    CommandResult<GetQueueTypeDescriptionsResult> getQueueTypeDescriptions(UserVisitPK userVisitPK, GetQueueTypeDescriptionsForm form);
    
    CommandResult<EditQueueTypeDescriptionResult> editQueueTypeDescription(UserVisitPK userVisitPK, EditQueueTypeDescriptionForm form);
    
    CommandResult<?> deleteQueueTypeDescription(UserVisitPK userVisitPK, DeleteQueueTypeDescriptionForm form);
    
}
