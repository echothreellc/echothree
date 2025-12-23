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

package com.echothree.ui.web.main.action.configuration.scale;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetServerChoicesResult;
import com.echothree.control.user.core.common.result.GetServiceChoicesResult;
import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.result.GetScaleTypeChoicesResult;
import com.echothree.model.control.core.common.choice.ServerChoicesBean;
import com.echothree.model.control.core.common.choice.ServiceChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ScaleAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ScaleTypeChoicesBean scaleTypeChoices;
    private ServerChoicesBean serverChoices;
    private ServiceChoicesBean serviceChoices;

    private String scaleName;
    private String scaleTypeChoice;
    private String serverChoice;
    private String serviceChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupScaleTypeChoices() {
        if(scaleTypeChoices == null) {
            try {
                var commandForm = ScaleUtil.getHome().getGetScaleTypeChoicesForm();

                commandForm.setDefaultScaleTypeChoice(scaleTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = ScaleUtil.getHome().getScaleTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getScaleTypeChoicesResult = (GetScaleTypeChoicesResult)executionResult.getResult();
                scaleTypeChoices = getScaleTypeChoicesResult.getScaleTypeChoices();

                if(scaleTypeChoice == null) {
                    scaleTypeChoice = scaleTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, scaleTypeChoices remains null, no default
            }
        }
    }

    private void setupServerChoices() {
        if(serverChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetServerChoicesForm();

                commandForm.setDefaultServerChoice(serverChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = CoreUtil.getHome().getServerChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getServerChoicesResult = (GetServerChoicesResult)executionResult.getResult();
                serverChoices = getServerChoicesResult.getServerChoices();

                if(serverChoice == null) {
                    serverChoice = serverChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, serverChoices remains null, no default
            }
        }
    }

    private void setupServiceChoices() {
        if(serviceChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetServiceChoicesForm();

                commandForm.setDefaultServiceChoice(serviceChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = CoreUtil.getHome().getServiceChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getServiceChoicesResult = (GetServiceChoicesResult)executionResult.getResult();
                serviceChoices = getServiceChoicesResult.getServiceChoices();

                if(serviceChoice == null) {
                    serviceChoice = serviceChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, serviceChoices remains null, no default
            }
        }
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }
    
    public String getScaleName() {
        return scaleName;
    }
    
    public List<LabelValueBean> getScaleTypeChoices() {
        List<LabelValueBean> choices = null;

        setupScaleTypeChoices();
        if(scaleTypeChoices != null) {
            choices = convertChoices(scaleTypeChoices);
        }

        return choices;
    }

    public void setScaleTypeChoice(String scaleTypeChoice) {
        this.scaleTypeChoice = scaleTypeChoice;
    }

    public String getScaleTypeChoice() {
        setupScaleTypeChoices();

        return scaleTypeChoice;
    }

    public List<LabelValueBean> getServerChoices() {
        List<LabelValueBean> choices = null;

        setupServerChoices();
        if(serverChoices != null) {
            choices = convertChoices(serverChoices);
        }

        return choices;
    }

    public void setServerChoice(String serverChoice) {
        this.serverChoice = serverChoice;
    }

    public String getServerChoice() {
        setupServerChoices();

        return serverChoice;
    }

    public List<LabelValueBean> getServiceChoices() {
        List<LabelValueBean> choices = null;

        setupServiceChoices();
        if(serviceChoices != null) {
            choices = convertChoices(serviceChoices);
        }

        return choices;
    }

    public void setServiceChoice(String serviceChoice) {
        this.serviceChoice = serviceChoice;
    }

    public String getServiceChoice() {
        setupServiceChoices();

        return serviceChoice;
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
