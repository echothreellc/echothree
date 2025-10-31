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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateFilterKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKinds(UserVisitPK userVisitPK, GetFilterKindsForm form) {
        return CDI.current().select(GetFilterKindsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKind(UserVisitPK userVisitPK, GetFilterKindForm form) {
        return CDI.current().select(GetFilterKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindChoices(UserVisitPK userVisitPK, GetFilterKindChoicesForm form) {
        return CDI.current().select(GetFilterKindChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultFilterKind(UserVisitPK userVisitPK, SetDefaultFilterKindForm form) {
        return CDI.current().select(SetDefaultFilterKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterKind(UserVisitPK userVisitPK, EditFilterKindForm form) {
        return CDI.current().select(EditFilterKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterKind(UserVisitPK userVisitPK, DeleteFilterKindForm form) {
        return CDI.current().select(DeleteFilterKindCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterKindDescription(UserVisitPK userVisitPK, CreateFilterKindDescriptionForm form) {
        return CDI.current().select(CreateFilterKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindDescriptions(UserVisitPK userVisitPK, GetFilterKindDescriptionsForm form) {
        return CDI.current().select(GetFilterKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterKindDescription(UserVisitPK userVisitPK, GetFilterKindDescriptionForm form) {
        return CDI.current().select(GetFilterKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterKindDescription(UserVisitPK userVisitPK, EditFilterKindDescriptionForm form) {
        return CDI.current().select(EditFilterKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterKindDescription(UserVisitPK userVisitPK, DeleteFilterKindDescriptionForm form) {
        return CDI.current().select(DeleteFilterKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterType(UserVisitPK userVisitPK, CreateFilterTypeForm form) {
        return CDI.current().select(CreateFilterTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypes(UserVisitPK userVisitPK, GetFilterTypesForm form) {
        return CDI.current().select(GetFilterTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterType(UserVisitPK userVisitPK, GetFilterTypeForm form) {
        return CDI.current().select(GetFilterTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeChoices(UserVisitPK userVisitPK, GetFilterTypeChoicesForm form) {
        return CDI.current().select(GetFilterTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultFilterType(UserVisitPK userVisitPK, SetDefaultFilterTypeForm form) {
        return CDI.current().select(SetDefaultFilterTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterType(UserVisitPK userVisitPK, EditFilterTypeForm form) {
        return CDI.current().select(EditFilterTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterType(UserVisitPK userVisitPK, DeleteFilterTypeForm form) {
        return CDI.current().select(DeleteFilterTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createFilterTypeDescription(UserVisitPK userVisitPK, CreateFilterTypeDescriptionForm form) {
        return CDI.current().select(CreateFilterTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeDescriptions(UserVisitPK userVisitPK, GetFilterTypeDescriptionsForm form) {
        return CDI.current().select(GetFilterTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterTypeDescription(UserVisitPK userVisitPK, GetFilterTypeDescriptionForm form) {
        return CDI.current().select(GetFilterTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterTypeDescription(UserVisitPK userVisitPK, EditFilterTypeDescriptionForm form) {
        return CDI.current().select(EditFilterTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteFilterTypeDescription(UserVisitPK userVisitPK, DeleteFilterTypeDescriptionForm form) {
        return CDI.current().select(DeleteFilterTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSource(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceForm form) {
        return CDI.current().select(CreateFilterAdjustmentSourceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSources(UserVisitPK userVisitPK, GetFilterAdjustmentSourcesForm form) {
        return CDI.current().select(GetFilterAdjustmentSourcesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSource(UserVisitPK userVisitPK, GetFilterAdjustmentSourceForm form) {
        return CDI.current().select(GetFilterAdjustmentSourceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentSourceChoices(UserVisitPK userVisitPK, GetFilterAdjustmentSourceChoicesForm form) {
        return CDI.current().select(GetFilterAdjustmentSourceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentSourceDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentSourceDescriptionForm form) {
        return CDI.current().select(CreateFilterAdjustmentSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentType(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeForm form) {
        return CDI.current().select(CreateFilterAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentTypes(UserVisitPK userVisitPK, GetFilterAdjustmentTypesForm form) {
        return CDI.current().select(GetFilterAdjustmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentType(UserVisitPK userVisitPK, GetFilterAdjustmentTypeForm form) {
        return CDI.current().select(GetFilterAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentTypeChoices(UserVisitPK userVisitPK, GetFilterAdjustmentTypeChoicesForm form) {
        return CDI.current().select(GetFilterAdjustmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(CreateFilterAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustments
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustment(UserVisitPK userVisitPK, CreateFilterAdjustmentForm form) {
        return CDI.current().select(CreateFilterAdjustmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustment(UserVisitPK userVisitPK, GetFilterAdjustmentForm form) {
        return CDI.current().select(GetFilterAdjustmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustments(UserVisitPK userVisitPK, GetFilterAdjustmentsForm form) {
        return CDI.current().select(GetFilterAdjustmentsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustmentChoices(UserVisitPK userVisitPK, GetFilterAdjustmentChoicesForm form) {
        return CDI.current().select(GetFilterAdjustmentChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFilterAdjustment(UserVisitPK userVisitPK, SetDefaultFilterAdjustmentForm form) {
        return CDI.current().select(SetDefaultFilterAdjustmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterAdjustment(UserVisitPK userVisitPK, EditFilterAdjustmentForm form) {
        return CDI.current().select(EditFilterAdjustmentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustment(UserVisitPK userVisitPK, DeleteFilterAdjustmentForm form) {
        return CDI.current().select(DeleteFilterAdjustmentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentDescription(UserVisitPK userVisitPK, CreateFilterAdjustmentDescriptionForm form) {
        return CDI.current().select(CreateFilterAdjustmentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterAdjustmentDescriptions(UserVisitPK userVisitPK, GetFilterAdjustmentDescriptionsForm form) {
        return CDI.current().select(GetFilterAdjustmentDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterAdjustmentDescription(UserVisitPK userVisitPK, EditFilterAdjustmentDescriptionForm form) {
        return CDI.current().select(EditFilterAdjustmentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentDescription(UserVisitPK userVisitPK, DeleteFilterAdjustmentDescriptionForm form) {
        return CDI.current().select(DeleteFilterAdjustmentDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentAmountForm form) {
        return CDI.current().select(CreateFilterAdjustmentAmountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentAmountsForm form) {
        return CDI.current().select(GetFilterAdjustmentAmountsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentAmount(UserVisitPK userVisitPK, GetFilterAdjustmentAmountForm form) {
        return CDI.current().select(GetFilterAdjustmentAmountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentAmount(UserVisitPK userVisitPK, EditFilterAdjustmentAmountForm form) {
        return CDI.current().select(EditFilterAdjustmentAmountCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentAmountForm form) {
        return CDI.current().select(DeleteFilterAdjustmentAmountCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amount
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, CreateFilterAdjustmentFixedAmountForm form) {
        return CDI.current().select(CreateFilterAdjustmentFixedAmountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentFixedAmounts(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountsForm form) {
        return CDI.current().select(GetFilterAdjustmentFixedAmountsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, GetFilterAdjustmentFixedAmountForm form) {
        return CDI.current().select(GetFilterAdjustmentFixedAmountCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, EditFilterAdjustmentFixedAmountForm form) {
        return CDI.current().select(EditFilterAdjustmentFixedAmountCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentFixedAmount(UserVisitPK userVisitPK, DeleteFilterAdjustmentFixedAmountForm form) {
        return CDI.current().select(DeleteFilterAdjustmentFixedAmountCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterAdjustmentPercent(UserVisitPK userVisitPK, CreateFilterAdjustmentPercentForm form) {
        return CDI.current().select(CreateFilterAdjustmentPercentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentPercents(UserVisitPK userVisitPK, GetFilterAdjustmentPercentsForm form) {
        return CDI.current().select(GetFilterAdjustmentPercentsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getFilterAdjustmentPercent(UserVisitPK userVisitPK, GetFilterAdjustmentPercentForm form) {
        return CDI.current().select(GetFilterAdjustmentPercentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editFilterAdjustmentPercent(UserVisitPK userVisitPK, EditFilterAdjustmentPercentForm form) {
        return CDI.current().select(EditFilterAdjustmentPercentCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterAdjustmentPercent(UserVisitPK userVisitPK, DeleteFilterAdjustmentPercentForm form) {
        return CDI.current().select(DeleteFilterAdjustmentPercentCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filters
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilter(UserVisitPK userVisitPK, CreateFilterForm form) {
        return CDI.current().select(CreateFilterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilters(UserVisitPK userVisitPK, GetFiltersForm form) {
        return CDI.current().select(GetFiltersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilter(UserVisitPK userVisitPK, GetFilterForm form) {
        return CDI.current().select(GetFilterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterChoices(UserVisitPK userVisitPK, GetFilterChoicesForm form) {
        return CDI.current().select(GetFilterChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultFilter(UserVisitPK userVisitPK, SetDefaultFilterForm form) {
        return CDI.current().select(SetDefaultFilterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilter(UserVisitPK userVisitPK, EditFilterForm form) {
        return CDI.current().select(EditFilterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilter(UserVisitPK userVisitPK, DeleteFilterForm form) {
        return CDI.current().select(DeleteFilterCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterDescription(UserVisitPK userVisitPK, CreateFilterDescriptionForm form) {
        return CDI.current().select(CreateFilterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterDescriptions(UserVisitPK userVisitPK, GetFilterDescriptionsForm form) {
        return CDI.current().select(GetFilterDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterDescription(UserVisitPK userVisitPK, EditFilterDescriptionForm form) {
        return CDI.current().select(EditFilterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterDescription(UserVisitPK userVisitPK, DeleteFilterDescriptionForm form) {
        return CDI.current().select(DeleteFilterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStep(UserVisitPK userVisitPK, CreateFilterStepForm form) {
        return CDI.current().select(CreateFilterStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStep(UserVisitPK userVisitPK, GetFilterStepForm form) {
        return CDI.current().select(GetFilterStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterSteps(UserVisitPK userVisitPK, GetFilterStepsForm form) {
        return CDI.current().select(GetFilterStepsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepChoices(UserVisitPK userVisitPK, GetFilterStepChoicesForm form) {
        return CDI.current().select(GetFilterStepChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStep(UserVisitPK userVisitPK, EditFilterStepForm form) {
        return CDI.current().select(EditFilterStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStep(UserVisitPK userVisitPK, DeleteFilterStepForm form) {
        return CDI.current().select(DeleteFilterStepCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDescription(UserVisitPK userVisitPK, CreateFilterStepDescriptionForm form) {
        return CDI.current().select(CreateFilterStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepDescriptions(UserVisitPK userVisitPK, GetFilterStepDescriptionsForm form) {
        return CDI.current().select(GetFilterStepDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepDescription(UserVisitPK userVisitPK, EditFilterStepDescriptionForm form) {
        return CDI.current().select(EditFilterStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepDescription(UserVisitPK userVisitPK, DeleteFilterStepDescriptionForm form) {
        return CDI.current().select(DeleteFilterStepDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElement(UserVisitPK userVisitPK, CreateFilterStepElementForm form) {
        return CDI.current().select(CreateFilterStepElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElement(UserVisitPK userVisitPK, GetFilterStepElementForm form) {
        return CDI.current().select(GetFilterStepElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElements(UserVisitPK userVisitPK, GetFilterStepElementsForm form) {
        return CDI.current().select(GetFilterStepElementsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepElement(UserVisitPK userVisitPK, EditFilterStepElementForm form) {
        return CDI.current().select(EditFilterStepElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepElement(UserVisitPK userVisitPK, DeleteFilterStepElementForm form) {
        return CDI.current().select(DeleteFilterStepElementCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepElementDescription(UserVisitPK userVisitPK, CreateFilterStepElementDescriptionForm form) {
        return CDI.current().select(CreateFilterStepElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepElementDescriptions(UserVisitPK userVisitPK, GetFilterStepElementDescriptionsForm form) {
        return CDI.current().select(GetFilterStepElementDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editFilterStepElementDescription(UserVisitPK userVisitPK, EditFilterStepElementDescriptionForm form) {
        return CDI.current().select(EditFilterStepElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepElementDescription(UserVisitPK userVisitPK, DeleteFilterStepElementDescriptionForm form) {
        return CDI.current().select(DeleteFilterStepElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Entrance Steps
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterEntranceStep(UserVisitPK userVisitPK, CreateFilterEntranceStepForm form) {
        return CDI.current().select(CreateFilterEntranceStepCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterEntranceSteps(UserVisitPK userVisitPK, GetFilterEntranceStepsForm form) {
        return CDI.current().select(GetFilterEntranceStepsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterEntranceStep(UserVisitPK userVisitPK, DeleteFilterEntranceStepForm form) {
        return CDI.current().select(DeleteFilterEntranceStepCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Filter Step Destinations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFilterStepDestination(UserVisitPK userVisitPK, CreateFilterStepDestinationForm form) {
        return CDI.current().select(CreateFilterStepDestinationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFilterStepDestinations(UserVisitPK userVisitPK, GetFilterStepDestinationsForm form) {
        return CDI.current().select(GetFilterStepDestinationsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteFilterStepDestination(UserVisitPK userVisitPK, DeleteFilterStepDestinationForm form) {
        return CDI.current().select(DeleteFilterStepDestinationCommand.class).get().run(userVisitPK, form);
    }
    
}
