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

package com.echothree.ui.web.main.action.core.cacheentry;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetCacheEntriesResult;
import com.echothree.model.data.core.common.CacheEntryConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Core/CacheEntry/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/cacheentry/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = CoreUtil.getHome().getGetCacheEntriesForm();

        var offsetParameter = request.getParameter(new ParamEncoder("cacheEntry").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

        Map<String, Limit> limits = new HashMap<>();
        limits.put(CacheEntryConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
        commandForm.setLimits(limits);

        var commandResult = CoreUtil.getHome().getCacheEntries(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCacheEntriesResult)executionResult.getResult();

        var cacheEntryCount = result.getCacheEntryCount();
        if(cacheEntryCount != null) {
            request.setAttribute(AttributeConstants.CACHE_ENTRY_COUNT, toIntExact(cacheEntryCount));
        }

        request.setAttribute(AttributeConstants.CACHE_ENTRIES, result.getCacheEntries());

        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}
