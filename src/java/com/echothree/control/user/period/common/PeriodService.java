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
import com.echothree.control.user.period.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface PeriodService
        extends PeriodForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Period Kinds
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPeriodKind(UserVisitPK userVisitPK, CreatePeriodKindForm form);
    
    CommandResult<GetPeriodKindsResult> getPeriodKinds(UserVisitPK userVisitPK, GetPeriodKindsForm form);
    
    CommandResult<GetPeriodKindResult> getPeriodKind(UserVisitPK userVisitPK, GetPeriodKindForm form);
    
    CommandResult<GetPeriodKindChoicesResult> getPeriodKindChoices(UserVisitPK userVisitPK, GetPeriodKindChoicesForm form);
    
    CommandResult<VoidResult> setDefaultPeriodKind(UserVisitPK userVisitPK, SetDefaultPeriodKindForm form);
    
    CommandResult<EditPeriodKindResult> editPeriodKind(UserVisitPK userVisitPK, EditPeriodKindForm form);
    
    CommandResult<VoidResult> deletePeriodKind(UserVisitPK userVisitPK, DeletePeriodKindForm form);
    
    // -------------------------------------------------------------------------
    //   Period Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPeriodKindDescription(UserVisitPK userVisitPK, CreatePeriodKindDescriptionForm form);
    
    CommandResult<GetPeriodKindDescriptionsResult> getPeriodKindDescriptions(UserVisitPK userVisitPK, GetPeriodKindDescriptionsForm form);
    
    CommandResult<EditPeriodKindDescriptionResult> editPeriodKindDescription(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form);
    
    CommandResult<VoidResult> deletePeriodKindDescription(UserVisitPK userVisitPK, DeletePeriodKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Period Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPeriodType(UserVisitPK userVisitPK, CreatePeriodTypeForm form);
    
    CommandResult<GetPeriodTypesResult> getPeriodTypes(UserVisitPK userVisitPK, GetPeriodTypesForm form);
    
    CommandResult<GetPeriodTypeResult> getPeriodType(UserVisitPK userVisitPK, GetPeriodTypeForm form);
    
    CommandResult<GetPeriodTypeChoicesResult> getPeriodTypeChoices(UserVisitPK userVisitPK, GetPeriodTypeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultPeriodType(UserVisitPK userVisitPK, SetDefaultPeriodTypeForm form);
    
    CommandResult<EditPeriodTypeResult> editPeriodType(UserVisitPK userVisitPK, EditPeriodTypeForm form);
    
    CommandResult<VoidResult> deletePeriodType(UserVisitPK userVisitPK, DeletePeriodTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Period Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPeriodTypeDescription(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form);
    
    CommandResult<GetPeriodTypeDescriptionsResult> getPeriodTypeDescriptions(UserVisitPK userVisitPK, GetPeriodTypeDescriptionsForm form);
    
    CommandResult<EditPeriodTypeDescriptionResult> editPeriodTypeDescription(UserVisitPK userVisitPK, EditPeriodTypeDescriptionForm form);
    
    CommandResult<VoidResult> deletePeriodTypeDescription(UserVisitPK userVisitPK, DeletePeriodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Fiscal Periods
    // -------------------------------------------------------------------------
    
    CommandResult<CreateFiscalYearResult> createFiscalYear(UserVisitPK userVisitPK, CreateFiscalYearForm form);
    
    CommandResult<GetFiscalPeriodStatusChoicesResult> getFiscalPeriodStatusChoices(UserVisitPK userVisitPK, GetFiscalPeriodStatusChoicesForm form);
    
    CommandResult<VoidResult> setFiscalPeriodStatus(UserVisitPK userVisitPK, SetFiscalPeriodStatusForm form);
    
}
