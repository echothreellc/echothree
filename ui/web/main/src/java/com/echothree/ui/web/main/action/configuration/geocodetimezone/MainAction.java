// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.geocodetimezone;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.form.GetGeoCodeTimeZonesForm;
import com.echothree.control.user.geo.common.result.GetGeoCodeTimeZonesResult;
import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/GeoCodeTimeZone/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/geocodetimezone/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        try {
            String geoCodeName = request.getParameter(ParameterConstants.GEO_CODE_NAME);
            GetGeoCodeTimeZonesForm commandForm = GeoUtil.getHome().getGetGeoCodeTimeZonesForm();
            
            commandForm.setGeoCodeName(geoCodeName);
            
            CommandResult commandResult = GeoUtil.getHome().getGeoCodeTimeZones(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetGeoCodeTimeZonesResult result = (GetGeoCodeTimeZonesResult)executionResult.getResult();
            GeoCodeTransfer geoCode = result.getGeoCode();
            
            if(geoCode == null) {
                forwardKey = ForwardConstants.ERROR_404;
            } else {
                request.setAttribute(AttributeConstants.GEO_CODE, geoCode);
                request.setAttribute(AttributeConstants.GEO_CODE_TIME_ZONES, result.getGeoCodeTimeZones());
                forwardKey = ForwardConstants.DISPLAY;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}