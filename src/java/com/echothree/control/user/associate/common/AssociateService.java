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
import com.echothree.control.user.associate.common.result.*;
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
    
    CommandResult<?> createAssociateProgram(UserVisitPK userVisitPK, CreateAssociateProgramForm form);
    
    CommandResult<GetAssociateProgramResult> getAssociateProgram(UserVisitPK userVisitPK, GetAssociateProgramForm form);
    
    CommandResult<GetAssociateProgramsResult> getAssociatePrograms(UserVisitPK userVisitPK, GetAssociateProgramsForm form);
    
    CommandResult<GetAssociateProgramChoicesResult> getAssociateProgramChoices(UserVisitPK userVisitPK, GetAssociateProgramChoicesForm form);
    
    CommandResult<?> setDefaultAssociateProgram(UserVisitPK userVisitPK, SetDefaultAssociateProgramForm form);
    
    CommandResult<EditAssociateProgramResult> editAssociateProgram(UserVisitPK userVisitPK, EditAssociateProgramForm form);
    
    CommandResult<?> deleteAssociateProgram(UserVisitPK userVisitPK, DeleteAssociateProgramForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Program Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createAssociateProgramDescription(UserVisitPK userVisitPK, CreateAssociateProgramDescriptionForm form);
    
    CommandResult<GetAssociateProgramDescriptionsResult> getAssociateProgramDescriptions(UserVisitPK userVisitPK, GetAssociateProgramDescriptionsForm form);
    
    CommandResult<EditAssociateProgramDescriptionResult> editAssociateProgramDescription(UserVisitPK userVisitPK, EditAssociateProgramDescriptionForm form);
    
    CommandResult<?> deleteAssociateProgramDescription(UserVisitPK userVisitPK, DeleteAssociateProgramDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Associates
    // -------------------------------------------------------------------------
    
    CommandResult<?> createAssociate(UserVisitPK userVisitPK, CreateAssociateForm form);
    
    CommandResult<GetAssociateResult> getAssociate(UserVisitPK userVisitPK, GetAssociateForm form);
    
    CommandResult<GetAssociatesResult> getAssociates(UserVisitPK userVisitPK, GetAssociatesForm form);
    
    CommandResult<GetAssociateChoicesResult> getAssociateChoices(UserVisitPK userVisitPK, GetAssociateChoicesForm form);
    
    CommandResult<?> deleteAssociate(UserVisitPK userVisitPK, DeleteAssociateForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Contact Mechanisms
    // -------------------------------------------------------------------------
    
    CommandResult<?> createAssociatePartyContactMechanism(UserVisitPK userVisitPK, CreateAssociatePartyContactMechanismForm form);
    
    CommandResult<GetAssociatePartyContactMechanismResult> getAssociatePartyContactMechanism(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismForm form);
    
    CommandResult<GetAssociatePartyContactMechanismsResult> getAssociatePartyContactMechanisms(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismsForm form);
    
    CommandResult<GetAssociatePartyContactMechanismChoicesResult> getAssociatePartyContactMechanismChoices(UserVisitPK userVisitPK, GetAssociatePartyContactMechanismChoicesForm form);
    
    CommandResult<?> setDefaultAssociatePartyContactMechanism(UserVisitPK userVisitPK, SetDefaultAssociatePartyContactMechanismForm form);
    
    CommandResult<?> deleteAssociatePartyContactMechanism(UserVisitPK userVisitPK, DeleteAssociatePartyContactMechanismForm form);
    
    // -------------------------------------------------------------------------
    //   Associate Referrals
    // -------------------------------------------------------------------------
    
    CommandResult<GetAssociateReferralResult> getAssociateReferral(UserVisitPK userVisitPK, GetAssociateReferralForm form);
    
    CommandResult<GetAssociateReferralsResult> getAssociateReferrals(UserVisitPK userVisitPK, GetAssociateReferralsForm form);
    
    CommandResult<?> deleteAssociateReferral(UserVisitPK userVisitPK, DeleteAssociateReferralForm form);
    
}
