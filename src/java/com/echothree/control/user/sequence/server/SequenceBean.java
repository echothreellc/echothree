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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateSequenceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypes(UserVisitPK userVisitPK, GetSequenceTypesForm form) {
        return CDI.current().select(GetSequenceTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceType(UserVisitPK userVisitPK, GetSequenceTypeForm form) {
        return CDI.current().select(GetSequenceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypeChoices(UserVisitPK userVisitPK, GetSequenceTypeChoicesForm form) {
        return CDI.current().select(GetSequenceTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSequenceType(UserVisitPK userVisitPK, SetDefaultSequenceTypeForm form) {
        return CDI.current().select(SetDefaultSequenceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceType(UserVisitPK userVisitPK, EditSequenceTypeForm form) {
        return CDI.current().select(EditSequenceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceType(UserVisitPK userVisitPK, DeleteSequenceTypeForm form) {
        return CDI.current().select(DeleteSequenceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceTypeDescription(UserVisitPK userVisitPK, CreateSequenceTypeDescriptionForm form) {
        return CDI.current().select(CreateSequenceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceTypeDescriptions(UserVisitPK userVisitPK, GetSequenceTypeDescriptionsForm form) {
        return CDI.current().select(GetSequenceTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceTypeDescription(UserVisitPK userVisitPK, EditSequenceTypeDescriptionForm form) {
        return CDI.current().select(EditSequenceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceTypeDescription(UserVisitPK userVisitPK, DeleteSequenceTypeDescriptionForm form) {
        return CDI.current().select(DeleteSequenceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceChecksumType(UserVisitPK userVisitPK, CreateSequenceChecksumTypeForm form) {
        return CDI.current().select(CreateSequenceChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumTypes(UserVisitPK userVisitPK, GetSequenceChecksumTypesForm form) {
        return CDI.current().select(GetSequenceChecksumTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumType(UserVisitPK userVisitPK, GetSequenceChecksumTypeForm form) {
        return CDI.current().select(GetSequenceChecksumTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceChecksumTypeChoices(UserVisitPK userVisitPK, GetSequenceChecksumTypeChoicesForm form) {
        return CDI.current().select(GetSequenceChecksumTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceChecksumTypeDescription(UserVisitPK userVisitPK, CreateSequenceChecksumTypeDescriptionForm form) {
        return CDI.current().select(CreateSequenceChecksumTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceEncoderType(UserVisitPK userVisitPK, CreateSequenceEncoderTypeForm form) {
        return CDI.current().select(CreateSequenceEncoderTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceEncoderTypes(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form) {
        return CDI.current().select(GetSequenceEncoderTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceEncoderType(UserVisitPK userVisitPK, GetSequenceEncoderTypeForm form) {
        return CDI.current().select(GetSequenceEncoderTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceEncoderTypeChoices(UserVisitPK userVisitPK, GetSequenceEncoderTypeChoicesForm form) {
        return CDI.current().select(GetSequenceEncoderTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceEncoderTypeDescription(UserVisitPK userVisitPK, CreateSequenceEncoderTypeDescriptionForm form) {
        return CDI.current().select(CreateSequenceEncoderTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequences
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequence(UserVisitPK userVisitPK, CreateSequenceForm form) {
        return CDI.current().select(CreateSequenceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequences(UserVisitPK userVisitPK, GetSequencesForm form) {
        return CDI.current().select(GetSequencesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequence(UserVisitPK userVisitPK, GetSequenceForm form) {
        return CDI.current().select(GetSequenceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSequence(UserVisitPK userVisitPK, SetDefaultSequenceForm form) {
        return CDI.current().select(SetDefaultSequenceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceChoices(UserVisitPK userVisitPK, GetSequenceChoicesForm form) {
        return CDI.current().select(GetSequenceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequence(UserVisitPK userVisitPK, EditSequenceForm form) {
        return CDI.current().select(EditSequenceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequence(UserVisitPK userVisitPK, DeleteSequenceForm form) {
        return CDI.current().select(DeleteSequenceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sequence Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSequenceDescription(UserVisitPK userVisitPK, CreateSequenceDescriptionForm form) {
        return CDI.current().select(CreateSequenceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSequenceDescriptions(UserVisitPK userVisitPK, GetSequenceDescriptionsForm form) {
        return CDI.current().select(GetSequenceDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSequenceDescription(UserVisitPK userVisitPK, EditSequenceDescriptionForm form) {
        return CDI.current().select(EditSequenceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSequenceDescription(UserVisitPK userVisitPK, DeleteSequenceDescriptionForm form) {
        return CDI.current().select(DeleteSequenceDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //    Sequence Values
    // -------------------------------------------------------------------------

    @Override
    public CommandResult setSequenceValue(UserVisitPK userVisitPK, SetSequenceValueForm form) {
        return CDI.current().select(SetSequenceValueCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSequenceValue(UserVisitPK userVisitPK, GetSequenceValueForm form) {
        return CDI.current().select(GetSequenceValueCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getNextSequenceValue(UserVisitPK userVisitPK, GetNextSequenceValueForm form) {
        return CDI.current().select(GetNextSequenceValueCommand.class).get().run(userVisitPK, form);
    }

}
