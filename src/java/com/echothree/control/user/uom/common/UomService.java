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
import com.echothree.control.user.uom.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface UomService
        extends UomForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureKind(UserVisitPK userVisitPK, CreateUnitOfMeasureKindForm form);
    
    CommandResult<GetUnitOfMeasureKindsResult> getUnitOfMeasureKinds(UserVisitPK userVisitPK, GetUnitOfMeasureKindsForm form);
    
    CommandResult<GetUnitOfMeasureKindResult> getUnitOfMeasureKind(UserVisitPK userVisitPK, GetUnitOfMeasureKindForm form);
    
    CommandResult<GetUnitOfMeasureKindChoicesResult> getUnitOfMeasureKindChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindChoicesForm form);
    
    CommandResult<VoidResult> setDefaultUnitOfMeasureKind(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindForm form);
    
    CommandResult<EditUnitOfMeasureKindResult> editUnitOfMeasureKind(UserVisitPK userVisitPK, EditUnitOfMeasureKindForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureKind(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureKindDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindDescriptionForm form);
    
    CommandResult<GetUnitOfMeasureKindDescriptionsResult> getUnitOfMeasureKindDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureKindDescriptionsForm form);
    
    CommandResult<EditUnitOfMeasureKindDescriptionResult> editUnitOfMeasureKindDescription(UserVisitPK userVisitPK, EditUnitOfMeasureKindDescriptionForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureKindDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureType(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeForm form);
    
    CommandResult<GetUnitOfMeasureTypesResult> getUnitOfMeasureTypes(UserVisitPK userVisitPK, GetUnitOfMeasureTypesForm form);
    
    CommandResult<GetUnitOfMeasureTypeResult> getUnitOfMeasureType(UserVisitPK userVisitPK, GetUnitOfMeasureTypeForm form);
    
    CommandResult<GetUnitOfMeasureTypeChoicesResult> getUnitOfMeasureTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureTypeChoicesForm form);
    
    CommandResult<GetUnitOfMeasureChoicesResult> getUnitOfMeasureChoices(UserVisitPK userVisitPK, GetUnitOfMeasureChoicesForm form);
    
    CommandResult<VoidResult> setDefaultUnitOfMeasureType(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureTypeForm form);
    
    CommandResult<EditUnitOfMeasureTypeResult> editUnitOfMeasureType(UserVisitPK userVisitPK, EditUnitOfMeasureTypeForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureType(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeDescriptionForm form);
    
    CommandResult<GetUnitOfMeasureTypeDescriptionsResult> getUnitOfMeasureTypeDescriptions(UserVisitPK userVisitPK, GetUnitOfMeasureTypeDescriptionsForm form);
    
    CommandResult<EditUnitOfMeasureTypeDescriptionResult> editUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, EditUnitOfMeasureTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureTypeDescription(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form);
    
    CommandResult<EditUnitOfMeasureTypeVolumeResult> editUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, EditUnitOfMeasureTypeVolumeForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureTypeVolume(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeVolumeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form);
    
    CommandResult<EditUnitOfMeasureTypeWeightResult> editUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureTypeWeight(UserVisitPK userVisitPK, DeleteUnitOfMeasureTypeWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureEquivalent(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form);
    
    CommandResult<GetUnitOfMeasureEquivalentsResult> getUnitOfMeasureEquivalents(UserVisitPK userVisitPK, GetUnitOfMeasureEquivalentsForm form);
    
    CommandResult<EditUnitOfMeasureEquivalentResult> editUnitOfMeasureEquivalent(UserVisitPK userVisitPK, EditUnitOfMeasureEquivalentForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureEquivalent(UserVisitPK userVisitPK, DeleteUnitOfMeasureEquivalentForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureKindUseType(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeForm form);
    
    CommandResult<GetUnitOfMeasureKindUseTypeChoicesResult> getUnitOfMeasureKindUseTypeChoices(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeChoicesForm form);
    
    CommandResult<GetUnitOfMeasureKindUseTypesResult> getUnitOfMeasureKindUseTypes(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypesForm form);
    
    CommandResult<GetUnitOfMeasureKindUseTypeResult> getUnitOfMeasureKindUseType(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureKindUseTypeDescription(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createUnitOfMeasureKindUse(UserVisitPK userVisitPK, CreateUnitOfMeasureKindUseForm form);
    
    CommandResult<GetUnitOfMeasureKindUsesResult> getUnitOfMeasureKindUses(UserVisitPK userVisitPK, GetUnitOfMeasureKindUsesForm form);
    
    CommandResult<GetUnitOfMeasureKindUseResult> getUnitOfMeasureKindUse(UserVisitPK userVisitPK, GetUnitOfMeasureKindUseForm form);
    
    CommandResult<VoidResult> setDefaultUnitOfMeasureKindUse(UserVisitPK userVisitPK, SetDefaultUnitOfMeasureKindUseForm form);
    
    CommandResult<EditUnitOfMeasureKindUseResult> editUnitOfMeasureKindUse(UserVisitPK userVisitPK, EditUnitOfMeasureKindUseForm form);
    
    CommandResult<VoidResult> deleteUnitOfMeasureKindUse(UserVisitPK userVisitPK, DeleteUnitOfMeasureKindUseForm form);
    
}
