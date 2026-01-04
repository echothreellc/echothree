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

package com.echothree.util.common.string;

public class NameResult {

    private String personalTitleChoice;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameSuffixChoice;

    public NameResult(final String personalTitleChoice, final String firstName, final String middleName,
            final String lastName, final String nameSuffixChoice) {
        this.personalTitleChoice = personalTitleChoice;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffixChoice = nameSuffixChoice;
    }

    /**
     * Returns the personalTitleChoice.
     * @return the personalTitleChoice
     */
    public String getPersonalTitleChoice() {
        return personalTitleChoice;
    }

    /**
     * Returns the firstName.
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the middleName.
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Returns the lastName.
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the nameSuffixChoice.
     * @return the nameSuffixChoice
     */
    public String getNameSuffixChoice() {
        return nameSuffixChoice;
    }

    public void print() {
        System.out.println("personalTitleChoice.: " + personalTitleChoice);
        System.out.println("firstName...........: " + firstName);
        System.out.println("middleName..........: " + middleName);
        System.out.println("lastName............: " + lastName);
        System.out.println("nameSuffixChoice....: " + nameSuffixChoice);
    }

    public String getFormattedName() {
        var formattedName = new StringBuilder();

        if(firstName != null) {
            formattedName.append(firstName);
        }

        if(middleName != null) {
            if(formattedName.length() > 0) {
                formattedName.append(' ');
            }

            formattedName.append(middleName);

            if(middleName.length() == 1) {
                formattedName.append('.');
            }
        }

        if(lastName != null) {
            if(formattedName.length() > 0) {
                formattedName.append(' ');
            }

            formattedName.append(lastName);
        }

        return formattedName.toString();
    }

}
