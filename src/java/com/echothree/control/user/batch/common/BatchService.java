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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface BatchService
        extends BatchForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Batch Types
    // --------------------------------------------------------------------------------
    
    CommandResult createBatchType(UserVisitPK userVisitPK, CreateBatchTypeForm form);
    
    CommandResult getBatchTypeChoices(UserVisitPK userVisitPK, GetBatchTypeChoicesForm form);
    
    CommandResult getBatchType(UserVisitPK userVisitPK, GetBatchTypeForm form);
    
    CommandResult getBatchTypes(UserVisitPK userVisitPK, GetBatchTypesForm form);
    
    CommandResult setDefaultBatchType(UserVisitPK userVisitPK, SetDefaultBatchTypeForm form);
    
    CommandResult editBatchType(UserVisitPK userVisitPK, EditBatchTypeForm form);
    
    CommandResult deleteBatchType(UserVisitPK userVisitPK, DeleteBatchTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createBatchTypeDescription(UserVisitPK userVisitPK, CreateBatchTypeDescriptionForm form);

    CommandResult getBatchTypeDescription(UserVisitPK userVisitPK, GetBatchTypeDescriptionForm form);

    CommandResult getBatchTypeDescriptions(UserVisitPK userVisitPK, GetBatchTypeDescriptionsForm form);

    CommandResult editBatchTypeDescription(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form);

    CommandResult deleteBatchTypeDescription(UserVisitPK userVisitPK, DeleteBatchTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    CommandResult createBatchTypeEntityType(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form);

    CommandResult getBatchTypeEntityType(UserVisitPK userVisitPK, GetBatchTypeEntityTypeForm form);

    CommandResult getBatchTypeEntityTypes(UserVisitPK userVisitPK, GetBatchTypeEntityTypesForm form);

    CommandResult deleteBatchTypeEntityType(UserVisitPK userVisitPK, DeleteBatchTypeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createBatchAliasType(UserVisitPK userVisitPK, CreateBatchAliasTypeForm form);

    CommandResult getBatchAliasTypeChoices(UserVisitPK userVisitPK, GetBatchAliasTypeChoicesForm form);

    CommandResult getBatchAliasType(UserVisitPK userVisitPK, GetBatchAliasTypeForm form);

    CommandResult getBatchAliasTypes(UserVisitPK userVisitPK, GetBatchAliasTypesForm form);

    CommandResult setDefaultBatchAliasType(UserVisitPK userVisitPK, SetDefaultBatchAliasTypeForm form);

    CommandResult editBatchAliasType(UserVisitPK userVisitPK, EditBatchAliasTypeForm form);

    CommandResult deleteBatchAliasType(UserVisitPK userVisitPK, DeleteBatchAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createBatchAliasTypeDescription(UserVisitPK userVisitPK, CreateBatchAliasTypeDescriptionForm form);

    CommandResult getBatchAliasTypeDescription(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form);

    CommandResult getBatchAliasTypeDescriptions(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionsForm form);

    CommandResult editBatchAliasTypeDescription(UserVisitPK userVisitPK, EditBatchAliasTypeDescriptionForm form);

    CommandResult deleteBatchAliasTypeDescription(UserVisitPK userVisitPK, DeleteBatchAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------

    CommandResult createBatchAlias(UserVisitPK userVisitPK, CreateBatchAliasForm form);

    CommandResult getBatchAlias(UserVisitPK userVisitPK, GetBatchAliasForm form);

    CommandResult getBatchAliases(UserVisitPK userVisitPK, GetBatchAliasesForm form);

    CommandResult editBatchAlias(UserVisitPK userVisitPK, EditBatchAliasForm form);

    CommandResult deleteBatchAlias(UserVisitPK userVisitPK, DeleteBatchAliasForm form);

}
