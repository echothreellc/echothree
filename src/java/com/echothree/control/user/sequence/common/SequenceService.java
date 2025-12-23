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

package com.echothree.control.user.sequence.common;

import com.echothree.control.user.sequence.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SequenceService
        extends SequenceForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Sequence Types
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceType(UserVisitPK userVisitPK, CreateSequenceTypeForm form);
    
    CommandResult getSequenceTypes(UserVisitPK userVisitPK, GetSequenceTypesForm form);
    
    CommandResult getSequenceType(UserVisitPK userVisitPK, GetSequenceTypeForm form);
    
    CommandResult getSequenceTypeChoices(UserVisitPK userVisitPK, GetSequenceTypeChoicesForm form);
    
    CommandResult setDefaultSequenceType(UserVisitPK userVisitPK, SetDefaultSequenceTypeForm form);
    
    CommandResult editSequenceType(UserVisitPK userVisitPK, EditSequenceTypeForm form);
    
    CommandResult deleteSequenceType(UserVisitPK userVisitPK, DeleteSequenceTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceTypeDescription(UserVisitPK userVisitPK, CreateSequenceTypeDescriptionForm form);
    
    CommandResult getSequenceTypeDescriptions(UserVisitPK userVisitPK, GetSequenceTypeDescriptionsForm form);
    
    CommandResult editSequenceTypeDescription(UserVisitPK userVisitPK, EditSequenceTypeDescriptionForm form);
    
    CommandResult deleteSequenceTypeDescription(UserVisitPK userVisitPK, DeleteSequenceTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Types
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceChecksumType(UserVisitPK userVisitPK, CreateSequenceChecksumTypeForm form);

    CommandResult getSequenceChecksumTypes(UserVisitPK userVisitPK, GetSequenceChecksumTypesForm form);

    CommandResult getSequenceChecksumType(UserVisitPK userVisitPK, GetSequenceChecksumTypeForm form);

    CommandResult getSequenceChecksumTypeChoices(UserVisitPK userVisitPK, GetSequenceChecksumTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceChecksumTypeDescription(UserVisitPK userVisitPK, CreateSequenceChecksumTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Types
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceEncoderType(UserVisitPK userVisitPK, CreateSequenceEncoderTypeForm form);

    CommandResult getSequenceEncoderTypes(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form);

    CommandResult getSequenceEncoderType(UserVisitPK userVisitPK, GetSequenceEncoderTypeForm form);
    
    CommandResult getSequenceEncoderTypeChoices(UserVisitPK userVisitPK, GetSequenceEncoderTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceEncoderTypeDescription(UserVisitPK userVisitPK, CreateSequenceEncoderTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequences
    // -------------------------------------------------------------------------
    
    CommandResult createSequence(UserVisitPK userVisitPK, CreateSequenceForm form);
    
    CommandResult getSequences(UserVisitPK userVisitPK, GetSequencesForm form);
    
    CommandResult getSequence(UserVisitPK userVisitPK, GetSequenceForm form);
    
    CommandResult setDefaultSequence(UserVisitPK userVisitPK, SetDefaultSequenceForm form);
    
    CommandResult getSequenceChoices(UserVisitPK userVisitPK, GetSequenceChoicesForm form);
    
    CommandResult editSequence(UserVisitPK userVisitPK, EditSequenceForm form);
    
    CommandResult deleteSequence(UserVisitPK userVisitPK, DeleteSequenceForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSequenceDescription(UserVisitPK userVisitPK, CreateSequenceDescriptionForm form);
    
    CommandResult getSequenceDescriptions(UserVisitPK userVisitPK, GetSequenceDescriptionsForm form);
    
    CommandResult editSequenceDescription(UserVisitPK userVisitPK, EditSequenceDescriptionForm form);
    
    CommandResult deleteSequenceDescription(UserVisitPK userVisitPK, DeleteSequenceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //    Sequence Values
    // -------------------------------------------------------------------------
    
    CommandResult setSequenceValue(UserVisitPK userVisitPK, SetSequenceValueForm form);

    CommandResult getSequenceValue(UserVisitPK userVisitPK, GetSequenceValueForm form);

    CommandResult getNextSequenceValue(UserVisitPK userVisitPK, GetNextSequenceValueForm form);

}
