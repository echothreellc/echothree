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

package com.echothree.control.user.period.server;

import com.echothree.control.user.period.common.PeriodRemote;
import com.echothree.control.user.period.common.form.*;
import com.echothree.control.user.period.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreatePeriodKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKinds(UserVisitPK userVisitPK, GetPeriodKindsForm form) {
        return CDI.current().select(GetPeriodKindsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKind(UserVisitPK userVisitPK, GetPeriodKindForm form) {
        return CDI.current().select(GetPeriodKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKindChoices(UserVisitPK userVisitPK, GetPeriodKindChoicesForm form) {
        return CDI.current().select(GetPeriodKindChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPeriodKind(UserVisitPK userVisitPK, SetDefaultPeriodKindForm form) {
        return CDI.current().select(SetDefaultPeriodKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodKind(UserVisitPK userVisitPK, EditPeriodKindForm form) {
        return CDI.current().select(EditPeriodKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodKind(UserVisitPK userVisitPK, DeletePeriodKindForm form) {
        return CDI.current().select(DeletePeriodKindCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodKindDescription(UserVisitPK userVisitPK, CreatePeriodKindDescriptionForm form) {
        return CDI.current().select(CreatePeriodKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodKindDescriptions(UserVisitPK userVisitPK, GetPeriodKindDescriptionsForm form) {
        return CDI.current().select(GetPeriodKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodKindDescription(UserVisitPK userVisitPK, EditPeriodKindDescriptionForm form) {
        return CDI.current().select(EditPeriodKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodKindDescription(UserVisitPK userVisitPK, DeletePeriodKindDescriptionForm form) {
        return CDI.current().select(DeletePeriodKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodType(UserVisitPK userVisitPK, CreatePeriodTypeForm form) {
        return CDI.current().select(CreatePeriodTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypes(UserVisitPK userVisitPK, GetPeriodTypesForm form) {
        return CDI.current().select(GetPeriodTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodType(UserVisitPK userVisitPK, GetPeriodTypeForm form) {
        return CDI.current().select(GetPeriodTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypeChoices(UserVisitPK userVisitPK, GetPeriodTypeChoicesForm form) {
        return CDI.current().select(GetPeriodTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPeriodType(UserVisitPK userVisitPK, SetDefaultPeriodTypeForm form) {
        return CDI.current().select(SetDefaultPeriodTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodType(UserVisitPK userVisitPK, EditPeriodTypeForm form) {
        return CDI.current().select(EditPeriodTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodType(UserVisitPK userVisitPK, DeletePeriodTypeForm form) {
        return CDI.current().select(DeletePeriodTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Period Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPeriodTypeDescription(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form) {
        return CDI.current().select(CreatePeriodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPeriodTypeDescriptions(UserVisitPK userVisitPK, GetPeriodTypeDescriptionsForm form) {
        return CDI.current().select(GetPeriodTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPeriodTypeDescription(UserVisitPK userVisitPK, EditPeriodTypeDescriptionForm form) {
        return CDI.current().select(EditPeriodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePeriodTypeDescription(UserVisitPK userVisitPK, DeletePeriodTypeDescriptionForm form) {
        return CDI.current().select(DeletePeriodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Fiscal Periods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createFiscalYear(UserVisitPK userVisitPK, CreateFiscalYearForm form) {
        return CDI.current().select(CreateFiscalYearCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getFiscalPeriodStatusChoices(UserVisitPK userVisitPK, GetFiscalPeriodStatusChoicesForm form) {
        return CDI.current().select(GetFiscalPeriodStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setFiscalPeriodStatus(UserVisitPK userVisitPK, SetFiscalPeriodStatusForm form) {
        return CDI.current().select(SetFiscalPeriodStatusCommand.class).get().run(userVisitPK, form);
    }
    
}
