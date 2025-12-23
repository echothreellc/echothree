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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.ui.web.cms.framework.CmsBaseJsonAction;
import com.echothree.ui.web.cms.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/v1/SearchContentCategories",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class SearchContentCategoriesAction
        extends CmsBaseJsonAction {

    /** Creates a new instance of SearchContentCategoriesAction */
    public SearchContentCategoriesAction() {
        super(true, false);
    }

    @Override
    protected CommandResult getCommandResult(HttpServletRequest request)
            throws Exception {
        var commandForm = SearchUtil.getHome().getSearchContentCategoriesForm();

        commandForm.setSearchDefaultOperatorName(request.getParameter(ParameterConstants.SEARCH_DEFAULT_ORDERATOR_NAME));
        commandForm.setSearchSortDirectionName(request.getParameter(ParameterConstants.SEARCH_SORT_DIRECTION_NAME));
        commandForm.setSearchTypeName(request.getParameter(ParameterConstants.SEARCH_TYPE_NAME));
        commandForm.setSearchSortOrderName(request.getParameter(ParameterConstants.SEARCH_SORT_ORDER_NAME));
        commandForm.setQ(request.getParameter(ParameterConstants.Q));
        commandForm.setCreatedSince(request.getParameter(ParameterConstants.CREATED_SINCE));
        commandForm.setModifiedSince(request.getParameter(ParameterConstants.MODIFIED_SINCE));
        commandForm.setFields(request.getParameter(ParameterConstants.FIELDS));
        commandForm.setRememberPreferences(request.getParameter(ParameterConstants.REMEMBER_PREFERENCES));

        return SearchUtil.getHome().searchContentCategories(getUserVisitPK(request), commandForm);
    }

}
