// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.sequence.sequencetype;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.form.GetSequenceChecksumTypeChoicesForm;
import com.echothree.control.user.sequence.common.form.GetSequenceEncoderTypeChoicesForm;
import com.echothree.control.user.sequence.common.result.GetSequenceChecksumTypeChoicesResult;
import com.echothree.control.user.sequence.common.result.GetSequenceEncoderTypeChoicesResult;
import com.echothree.model.control.sequence.common.choice.SequenceChecksumTypeChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceEncoderTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SequenceTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SequenceEncoderTypeChoicesBean sequenceEncoderTypeChoices;
    private SequenceChecksumTypeChoicesBean sequenceChecksumTypeChoices;
    
    private String sequenceTypeName;
    private String prefix;
    private String suffix;
    private String sequenceEncoderTypeChoice;
    private String sequenceChecksumTypeChoice;
    private String chunkSize;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupSequenceEncoderTypeChoices() {
        if(sequenceEncoderTypeChoices == null) {
            try {
                GetSequenceEncoderTypeChoicesForm form = SequenceUtil.getHome().getGetSequenceEncoderTypeChoicesForm();
                
                form.setDefaultSequenceEncoderTypeChoice(sequenceEncoderTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceEncoderTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceEncoderTypeChoicesResult getSequenceEncoderTypeChoicesResult = (GetSequenceEncoderTypeChoicesResult)executionResult.getResult();
                sequenceEncoderTypeChoices = getSequenceEncoderTypeChoicesResult.getSequenceEncoderTypeChoices();
                
                if(sequenceEncoderTypeChoice == null)
                    sequenceEncoderTypeChoice = sequenceEncoderTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, sequenceEncoderTypeChoices remains null, no default
            }
        }
    }
    
    public void setupSequenceChecksumTypeChoices() {
        if(sequenceChecksumTypeChoices == null) {
            try {
                GetSequenceChecksumTypeChoicesForm form = SequenceUtil.getHome().getGetSequenceChecksumTypeChoicesForm();
                
                form.setDefaultSequenceChecksumTypeChoice(sequenceChecksumTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = SequenceUtil.getHome().getSequenceChecksumTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSequenceChecksumTypeChoicesResult getSequenceChecksumTypeChoicesResult = (GetSequenceChecksumTypeChoicesResult)executionResult.getResult();
                sequenceChecksumTypeChoices = getSequenceChecksumTypeChoicesResult.getSequenceChecksumTypeChoices();
                
                if(sequenceChecksumTypeChoice == null)
                    sequenceChecksumTypeChoice = sequenceChecksumTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, sequenceChecksumTypeChoices remains null, no default
            }
        }
    }
    
    public void setSequenceTypeName(String sequenceTypeName) {
        this.sequenceTypeName = sequenceTypeName;
    }
    
    public String getSequenceTypeName() {
        return sequenceTypeName;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public String getSequenceEncoderTypeChoice() {
        return sequenceEncoderTypeChoice;
    }
    
    public void setSequenceEncoderTypeChoice(String sequenceEncoderTypeChoice) {
        this.sequenceEncoderTypeChoice = sequenceEncoderTypeChoice;
    }
    
    public List<LabelValueBean> getSequenceEncoderTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupSequenceEncoderTypeChoices();
        if(sequenceEncoderTypeChoices != null)
            choices = convertChoices(sequenceEncoderTypeChoices);
        
        return choices;
    }
    
    public String getSequenceChecksumTypeChoice() {
        return sequenceChecksumTypeChoice;
    }
    
    public void setSequenceChecksumTypeChoice(String sequenceChecksumTypeChoice) {
        this.sequenceChecksumTypeChoice = sequenceChecksumTypeChoice;
    }
    
    public List<LabelValueBean> getSequenceChecksumTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupSequenceChecksumTypeChoices();
        if(sequenceChecksumTypeChoices != null)
            choices = convertChoices(sequenceChecksumTypeChoices);
        
        return choices;
    }

    public String getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(String chunkSize) {
        this.chunkSize = chunkSize;
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
        
        isDefault = Boolean.FALSE;
    }
    
}
