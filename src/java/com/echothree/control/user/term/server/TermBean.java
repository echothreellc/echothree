// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
        return new CreateTermTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTermTypes(UserVisitPK userVisitPK, GetTermTypesForm form) {
        return new GetTermTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTermType(UserVisitPK userVisitPK, GetTermTypeForm form) {
        return new GetTermTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTermTypeChoices(UserVisitPK userVisitPK, GetTermTypeChoicesForm form) {
        return new GetTermTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Term Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermTypeDescription(UserVisitPK userVisitPK, CreateTermTypeDescriptionForm form) {
        return new CreateTermTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerm(UserVisitPK userVisitPK, CreateTermForm form) {
        return new CreateTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerms(UserVisitPK userVisitPK, GetTermsForm form) {
        return new GetTermsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerm(UserVisitPK userVisitPK, GetTermForm form) {
        return new GetTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTermChoices(UserVisitPK userVisitPK, GetTermChoicesForm form) {
        return new GetTermChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTerm(UserVisitPK userVisitPK, SetDefaultTermForm form) {
        return new SetDefaultTermCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTerm(UserVisitPK userVisitPK, DeleteTermForm form) {
        return new DeleteTermCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Term Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermDescription(UserVisitPK userVisitPK, CreateTermDescriptionForm form) {
        return new CreateTermDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTermDescriptions(UserVisitPK userVisitPK, GetTermDescriptionsForm form) {
        return new GetTermDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTermDescription(UserVisitPK userVisitPK, EditTermDescriptionForm form) {
        return new EditTermDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTermDescription(UserVisitPK userVisitPK, DeleteTermDescriptionForm form) {
        return new DeleteTermDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeCreditLimit(UserVisitPK userVisitPK, CreateCustomerTypeCreditLimitForm form) {
        return new CreateCustomerTypeCreditLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCustomerTypeCreditLimit(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form) {
        return new EditCustomerTypeCreditLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypeCreditLimits(UserVisitPK userVisitPK, GetCustomerTypeCreditLimitsForm form) {
        return new GetCustomerTypeCreditLimitsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCustomerTypeCreditLimit(UserVisitPK userVisitPK, DeleteCustomerTypeCreditLimitForm form) {
        return new DeleteCustomerTypeCreditLimitCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyCreditLimit(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form) {
        return new CreatePartyCreditLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyCreditLimit(UserVisitPK userVisitPK, EditPartyCreditLimitForm form) {
        return new EditPartyCreditLimitCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyCreditLimits(UserVisitPK userVisitPK, GetPartyCreditLimitsForm form) {
        return new GetPartyCreditLimitsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyCreditLimit(UserVisitPK userVisitPK, DeletePartyCreditLimitForm form) {
        return new DeletePartyCreditLimitCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTerm(UserVisitPK userVisitPK, EditPartyTermForm form) {
        return new EditPartyTermCommand(userVisitPK, form).run();
    }
    
}
