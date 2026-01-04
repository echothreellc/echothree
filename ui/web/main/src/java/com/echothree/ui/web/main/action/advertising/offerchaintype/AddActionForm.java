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

package com.echothree.ui.web.main.action.advertising.offerchaintype;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainChoicesResult;
import com.echothree.model.control.chain.common.choice.ChainChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="OfferChainTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ChainChoicesBean chainChoices;
    
    private String offerName;
    private String chainKindName;
    private String chainTypeName;
    private String chainChoice;
    
    public void setupChainChoices()
            throws NamingException {
        if(chainChoices == null) {
            var form = ChainUtil.getHome().getGetChainChoicesForm();

            form.setChainKindName(chainKindName);
            form.setChainTypeName(chainTypeName);
            form.setDefaultChainChoice(chainChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ChainUtil.getHome().getChainChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetChainChoicesResult)executionResult.getResult();
            chainChoices = result.getChainChoices();

            if(chainChoice == null) {
                chainChoice = chainChoices.getDefaultValue();
            }
        }
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getChainKindName() {
        return chainKindName;
    }
    
    public void setChainKindName(String chainKindName) {
        this.chainKindName = chainKindName;
    }
    
    public String getChainTypeName() {
        return chainTypeName;
    }
    
    public void setChainTypeName(String chainTypeName) {
        this.chainTypeName = chainTypeName;
    }
    
    public List<LabelValueBean> getChainChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupChainChoices();
        if(chainChoices != null) {
            choices = convertChoices(chainChoices);
        }
        
        return choices;
    }
    
    public void setChainChoice(String chainChoice) {
        this.chainChoice = chainChoice;
    }
    
    public String getChainChoice()
            throws NamingException {
        setupChainChoices();
        
        return chainChoice;
    }
    
}
