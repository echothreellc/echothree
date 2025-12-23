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

package com.echothree.util.client.string.test;

import com.echothree.util.client.string.NameCleaner;
import com.echothree.util.client.test.UserVisitTestCase;
import javax.naming.NamingException;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class NameCleanerTest
        extends UserVisitTestCase {
    
    /** Creates a new instance of NameCleanerTest */
    public NameCleanerTest(String name) {
        super(name);
    }
    
    public static void main(String[] args) {
        TestRunner.run(suite());
    }
    
    public static Test suite() {
        var suite = new TestSuite();
        
        suite.addTest(new NameCleanerTest("testNameCleaner"));
        
        return suite;
    }

    NameCleaner nameCleaner = null;

    @Override
    protected void setUp() {
        super.setUp();

        try {
            nameCleaner = new NameCleaner(userVisitPK);
        } catch(NamingException e) {
            throw new AssertionError("setUp failed", e);
        }
    }

    @Override
    protected void tearDown() {
        super.tearDown();

        nameCleaner = null;
    }

    protected void checkCleansedName(final String str, final String personalTitleChoice, final String firstName, final String middleName,
            final String lastName, final String nameSuffixChoice) {
        var nameResult = nameCleaner.getCleansedName(str);

        if(personalTitleChoice == null ? !(nameResult.getPersonalTitleChoice() == null) : !nameCleaner.getPersonalTitlesOriginal().get(nameResult.getPersonalTitleChoice()).equals(personalTitleChoice)) {
            fail("checkCleansedName personalTitleChoice: " + personalTitleChoice + " != " + nameCleaner.getPersonalTitlesOriginal().get(nameResult.getPersonalTitleChoice()));
        }

        if(firstName == null ? !(nameResult.getFirstName() == null) : !nameResult.getFirstName().equals(firstName)) {
            fail("checkCleansedName firstName: " + firstName + " != " + nameResult.getFirstName());
        }

        if(middleName == null ? !(nameResult.getMiddleName() == null) : !nameResult.getMiddleName().equals(middleName)) {
            fail("checkCleansedName middleName: " + middleName + " != " + nameResult.getMiddleName());
        }

        if(lastName == null ? !(nameResult.getLastName() == null) : !nameResult.getLastName().equals(lastName)) {
            fail("checkCleansedName lastName: " + lastName + " != " + nameResult.getLastName());
        }

        if(nameSuffixChoice == null ? !(nameResult.getNameSuffixChoice() == null) : !nameCleaner.getNameSuffixesOriginal().get(nameResult.getNameSuffixChoice()).equals(nameSuffixChoice)) {
            fail("checkCleansedName nameSuffixChoice: " + nameSuffixChoice + " != " + nameCleaner.getNameSuffixesOriginal().get(nameResult.getNameSuffixChoice()));
        }
    }

    public void testNameCleaner() {
        setUp();

        checkCleansedName("", null, null, null, null, null);
        checkCleansedName("Richard", null, "Richard", null, null, null);
        checkCleansedName("Mr.", "Mr.", null, null, null, null);
        checkCleansedName("Jr.", null, null, null, null, "Jr.");
        checkCleansedName("Richard Harms", null, "Richard", null, "Harms", null);
        checkCleansedName("Mr. Richard Harms", "Mr.", "Richard", null, "Harms", null);
        checkCleansedName("Richard Harms Jr.", null, "Richard", null, "Harms", "Jr.");
        checkCleansedName("Mr. Richard Harms Jr.",  "Mr.", "Richard", null, "Harms", "Jr.");
        checkCleansedName("Richard R. Harms", null,  "Richard", "R.", "Harms", null);
        checkCleansedName("Mr. Richard R. Harms", "Mr.", "Richard", "R.", "Harms", null);
        checkCleansedName("Richard R. Harms Jr.", null, "Richard", "R.", "Harms", "Jr.");
        checkCleansedName("Mr. Richard R. Harms Jr.", "Mr.", "Richard", "R.", "Harms", "Jr.");
        checkCleansedName("Mr. Harms", "Mr.", null, null, "Harms", null);
        checkCleansedName("Richard Jr.", null, "Richard", null, null, "Jr.");
        checkCleansedName("Mr. Harms Jr.", "Mr.", null, null, "Harms", "Jr.");
        checkCleansedName("Mr. Harms, Richard", "Mr.", "Richard", null, "Harms", null);
        checkCleansedName("Mr. Harms, Richard R", "Mr.", "Richard", "R", "Harms", null);
        checkCleansedName("Harms, Richard", null, "Richard", null, "Harms", null);
        checkCleansedName("Harms, Richard R", null, "Richard", "R", "Harms", null);
        checkCleansedName("Harms,", null, "Harms", null, null, null);
        checkCleansedName(",", null, null, null, null, null);
        checkCleansedName("Richard Harms", null, "Richard", null, "Harms", null);
        checkCleansedName("Richard R. S. T. Harms", null, "Richard", "R. S. T.", "Harms", null);
        checkCleansedName("Richard R. Harms-Harms", null, "Richard", "R.", "Harms-Harms", null);
        
        tearDown();
    }

}
