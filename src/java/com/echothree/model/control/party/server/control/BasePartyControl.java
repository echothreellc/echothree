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

package com.echothree.model.control.party.server.control;

import com.echothree.model.control.party.server.transfer.BirthdayFormatDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.BirthdayFormatTransferCache;
import com.echothree.model.control.party.server.transfer.CompanyTransferCache;
import com.echothree.model.control.party.server.transfer.DateTimeFormatDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.DateTimeFormatTransferCache;
import com.echothree.model.control.party.server.transfer.DepartmentTransferCache;
import com.echothree.model.control.party.server.transfer.DivisionTransferCache;
import com.echothree.model.control.party.server.transfer.GenderDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.GenderTransferCache;
import com.echothree.model.control.party.server.transfer.LanguageTransferCache;
import com.echothree.model.control.party.server.transfer.MoodDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.MoodTransferCache;
import com.echothree.model.control.party.server.transfer.NameSuffixTransferCache;
import com.echothree.model.control.party.server.transfer.PartyAliasTransferCache;
import com.echothree.model.control.party.server.transfer.PartyAliasTypeDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.PartyAliasTypeTransferCache;
import com.echothree.model.control.party.server.transfer.PartyApplicationEditorUseTransferCache;
import com.echothree.model.control.party.server.transfer.PartyEntityTypeTransferCache;
import com.echothree.model.control.party.server.transfer.PartyGroupTransferCache;
import com.echothree.model.control.party.server.transfer.PartyRelationshipTransferCache;
import com.echothree.model.control.party.server.transfer.PartyTransferCache;
import com.echothree.model.control.party.server.transfer.PartyTypeAuditPolicyTransferCache;
import com.echothree.model.control.party.server.transfer.PartyTypeLockoutPolicyTransferCache;
import com.echothree.model.control.party.server.transfer.PartyTypePasswordStringPolicyTransferCache;
import com.echothree.model.control.party.server.transfer.PartyTypeTransferCache;
import com.echothree.model.control.party.server.transfer.PersonTransferCache;
import com.echothree.model.control.party.server.transfer.PersonalTitleTransferCache;
import com.echothree.model.control.party.server.transfer.ProfileTransferCache;
import com.echothree.model.control.party.server.transfer.RoleTypeTransferCache;
import com.echothree.model.control.party.server.transfer.TimeZoneDescriptionTransferCache;
import com.echothree.model.control.party.server.transfer.TimeZoneTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public class BasePartyControl
        extends BaseModelControl {

    /** Creates a new instance of BasePartyControl */
    protected BasePartyControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    CompanyTransferCache companyTransferCache;

    @Inject
    DivisionTransferCache divisionTransferCache;

    @Inject
    DepartmentTransferCache departmentTransferCache;

    @Inject
    LanguageTransferCache languageTransferCache;

    @Inject
    DateTimeFormatTransferCache dateTimeFormatTransferCache;

    @Inject
    PartyTypeTransferCache partyTypeTransferCache;

    @Inject
    TimeZoneTransferCache timeZoneTransferCache;

    @Inject
    NameSuffixTransferCache nameSuffixTransferCache;

    @Inject
    PartyGroupTransferCache partyGroupTransferCache;

    @Inject
    ProfileTransferCache profileTransferCache;

    @Inject
    PersonTransferCache personTransferCache;

    @Inject
    PersonalTitleTransferCache personalTitleTransferCache;

    @Inject
    PartyTransferCache partyTransferCache;

    @Inject
    DateTimeFormatDescriptionTransferCache dateTimeFormatDescriptionTransferCache;

    @Inject
    TimeZoneDescriptionTransferCache timeZoneDescriptionTransferCache;

    @Inject
    PartyRelationshipTransferCache partyRelationshipTransferCache;

    @Inject
    RoleTypeTransferCache roleTypeTransferCache;

    @Inject
    PartyTypeAuditPolicyTransferCache partyTypeAuditPolicyTransferCache;

    @Inject
    PartyTypeLockoutPolicyTransferCache partyTypeLockoutPolicyTransferCache;

    @Inject
    PartyTypePasswordStringPolicyTransferCache partyTypePasswordStringPolicyTransferCache;

    @Inject
    GenderTransferCache genderTransferCache;

    @Inject
    GenderDescriptionTransferCache genderDescriptionTransferCache;

    @Inject
    MoodTransferCache moodTransferCache;

    @Inject
    MoodDescriptionTransferCache moodDescriptionTransferCache;

    @Inject
    BirthdayFormatTransferCache birthdayFormatTransferCache;

    @Inject
    BirthdayFormatDescriptionTransferCache birthdayFormatDescriptionTransferCache;

    @Inject
    PartyAliasTypeTransferCache partyAliasTypeTransferCache;

    @Inject
    PartyAliasTypeDescriptionTransferCache partyAliasTypeDescriptionTransferCache;

    @Inject
    PartyAliasTransferCache partyAliasTransferCache;

    @Inject
    PartyEntityTypeTransferCache partyEntityTypeTransferCache;

    @Inject
    PartyApplicationEditorUseTransferCache partyApplicationEditorUseTransferCache;

}
