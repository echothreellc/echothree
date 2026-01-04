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

package com.echothree.view.client.web.struts;

import com.echothree.view.client.web.struts.sprout.SproutRequestProcessor;
import com.echothree.view.client.web.util.HttpSessionUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;

/**
 * A customized RequestProcessor that checks the user's preferred Locale
 * from the request each time. If a Locale is not in the session or
 * the one in the session doesn't match the request, the Locale in the
 * request is set to the session.
 */
public class CustomRequestProcessor
        extends SproutRequestProcessor {

    /** Creates a new instance of CustomRequestProcessor */
    public CustomRequestProcessor() {
        log = LogFactory.getLog(CustomRequestProcessor.class);
    }
    
    @Override
    protected boolean processPreprocess(HttpServletRequest request, HttpServletResponse response) {
        if(!super.processPreprocess(request, response)) {
            return false;
        }
        
        HttpSessionUtils.getInstance().setupUserVisit(request, response, false);
        
        return true;
    }

}