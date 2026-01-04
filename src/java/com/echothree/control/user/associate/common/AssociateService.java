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

package com.echothree.control.user.associate.common;

import com.echothree.control.user.associate.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface AssociateService
        extends AssociateForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Associate Programs
    // -------------------------------------------------------------------------
    
    CommandResult createAssociateProgram(UserVisitPK userVisitPK, CreateAssociateProgramForm form);
    
    CommandResult getAssociateProgram(UserVisitPK userVisitPK, GetAssociateProgramForm form);
    
    CommandResult getAssociatePrograms(UserVisitPK userVisitPK, GetAssociateProgramsForm form);
    
    CommandResult getAssociateProgramChoices(UserVisitPK userVisitPK, GetAssociateProgramChoicesForm form);
    
    CommandResult setDefaultAssociateProgram(UserVisitPK userVisitPK, SetDefaultAssociateProgramForm form);
    
    CommandResult editAssociateProgram(UserVisitPK userVisitPK, EditAssociateProgramForm form);
    
    CommandResult deleteAssociateProgram(UserVisitPK userVisitPK, DeleteAssociateProgramForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Program Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createAssociateProgramDescription(UserVisitPK userVisitPK, CreateAssociateProgramDescriptionForm form);
    
    CommandResult getAssociateProgramDescriptions(UserVisitPK userVisitPK, GetAssociateProgramDescriptionsForm form);
    
    CommandResult editAssociateProgramDescription(UserVisitPK userVisitPK, EditAssociateProgramDescriptionForm form);
    
    CommandResult deleteAssociateProgramDescription(UserVisitPK userVisitPK, DeleteAssociateProgramDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Associates
    // -------------------------------------------------------------------------
    
    CommandResult createAssociate(UserVisitPK userVisitPK, CreateAssociateForm form);
    
    CommandResult getAssociate(UserVisitPK userVisitPK, GetAssociateForm form);
    
    CommandResult getAssociates(UserVisitPK userVisitPK, GetAssociatesForm form);
    
    CommandResult getAssociateChoices(UserVisitPK userVisitPK, GetAssociateChoicesForm form);
    
    CommandResult deleteAssociate(UserVisitPK userVisitPK, DeleteAssociateForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Contact Mechanisms
    // -------------------------------------------------------------------------
    
    CommandResult createAssociatePartyContactMechanism(UserVisitPK userVisitPK, CreateAssociatePartyContactMechanismForm form);
    
    CommandResult getAssociatePartyContactMechanism(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismForm form);
    
    CommandResult getAssociatePartyContactMechanisms(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismsForm form);
    
    CommandResult getAssociatePartyContactMechanismChoices(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismChoicesForm form);
    
    CommandResult setDefaultAssociatePartyContactMechanism(UserVisitPK userVisitPK, SetDefaultAssociatePartyContactMechanismForm form);
    
    CommandResult deleteAssociatePartyContactMechanism(UserVisitPK userVisitPK, DeleteAssociatePartyContactMechanismForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Referrals
    // -------------------------------------------------------------------------
    
    CommandResult getAssociateReferral(UserVisitPK userVisitPK, GetAssociateReferralForm form);
    
    CommandResult getAssociateReferrals(UserVisitPK userVisitPK, GetAssociateReferralsForm form);
    
    CommandResult deleteAssociateReferral(UserVisitPK userVisitPK, DeleteAssociateReferralForm form);
    
}
