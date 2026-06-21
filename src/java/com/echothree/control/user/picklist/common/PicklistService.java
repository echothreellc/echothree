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

package com.echothree.control.user.picklist.common;

import com.echothree.control.user.picklist.common.form.*;
import com.echothree.control.user.picklist.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface PicklistService
        extends PicklistForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Picklist Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistType(UserVisitPK userVisitPK, CreatePicklistTypeForm form);

    CommandResult<GetPicklistTypeChoicesResult> getPicklistTypeChoices(UserVisitPK userVisitPK, GetPicklistTypeChoicesForm form);

    CommandResult<GetPicklistTypeResult> getPicklistType(UserVisitPK userVisitPK, GetPicklistTypeForm form);

    CommandResult<GetPicklistTypesResult> getPicklistTypes(UserVisitPK userVisitPK, GetPicklistTypesForm form);

    CommandResult<VoidResult> setDefaultPicklistType(UserVisitPK userVisitPK, SetDefaultPicklistTypeForm form);

    CommandResult<EditPicklistTypeResult> editPicklistType(UserVisitPK userVisitPK, EditPicklistTypeForm form);

    CommandResult<VoidResult> deletePicklistType(UserVisitPK userVisitPK, DeletePicklistTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistTypeDescription(UserVisitPK userVisitPK, CreatePicklistTypeDescriptionForm form);

    CommandResult<GetPicklistTypeDescriptionResult> getPicklistTypeDescription(UserVisitPK userVisitPK, GetPicklistTypeDescriptionForm form);

    CommandResult<GetPicklistTypeDescriptionsResult> getPicklistTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTypeDescriptionsForm form);

    CommandResult<EditPicklistTypeDescriptionResult> editPicklistTypeDescription(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form);

    CommandResult<VoidResult> deletePicklistTypeDescription(UserVisitPK userVisitPK, DeletePicklistTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistTimeType(UserVisitPK userVisitPK, CreatePicklistTimeTypeForm form);

    CommandResult<GetPicklistTimeTypeChoicesResult> getPicklistTimeTypeChoices(UserVisitPK userVisitPK, GetPicklistTimeTypeChoicesForm form);

    CommandResult<GetPicklistTimeTypeResult> getPicklistTimeType(UserVisitPK userVisitPK, GetPicklistTimeTypeForm form);

    CommandResult<GetPicklistTimeTypesResult> getPicklistTimeTypes(UserVisitPK userVisitPK, GetPicklistTimeTypesForm form);

    CommandResult<VoidResult> setDefaultPicklistTimeType(UserVisitPK userVisitPK, SetDefaultPicklistTimeTypeForm form);

    CommandResult<EditPicklistTimeTypeResult> editPicklistTimeType(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form);

    CommandResult<VoidResult> deletePicklistTimeType(UserVisitPK userVisitPK, DeletePicklistTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistTimeTypeDescription(UserVisitPK userVisitPK, CreatePicklistTimeTypeDescriptionForm form);

    CommandResult<GetPicklistTimeTypeDescriptionResult> getPicklistTimeTypeDescription(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionForm form);

    CommandResult<GetPicklistTimeTypeDescriptionsResult> getPicklistTimeTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionsForm form);

    CommandResult<EditPicklistTimeTypeDescriptionResult> editPicklistTimeTypeDescription(UserVisitPK userVisitPK, EditPicklistTimeTypeDescriptionForm form);

    CommandResult<VoidResult> deletePicklistTimeTypeDescription(UserVisitPK userVisitPK, DeletePicklistTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistAliasType(UserVisitPK userVisitPK, CreatePicklistAliasTypeForm form);

    CommandResult<GetPicklistAliasTypeChoicesResult> getPicklistAliasTypeChoices(UserVisitPK userVisitPK, GetPicklistAliasTypeChoicesForm form);

    CommandResult<GetPicklistAliasTypeResult> getPicklistAliasType(UserVisitPK userVisitPK, GetPicklistAliasTypeForm form);

    CommandResult<GetPicklistAliasTypesResult> getPicklistAliasTypes(UserVisitPK userVisitPK, GetPicklistAliasTypesForm form);

    CommandResult<VoidResult> setDefaultPicklistAliasType(UserVisitPK userVisitPK, SetDefaultPicklistAliasTypeForm form);

    CommandResult<EditPicklistAliasTypeResult> editPicklistAliasType(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form);

    CommandResult<VoidResult> deletePicklistAliasType(UserVisitPK userVisitPK, DeletePicklistAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistAliasTypeDescription(UserVisitPK userVisitPK, CreatePicklistAliasTypeDescriptionForm form);

    CommandResult<GetPicklistAliasTypeDescriptionResult> getPicklistAliasTypeDescription(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionForm form);

    CommandResult<GetPicklistAliasTypeDescriptionsResult> getPicklistAliasTypeDescriptions(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionsForm form);

    CommandResult<EditPicklistAliasTypeDescriptionResult> editPicklistAliasTypeDescription(UserVisitPK userVisitPK, EditPicklistAliasTypeDescriptionForm form);

    CommandResult<VoidResult> deletePicklistAliasTypeDescription(UserVisitPK userVisitPK, DeletePicklistAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createPicklistAlias(UserVisitPK userVisitPK, CreatePicklistAliasForm form);

    CommandResult<GetPicklistAliasResult> getPicklistAlias(UserVisitPK userVisitPK, GetPicklistAliasForm form);

    CommandResult<GetPicklistAliasesResult> getPicklistAliases(UserVisitPK userVisitPK, GetPicklistAliasesForm form);

    CommandResult<EditPicklistAliasResult> editPicklistAlias(UserVisitPK userVisitPK, EditPicklistAliasForm form);

    CommandResult<VoidResult> deletePicklistAlias(UserVisitPK userVisitPK, DeletePicklistAliasForm form);

}
