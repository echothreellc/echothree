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

package com.echothree.control.user.training.common.result;

import com.echothree.model.control.training.common.transfer.TrainingClassSectionTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassSectionTranslationTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetTrainingClassSectionTranslationsResult
        extends BaseResult {
    
    TrainingClassSectionTransfer getTrainingClassSection();
    void setTrainingClassSection(TrainingClassSectionTransfer trainingClassSection);
    
    List<TrainingClassSectionTranslationTransfer> getTrainingClassSectionTranslations();
    void setTrainingClassSectionTranslations(List<TrainingClassSectionTranslationTransfer> trainingClassSectionTranslations);
    
}
