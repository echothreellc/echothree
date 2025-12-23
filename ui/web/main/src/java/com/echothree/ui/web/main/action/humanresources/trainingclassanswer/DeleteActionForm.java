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

package com.echothree.ui.web.main.action.humanresources.trainingclassanswer;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="TrainingClassAnswerDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String trainingClassName;
    private String trainingClassSectionName;
    private String trainingClassQuestionName;
    private String trainingClassAnswerName;
    
    public void setTrainingClassName(String trainingClassName) {
        this.trainingClassName = trainingClassName;
    }
    
    public String getTrainingClassName() {
        return trainingClassName;
    }
    
    public void setTrainingClassSectionName(String trainingClassSectionName) {
        this.trainingClassSectionName = trainingClassSectionName;
    }

    public String getTrainingClassSectionName() {
        return trainingClassSectionName;
    }

    public void setTrainingClassQuestionName(String trainingClassQuestionName) {
        this.trainingClassQuestionName = trainingClassQuestionName;
    }

    public String getTrainingClassQuestionName() {
        return trainingClassQuestionName;
    }

    public void setTrainingClassAnswerName(String trainingClassAnswerName) {
        this.trainingClassAnswerName = trainingClassAnswerName;
    }
    
    public String getTrainingClassAnswerName() {
        return trainingClassAnswerName;
    }
    
}
