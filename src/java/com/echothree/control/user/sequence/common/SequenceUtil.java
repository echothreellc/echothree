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

package com.echothree.control.user.sequence.common;

import com.echothree.control.user.sequence.server.SequenceLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.NamingException;

public class SequenceUtil {
    
    private static SequenceLocal cachedLocal = null;
    private static SequenceRemote cachedRemote = null;

    @SuppressWarnings("BanJNDI")
    public static SequenceLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (SequenceLocal)ctx.lookup("ejb:echothree/echothree-server/SequenceBean!com.echothree.control.user.sequence.server.SequenceLocal");
        }
        
        return cachedLocal;
    }

    @SuppressWarnings("BanJNDI")
    public static SequenceRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            var ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (SequenceRemote)ctx.lookup("ejb:echothree/echothree-server/SequenceBean!com.echothree.control.user.sequence.common.SequenceRemote");
        }
        
        return cachedRemote;
    }
    
}
