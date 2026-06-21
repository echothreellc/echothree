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
import com.echothree.util.common.command.VoidResult;

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
    
    CommandResult<VoidResult> setDefaultSequenceType(UserVisitPK userVisitPK, SetDefaultSequenceTypeForm form);
    
    CommandResult<EditSequenceTypeResult> editSequenceType(UserVisitPK userVisitPK, EditSequenceTypeForm form);
    
    CommandResult<VoidResult> deleteSequenceType(UserVisitPK userVisitPK, DeleteSequenceTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceTypeDescription(UserVisitPK userVisitPK, CreateSequenceTypeDescriptionForm form);
    
    CommandResult<GetSequenceTypeDescriptionsResult> getSequenceTypeDescriptions(UserVisitPK userVisitPK, GetSequenceTypeDescriptionsForm form);
    
    CommandResult<EditSequenceTypeDescriptionResult> editSequenceTypeDescription(UserVisitPK userVisitPK, EditSequenceTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteSequenceTypeDescription(UserVisitPK userVisitPK, DeleteSequenceTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceChecksumType(UserVisitPK userVisitPK, CreateSequenceChecksumTypeForm form);

    CommandResult<GetSequenceChecksumTypesResult> getSequenceChecksumTypes(UserVisitPK userVisitPK, GetSequenceChecksumTypesForm form);

    CommandResult<GetSequenceChecksumTypeResult> getSequenceChecksumType(UserVisitPK userVisitPK, GetSequenceChecksumTypeForm form);

    CommandResult<GetSequenceChecksumTypeChoicesResult> getSequenceChecksumTypeChoices(UserVisitPK userVisitPK, GetSequenceChecksumTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceChecksumTypeDescription(UserVisitPK userVisitPK, CreateSequenceChecksumTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceEncoderType(UserVisitPK userVisitPK, CreateSequenceEncoderTypeForm form);

    CommandResult<GetSequenceEncoderTypesResult> getSequenceEncoderTypes(UserVisitPK userVisitPK, GetSequenceEncoderTypesForm form);

    CommandResult<GetSequenceEncoderTypeResult> getSequenceEncoderType(UserVisitPK userVisitPK, GetSequenceEncoderTypeForm form);
    
    CommandResult<GetSequenceEncoderTypeChoicesResult> getSequenceEncoderTypeChoices(UserVisitPK userVisitPK, GetSequenceEncoderTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceEncoderTypeDescription(UserVisitPK userVisitPK, CreateSequenceEncoderTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Sequences
    // -------------------------------------------------------------------------
    
    CommandResult<CreateSequenceResult> createSequence(UserVisitPK userVisitPK, CreateSequenceForm form);
    
    CommandResult<GetSequencesResult> getSequences(UserVisitPK userVisitPK, GetSequencesForm form);
    
    CommandResult<GetSequenceResult> getSequence(UserVisitPK userVisitPK, GetSequenceForm form);
    
    CommandResult<VoidResult> setDefaultSequence(UserVisitPK userVisitPK, SetDefaultSequenceForm form);
    
    CommandResult<GetSequenceChoicesResult> getSequenceChoices(UserVisitPK userVisitPK, GetSequenceChoicesForm form);
    
    CommandResult<EditSequenceResult> editSequence(UserVisitPK userVisitPK, EditSequenceForm form);
    
    CommandResult<VoidResult> deleteSequence(UserVisitPK userVisitPK, DeleteSequenceForm form);
    
    // -------------------------------------------------------------------------
    //   Sequence Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSequenceDescription(UserVisitPK userVisitPK, CreateSequenceDescriptionForm form);
    
    CommandResult<GetSequenceDescriptionsResult> getSequenceDescriptions(UserVisitPK userVisitPK, GetSequenceDescriptionsForm form);
    
    CommandResult<EditSequenceDescriptionResult> editSequenceDescription(UserVisitPK userVisitPK, EditSequenceDescriptionForm form);
    
    CommandResult<VoidResult> deleteSequenceDescription(UserVisitPK userVisitPK, DeleteSequenceDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //    Sequence Values
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> setSequenceValue(UserVisitPK userVisitPK, SetSequenceValueForm form);

    CommandResult<GetSequenceValueResult> getSequenceValue(UserVisitPK userVisitPK, GetSequenceValueForm form);

    CommandResult<GetNextSequenceValueResult> getNextSequenceValue(UserVisitPK userVisitPK, GetNextSequenceValueForm form);

}
