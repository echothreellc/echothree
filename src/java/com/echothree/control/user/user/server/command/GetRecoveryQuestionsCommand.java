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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.GetRecoveryQuestionsForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.factory.RecoveryQuestionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetRecoveryQuestionsCommand
        extends BasePaginatedMultipleEntitiesCommand<RecoveryQuestion, GetRecoveryQuestionsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetRecoveryQuestionsCommand */
    public GetRecoveryQuestionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // no fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.countRecoveryQuestions();
    }

    @Override
    protected Collection<RecoveryQuestion> getEntities() {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getRecoveryQuestions();
    }

    @Override
    protected BaseResult getResult(Collection<RecoveryQuestion> entities) {
        var result = UserResultFactory.getGetRecoveryQuestionsResult();

        if(entities != null) {
            var userControl = Session.getModelController(UserControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(RecoveryQuestionFactory.class)) {
                result.setRecoveryQuestionCount(getTotalEntities());
            }

            result.setRecoveryQuestions(userControl.getRecoveryQuestionTransfers(userVisit, entities));
        }

        return result;
    }

}
