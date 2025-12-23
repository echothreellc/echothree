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

package com.echothree.model.control.contact.common;

public interface ContactOptions {
    
    String PostalAddressFormatIncludeLines = "PostalAddressFormatIncludeLines";
    
    String PostalAddressLineIncludeElements = "PostalAddressLineIncludeElements";

    String ContactMechanismIncludeUuid = "ContactMechanismIncludeUuid";
    String ContactMechanismIncludeComments = "ContactMechanismIncludeComments";
    String ContactMechanismIncludeEntityAttributeGroups = "ContactMechanismIncludeEntityAttributeGroups";
    String ContactMechanismIncludeTagScopes = "ContactMechanismIncludeTagScopes";

    String PartyContactMechanismIncludePartyContactMechanismPurposes = "PartyContactMechanismIncludePartyContactMechanismPurposes";
    String PartyContactMechanismIncludePartyContactMechanismRelationships = "PartyContactMechanismIncludePartyContactMechanismRelationships";
    String PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism = "PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism";
    String PartyContactMechanismIncludePartyContactMechanismRelationshipsByToPartyContactMechanism = "PartyContactMechanismIncludePartyContactMechanismRelationshipsByToPartyContactMechanism";
    String PartyContactMechanismIncludeUuid = "PartyContactMechanismIncludeUuid";

}
