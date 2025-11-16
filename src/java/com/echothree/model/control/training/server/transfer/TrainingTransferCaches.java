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

package com.echothree.model.control.training.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class TrainingTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    TrainingClassTransferCache trainingClassTransferCache;
    
    @Inject
    TrainingClassTranslationTransferCache trainingClassTranslationTransferCache;
    
    @Inject
    TrainingClassSectionTransferCache trainingClassSectionTransferCache;
    
    @Inject
    TrainingClassSectionTranslationTransferCache trainingClassSectionTranslationTransferCache;
    
    @Inject
    TrainingClassPageTransferCache trainingClassPageTransferCache;
    
    @Inject
    TrainingClassPageTranslationTransferCache trainingClassPageTranslationTransferCache;
    
    @Inject
    TrainingClassQuestionTransferCache trainingClassQuestionTransferCache;
    
    @Inject
    TrainingClassQuestionTranslationTransferCache trainingClassQuestionTranslationTransferCache;
    
    @Inject
    TrainingClassAnswerTransferCache trainingClassAnswerTransferCache;
    
    @Inject
    TrainingClassAnswerTranslationTransferCache trainingClassAnswerTranslationTransferCache;
    
    @Inject
    PartyTrainingClassTransferCache partyTrainingClassTransferCache;
    
    @Inject
    PartyTrainingClassSessionTransferCache partyTrainingClassSessionTransferCache;
    
    @Inject
    PartyTrainingClassSessionSectionTransferCache partyTrainingClassSessionSectionTransferCache;
    
    @Inject
    PartyTrainingClassSessionPageTransferCache partyTrainingClassSessionPageTransferCache;
    
    @Inject
    PartyTrainingClassSessionQuestionTransferCache partyTrainingClassSessionQuestionTransferCache;
    
    @Inject
    PartyTrainingClassSessionAnswerTransferCache partyTrainingClassSessionAnswerTransferCache;

    /** Creates a new instance of TrainingTransferCaches */
    protected TrainingTransferCaches() {
        super();
    }
    
    public TrainingClassTransferCache getTrainingClassTransferCache() {
        return trainingClassTransferCache;
    }
    
    public TrainingClassTranslationTransferCache getTrainingClassTranslationTransferCache() {
        return trainingClassTranslationTransferCache;
    }
    
    public TrainingClassSectionTransferCache getTrainingClassSectionTransferCache() {
        return trainingClassSectionTransferCache;
    }
    
    public TrainingClassSectionTranslationTransferCache getTrainingClassSectionTranslationTransferCache() {
        return trainingClassSectionTranslationTransferCache;
    }
    
    public TrainingClassPageTransferCache getTrainingClassPageTransferCache() {
        return trainingClassPageTransferCache;
    }
    
    public TrainingClassPageTranslationTransferCache getTrainingClassPageTranslationTransferCache() {
        return trainingClassPageTranslationTransferCache;
    }
    
    public TrainingClassQuestionTransferCache getTrainingClassQuestionTransferCache() {
        return trainingClassQuestionTransferCache;
    }
    
    public TrainingClassQuestionTranslationTransferCache getTrainingClassQuestionTranslationTransferCache() {
        return trainingClassQuestionTranslationTransferCache;
    }
    
    public TrainingClassAnswerTransferCache getTrainingClassAnswerTransferCache() {
        return trainingClassAnswerTransferCache;
    }
    
    public TrainingClassAnswerTranslationTransferCache getTrainingClassAnswerTranslationTransferCache() {
        return trainingClassAnswerTranslationTransferCache;
    }
    
    public PartyTrainingClassTransferCache getPartyTrainingClassTransferCache() {
        return partyTrainingClassTransferCache;
    }
    
    public PartyTrainingClassSessionTransferCache getPartyTrainingClassSessionTransferCache() {
        return partyTrainingClassSessionTransferCache;
    }
    
    public PartyTrainingClassSessionSectionTransferCache getPartyTrainingClassSessionSectionTransferCache() {
        return partyTrainingClassSessionSectionTransferCache;
    }
    
    public PartyTrainingClassSessionPageTransferCache getPartyTrainingClassSessionPageTransferCache() {
        return partyTrainingClassSessionPageTransferCache;
    }
    
    public PartyTrainingClassSessionQuestionTransferCache getPartyTrainingClassSessionQuestionTransferCache() {
        return partyTrainingClassSessionQuestionTransferCache;
    }
    
    public PartyTrainingClassSessionAnswerTransferCache getPartyTrainingClassSessionAnswerTransferCache() {
        return partyTrainingClassSessionAnswerTransferCache;
    }
    
}
