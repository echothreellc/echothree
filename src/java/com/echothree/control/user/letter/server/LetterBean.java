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
import com.echothree.control.user.letter.common.result.*;
import com.echothree.control.user.letter.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
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
    public CommandResult<VoidResult> createLetterSource(UserVisitPK userVisitPK, CreateLetterSourceForm form) {
        return CDI.current().select(CreateLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterSourceChoicesResult> getLetterSourceChoices(UserVisitPK userVisitPK, GetLetterSourceChoicesForm form) {
        return CDI.current().select(GetLetterSourceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterSourceResult> getLetterSource(UserVisitPK userVisitPK, GetLetterSourceForm form) {
        return CDI.current().select(GetLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterSourcesResult> getLetterSources(UserVisitPK userVisitPK, GetLetterSourcesForm form) {
        return CDI.current().select(GetLetterSourcesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultLetterSource(UserVisitPK userVisitPK, SetDefaultLetterSourceForm form) {
        return CDI.current().select(SetDefaultLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLetterSourceResult> editLetterSource(UserVisitPK userVisitPK, EditLetterSourceForm form) {
        return CDI.current().select(EditLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLetterSource(UserVisitPK userVisitPK, DeleteLetterSourceForm form) {
        return CDI.current().select(DeleteLetterSourceCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLetterSourceDescription(UserVisitPK userVisitPK, CreateLetterSourceDescriptionForm form) {
        return CDI.current().select(CreateLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterSourceDescriptionsResult> getLetterSourceDescriptions(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form) {
        return CDI.current().select(GetLetterSourceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLetterSourceDescriptionResult> editLetterSourceDescription(UserVisitPK userVisitPK, EditLetterSourceDescriptionForm form) {
        return CDI.current().select(EditLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLetterSourceDescription(UserVisitPK userVisitPK, DeleteLetterSourceDescriptionForm form) {
        return CDI.current().select(DeleteLetterSourceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLetter(UserVisitPK userVisitPK, CreateLetterForm form) {
        return CDI.current().select(CreateLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterChoicesResult> getLetterChoices(UserVisitPK userVisitPK, GetLetterChoicesForm form) {
        return CDI.current().select(GetLetterChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterResult> getLetter(UserVisitPK userVisitPK, GetLetterForm form) {
        return CDI.current().select(GetLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLettersResult> getLetters(UserVisitPK userVisitPK, GetLettersForm form) {
        return CDI.current().select(GetLettersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultLetter(UserVisitPK userVisitPK, SetDefaultLetterForm form) {
        return CDI.current().select(SetDefaultLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLetterResult> editLetter(UserVisitPK userVisitPK, EditLetterForm form) {
        return CDI.current().select(EditLetterCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLetter(UserVisitPK userVisitPK, DeleteLetterForm form) {
        return CDI.current().select(DeleteLetterCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLetterDescription(UserVisitPK userVisitPK, CreateLetterDescriptionForm form) {
        return CDI.current().select(CreateLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterDescriptionsResult> getLetterDescriptions(UserVisitPK userVisitPK, GetLetterDescriptionsForm form) {
        return CDI.current().select(GetLetterDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLetterDescriptionResult> editLetterDescription(UserVisitPK userVisitPK, EditLetterDescriptionForm form) {
        return CDI.current().select(EditLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLetterDescription(UserVisitPK userVisitPK, DeleteLetterDescriptionForm form) {
        return CDI.current().select(DeleteLetterDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createLetterContactMechanismPurpose(UserVisitPK userVisitPK, CreateLetterContactMechanismPurposeForm form) {
        return CDI.current().select(CreateLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetLetterContactMechanismPurposesResult> getLetterContactMechanismPurposes(UserVisitPK userVisitPK, GetLetterContactMechanismPurposesForm form) {
        return CDI.current().select(GetLetterContactMechanismPurposesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditLetterContactMechanismPurposeResult> editLetterContactMechanismPurpose(UserVisitPK userVisitPK, EditLetterContactMechanismPurposeForm form) {
        return CDI.current().select(EditLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteLetterContactMechanismPurpose(UserVisitPK userVisitPK, DeleteLetterContactMechanismPurposeForm form) {
        return CDI.current().select(DeleteLetterContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<GetQueuedLetterResult> getQueuedLetter(UserVisitPK userVisitPK, GetQueuedLetterForm form) {
        return CDI.current().select(GetQueuedLetterCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetQueuedLettersResult> getQueuedLetters(UserVisitPK userVisitPK, GetQueuedLettersForm form) {
        return CDI.current().select(GetQueuedLettersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteQueuedLetter(UserVisitPK userVisitPK, DeleteQueuedLetterForm form) {
        return CDI.current().select(DeleteQueuedLetterCommand.class).get().run(userVisitPK, form);
    }

}
