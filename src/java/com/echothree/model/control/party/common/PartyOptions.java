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

package com.echothree.model.control.party.common;

public interface PartyOptions {
    
    String PartyTypeIncludeAuditPolicy = "PartyTypeIncludeAuditPolicy";
    String PartyTypeIncludeLockoutPolicy = "PartyTypeIncludeLockoutPolicy";
    String PartyTypeIncludePasswordStringPolicy = "PartyTypeIncludePasswordStringPolicy";
    String PartyTypeIncludePartyAliasTypes = "PartyTypeIncludePartyAliasTypes";
    
    String PartyIncludeUuid = "PartyIncludeUuid";
    String PartyIncludeDescription = "PartyIncludeDescription";
    String PartyIncludePartyCarriers = "PartyIncludePartyCarriers";
    String PartyIncludePartyCarrierAccounts = "PartyIncludePartyCarrierAccounts";
    String PartyIncludePartyContactLists = "PartyIncludeCPartyontactLists";
    String PartyIncludePartyAliases = "PartyIncludePartyAliases";
    String PartyIncludePartyContactMechanisms = "PartyIncludePartyContactMechanisms";
    String PartyIncludeRecoveryAnswer = "PartyIncludeRecoveryAnswer";
    String PartyIncludeUserLogin = "PartyIncludeUserLogin";
    String PartyIncludePartyDocuments = "PartyIncludePartyDocuments";
    String PartyIncludePartyPrinterGroupUses = "PartyIncludePartyPrinterGroupUses";
    String PartyIncludePartyScaleUses = "PartyIncludePartyScaleUses";
    String PartyIncludePartyEntityTypes = "PartyIncludePartyEntityTypes";
    String PartyIncludePartyApplicationEditorUses = "PartyIncludePartyApplicationEditorUses";
    String PartyIncludePartyRelationships = "PartyIncludePartyRelationships";
    String PartyIncludePartyRelationshipsByFromParty = "PartyIncludePartyRelationshipsByFromParty";
    String PartyIncludePartyRelationshipsByToParty = "PartyIncludePartyRelationshipsByToParty";
    String PartyIncludeEmployments = "PartyIncludeEmployments";
    String PartyIncludeLeaves = "PartyIncludeLeaves";
    String PartyIncludePartyResponsibilities = "PartyIncludePartyResponsibilities";
    String PartyIncludePartyTrainingClasses = "PartyIncludePartyTrainingClasses";
    String PartyIncludePartySkills = "PartyIncludePartySkills";

    String PartyAliasTypeIncludeEntityAttributeGroups = "PartyAliasTypeIncludeEntityAttributeGroups";
    String PartyAliasTypeIncludeTagScopes = "PartyAliasTypeIncludeTagScopes";
    String PartyAliasTypeIncludeUuid = "PartyAliasTypeIncludeUuid";
    
    String CompanyIncludeEntityAttributeGroups = "CompanyIncludeEntityAttributeGroups";
    String CompanyIncludeTagScopes = "CompanyIncludeTagScopes";
    String CompanyIncludeUuid = "CompanyIncludeUuid";
    String CompanyIncludeBillingAccounts = "CompanyIncludeBillingAccounts";
    String CompanyIncludeInvoicesFrom = "CompanyIncludeInvoicesFrom";
    String CompanyIncludeInvoicesTo = "CompanyIncludeInvoicesTo";
    
    String DivisionIncludeEntityAttributeGroups = "DivisionIncludeEntityAttributeGroups";
    String DivisionIncludeTagScopes = "DivisionIncludeTagScopes";
    String DivisionIncludeUuid = "DivisionIncludeUuid";
    
    String DepartmentIncludeEntityAttributeGroups = "DepartmentIncludeEntityAttributeGroups";
    String DepartmentIncludeTagScopes = "DepartmentIncludeTagScopes";
    String DepartmentIncludeUuid = "DepartmentIncludeUuid";
    
}
