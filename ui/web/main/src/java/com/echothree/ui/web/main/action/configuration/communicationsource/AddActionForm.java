// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.communicationsource;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetServerChoicesForm;
import com.echothree.control.user.core.common.result.GetServerChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.form.GetSelectorChoicesForm;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.control.user.workeffort.common.WorkEffortUtil;
import com.echothree.control.user.workeffort.common.form.GetWorkEffortScopeChoicesForm;
import com.echothree.control.user.workeffort.common.result.GetWorkEffortScopeChoicesResult;
import com.echothree.model.control.core.common.choice.ServerChoicesBean;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.model.control.workeffort.common.choice.WorkEffortScopeChoicesBean;
import com.echothree.model.control.workeffort.common.workeffort.ReceiveCustomerEmailConstants;
import com.echothree.model.control.workeffort.common.workeffort.SendCustomerEmailConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CommunicationSourceAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ServerChoicesBean serverChoices;
    private WorkEffortScopeChoicesBean receiveWorkEffortScopeChoices;
    private WorkEffortScopeChoicesBean sendWorkEffortScopeChoices;
    private SelectorChoicesBean reviewEmployeeSelectorChoices;
    
    private String communicationSourceName;
    private String sortOrder;
    private String serverChoice;
    private String username;
    private String password;
    private String receiveWorkEffortScopeChoice;
    private String sendWorkEffortScopeChoice;
    private String reviewEmployeeSelectorChoice;
    private String description;
    
    public void setupServerChoices() {
        if(serverChoices == null) {
            try {
                GetServerChoicesForm form = CoreUtil.getHome().getGetServerChoicesForm();
                
                form.setDefaultServerChoice(serverChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = CoreUtil.getHome().getServerChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetServerChoicesResult result = (GetServerChoicesResult)executionResult.getResult();
                serverChoices = result.getServerChoices();
                
                if(serverChoice == null) {
                    serverChoice = serverChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, serverChoices remains null, no default
            }
        }
    }
    
    public void setupReceiveWorkEffortScopeChoices() {
        if(receiveWorkEffortScopeChoices == null) {
            try {
                GetWorkEffortScopeChoicesForm form = WorkEffortUtil.getHome().getGetWorkEffortScopeChoicesForm();
                
                form.setWorkEffortTypeName(ReceiveCustomerEmailConstants.WorkEffortType_RECEIVE_CUSTOMER_EMAIL);
                form.setDefaultWorkEffortScopeChoice(receiveWorkEffortScopeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = WorkEffortUtil.getHome().getWorkEffortScopeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetWorkEffortScopeChoicesResult result = (GetWorkEffortScopeChoicesResult)executionResult.getResult();
                receiveWorkEffortScopeChoices = result.getWorkEffortScopeChoices();
                
                if(receiveWorkEffortScopeChoice == null) {
                    receiveWorkEffortScopeChoice = receiveWorkEffortScopeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, receiveWorkEffortScopeChoices remains null, no default
            }
        }
    }
    
    public void setupSendWorkEffortScopeChoices() {
        if(sendWorkEffortScopeChoices == null) {
            try {
                GetWorkEffortScopeChoicesForm form = WorkEffortUtil.getHome().getGetWorkEffortScopeChoicesForm();
                
                form.setWorkEffortTypeName(SendCustomerEmailConstants.WorkEffortType_SEND_CUSTOMER_EMAIL);
                form.setDefaultWorkEffortScopeChoice(sendWorkEffortScopeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = WorkEffortUtil.getHome().getWorkEffortScopeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetWorkEffortScopeChoicesResult result = (GetWorkEffortScopeChoicesResult)executionResult.getResult();
                sendWorkEffortScopeChoices = result.getWorkEffortScopeChoices();
                
                if(sendWorkEffortScopeChoice == null) {
                    sendWorkEffortScopeChoice = sendWorkEffortScopeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, sendWorkEffortScopeChoices remains null, no default
            }
        }
    }
    
    public void setupReviewEmployeeSelectorChoices() {
        if(reviewEmployeeSelectorChoices == null) {
            try {
                GetSelectorChoicesForm form = SelectorUtil.getHome().getGetSelectorChoicesForm();
                
                form.setSelectorKindName(SelectorKinds.EMPLOYEE.name());
                form.setSelectorTypeName(SelectorTypes.EMAIL_REVIEW.name());
                form.setDefaultSelectorChoice(reviewEmployeeSelectorChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSelectorChoicesResult result = (GetSelectorChoicesResult)executionResult.getResult();
                reviewEmployeeSelectorChoices = result.getSelectorChoices();
                
                if(reviewEmployeeSelectorChoice == null) {
                    reviewEmployeeSelectorChoice = reviewEmployeeSelectorChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, reviewEmployeeSelectorChoices remains null, no default
            }
        }
    }
    
    public void setCommunicationSourceName(String communicationSourceName) {
        this.communicationSourceName = communicationSourceName;
    }
    
    public String getCommunicationSourceName() {
        return communicationSourceName;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getServerChoice() {
        return serverChoice;
    }
    
    public void setServerChoice(String serverChoice) {
        this.serverChoice = serverChoice;
    }
    
    public List<LabelValueBean> getServerChoices() {
        List<LabelValueBean> choices = null;
        
        setupServerChoices();
        if(serverChoices != null) {
            choices = convertChoices(serverChoices);
        }
        
        return choices;
    }
    
    public String getReceiveWorkEffortScopeChoice() {
        return receiveWorkEffortScopeChoice;
    }
    
    public void setReceiveWorkEffortScopeChoice(String receiveWorkEffortScopeChoice) {
        this.receiveWorkEffortScopeChoice = receiveWorkEffortScopeChoice;
    }
    
    public List<LabelValueBean> getReceiveWorkEffortScopeChoices() {
        List<LabelValueBean> choices = null;
        
        setupReceiveWorkEffortScopeChoices();
        if(receiveWorkEffortScopeChoices != null) {
            choices = convertChoices(receiveWorkEffortScopeChoices);
        }
        
        return choices;
    }
    
    public String getSendWorkEffortScopeChoice() {
        return sendWorkEffortScopeChoice;
    }
    
    public void setSendWorkEffortScopeChoice(String sendWorkEffortScopeChoice) {
        this.sendWorkEffortScopeChoice = sendWorkEffortScopeChoice;
    }
    
    public List<LabelValueBean> getSendWorkEffortScopeChoices() {
        List<LabelValueBean> choices = null;
        
        setupSendWorkEffortScopeChoices();
        if(sendWorkEffortScopeChoices != null) {
            choices = convertChoices(sendWorkEffortScopeChoices);
        }
        
        return choices;
    }
    
    public String getReviewEmployeeSelectorChoice() {
        return reviewEmployeeSelectorChoice;
    }
    
    public void setReviewEmployeeSelectorChoice(String reviewEmployeeSelectorChoice) {
        this.reviewEmployeeSelectorChoice = reviewEmployeeSelectorChoice;
    }
    
    public List<LabelValueBean> getReviewEmployeeSelectorChoices() {
        List<LabelValueBean> choices = null;
        
        setupReviewEmployeeSelectorChoices();
        if(reviewEmployeeSelectorChoices != null) {
            choices = convertChoices(reviewEmployeeSelectorChoices);
        }
        
        return choices;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
