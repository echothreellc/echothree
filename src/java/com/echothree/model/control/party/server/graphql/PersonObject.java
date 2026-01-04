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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.data.party.server.entity.Person;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("person object")
@GraphQLName("Person")
public class PersonObject {
    
    private final Person person; // Always Present
    
    public PersonObject(Person person) {
        this.person = person;
    }

    @GraphQLField
    @GraphQLDescription("personal title")
    public PersonalTitleObject getPersonalTitle() {
        var personalTitle = person.getPersonalTitle();
        
        return personalTitle == null ? null : new PersonalTitleObject(personalTitle);
    }
    
    @GraphQLField
    @GraphQLDescription("first name")
    public String getFirstName() {
        return person.getFirstName();
    }
    
    @GraphQLField
    @GraphQLDescription("middle name")
    public String getMiddleName() {
        return person.getMiddleName();
    }
    
    @GraphQLField
    @GraphQLDescription("last name")
    public String getLastName() {
        return person.getLastName();
    }
    
    @GraphQLField
    @GraphQLDescription("name suffix")
    public NameSuffixObject getNameSuffix() {
        var nameSuffix = person.getNameSuffix();
        
        return nameSuffix == null ? null : new NameSuffixObject(nameSuffix);
    }
    
}
