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

package com.echothree.control.user.term.common;

import com.echothree.control.user.term.common.form.*;
import com.echothree.control.user.term.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface TermService
        extends TermForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Term Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTermType(UserVisitPK userVisitPK, CreateTermTypeForm form);
    
    CommandResult<GetTermTypesResult> getTermTypes(UserVisitPK userVisitPK, GetTermTypesForm form);
    
    CommandResult<GetTermTypeResult> getTermType(UserVisitPK userVisitPK, GetTermTypeForm form);
    
    CommandResult<GetTermTypeChoicesResult> getTermTypeChoices(UserVisitPK userVisitPK, GetTermTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Term Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTermTypeDescription(UserVisitPK userVisitPK, CreateTermTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Terms
    // -------------------------------------------------------------------------
    
    CommandResult<CreateTermResult> createTerm(UserVisitPK userVisitPK, CreateTermForm form);
    
    CommandResult<GetTermsResult> getTerms(UserVisitPK userVisitPK, GetTermsForm form);
    
    CommandResult<GetTermResult> getTerm(UserVisitPK userVisitPK, GetTermForm form);
    
    CommandResult<GetTermChoicesResult> getTermChoices(UserVisitPK userVisitPK, GetTermChoicesForm form);
    
    CommandResult<VoidResult> setDefaultTerm(UserVisitPK userVisitPK, SetDefaultTermForm form);
    
    CommandResult<VoidResult> deleteTerm(UserVisitPK userVisitPK, DeleteTermForm form);
    
    // -------------------------------------------------------------------------
    //   Term Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createTermDescription(UserVisitPK userVisitPK, CreateTermDescriptionForm form);
    
    CommandResult<GetTermDescriptionsResult> getTermDescriptions(UserVisitPK userVisitPK, GetTermDescriptionsForm form);
    
    CommandResult<EditTermDescriptionResult> editTermDescription(UserVisitPK userVisitPK, EditTermDescriptionForm form);
    
    CommandResult<VoidResult> deleteTermDescription(UserVisitPK userVisitPK, DeleteTermDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCustomerTypeCreditLimit(UserVisitPK userVisitPK, CreateCustomerTypeCreditLimitForm form);
    
    CommandResult<EditCustomerTypeCreditLimitResult> editCustomerTypeCreditLimit(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form);
    
    CommandResult<GetCustomerTypeCreditLimitsResult> getCustomerTypeCreditLimits(UserVisitPK userVisitPK, GetCustomerTypeCreditLimitsForm form);
    
    CommandResult<VoidResult> deleteCustomerTypeCreditLimit(UserVisitPK userVisitPK, DeleteCustomerTypeCreditLimitForm form);
    
    // -------------------------------------------------------------------------
    //   Party Credit Limits
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyCreditLimit(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form);
    
    CommandResult<EditPartyCreditLimitResult> editPartyCreditLimit(UserVisitPK userVisitPK, EditPartyCreditLimitForm form);
    
    CommandResult<GetPartyCreditLimitsResult> getPartyCreditLimits(UserVisitPK userVisitPK, GetPartyCreditLimitsForm form);
    
    CommandResult<VoidResult> deletePartyCreditLimit(UserVisitPK userVisitPK, DeletePartyCreditLimitForm form);
    
    // -------------------------------------------------------------------------
    //   Party Terms
    // -------------------------------------------------------------------------
    
    CommandResult<EditPartyTermResult> editPartyTerm(UserVisitPK userVisitPK, EditPartyTermForm form);
    
}
