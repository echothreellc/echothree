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

package com.echothree.ui.web.main.action.core.entitytag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.GetEntityTagsResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.tag.common.transfer.EntityTagTransfer;
import com.echothree.model.control.tag.common.transfer.TagTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/EntityTag/Search",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Review", path = "/action/Core/TagScope/Review", redirect = true),
        @SproutForward(name = "Search", path = "/core/entitytag/search.jsp")
    }
)
public class SearchAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = TagUtil.getHome().getGetEntityTagsForm();

        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setTagScopeName(request.getParameter(ParameterConstants.TAG_SCOPE_NAME));
        commandForm.setTagName(request.getParameter(ParameterConstants.TAG_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        var commandResult = TagUtil.getHome().getEntityTags(getUserVisitPK(request), commandForm);
        EntityInstanceTransfer taggedEntityInstance = null;
        TagTransfer tag = null;
        List<EntityTagTransfer> entityTags = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEntityTagsResult)executionResult.getResult();

            taggedEntityInstance = result.getTaggedEntityInstance();
            tag = result.getTag();
            entityTags = result.getEntityTags();
        }
        
        if(entityTags == null) {
            checkSecurityRoles(request, "TagScope.Review");

            if(hasSecurityRole(request, "TagScope.Review")) {
                forwardKey = ForwardConstants.REVIEW;
            } else {
                forwardKey = ForwardConstants.ERROR_404;
            }
        } else {
            if(taggedEntityInstance != null) {
                request.setAttribute(AttributeConstants.TAGGED_ENTITY_INSTANCE, taggedEntityInstance);
            }

            if(tag != null) {
                request.setAttribute(AttributeConstants.TAG, tag);
            }

            request.setAttribute(AttributeConstants.ENTITY_TAGS, new ListWrapper<>(entityTags));
            forwardKey = ForwardConstants.SEARCH;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);

            parameters.put(ParameterConstants.TAG_SCOPE_NAME, commandForm.getTagScopeName());
            customActionForward.setParameters(parameters);
        }

        return customActionForward;

    }
    
}
