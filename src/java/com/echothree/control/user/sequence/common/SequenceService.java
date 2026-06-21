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
import com.echothree.control.user.sequence.common.result.*;
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
    
    CommandResult<CreateSequenceTypeResult> createSequenceType(UserVisitPK userVisitPK, CreateSequenceTypeForm form);
    
    CommandResult<GetSequenceTypesResult> getSequenceTypes(UserVisitPK userVisitPK, GetSequenceTypesForm form);
    
    CommandResult<GetSequenceTypeResult> getSequenceType(UserVisitPK userVisitPK, GetSequenceTypeForm form);
    
    CommandResult<GetSequenceTypeChoicesResult> getSequenceTypeChoices(UserVisitPK userVisitPK, GetSequenceTypeChoicesForm form);
    
    CommandResult<?> setDefaultSequenceType(UserVisitPK userVisitPK, SetDefaultSequenceTypeForm form);
    
    CommandResult<EditSequenceTypeResult> editSequenceType(UserVisitPK userVisitPK, EditSequenceTypeForm form);
    
    CommandResult<?> deleteSequenceType(UserVisitPK userVisitPK, DeleteSequenceTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceTypeDescription(UserVisitPK userVisitPK, CreateSequenceTypeDescriptionForm form);
    
    CommandResult<GetSequenceTypeDescriptionsResult> getSequenceTypeDescriptions(UserVisitPK userVisitPK, GetSequenceTypeDescriptionsForm form);
    
    CommandResult<EditSequenceTypeDescriptionResult> editSequenceTypeDescription(UserVisitPK userVisitPK, EditSequenceTypeDescriptionForm form);
    
    CommandResult<?> deleteSequenceTypeDescription(UserVisitPK userVisitPK, DeleteSequenceTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceChecksumType(UserVisitPK userVisitPK, CreateSequenceChecksumTypeForm form);

    CommandResult<GetSequenceChecksumTypesResult> getSequenceChecksumTypes(UserVisitPK userVisitPK, GetSequenceChecksumTypesForm form);

    CommandResult<GetSequenceChecksumTypeResult> getSequenceChecksumType(UserVisitPK userVisitPK, GetSequenceChecksumTypeForm form);

    CommandResult<GetSequenceChecksumTypeChoicesResult> getSequenceChecksumTypeChoices(UserVisitPK userVisitPK, GetSequenceChecksumTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceChecksumTypeDescription(UserVisitPK userVisitPK, CreateSequenceChecksumTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceEncoderType(UserVisitPK userVisitPK, CreateSequenceEncoderTypeForm form);

    CommandResult<GetSequenceEncoderTypesResult> getSequenceEncoderTypes(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form);

    CommandResult<GetSequenceEncoderTypeResult> getSequenceEncoderType(UserVisitPK userVisitPK, GetSequenceEncoderTypeForm form);
    
    CommandResult<GetSequenceEncoderTypeChoicesResult> getSequenceEncoderTypeChoices(UserVisitPK userVisitPK, GetSequenceEncoderTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceEncoderTypeDescription(UserVisitPK userVisitPK, CreateSequenceEncoderTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequences
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSequenceResult> createSequence(UserVisitPK userVisitPK, CreateSequenceForm form);
    
    CommandResult<GetSequencesResult> getSequences(UserVisitPK userVisitPK, GetSequencesForm form);
    
    CommandResult<GetSequenceResult> getSequence(UserVisitPK userVisitPK, GetSequenceForm form);
    
    CommandResult<?> setDefaultSequence(UserVisitPK userVisitPK, SetDefaultSequenceForm form);
    
    CommandResult<GetSequenceChoicesResult> getSequenceChoices(UserVisitPK userVisitPK, GetSequenceChoicesForm form);
    
    CommandResult<EditSequenceResult> editSequence(UserVisitPK userVisitPK, EditSequenceForm form);
    
    CommandResult<?> deleteSequence(UserVisitPK userVisitPK, DeleteSequenceForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSequenceDescription(UserVisitPK userVisitPK, CreateSequenceDescriptionForm form);
    
    CommandResult<GetSequenceDescriptionsResult> getSequenceDescriptions(UserVisitPK userVisitPK, GetSequenceDescriptionsForm form);
    
    CommandResult<EditSequenceDescriptionResult> editSequenceDescription(UserVisitPK userVisitPK, EditSequenceDescriptionForm form);
    
    CommandResult<?> deleteSequenceDescription(UserVisitPK userVisitPK, DeleteSequenceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //    Sequence Values
    // -------------------------------------------------------------------------
    
    CommandResult<?> setSequenceValue(UserVisitPK userVisitPK, SetSequenceValueForm form);

    CommandResult<GetSequenceValueResult> getSequenceValue(UserVisitPK userVisitPK, GetSequenceValueForm form);

    CommandResult<GetNextSequenceValueResult> getNextSequenceValue(UserVisitPK userVisitPK, GetNextSequenceValueForm form);

}
