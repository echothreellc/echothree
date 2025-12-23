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

package com.echothree.ui.web.cms.action.v1;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.ui.web.cms.framework.CmsBaseJsonAction;
import com.echothree.ui.web.cms.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import com.echothree.view.client.web.taglib.BaseTag;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/v1/GetEntityListItem",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class GetEntityListItemAction
        extends CmsBaseJsonAction {

    /** Creates a new instance of GetEntityListItemAction */
    public GetEntityListItemAction() {
        super(true, false);
    }

    @Override
    protected CommandResult getCommandResult(HttpServletRequest request)
            throws Exception {
        var commandForm = CoreUtil.getHome().getGetEntityListItemForm();

        commandForm.setComponentVendorName(request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME));
        commandForm.setEntityTypeName(request.getParameter(ParameterConstants.ENTITY_TYPE_NAME));
        commandForm.setEntityAttributeName(request.getParameter(ParameterConstants.ENTITY_ATTRIBUTE_NAME));
        commandForm.setEntityListItemName(request.getParameter(ParameterConstants.ENTITY_LIST_ITEM_NAME));

        BaseTag.setOptions(request.getParameter(ParameterConstants.OPTIONS), null, commandForm);

        var commandResult = CoreUtil.getHome().getEntityListItem(getUserVisitPK(request), commandForm);
        
        return commandResult;
    }

}