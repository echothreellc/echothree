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

package com.echothree.control.user.invoice.common;

import com.echothree.control.user.invoice.common.form.*;
import com.echothree.control.user.invoice.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface InvoiceService
        extends InvoiceForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceLineUseType(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceLineUseTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceRoleType(UserVisitPK userVisitPK, CreateInvoiceRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceRoleTypeDescription(UserVisitPK userVisitPK, CreateInvoiceRoleTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceType(UserVisitPK userVisitPK, CreateInvoiceTypeForm form);
    
    CommandResult<GetInvoiceTypeChoicesResult> getInvoiceTypeChoices(UserVisitPK userVisitPK, GetInvoiceTypeChoicesForm form);
    
    CommandResult<GetInvoiceTypeResult> getInvoiceType(UserVisitPK userVisitPK, GetInvoiceTypeForm form);
    
    CommandResult<GetInvoiceTypesResult> getInvoiceTypes(UserVisitPK userVisitPK, GetInvoiceTypesForm form);
    
    CommandResult<VoidResult> setDefaultInvoiceType(UserVisitPK userVisitPK, SetDefaultInvoiceTypeForm form);
    
    CommandResult<EditInvoiceTypeResult> editInvoiceType(UserVisitPK userVisitPK, EditInvoiceTypeForm form);
    
    CommandResult<VoidResult> deleteInvoiceType(UserVisitPK userVisitPK, DeleteInvoiceTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTypeDescriptionForm form);
    
    CommandResult<GetInvoiceTypeDescriptionsResult> getInvoiceTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTypeDescriptionsForm form);
    
    CommandResult<EditInvoiceTypeDescriptionResult> editInvoiceTypeDescription(UserVisitPK userVisitPK, EditInvoiceTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteInvoiceTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createInvoiceTimeType(UserVisitPK userVisitPK, CreateInvoiceTimeTypeForm form);

    CommandResult<GetInvoiceTimeTypeChoicesResult> getInvoiceTimeTypeChoices(UserVisitPK userVisitPK, GetInvoiceTimeTypeChoicesForm form);

    CommandResult<GetInvoiceTimeTypeResult> getInvoiceTimeType(UserVisitPK userVisitPK, GetInvoiceTimeTypeForm form);

    CommandResult<GetInvoiceTimeTypesResult> getInvoiceTimeTypes(UserVisitPK userVisitPK, GetInvoiceTimeTypesForm form);

    CommandResult<VoidResult> setDefaultInvoiceTimeType(UserVisitPK userVisitPK, SetDefaultInvoiceTimeTypeForm form);

    CommandResult<EditInvoiceTimeTypeResult> editInvoiceTimeType(UserVisitPK userVisitPK, EditInvoiceTimeTypeForm form);

    CommandResult<VoidResult> deleteInvoiceTimeType(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createInvoiceTimeTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTimeTypeDescriptionForm form);

    CommandResult<GetInvoiceTimeTypeDescriptionResult> getInvoiceTimeTypeDescription(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionForm form);

    CommandResult<GetInvoiceTimeTypeDescriptionsResult> getInvoiceTimeTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionsForm form);

    CommandResult<EditInvoiceTimeTypeDescriptionResult> editInvoiceTimeTypeDescription(UserVisitPK userVisitPK, EditInvoiceTimeTypeDescriptionForm form);

    CommandResult<VoidResult> deleteInvoiceTimeTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceLineType(UserVisitPK userVisitPK, CreateInvoiceLineTypeForm form);
    
    CommandResult<GetInvoiceLineTypeChoicesResult> getInvoiceLineTypeChoices(UserVisitPK userVisitPK, GetInvoiceLineTypeChoicesForm form);
    
    CommandResult<GetInvoiceLineTypeResult> getInvoiceLineType(UserVisitPK userVisitPK, GetInvoiceLineTypeForm form);
    
    CommandResult<GetInvoiceLineTypesResult> getInvoiceLineTypes(UserVisitPK userVisitPK, GetInvoiceLineTypesForm form);
    
    CommandResult<VoidResult> setDefaultInvoiceLineType(UserVisitPK userVisitPK, SetDefaultInvoiceLineTypeForm form);
    
    CommandResult<EditInvoiceLineTypeResult> editInvoiceLineType(UserVisitPK userVisitPK, EditInvoiceLineTypeForm form);
    
    CommandResult<VoidResult> deleteInvoiceLineType(UserVisitPK userVisitPK, DeleteInvoiceLineTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createInvoiceLineTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineTypeDescriptionForm form);
    
    CommandResult<GetInvoiceLineTypeDescriptionsResult> getInvoiceLineTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceLineTypeDescriptionsForm form);
    
    CommandResult<EditInvoiceLineTypeDescriptionResult> editInvoiceLineTypeDescription(UserVisitPK userVisitPK, EditInvoiceLineTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteInvoiceLineTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceLineTypeDescriptionForm form);
    
}
