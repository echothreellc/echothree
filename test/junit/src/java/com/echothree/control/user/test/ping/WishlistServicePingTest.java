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

package com.echothree.control.user.test.ping;

import com.echothree.control.user.wishlist.common.WishlistUtil;
import com.echothree.control.user.wishlist.common.WishlistService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WishlistServicePingTest {
    private WishlistService wishlistService;
    
    @BeforeEach
    public void setUp() {
        try {
            wishlistService = WishlistUtil.getHome();
            assertNotNull(wishlistService);
        } catch (Exception e) {
            fail("setup Exception: " + printException(e));
        }
    }
    
    @AfterEach
    public void tearDown() {
        try {
            wishlistService = null;
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
    }
    
    @Test
    public void testPingWishlistService() {
        try {
            wishlistService.ping();
        } catch (Exception e) {
            fail("Exception: " + printException(e));
        }
    }
        
    private String printException(Exception e) {
        return e.getClass().getName() + ", " + e.getMessage();
    }
    
}
