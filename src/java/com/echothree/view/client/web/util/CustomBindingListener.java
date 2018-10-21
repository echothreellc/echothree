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

package com.echothree.view.client.web.util;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import java.io.Serializable;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class CustomBindingListener
        implements HttpSessionBindingListener, Serializable {
    
    UserVisitPK userVisitPK;
    
    /** Creates a new instance of CustomBindingListener */
    public CustomBindingListener(UserVisitPK userVisitPK) {
        this.userVisitPK = userVisitPK;
    }
    
    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        // nothing right now
    }
    
    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        try {
            AuthenticationUtil.getHome().invalidateUserVisit(userVisitPK);
        } catch (NamingException dne) {
            // nothing right now
        }
    }
    
}