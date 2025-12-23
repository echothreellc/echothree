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

package com.echothree.ui.web.main.action.warehouse.warehousetype;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetWarehouseTypesResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.data.warehouse.common.WarehouseTypeConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
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
    path = "/Warehouse/WarehouseType/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/warehouse/warehousetype/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    final private int pageSize = 20;

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var commandForm = WarehouseUtil.getHome().getGetWarehouseTypesForm();
        var results = request.getParameter(ParameterConstants.RESULTS);

        commandForm.setOptions(Set.of(
                CoreOptions.EntityInstanceIncludeEntityAppearance,
                CoreOptions.EntityInstanceIncludeEntityVisit,
                CoreOptions.AppearanceIncludeTextDecorations,
                CoreOptions.AppearanceIncludeTextTransformations
                ));

        if(results == null) {
            var offsetParameter = request.getParameter(new ParamEncoder(AttributeConstants.WAREHOUSE_TYPE).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
            var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * pageSize;
            commandForm.setLimits(Map.of(
                    WarehouseTypeConstants.ENTITY_TYPE_NAME, new Limit(Integer.toString(pageSize), offset == null ? null : offset.toString())
            ));
        }

        var commandResult = WarehouseUtil.getHome().getWarehouseTypes(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWarehouseTypesResult)executionResult.getResult();

            var warehouseCount = result.getWarehouseTypeCount();
            if(warehouseCount != null) {
                request.setAttribute(AttributeConstants.WAREHOUSE_TYPE_COUNT, toIntExact(warehouseCount));
            }

            request.setAttribute(AttributeConstants.WAREHOUSE_TYPES, new ListWrapper<>(result.getWarehouseTypes()));
        }

        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}
