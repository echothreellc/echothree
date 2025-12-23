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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface PicklistService
        extends PicklistForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Picklist Types
    // --------------------------------------------------------------------------------

    CommandResult createPicklistType(UserVisitPK userVisitPK, CreatePicklistTypeForm form);

    CommandResult getPicklistTypeChoices(UserVisitPK userVisitPK, GetPicklistTypeChoicesForm form);

    CommandResult getPicklistType(UserVisitPK userVisitPK, GetPicklistTypeForm form);

    CommandResult getPicklistTypes(UserVisitPK userVisitPK, GetPicklistTypesForm form);

    CommandResult setDefaultPicklistType(UserVisitPK userVisitPK, SetDefaultPicklistTypeForm form);

    CommandResult editPicklistType(UserVisitPK userVisitPK, EditPicklistTypeForm form);

    CommandResult deletePicklistType(UserVisitPK userVisitPK, DeletePicklistTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createPicklistTypeDescription(UserVisitPK userVisitPK, CreatePicklistTypeDescriptionForm form);

    CommandResult getPicklistTypeDescription(UserVisitPK userVisitPK, GetPicklistTypeDescriptionForm form);

    CommandResult getPicklistTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTypeDescriptionsForm form);

    CommandResult editPicklistTypeDescription(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form);

    CommandResult deletePicklistTypeDescription(UserVisitPK userVisitPK, DeletePicklistTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    CommandResult createPicklistTimeType(UserVisitPK userVisitPK, CreatePicklistTimeTypeForm form);

    CommandResult getPicklistTimeTypeChoices(UserVisitPK userVisitPK, GetPicklistTimeTypeChoicesForm form);

    CommandResult getPicklistTimeType(UserVisitPK userVisitPK, GetPicklistTimeTypeForm form);

    CommandResult getPicklistTimeTypes(UserVisitPK userVisitPK, GetPicklistTimeTypesForm form);

    CommandResult setDefaultPicklistTimeType(UserVisitPK userVisitPK, SetDefaultPicklistTimeTypeForm form);

    CommandResult editPicklistTimeType(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form);

    CommandResult deletePicklistTimeType(UserVisitPK userVisitPK, DeletePicklistTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createPicklistTimeTypeDescription(UserVisitPK userVisitPK, CreatePicklistTimeTypeDescriptionForm form);

    CommandResult getPicklistTimeTypeDescription(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionForm form);

    CommandResult getPicklistTimeTypeDescriptions(UserVisitPK userVisitPK, GetPicklistTimeTypeDescriptionsForm form);

    CommandResult editPicklistTimeTypeDescription(UserVisitPK userVisitPK, EditPicklistTimeTypeDescriptionForm form);

    CommandResult deletePicklistTimeTypeDescription(UserVisitPK userVisitPK, DeletePicklistTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createPicklistAliasType(UserVisitPK userVisitPK, CreatePicklistAliasTypeForm form);

    CommandResult getPicklistAliasTypeChoices(UserVisitPK userVisitPK, GetPicklistAliasTypeChoicesForm form);

    CommandResult getPicklistAliasType(UserVisitPK userVisitPK, GetPicklistAliasTypeForm form);

    CommandResult getPicklistAliasTypes(UserVisitPK userVisitPK, GetPicklistAliasTypesForm form);

    CommandResult setDefaultPicklistAliasType(UserVisitPK userVisitPK, SetDefaultPicklistAliasTypeForm form);

    CommandResult editPicklistAliasType(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form);

    CommandResult deletePicklistAliasType(UserVisitPK userVisitPK, DeletePicklistAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createPicklistAliasTypeDescription(UserVisitPK userVisitPK, CreatePicklistAliasTypeDescriptionForm form);

    CommandResult getPicklistAliasTypeDescription(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionForm form);

    CommandResult getPicklistAliasTypeDescriptions(UserVisitPK userVisitPK, GetPicklistAliasTypeDescriptionsForm form);

    CommandResult editPicklistAliasTypeDescription(UserVisitPK userVisitPK, EditPicklistAliasTypeDescriptionForm form);

    CommandResult deletePicklistAliasTypeDescription(UserVisitPK userVisitPK, DeletePicklistAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    CommandResult createPicklistAlias(UserVisitPK userVisitPK, CreatePicklistAliasForm form);

    CommandResult getPicklistAlias(UserVisitPK userVisitPK, GetPicklistAliasForm form);

    CommandResult getPicklistAliases(UserVisitPK userVisitPK, GetPicklistAliasesForm form);

    CommandResult editPicklistAlias(UserVisitPK userVisitPK, EditPicklistAliasForm form);

    CommandResult deletePicklistAlias(UserVisitPK userVisitPK, DeletePicklistAliasForm form);

}
