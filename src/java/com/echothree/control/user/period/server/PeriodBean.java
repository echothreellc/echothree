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

package com.echothree.control.user.period.server;

import com.echothree.control.user.period.common.PeriodRemote;
import com.echothree.control.user.period.common.form.*;
import com.echothree.control.user.period.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class PeriodBean
        extends PeriodFormsImpl
        implements PeriodRemote, PeriodLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "PeriodBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Period Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodKind(UserVisitPK userVisitPK, CreatePeriodKindForm form) {
        return new CreatePeriodKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKinds(UserVisitPK userVisitPK, GetPeriodKindsForm form) {
        return new GetPeriodKindsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKind(UserVisitPK userVisitPK, GetPeriodKindForm form) {
        return new GetPeriodKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKindChoices(UserVisitPK userVisitPK, GetPeriodKindChoicesForm form) {
        return new GetPeriodKindChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPeriodKind(UserVisitPK userVisitPK, SetDefaultPeriodKindForm form) {
        return new SetDefaultPeriodKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodKind(UserVisitPK userVisitPK, EditPeriodKindForm form) {
        return new EditPeriodKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodKind(UserVisitPK userVisitPK, DeletePeriodKindForm form) {
        return new DeletePeriodKindCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodKindDescription(UserVisitPK userVisitPK, CreatePeriodKindDescriptionForm form) {
        return new CreatePeriodKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKindDescriptions(UserVisitPK userVisitPK, GetPeriodKindDescriptionsForm form) {
        return new GetPeriodKindDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodKindDescription(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form) {
        return new EditPeriodKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodKindDescription(UserVisitPK userVisitPK, DeletePeriodKindDescriptionForm form) {
        return new DeletePeriodKindDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodType(UserVisitPK userVisitPK, CreatePeriodTypeForm form) {
        return new CreatePeriodTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypes(UserVisitPK userVisitPK, GetPeriodTypesForm form) {
        return new GetPeriodTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodType(UserVisitPK userVisitPK, GetPeriodTypeForm form) {
        return new GetPeriodTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypeChoices(UserVisitPK userVisitPK, GetPeriodTypeChoicesForm form) {
        return new GetPeriodTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPeriodType(UserVisitPK userVisitPK, SetDefaultPeriodTypeForm form) {
        return new SetDefaultPeriodTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodType(UserVisitPK userVisitPK, EditPeriodTypeForm form) {
        return new EditPeriodTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodType(UserVisitPK userVisitPK, DeletePeriodTypeForm form) {
        return new DeletePeriodTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodTypeDescription(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form) {
        return new CreatePeriodTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypeDescriptions(UserVisitPK userVisitPK, GetPeriodTypeDescriptionsForm form) {
        return new GetPeriodTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodTypeDescription(UserVisitPK userVisitPK, EditPeriodTypeDescriptionForm form) {
        return new EditPeriodTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodTypeDescription(UserVisitPK userVisitPK, DeletePeriodTypeDescriptionForm form) {
        return new DeletePeriodTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Fiscal Periods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFiscalYear(UserVisitPK userVisitPK, CreateFiscalYearForm form) {
        return new CreateFiscalYearCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFiscalPeriodStatusChoices(UserVisitPK userVisitPK, GetFiscalPeriodStatusChoicesForm form) {
        return new GetFiscalPeriodStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setFiscalPeriodStatus(UserVisitPK userVisitPK, SetFiscalPeriodStatusForm form) {
        return new SetFiscalPeriodStatusCommand().run(userVisitPK, form);
    }
    
}
