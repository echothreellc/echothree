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

package com.echothree.ui.web.main.action.configuration.geocodetype;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetGeoCodeTypeChoicesResult;
import com.echothree.model.control.geo.common.choice.GeoCodeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GeoCodeTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private GeoCodeTypeChoicesBean parentGeoCodeTypeChoices;

    private String geoCodeTypeName;
    private String parentGeoCodeTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupParentGeoCodeTypeChoices()
            throws NamingException {
        if(parentGeoCodeTypeChoices == null) {
            var form = GeoUtil.getHome().getGetGeoCodeTypeChoicesForm();

            form.setDefaultGeoCodeTypeChoice(parentGeoCodeTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = GeoUtil.getHome().getGeoCodeTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGeoCodeTypeChoicesResult = (GetGeoCodeTypeChoicesResult)executionResult.getResult();
            parentGeoCodeTypeChoices = getGeoCodeTypeChoicesResult.getGeoCodeTypeChoices();

            if(parentGeoCodeTypeChoice == null) {
                parentGeoCodeTypeChoice = parentGeoCodeTypeChoices.getDefaultValue();
            }
        }
    }

   public void setGeoCodeTypeName(String geoCodeTypeName) {
        this.geoCodeTypeName = geoCodeTypeName;
    }
    
    public String getGeoCodeTypeName() {
        return geoCodeTypeName;
    }
    
    public String getParentGeoCodeTypeChoice()
            throws NamingException {
        setupParentGeoCodeTypeChoices();
        return parentGeoCodeTypeChoice;
    }

    public void setParentGeoCodeTypeChoice(String parentGeoCodeTypeChoice) {
        this.parentGeoCodeTypeChoice = parentGeoCodeTypeChoice;
    }

    public List<LabelValueBean> getParentGeoCodeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupParentGeoCodeTypeChoices();
        if(parentGeoCodeTypeChoices != null)
            choices = convertChoices(parentGeoCodeTypeChoices);

        return choices;
    }

     public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }

}
