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

package com.echothree.ui.web.main.action.advertising.offeruse;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.form.GetUseChoicesForm;
import com.echothree.control.user.offer.common.result.GetUseChoicesResult;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="OfferUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UseChoicesBean useChoices;
    private SequenceChoicesBean salesOrderSequenceChoices;
    
    private String offerName;
    private String useChoice;
    private String salesOrderSequenceChoice;
    
    public void setupUseChoices() {
        if(useChoices == null) {
            try {
                GetUseChoicesForm form = OfferUtil.getHome().getGetUseChoicesForm();
                
                form.setDefaultUseChoice(useChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = OfferUtil.getHome().getUseChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUseChoicesResult result = (GetUseChoicesResult)executionResult.getResult();
                useChoices = result.getUseChoices();
                
                if(useChoice == null)
                    useChoice = useChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, useChoices remains null, no default
            }
        }
    }
    
    public void setupSalesOrderSequenceChoices() {
        if(salesOrderSequenceChoices == null) {
            try {
                GetSequenceChoicesForm form = SequenceUtil.getHome().getGetSequenceChoicesForm();
                
                form.setSequenceTypeName(SequenceTypes.SALES_ORDER.name());
                form.setDefaultSequenceChoice(salesOrderSequenceChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChoicesResult result = (GetSequenceChoicesResult)executionResult.getResult();
                salesOrderSequenceChoices = result.getSequenceChoices();
                
                if(salesOrderSequenceChoice == null)
                    salesOrderSequenceChoice = salesOrderSequenceChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, salesOrderSequenceChoices remains null, no default
            }
        }
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public List<LabelValueBean> getUseChoices() {
        List<LabelValueBean> choices = null;
        
        setupUseChoices();
        if(useChoices != null)
            choices = convertChoices(useChoices);
        
        return choices;
    }
    
    public void setUseChoice(String useChoice) {
        this.useChoice = useChoice;
    }
    
    public String getUseChoice() {
        setupUseChoices();
        return useChoice;
    }
    
    public List<LabelValueBean> getSalesOrderSequenceChoices() {
        List<LabelValueBean> choices = null;
        
        setupSalesOrderSequenceChoices();
        if(salesOrderSequenceChoices != null)
            choices = convertChoices(salesOrderSequenceChoices);
        
        return choices;
    }
    
    public void setSalesOrderSequenceChoice(String salesOrderSequenceChoice) {
        this.salesOrderSequenceChoice = salesOrderSequenceChoice;
    }
    
    public String getSalesOrderSequenceChoice() {
        setupSalesOrderSequenceChoices();
        return salesOrderSequenceChoice;
    }
    
}
