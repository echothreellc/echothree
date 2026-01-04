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

package com.echothree.control.user.term.server;

import com.echothree.control.user.term.common.TermRemote;
import com.echothree.control.user.term.common.form.*;
import com.echothree.control.user.term.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateTermTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermTypes(UserVisitPK userVisitPK, GetTermTypesForm form) {
        return CDI.current().select(GetTermTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermType(UserVisitPK userVisitPK, GetTermTypeForm form) {
        return CDI.current().select(GetTermTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermTypeChoices(UserVisitPK userVisitPK, GetTermTypeChoicesForm form) {
        return CDI.current().select(GetTermTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Term Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermTypeDescription(UserVisitPK userVisitPK, CreateTermTypeDescriptionForm form) {
        return CDI.current().select(CreateTermTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerm(UserVisitPK userVisitPK, CreateTermForm form) {
        return CDI.current().select(CreateTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerms(UserVisitPK userVisitPK, GetTermsForm form) {
        return CDI.current().select(GetTermsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerm(UserVisitPK userVisitPK, GetTermForm form) {
        return CDI.current().select(GetTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermChoices(UserVisitPK userVisitPK, GetTermChoicesForm form) {
        return CDI.current().select(GetTermChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTerm(UserVisitPK userVisitPK, SetDefaultTermForm form) {
        return CDI.current().select(SetDefaultTermCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTerm(UserVisitPK userVisitPK, DeleteTermForm form) {
        return CDI.current().select(DeleteTermCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Term Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTermDescription(UserVisitPK userVisitPK, CreateTermDescriptionForm form) {
        return CDI.current().select(CreateTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTermDescriptions(UserVisitPK userVisitPK, GetTermDescriptionsForm form) {
        return CDI.current().select(GetTermDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTermDescription(UserVisitPK userVisitPK, EditTermDescriptionForm form) {
        return CDI.current().select(EditTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTermDescription(UserVisitPK userVisitPK, DeleteTermDescriptionForm form) {
        return CDI.current().select(DeleteTermDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeCreditLimit(UserVisitPK userVisitPK, CreateCustomerTypeCreditLimitForm form) {
        return CDI.current().select(CreateCustomerTypeCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeCreditLimit(UserVisitPK userVisitPK, EditCustomerTypeCreditLimitForm form) {
        return CDI.current().select(EditCustomerTypeCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeCreditLimits(UserVisitPK userVisitPK, GetCustomerTypeCreditLimitsForm form) {
        return CDI.current().select(GetCustomerTypeCreditLimitsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeCreditLimit(UserVisitPK userVisitPK, DeleteCustomerTypeCreditLimitForm form) {
        return CDI.current().select(DeleteCustomerTypeCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Credit Limits
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyCreditLimit(UserVisitPK userVisitPK, CreatePartyCreditLimitForm form) {
        return CDI.current().select(CreatePartyCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyCreditLimit(UserVisitPK userVisitPK, EditPartyCreditLimitForm form) {
        return CDI.current().select(EditPartyCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyCreditLimits(UserVisitPK userVisitPK, GetPartyCreditLimitsForm form) {
        return CDI.current().select(GetPartyCreditLimitsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyCreditLimit(UserVisitPK userVisitPK, DeletePartyCreditLimitForm form) {
        return CDI.current().select(DeletePartyCreditLimitCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Terms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult editPartyTerm(UserVisitPK userVisitPK, EditPartyTermForm form) {
        return CDI.current().select(EditPartyTermCommand.class).get().run(userVisitPK, form);
    }
    
}
