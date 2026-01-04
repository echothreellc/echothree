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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplatetrainingclass;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="PartySecurityRoleTemplateTrainingClassDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String partySecurityRoleTemplateName;
    private String trainingClassName;
    
    public void setPartySecurityRoleTemplateName(String partySecurityRoleTemplateName) {
        this.partySecurityRoleTemplateName = partySecurityRoleTemplateName;
    }
    
    public String getPartySecurityRoleTemplateName() {
        return partySecurityRoleTemplateName;
    }
    
    public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }
    
    public String getTrainingClassName() {
        return trainingClassName;
    }
    
}
