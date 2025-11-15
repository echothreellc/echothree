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

package com.echothree.model.control.job.server.transfer;

import com.echothree.model.control.job.common.transfer.JobDescriptionTransfer;
import com.echothree.model.control.job.server.control.JobControl;
import com.echothree.model.data.job.server.entity.JobDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class JobDescriptionTransferCache
        extends BaseJobDescriptionTransferCache<JobDescription, JobDescriptionTransfer> {

    JobControl jobControl = Session.getModelController(JobControl.class);

    /** Creates a new instance of JobDescriptionTransferCache */
    public JobDescriptionTransferCache() {
        super();
    }
    
    public JobDescriptionTransfer getJobDescriptionTransfer(UserVisit userVisit, JobDescription jobDescription) {
        var jobDescriptionTransfer = get(jobDescription);
        
        if(jobDescriptionTransfer == null) {
            var jobTransfer = jobControl.getJobTransfer(userVisit, jobDescription.getJob());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, jobDescription.getLanguage());
            
            jobDescriptionTransfer = new JobDescriptionTransfer(languageTransfer, jobTransfer, jobDescription.getDescription());
            put(userVisit, jobDescription, jobDescriptionTransfer);
        }
        
        return jobDescriptionTransfer;
    }
    
}
