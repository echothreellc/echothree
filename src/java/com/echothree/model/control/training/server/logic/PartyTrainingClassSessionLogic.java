// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.training.server.logic;

import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassSessionAnswerException;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassSessionPageException;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassSessionQuestionException;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassSessionSequenceException;
import com.echothree.model.control.training.common.exception.UnknownPartyTrainingClassSessionStatusException;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassDetail;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionDetail;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionPage;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestionDetail;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionSection;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionStatus;
import com.echothree.model.data.training.server.entity.PartyTrainingClassStatus;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassDetail;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassQuestionDetail;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.entity.TrainingClassSectionDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PartyTrainingClassSessionLogic
        extends BaseLogic {
    
    private PartyTrainingClassSessionLogic() {
        super();
    }
    
    private static class PartyTrainingClassSessionLogicHolder {
        static PartyTrainingClassSessionLogic instance = new PartyTrainingClassSessionLogic();
    }
    
    public static PartyTrainingClassSessionLogic getInstance() {
        return PartyTrainingClassSessionLogicHolder.instance;
    }

    private PartyTrainingClassSession getPartyTrainingClassSession(final ExecutionErrorAccumulator eea, final PartyTrainingClass partyTrainingClass,
            final Integer partyTrainingClassSessionSequence, final EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSession partyTrainingClassSession = trainingControl.getPartyTrainingClassSessionBySequence(partyTrainingClass,
                partyTrainingClassSessionSequence, entityPermission);

        if(partyTrainingClass == null) {
            handleExecutionError(UnknownPartyTrainingClassSessionSequenceException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionSequence.name(),
                    partyTrainingClass.getLastDetail().getPartyTrainingClassName(), partyTrainingClassSessionSequence.toString());
        }

        return partyTrainingClassSession;
    }
    
    public PartyTrainingClassSession getPartyTrainingClassSession(final ExecutionErrorAccumulator eea, final PartyTrainingClass partyTrainingClass,
            final Integer partyTrainingClassSessionSequence) {
        return getPartyTrainingClassSession(eea, partyTrainingClass, partyTrainingClassSessionSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSession getPartyTrainingClassSessionForUpdate(final ExecutionErrorAccumulator eea, final PartyTrainingClass partyTrainingClass,
            final Integer partyTrainingClassSessionSequence) {
        return getPartyTrainingClassSession(eea, partyTrainingClass, partyTrainingClassSessionSequence, EntityPermission.READ_WRITE);
    }
    
    private PartyTrainingClassSession getLatestPartyTrainingClassSession(final ExecutionErrorAccumulator eea, final PartyTrainingClass partyTrainingClass,
            final EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassStatus partyTrainingClassStatus = trainingControl.getPartyTrainingClassStatus(partyTrainingClass);
        Integer partyTrainingClassSessionSequence = partyTrainingClassStatus.getPartyTrainingClassSessionSequence();
        PartyTrainingClassSession partyTrainingClassSession = partyTrainingClassStatus == null ? null
                : trainingControl.getPartyTrainingClassSessionBySequence(partyTrainingClass, partyTrainingClassSessionSequence, entityPermission);

        if(partyTrainingClass == null) {
            handleExecutionError(UnknownPartyTrainingClassSessionSequenceException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionSequence.name(),
                    partyTrainingClass.getLastDetail().getPartyTrainingClassName(), partyTrainingClassSessionSequence.toString());
        }

        return partyTrainingClassSession;
    }
    
    public PartyTrainingClassSession getLatestPartyTrainingClassSession(final ExecutionErrorAccumulator eea,
            final PartyTrainingClass partyTrainingClass) {
        return getLatestPartyTrainingClassSession(eea, partyTrainingClass, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSession getLatestPartyTrainingClassSessionForUpdate(final ExecutionErrorAccumulator eea,
            final PartyTrainingClass partyTrainingClass) {
        return getLatestPartyTrainingClassSession(eea, partyTrainingClass, EntityPermission.READ_WRITE);
    }
    
    private PartyTrainingClassSessionPage getPartyTrainingClassSessionPage(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final Integer partyTrainingClassSessionPageSequence,
            final EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSessionPage partyTrainingClassSessionPage = trainingControl.getPartyTrainingClassSessionPage(partyTrainingClassSession,
                partyTrainingClassSessionPageSequence, entityPermission);

        if(partyTrainingClassSessionPage == null) {
            PartyTrainingClassSessionDetail partyTrainingClassSessionDetail = partyTrainingClassSession.getLastDetail();
            PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClassSessionDetail.getPartyTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownPartyTrainingClassSessionPageException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionPage.name(),
                    partyTrainingClassDetail.getPartyTrainingClassName(), partyTrainingClassSessionDetail.getPartyTrainingClassSessionSequence().toString(),
                    partyTrainingClassSessionPageSequence.toString());
        }

        return partyTrainingClassSessionPage;
    }
    
    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPage(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final Integer partyTrainingClassSessionPageSequence) {
        return getPartyTrainingClassSessionPage(eea, partyTrainingClassSession, partyTrainingClassSessionPageSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionPage getPartyTrainingClassSessionPageForUpdate(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final Integer partyTrainingClassSessionPageSequence) {
        return getPartyTrainingClassSessionPage(eea, partyTrainingClassSession, partyTrainingClassSessionPageSequence, EntityPermission.READ_WRITE);
    }
    
    private PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestion(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final TrainingClassQuestion trainingClassQuestion,
            final EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion = trainingControl.getPartyTrainingClassSessionQuestion(partyTrainingClassSession,
                trainingClassQuestion, entityPermission);

        if(partyTrainingClassSessionQuestion == null) {
            PartyTrainingClassSessionDetail partyTrainingClassSessionDetail = partyTrainingClassSession.getLastDetail();
            PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClassSessionDetail.getPartyTrainingClass().getLastDetail();
            TrainingClassQuestionDetail trainingClassQuestionDetail = trainingClassQuestion.getLastDetail();
            TrainingClassSectionDetail trainingClassSectionDetail = trainingClassQuestionDetail.getTrainingClassSection().getLastDetail();
            TrainingClassDetail trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownPartyTrainingClassSessionQuestionException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionQuestion.name(),
                    partyTrainingClassDetail.getPartyTrainingClassName(), partyTrainingClassSessionDetail.getPartyTrainingClassSessionSequence().toString(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionDetail.getTrainingClassSectionName(),
                    trainingClassQuestionDetail.getTrainingClassQuestionName());
        }

        return partyTrainingClassSessionQuestion;
    }
    
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestion(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestion(eea, partyTrainingClassSession, trainingClassQuestion, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionQuestion getPartyTrainingClassSessionQuestionForUpdate(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSession partyTrainingClassSession, final TrainingClassQuestion trainingClassQuestion) {
        return getPartyTrainingClassSessionQuestion(eea, partyTrainingClassSession, trainingClassQuestion, EntityPermission.READ_WRITE);
    }
    
    private PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswer(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion, final Integer partyTrainingClassSessionAnswerSequence,
            final EntityPermission entityPermission) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer = trainingControl.getPartyTrainingClassSessionAnswer(partyTrainingClassSessionQuestion,
                partyTrainingClassSessionAnswerSequence, entityPermission);

        if(partyTrainingClassSessionAnswer == null) {
            PartyTrainingClassSessionQuestionDetail partyTrainingClassSessionQuestionDetail = partyTrainingClassSessionQuestion.getLastDetail();
            PartyTrainingClassSessionDetail partyTrainingClassSessionDetail = partyTrainingClassSessionQuestionDetail.getPartyTrainingClassSession().getLastDetail();
            PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClassSessionDetail.getPartyTrainingClass().getLastDetail();
            TrainingClassQuestionDetail trainingClassQuestionDetail = partyTrainingClassSessionQuestionDetail.getTrainingClassQuestion().getLastDetail();
            TrainingClassSectionDetail trainingClassSectionDetail = trainingClassQuestionDetail.getTrainingClassSection().getLastDetail();
            TrainingClassDetail trainingClassDetail = trainingClassSectionDetail.getTrainingClass().getLastDetail();
            
            handleExecutionError(UnknownPartyTrainingClassSessionAnswerException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionAnswer.name(),
                    partyTrainingClassDetail.getPartyTrainingClassName(), partyTrainingClassSessionDetail.getPartyTrainingClassSessionSequence().toString(),
                    trainingClassDetail.getTrainingClassName(), trainingClassSectionDetail.getTrainingClassSectionName(),
                    trainingClassQuestionDetail.getTrainingClassQuestionName(), partyTrainingClassSessionAnswerSequence.toString());
        }

        return partyTrainingClassSessionAnswer;
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswer(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion, final Integer partyTrainingClassSessionAnswerSequence) {
        return getPartyTrainingClassSessionAnswer(eea, partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, EntityPermission.READ_ONLY);
    }
    
    public PartyTrainingClassSessionAnswer getPartyTrainingClassSessionAnswerForUpdate(final ExecutionErrorAccumulator eea,
            final PartyTrainingClassSessionQuestion partyTrainingClassSessionQuestion, final Integer partyTrainingClassSessionAnswerSequence) {
        return getPartyTrainingClassSessionAnswer(eea, partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence, EntityPermission.READ_WRITE);
    }
    
    public PartyTrainingClassSessionStatus getLatestPartyTrainingClassSessionStatusForUpdate(final ExecutionErrorAccumulator eea, final String partyTrainingClassName) {
        PartyTrainingClassSessionStatus partyTrainingClassSessionStatus = null;
        PartyTrainingClass partyTrainingClass = PartyTrainingClassLogic.getInstance().getPartyTrainingClassByName(eea, partyTrainingClassName);
        
        if(!hasExecutionErrors(eea)) {
            PartyTrainingClassSession partyTrainingClassSession = getLatestPartyTrainingClassSession(eea, partyTrainingClass);
            
            if(!hasExecutionErrors(eea)) {
                var trainingControl = Session.getModelController(TrainingControl.class);
                
                partyTrainingClassSessionStatus = trainingControl.getPartyTrainingClassSessionStatusForUpdate(partyTrainingClassSession);
                
                if(partyTrainingClassSessionStatus == null) {
                    handleExecutionError(UnknownPartyTrainingClassSessionStatusException.class, eea, ExecutionErrors.UnknownPartyTrainingClassSessionStatus.name(),
                            partyTrainingClass.getLastDetail().getPartyTrainingClassName(),
                            partyTrainingClassSession.getLastDetail().getPartyTrainingClassSessionSequence().toString());
                }
            }
        }
        
        return partyTrainingClassSessionStatus;
    }
    
    public PartyTrainingClassSessionPage createPartyTrainingClassSessionPage(final Session session, final PartyTrainingClassSession partyTrainingClassSession,
            final TrainingClassPage trainingClassPage, final BasePK createdBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSessionPage partyTrainingClassSessionPage = trainingControl.createPartyTrainingClassSessionPage(partyTrainingClassSession,
                trainingClassPage, session.START_TIME_LONG, null, createdBy);

        return partyTrainingClassSessionPage;
    }

    public PartyTrainingClassSessionQuestion createPartyTrainingClassSessionQuestion(final PartyTrainingClassSession partyTrainingClassSession,
            final TrainingClassQuestion trainingClassQuestion, final Integer sortOrder, final BasePK createdBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        
        return trainingControl.createPartyTrainingClassSessionQuestion(partyTrainingClassSession, trainingClassQuestion, sortOrder, createdBy);
    }

    static class SortBySortOrder implements Comparator<TrainingClassQuestion>, Serializable {

        @Override
        public int compare(TrainingClassQuestion o1, TrainingClassQuestion o2) {
            Integer s1 = o1.getLastDetail().getSortOrder();
            Integer s2 = o2.getLastDetail().getSortOrder();

            return s1.compareTo(s2);
        }
        
    }

    public void setupPartyTrainingClassSessionQuestions(final PartyTrainingClassSession partyTrainingClassSession, final BasePK createdBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClass trainingClass = partyTrainingClassSession.getLastDetail().getPartyTrainingClass().getLastDetail().getTrainingClass();
        List<TrainingClassSection> trainingClassSections = trainingControl.getTrainingClassSections(trainingClass);
        Integer overallQuestionCount = trainingClass.getLastDetail().getOverallQuestionCount();
        List<TrainingClassQuestion> randomOverallQuestions = new ArrayList<>();
        List<TrainingClassQuestion> finalTrainingClassQuestions = new ArrayList<>();
        Random random = EncryptionUtils.getInstance().getRandom();

        int overallQuestionTotal = 0;
        for(var trainingClassSection : trainingClassSections) {
            Integer questionCount = trainingClassSection.getLastDetail().getQuestionCount();
            List<TrainingClassQuestion> trainingClassQuestions = trainingControl.getTrainingClassQuestions(trainingClassSection);
            List<TrainingClassQuestion> randomSectionQuestions = new ArrayList<>();

            // Add in all required questions...
            int questionTotal = 0;
            for(var trainingClassQuestion : trainingClassQuestions) {
                if(trainingClassQuestion.getLastDetail().getPassingRequired()) {
                    finalTrainingClassQuestions.add(trainingClassQuestion);
                    questionTotal++;
                    overallQuestionTotal++;
                } else {
                    // If it isn't required, add it to the pool of questions used to fill in random ones.
                    randomSectionQuestions.add(trainingClassQuestion);
                }
            }

            // If there's a required minimum Question count, try to fill in the rest randomly.
            if(questionCount != null) {
                int remainingQuestions = questionCount - questionTotal;

                // If there are random questions, add in a few from the section randomly.
                while(remainingQuestions > 0) {
                    if(randomSectionQuestions.isEmpty()) {
                        break;
                    } else {
                        // pick a Question and add to finalTrainingClassQuestions
                        int randomQuestion = random.nextInt(randomSectionQuestions.size());
                        finalTrainingClassQuestions.add(randomSectionQuestions.get(randomQuestion));
                        overallQuestionTotal++;

                        // removed picked one from randomSectionQuestions
                        randomSectionQuestions.remove(randomQuestion);

                        remainingQuestions--;
                    }
                }
            }

            // Add any remaining Questions to the overall random Question Set.
            randomOverallQuestions.addAll(randomSectionQuestions);
        }

        if(overallQuestionCount != null) {
            int remainingQuestions = overallQuestionCount - overallQuestionTotal;

            while(remainingQuestions > 0) {
                if(randomOverallQuestions.isEmpty()) {
                    break;
                } else {
                    // pick one and add to finalTrainingClassQuestions
                    int randomQuestion = random.nextInt(randomOverallQuestions.size());
                    finalTrainingClassQuestions.add(randomOverallQuestions.get(randomQuestion));

                    // removed picked one from randomOverallQuestions
                    randomOverallQuestions.remove(randomQuestion);

                    remainingQuestions--;
                }
            }
        }

        Collections.sort(finalTrainingClassQuestions, new SortBySortOrder());

        int sortOrder = 0;
        for(var finalTrainingClassQuestion : finalTrainingClassQuestions) {
            createPartyTrainingClassSessionQuestion(partyTrainingClassSession, finalTrainingClassQuestion, sortOrder++, createdBy);
        }
    }
    
    public PartyTrainingClassSession createPartyTrainingClassSession(final PartyTrainingClass partyTrainingClass, final BasePK createdBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClassSession partyTrainingClassSession = trainingControl.createPartyTrainingClassSession(partyTrainingClass, createdBy);
        PartyTrainingClassStatus partyTrainingClassStatus = trainingControl.getPartyTrainingClassStatusForUpdate(partyTrainingClass);

        partyTrainingClassStatus.setLastPartyTrainingClassSession(partyTrainingClassSession);

        setupPartyTrainingClassSessionQuestions(partyTrainingClassSession, createdBy);

        return partyTrainingClassSession;
    }

    public void deletePartyTrainingClassSession(final PartyTrainingClassSession partyTrainingClassSession, final BasePK deletedBy) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        PartyTrainingClass partyTrainingClass = partyTrainingClassSession.getLastDetail().getPartyTrainingClass();

        trainingControl.deletePartyTrainingClassSession(partyTrainingClassSession, deletedBy);

        // Create a new PartyTrainingClassSession if the PartyTrainingClass is not yet complete (where "complete" means that the
        // completedTime is null, and the LastPartyTrainingClassSession is equals to the one we're deleting in the PartyTrainingClassStatus.
        if(partyTrainingClass.getLastDetail().getCompletedTime() == null) {
            PartyTrainingClassStatus partyTrainingClassStatus = trainingControl.getPartyTrainingClassStatusForUpdate(partyTrainingClass);

            if(partyTrainingClassSession.equals(partyTrainingClassStatus.getLastPartyTrainingClassSession())) {
                createPartyTrainingClassSession(partyTrainingClass, deletedBy);
            }
        }
    }
    
    public void updatePartyTrainingClassSessionStatus(final Session session, final PartyTrainingClassSessionStatus partyTrainingClassSessionStatus,
            final PartyTrainingClassSessionSection lastPartyTrainingClassSessionSection, final PartyTrainingClassSessionPage lastPartyTrainingClassSessionPage,
            final PartyTrainingClassSessionQuestion lastPartyTrainingClassSessionQuestion) {
        partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionSection(lastPartyTrainingClassSessionSection);
        partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionPage(lastPartyTrainingClassSessionPage);
        partyTrainingClassSessionStatus.setLastPartyTrainingClassSessionQuestion(lastPartyTrainingClassSessionQuestion);
    }

}
