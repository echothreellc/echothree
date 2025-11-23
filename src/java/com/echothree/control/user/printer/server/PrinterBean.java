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

package com.echothree.control.user.printer.server;

import com.echothree.control.user.printer.common.PrinterRemote;
import com.echothree.control.user.printer.common.form.*;
import com.echothree.control.user.printer.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class PrinterBean
        extends PrinterFormsImpl
        implements PrinterRemote, PrinterLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "PrinterBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Printer Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterGroup(UserVisitPK userVisitPK, CreatePrinterGroupForm form) {
        return CDI.current().select(CreatePrinterGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupChoices(UserVisitPK userVisitPK, GetPrinterGroupChoicesForm form) {
        return CDI.current().select(GetPrinterGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroup(UserVisitPK userVisitPK, GetPrinterGroupForm form) {
        return CDI.current().select(GetPrinterGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroups(UserVisitPK userVisitPK, GetPrinterGroupsForm form) {
        return CDI.current().select(GetPrinterGroupsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPrinterGroup(UserVisitPK userVisitPK, SetDefaultPrinterGroupForm form) {
        return CDI.current().select(SetDefaultPrinterGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPrinterGroup(UserVisitPK userVisitPK, EditPrinterGroupForm form) {
        return CDI.current().select(EditPrinterGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupStatusChoicesForm form) {
        return CDI.current().select(GetPrinterGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPrinterGroupStatus(UserVisitPK userVisitPK, SetPrinterGroupStatusForm form) {
        return CDI.current().select(SetPrinterGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterGroup(UserVisitPK userVisitPK, DeletePrinterGroupForm form) {
        return CDI.current().select(DeletePrinterGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterGroupDescription(UserVisitPK userVisitPK, CreatePrinterGroupDescriptionForm form) {
        return CDI.current().select(CreatePrinterGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupDescription(UserVisitPK userVisitPK, GetPrinterGroupDescriptionForm form) {
        return CDI.current().select(GetPrinterGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupDescriptions(UserVisitPK userVisitPK, GetPrinterGroupDescriptionsForm form) {
        return CDI.current().select(GetPrinterGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupDescription(UserVisitPK userVisitPK, EditPrinterGroupDescriptionForm form) {
        return CDI.current().select(EditPrinterGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterGroupDescription(UserVisitPK userVisitPK, DeletePrinterGroupDescriptionForm form) {
        return CDI.current().select(DeletePrinterGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinter(UserVisitPK userVisitPK, CreatePrinterForm form) {
        return CDI.current().select(CreatePrinterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinter(UserVisitPK userVisitPK, GetPrinterForm form) {
        return CDI.current().select(GetPrinterCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinters(UserVisitPK userVisitPK, GetPrintersForm form) {
        return CDI.current().select(GetPrintersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinter(UserVisitPK userVisitPK, EditPrinterForm form) {
        return CDI.current().select(EditPrinterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterStatusChoices(UserVisitPK userVisitPK, GetPrinterStatusChoicesForm form) {
        return CDI.current().select(GetPrinterStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPrinterStatus(UserVisitPK userVisitPK, SetPrinterStatusForm form) {
        return CDI.current().select(SetPrinterStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinter(UserVisitPK userVisitPK, DeletePrinterForm form) {
        return CDI.current().select(DeletePrinterCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterDescription(UserVisitPK userVisitPK, CreatePrinterDescriptionForm form) {
        return CDI.current().select(CreatePrinterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterDescription(UserVisitPK userVisitPK, GetPrinterDescriptionForm form) {
        return CDI.current().select(GetPrinterDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterDescriptions(UserVisitPK userVisitPK, GetPrinterDescriptionsForm form) {
        return CDI.current().select(GetPrinterDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterDescription(UserVisitPK userVisitPK, EditPrinterDescriptionForm form) {
        return CDI.current().select(EditPrinterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterDescription(UserVisitPK userVisitPK, DeletePrinterDescriptionForm form) {
        return CDI.current().select(DeletePrinterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Group Jobs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterGroupJob(UserVisitPK userVisitPK, CreatePrinterGroupJobForm form) {
        return CDI.current().select(CreatePrinterGroupJobCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJob(UserVisitPK userVisitPK, GetPrinterGroupJobForm form) {
        return CDI.current().select(GetPrinterGroupJobCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJobs(UserVisitPK userVisitPK, GetPrinterGroupJobsForm form) {
        return CDI.current().select(GetPrinterGroupJobsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJobStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupJobStatusChoicesForm form) {
        return CDI.current().select(GetPrinterGroupJobStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPrinterGroupJobStatus(UserVisitPK userVisitPK, SetPrinterGroupJobStatusForm form) {
        return CDI.current().select(SetPrinterGroupJobStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupJob(UserVisitPK userVisitPK, DeletePrinterGroupJobForm form) {
        return CDI.current().select(DeletePrinterGroupJobCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Printer Group Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPrinterGroupUseType(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeForm form) {
        return CDI.current().select(CreatePrinterGroupUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeChoices(UserVisitPK userVisitPK, GetPrinterGroupUseTypeChoicesForm form) {
        return CDI.current().select(GetPrinterGroupUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseType(UserVisitPK userVisitPK, GetPrinterGroupUseTypeForm form) {
        return CDI.current().select(GetPrinterGroupUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypes(UserVisitPK userVisitPK, GetPrinterGroupUseTypesForm form) {
        return CDI.current().select(GetPrinterGroupUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPrinterGroupUseType(UserVisitPK userVisitPK, SetDefaultPrinterGroupUseTypeForm form) {
        return CDI.current().select(SetDefaultPrinterGroupUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupUseType(UserVisitPK userVisitPK, EditPrinterGroupUseTypeForm form) {
        return CDI.current().select(EditPrinterGroupUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupUseType(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeForm form) {
        return CDI.current().select(DeletePrinterGroupUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Printer Group Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeDescriptionForm form) {
        return CDI.current().select(CreatePrinterGroupUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionForm form) {
        return CDI.current().select(GetPrinterGroupUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeDescriptions(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionsForm form) {
        return CDI.current().select(GetPrinterGroupUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, EditPrinterGroupUseTypeDescriptionForm form) {
        return CDI.current().select(EditPrinterGroupUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupUseTypeDescription(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeDescriptionForm form) {
        return CDI.current().select(DeletePrinterGroupUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Printer Group Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyPrinterGroupUse(UserVisitPK userVisitPK, CreatePartyPrinterGroupUseForm form) {
        return CDI.current().select(CreatePartyPrinterGroupUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPrinterGroupUse(UserVisitPK userVisitPK, GetPartyPrinterGroupUseForm form) {
        return CDI.current().select(GetPartyPrinterGroupUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPrinterGroupUses(UserVisitPK userVisitPK, GetPartyPrinterGroupUsesForm form) {
        return CDI.current().select(GetPartyPrinterGroupUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyPrinterGroupUse(UserVisitPK userVisitPK, EditPartyPrinterGroupUseForm form) {
        return CDI.current().select(EditPartyPrinterGroupUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyPrinterGroupUse(UserVisitPK userVisitPK, DeletePartyPrinterGroupUseForm form) {
        return CDI.current().select(DeletePartyPrinterGroupUseCommand.class).get().run(userVisitPK, form);
    }

}
