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

package com.echothree.util.client.test;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import junit.framework.TestCase;

public abstract class UserVisitTestCase
        extends TestCase {
    
    /** Creates a new instance of UserVisitTestCase */
    protected UserVisitTestCase(String name) {
        super(name);
    }

    protected UserVisitPK userVisitPK = null;

    @Override
    protected void setUp() {
        try {
            userVisitPK = AuthenticationUtil.getHome().getDataLoaderUserVisit();
        } catch(Exception e) {
            throw (AssertionError)new AssertionError("setUp failed").initCause(e);
        }
    }

    @Override
    protected void tearDown() {
        try {
            AuthenticationUtil.getHome().invalidateUserVisit(userVisitPK);
            userVisitPK = null;
        } catch(Exception e) {
            throw (AssertionError)new AssertionError("tearDown failed").initCause(e);
        }
    }

}
