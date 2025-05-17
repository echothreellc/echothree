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

package com.echothree.control.user.term.server;

import com.echothree.control.user.term.common.TermRemote;
import com.echothree.control.user.term.common.form.*;
import com.echothree.control.user.term.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class TermBean
        extends TermFormsImpl
        implements TermRemote, TermLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "TermBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Term Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermType(UserVisitPK userVisitPK, CreateTermTypeForm form) {
        return new CreateTermTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermTypes(UserVisitPK userVisitPK, GetTermTypesForm form) {
        return new GetTermTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermType(UserVisitPK userVisitPK, GetTermTypeForm form) {
        return new GetTermTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermTypeChoices(UserVisitPK userVisitPK, GetTermTypeChoicesForm form) {
        return new GetTermTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Term Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermTypeDescription(UserVisitPK userVisitPK, CreateTermTypeDescriptionForm form) {
        return new CreateTermTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerm(UserVisitPK userVisitPK, CreateTermForm form) {
        return new CreateTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerms(UserVisitPK userVisitPK, GetTermsForm form) {
        return new GetTermsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerm(UserVisitPK userVisitPK, GetTermForm form) {
        return new GetTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermChoices(UserVisitPK userVisitPK, GetTermChoicesForm form) {
        return new GetTermChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTerm(UserVisitPK userVisitPK, SetDefaultTermForm form) {
        return new SetDefaultTermCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTerm(UserVisitPK userVisitPK, DeleteTermForm form) {
        return new DeleteTermCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Term Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermDescription(UserVisitPK userVisitPK, CreateTermDescriptionForm form) {
        return new CreateTermDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermDescriptions(UserVisitPK userVisitPK, GetTermDescriptionsForm form) {
        return new GetTermDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTermDescription(UserVisitPK userVisitPK, EditTermDescriptionForm form) {
        return new EditTermDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTermDescription(UserVisitPK userVisitPK, DeleteTermDescriptionForm form) {
        return new DeleteTermDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeCreditLimit(UserVisitPK userVisitPK, CreateCustomerTypeCreditLimitForm form) {
        return new CreateCustomerTypeCreditLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeCreditLimit(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form) {
        return new EditCustomerTypeCreditLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeCreditLimits(UserVisitPK userVisitPK, GetCustomerTypeCreditLimitsForm form) {
        return new GetCustomerTypeCreditLimitsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeCreditLimit(UserVisitPK userVisitPK, DeleteCustomerTypeCreditLimitForm form) {
        return new DeleteCustomerTypeCreditLimitCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyCreditLimit(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form) {
        return new CreatePartyCreditLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyCreditLimit(UserVisitPK userVisitPK, EditPartyCreditLimitForm form) {
        return new EditPartyCreditLimitCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyCreditLimits(UserVisitPK userVisitPK, GetPartyCreditLimitsForm form) {
        return new GetPartyCreditLimitsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyCreditLimit(UserVisitPK userVisitPK, DeletePartyCreditLimitForm form) {
        return new DeletePartyCreditLimitCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTerm(UserVisitPK userVisitPK, EditPartyTermForm form) {
        return new EditPartyTermCommand().run(userVisitPK, form);
    }
    
}
