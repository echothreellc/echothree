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

/*
Copyright 2005-2006 Seth Fitzsimmons <seth@note.amherst.edu>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.echothree.view.client.web.struts.sprout;

import com.echothree.view.client.web.struts.sprout.support.SpringHttpServlet;
import com.echothree.view.client.web.struts.sprout.support.SproutUtils;
import org.apache.log4j.Logger;

/**
 * <p>Spring-aware <code>HttpServlet</code>.  This uses the
 * <code>Spring WebApplicationContext</code> provided by
 * <code>SpringHttpServlet</code> and uses reflection to determine and
 * fulfill dependencies.</p>
 * 
 * <p>To use, subclass <code>Sproutlet</code> and declare dependencies as you
 * would normally in a Spring-aware environment. (Setter only.)</p>
 * 
 * @see javax.servlet.http.HttpServlet
 * @see org.springframework.web.context.WebApplicationContext
 * @see com.echothree.view.client.web.struts.sprout.support.SpringHttpServlet
 * @author Seth Fitzsimmons
 */
public abstract class Sproutlet
        extends SpringHttpServlet {
    private static final Logger log = Logger.getLogger( Sproutlet.class );
    
    /**
     * Delegates initialization to SproutUtils.
     */
    @Override
    protected void onInit() {
        super.onInit();
        
        log.debug("Initializing Sproutlet.");
        SproutUtils.initialize( this, getWebApplicationContext(), Sproutlet.class );
    }
}
