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

package com.echothree.ui.web.main.action.advertising.offeruse;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="OfferUseEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean salesOrderSequenceChoices;
    
    private String offerName;
    private String useName;
    private String salesOrderSequenceChoice;
    
    public void setupSalesOrderSequenceChoices()
            throws NamingException {
        if(salesOrderSequenceChoices == null) {
            var form = SequenceUtil.getHome().getGetSequenceChoicesForm();

            form.setSequenceTypeName(SequenceTypes.SALES_ORDER.name());
            form.setDefaultSequenceChoice(salesOrderSequenceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSequenceChoicesResult)executionResult.getResult();
            salesOrderSequenceChoices = result.getSequenceChoices();

            if(salesOrderSequenceChoice == null)
                salesOrderSequenceChoice = salesOrderSequenceChoices.getDefaultValue();
        }
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getUseName() {
        return useName;
    }
    
    public void setUseName(String useName) {
        this.useName = useName;
    }
    
    public List<LabelValueBean> getSalesOrderSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSalesOrderSequenceChoices();
        if(salesOrderSequenceChoices != null)
            choices = convertChoices(salesOrderSequenceChoices);
        
        return choices;
    }
    
    public void setSalesOrderSequenceChoice(String salesOrderSequenceChoice) {
        this.salesOrderSequenceChoice = salesOrderSequenceChoice;
    }
    
    public String getSalesOrderSequenceChoice()
            throws NamingException {
        setupSalesOrderSequenceChoices();
        return salesOrderSequenceChoice;
    }
    
}
