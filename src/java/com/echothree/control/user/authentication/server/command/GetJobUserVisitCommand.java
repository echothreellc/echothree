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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.GetJobUserVisitForm;
import com.echothree.control.user.authentication.common.result.AuthenticationResultFactory;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetJobUserVisitCommand
        extends BaseSimpleCommand<GetJobUserVisitForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("JobName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetJobUserVisitCommand */
    public GetJobUserVisitCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);

        // Isn't really the user executing this command, don't update the last command time.
        setUpdateLastCommandTime(false);
    }
    
    private static final String SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND = "42S02";
    
    @Override
    protected BaseResult execute() {
        var jobControl = Session.getModelController(JobControl.class);
        var result = AuthenticationResultFactory.getGetJobUserVisitResult();
        
        try {
            var jobName = form.getJobName();
            var job = jobControl.getJobByName(jobName);

            if(job != null) {
                var userControl = getUserControl();
                var userVisit = userControl.createUserVisit(null, null, null, null, null, null, null, null);

                userControl.associatePartyToUserVisit(userVisit, job.getLastDetail().getRunAsParty(), null, null);

                result.setUserVisitPK(userVisit.getPrimaryKey());
                result.setJob(jobControl.getJobTransfer(userVisit, job));
            } else {
                addExecutionError(ExecutionErrors.UnknownJobName.name(), jobName);
            }
        } catch(PersistenceDatabaseException pde) {
            var cause = pde.getCause();

            if(cause instanceof SQLException se) {
                if(se.getSQLState().equals(SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND)) {
                    getLog().info("Ignoring \"" + se.getMessage() + "\"");

                    // The table jobs did not exist, don't pass along exception. Tested w/ MySQL only.
                    pde = null;
                }
            }

            if(pde != null) {
                throw pde;
            }
        }
        
        return result;
    }
    
}
