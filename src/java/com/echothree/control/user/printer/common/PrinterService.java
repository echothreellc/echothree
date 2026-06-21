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
import com.echothree.control.user.printer.common.result.*;
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
    
    CommandResult<?> createPrinterGroup(UserVisitPK userVisitPK, CreatePrinterGroupForm form);
    
    CommandResult<GetPrinterGroupChoicesResult> getPrinterGroupChoices(UserVisitPK userVisitPK, GetPrinterGroupChoicesForm form);

    CommandResult<GetPrinterGroupResult> getPrinterGroup(UserVisitPK userVisitPK, GetPrinterGroupForm form);

    CommandResult<GetPrinterGroupsResult> getPrinterGroups(UserVisitPK userVisitPK, GetPrinterGroupsForm form);
    
    CommandResult<?> setDefaultPrinterGroup(UserVisitPK userVisitPK, SetDefaultPrinterGroupForm form);
    
    CommandResult<EditPrinterGroupResult> editPrinterGroup(UserVisitPK userVisitPK, EditPrinterGroupForm form);
    
    CommandResult<GetPrinterGroupStatusChoicesResult> getPrinterGroupStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupStatusChoicesForm form);
    
    CommandResult<?> setPrinterGroupStatus(UserVisitPK userVisitPK, SetPrinterGroupStatusForm form);
    
    CommandResult<?> deletePrinterGroup(UserVisitPK userVisitPK, DeletePrinterGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPrinterGroupDescription(UserVisitPK userVisitPK, CreatePrinterGroupDescriptionForm form);
    
    CommandResult<GetPrinterGroupDescriptionResult> getPrinterGroupDescription(UserVisitPK userVisitPK, GetPrinterGroupDescriptionForm form);

    CommandResult<GetPrinterGroupDescriptionsResult> getPrinterGroupDescriptions(UserVisitPK userVisitPK, GetPrinterGroupDescriptionsForm form);

    CommandResult<EditPrinterGroupDescriptionResult> editPrinterGroupDescription(UserVisitPK userVisitPK, EditPrinterGroupDescriptionForm form);
    
    CommandResult<?> deletePrinterGroupDescription(UserVisitPK userVisitPK, DeletePrinterGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Printers
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPrinter(UserVisitPK userVisitPK, CreatePrinterForm form);

    CommandResult<GetPrinterResult> getPrinter(UserVisitPK userVisitPK, GetPrinterForm form);

    CommandResult<GetPrintersResult> getPrinters(UserVisitPK userVisitPK, GetPrintersForm form);
    
    CommandResult<EditPrinterResult> editPrinter(UserVisitPK userVisitPK, EditPrinterForm form);
    
    CommandResult<GetPrinterStatusChoicesResult> getPrinterStatusChoices(UserVisitPK userVisitPK, GetPrinterStatusChoicesForm form);
    
    CommandResult<?> setPrinterStatus(UserVisitPK userVisitPK, SetPrinterStatusForm form);
    
    CommandResult<?> deletePrinter(UserVisitPK userVisitPK, DeletePrinterForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPrinterDescription(UserVisitPK userVisitPK, CreatePrinterDescriptionForm form);
    
    CommandResult<GetPrinterDescriptionResult> getPrinterDescription(UserVisitPK userVisitPK, GetPrinterDescriptionForm form);

    CommandResult<GetPrinterDescriptionsResult> getPrinterDescriptions(UserVisitPK userVisitPK, GetPrinterDescriptionsForm form);

    CommandResult<EditPrinterDescriptionResult> editPrinterDescription(UserVisitPK userVisitPK, EditPrinterDescriptionForm form);
    
    CommandResult<?> deletePrinterDescription(UserVisitPK userVisitPK, DeletePrinterDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Jobs
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPrinterGroupJob(UserVisitPK userVisitPK, CreatePrinterGroupJobForm form);

    CommandResult<GetPrinterGroupJobResult> getPrinterGroupJob(UserVisitPK userVisitPK, GetPrinterGroupJobForm form);

    CommandResult<GetPrinterGroupJobsResult> getPrinterGroupJobs(UserVisitPK userVisitPK, GetPrinterGroupJobsForm form);

    CommandResult<GetPrinterGroupJobStatusChoicesResult> getPrinterGroupJobStatusChoices(UserVisitPK userVisitPK, GetPrinterGroupJobStatusChoicesForm form);

    CommandResult<?> setPrinterGroupJobStatus(UserVisitPK userVisitPK, SetPrinterGroupJobStatusForm form);

    CommandResult<?> deletePrinterGroupJob(UserVisitPK userVisitPK, DeletePrinterGroupJobForm form);
    
    // -------------------------------------------------------------------------
    //   Printer Group Use Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPrinterGroupUseType(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeForm form);

    CommandResult<GetPrinterGroupUseTypeChoicesResult> getPrinterGroupUseTypeChoices(UserVisitPK userVisitPK, GetPrinterGroupUseTypeChoicesForm form);

    CommandResult<GetPrinterGroupUseTypeResult> getPrinterGroupUseType(UserVisitPK userVisitPK, GetPrinterGroupUseTypeForm form);

    CommandResult<GetPrinterGroupUseTypesResult> getPrinterGroupUseTypes(UserVisitPK userVisitPK, GetPrinterGroupUseTypesForm form);

    CommandResult<?> setDefaultPrinterGroupUseType(UserVisitPK userVisitPK, SetDefaultPrinterGroupUseTypeForm form);

    CommandResult<EditPrinterGroupUseTypeResult> editPrinterGroupUseType(UserVisitPK userVisitPK, EditPrinterGroupUseTypeForm form);

    CommandResult<?> deletePrinterGroupUseType(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Printer Group Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, CreatePrinterGroupUseTypeDescriptionForm form);

    CommandResult<GetPrinterGroupUseTypeDescriptionResult> getPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionForm form);

    CommandResult<GetPrinterGroupUseTypeDescriptionsResult> getPrinterGroupUseTypeDescriptions(UserVisitPK userVisitPK, GetPrinterGroupUseTypeDescriptionsForm form);

    CommandResult<EditPrinterGroupUseTypeDescriptionResult> editPrinterGroupUseTypeDescription(UserVisitPK userVisitPK, EditPrinterGroupUseTypeDescriptionForm form);

    CommandResult<?> deletePrinterGroupUseTypeDescription(UserVisitPK userVisitPK, DeletePrinterGroupUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Party Printer Group Uses
    // -------------------------------------------------------------------------

    CommandResult<?> createPartyPrinterGroupUse(UserVisitPK userVisitPK, CreatePartyPrinterGroupUseForm form);

    CommandResult<GetPartyPrinterGroupUseResult> getPartyPrinterGroupUse(UserVisitPK userVisitPK, GetPartyPrinterGroupUseForm form);

    CommandResult<GetPartyPrinterGroupUsesResult> getPartyPrinterGroupUses(UserVisitPK userVisitPK, GetPartyPrinterGroupUsesForm form);

    CommandResult<EditPartyPrinterGroupUseResult> editPartyPrinterGroupUse(UserVisitPK userVisitPK, EditPartyPrinterGroupUseForm form);

    CommandResult<?> deletePartyPrinterGroupUse(UserVisitPK userVisitPK, DeletePartyPrinterGroupUseForm form);

}
