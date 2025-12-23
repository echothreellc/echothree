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

package com.echothree.ui.web.main.action.warehouse.location;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.result.GetLocationStatusChoicesResult;
import com.echothree.model.control.warehouse.common.choice.LocationStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LocationStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private LocationStatusChoicesBean locationStatusChoices;
    
    private String warehouseName;
    private String locationName;
    private String locationStatusChoice;
    
    public void setupLocationStatusChoices()
            throws NamingException {
        if(locationStatusChoices == null) {
            var form = WarehouseUtil.getHome().getGetLocationStatusChoicesForm();

            form.setWarehouseName(warehouseName);
            form.setLocationName(locationName);
            form.setDefaultLocationStatusChoice(locationStatusChoice);

            var commandResult = WarehouseUtil.getHome().getLocationStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getLocationStatusChoicesResult = (GetLocationStatusChoicesResult)executionResult.getResult();
            locationStatusChoices = getLocationStatusChoicesResult.getLocationStatusChoices();

            if(locationStatusChoice == null)
                locationStatusChoice = locationStatusChoices.getDefaultValue();
        }
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getLocationStatusChoice() {
        return locationStatusChoice;
    }
    
    public void setLocationStatusChoice(String locationStatusChoice) {
        this.locationStatusChoice = locationStatusChoice;
    }
    
    public List<LabelValueBean> getLocationStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLocationStatusChoices();
        if(locationStatusChoices != null)
            choices = convertChoices(locationStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
