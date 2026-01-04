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

package com.echothree.control.user.uom.common;

import com.echothree.control.user.uom.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface UomService
        extends UomForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureKind(UserVisitPK userVisitPK, CreateUnitOfMeasureKindForm form);
    
    CommandResult getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form);
    
    CommandResult getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form);
    
    CommandResult getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form);
    
    CommandResult setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form);
    
    CommandResult editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form);
    
    CommandResult deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form);
    
    CommandResult getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form);
    
    CommandResult editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form);
    
    CommandResult deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form);
    
    CommandResult getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form);
    
    CommandResult getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form);
    
    CommandResult getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form);
    
    CommandResult getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form);
    
    CommandResult setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form);
    
    CommandResult editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form);
    
    CommandResult deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form);
    
    CommandResult getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form);
    
    CommandResult editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form);
    
    CommandResult deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form);
    
    CommandResult editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form);
    
    CommandResult deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form);
    
    CommandResult editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form);
    
    CommandResult deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form);
    
    CommandResult getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form);
    
    CommandResult editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form);
    
    CommandResult deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form);
    
    CommandResult getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form);
    
    CommandResult getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form);
    
    CommandResult getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form);
    
    CommandResult getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form);
    
    CommandResult getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form);
    
    CommandResult setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form);
    
    CommandResult editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form);
    
    CommandResult deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form);
    
}
