// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.entitygeopointattribute;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EntityGeoPointAttributeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean elevationUnitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean altitudeUnitOfMeasureTypeChoices;
    
    private String entityAttributeName;
    private String entityRef;
    private String returnUrl;
    private String latitude;
    private String longitude;
    private String elevation;
    private String elevationUnitOfMeasureTypeChoice;
    private String altitude;
    private String altitudeUnitOfMeasureTypeChoice;
    
    private void setupElevationUnitOfMeasureTypeChoices()
            throws NamingException {
        if(elevationUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(elevationUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_ELEVATION);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            elevationUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(elevationUnitOfMeasureTypeChoice == null) {
                elevationUnitOfMeasureTypeChoice = elevationUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupAltitudeUnitOfMeasureTypeChoices()
            throws NamingException {
        if(altitudeUnitOfMeasureTypeChoices == null) {
            var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();

            form.setDefaultUnitOfMeasureTypeChoice(altitudeUnitOfMeasureTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));
            form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_ALTITUDE);

            var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
            altitudeUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();

            if(altitudeUnitOfMeasureTypeChoice == null) {
                altitudeUnitOfMeasureTypeChoice = altitudeUnitOfMeasureTypeChoices.getDefaultValue();
            }
        }
    }
    
    public String getEntityAttributeName() {
        return entityAttributeName;
    }
    
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }
    
    public String getEntityRef() {
        return entityRef;
    }
    
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }
    
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getElevation() {
        return elevation;
    }
    
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
    
    public List<LabelValueBean> getElevationUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupElevationUnitOfMeasureTypeChoices();
        if(elevationUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(elevationUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getElevationUnitOfMeasureTypeChoice()
            throws NamingException {
        setupElevationUnitOfMeasureTypeChoices();
        return elevationUnitOfMeasureTypeChoice;
    }
    
    public void setElevationUnitOfMeasureTypeChoice(String elevationUnitOfMeasureTypeChoice) {
        this.elevationUnitOfMeasureTypeChoice = elevationUnitOfMeasureTypeChoice;
    }
    
    public String getAltitude() {
        return altitude;
    }
    
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
    
    public List<LabelValueBean> getAltitudeUnitOfMeasureTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupAltitudeUnitOfMeasureTypeChoices();
        if(altitudeUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(altitudeUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getAltitudeUnitOfMeasureTypeChoice()
            throws NamingException {
        setupAltitudeUnitOfMeasureTypeChoices();
        return altitudeUnitOfMeasureTypeChoice;
    }
    
    public void setAltitudeUnitOfMeasureTypeChoice(String altitudeUnitOfMeasureTypeChoice) {
        this.altitudeUnitOfMeasureTypeChoice = altitudeUnitOfMeasureTypeChoice;
    }
    
}
