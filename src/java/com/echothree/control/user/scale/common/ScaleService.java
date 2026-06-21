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

package com.echothree.control.user.scale.common;

import com.echothree.control.user.scale.common.form.*;
import com.echothree.control.user.scale.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ScaleService
        extends ScaleForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Scale Types
    // -------------------------------------------------------------------------

    CommandResult<?> createScaleType(UserVisitPK userVisitPK, CreateScaleTypeForm form);

    CommandResult<GetScaleTypeChoicesResult> getScaleTypeChoices(UserVisitPK userVisitPK, GetScaleTypeChoicesForm form);

    CommandResult<GetScaleTypeResult> getScaleType(UserVisitPK userVisitPK, GetScaleTypeForm form);

    CommandResult<GetScaleTypesResult> getScaleTypes(UserVisitPK userVisitPK, GetScaleTypesForm form);

    CommandResult<?> setDefaultScaleType(UserVisitPK userVisitPK, SetDefaultScaleTypeForm form);

    CommandResult<EditScaleTypeResult> editScaleType(UserVisitPK userVisitPK, EditScaleTypeForm form);

    CommandResult<?> deleteScaleType(UserVisitPK userVisitPK, DeleteScaleTypeForm form);

    // -------------------------------------------------------------------------
    //   Scale Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createScaleTypeDescription(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form);

    CommandResult<GetScaleTypeDescriptionResult> getScaleTypeDescription(UserVisitPK userVisitPK, GetScaleTypeDescriptionForm form);

    CommandResult<GetScaleTypeDescriptionsResult> getScaleTypeDescriptions(UserVisitPK userVisitPK, GetScaleTypeDescriptionsForm form);

    CommandResult<EditScaleTypeDescriptionResult> editScaleTypeDescription(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form);

    CommandResult<?> deleteScaleTypeDescription(UserVisitPK userVisitPK, DeleteScaleTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Scales
    // -------------------------------------------------------------------------
    
    CommandResult<?> createScale(UserVisitPK userVisitPK, CreateScaleForm form);
    
    CommandResult<GetScaleChoicesResult> getScaleChoices(UserVisitPK userVisitPK, GetScaleChoicesForm form);

    CommandResult<GetScaleResult> getScale(UserVisitPK userVisitPK, GetScaleForm form);

    CommandResult<GetScalesResult> getScales(UserVisitPK userVisitPK, GetScalesForm form);
    
    CommandResult<?> setDefaultScale(UserVisitPK userVisitPK, SetDefaultScaleForm form);
    
    CommandResult<EditScaleResult> editScale(UserVisitPK userVisitPK, EditScaleForm form);
    
    CommandResult<?> deleteScale(UserVisitPK userVisitPK, DeleteScaleForm form);
    
    // -------------------------------------------------------------------------
    //   Scale Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createScaleDescription(UserVisitPK userVisitPK, CreateScaleDescriptionForm form);
    
    CommandResult<GetScaleDescriptionResult> getScaleDescription(UserVisitPK userVisitPK, GetScaleDescriptionForm form);

    CommandResult<GetScaleDescriptionsResult> getScaleDescriptions(UserVisitPK userVisitPK, GetScaleDescriptionsForm form);

    CommandResult<EditScaleDescriptionResult> editScaleDescription(UserVisitPK userVisitPK, EditScaleDescriptionForm form);
    
    CommandResult<?> deleteScaleDescription(UserVisitPK userVisitPK, DeleteScaleDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Scale Use Types
    // -------------------------------------------------------------------------

    CommandResult<?> createScaleUseType(UserVisitPK userVisitPK, CreateScaleUseTypeForm form);

    CommandResult<GetScaleUseTypeChoicesResult> getScaleUseTypeChoices(UserVisitPK userVisitPK, GetScaleUseTypeChoicesForm form);

    CommandResult<GetScaleUseTypeResult> getScaleUseType(UserVisitPK userVisitPK, GetScaleUseTypeForm form);

    CommandResult<GetScaleUseTypesResult> getScaleUseTypes(UserVisitPK userVisitPK, GetScaleUseTypesForm form);

    CommandResult<?> setDefaultScaleUseType(UserVisitPK userVisitPK, SetDefaultScaleUseTypeForm form);

    CommandResult<EditScaleUseTypeResult> editScaleUseType(UserVisitPK userVisitPK, EditScaleUseTypeForm form);

    CommandResult<?> deleteScaleUseType(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form);

    // -------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createScaleUseTypeDescription(UserVisitPK userVisitPK, CreateScaleUseTypeDescriptionForm form);

    CommandResult<GetScaleUseTypeDescriptionResult> getScaleUseTypeDescription(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionForm form);

    CommandResult<GetScaleUseTypeDescriptionsResult> getScaleUseTypeDescriptions(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionsForm form);

    CommandResult<EditScaleUseTypeDescriptionResult> editScaleUseTypeDescription(UserVisitPK userVisitPK, EditScaleUseTypeDescriptionForm form);

    CommandResult<?> deleteScaleUseTypeDescription(UserVisitPK userVisitPK, DeleteScaleUseTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Party Scale Uses
    // -------------------------------------------------------------------------

    CommandResult<?> createPartyScaleUse(UserVisitPK userVisitPK, CreatePartyScaleUseForm form);

    CommandResult<GetPartyScaleUseResult> getPartyScaleUse(UserVisitPK userVisitPK, GetPartyScaleUseForm form);

    CommandResult<GetPartyScaleUsesResult> getPartyScaleUses(UserVisitPK userVisitPK, GetPartyScaleUsesForm form);

    CommandResult<EditPartyScaleUseResult> editPartyScaleUse(UserVisitPK userVisitPK, EditPartyScaleUseForm form);

    CommandResult<?> deletePartyScaleUse(UserVisitPK userVisitPK, DeletePartyScaleUseForm form);

}
