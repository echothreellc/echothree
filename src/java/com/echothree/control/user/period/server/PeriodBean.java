// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
        return new CreatePeriodKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodKinds(UserVisitPK userVisitPK, GetPeriodKindsForm form) {
        return new GetPeriodKindsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodKind(UserVisitPK userVisitPK, GetPeriodKindForm form) {
        return new GetPeriodKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodKindChoices(UserVisitPK userVisitPK, GetPeriodKindChoicesForm form) {
        return new GetPeriodKindChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPeriodKind(UserVisitPK userVisitPK, SetDefaultPeriodKindForm form) {
        return new SetDefaultPeriodKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPeriodKind(UserVisitPK userVisitPK, EditPeriodKindForm form) {
        return new EditPeriodKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePeriodKind(UserVisitPK userVisitPK, DeletePeriodKindForm form) {
        return new DeletePeriodKindCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Period Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodKindDescription(UserVisitPK userVisitPK, CreatePeriodKindDescriptionForm form) {
        return new CreatePeriodKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodKindDescriptions(UserVisitPK userVisitPK, GetPeriodKindDescriptionsForm form) {
        return new GetPeriodKindDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPeriodKindDescription(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form) {
        return new EditPeriodKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePeriodKindDescription(UserVisitPK userVisitPK, DeletePeriodKindDescriptionForm form) {
        return new DeletePeriodKindDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Period Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodType(UserVisitPK userVisitPK, CreatePeriodTypeForm form) {
        return new CreatePeriodTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodTypes(UserVisitPK userVisitPK, GetPeriodTypesForm form) {
        return new GetPeriodTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodType(UserVisitPK userVisitPK, GetPeriodTypeForm form) {
        return new GetPeriodTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodTypeChoices(UserVisitPK userVisitPK, GetPeriodTypeChoicesForm form) {
        return new GetPeriodTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPeriodType(UserVisitPK userVisitPK, SetDefaultPeriodTypeForm form) {
        return new SetDefaultPeriodTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPeriodType(UserVisitPK userVisitPK, EditPeriodTypeForm form) {
        return new EditPeriodTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePeriodType(UserVisitPK userVisitPK, DeletePeriodTypeForm form) {
        return new DeletePeriodTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Period Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodTypeDescription(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form) {
        return new CreatePeriodTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPeriodTypeDescriptions(UserVisitPK userVisitPK, GetPeriodTypeDescriptionsForm form) {
        return new GetPeriodTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPeriodTypeDescription(UserVisitPK userVisitPK, EditPeriodTypeDescriptionForm form) {
        return new EditPeriodTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePeriodTypeDescription(UserVisitPK userVisitPK, DeletePeriodTypeDescriptionForm form) {
        return new DeletePeriodTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Fiscal Periods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFiscalYear(UserVisitPK userVisitPK, CreateFiscalYearForm form) {
        return new CreateFiscalYearCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFiscalPeriodStatusChoices(UserVisitPK userVisitPK, GetFiscalPeriodStatusChoicesForm form) {
        return new GetFiscalPeriodStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setFiscalPeriodStatus(UserVisitPK userVisitPK, SetFiscalPeriodStatusForm form) {
        return new SetFiscalPeriodStatusCommand(userVisitPK, form).run();
    }
    
}
