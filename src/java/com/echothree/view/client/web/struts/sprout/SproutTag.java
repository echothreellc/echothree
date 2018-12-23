// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.view.client.web.struts.sprout.support.SpringBodyTagSupport;
import com.echothree.view.client.web.struts.sprout.support.SproutUtils;
import org.apache.log4j.Logger;

/**
 * <p>Spring-aware <code>BodyTagSupport</code>.  This uses the Spring
 * <code>WebApplicationContext</code> provided by
 * <code>SpringBodyTagSupport</code> and uses reflection to determine and
 * fulfill dependencies.</p>
 * 
 * <p>To use, subclass <code>SproutTag</code> and declare dependencies as you
 * would normally in a Spring-aware environment. (Setter only.)</p>
 * 
 * @see javax.servlet.jsp.tagext.BodyTagSupport
 * @see org.springframework.web.context.WebApplicationContext
 * @see com.echothree.view.client.web.struts.sprout.support.SpringBodyTagSupport
 * @author Seth Fitzsimmons
 */
public abstract class SproutTag
        extends SpringBodyTagSupport {
    private static final Logger log = Logger.getLogger( SproutTag.class );

    /**
     * <p>Delegates initialization to SproutUtils.</p>
     * 
     * <p>If a tag has already been instantiated and initialized, it should
     * already have had dependencies satisfied.</p>
     */
    @Override
    protected void onInit() {
        super.onInit();
        
        log.debug("Initializing SproutTag.");
        SproutUtils.initialize( this, getWebApplicationContext(), SproutTag.class );
    }
}
