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

package com.echothree.ui.web.main.action.core.ratingtype;


import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.result.GetSequenceChoicesResult;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="RatingTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceChoicesBean ratingSequenceChoices;
    
    private String componentVendorName;
    private String entityTypeName;
    private String ratingTypeName;
    private String ratingSequenceChoice;
    private String sortOrder;
    private String description;
    
    public void setupRatingSequenceChoices()
            throws NamingException {
        if(ratingSequenceChoices == null) {
            var form = SequenceUtil.getHome().getGetSequenceChoicesForm();

            form.setSequenceTypeName(SequenceTypes.RATING.name());
            form.setDefaultSequenceChoice(ratingSequenceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SequenceUtil.getHome().getSequenceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSequenceChoicesResult)executionResult.getResult();
            ratingSequenceChoices = result.getSequenceChoices();

            if(ratingSequenceChoice == null)
                ratingSequenceChoice = ratingSequenceChoices.getDefaultValue();
        }
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }

    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public void setRatingTypeName(String ratingTypeName) {
        this.ratingTypeName = ratingTypeName;
    }
    
    public String getRatingTypeName() {
        return ratingTypeName;
    }
    
    public List<LabelValueBean> getRatingSequenceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupRatingSequenceChoices();
        if(ratingSequenceChoices != null)
            choices = convertChoices(ratingSequenceChoices);
        
        return choices;
    }
    
    public void setRatingSequenceChoice(String ratingSequenceChoice) {
        this.ratingSequenceChoice = ratingSequenceChoice;
    }
    
    public String getRatingSequenceChoice()
            throws NamingException {
        setupRatingSequenceChoices();
        return ratingSequenceChoice;
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
    
}
