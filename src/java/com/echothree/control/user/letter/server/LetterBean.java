// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.letter.remote.LetterRemote;
import com.echothree.control.user.letter.remote.form.*;
import com.echothree.control.user.letter.server.command.*;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
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
        return new CreateLetterSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterSourceChoices(UserVisitPK userVisitPK, GetLetterSourceChoicesForm form) {
        return new GetLetterSourceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterSource(UserVisitPK userVisitPK, GetLetterSourceForm form) {
        return new GetLetterSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterSources(UserVisitPK userVisitPK, GetLetterSourcesForm form) {
        return new GetLetterSourcesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultLetterSource(UserVisitPK userVisitPK, SetDefaultLetterSourceForm form) {
        return new SetDefaultLetterSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLetterSource(UserVisitPK userVisitPK, EditLetterSourceForm form) {
        return new EditLetterSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLetterSource(UserVisitPK userVisitPK, DeleteLetterSourceForm form) {
        return new DeleteLetterSourceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterSourceDescription(UserVisitPK userVisitPK, CreateLetterSourceDescriptionForm form) {
        return new CreateLetterSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterSourceDescriptions(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form) {
        return new GetLetterSourceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLetterSourceDescription(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form) {
        return new EditLetterSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLetterSourceDescription(UserVisitPK userVisitPK, DeleteLetterSourceDescriptionForm form) {
        return new DeleteLetterSourceDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetter(UserVisitPK userVisitPK, CreateLetterForm form) {
        return new CreateLetterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterChoices(UserVisitPK userVisitPK, GetLetterChoicesForm form) {
        return new GetLetterChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetter(UserVisitPK userVisitPK, GetLetterForm form) {
        return new GetLetterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetters(UserVisitPK userVisitPK, GetLettersForm form) {
        return new GetLettersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultLetter(UserVisitPK userVisitPK, SetDefaultLetterForm form) {
        return new SetDefaultLetterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLetter(UserVisitPK userVisitPK, EditLetterForm form) {
        return new EditLetterCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLetter(UserVisitPK userVisitPK, DeleteLetterForm form) {
        return new DeleteLetterCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterDescription(UserVisitPK userVisitPK, CreateLetterDescriptionForm form) {
        return new CreateLetterDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterDescriptions(UserVisitPK userVisitPK, GetLetterDescriptionsForm form) {
        return new GetLetterDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLetterDescription(UserVisitPK userVisitPK, EditLetterDescriptionForm form) {
        return new EditLetterDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLetterDescription(UserVisitPK userVisitPK, DeleteLetterDescriptionForm form) {
        return new DeleteLetterDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterContactMechanismPurpose(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form) {
        return new CreateLetterContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getLetterContactMechanismPurposes(UserVisitPK userVisitPK, GetLetterContactMechanismPurposesForm form) {
        return new GetLetterContactMechanismPurposesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editLetterContactMechanismPurpose(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form) {
        return new EditLetterContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteLetterContactMechanismPurpose(UserVisitPK userVisitPK, DeleteLetterContactMechanismPurposeForm form) {
        return new DeleteLetterContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getQueuedLetter(UserVisitPK userVisitPK, GetQueuedLetterForm form) {
        return new GetQueuedLetterCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getQueuedLetters(UserVisitPK userVisitPK, GetQueuedLettersForm form) {
        return new GetQueuedLettersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteQueuedLetter(UserVisitPK userVisitPK, DeleteQueuedLetterForm form) {
        return new DeleteQueuedLetterCommand(userVisitPK, form).run();
    }

}
