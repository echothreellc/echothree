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

package com.echothree.control.user.letter.common;

import com.echothree.control.user.letter.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface LetterService
        extends LetterForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Letter Sources
    // --------------------------------------------------------------------------------
    
    CommandResult createLetterSource(UserVisitPK userVisitPK, CreateLetterSourceForm form);
    
    CommandResult getLetterSourceChoices(UserVisitPK userVisitPK, GetLetterSourceChoicesForm form);
    
    CommandResult getLetterSource(UserVisitPK userVisitPK, GetLetterSourceForm form);
    
    CommandResult getLetterSources(UserVisitPK userVisitPK, GetLetterSourcesForm form);
    
    CommandResult setDefaultLetterSource(UserVisitPK userVisitPK, SetDefaultLetterSourceForm form);
    
    CommandResult editLetterSource(UserVisitPK userVisitPK, EditLetterSourceForm form);
    
    CommandResult deleteLetterSource(UserVisitPK userVisitPK, DeleteLetterSourceForm form);
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createLetterSourceDescription(UserVisitPK userVisitPK, CreateLetterSourceDescriptionForm form);
    
    CommandResult getLetterSourceDescriptions(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form);
    
    CommandResult editLetterSourceDescription(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form);
    
    CommandResult deleteLetterSourceDescription(UserVisitPK userVisitPK, DeleteLetterSourceDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    CommandResult createLetter(UserVisitPK userVisitPK, CreateLetterForm form);
    
    CommandResult getLetterChoices(UserVisitPK userVisitPK, GetLetterChoicesForm form);
    
    CommandResult getLetter(UserVisitPK userVisitPK, GetLetterForm form);
    
    CommandResult getLetters(UserVisitPK userVisitPK, GetLettersForm form);
    
    CommandResult setDefaultLetter(UserVisitPK userVisitPK, SetDefaultLetterForm form);
    
    CommandResult editLetter(UserVisitPK userVisitPK, EditLetterForm form);
    
    CommandResult deleteLetter(UserVisitPK userVisitPK, DeleteLetterForm form);
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createLetterDescription(UserVisitPK userVisitPK, CreateLetterDescriptionForm form);
    
    CommandResult getLetterDescriptions(UserVisitPK userVisitPK, GetLetterDescriptionsForm form);
    
    CommandResult editLetterDescription(UserVisitPK userVisitPK, EditLetterDescriptionForm form);
    
    CommandResult deleteLetterDescription(UserVisitPK userVisitPK, DeleteLetterDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Letter Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult createLetterContactMechanismPurpose(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form);
    
    CommandResult getLetterContactMechanismPurposes(UserVisitPK userVisitPK, GetLetterContactMechanismPurposesForm form);
    
    CommandResult editLetterContactMechanismPurpose(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form);
    
    CommandResult deleteLetterContactMechanismPurpose(UserVisitPK userVisitPK, DeleteLetterContactMechanismPurposeForm form);
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------
    
    CommandResult getQueuedLetter(UserVisitPK userVisitPK, GetQueuedLetterForm form);

    CommandResult getQueuedLetters(UserVisitPK userVisitPK, GetQueuedLettersForm form);

    CommandResult deleteQueuedLetter(UserVisitPK userVisitPK, DeleteQueuedLetterForm form);

}
