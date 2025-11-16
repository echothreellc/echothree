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
import javax.enterprise.inject.spi.CDI;

public class PartyTransferCaches
        extends BaseTransferCaches {
    
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
            companyTransferCache = CDI.current().select(CompanyTransferCache.class).get();
        
        return companyTransferCache;
    }
    
    public DivisionTransferCache getDivisionTransferCache() {
        if(divisionTransferCache == null)
            divisionTransferCache = CDI.current().select(DivisionTransferCache.class).get();
        
        return divisionTransferCache;
    }
    
    public DepartmentTransferCache getDepartmentTransferCache() {
        if(departmentTransferCache == null)
            departmentTransferCache = CDI.current().select(DepartmentTransferCache.class).get();
        
        return departmentTransferCache;
    }
    
    public LanguageTransferCache getLanguageTransferCache() {
        if(languageTransferCache == null)
            languageTransferCache = CDI.current().select(LanguageTransferCache.class).get();
        
        return languageTransferCache;
    }
    
    public DateTimeFormatTransferCache getDateTimeFormatTransferCache() {
        if(dateTimeFormatTransferCache == null)
            dateTimeFormatTransferCache = CDI.current().select(DateTimeFormatTransferCache.class).get();
        
        return dateTimeFormatTransferCache;
    }
    
    public PartyTypeTransferCache getPartyTypeTransferCache() {
        if(partyTypeTransferCache == null)
            partyTypeTransferCache = CDI.current().select(PartyTypeTransferCache.class).get();
        
        return partyTypeTransferCache;
    }
    
    public TimeZoneTransferCache getTimeZoneTransferCache() {
        if(timeZoneTransferCache == null)
            timeZoneTransferCache = CDI.current().select(TimeZoneTransferCache.class).get();
        
        return timeZoneTransferCache;
    }
    
    public NameSuffixTransferCache getNameSuffixTransferCache() {
        if(nameSuffixTransferCache == null)
            nameSuffixTransferCache = CDI.current().select(NameSuffixTransferCache.class).get();
        
        return nameSuffixTransferCache;
    }
    
    public PartyGroupTransferCache getPartyGroupTransferCache() {
        if(partyGroupTransferCache == null)
            partyGroupTransferCache = CDI.current().select(PartyGroupTransferCache.class).get();
        
        return partyGroupTransferCache;
    }
    
    public ProfileTransferCache getProfileTransferCache() {
        if(profileTransferCache == null)
            profileTransferCache = CDI.current().select(ProfileTransferCache.class).get();
        
        return profileTransferCache;
    }
    
    public PersonTransferCache getPersonTransferCache() {
        if(personTransferCache == null)
            personTransferCache = CDI.current().select(PersonTransferCache.class).get();
        
        return personTransferCache;
    }
    
    public PersonalTitleTransferCache getPersonalTitleTransferCache() {
        if(personalTitleTransferCache == null)
            personalTitleTransferCache = CDI.current().select(PersonalTitleTransferCache.class).get();
        
        return personalTitleTransferCache;
    }
    
    public PartyTransferCache getPartyTransferCache() {
        if(partyTransferCache == null)
            partyTransferCache = CDI.current().select(PartyTransferCache.class).get();
        
        return partyTransferCache;
    }
    
    public DateTimeFormatDescriptionTransferCache getDateTimeFormatDescriptionTransferCache() {
        if(dateTimeFormatDescriptionTransferCache == null)
            dateTimeFormatDescriptionTransferCache = CDI.current().select(DateTimeFormatDescriptionTransferCache.class).get();
        
        return dateTimeFormatDescriptionTransferCache;
    }
    
    public TimeZoneDescriptionTransferCache getTimeZoneDescriptionTransferCache() {
        if(timeZoneDescriptionTransferCache == null)
            timeZoneDescriptionTransferCache = CDI.current().select(TimeZoneDescriptionTransferCache.class).get();
        
        return timeZoneDescriptionTransferCache;
    }
    
    public PartyRelationshipTransferCache getPartyRelationshipTransferCache() {
        if(partyRelationshipTransferCache == null)
            partyRelationshipTransferCache = CDI.current().select(PartyRelationshipTransferCache.class).get();
        
        return partyRelationshipTransferCache;
    }
    
    public RoleTypeTransferCache getRoleTypeTransferCache() {
        if(roleTypeTransferCache == null)
            roleTypeTransferCache = CDI.current().select(RoleTypeTransferCache.class).get();
        
        return roleTypeTransferCache;
    }
    
    public PartyTypeAuditPolicyTransferCache getPartyTypeAuditPolicyTransferCache() {
        if(partyTypeAuditPolicyTransferCache == null)
            partyTypeAuditPolicyTransferCache = CDI.current().select(PartyTypeAuditPolicyTransferCache.class).get();
        
        return partyTypeAuditPolicyTransferCache;
    }
    
    public PartyTypeLockoutPolicyTransferCache getPartyTypeLockoutPolicyTransferCache() {
        if(partyTypeLockoutPolicyTransferCache == null)
            partyTypeLockoutPolicyTransferCache = CDI.current().select(PartyTypeLockoutPolicyTransferCache.class).get();
        
        return partyTypeLockoutPolicyTransferCache;
    }
    
    public PartyTypePasswordStringPolicyTransferCache getPartyTypePasswordStringPolicyTransferCache() {
        if(partyTypePasswordStringPolicyTransferCache == null)
            partyTypePasswordStringPolicyTransferCache = CDI.current().select(PartyTypePasswordStringPolicyTransferCache.class).get();
        
        return partyTypePasswordStringPolicyTransferCache;
    }
    
    public GenderTransferCache getGenderTransferCache() {
        if(genderTransferCache == null)
            genderTransferCache = CDI.current().select(GenderTransferCache.class).get();
        
        return genderTransferCache;
    }
    
    public GenderDescriptionTransferCache getGenderDescriptionTransferCache() {
        if(genderDescriptionTransferCache == null)
            genderDescriptionTransferCache = CDI.current().select(GenderDescriptionTransferCache.class).get();
        
        return genderDescriptionTransferCache;
    }
    
    public MoodTransferCache getMoodTransferCache() {
        if(moodTransferCache == null)
            moodTransferCache = CDI.current().select(MoodTransferCache.class).get();

        return moodTransferCache;
    }

    public MoodDescriptionTransferCache getMoodDescriptionTransferCache() {
        if(moodDescriptionTransferCache == null)
            moodDescriptionTransferCache = CDI.current().select(MoodDescriptionTransferCache.class).get();

        return moodDescriptionTransferCache;
    }

    public BirthdayFormatTransferCache getBirthdayFormatTransferCache() {
        if(birthdayFormatTransferCache == null)
            birthdayFormatTransferCache = CDI.current().select(BirthdayFormatTransferCache.class).get();

        return birthdayFormatTransferCache;
    }

    public BirthdayFormatDescriptionTransferCache getBirthdayFormatDescriptionTransferCache() {
        if(birthdayFormatDescriptionTransferCache == null)
            birthdayFormatDescriptionTransferCache = CDI.current().select(BirthdayFormatDescriptionTransferCache.class).get();

        return birthdayFormatDescriptionTransferCache;
    }

    public PartyAliasTypeTransferCache getPartyAliasTypeTransferCache() {
        if(partyAliasTypeTransferCache == null)
            partyAliasTypeTransferCache = CDI.current().select(PartyAliasTypeTransferCache.class).get();

        return partyAliasTypeTransferCache;
    }

    public PartyAliasTypeDescriptionTransferCache getPartyAliasTypeDescriptionTransferCache() {
        if(partyAliasTypeDescriptionTransferCache == null)
            partyAliasTypeDescriptionTransferCache = CDI.current().select(PartyAliasTypeDescriptionTransferCache.class).get();

        return partyAliasTypeDescriptionTransferCache;
    }

    public PartyAliasTransferCache getPartyAliasTransferCache() {
        if(partyAliasTransferCache == null)
            partyAliasTransferCache = CDI.current().select(PartyAliasTransferCache.class).get();

        return partyAliasTransferCache;
    }

    public PartyEntityTypeTransferCache getPartyEntityTypeTransferCache() {
        if(partyEntityTypeTransferCache == null)
            partyEntityTypeTransferCache = CDI.current().select(PartyEntityTypeTransferCache.class).get();

        return partyEntityTypeTransferCache;
    }

    public PartyApplicationEditorUseTransferCache getPartyApplicationEditorUseTransferCache() {
        if(partyApplicationEditorUseTransferCache == null)
            partyApplicationEditorUseTransferCache = CDI.current().select(PartyApplicationEditorUseTransferCache.class).get();

        return partyApplicationEditorUseTransferCache;
    }

}
