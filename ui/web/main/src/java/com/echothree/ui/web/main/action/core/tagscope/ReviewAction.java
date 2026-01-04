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

package com.echothree.ui.web.main.action.core.tagscope;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.GetTagScopeResult;
import com.echothree.model.control.tag.common.TagOptions;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/TagScope/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/tagscope/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = TagUtil.getHome().getGetTagScopeForm();

        Set<String> options = new HashSet<>();
        options.add(TagOptions.TagScopeIncludeTags);
        options.add(TagOptions.TagIncludeUsageCount);
        commandForm.setOptions(options);

        commandForm.setTagScopeName(request.getParameter(ParameterConstants.TAG_SCOPE_NAME));

        var commandResult = TagUtil.getHome().getTagScope(getUserVisitPK(request), commandForm);
        TagScopeTransfer tagScope = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTagScopeResult)executionResult.getResult();
            
            tagScope = result.getTagScope();
        }
        
        if(tagScope == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            var minimumUsageCount = Long.MAX_VALUE;
            long maximumUsageCount = 0;

            for(var tag : tagScope.getTags().getList()) {
                long usageCount = tag.getUsageCount();

                if(usageCount < minimumUsageCount) {
                    minimumUsageCount = usageCount;
                }

                if(usageCount > maximumUsageCount) {
                    maximumUsageCount = usageCount;
                }
            }

            request.setAttribute(AttributeConstants.TAG_SCOPE, tagScope);
            request.setAttribute(AttributeConstants.MINIMUM_USAGE_COUNT, minimumUsageCount);
            request.setAttribute(AttributeConstants.MAXIMUM_USAGE_COUNT, maximumUsageCount);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
