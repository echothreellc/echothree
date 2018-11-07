// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.offer.common;

import com.echothree.control.user.offer.common.OfferRemote;
import com.echothree.control.user.offer.server.OfferLocal;
import com.echothree.util.common.control.InitialContextUtils;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OfferUtil {
    
    private static OfferLocal cachedLocal = null;
    private static OfferRemote cachedRemote = null;
    
    public static OfferLocal getLocalHome()
            throws NamingException {
        if(cachedLocal == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();

            cachedLocal = (OfferLocal)ctx.lookup("ejb:echothree/echothree-server/OfferBean!com.echothree.control.user.offer.server.OfferLocal");
        }
        
        return cachedLocal;
    }
    
    public static OfferRemote getHome()
            throws NamingException {
        if(cachedRemote == null) {
            InitialContext ctx = InitialContextUtils.getInstance().getInitialContext();
            
            cachedRemote = (OfferRemote)ctx.lookup("ejb:echothree/echothree-server/OfferBean!com.echothree.control.user.offer.common.OfferRemote");
        }
        
        return cachedRemote;
    }
    
}
