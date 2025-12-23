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

package com.echothree.ui.web.main.action.core.entitytype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityTypeResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.data.core.common.EntityInstanceConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/EntityType/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/entitytype/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = CoreUtil.getHome().getGetEntityTypeForm();

        commandForm.setComponentVendorName(request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME));
        commandForm.setEntityTypeName(request.getParameter(ParameterConstants.ENTITY_TYPE_NAME));

        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityTypeIncludeEntityAttributes);
        options.add(CoreOptions.EntityTypeIncludeCommentTypes);
        options.add(CoreOptions.EntityTypeIncludeRatingTypes);
        options.add(CoreOptions.EntityTypeIncludeMessageTypes);
        options.add(CoreOptions.EntityTypeIncludeEntityInstancesCount);
        options.add(CoreOptions.EntityTypeIncludeEntityInstances);
        options.add(CoreOptions.EntityInstanceIncludeEntityAppearance);
        options.add(CoreOptions.AppearanceIncludeTextDecorations);
        options.add(CoreOptions.AppearanceIncludeTextTransformations);
        commandForm.setOptions(options);

        Map<String, Limit> limits = new HashMap<>();
        limits.put(EntityInstanceConstants.ENTITY_TYPE_NAME, new Limit("10"));
        commandForm.setLimits(limits);

        var commandResult = CoreUtil.getHome().getEntityType(getUserVisitPK(request), commandForm);

        EntityTypeTransfer entityType = null;
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEntityTypeResult)executionResult.getResult();
            
            entityType = result.getEntityType();
        }

        String forwardKey;
        if(entityType == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.ENTITY_TYPE, entityType);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
