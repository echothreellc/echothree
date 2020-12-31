// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.associate.server;

import com.echothree.control.user.associate.common.AssociateRemote;
import com.echothree.control.user.associate.common.form.*;
import com.echothree.control.user.associate.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class AssociateBean
        extends AssociateFormsImpl
        implements AssociateRemote, AssociateLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "AssociateBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Associate Programs
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociateProgram(UserVisitPK userVisitPK, CreateAssociateProgramForm form) {
        return new CreateAssociateProgramCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociateProgram(UserVisitPK userVisitPK, GetAssociateProgramForm form) {
        return new GetAssociateProgramCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociatePrograms(UserVisitPK userVisitPK, GetAssociateProgramsForm form) {
        return new GetAssociateProgramsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociateProgramChoices(UserVisitPK userVisitPK, GetAssociateProgramChoicesForm form) {
        return new GetAssociateProgramChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultAssociateProgram(UserVisitPK userVisitPK, SetDefaultAssociateProgramForm form) {
        return new SetDefaultAssociateProgramCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAssociateProgram(UserVisitPK userVisitPK, EditAssociateProgramForm form) {
        return new EditAssociateProgramCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAssociateProgram(UserVisitPK userVisitPK, DeleteAssociateProgramForm form) {
        return new DeleteAssociateProgramCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Associate Program Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociateProgramDescription(UserVisitPK userVisitPK, CreateAssociateProgramDescriptionForm form) {
        return new CreateAssociateProgramDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociateProgramDescriptions(UserVisitPK userVisitPK, GetAssociateProgramDescriptionsForm form) {
        return new GetAssociateProgramDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAssociateProgramDescription(UserVisitPK userVisitPK, EditAssociateProgramDescriptionForm form) {
        return new EditAssociateProgramDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAssociateProgramDescription(UserVisitPK userVisitPK, DeleteAssociateProgramDescriptionForm form) {
        return new DeleteAssociateProgramDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Associates
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociate(UserVisitPK userVisitPK, CreateAssociateForm form) {
        return new CreateAssociateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociate(UserVisitPK userVisitPK, GetAssociateForm form) {
        return new GetAssociateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociates(UserVisitPK userVisitPK, GetAssociatesForm form) {
        return new GetAssociatesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociateChoices(UserVisitPK userVisitPK, GetAssociateChoicesForm form) {
        return new GetAssociateChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAssociate(UserVisitPK userVisitPK, DeleteAssociateForm form) {
        return new DeleteAssociateCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Associate Contact Mechanisms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociatePartyContactMechanism(UserVisitPK userVisitPK, CreateAssociatePartyContactMechanismForm form) {
        return new CreateAssociatePartyContactMechanismCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanism(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismForm form) {
        return new GetAssociatePartyContactMechanismCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanisms(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismsForm form) {
        return new GetAssociatePartyContactMechanismsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanismChoices(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismChoicesForm form) {
        return new GetAssociatePartyContactMechanismChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultAssociatePartyContactMechanism(UserVisitPK userVisitPK, SetDefaultAssociatePartyContactMechanismForm form) {
        return new SetDefaultAssociatePartyContactMechanismCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAssociatePartyContactMechanism(UserVisitPK userVisitPK, DeleteAssociatePartyContactMechanismForm form) {
        return new DeleteAssociatePartyContactMechanismCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Associate Referrals
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getAssociateReferral(UserVisitPK userVisitPK, GetAssociateReferralForm form) {
        return new GetAssociateReferralCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAssociateReferrals(UserVisitPK userVisitPK, GetAssociateReferralsForm form) {
        return new GetAssociateReferralsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAssociateReferral(UserVisitPK userVisitPK, DeleteAssociateReferralForm form) {
        return new DeleteAssociateReferralCommand(userVisitPK, form).run();
    }
    
}
