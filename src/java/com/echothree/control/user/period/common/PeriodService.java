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

package com.echothree.control.user.period.common;

import com.echothree.control.user.period.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface PeriodService
        extends PeriodForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Period Kinds
    // -------------------------------------------------------------------------
    
    CommandResult createPeriodKind(UserVisitPK userVisitPK, CreatePeriodKindForm form);
    
    CommandResult getPeriodKinds(UserVisitPK userVisitPK, GetPeriodKindsForm form);
    
    CommandResult getPeriodKind(UserVisitPK userVisitPK, GetPeriodKindForm form);
    
    CommandResult getPeriodKindChoices(UserVisitPK userVisitPK, GetPeriodKindChoicesForm form);
    
    CommandResult setDefaultPeriodKind(UserVisitPK userVisitPK, SetDefaultPeriodKindForm form);
    
    CommandResult editPeriodKind(UserVisitPK userVisitPK, EditPeriodKindForm form);
    
    CommandResult deletePeriodKind(UserVisitPK userVisitPK, DeletePeriodKindForm form);
    
    // -------------------------------------------------------------------------
    //   Period Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPeriodKindDescription(UserVisitPK userVisitPK, CreatePeriodKindDescriptionForm form);
    
    CommandResult getPeriodKindDescriptions(UserVisitPK userVisitPK, GetPeriodKindDescriptionsForm form);
    
    CommandResult editPeriodKindDescription(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form);
    
    CommandResult deletePeriodKindDescription(UserVisitPK userVisitPK, DeletePeriodKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Period Types
    // -------------------------------------------------------------------------
    
    CommandResult createPeriodType(UserVisitPK userVisitPK, CreatePeriodTypeForm form);
    
    CommandResult getPeriodTypes(UserVisitPK userVisitPK, GetPeriodTypesForm form);
    
    CommandResult getPeriodType(UserVisitPK userVisitPK, GetPeriodTypeForm form);
    
    CommandResult getPeriodTypeChoices(UserVisitPK userVisitPK, GetPeriodTypeChoicesForm form);
    
    CommandResult setDefaultPeriodType(UserVisitPK userVisitPK, SetDefaultPeriodTypeForm form);
    
    CommandResult editPeriodType(UserVisitPK userVisitPK, EditPeriodTypeForm form);
    
    CommandResult deletePeriodType(UserVisitPK userVisitPK, DeletePeriodTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Period Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPeriodTypeDescription(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form);
    
    CommandResult getPeriodTypeDescriptions(UserVisitPK userVisitPK, GetPeriodTypeDescriptionsForm form);
    
    CommandResult editPeriodTypeDescription(UserVisitPK userVisitPK, EditPeriodTypeDescriptionForm form);
    
    CommandResult deletePeriodTypeDescription(UserVisitPK userVisitPK, DeletePeriodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Fiscal Periods
    // -------------------------------------------------------------------------
    
    CommandResult createFiscalYear(UserVisitPK userVisitPK, CreateFiscalYearForm form);
    
    CommandResult getFiscalPeriodStatusChoices(UserVisitPK userVisitPK, GetFiscalPeriodStatusChoicesForm form);
    
    CommandResult setFiscalPeriodStatus(UserVisitPK userVisitPK, SetFiscalPeriodStatusForm form);
    
}
