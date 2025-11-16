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

package com.echothree.model.control.party.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PartyTransferCaches
        extends BaseTransferCaches {
    
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
    
    /** Creates a new instance of PartyTransferCaches */
    protected PartyTransferCaches() {
        super();
    }
    
    public CompanyTransferCache getCompanyTransferCache() {
        return companyTransferCache;
    }
    
    public DivisionTransferCache getDivisionTransferCache() {
        return divisionTransferCache;
    }
    
    public DepartmentTransferCache getDepartmentTransferCache() {
        return departmentTransferCache;
    }
    
    public LanguageTransferCache getLanguageTransferCache() {
        return languageTransferCache;
    }
    
    public DateTimeFormatTransferCache getDateTimeFormatTransferCache() {
        return dateTimeFormatTransferCache;
    }
    
    public PartyTypeTransferCache getPartyTypeTransferCache() {
        return partyTypeTransferCache;
    }
    
    public TimeZoneTransferCache getTimeZoneTransferCache() {
        return timeZoneTransferCache;
    }
    
    public NameSuffixTransferCache getNameSuffixTransferCache() {
        return nameSuffixTransferCache;
    }
    
    public PartyGroupTransferCache getPartyGroupTransferCache() {
        return partyGroupTransferCache;
    }
    
    public ProfileTransferCache getProfileTransferCache() {
        return profileTransferCache;
    }
    
    public PersonTransferCache getPersonTransferCache() {
        return personTransferCache;
    }
    
    public PersonalTitleTransferCache getPersonalTitleTransferCache() {
        return personalTitleTransferCache;
    }
    
    public PartyTransferCache getPartyTransferCache() {
        return partyTransferCache;
    }
    
    public DateTimeFormatDescriptionTransferCache getDateTimeFormatDescriptionTransferCache() {
        return dateTimeFormatDescriptionTransferCache;
    }
    
    public TimeZoneDescriptionTransferCache getTimeZoneDescriptionTransferCache() {
        return timeZoneDescriptionTransferCache;
    }
    
    public PartyRelationshipTransferCache getPartyRelationshipTransferCache() {
        return partyRelationshipTransferCache;
    }
    
    public RoleTypeTransferCache getRoleTypeTransferCache() {
        return roleTypeTransferCache;
    }
    
    public PartyTypeAuditPolicyTransferCache getPartyTypeAuditPolicyTransferCache() {
        return partyTypeAuditPolicyTransferCache;
    }
    
    public PartyTypeLockoutPolicyTransferCache getPartyTypeLockoutPolicyTransferCache() {
        return partyTypeLockoutPolicyTransferCache;
    }
    
    public PartyTypePasswordStringPolicyTransferCache getPartyTypePasswordStringPolicyTransferCache() {
        return partyTypePasswordStringPolicyTransferCache;
    }
    
    public GenderTransferCache getGenderTransferCache() {
        return genderTransferCache;
    }
    
    public GenderDescriptionTransferCache getGenderDescriptionTransferCache() {
        return genderDescriptionTransferCache;
    }
    
    public MoodTransferCache getMoodTransferCache() {
        return moodTransferCache;
    }

    public MoodDescriptionTransferCache getMoodDescriptionTransferCache() {
        return moodDescriptionTransferCache;
    }

    public BirthdayFormatTransferCache getBirthdayFormatTransferCache() {
        return birthdayFormatTransferCache;
    }

    public BirthdayFormatDescriptionTransferCache getBirthdayFormatDescriptionTransferCache() {
        return birthdayFormatDescriptionTransferCache;
    }

    public PartyAliasTypeTransferCache getPartyAliasTypeTransferCache() {
        return partyAliasTypeTransferCache;
    }

    public PartyAliasTypeDescriptionTransferCache getPartyAliasTypeDescriptionTransferCache() {
        return partyAliasTypeDescriptionTransferCache;
    }

    public PartyAliasTransferCache getPartyAliasTransferCache() {
        return partyAliasTransferCache;
    }

    public PartyEntityTypeTransferCache getPartyEntityTypeTransferCache() {
        return partyEntityTypeTransferCache;
    }

    public PartyApplicationEditorUseTransferCache getPartyApplicationEditorUseTransferCache() {
        return partyApplicationEditorUseTransferCache;
    }

}
