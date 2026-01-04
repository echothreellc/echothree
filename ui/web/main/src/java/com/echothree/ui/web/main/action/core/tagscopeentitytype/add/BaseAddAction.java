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

package com.echothree.ui.web.main.action.core.tagscopeentitytype.add;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.GetTagScopeResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseAddAction
        extends MainBaseAction<ActionForm> {
    
    public void setupTagScopeTransfer(HttpServletRequest request, String tagScopeName)
            throws NamingException {
        var commandForm = TagUtil.getHome().getGetTagScopeForm();
        
        commandForm.setTagScopeName(tagScopeName);

        var commandResult = TagUtil.getHome().getTagScope(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetTagScopeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TAG_SCOPE, result.getTagScope());
    }
    
}
