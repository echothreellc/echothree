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

package com.echothree.control.user.printer.common;

import com.echothree.control.user.printer.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface PrinterService
        extends PrinterForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Printer Groups
    // -------------------------------------------------------------------------
    
    CommandResult createPrinterGroup(UserVisitPK userVisitPK, CreatePrinterGroupForm form);
    
    CommandResult getPrinterGroupChoices(UserVisitPK userVisitPK, GetPrinterGroupChoicesForm form);

    CommandResult getPrinterGroup(UserVisitPK userVisitPK, GetPrinterGroupForm form);

    CommandResult getPrinterGroups(UserVisitPK userVisitPK, GetPrinterGroupsForm form);
    
    CommandResult setDefaultPrinterGroup(UserVisitPK userVisitPK, SetDefaultPrinterGroupForm form);
    
    CommandResult editPrinterGroup(UserVisitPK userVisitPK, EditPrinterGroupForm form);
    
    CommandResult getPrinterGroupStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupStatusChoicesForm form);
    
    CommandResult setPrinterGroupStatus(UserVisitPK userVisitPK, SetPrinterGroupStatusForm form);
    
    CommandResult deletePrinterGroup(UserVisitPK userVisitPK, DeletePrinterGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPrinterGroupDescription(UserVisitPK userVisitPK, CreatePrinterGroupDescriptionForm form);
    
    CommandResult getPrinterGroupDescription(UserVisitPK userVisitPK, GetPrinterGroupDescriptionForm form);

    CommandResult getPrinterGroupDescriptions(UserVisitPK userVisitPK, GetPrinterGroupDescriptionsForm form);

    CommandResult editPrinterGroupDescription(UserVisitPK userVisitPK, EditPrinterGroupDescriptionForm form);
    
    CommandResult deletePrinterGroupDescription(UserVisitPK userVisitPK, DeletePrinterGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Printers
    // -------------------------------------------------------------------------
    
    CommandResult createPrinter(UserVisitPK userVisitPK, CreatePrinterForm form);

    CommandResult getPrinter(UserVisitPK userVisitPK, GetPrinterForm form);

    CommandResult getPrinters(UserVisitPK userVisitPK, GetPrintersForm form);
    
    CommandResult editPrinter(UserVisitPK userVisitPK, EditPrinterForm form);
    
    CommandResult getPrinterStatusChoices(UserVisitPK userVisitPK, GetPrinterStatusChoicesForm form);
    
    CommandResult setPrinterStatus(UserVisitPK userVisitPK, SetPrinterStatusForm form);
    
    CommandResult deletePrinter(UserVisitPK userVisitPK, DeletePrinterForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPrinterDescription(UserVisitPK userVisitPK, CreatePrinterDescriptionForm form);
    
    CommandResult getPrinterDescription(UserVisitPK userVisitPK, GetPrinterDescriptionForm form);

    CommandResult getPrinterDescriptions(UserVisitPK userVisitPK, GetPrinterDescriptionsForm form);

    CommandResult editPrinterDescription(UserVisitPK userVisitPK, EditPrinterDescriptionForm form);
    
    CommandResult deletePrinterDescription(UserVisitPK userVisitPK, DeletePrinterDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Jobs
    // -------------------------------------------------------------------------
    
    CommandResult createPrinterGroupJob(UserVisitPK userVisitPK, CreatePrinterGroupJobForm form);

    CommandResult getPrinterGroupJob(UserVisitPK userVisitPK, GetPrinterGroupJobForm form);

    CommandResult getPrinterGroupJobs(UserVisitPK userVisitPK, GetPrinterGroupJobsForm form);

    CommandResult getPrinterGroupJobStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupJobStatusChoicesForm form);

    CommandResult setPrinterGroupJobStatus(UserVisitPK userVisitPK, SetPrinterGroupJobStatusForm form);

    CommandResult deletePrinterGroupJob(UserVisitPK userVisitPK, DeletePrinterGroupJobForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Use Types
    // -------------------------------------------------------------------------

    CommandResult createPrinterGroupUseType(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeForm form);

    CommandResult getPrinterGroupUseTypeChoices(UserVisitPK userVisitPK, GetPrinterGroupUseTypeChoicesForm form);

    CommandResult getPrinterGroupUseType(UserVisitPK userVisitPK, GetPrinterGroupUseTypeForm form);

    CommandResult getPrinterGroupUseTypes(UserVisitPK userVisitPK, GetPrinterGroupUseTypesForm form);

    CommandResult setDefaultPrinterGroupUseType(UserVisitPK userVisitPK, SetDefaultPrinterGroupUseTypeForm form);

    CommandResult editPrinterGroupUseType(UserVisitPK userVisitPK, EditPrinterGroupUseTypeForm form);

    CommandResult deletePrinterGroupUseType(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Printer Group Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeDescriptionForm form);

    CommandResult getPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionForm form);

    CommandResult getPrinterGroupUseTypeDescriptions(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionsForm form);

    CommandResult editPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, EditPrinterGroupUseTypeDescriptionForm form);

    CommandResult deletePrinterGroupUseTypeDescription(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Party Printer Group Uses
    // -------------------------------------------------------------------------

    CommandResult createPartyPrinterGroupUse(UserVisitPK userVisitPK, CreatePartyPrinterGroupUseForm form);

    CommandResult getPartyPrinterGroupUse(UserVisitPK userVisitPK, GetPartyPrinterGroupUseForm form);

    CommandResult getPartyPrinterGroupUses(UserVisitPK userVisitPK, GetPartyPrinterGroupUsesForm form);

    CommandResult editPartyPrinterGroupUse(UserVisitPK userVisitPK, EditPartyPrinterGroupUseForm form);

    CommandResult deletePartyPrinterGroupUse(UserVisitPK userVisitPK, DeletePartyPrinterGroupUseForm form);

}
