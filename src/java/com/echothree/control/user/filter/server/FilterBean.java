// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.filter.server;

import com.echothree.control.user.filter.common.FilterRemote;
import com.echothree.control.user.filter.common.form.*;
import com.echothree.control.user.filter.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class FilterBean
        extends FilterFormsImpl
        implements FilterRemote, FilterLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "FilterBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Filter Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterKind(UserVisitPK userVisitPK, CreateFilterKindForm form) {
        return new CreateFilterKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKinds(UserVisitPK userVisitPK, GetFilterKindsForm form) {
        return new GetFilterKindsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKind(UserVisitPK userVisitPK, GetFilterKindForm form) {
        return new GetFilterKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindChoices(UserVisitPK userVisitPK, GetFilterKindChoicesForm form) {
        return new GetFilterKindChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultFilterKind(UserVisitPK userVisitPK, SetDefaultFilterKindForm form) {
        return new SetDefaultFilterKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterKind(UserVisitPK userVisitPK, EditFilterKindForm form) {
        return new EditFilterKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterKind(UserVisitPK userVisitPK, DeleteFilterKindForm form) {
        return new DeleteFilterKindCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterKindDescription(UserVisitPK userVisitPK, CreateFilterKindDescriptionForm form) {
        return new CreateFilterKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindDescriptions(UserVisitPK userVisitPK, GetFilterKindDescriptionsForm form) {
        return new GetFilterKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindDescription(UserVisitPK userVisitPK, GetFilterKindDescriptionForm form) {
        return new GetFilterKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterKindDescription(UserVisitPK userVisitPK, EditFilterKindDescriptionForm form) {
        return new EditFilterKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterKindDescription(UserVisitPK userVisitPK, DeleteFilterKindDescriptionForm form) {
        return new DeleteFilterKindDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterType(UserVisitPK userVisitPK, CreateFilterTypeForm form) {
        return new CreateFilterTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypes(UserVisitPK userVisitPK, GetFilterTypesForm form) {
        return new GetFilterTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterType(UserVisitPK userVisitPK, GetFilterTypeForm form) {
        return new GetFilterTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeChoices(UserVisitPK userVisitPK, GetFilterTypeChoicesForm form) {
        return new GetFilterTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultFilterType(UserVisitPK userVisitPK, SetDefaultFilterTypeForm form) {
        return new SetDefaultFilterTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterType(UserVisitPK userVisitPK, EditFilterTypeForm form) {
        return new EditFilterTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterType(UserVisitPK userVisitPK, DeleteFilterTypeForm form) {
        return new DeleteFilterTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterTypeDescription(UserVisitPK userVisitPK, CreateFilterTypeDescriptionForm form) {
        return new CreateFilterTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeDescriptions(UserVisitPK userVisitPK, GetFilterTypeDescriptionsForm form) {
        return new GetFilterTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeDescription(UserVisitPK userVisitPK, GetFilterTypeDescriptionForm form) {
        return new GetFilterTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterTypeDescription(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form) {
        return new EditFilterTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterTypeDescription(UserVisitPK userVisitPK, DeleteFilterTypeDescriptionForm form) {
        return new DeleteFilterTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSource(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceForm form) {
        return new CreateFilterAdjustmentSourceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSources(UserVisitPK userVisitPK, GetFilterAdjustmentSourcesForm form) {
        return new GetFilterAdjustmentSourcesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSource(UserVisitPK userVisitPK, GetFilterAdjustmentSourceForm form) {
        return new GetFilterAdjustmentSourceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSourceChoices(UserVisitPK userVisitPK, GetFilterAdjustmentSourceChoicesForm form) {
        return new GetFilterAdjustmentSourceChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSourceDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceDescriptionForm form) {
        return new CreateFilterAdjustmentSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentType(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeForm form) {
        return new CreateFilterAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentTypes(UserVisitPK userVisitPK, GetFilterAdjustmentTypesForm form) {
        return new GetFilterAdjustmentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentType(UserVisitPK userVisitPK, GetFilterAdjustmentTypeForm form) {
        return new GetFilterAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentTypeChoices(UserVisitPK userVisitPK, GetFilterAdjustmentTypeChoicesForm form) {
        return new GetFilterAdjustmentTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeDescriptionForm form) {
        return new CreateFilterAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustment(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form) {
        return new CreateFilterAdjustmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustment(UserVisitPK userVisitPK, GetFilterAdjustmentForm form) {
        return new GetFilterAdjustmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustments(UserVisitPK userVisitPK, GetFilterAdjustmentsForm form) {
        return new GetFilterAdjustmentsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustmentChoices(UserVisitPK userVisitPK, GetFilterAdjustmentChoicesForm form) {
        return new GetFilterAdjustmentChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFilterAdjustment(UserVisitPK userVisitPK, SetDefaultFilterAdjustmentForm form) {
        return new SetDefaultFilterAdjustmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterAdjustment(UserVisitPK userVisitPK, EditFilterAdjustmentForm form) {
        return new EditFilterAdjustmentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustment(UserVisitPK userVisitPK, DeleteFilterAdjustmentForm form) {
        return new DeleteFilterAdjustmentCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentDescriptionForm form) {
        return new CreateFilterAdjustmentDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustmentDescriptions(UserVisitPK userVisitPK, GetFilterAdjustmentDescriptionsForm form) {
        return new GetFilterAdjustmentDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterAdjustmentDescription(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form) {
        return new EditFilterAdjustmentDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentDescription(UserVisitPK userVisitPK, DeleteFilterAdjustmentDescriptionForm form) {
        return new DeleteFilterAdjustmentDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form) {
        return new CreateFilterAdjustmentAmountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form) {
        return new GetFilterAdjustmentAmountsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentAmount(UserVisitPK userVisitPK, GetFilterAdjustmentAmountForm form) {
        return new GetFilterAdjustmentAmountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentAmount(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form) {
        return new EditFilterAdjustmentAmountCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentAmountForm form) {
        return new DeleteFilterAdjustmentAmountCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amount
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentFixedAmountForm form) {
        return new CreateFilterAdjustmentFixedAmountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentFixedAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountsForm form) {
        return new GetFilterAdjustmentFixedAmountsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountForm form) {
        return new GetFilterAdjustmentFixedAmountCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, EditFilterAdjustmentFixedAmountForm form) {
        return new EditFilterAdjustmentFixedAmountCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form) {
        return new DeleteFilterAdjustmentFixedAmountCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentPercent(UserVisitPK userVisitPK, CreateFilterAdjustmentPercentForm form) {
        return new CreateFilterAdjustmentPercentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentPercents(UserVisitPK userVisitPK, GetFilterAdjustmentPercentsForm form) {
        return new GetFilterAdjustmentPercentsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentPercent(UserVisitPK userVisitPK, GetFilterAdjustmentPercentForm form) {
        return new GetFilterAdjustmentPercentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentPercent(UserVisitPK userVisitPK, EditFilterAdjustmentPercentForm form) {
        return new EditFilterAdjustmentPercentCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentPercent(UserVisitPK userVisitPK, DeleteFilterAdjustmentPercentForm form) {
        return new DeleteFilterAdjustmentPercentCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filters
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilter(UserVisitPK userVisitPK, CreateFilterForm form) {
        return new CreateFilterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilters(UserVisitPK userVisitPK, GetFiltersForm form) {
        return new GetFiltersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilter(UserVisitPK userVisitPK, GetFilterForm form) {
        return new GetFilterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterChoices(UserVisitPK userVisitPK, GetFilterChoicesForm form) {
        return new GetFilterChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFilter(UserVisitPK userVisitPK, SetDefaultFilterForm form) {
        return new SetDefaultFilterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilter(UserVisitPK userVisitPK, EditFilterForm form) {
        return new EditFilterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilter(UserVisitPK userVisitPK, DeleteFilterForm form) {
        return new DeleteFilterCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterDescription(UserVisitPK userVisitPK, CreateFilterDescriptionForm form) {
        return new CreateFilterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterDescriptions(UserVisitPK userVisitPK, GetFilterDescriptionsForm form) {
        return new GetFilterDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterDescription(UserVisitPK userVisitPK, EditFilterDescriptionForm form) {
        return new EditFilterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterDescription(UserVisitPK userVisitPK, DeleteFilterDescriptionForm form) {
        return new DeleteFilterDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStep(UserVisitPK userVisitPK, CreateFilterStepForm form) {
        return new CreateFilterStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStep(UserVisitPK userVisitPK, GetFilterStepForm form) {
        return new GetFilterStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterSteps(UserVisitPK userVisitPK, GetFilterStepsForm form) {
        return new GetFilterStepsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepChoices(UserVisitPK userVisitPK, GetFilterStepChoicesForm form) {
        return new GetFilterStepChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStep(UserVisitPK userVisitPK, EditFilterStepForm form) {
        return new EditFilterStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStep(UserVisitPK userVisitPK, DeleteFilterStepForm form) {
        return new DeleteFilterStepCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDescription(UserVisitPK userVisitPK, CreateFilterStepDescriptionForm form) {
        return new CreateFilterStepDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepDescriptions(UserVisitPK userVisitPK, GetFilterStepDescriptionsForm form) {
        return new GetFilterStepDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepDescription(UserVisitPK userVisitPK, EditFilterStepDescriptionForm form) {
        return new EditFilterStepDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepDescription(UserVisitPK userVisitPK, DeleteFilterStepDescriptionForm form) {
        return new DeleteFilterStepDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElement(UserVisitPK userVisitPK, CreateFilterStepElementForm form) {
        return new CreateFilterStepElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElement(UserVisitPK userVisitPK, GetFilterStepElementForm form) {
        return new GetFilterStepElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElements(UserVisitPK userVisitPK, GetFilterStepElementsForm form) {
        return new GetFilterStepElementsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepElement(UserVisitPK userVisitPK, EditFilterStepElementForm form) {
        return new EditFilterStepElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepElement(UserVisitPK userVisitPK, DeleteFilterStepElementForm form) {
        return new DeleteFilterStepElementCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElementDescription(UserVisitPK userVisitPK, CreateFilterStepElementDescriptionForm form) {
        return new CreateFilterStepElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElementDescriptions(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form) {
        return new GetFilterStepElementDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepElementDescription(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form) {
        return new EditFilterStepElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepElementDescription(UserVisitPK userVisitPK, DeleteFilterStepElementDescriptionForm form) {
        return new DeleteFilterStepElementDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Entrance Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterEntranceStep(UserVisitPK userVisitPK, CreateFilterEntranceStepForm form) {
        return new CreateFilterEntranceStepCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterEntranceSteps(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form) {
        return new GetFilterEntranceStepsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterEntranceStep(UserVisitPK userVisitPK, DeleteFilterEntranceStepForm form) {
        return new DeleteFilterEntranceStepCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Destinations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDestination(UserVisitPK userVisitPK, CreateFilterStepDestinationForm form) {
        return new CreateFilterStepDestinationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepDestinations(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form) {
        return new GetFilterStepDestinationsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepDestination(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form) {
        return new DeleteFilterStepDestinationCommand().run(userVisitPK, form);
    }
    
}
