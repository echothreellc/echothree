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
    
    CommandResult createIndexType(UserVisitPK userVisitPK, CreateIndexTypeForm form);
    
    CommandResult getIndexTypeChoices(UserVisitPK userVisitPK, GetIndexTypeChoicesForm form);
    
    CommandResult getIndexType(UserVisitPK userVisitPK, GetIndexTypeForm form);
    
    CommandResult getIndexTypes(UserVisitPK userVisitPK, GetIndexTypesForm form);
    
    CommandResult setDefaultIndexType(UserVisitPK userVisitPK, SetDefaultIndexTypeForm form);
    
    CommandResult editIndexType(UserVisitPK userVisitPK, EditIndexTypeForm form);
    
    CommandResult deleteIndexType(UserVisitPK userVisitPK, DeleteIndexTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createIndexTypeDescription(UserVisitPK userVisitPK, CreateIndexTypeDescriptionForm form);
    
    CommandResult getIndexTypeDescription(UserVisitPK userVisitPK, GetIndexTypeDescriptionForm form);
    
    CommandResult getIndexTypeDescriptions(UserVisitPK userVisitPK, GetIndexTypeDescriptionsForm form);
    
    CommandResult editIndexTypeDescription(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form);
    
    CommandResult deleteIndexTypeDescription(UserVisitPK userVisitPK, DeleteIndexTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------
    
    CommandResult createIndexField(UserVisitPK userVisitPK, CreateIndexFieldForm form);
    
    CommandResult getIndexFieldChoices(UserVisitPK userVisitPK, GetIndexFieldChoicesForm form);
    
    CommandResult getIndexField(UserVisitPK userVisitPK, GetIndexFieldForm form);
    
    CommandResult getIndexFields(UserVisitPK userVisitPK, GetIndexFieldsForm form);
    
    CommandResult setDefaultIndexField(UserVisitPK userVisitPK, SetDefaultIndexFieldForm form);
    
    CommandResult editIndexField(UserVisitPK userVisitPK, EditIndexFieldForm form);
    
    CommandResult deleteIndexField(UserVisitPK userVisitPK, DeleteIndexFieldForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createIndexFieldDescription(UserVisitPK userVisitPK, CreateIndexFieldDescriptionForm form);
    
    CommandResult getIndexFieldDescription(UserVisitPK userVisitPK, GetIndexFieldDescriptionForm form);
    
    CommandResult getIndexFieldDescriptions(UserVisitPK userVisitPK, GetIndexFieldDescriptionsForm form);
    
    CommandResult editIndexFieldDescription(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form);
    
    CommandResult deleteIndexFieldDescription(UserVisitPK userVisitPK, DeleteIndexFieldDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------
    
    CommandResult createIndex(UserVisitPK userVisitPK, CreateIndexForm form);
    
    CommandResult getIndexChoices(UserVisitPK userVisitPK, GetIndexChoicesForm form);
    
    CommandResult getIndex(UserVisitPK userVisitPK, GetIndexForm form);
    
    CommandResult getIndexes(UserVisitPK userVisitPK, GetIndexesForm form);
    
    CommandResult setDefaultIndex(UserVisitPK userVisitPK, SetDefaultIndexForm form);
    
    CommandResult editIndex(UserVisitPK userVisitPK, EditIndexForm form);
    
    CommandResult deleteIndex(UserVisitPK userVisitPK, DeleteIndexForm form);
    
    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createIndexDescription(UserVisitPK userVisitPK, CreateIndexDescriptionForm form);
    
    CommandResult getIndexDescription(UserVisitPK userVisitPK, GetIndexDescriptionForm form);
    
    CommandResult getIndexDescriptions(UserVisitPK userVisitPK, GetIndexDescriptionsForm form);
    
    CommandResult editIndexDescription(UserVisitPK userVisitPK, EditIndexDescriptionForm form);
    
    CommandResult deleteIndexDescription(UserVisitPK userVisitPK, DeleteIndexDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    CommandResult updateIndexes(UserVisitPK userVisitPK, UpdateIndexesForm form);
    
    CommandResult forceReindex(UserVisitPK userVisitPK, ForceReindexForm form);
    
}
