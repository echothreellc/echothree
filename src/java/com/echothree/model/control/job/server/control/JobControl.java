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

package com.echothree.model.control.job.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.job.common.choice.JobStatusChoicesBean;
import com.echothree.model.control.job.common.transfer.JobDescriptionTransfer;
import com.echothree.model.control.job.common.transfer.JobTransfer;
import com.echothree.model.control.job.common.workflow.JobStatusConstants;
import com.echothree.model.control.job.server.transfer.JobTransferCaches;
import com.echothree.model.data.job.server.entity.Job;
import com.echothree.model.data.job.server.entity.JobDescription;
import com.echothree.model.data.job.server.entity.JobStatus;
import com.echothree.model.data.job.server.factory.JobDescriptionFactory;
import com.echothree.model.data.job.server.factory.JobDetailFactory;
import com.echothree.model.data.job.server.factory.JobFactory;
import com.echothree.model.data.job.server.factory.JobStatusFactory;
import com.echothree.model.data.job.server.value.JobDescriptionValue;
import com.echothree.model.data.job.server.value.JobDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobControl
        extends BaseModelControl {
    
    /** Creates a new instance of JobControl */
    public JobControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact List Transfer Caches
    // --------------------------------------------------------------------------------
    
    private JobTransferCaches jobTransferCaches;
    
    public JobTransferCaches getJobTransferCaches(UserVisit userVisit) {
        if(jobTransferCaches == null) {
            jobTransferCaches = new JobTransferCaches(userVisit, this);
        }
        
        return jobTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Jobs
    // --------------------------------------------------------------------------------
    
    public Job createJob(String jobName, Party runAsParty, Integer sortOrder, BasePK createdBy) {
        var job = JobFactory.getInstance().create();
        var jobDetail = JobDetailFactory.getInstance().create(job, jobName, runAsParty, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        job = JobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, job.getPrimaryKey());
        job.setActiveDetail(jobDetail);
        job.setLastDetail(jobDetail);
        job.store();
        
        sendEvent(job.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createJobStatus(job);
        
        return job;
    }
    
    private Job getJobByName(String jobName, EntityPermission entityPermission) {
        Job job;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM jobs, jobdetails " +
                        "WHERE jb_activedetailid = jbdt_jobdetailid AND jbdt_jobname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM jobs, jobdetails " +
                        "WHERE jb_activedetailid = jbdt_jobdetailid AND jbdt_jobname = ? " +
                        "FOR UPDATE";
            }

            var ps = JobFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, jobName);
            
            job = JobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return job;
    }
    
    public Job getJobByName(String jobName) {
        return getJobByName(jobName, EntityPermission.READ_ONLY);
    }
    
    public Job getJobByNameForUpdate(String jobName) {
        return getJobByName(jobName, EntityPermission.READ_WRITE);
    }
    
    public JobDetailValue getJobDetailValueForUpdate(Job job) {
        return job == null? null: job.getLastDetailForUpdate().getJobDetailValue().clone();
    }
    
    public JobDetailValue getJobDetailValueByNameForUpdate(String jobName) {
        return getJobDetailValueForUpdate(getJobByNameForUpdate(jobName));
    }
    
    private List<Job> getJobs(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM jobs, jobdetails " +
                    "WHERE jb_activedetailid = jbdt_jobdetailid " +
                    "ORDER BY jbdt_sortorder, jbdt_jobname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM jobs, jobdetails " +
                    "WHERE jb_activedetailid = jbdt_jobdetailid " +
                    "FOR UPDATE";
        }

        var ps = JobFactory.getInstance().prepareStatement(query);
        
        return JobFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Job> getJobs() {
        return getJobs(EntityPermission.READ_ONLY);
    }
    
    public List<Job> getJobsForUpdate() {
        return getJobs(EntityPermission.READ_WRITE);
    }
    
    public JobTransfer getJobTransfer(UserVisit userVisit, Job job) {
        return getJobTransferCaches(userVisit).getJobTransferCache().getJobTransfer(job);
    }
    
    public List<JobTransfer> getJobTransfers(UserVisit userVisit) {
        var jobs = getJobs();
        List<JobTransfer> jobTransfers = new ArrayList<>(jobs.size());
        var jobTransferCache = getJobTransferCaches(userVisit).getJobTransferCache();
        
        jobs.forEach((job) ->
                jobTransfers.add(jobTransferCache.getJobTransfer(job))
        );
        
        return jobTransfers;
    }
    
    public JobStatusChoicesBean getJobStatusChoices(String defaultJobStatusChoice, Language language, boolean allowNullChoice,
            Job job, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        var jobStatusChoicesBean = new JobStatusChoicesBean();
        
        if(job == null) {
            workflowControl.getWorkflowEntranceChoices(jobStatusChoicesBean, defaultJobStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(JobStatusConstants.Workflow_JOB_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(job.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(JobStatusConstants.Workflow_JOB_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(jobStatusChoicesBean, defaultJobStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return jobStatusChoicesBean;
    }
    
    public void setJobStatus(ExecutionErrorAccumulator eea, Job job, String jobStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        var entityInstance = getEntityInstanceByBaseEntity(job);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(JobStatusConstants.Workflow_JOB_STATUS,
                entityInstance);
        var workflowDestination = jobStatusChoice == null? null: workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), jobStatusChoice);
        
        if(workflowDestination != null || jobStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownJobStatusChoice.name(), jobStatusChoice);
        }
    }
    
    public void updateJobFromValue(JobDetailValue jobDetailValue, BasePK updatedBy) {
        var job = JobFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, jobDetailValue.getJobPK());
        var jobDetail = job.getActiveDetailForUpdate();
        
        jobDetail.setThruTime(session.START_TIME_LONG);
        jobDetail.store();

        var jobPK = jobDetail.getJobPK();
        var jobName = jobDetailValue.getJobName();
        var runAsPartyPK = jobDetailValue.getRunAsPartyPK();
        var sortOrder = jobDetailValue.getSortOrder();
        
        jobDetail = JobDetailFactory.getInstance().create(jobPK, jobName, runAsPartyPK, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        job.setActiveDetail(jobDetail);
        job.setLastDetail(jobDetail);
        job.store();
        
        sendEvent(jobPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteJob(Job job, BasePK deletedBy) {
        deleteJobDescriptionsByJob(job, deletedBy);

        var jobDetail = job.getLastDetailForUpdate();
        jobDetail.setThruTime(session.START_TIME_LONG);
        job.setActiveDetail(null);
        job.store();
        
        sendEvent(job.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
        
        removeJobStatusByJob(job);
    }
    
    // --------------------------------------------------------------------------------
    //   Job Descriptions
    // --------------------------------------------------------------------------------
    
    public JobDescription createJobDescription(Job job, Language language,
            String description, BasePK createdBy) {
        var jobDescription = JobDescriptionFactory.getInstance().create(session,
                job, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(job.getPrimaryKey(), EventTypes.MODIFY, jobDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return jobDescription;
    }
    
    private JobDescription getJobDescription(Job job, Language language, EntityPermission entityPermission) {
        JobDescription jobDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM jobdescriptions " +
                        "WHERE jbd_jb_jobid = ? AND jbd_lang_languageid = ? AND jbd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM jobdescriptions " +
                        "WHERE jbd_jb_jobid = ? AND jbd_lang_languageid = ? AND jbd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = JobDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, job.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            jobDescription = JobDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return jobDescription;
    }
    
    public JobDescription getJobDescription(Job job, Language language) {
        return getJobDescription(job, language, EntityPermission.READ_ONLY);
    }
    
    public JobDescription getJobDescriptionForUpdate(Job job, Language language) {
        return getJobDescription(job, language, EntityPermission.READ_WRITE);
    }
    
    public JobDescriptionValue getJobDescriptionValue(JobDescription jobDescription) {
        return jobDescription == null? null: jobDescription.getJobDescriptionValue().clone();
    }
    
    public JobDescriptionValue getJobDescriptionValueForUpdate(Job job, Language language) {
        return getJobDescriptionValue(getJobDescriptionForUpdate(job, language));
    }
    
    private List<JobDescription> getJobDescriptionsByJob(Job job, EntityPermission entityPermission) {
        List<JobDescription> jobDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM jobdescriptions, languages " +
                        "WHERE jbd_jb_jobid = ? AND jbd_thrutime = ? AND jbd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM jobdescriptions " +
                        "WHERE jbd_jb_jobid = ? AND jbd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = JobDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, job.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            jobDescriptions = JobDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return jobDescriptions;
    }
    
    public List<JobDescription> getJobDescriptionsByJob(Job job) {
        return getJobDescriptionsByJob(job, EntityPermission.READ_ONLY);
    }
    
    public List<JobDescription> getJobDescriptionsByJobForUpdate(Job job) {
        return getJobDescriptionsByJob(job, EntityPermission.READ_WRITE);
    }
    
    public String getBestJobDescription(Job job, Language language) {
        String description;
        var jobDescription = getJobDescription(job, language);
        
        if(jobDescription == null && !language.getIsDefault()) {
            jobDescription = getJobDescription(job, getPartyControl().getDefaultLanguage());
        }
        
        if(jobDescription == null) {
            description = job.getLastDetail().getJobName();
        } else {
            description = jobDescription.getDescription();
        }
        
        return description;
    }
    
    public JobDescriptionTransfer getJobDescriptionTransfer(UserVisit userVisit, JobDescription jobDescription) {
        return getJobTransferCaches(userVisit).getJobDescriptionTransferCache().getJobDescriptionTransfer(jobDescription);
    }
    
    public List<JobDescriptionTransfer> getJobDescriptionTransfersByJob(UserVisit userVisit, Job job) {
        var jobDescriptions = getJobDescriptionsByJob(job);
        List<JobDescriptionTransfer> jobDescriptionTransfers = new ArrayList<>(jobDescriptions.size());
        
        jobDescriptions.forEach((jobDescription) -> {
            jobDescriptionTransfers.add(getJobTransferCaches(userVisit).getJobDescriptionTransferCache().getJobDescriptionTransfer(jobDescription));
        });
        
        return jobDescriptionTransfers;
    }
    
    public void updateJobDescriptionFromValue(JobDescriptionValue jobDescriptionValue, BasePK updatedBy) {
        if(jobDescriptionValue.hasBeenModified()) {
            var jobDescription = JobDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     jobDescriptionValue.getPrimaryKey());
            
            jobDescription.setThruTime(session.START_TIME_LONG);
            jobDescription.store();

            var job = jobDescription.getJob();
            var language = jobDescription.getLanguage();
            var description = jobDescriptionValue.getDescription();
            
            jobDescription = JobDescriptionFactory.getInstance().create(job, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(job.getPrimaryKey(), EventTypes.MODIFY, jobDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteJobDescription(JobDescription jobDescription, BasePK deletedBy) {
        jobDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(jobDescription.getJobPK(), EventTypes.MODIFY, jobDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteJobDescriptionsByJob(Job job, BasePK deletedBy) {
        var jobDescriptions = getJobDescriptionsByJobForUpdate(job);
        
        jobDescriptions.forEach((jobDescription) -> 
                deleteJobDescription(jobDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Job Statuses
    // --------------------------------------------------------------------------------
    
    public JobStatus createJobStatus(Job job) {
        return JobStatusFactory.getInstance().create(job, null, null);
    }
    
    private JobStatus getJobStatus(Job job, EntityPermission entityPermission) {
        JobStatus jobStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM jobstatuses " +
                        "WHERE jbst_jb_jobid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM jobstatuses " +
                        "WHERE jbst_jb_jobid = ? " +
                        "FOR UPDATE";
            }

            var ps = JobStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, job.getPrimaryKey().getEntityId());
            
            jobStatus = JobStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return jobStatus;
    }
    
    public JobStatus getJobStatus(Job job) {
        return getJobStatus(job, EntityPermission.READ_ONLY);
    }
    
    public JobStatus getJobStatusForUpdate(Job job) {
        return getJobStatus(job, EntityPermission.READ_WRITE);
    }
    
    public void removeJobStatusByJob(Job job) {
        var jobStatus = getJobStatusForUpdate(job);
        
        if(jobStatus != null) {
            jobStatus.remove();
        }
    }
    
}
