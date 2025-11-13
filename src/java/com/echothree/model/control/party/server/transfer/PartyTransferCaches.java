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

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class PartyTransferCaches
        extends BaseTransferCaches {
    
    protected PartyControl partyControl;
    
    protected CompanyTransferCache companyTransferCache;
    protected DivisionTransferCache divisionTransferCache;
    protected DepartmentTransferCache departmentTransferCache;
    protected LanguageTransferCache languageTransferCache;
    protected DateTimeFormatTransferCache dateTimeFormatTransferCache;
    protected PartyTypeTransferCache partyTypeTransferCache;
    protected TimeZoneTransferCache timeZoneTransferCache;
    protected NameSuffixTransferCache nameSuffixTransferCache;
    protected PartyGroupTransferCache partyGroupTransferCache;
    protected ProfileTransferCache profileTransferCache;
    protected PersonTransferCache personTransferCache;
    protected PersonalTitleTransferCache personalTitleTransferCache;
    protected PartyTransferCache partyTransferCache;
    protected DateTimeFormatDescriptionTransferCache dateTimeFormatDescriptionTransferCache;
    protected TimeZoneDescriptionTransferCache timeZoneDescriptionTransferCache;
    protected PartyRelationshipTransferCache partyRelationshipTransferCache;
    protected RoleTypeTransferCache roleTypeTransferCache;
    protected PartyTypeAuditPolicyTransferCache partyTypeAuditPolicyTransferCache;
    protected PartyTypeLockoutPolicyTransferCache partyTypeLockoutPolicyTransferCache;
    protected PartyTypePasswordStringPolicyTransferCache partyTypePasswordStringPolicyTransferCache;
    protected GenderTransferCache genderTransferCache;
    protected GenderDescriptionTransferCache genderDescriptionTransferCache;
    protected MoodTransferCache moodTransferCache;
    protected MoodDescriptionTransferCache moodDescriptionTransferCache;
    protected BirthdayFormatTransferCache birthdayFormatTransferCache;
    protected BirthdayFormatDescriptionTransferCache birthdayFormatDescriptionTransferCache;
    protected PartyAliasTypeTransferCache partyAliasTypeTransferCache;
    protected PartyAliasTypeDescriptionTransferCache partyAliasTypeDescriptionTransferCache;
    protected PartyAliasTransferCache partyAliasTransferCache;
    protected PartyEntityTypeTransferCache partyEntityTypeTransferCache;
    protected PartyApplicationEditorUseTransferCache partyApplicationEditorUseTransferCache;

    /** Creates a new instance of PartyTransferCaches */
    public PartyTransferCaches() {
        super();
    }
    
    public CompanyTransferCache getCompanyTransferCache() {
        if(companyTransferCache == null)
            companyTransferCache = new CompanyTransferCache();
        
        return companyTransferCache;
    }
    
    public DivisionTransferCache getDivisionTransferCache() {
        if(divisionTransferCache == null)
            divisionTransferCache = new DivisionTransferCache();
        
        return divisionTransferCache;
    }
    
    public DepartmentTransferCache getDepartmentTransferCache() {
        if(departmentTransferCache == null)
            departmentTransferCache = new DepartmentTransferCache();
        
        return departmentTransferCache;
    }
    
    public LanguageTransferCache getLanguageTransferCache() {
        if(languageTransferCache == null)
            languageTransferCache = new LanguageTransferCache();
        
        return languageTransferCache;
    }
    
    public DateTimeFormatTransferCache getDateTimeFormatTransferCache() {
        if(dateTimeFormatTransferCache == null)
            dateTimeFormatTransferCache = new DateTimeFormatTransferCache();
        
        return dateTimeFormatTransferCache;
    }
    
    public PartyTypeTransferCache getPartyTypeTransferCache() {
        if(partyTypeTransferCache == null)
            partyTypeTransferCache = new PartyTypeTransferCache();
        
        return partyTypeTransferCache;
    }
    
    public TimeZoneTransferCache getTimeZoneTransferCache() {
        if(timeZoneTransferCache == null)
            timeZoneTransferCache = new TimeZoneTransferCache();
        
        return timeZoneTransferCache;
    }
    
    public NameSuffixTransferCache getNameSuffixTransferCache() {
        if(nameSuffixTransferCache == null)
            nameSuffixTransferCache = new NameSuffixTransferCache();
        
        return nameSuffixTransferCache;
    }
    
    public PartyGroupTransferCache getPartyGroupTransferCache() {
        if(partyGroupTransferCache == null)
            partyGroupTransferCache = new PartyGroupTransferCache();
        
        return partyGroupTransferCache;
    }
    
    public ProfileTransferCache getProfileTransferCache() {
        if(profileTransferCache == null)
            profileTransferCache = new ProfileTransferCache();
        
        return profileTransferCache;
    }
    
    public PersonTransferCache getPersonTransferCache() {
        if(personTransferCache == null)
            personTransferCache = new PersonTransferCache();
        
        return personTransferCache;
    }
    
    public PersonalTitleTransferCache getPersonalTitleTransferCache() {
        if(personalTitleTransferCache == null)
            personalTitleTransferCache = new PersonalTitleTransferCache();
        
        return personalTitleTransferCache;
    }
    
    public PartyTransferCache getPartyTransferCache() {
        if(partyTransferCache == null)
            partyTransferCache = new PartyTransferCache();
        
        return partyTransferCache;
    }
    
    public DateTimeFormatDescriptionTransferCache getDateTimeFormatDescriptionTransferCache() {
        if(dateTimeFormatDescriptionTransferCache == null)
            dateTimeFormatDescriptionTransferCache = new DateTimeFormatDescriptionTransferCache();
        
        return dateTimeFormatDescriptionTransferCache;
    }
    
    public TimeZoneDescriptionTransferCache getTimeZoneDescriptionTransferCache() {
        if(timeZoneDescriptionTransferCache == null)
            timeZoneDescriptionTransferCache = new TimeZoneDescriptionTransferCache();
        
        return timeZoneDescriptionTransferCache;
    }
    
    public PartyRelationshipTransferCache getPartyRelationshipTransferCache() {
        if(partyRelationshipTransferCache == null)
            partyRelationshipTransferCache = new PartyRelationshipTransferCache();
        
        return partyRelationshipTransferCache;
    }
    
    public RoleTypeTransferCache getRoleTypeTransferCache() {
        if(roleTypeTransferCache == null)
            roleTypeTransferCache = new RoleTypeTransferCache();
        
        return roleTypeTransferCache;
    }
    
    public PartyTypeAuditPolicyTransferCache getPartyTypeAuditPolicyTransferCache() {
        if(partyTypeAuditPolicyTransferCache == null)
            partyTypeAuditPolicyTransferCache = new PartyTypeAuditPolicyTransferCache();
        
        return partyTypeAuditPolicyTransferCache;
    }
    
    public PartyTypeLockoutPolicyTransferCache getPartyTypeLockoutPolicyTransferCache() {
        if(partyTypeLockoutPolicyTransferCache == null)
            partyTypeLockoutPolicyTransferCache = new PartyTypeLockoutPolicyTransferCache();
        
        return partyTypeLockoutPolicyTransferCache;
    }
    
    public PartyTypePasswordStringPolicyTransferCache getPartyTypePasswordStringPolicyTransferCache() {
        if(partyTypePasswordStringPolicyTransferCache == null)
            partyTypePasswordStringPolicyTransferCache = new PartyTypePasswordStringPolicyTransferCache();
        
        return partyTypePasswordStringPolicyTransferCache;
    }
    
    public GenderTransferCache getGenderTransferCache() {
        if(genderTransferCache == null)
            genderTransferCache = new GenderTransferCache();
        
        return genderTransferCache;
    }
    
    public GenderDescriptionTransferCache getGenderDescriptionTransferCache() {
        if(genderDescriptionTransferCache == null)
            genderDescriptionTransferCache = new GenderDescriptionTransferCache();
        
        return genderDescriptionTransferCache;
    }
    
    public MoodTransferCache getMoodTransferCache() {
        if(moodTransferCache == null)
            moodTransferCache = new MoodTransferCache();

        return moodTransferCache;
    }

    public MoodDescriptionTransferCache getMoodDescriptionTransferCache() {
        if(moodDescriptionTransferCache == null)
            moodDescriptionTransferCache = new MoodDescriptionTransferCache();

        return moodDescriptionTransferCache;
    }

    public BirthdayFormatTransferCache getBirthdayFormatTransferCache() {
        if(birthdayFormatTransferCache == null)
            birthdayFormatTransferCache = new BirthdayFormatTransferCache();

        return birthdayFormatTransferCache;
    }

    public BirthdayFormatDescriptionTransferCache getBirthdayFormatDescriptionTransferCache() {
        if(birthdayFormatDescriptionTransferCache == null)
            birthdayFormatDescriptionTransferCache = new BirthdayFormatDescriptionTransferCache();

        return birthdayFormatDescriptionTransferCache;
    }

    public PartyAliasTypeTransferCache getPartyAliasTypeTransferCache() {
        if(partyAliasTypeTransferCache == null)
            partyAliasTypeTransferCache = new PartyAliasTypeTransferCache();

        return partyAliasTypeTransferCache;
    }

    public PartyAliasTypeDescriptionTransferCache getPartyAliasTypeDescriptionTransferCache() {
        if(partyAliasTypeDescriptionTransferCache == null)
            partyAliasTypeDescriptionTransferCache = new PartyAliasTypeDescriptionTransferCache();

        return partyAliasTypeDescriptionTransferCache;
    }

    public PartyAliasTransferCache getPartyAliasTransferCache() {
        if(partyAliasTransferCache == null)
            partyAliasTransferCache = new PartyAliasTransferCache();

        return partyAliasTransferCache;
    }

    public PartyEntityTypeTransferCache getPartyEntityTypeTransferCache() {
        if(partyEntityTypeTransferCache == null)
            partyEntityTypeTransferCache = new PartyEntityTypeTransferCache();

        return partyEntityTypeTransferCache;
    }

    public PartyApplicationEditorUseTransferCache getPartyApplicationEditorUseTransferCache() {
        if(partyApplicationEditorUseTransferCache == null)
            partyApplicationEditorUseTransferCache = new PartyApplicationEditorUseTransferCache();

        return partyApplicationEditorUseTransferCache;
    }

}
