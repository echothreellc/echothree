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

package com.echothree.control.user.associate.server;

import com.echothree.control.user.associate.common.AssociateRemote;
import com.echothree.control.user.associate.common.form.*;
import com.echothree.control.user.associate.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateAssociateProgramCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociateProgram(UserVisitPK userVisitPK, GetAssociateProgramForm form) {
        return CDI.current().select(GetAssociateProgramCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociatePrograms(UserVisitPK userVisitPK, GetAssociateProgramsForm form) {
        return CDI.current().select(GetAssociateProgramsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociateProgramChoices(UserVisitPK userVisitPK, GetAssociateProgramChoicesForm form) {
        return CDI.current().select(GetAssociateProgramChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultAssociateProgram(UserVisitPK userVisitPK, SetDefaultAssociateProgramForm form) {
        return CDI.current().select(SetDefaultAssociateProgramCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAssociateProgram(UserVisitPK userVisitPK, EditAssociateProgramForm form) {
        return CDI.current().select(EditAssociateProgramCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAssociateProgram(UserVisitPK userVisitPK, DeleteAssociateProgramForm form) {
        return CDI.current().select(DeleteAssociateProgramCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Associate Program Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociateProgramDescription(UserVisitPK userVisitPK, CreateAssociateProgramDescriptionForm form) {
        return CDI.current().select(CreateAssociateProgramDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociateProgramDescriptions(UserVisitPK userVisitPK, GetAssociateProgramDescriptionsForm form) {
        return CDI.current().select(GetAssociateProgramDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editAssociateProgramDescription(UserVisitPK userVisitPK, EditAssociateProgramDescriptionForm form) {
        return CDI.current().select(EditAssociateProgramDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAssociateProgramDescription(UserVisitPK userVisitPK, DeleteAssociateProgramDescriptionForm form) {
        return CDI.current().select(DeleteAssociateProgramDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Associates
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociate(UserVisitPK userVisitPK, CreateAssociateForm form) {
        return CDI.current().select(CreateAssociateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociate(UserVisitPK userVisitPK, GetAssociateForm form) {
        return CDI.current().select(GetAssociateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociates(UserVisitPK userVisitPK, GetAssociatesForm form) {
        return CDI.current().select(GetAssociatesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociateChoices(UserVisitPK userVisitPK, GetAssociateChoicesForm form) {
        return CDI.current().select(GetAssociateChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAssociate(UserVisitPK userVisitPK, DeleteAssociateForm form) {
        return CDI.current().select(DeleteAssociateCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Associate Contact Mechanisms
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createAssociatePartyContactMechanism(UserVisitPK userVisitPK, CreateAssociatePartyContactMechanismForm form) {
        return CDI.current().select(CreateAssociatePartyContactMechanismCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanism(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismForm form) {
        return CDI.current().select(GetAssociatePartyContactMechanismCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanisms(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismsForm form) {
        return CDI.current().select(GetAssociatePartyContactMechanismsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociatePartyContactMechanismChoices(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismChoicesForm form) {
        return CDI.current().select(GetAssociatePartyContactMechanismChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultAssociatePartyContactMechanism(UserVisitPK userVisitPK, SetDefaultAssociatePartyContactMechanismForm form) {
        return CDI.current().select(SetDefaultAssociatePartyContactMechanismCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAssociatePartyContactMechanism(UserVisitPK userVisitPK, DeleteAssociatePartyContactMechanismForm form) {
        return CDI.current().select(DeleteAssociatePartyContactMechanismCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Associate Referrals
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getAssociateReferral(UserVisitPK userVisitPK, GetAssociateReferralForm form) {
        return CDI.current().select(GetAssociateReferralCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getAssociateReferrals(UserVisitPK userVisitPK, GetAssociateReferralsForm form) {
        return CDI.current().select(GetAssociateReferralsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteAssociateReferral(UserVisitPK userVisitPK, DeleteAssociateReferralForm form) {
        return CDI.current().select(DeleteAssociateReferralCommand.class).get().run(userVisitPK, form);
    }
    
}
