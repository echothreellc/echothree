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

package com.echothree.control.user.index.common;

import com.echothree.control.user.index.common.form.*;
import com.echothree.control.user.index.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface IndexService
        extends IndexForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndexType(UserVisitPK userVisitPK, CreateIndexTypeForm form);
    
    CommandResult<GetIndexTypeChoicesResult> getIndexTypeChoices(UserVisitPK userVisitPK, GetIndexTypeChoicesForm form);
    
    CommandResult<GetIndexTypeResult> getIndexType(UserVisitPK userVisitPK, GetIndexTypeForm form);
    
    CommandResult<GetIndexTypesResult> getIndexTypes(UserVisitPK userVisitPK, GetIndexTypesForm form);
    
    CommandResult<?> setDefaultIndexType(UserVisitPK userVisitPK, SetDefaultIndexTypeForm form);
    
    CommandResult<EditIndexTypeResult> editIndexType(UserVisitPK userVisitPK, EditIndexTypeForm form);
    
    CommandResult<?> deleteIndexType(UserVisitPK userVisitPK, DeleteIndexTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndexTypeDescription(UserVisitPK userVisitPK, CreateIndexTypeDescriptionForm form);
    
    CommandResult<GetIndexTypeDescriptionResult> getIndexTypeDescription(UserVisitPK userVisitPK, GetIndexTypeDescriptionForm form);
    
    CommandResult<GetIndexTypeDescriptionsResult> getIndexTypeDescriptions(UserVisitPK userVisitPK, GetIndexTypeDescriptionsForm form);
    
    CommandResult<EditIndexTypeDescriptionResult> editIndexTypeDescription(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form);
    
    CommandResult<?> deleteIndexTypeDescription(UserVisitPK userVisitPK, DeleteIndexTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndexField(UserVisitPK userVisitPK, CreateIndexFieldForm form);
    
    CommandResult<GetIndexFieldChoicesResult> getIndexFieldChoices(UserVisitPK userVisitPK, GetIndexFieldChoicesForm form);
    
    CommandResult<GetIndexFieldResult> getIndexField(UserVisitPK userVisitPK, GetIndexFieldForm form);
    
    CommandResult<GetIndexFieldsResult> getIndexFields(UserVisitPK userVisitPK, GetIndexFieldsForm form);
    
    CommandResult<?> setDefaultIndexField(UserVisitPK userVisitPK, SetDefaultIndexFieldForm form);
    
    CommandResult<EditIndexFieldResult> editIndexField(UserVisitPK userVisitPK, EditIndexFieldForm form);
    
    CommandResult<?> deleteIndexField(UserVisitPK userVisitPK, DeleteIndexFieldForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndexFieldDescription(UserVisitPK userVisitPK, CreateIndexFieldDescriptionForm form);
    
    CommandResult<GetIndexFieldDescriptionResult> getIndexFieldDescription(UserVisitPK userVisitPK, GetIndexFieldDescriptionForm form);
    
    CommandResult<GetIndexFieldDescriptionsResult> getIndexFieldDescriptions(UserVisitPK userVisitPK, GetIndexFieldDescriptionsForm form);
    
    CommandResult<EditIndexFieldDescriptionResult> editIndexFieldDescription(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form);
    
    CommandResult<?> deleteIndexFieldDescription(UserVisitPK userVisitPK, DeleteIndexFieldDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndex(UserVisitPK userVisitPK, CreateIndexForm form);
    
    CommandResult<GetIndexChoicesResult> getIndexChoices(UserVisitPK userVisitPK, GetIndexChoicesForm form);
    
    CommandResult<GetIndexResult> getIndex(UserVisitPK userVisitPK, GetIndexForm form);
    
    CommandResult<GetIndexesResult> getIndexes(UserVisitPK userVisitPK, GetIndexesForm form);
    
    CommandResult<?> setDefaultIndex(UserVisitPK userVisitPK, SetDefaultIndexForm form);
    
    CommandResult<EditIndexResult> editIndex(UserVisitPK userVisitPK, EditIndexForm form);
    
    CommandResult<?> deleteIndex(UserVisitPK userVisitPK, DeleteIndexForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createIndexDescription(UserVisitPK userVisitPK, CreateIndexDescriptionForm form);
    
    CommandResult<GetIndexDescriptionResult> getIndexDescription(UserVisitPK userVisitPK, GetIndexDescriptionForm form);
    
    CommandResult<GetIndexDescriptionsResult> getIndexDescriptions(UserVisitPK userVisitPK, GetIndexDescriptionsForm form);
    
    CommandResult<EditIndexDescriptionResult> editIndexDescription(UserVisitPK userVisitPK, EditIndexDescriptionForm form);
    
    CommandResult<?> deleteIndexDescription(UserVisitPK userVisitPK, DeleteIndexDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    CommandResult<?> updateIndexes(UserVisitPK userVisitPK, UpdateIndexesForm form);
    
    CommandResult<?> forceReindex(UserVisitPK userVisitPK, ForceReindexForm form);
    
}
