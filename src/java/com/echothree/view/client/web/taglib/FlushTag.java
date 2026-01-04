// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;

public class FlushTag
        extends BaseTag {
    
    protected String key;
    
    private void init() {
        key = null;
    }
    
    /** Creates a new instance of FlushTag */
    public FlushTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag()
            throws JspException {
        try {
            var commandForm = CoreUtil.getHome().getRemoveCacheEntryForm();

            commandForm.setCacheEntryKey(key);

            var commandResult = CoreUtil.getHome().removeCacheEntry(getUserVisitPK(), commandForm);
            if(commandResult.hasErrors() && !commandResult.containsExecutionError(ExecutionErrors.UnknownCacheEntryKey.name())) {
                getLog().error(commandResult);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }

}
