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

package com.echothree.control.user.test.ping;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.ItemService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class ItemServicePingTest
        extends TestCase {
    private ItemService itemService;
    
    /** Creates a new instance of ItemServiceTest */
    public ItemServicePingTest(String name) {
        super(name);
    }
    
    public static void main(String[] args) {
        TestRunner.run(suite());
    }
    
    public static Test suite() {
        var suite = new TestSuite();
        
        suite.addTest(new ItemServicePingTest("testPingItemService"));
        
        return suite;
    }
    
    @Override
    protected void setUp() {
        try {
            itemService = ItemUtil.getHome();
            assertNotNull(itemService);
        } catch (Exception e) {
            fail("setup Exception: " + printException(e));
        }
    }
    
    @Override
    protected void tearDown() {
        try {
            itemService = null;
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
    }
    
    public void testPingItemService() {
        setUp();
        try {
            itemService.ping();
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
        tearDown();
    }
        
    private String printException(Exception e) {
        return e.getClass().getName() + ", " + e.getMessage();
    }
    
}
