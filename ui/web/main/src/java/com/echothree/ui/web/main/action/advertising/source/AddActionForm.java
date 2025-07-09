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

package com.echothree.ui.web.main.action.advertising.source;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetOfferChoicesResult;
import com.echothree.control.user.offer.common.result.GetUseChoicesResult;
import com.echothree.model.control.offer.common.choice.OfferChoicesBean;
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SourceAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UseChoicesBean useChoices;
    private OfferChoicesBean offerChoices;

    private String sourceName;
    private String offerChoice;
    private String useChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    public void setupOfferChoices()
            throws NamingException {
        if(offerChoices == null) {
            var form = OfferUtil.getHome().getGetOfferChoicesForm();

            form.setDefaultOfferChoice(offerChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = OfferUtil.getHome().getOfferChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetOfferChoicesResult)executionResult.getResult();
            offerChoices = result.getOfferChoices();

            if(offerChoice == null)
                offerChoice = offerChoices.getDefaultValue();
        }
    }

    public void setupUseChoices()
            throws NamingException {
        if(useChoices == null) {
            var form = OfferUtil.getHome().getGetUseChoicesForm();

            form.setDefaultUseChoice(useChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = OfferUtil.getHome().getUseChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUseChoicesResult)executionResult.getResult();
            useChoices = result.getUseChoices();

            if(useChoice == null)
                useChoice = useChoices.getDefaultValue();
        }
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<LabelValueBean> getOfferChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupOfferChoices();
        if(offerChoices != null)
            choices = convertChoices(offerChoices);

        return choices;
    }

    public void setOfferChoice(String offerChoice) {
        this.offerChoice = offerChoice;
    }

    public String getOfferChoice()
            throws NamingException {
        setupOfferChoices();
        return offerChoice;
    }

    public List<LabelValueBean> getUseChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupUseChoices();
        if(useChoices != null)
            choices = convertChoices(useChoices);

        return choices;
    }

    public void setUseChoice(String useChoice) {
        this.useChoice = useChoice;
    }

    public String getUseChoice()
            throws NamingException {
        setupUseChoices();
        return useChoice;
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

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        isDefault = false;
    }

}
