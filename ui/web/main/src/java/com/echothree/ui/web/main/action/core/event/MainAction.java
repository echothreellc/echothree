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

package com.echothree.ui.web.main.action.core.event;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEventsResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.data.core.common.EventConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Core/Event/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/event/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = CoreUtil.getHome().getGetEventsForm();

        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setUuid(request.getParameter(ParameterConstants.UUID));
        commandForm.setCreatedByEntityRef(request.getParameter(ParameterConstants.CREATED_BY_ENTITY_REF));
        commandForm.setCreatedByUuid(request.getParameter(ParameterConstants.CREATED_BY_UUID));

        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.EntityInstanceIncludeEntityVisit);
        options.add(CoreOptions.EntityInstanceIncludeUuidIfAvailable);
        options.add(CoreOptions.EntityVisitIncludeVisitedTime);
        commandForm.setOptions(options);

        var offsetParameter = request.getParameter(new ParamEncoder("event").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 20;

        Map<String, Limit> limits = new HashMap<>();
        limits.put(EventConstants.ENTITY_TYPE_NAME, new Limit("20", offset == null ? null : offset.toString()));
        commandForm.setLimits(limits);

        var commandResult = CoreUtil.getHome().getEvents(getUserVisitPK(request), commandForm);
        
        if(commandResult.hasErrors()) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEventsResult)executionResult.getResult();
            var entityInstance = result.getEntityInstance();
            var createdByEntityInstance = result.getCreatedByEntityInstance();

            if(entityInstance != null) {
                request.setAttribute(AttributeConstants.ENTITY_INSTANCE, entityInstance);
            }
            if(createdByEntityInstance != null) {
                request.setAttribute(AttributeConstants.CREATED_BY_ENTITY_INSTANCE, createdByEntityInstance);
            }
            request.setAttribute(AttributeConstants.EVENT_COUNT, toIntExact(result.getEventCount()));
            request.setAttribute(AttributeConstants.EVENTS, result.getEvents());
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
