// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.geo.common.edit.GeoCodeTimeZoneEdit;
import com.echothree.control.user.geo.common.form.EditGeoCodeTimeZoneForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeTimeZoneResult;
import com.echothree.control.user.geo.common.spec.GeoCodeTimeZoneSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/GeoCodeTimeZone/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GeoCodeTimeZoneEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/GeoCodeTimeZone/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/geocodetimezone/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String geoCodeName = request.getParameter(ParameterConstants.GEO_CODE_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditGeoCodeTimeZoneForm commandForm = GeoUtil.getHome().getEditGeoCodeTimeZoneForm();
                GeoCodeTimeZoneSpec spec = GeoUtil.getHome().getGeoCodeTimeZoneSpec();
                String javaTimeZoneName = request.getParameter(ParameterConstants.JAVA_TIME_ZONE_NAME);
                
                if(geoCodeName == null)
                    geoCodeName = actionForm.getGeoCodeName();
                if(javaTimeZoneName == null)
                    javaTimeZoneName = actionForm.getJavaTimeZoneName();
                
                commandForm.setSpec(spec);
                spec.setGeoCodeName(geoCodeName);
                spec.setJavaTimeZoneName(javaTimeZoneName);
                
                if(wasPost(request)) {
                    GeoCodeTimeZoneEdit edit = GeoUtil.getHome().getGeoCodeTimeZoneEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    
                    CommandResult commandResult = GeoUtil.getHome().editGeoCodeTimeZone(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditGeoCodeTimeZoneResult result = (EditGeoCodeTimeZoneResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = GeoUtil.getHome().editGeoCodeTimeZone(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditGeoCodeTimeZoneResult result = (EditGeoCodeTimeZoneResult)executionResult.getResult();
                    
                    if(result != null) {
                        GeoCodeTimeZoneEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setGeoCodeName(geoCodeName);
                            actionForm.setJavaTimeZoneName(javaTimeZoneName);
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.GEO_CODE_NAME, geoCodeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.GEO_CODE_NAME, geoCodeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
