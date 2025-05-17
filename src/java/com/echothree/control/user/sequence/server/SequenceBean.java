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

package com.echothree.control.user.sequence.server;

import com.echothree.control.user.sequence.common.SequenceRemote;
import com.echothree.control.user.sequence.common.form.*;
import com.echothree.control.user.sequence.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SequenceBean
        extends SequenceFormsImpl
        implements SequenceRemote, SequenceLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SequenceBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceType(UserVisitPK userVisitPK, CreateSequenceTypeForm form) {
        return new CreateSequenceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypes(UserVisitPK userVisitPK, GetSequenceTypesForm form) {
        return new GetSequenceTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceType(UserVisitPK userVisitPK, GetSequenceTypeForm form) {
        return new GetSequenceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypeChoices(UserVisitPK userVisitPK, GetSequenceTypeChoicesForm form) {
        return new GetSequenceTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSequenceType(UserVisitPK userVisitPK, SetDefaultSequenceTypeForm form) {
        return new SetDefaultSequenceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceType(UserVisitPK userVisitPK, EditSequenceTypeForm form) {
        return new EditSequenceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceType(UserVisitPK userVisitPK, DeleteSequenceTypeForm form) {
        return new DeleteSequenceTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceTypeDescription(UserVisitPK userVisitPK, CreateSequenceTypeDescriptionForm form) {
        return new CreateSequenceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypeDescriptions(UserVisitPK userVisitPK, GetSequenceTypeDescriptionsForm form) {
        return new GetSequenceTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceTypeDescription(UserVisitPK userVisitPK, EditSequenceTypeDescriptionForm form) {
        return new EditSequenceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceTypeDescription(UserVisitPK userVisitPK, DeleteSequenceTypeDescriptionForm form) {
        return new DeleteSequenceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceChecksumType(UserVisitPK userVisitPK, CreateSequenceChecksumTypeForm form) {
        return new CreateSequenceChecksumTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumTypes(UserVisitPK userVisitPK, GetSequenceChecksumTypesForm form) {
        return new GetSequenceChecksumTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumType(UserVisitPK userVisitPK, GetSequenceChecksumTypeForm form) {
        return new GetSequenceChecksumTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumTypeChoices(UserVisitPK userVisitPK, GetSequenceChecksumTypeChoicesForm form) {
        return new GetSequenceChecksumTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceChecksumTypeDescription(UserVisitPK userVisitPK, CreateSequenceChecksumTypeDescriptionForm form) {
        return new CreateSequenceChecksumTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceEncoderType(UserVisitPK userVisitPK, CreateSequenceEncoderTypeForm form) {
        return new CreateSequenceEncoderTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceEncoderTypes(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form) {
        return new GetSequenceEncoderTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceEncoderType(UserVisitPK userVisitPK, GetSequenceEncoderTypeForm form) {
        return new GetSequenceEncoderTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceEncoderTypeChoices(UserVisitPK userVisitPK, GetSequenceEncoderTypeChoicesForm form) {
        return new GetSequenceEncoderTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceEncoderTypeDescription(UserVisitPK userVisitPK, CreateSequenceEncoderTypeDescriptionForm form) {
        return new CreateSequenceEncoderTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequences
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequence(UserVisitPK userVisitPK, CreateSequenceForm form) {
        return new CreateSequenceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequences(UserVisitPK userVisitPK, GetSequencesForm form) {
        return new GetSequencesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequence(UserVisitPK userVisitPK, GetSequenceForm form) {
        return new GetSequenceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSequence(UserVisitPK userVisitPK, SetDefaultSequenceForm form) {
        return new SetDefaultSequenceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceChoices(UserVisitPK userVisitPK, GetSequenceChoicesForm form) {
        return new GetSequenceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequence(UserVisitPK userVisitPK, EditSequenceForm form) {
        return new EditSequenceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequence(UserVisitPK userVisitPK, DeleteSequenceForm form) {
        return new DeleteSequenceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceDescription(UserVisitPK userVisitPK, CreateSequenceDescriptionForm form) {
        return new CreateSequenceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceDescriptions(UserVisitPK userVisitPK, GetSequenceDescriptionsForm form) {
        return new GetSequenceDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceDescription(UserVisitPK userVisitPK, EditSequenceDescriptionForm form) {
        return new EditSequenceDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceDescription(UserVisitPK userVisitPK, DeleteSequenceDescriptionForm form) {
        return new DeleteSequenceDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //    Sequence Values
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult setSequenceValue(UserVisitPK userVisitPK, SetSequenceValueForm form) {
        return new SetSequenceValueCommand().run(userVisitPK, form);
    }
    
}
