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

package com.echothree.control.user.letter.server;

import com.echothree.control.user.letter.common.LetterRemote;
import com.echothree.control.user.letter.common.form.*;
import com.echothree.control.user.letter.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class LetterBean
        extends LetterFormsImpl
        implements LetterRemote, LetterLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "LetterBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Sources
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterSource(UserVisitPK userVisitPK, CreateLetterSourceForm form) {
        return new CreateLetterSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSourceChoices(UserVisitPK userVisitPK, GetLetterSourceChoicesForm form) {
        return new GetLetterSourceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSource(UserVisitPK userVisitPK, GetLetterSourceForm form) {
        return new GetLetterSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSources(UserVisitPK userVisitPK, GetLetterSourcesForm form) {
        return new GetLetterSourcesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLetterSource(UserVisitPK userVisitPK, SetDefaultLetterSourceForm form) {
        return new SetDefaultLetterSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterSource(UserVisitPK userVisitPK, EditLetterSourceForm form) {
        return new EditLetterSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterSource(UserVisitPK userVisitPK, DeleteLetterSourceForm form) {
        return new DeleteLetterSourceCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterSourceDescription(UserVisitPK userVisitPK, CreateLetterSourceDescriptionForm form) {
        return new CreateLetterSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSourceDescriptions(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form) {
        return new GetLetterSourceDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterSourceDescription(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form) {
        return new EditLetterSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterSourceDescription(UserVisitPK userVisitPK, DeleteLetterSourceDescriptionForm form) {
        return new DeleteLetterSourceDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetter(UserVisitPK userVisitPK, CreateLetterForm form) {
        return new CreateLetterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterChoices(UserVisitPK userVisitPK, GetLetterChoicesForm form) {
        return new GetLetterChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetter(UserVisitPK userVisitPK, GetLetterForm form) {
        return new GetLetterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetters(UserVisitPK userVisitPK, GetLettersForm form) {
        return new GetLettersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLetter(UserVisitPK userVisitPK, SetDefaultLetterForm form) {
        return new SetDefaultLetterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetter(UserVisitPK userVisitPK, EditLetterForm form) {
        return new EditLetterCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetter(UserVisitPK userVisitPK, DeleteLetterForm form) {
        return new DeleteLetterCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterDescription(UserVisitPK userVisitPK, CreateLetterDescriptionForm form) {
        return new CreateLetterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterDescriptions(UserVisitPK userVisitPK, GetLetterDescriptionsForm form) {
        return new GetLetterDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterDescription(UserVisitPK userVisitPK, EditLetterDescriptionForm form) {
        return new EditLetterDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterDescription(UserVisitPK userVisitPK, DeleteLetterDescriptionForm form) {
        return new DeleteLetterDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterContactMechanismPurpose(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form) {
        return new CreateLetterContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterContactMechanismPurposes(UserVisitPK userVisitPK, GetLetterContactMechanismPurposesForm form) {
        return new GetLetterContactMechanismPurposesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterContactMechanismPurpose(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form) {
        return new EditLetterContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterContactMechanismPurpose(UserVisitPK userVisitPK, DeleteLetterContactMechanismPurposeForm form) {
        return new DeleteLetterContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getQueuedLetter(UserVisitPK userVisitPK, GetQueuedLetterForm form) {
        return new GetQueuedLetterCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getQueuedLetters(UserVisitPK userVisitPK, GetQueuedLettersForm form) {
        return new GetQueuedLettersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteQueuedLetter(UserVisitPK userVisitPK, DeleteQueuedLetterForm form) {
        return new DeleteQueuedLetterCommand().run(userVisitPK, form);
    }

}
