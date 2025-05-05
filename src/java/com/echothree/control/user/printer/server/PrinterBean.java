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
        return new CreatePrinterGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupChoices(UserVisitPK userVisitPK, GetPrinterGroupChoicesForm form) {
        return new GetPrinterGroupChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroup(UserVisitPK userVisitPK, GetPrinterGroupForm form) {
        return new GetPrinterGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroups(UserVisitPK userVisitPK, GetPrinterGroupsForm form) {
        return new GetPrinterGroupsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPrinterGroup(UserVisitPK userVisitPK, SetDefaultPrinterGroupForm form) {
        return new SetDefaultPrinterGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPrinterGroup(UserVisitPK userVisitPK, EditPrinterGroupForm form) {
        return new EditPrinterGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupStatusChoicesForm form) {
        return new GetPrinterGroupStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPrinterGroupStatus(UserVisitPK userVisitPK, SetPrinterGroupStatusForm form) {
        return new SetPrinterGroupStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterGroup(UserVisitPK userVisitPK, DeletePrinterGroupForm form) {
        return new DeletePrinterGroupCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterGroupDescription(UserVisitPK userVisitPK, CreatePrinterGroupDescriptionForm form) {
        return new CreatePrinterGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterGroupDescription(UserVisitPK userVisitPK, GetPrinterGroupDescriptionForm form) {
        return new GetPrinterGroupDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupDescriptions(UserVisitPK userVisitPK, GetPrinterGroupDescriptionsForm form) {
        return new GetPrinterGroupDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupDescription(UserVisitPK userVisitPK, EditPrinterGroupDescriptionForm form) {
        return new EditPrinterGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterGroupDescription(UserVisitPK userVisitPK, DeletePrinterGroupDescriptionForm form) {
        return new DeletePrinterGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinter(UserVisitPK userVisitPK, CreatePrinterForm form) {
        return new CreatePrinterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinter(UserVisitPK userVisitPK, GetPrinterForm form) {
        return new GetPrinterCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinters(UserVisitPK userVisitPK, GetPrintersForm form) {
        return new GetPrintersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinter(UserVisitPK userVisitPK, EditPrinterForm form) {
        return new EditPrinterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterStatusChoices(UserVisitPK userVisitPK, GetPrinterStatusChoicesForm form) {
        return new GetPrinterStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPrinterStatus(UserVisitPK userVisitPK, SetPrinterStatusForm form) {
        return new SetPrinterStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinter(UserVisitPK userVisitPK, DeletePrinterForm form) {
        return new DeletePrinterCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterDescription(UserVisitPK userVisitPK, CreatePrinterDescriptionForm form) {
        return new CreatePrinterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPrinterDescription(UserVisitPK userVisitPK, GetPrinterDescriptionForm form) {
        return new GetPrinterDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterDescriptions(UserVisitPK userVisitPK, GetPrinterDescriptionsForm form) {
        return new GetPrinterDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterDescription(UserVisitPK userVisitPK, EditPrinterDescriptionForm form) {
        return new EditPrinterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePrinterDescription(UserVisitPK userVisitPK, DeletePrinterDescriptionForm form) {
        return new DeletePrinterDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Printer Group Jobs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPrinterGroupJob(UserVisitPK userVisitPK, CreatePrinterGroupJobForm form) {
        return new CreatePrinterGroupJobCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJob(UserVisitPK userVisitPK, GetPrinterGroupJobForm form) {
        return new GetPrinterGroupJobCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJobs(UserVisitPK userVisitPK, GetPrinterGroupJobsForm form) {
        return new GetPrinterGroupJobsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupJobStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupJobStatusChoicesForm form) {
        return new GetPrinterGroupJobStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPrinterGroupJobStatus(UserVisitPK userVisitPK, SetPrinterGroupJobStatusForm form) {
        return new SetPrinterGroupJobStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupJob(UserVisitPK userVisitPK, DeletePrinterGroupJobForm form) {
        return new DeletePrinterGroupJobCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Printer Group Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPrinterGroupUseType(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeForm form) {
        return new CreatePrinterGroupUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeChoices(UserVisitPK userVisitPK, GetPrinterGroupUseTypeChoicesForm form) {
        return new GetPrinterGroupUseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseType(UserVisitPK userVisitPK, GetPrinterGroupUseTypeForm form) {
        return new GetPrinterGroupUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypes(UserVisitPK userVisitPK, GetPrinterGroupUseTypesForm form) {
        return new GetPrinterGroupUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPrinterGroupUseType(UserVisitPK userVisitPK, SetDefaultPrinterGroupUseTypeForm form) {
        return new SetDefaultPrinterGroupUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupUseType(UserVisitPK userVisitPK, EditPrinterGroupUseTypeForm form) {
        return new EditPrinterGroupUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupUseType(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeForm form) {
        return new DeletePrinterGroupUseTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Printer Group Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeDescriptionForm form) {
        return new CreatePrinterGroupUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionForm form) {
        return new GetPrinterGroupUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPrinterGroupUseTypeDescriptions(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionsForm form) {
        return new GetPrinterGroupUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, EditPrinterGroupUseTypeDescriptionForm form) {
        return new EditPrinterGroupUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePrinterGroupUseTypeDescription(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeDescriptionForm form) {
        return new DeletePrinterGroupUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Printer Group Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyPrinterGroupUse(UserVisitPK userVisitPK, CreatePartyPrinterGroupUseForm form) {
        return new CreatePartyPrinterGroupUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPrinterGroupUse(UserVisitPK userVisitPK, GetPartyPrinterGroupUseForm form) {
        return new GetPartyPrinterGroupUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPrinterGroupUses(UserVisitPK userVisitPK, GetPartyPrinterGroupUsesForm form) {
        return new GetPartyPrinterGroupUsesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyPrinterGroupUse(UserVisitPK userVisitPK, EditPartyPrinterGroupUseForm form) {
        return new EditPartyPrinterGroupUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyPrinterGroupUse(UserVisitPK userVisitPK, DeletePartyPrinterGroupUseForm form) {
        return new DeletePartyPrinterGroupUseCommand().run(userVisitPK, form);
    }

}
