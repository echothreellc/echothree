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

package com.echothree.control.user.batch.common;

import com.echothree.control.user.batch.common.form.*;
import com.echothree.control.user.batch.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface BatchService
        extends BatchForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Batch Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createBatchType(UserVisitPK userVisitPK, CreateBatchTypeForm form);
    
    CommandResult<GetBatchTypeChoicesResult> getBatchTypeChoices(UserVisitPK userVisitPK, GetBatchTypeChoicesForm form);
    
    CommandResult<GetBatchTypeResult> getBatchType(UserVisitPK userVisitPK, GetBatchTypeForm form);
    
    CommandResult<GetBatchTypesResult> getBatchTypes(UserVisitPK userVisitPK, GetBatchTypesForm form);
    
    CommandResult<VoidResult> setDefaultBatchType(UserVisitPK userVisitPK, SetDefaultBatchTypeForm form);
    
    CommandResult<EditBatchTypeResult> editBatchType(UserVisitPK userVisitPK, EditBatchTypeForm form);
    
    CommandResult<VoidResult> deleteBatchType(UserVisitPK userVisitPK, DeleteBatchTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBatchTypeDescription(UserVisitPK userVisitPK, CreateBatchTypeDescriptionForm form);

    CommandResult<GetBatchTypeDescriptionResult> getBatchTypeDescription(UserVisitPK userVisitPK, GetBatchTypeDescriptionForm form);

    CommandResult<GetBatchTypeDescriptionsResult> getBatchTypeDescriptions(UserVisitPK userVisitPK, GetBatchTypeDescriptionsForm form);

    CommandResult<EditBatchTypeDescriptionResult> editBatchTypeDescription(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form);

    CommandResult<VoidResult> deleteBatchTypeDescription(UserVisitPK userVisitPK, DeleteBatchTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBatchTypeEntityType(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form);

    CommandResult<GetBatchTypeEntityTypeResult> getBatchTypeEntityType(UserVisitPK userVisitPK, GetBatchTypeEntityTypeForm form);

    CommandResult<GetBatchTypeEntityTypesResult> getBatchTypeEntityTypes(UserVisitPK userVisitPK, GetBatchTypeEntityTypesForm form);

    CommandResult<VoidResult> deleteBatchTypeEntityType(UserVisitPK userVisitPK, DeleteBatchTypeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBatchAliasType(UserVisitPK userVisitPK, CreateBatchAliasTypeForm form);

    CommandResult<GetBatchAliasTypeChoicesResult> getBatchAliasTypeChoices(UserVisitPK userVisitPK, GetBatchAliasTypeChoicesForm form);

    CommandResult<GetBatchAliasTypeResult> getBatchAliasType(UserVisitPK userVisitPK, GetBatchAliasTypeForm form);

    CommandResult<GetBatchAliasTypesResult> getBatchAliasTypes(UserVisitPK userVisitPK, GetBatchAliasTypesForm form);

    CommandResult<VoidResult> setDefaultBatchAliasType(UserVisitPK userVisitPK, SetDefaultBatchAliasTypeForm form);

    CommandResult<EditBatchAliasTypeResult> editBatchAliasType(UserVisitPK userVisitPK, EditBatchAliasTypeForm form);

    CommandResult<VoidResult> deleteBatchAliasType(UserVisitPK userVisitPK, DeleteBatchAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBatchAliasTypeDescription(UserVisitPK userVisitPK, CreateBatchAliasTypeDescriptionForm form);

    CommandResult<GetBatchAliasTypeDescriptionResult> getBatchAliasTypeDescription(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form);

    CommandResult<GetBatchAliasTypeDescriptionsResult> getBatchAliasTypeDescriptions(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionsForm form);

    CommandResult<EditBatchAliasTypeDescriptionResult> editBatchAliasTypeDescription(UserVisitPK userVisitPK, EditBatchAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deleteBatchAliasTypeDescription(UserVisitPK userVisitPK, DeleteBatchAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createBatchAlias(UserVisitPK userVisitPK, CreateBatchAliasForm form);

    CommandResult<GetBatchAliasResult> getBatchAlias(UserVisitPK userVisitPK, GetBatchAliasForm form);

    CommandResult<GetBatchAliasesResult> getBatchAliases(UserVisitPK userVisitPK, GetBatchAliasesForm form);

    CommandResult<EditBatchAliasResult> editBatchAlias(UserVisitPK userVisitPK, EditBatchAliasForm form);

    CommandResult<VoidResult> deleteBatchAlias(UserVisitPK userVisitPK, DeleteBatchAliasForm form);

}
