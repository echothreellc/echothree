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

package com.echothree.control.user.selector.common;

import com.echothree.control.user.selector.server.SelectorLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.NamingException;

public class SelectorUtil {
    
    private static SelectorLocal cachedLocal = null;
    private static SelectorRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static SelectorLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (SelectorLocal)ctx.lookup("ejb:echothree/echothree-server/SelectorBean!com.echothree.control.user.selector.server.SelectorLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static SelectorRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (SelectorRemote)ctx.lookup("ejb:echothree/echothree-server/SelectorBean!com.echothree.control.user.selector.common.SelectorRemote");
        }
        
        return cachedRemote;
    }
    
}
