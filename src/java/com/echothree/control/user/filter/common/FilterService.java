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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface FilterService
        extends FilterForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Filter Kinds
    // -------------------------------------------------------------------------

    CommandResult createFilterKind(UserVisitPK userVisitPK, CreateFilterKindForm form);

    CommandResult getFilterKinds(UserVisitPK userVisitPK, GetFilterKindsForm form);

    CommandResult getFilterKind(UserVisitPK userVisitPK, GetFilterKindForm form);

    CommandResult getFilterKindChoices(UserVisitPK userVisitPK, GetFilterKindChoicesForm form);

    CommandResult setDefaultFilterKind(UserVisitPK userVisitPK, SetDefaultFilterKindForm form);

    CommandResult editFilterKind(UserVisitPK userVisitPK, EditFilterKindForm form);

    CommandResult deleteFilterKind(UserVisitPK userVisitPK, DeleteFilterKindForm form);

    // -------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult createFilterKindDescription(UserVisitPK userVisitPK, CreateFilterKindDescriptionForm form);

    CommandResult getFilterKindDescriptions(UserVisitPK userVisitPK, GetFilterKindDescriptionsForm form);

    CommandResult getFilterKindDescription(UserVisitPK userVisitPK, GetFilterKindDescriptionForm form);

    CommandResult editFilterKindDescription(UserVisitPK userVisitPK, EditFilterKindDescriptionForm form);

    CommandResult deleteFilterKindDescription(UserVisitPK userVisitPK, DeleteFilterKindDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Filter Types
    // -------------------------------------------------------------------------

    CommandResult createFilterType(UserVisitPK userVisitPK, CreateFilterTypeForm form);

    CommandResult getFilterTypes(UserVisitPK userVisitPK, GetFilterTypesForm form);

    CommandResult getFilterType(UserVisitPK userVisitPK, GetFilterTypeForm form);

    CommandResult getFilterTypeChoices(UserVisitPK userVisitPK, GetFilterTypeChoicesForm form);

    CommandResult setDefaultFilterType(UserVisitPK userVisitPK, SetDefaultFilterTypeForm form);

    CommandResult editFilterType(UserVisitPK userVisitPK, EditFilterTypeForm form);

    CommandResult deleteFilterType(UserVisitPK userVisitPK, DeleteFilterTypeForm form);

    // -------------------------------------------------------------------------
    //   Filter Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createFilterTypeDescription(UserVisitPK userVisitPK, CreateFilterTypeDescriptionForm form);

    CommandResult getFilterTypeDescriptions(UserVisitPK userVisitPK, GetFilterTypeDescriptionsForm form);

    CommandResult getFilterTypeDescription(UserVisitPK userVisitPK, GetFilterTypeDescriptionForm form);

    CommandResult editFilterTypeDescription(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form);

    CommandResult deleteFilterTypeDescription(UserVisitPK userVisitPK, DeleteFilterTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentSource(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceForm form);

    CommandResult getFilterAdjustmentSources(UserVisitPK userVisitPK, GetFilterAdjustmentSourcesForm form);

    CommandResult getFilterAdjustmentSource(UserVisitPK userVisitPK, GetFilterAdjustmentSourceForm form);
    
    CommandResult getFilterAdjustmentSourceChoices(UserVisitPK userVisitPK, GetFilterAdjustmentSourceChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentSourceDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Types
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentType(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeForm form);

    CommandResult getFilterAdjustmentTypes(UserVisitPK userVisitPK, GetFilterAdjustmentTypesForm form);

    CommandResult getFilterAdjustmentType(UserVisitPK userVisitPK, GetFilterAdjustmentTypeForm form);
    
    CommandResult getFilterAdjustmentTypeChoices(UserVisitPK userVisitPK, GetFilterAdjustmentTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustments
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustment(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form);
    
    CommandResult getFilterAdjustment(UserVisitPK userVisitPK, GetFilterAdjustmentForm form);
    
    CommandResult getFilterAdjustments(UserVisitPK userVisitPK, GetFilterAdjustmentsForm form);
    
    CommandResult getFilterAdjustmentChoices(UserVisitPK userVisitPK, GetFilterAdjustmentChoicesForm form);
    
    CommandResult setDefaultFilterAdjustment(UserVisitPK userVisitPK, SetDefaultFilterAdjustmentForm form);
    
    CommandResult editFilterAdjustment(UserVisitPK userVisitPK, EditFilterAdjustmentForm form);
    
    CommandResult deleteFilterAdjustment(UserVisitPK userVisitPK, DeleteFilterAdjustmentForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentDescriptionForm form);
    
    CommandResult getFilterAdjustmentDescriptions(UserVisitPK userVisitPK, GetFilterAdjustmentDescriptionsForm form);
    
    CommandResult editFilterAdjustmentDescription(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form);
    
    CommandResult deleteFilterAdjustmentDescription(UserVisitPK userVisitPK, DeleteFilterAdjustmentDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form);

    CommandResult getFilterAdjustmentAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form);

    CommandResult getFilterAdjustmentAmount(UserVisitPK userVisitPK, GetFilterAdjustmentAmountForm form);

    CommandResult editFilterAdjustmentAmount(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form);
    
    CommandResult deleteFilterAdjustmentAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentAmountForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amount
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentFixedAmountForm form);

    CommandResult getFilterAdjustmentFixedAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountsForm form);

    CommandResult getFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountForm form);

    CommandResult editFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, EditFilterAdjustmentFixedAmountForm form);
    
    CommandResult deleteFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // -------------------------------------------------------------------------
    
    CommandResult createFilterAdjustmentPercent(UserVisitPK userVisitPK, CreateFilterAdjustmentPercentForm form);

    CommandResult getFilterAdjustmentPercents(UserVisitPK userVisitPK, GetFilterAdjustmentPercentsForm form);

    CommandResult getFilterAdjustmentPercent(UserVisitPK userVisitPK, GetFilterAdjustmentPercentForm form);

    CommandResult editFilterAdjustmentPercent(UserVisitPK userVisitPK, EditFilterAdjustmentPercentForm form);
    
    CommandResult deleteFilterAdjustmentPercent(UserVisitPK userVisitPK, DeleteFilterAdjustmentPercentForm form);
    
    // -------------------------------------------------------------------------
    //   Filters
    // -------------------------------------------------------------------------
    
    CommandResult createFilter(UserVisitPK userVisitPK, CreateFilterForm createFilterForm);
    
    CommandResult getFilters(UserVisitPK userVisitPK, GetFiltersForm form);
    
    CommandResult getFilter(UserVisitPK userVisitPK, GetFilterForm form);
    
    CommandResult getFilterChoices(UserVisitPK userVisitPK, GetFilterChoicesForm form);
    
    CommandResult setDefaultFilter(UserVisitPK userVisitPK, SetDefaultFilterForm form);
    
    CommandResult editFilter(UserVisitPK userVisitPK, EditFilterForm form);
    
    CommandResult deleteFilter(UserVisitPK userVisitPK, DeleteFilterForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterDescription(UserVisitPK userVisitPK, CreateFilterDescriptionForm form);
    
    CommandResult getFilterDescriptions(UserVisitPK userVisitPK, GetFilterDescriptionsForm form);
    
    CommandResult editFilterDescription(UserVisitPK userVisitPK, EditFilterDescriptionForm form);
    
    CommandResult deleteFilterDescription(UserVisitPK userVisitPK, DeleteFilterDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Steps
    // -------------------------------------------------------------------------
    
    CommandResult createFilterStep(UserVisitPK userVisitPK, CreateFilterStepForm form);
    
    CommandResult getFilterStep(UserVisitPK userVisitPK, GetFilterStepForm form);
    
    CommandResult getFilterSteps(UserVisitPK userVisitPK, GetFilterStepsForm form);
    
    CommandResult getFilterStepChoices(UserVisitPK userVisitPK, GetFilterStepChoicesForm form);
    
    CommandResult editFilterStep(UserVisitPK userVisitPK, EditFilterStepForm form);
    
    CommandResult deleteFilterStep(UserVisitPK userVisitPK, DeleteFilterStepForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterStepDescription(UserVisitPK userVisitPK, CreateFilterStepDescriptionForm form);
    
    CommandResult getFilterStepDescriptions(UserVisitPK userVisitPK, GetFilterStepDescriptionsForm form);
    
    CommandResult editFilterStepDescription(UserVisitPK userVisitPK, EditFilterStepDescriptionForm form);
    
    CommandResult deleteFilterStepDescription(UserVisitPK userVisitPK, DeleteFilterStepDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Elements
    // -------------------------------------------------------------------------
    
    CommandResult createFilterStepElement(UserVisitPK userVisitPK, CreateFilterStepElementForm form);
    
    CommandResult getFilterStepElement(UserVisitPK userVisitPK, GetFilterStepElementForm form);
    
    CommandResult getFilterStepElements(UserVisitPK userVisitPK, GetFilterStepElementsForm form);
    
    CommandResult editFilterStepElement(UserVisitPK userVisitPK, EditFilterStepElementForm form);
    
    CommandResult deleteFilterStepElement(UserVisitPK userVisitPK, DeleteFilterStepElementForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createFilterStepElementDescription(UserVisitPK userVisitPK, CreateFilterStepElementDescriptionForm form);
    
    CommandResult getFilterStepElementDescriptions(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form);
    
    CommandResult editFilterStepElementDescription(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form);
    
    CommandResult deleteFilterStepElementDescription(UserVisitPK userVisitPK, DeleteFilterStepElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Entrance Steps
    // -------------------------------------------------------------------------
    
    CommandResult createFilterEntranceStep(UserVisitPK userVisitPK, CreateFilterEntranceStepForm form);
    
    CommandResult getFilterEntranceSteps(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form);
    
    CommandResult deleteFilterEntranceStep(UserVisitPK userVisitPK, DeleteFilterEntranceStepForm form);
    
    // -------------------------------------------------------------------------
    //   Filter Step Destinations
    // -------------------------------------------------------------------------
    
    CommandResult createFilterStepDestination(UserVisitPK userVisitPK, CreateFilterStepDestinationForm form);
    
    CommandResult getFilterStepDestinations(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form);
    
    CommandResult deleteFilterStepDestination(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form);
    
}
