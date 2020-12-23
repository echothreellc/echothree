// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
        return new CreateFilterKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterKinds(UserVisitPK userVisitPK, GetFilterKindsForm form) {
        return new GetFilterKindsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterKind(UserVisitPK userVisitPK, GetFilterKindForm form) {
        return new GetFilterKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterKindChoices(UserVisitPK userVisitPK, GetFilterKindChoicesForm form) {
        return new GetFilterKindChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultFilterKind(UserVisitPK userVisitPK, SetDefaultFilterKindForm form) {
        return new SetDefaultFilterKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFilterKind(UserVisitPK userVisitPK, EditFilterKindForm form) {
        return new EditFilterKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFilterKind(UserVisitPK userVisitPK, DeleteFilterKindForm form) {
        return new DeleteFilterKindCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterKindDescription(UserVisitPK userVisitPK, CreateFilterKindDescriptionForm form) {
        return new CreateFilterKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterKindDescriptions(UserVisitPK userVisitPK, GetFilterKindDescriptionsForm form) {
        return new GetFilterKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterKindDescription(UserVisitPK userVisitPK, GetFilterKindDescriptionForm form) {
        return new GetFilterKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFilterKindDescription(UserVisitPK userVisitPK, EditFilterKindDescriptionForm form) {
        return new EditFilterKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFilterKindDescription(UserVisitPK userVisitPK, DeleteFilterKindDescriptionForm form) {
        return new DeleteFilterKindDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Filter Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterType(UserVisitPK userVisitPK, CreateFilterTypeForm form) {
        return new CreateFilterTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterTypes(UserVisitPK userVisitPK, GetFilterTypesForm form) {
        return new GetFilterTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterType(UserVisitPK userVisitPK, GetFilterTypeForm form) {
        return new GetFilterTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterTypeChoices(UserVisitPK userVisitPK, GetFilterTypeChoicesForm form) {
        return new GetFilterTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultFilterType(UserVisitPK userVisitPK, SetDefaultFilterTypeForm form) {
        return new SetDefaultFilterTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFilterType(UserVisitPK userVisitPK, EditFilterTypeForm form) {
        return new EditFilterTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFilterType(UserVisitPK userVisitPK, DeleteFilterTypeForm form) {
        return new DeleteFilterTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Filter Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterTypeDescription(UserVisitPK userVisitPK, CreateFilterTypeDescriptionForm form) {
        return new CreateFilterTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterTypeDescriptions(UserVisitPK userVisitPK, GetFilterTypeDescriptionsForm form) {
        return new GetFilterTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterTypeDescription(UserVisitPK userVisitPK, GetFilterTypeDescriptionForm form) {
        return new GetFilterTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editFilterTypeDescription(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form) {
        return new EditFilterTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteFilterTypeDescription(UserVisitPK userVisitPK, DeleteFilterTypeDescriptionForm form) {
        return new DeleteFilterTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSource(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceForm form) {
        return new CreateFilterAdjustmentSourceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentSources(UserVisitPK userVisitPK, GetFilterAdjustmentSourcesForm form) {
        return new GetFilterAdjustmentSourcesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentSource(UserVisitPK userVisitPK, GetFilterAdjustmentSourceForm form) {
        return new GetFilterAdjustmentSourceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentSourceChoices(UserVisitPK userVisitPK, GetFilterAdjustmentSourceChoicesForm form) {
        return new GetFilterAdjustmentSourceChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSourceDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceDescriptionForm form) {
        return new CreateFilterAdjustmentSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentType(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeForm form) {
        return new CreateFilterAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentTypes(UserVisitPK userVisitPK, GetFilterAdjustmentTypesForm form) {
        return new GetFilterAdjustmentTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentType(UserVisitPK userVisitPK, GetFilterAdjustmentTypeForm form) {
        return new GetFilterAdjustmentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getFilterAdjustmentTypeChoices(UserVisitPK userVisitPK, GetFilterAdjustmentTypeChoicesForm form) {
        return new GetFilterAdjustmentTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeDescriptionForm form) {
        return new CreateFilterAdjustmentTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustment(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form) {
        return new CreateFilterAdjustmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustment(UserVisitPK userVisitPK, GetFilterAdjustmentForm form) {
        return new GetFilterAdjustmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustments(UserVisitPK userVisitPK, GetFilterAdjustmentsForm form) {
        return new GetFilterAdjustmentsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustmentChoices(UserVisitPK userVisitPK, GetFilterAdjustmentChoicesForm form) {
        return new GetFilterAdjustmentChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFilterAdjustment(UserVisitPK userVisitPK, SetDefaultFilterAdjustmentForm form) {
        return new SetDefaultFilterAdjustmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterAdjustment(UserVisitPK userVisitPK, EditFilterAdjustmentForm form) {
        return new EditFilterAdjustmentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterAdjustment(UserVisitPK userVisitPK, DeleteFilterAdjustmentForm form) {
        return new DeleteFilterAdjustmentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentDescriptionForm form) {
        return new CreateFilterAdjustmentDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustmentDescriptions(UserVisitPK userVisitPK, GetFilterAdjustmentDescriptionsForm form) {
        return new GetFilterAdjustmentDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterAdjustmentDescription(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form) {
        return new EditFilterAdjustmentDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentDescription(UserVisitPK userVisitPK, DeleteFilterAdjustmentDescriptionForm form) {
        return new DeleteFilterAdjustmentDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form) {
        return new CreateFilterAdjustmentAmountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustmentAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form) {
        return new GetFilterAdjustmentAmountsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterAdjustmentAmount(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form) {
        return new EditFilterAdjustmentAmountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentAmountForm form) {
        return new DeleteFilterAdjustmentAmountCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amount
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentFixedAmountForm form) {
        return new CreateFilterAdjustmentFixedAmountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustmentFixedAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountsForm form) {
        return new GetFilterAdjustmentFixedAmountsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, EditFilterAdjustmentFixedAmountForm form) {
        return new EditFilterAdjustmentFixedAmountCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form) {
        return new DeleteFilterAdjustmentFixedAmountCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentPercent(UserVisitPK userVisitPK, CreateFilterAdjustmentPercentForm form) {
        return new CreateFilterAdjustmentPercentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterAdjustmentPercents(UserVisitPK userVisitPK, GetFilterAdjustmentPercentsForm form) {
        return new GetFilterAdjustmentPercentsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterAdjustmentPercent(UserVisitPK userVisitPK, EditFilterAdjustmentPercentForm form) {
        return new EditFilterAdjustmentPercentCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentPercent(UserVisitPK userVisitPK, DeleteFilterAdjustmentPercentForm form) {
        return new DeleteFilterAdjustmentPercentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filters
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilter(UserVisitPK userVisitPK, CreateFilterForm form) {
        return new CreateFilterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilters(UserVisitPK userVisitPK, GetFiltersForm form) {
        return new GetFiltersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilter(UserVisitPK userVisitPK, GetFilterForm form) {
        return new GetFilterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterChoices(UserVisitPK userVisitPK, GetFilterChoicesForm form) {
        return new GetFilterChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFilter(UserVisitPK userVisitPK, SetDefaultFilterForm form) {
        return new SetDefaultFilterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilter(UserVisitPK userVisitPK, EditFilterForm form) {
        return new EditFilterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilter(UserVisitPK userVisitPK, DeleteFilterForm form) {
        return new DeleteFilterCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterDescription(UserVisitPK userVisitPK, CreateFilterDescriptionForm form) {
        return new CreateFilterDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterDescriptions(UserVisitPK userVisitPK, GetFilterDescriptionsForm form) {
        return new GetFilterDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterDescription(UserVisitPK userVisitPK, EditFilterDescriptionForm form) {
        return new EditFilterDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterDescription(UserVisitPK userVisitPK, DeleteFilterDescriptionForm form) {
        return new DeleteFilterDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStep(UserVisitPK userVisitPK, CreateFilterStepForm form) {
        return new CreateFilterStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStep(UserVisitPK userVisitPK, GetFilterStepForm form) {
        return new GetFilterStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterSteps(UserVisitPK userVisitPK, GetFilterStepsForm form) {
        return new GetFilterStepsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepChoices(UserVisitPK userVisitPK, GetFilterStepChoicesForm form) {
        return new GetFilterStepChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterStep(UserVisitPK userVisitPK, EditFilterStepForm form) {
        return new EditFilterStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterStep(UserVisitPK userVisitPK, DeleteFilterStepForm form) {
        return new DeleteFilterStepCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDescription(UserVisitPK userVisitPK, CreateFilterStepDescriptionForm form) {
        return new CreateFilterStepDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepDescriptions(UserVisitPK userVisitPK, GetFilterStepDescriptionsForm form) {
        return new GetFilterStepDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterStepDescription(UserVisitPK userVisitPK, EditFilterStepDescriptionForm form) {
        return new EditFilterStepDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterStepDescription(UserVisitPK userVisitPK, DeleteFilterStepDescriptionForm form) {
        return new DeleteFilterStepDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElement(UserVisitPK userVisitPK, CreateFilterStepElementForm form) {
        return new CreateFilterStepElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepElement(UserVisitPK userVisitPK, GetFilterStepElementForm form) {
        return new GetFilterStepElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepElements(UserVisitPK userVisitPK, GetFilterStepElementsForm form) {
        return new GetFilterStepElementsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterStepElement(UserVisitPK userVisitPK, EditFilterStepElementForm form) {
        return new EditFilterStepElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterStepElement(UserVisitPK userVisitPK, DeleteFilterStepElementForm form) {
        return new DeleteFilterStepElementCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElementDescription(UserVisitPK userVisitPK, CreateFilterStepElementDescriptionForm form) {
        return new CreateFilterStepElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepElementDescriptions(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form) {
        return new GetFilterStepElementDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFilterStepElementDescription(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form) {
        return new EditFilterStepElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterStepElementDescription(UserVisitPK userVisitPK, DeleteFilterStepElementDescriptionForm form) {
        return new DeleteFilterStepElementDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Entrance Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterEntranceStep(UserVisitPK userVisitPK, CreateFilterEntranceStepForm form) {
        return new CreateFilterEntranceStepCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterEntranceSteps(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form) {
        return new GetFilterEntranceStepsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterEntranceStep(UserVisitPK userVisitPK, DeleteFilterEntranceStepForm form) {
        return new DeleteFilterEntranceStepCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Destinations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDestination(UserVisitPK userVisitPK, CreateFilterStepDestinationForm form) {
        return new CreateFilterStepDestinationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFilterStepDestinations(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form) {
        return new GetFilterStepDestinationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFilterStepDestination(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form) {
        return new DeleteFilterStepDestinationCommand(userVisitPK, form).run();
    }
    
}
