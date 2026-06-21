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

package com.echothree.control.user.filter.common;

import com.echothree.control.user.filter.common.form.*;
import com.echothree.control.user.filter.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface FilterService
        extends FilterForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Filter Kinds
    // -------------------------------------------------------------------------

    CommandResult<CreateFilterKindResult> createFilterKind(UserVisitPK userVisitPK, CreateFilterKindForm form);

    CommandResult<GetFilterKindsResult> getFilterKinds(UserVisitPK userVisitPK, GetFilterKindsForm form);

    CommandResult<GetFilterKindResult> getFilterKind(UserVisitPK userVisitPK, GetFilterKindForm form);

    CommandResult<GetFilterKindChoicesResult> getFilterKindChoices(UserVisitPK userVisitPK, GetFilterKindChoicesForm form);

    CommandResult<VoidResult> setDefaultFilterKind(UserVisitPK userVisitPK, SetDefaultFilterKindForm form);

    CommandResult<EditFilterKindResult> editFilterKind(UserVisitPK userVisitPK, EditFilterKindForm form);

    CommandResult<VoidResult> deleteFilterKind(UserVisitPK userVisitPK, DeleteFilterKindForm form);

    // -------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createFilterKindDescription(UserVisitPK userVisitPK, CreateFilterKindDescriptionForm form);

    CommandResult<GetFilterKindDescriptionsResult> getFilterKindDescriptions(UserVisitPK userVisitPK, GetFilterKindDescriptionsForm form);

    CommandResult<GetFilterKindDescriptionResult> getFilterKindDescription(UserVisitPK userVisitPK, GetFilterKindDescriptionForm form);

    CommandResult<EditFilterKindDescriptionResult> editFilterKindDescription(UserVisitPK userVisitPK, EditFilterKindDescriptionForm form);

    CommandResult<VoidResult> deleteFilterKindDescription(UserVisitPK userVisitPK, DeleteFilterKindDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Filter Types
    // -------------------------------------------------------------------------

    CommandResult<CreateFilterTypeResult> createFilterType(UserVisitPK userVisitPK, CreateFilterTypeForm form);

    CommandResult<GetFilterTypesResult> getFilterTypes(UserVisitPK userVisitPK, GetFilterTypesForm form);

    CommandResult<GetFilterTypeResult> getFilterType(UserVisitPK userVisitPK, GetFilterTypeForm form);

    CommandResult<GetFilterTypeChoicesResult> getFilterTypeChoices(UserVisitPK userVisitPK, GetFilterTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultFilterType(UserVisitPK userVisitPK, SetDefaultFilterTypeForm form);

    CommandResult<EditFilterTypeResult> editFilterType(UserVisitPK userVisitPK, EditFilterTypeForm form);

    CommandResult<VoidResult> deleteFilterType(UserVisitPK userVisitPK, DeleteFilterTypeForm form);

    // -------------------------------------------------------------------------
    //   Filter Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createFilterTypeDescription(UserVisitPK userVisitPK, CreateFilterTypeDescriptionForm form);

    CommandResult<GetFilterTypeDescriptionsResult> getFilterTypeDescriptions(UserVisitPK userVisitPK, GetFilterTypeDescriptionsForm form);

    CommandResult<GetFilterTypeDescriptionResult> getFilterTypeDescription(UserVisitPK userVisitPK, GetFilterTypeDescriptionForm form);

    CommandResult<EditFilterTypeDescriptionResult> editFilterTypeDescription(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form);

    CommandResult<VoidResult> deleteFilterTypeDescription(UserVisitPK userVisitPK, DeleteFilterTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentSource(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceForm form);

    CommandResult<GetFilterAdjustmentSourcesResult> getFilterAdjustmentSources(UserVisitPK userVisitPK, GetFilterAdjustmentSourcesForm form);

    CommandResult<GetFilterAdjustmentSourceResult> getFilterAdjustmentSource(UserVisitPK userVisitPK, GetFilterAdjustmentSourceForm form);
    
    CommandResult<GetFilterAdjustmentSourceChoicesResult> getFilterAdjustmentSourceChoices(UserVisitPK userVisitPK, GetFilterAdjustmentSourceChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentSourceDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentType(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeForm form);

    CommandResult<GetFilterAdjustmentTypesResult> getFilterAdjustmentTypes(UserVisitPK userVisitPK, GetFilterAdjustmentTypesForm form);

    CommandResult<GetFilterAdjustmentTypeResult> getFilterAdjustmentType(UserVisitPK userVisitPK, GetFilterAdjustmentTypeForm form);
    
    CommandResult<GetFilterAdjustmentTypeChoicesResult> getFilterAdjustmentTypeChoices(UserVisitPK userVisitPK, GetFilterAdjustmentTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustments
    // -------------------------------------------------------------------------
    
    CommandResult<CreateFilterAdjustmentResult> createFilterAdjustment(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form);
    
    CommandResult<GetFilterAdjustmentResult> getFilterAdjustment(UserVisitPK userVisitPK, GetFilterAdjustmentForm form);
    
    CommandResult<GetFilterAdjustmentsResult> getFilterAdjustments(UserVisitPK userVisitPK, GetFilterAdjustmentsForm form);
    
    CommandResult<GetFilterAdjustmentChoicesResult> getFilterAdjustmentChoices(UserVisitPK userVisitPK, GetFilterAdjustmentChoicesForm form);
    
    CommandResult<VoidResult> setDefaultFilterAdjustment(UserVisitPK userVisitPK, SetDefaultFilterAdjustmentForm form);
    
    CommandResult<EditFilterAdjustmentResult> editFilterAdjustment(UserVisitPK userVisitPK, EditFilterAdjustmentForm form);
    
    CommandResult<VoidResult> deleteFilterAdjustment(UserVisitPK userVisitPK, DeleteFilterAdjustmentForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentDescriptionForm form);
    
    CommandResult<GetFilterAdjustmentDescriptionsResult> getFilterAdjustmentDescriptions(UserVisitPK userVisitPK, GetFilterAdjustmentDescriptionsForm form);
    
    CommandResult<EditFilterAdjustmentDescriptionResult> editFilterAdjustmentDescription(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form);
    
    CommandResult<VoidResult> deleteFilterAdjustmentDescription(UserVisitPK userVisitPK, DeleteFilterAdjustmentDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form);

    CommandResult<GetFilterAdjustmentAmountsResult> getFilterAdjustmentAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form);

    CommandResult<GetFilterAdjustmentAmountResult> getFilterAdjustmentAmount(UserVisitPK userVisitPK, GetFilterAdjustmentAmountForm form);

    CommandResult<EditFilterAdjustmentAmountResult> editFilterAdjustmentAmount(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form);
    
    CommandResult<VoidResult> deleteFilterAdjustmentAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentAmountForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amount
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentFixedAmountForm form);

    CommandResult<GetFilterAdjustmentFixedAmountsResult> getFilterAdjustmentFixedAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountsForm form);

    CommandResult<GetFilterAdjustmentFixedAmountResult> getFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountForm form);

    CommandResult<EditFilterAdjustmentFixedAmountResult> editFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, EditFilterAdjustmentFixedAmountForm form);
    
    CommandResult<VoidResult> deleteFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterAdjustmentPercent(UserVisitPK userVisitPK, CreateFilterAdjustmentPercentForm form);

    CommandResult<GetFilterAdjustmentPercentsResult> getFilterAdjustmentPercents(UserVisitPK userVisitPK, GetFilterAdjustmentPercentsForm form);

    CommandResult<GetFilterAdjustmentPercentResult> getFilterAdjustmentPercent(UserVisitPK userVisitPK, GetFilterAdjustmentPercentForm form);

    CommandResult<EditFilterAdjustmentPercentResult> editFilterAdjustmentPercent(UserVisitPK userVisitPK, EditFilterAdjustmentPercentForm form);
    
    CommandResult<VoidResult> deleteFilterAdjustmentPercent(UserVisitPK userVisitPK, DeleteFilterAdjustmentPercentForm form);
    
    // -------------------------------------------------------------------------
    //   Filters
    // -------------------------------------------------------------------------
    
    CommandResult<CreateFilterResult> createFilter(UserVisitPK userVisitPK, CreateFilterForm createFilterForm);
    
    CommandResult<GetFiltersResult> getFilters(UserVisitPK userVisitPK, GetFiltersForm form);
    
    CommandResult<GetFilterResult> getFilter(UserVisitPK userVisitPK, GetFilterForm form);
    
    CommandResult<GetFilterChoicesResult> getFilterChoices(UserVisitPK userVisitPK, GetFilterChoicesForm form);
    
    CommandResult<VoidResult> setDefaultFilter(UserVisitPK userVisitPK, SetDefaultFilterForm form);
    
    CommandResult<EditFilterResult> editFilter(UserVisitPK userVisitPK, EditFilterForm form);
    
    CommandResult<VoidResult> deleteFilter(UserVisitPK userVisitPK, DeleteFilterForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterDescription(UserVisitPK userVisitPK, CreateFilterDescriptionForm form);
    
    CommandResult<GetFilterDescriptionsResult> getFilterDescriptions(UserVisitPK userVisitPK, GetFilterDescriptionsForm form);
    
    CommandResult<EditFilterDescriptionResult> editFilterDescription(UserVisitPK userVisitPK, EditFilterDescriptionForm form);
    
    CommandResult<VoidResult> deleteFilterDescription(UserVisitPK userVisitPK, DeleteFilterDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Steps
    // -------------------------------------------------------------------------
    
    CommandResult<CreateFilterStepResult> createFilterStep(UserVisitPK userVisitPK, CreateFilterStepForm form);
    
    CommandResult<GetFilterStepResult> getFilterStep(UserVisitPK userVisitPK, GetFilterStepForm form);
    
    CommandResult<GetFilterStepsResult> getFilterSteps(UserVisitPK userVisitPK, GetFilterStepsForm form);
    
    CommandResult<GetFilterStepChoicesResult> getFilterStepChoices(UserVisitPK userVisitPK, GetFilterStepChoicesForm form);
    
    CommandResult<EditFilterStepResult> editFilterStep(UserVisitPK userVisitPK, EditFilterStepForm form);
    
    CommandResult<VoidResult> deleteFilterStep(UserVisitPK userVisitPK, DeleteFilterStepForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterStepDescription(UserVisitPK userVisitPK, CreateFilterStepDescriptionForm form);
    
    CommandResult<GetFilterStepDescriptionsResult> getFilterStepDescriptions(UserVisitPK userVisitPK, GetFilterStepDescriptionsForm form);
    
    CommandResult<EditFilterStepDescriptionResult> editFilterStepDescription(UserVisitPK userVisitPK, EditFilterStepDescriptionForm form);
    
    CommandResult<VoidResult> deleteFilterStepDescription(UserVisitPK userVisitPK, DeleteFilterStepDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Elements
    // -------------------------------------------------------------------------
    
    CommandResult<CreateFilterStepElementResult> createFilterStepElement(UserVisitPK userVisitPK, CreateFilterStepElementForm form);
    
    CommandResult<GetFilterStepElementResult> getFilterStepElement(UserVisitPK userVisitPK, GetFilterStepElementForm form);
    
    CommandResult<GetFilterStepElementsResult> getFilterStepElements(UserVisitPK userVisitPK, GetFilterStepElementsForm form);
    
    CommandResult<EditFilterStepElementResult> editFilterStepElement(UserVisitPK userVisitPK, EditFilterStepElementForm form);
    
    CommandResult<VoidResult> deleteFilterStepElement(UserVisitPK userVisitPK, DeleteFilterStepElementForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterStepElementDescription(UserVisitPK userVisitPK, CreateFilterStepElementDescriptionForm form);
    
    CommandResult<GetFilterStepElementDescriptionsResult> getFilterStepElementDescriptions(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form);
    
    CommandResult<EditFilterStepElementDescriptionResult> editFilterStepElementDescription(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form);
    
    CommandResult<VoidResult> deleteFilterStepElementDescription(UserVisitPK userVisitPK, DeleteFilterStepElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Entrance Steps
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterEntranceStep(UserVisitPK userVisitPK, CreateFilterEntranceStepForm form);

    CommandResult<GetFilterEntranceStepsResult> getFilterEntranceSteps(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form);

    CommandResult<GetFilterEntranceStepResult> getFilterEntranceStep(UserVisitPK userVisitPK, GetFilterEntranceStepForm form);

    CommandResult<VoidResult> deleteFilterEntranceStep(UserVisitPK userVisitPK, DeleteFilterEntranceStepForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Destinations
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createFilterStepDestination(UserVisitPK userVisitPK, CreateFilterStepDestinationForm form);

    CommandResult<GetFilterStepDestinationsResult> getFilterStepDestinations(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form);

    CommandResult<GetFilterStepDestinationResult> getFilterStepDestination(UserVisitPK userVisitPK, GetFilterStepDestinationForm form);

    CommandResult<VoidResult> deleteFilterStepDestination(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form);
    
}
