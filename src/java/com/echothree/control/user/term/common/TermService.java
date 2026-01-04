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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TermService
        extends TermForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Term Types
    // -------------------------------------------------------------------------
    
    CommandResult createTermType(UserVisitPK userVisitPK, CreateTermTypeForm form);
    
    CommandResult getTermTypes(UserVisitPK userVisitPK, GetTermTypesForm form);
    
    CommandResult getTermType(UserVisitPK userVisitPK, GetTermTypeForm form);
    
    CommandResult getTermTypeChoices(UserVisitPK userVisitPK, GetTermTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Term Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTermTypeDescription(UserVisitPK userVisitPK, CreateTermTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Terms
    // -------------------------------------------------------------------------
    
    CommandResult createTerm(UserVisitPK userVisitPK, CreateTermForm form);
    
    CommandResult getTerms(UserVisitPK userVisitPK, GetTermsForm form);
    
    CommandResult getTerm(UserVisitPK userVisitPK, GetTermForm form);
    
    CommandResult getTermChoices(UserVisitPK userVisitPK, GetTermChoicesForm form);
    
    CommandResult setDefaultTerm(UserVisitPK userVisitPK, SetDefaultTermForm form);
    
    CommandResult deleteTerm(UserVisitPK userVisitPK, DeleteTermForm form);
    
    // -------------------------------------------------------------------------
    //   Term Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTermDescription(UserVisitPK userVisitPK, CreateTermDescriptionForm form);
    
    CommandResult getTermDescriptions(UserVisitPK userVisitPK, GetTermDescriptionsForm form);
    
    CommandResult editTermDescription(UserVisitPK userVisitPK, EditTermDescriptionForm form);
    
    CommandResult deleteTermDescription(UserVisitPK userVisitPK, DeleteTermDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // -------------------------------------------------------------------------
    
    CommandResult createCustomerTypeCreditLimit(UserVisitPK userVisitPK, CreateCustomerTypeCreditLimitForm form);
    
    CommandResult editCustomerTypeCreditLimit(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form);
    
    CommandResult getCustomerTypeCreditLimits(UserVisitPK userVisitPK, GetCustomerTypeCreditLimitsForm form);
    
    CommandResult deleteCustomerTypeCreditLimit(UserVisitPK userVisitPK, DeleteCustomerTypeCreditLimitForm form);
    
    // -------------------------------------------------------------------------
    //   Party Credit Limits
    // -------------------------------------------------------------------------
    
    CommandResult createPartyCreditLimit(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form);
    
    CommandResult editPartyCreditLimit(UserVisitPK userVisitPK, EditPartyCreditLimitForm form);
    
    CommandResult getPartyCreditLimits(UserVisitPK userVisitPK, GetPartyCreditLimitsForm form);
    
    CommandResult deletePartyCreditLimit(UserVisitPK userVisitPK, DeletePartyCreditLimitForm form);
    
    // -------------------------------------------------------------------------
    //   Party Terms
    // -------------------------------------------------------------------------
    
    CommandResult editPartyTerm(UserVisitPK userVisitPK, EditPartyTermForm form);
    
}
