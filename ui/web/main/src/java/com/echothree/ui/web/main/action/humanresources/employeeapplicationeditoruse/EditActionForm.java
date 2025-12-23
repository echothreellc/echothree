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

package com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetApplicationEditorChoicesResult;
import com.echothree.model.control.core.common.choice.ApplicationEditorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeApplicationEditorUseEdit")
public class EditActionForm
        extends BaseActionForm {

    private ApplicationEditorChoicesBean applicationEditorChoices;

    private String partyName;
    private String applicationName;
    private String applicationEditorUseName;
    private String editorChoice;
    private String preferredHeight;
    private String preferredWidth;

    private void setupApplicationEditorChoices()
            throws NamingException {
        if(applicationEditorChoices == null) {
            var commandForm = CoreUtil.getHome().getGetApplicationEditorChoicesForm();

            commandForm.setApplicationName(applicationName);
            commandForm.setDefaultEditorChoice(editorChoice);
            commandForm.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getApplicationEditorChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var getApplicationEditorChoicesResult = (GetApplicationEditorChoicesResult)executionResult.getResult();
            applicationEditorChoices = getApplicationEditorChoicesResult.getApplicationEditorChoices();

            if(editorChoice == null) {
                editorChoice = applicationEditorChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationEditorUseName(String applicationEditorUseName) {
        this.applicationEditorUseName = applicationEditorUseName;
    }

    public String getApplicationEditorUseName() {
        return applicationEditorUseName;
    }

    public List<LabelValueBean> getApplicationEditorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupApplicationEditorChoices();
        if(applicationEditorChoices != null) {
            choices = convertChoices(applicationEditorChoices);
        }
        
        return choices;
    }
    
    public void setEditorChoice(String editorChoice) {
        this.editorChoice = editorChoice;
    }
    
    public String getEditorChoice()
            throws NamingException {
        setupApplicationEditorChoices();
        return editorChoice;
    }
    
    public String getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(String preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    public String getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(String preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

}
