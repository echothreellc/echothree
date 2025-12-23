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

package com.echothree.control.user.letter.server;

import com.echothree.control.user.letter.common.LetterRemote;
import com.echothree.control.user.letter.common.form.*;
import com.echothree.control.user.letter.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSourceChoices(UserVisitPK userVisitPK, GetLetterSourceChoicesForm form) {
        return CDI.current().select(GetLetterSourceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSource(UserVisitPK userVisitPK, GetLetterSourceForm form) {
        return CDI.current().select(GetLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSources(UserVisitPK userVisitPK, GetLetterSourcesForm form) {
        return CDI.current().select(GetLetterSourcesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLetterSource(UserVisitPK userVisitPK, SetDefaultLetterSourceForm form) {
        return CDI.current().select(SetDefaultLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterSource(UserVisitPK userVisitPK, EditLetterSourceForm form) {
        return CDI.current().select(EditLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterSource(UserVisitPK userVisitPK, DeleteLetterSourceForm form) {
        return CDI.current().select(DeleteLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterSourceDescription(UserVisitPK userVisitPK, CreateLetterSourceDescriptionForm form) {
        return CDI.current().select(CreateLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterSourceDescriptions(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form) {
        return CDI.current().select(GetLetterSourceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterSourceDescription(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form) {
        return CDI.current().select(EditLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterSourceDescription(UserVisitPK userVisitPK, DeleteLetterSourceDescriptionForm form) {
        return CDI.current().select(DeleteLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetter(UserVisitPK userVisitPK, CreateLetterForm form) {
        return CDI.current().select(CreateLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterChoices(UserVisitPK userVisitPK, GetLetterChoicesForm form) {
        return CDI.current().select(GetLetterChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetter(UserVisitPK userVisitPK, GetLetterForm form) {
        return CDI.current().select(GetLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetters(UserVisitPK userVisitPK, GetLettersForm form) {
        return CDI.current().select(GetLettersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLetter(UserVisitPK userVisitPK, SetDefaultLetterForm form) {
        return CDI.current().select(SetDefaultLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetter(UserVisitPK userVisitPK, EditLetterForm form) {
        return CDI.current().select(EditLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetter(UserVisitPK userVisitPK, DeleteLetterForm form) {
        return CDI.current().select(DeleteLetterCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterDescription(UserVisitPK userVisitPK, CreateLetterDescriptionForm form) {
        return CDI.current().select(CreateLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterDescriptions(UserVisitPK userVisitPK, GetLetterDescriptionsForm form) {
        return CDI.current().select(GetLetterDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterDescription(UserVisitPK userVisitPK, EditLetterDescriptionForm form) {
        return CDI.current().select(EditLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterDescription(UserVisitPK userVisitPK, DeleteLetterDescriptionForm form) {
        return CDI.current().select(DeleteLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLetterContactMechanismPurpose(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form) {
        return CDI.current().select(CreateLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLetterContactMechanismPurposes(UserVisitPK userVisitPK, GetLetterContactMechanismPurposesForm form) {
        return CDI.current().select(GetLetterContactMechanismPurposesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLetterContactMechanismPurpose(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form) {
        return CDI.current().select(EditLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLetterContactMechanismPurpose(UserVisitPK userVisitPK, DeleteLetterContactMechanismPurposeForm form) {
        return CDI.current().select(DeleteLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getQueuedLetter(UserVisitPK userVisitPK, GetQueuedLetterForm form) {
        return CDI.current().select(GetQueuedLetterCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getQueuedLetters(UserVisitPK userVisitPK, GetQueuedLettersForm form) {
        return CDI.current().select(GetQueuedLettersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteQueuedLetter(UserVisitPK userVisitPK, DeleteQueuedLetterForm form) {
        return CDI.current().select(DeleteQueuedLetterCommand.class).get().run(userVisitPK, form);
    }

}
