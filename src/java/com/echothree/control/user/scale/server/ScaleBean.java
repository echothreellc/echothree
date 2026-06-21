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

package com.echothree.control.user.scale.server;

import com.echothree.control.user.scale.common.ScaleRemote;
import com.echothree.control.user.scale.common.form.*;
import com.echothree.control.user.scale.common.result.*;
import com.echothree.control.user.scale.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class ScaleBean
        extends ScaleFormsImpl
        implements ScaleRemote, ScaleLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ScaleBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Scale Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createScaleType(UserVisitPK userVisitPK, CreateScaleTypeForm form) {
        return CDI.current().select(CreateScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleTypeChoicesResult> getScaleTypeChoices(UserVisitPK userVisitPK, GetScaleTypeChoicesForm form) {
        return CDI.current().select(GetScaleTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleTypeResult> getScaleType(UserVisitPK userVisitPK, GetScaleTypeForm form) {
        return CDI.current().select(GetScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleTypesResult> getScaleTypes(UserVisitPK userVisitPK, GetScaleTypesForm form) {
        return CDI.current().select(GetScaleTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultScaleType(UserVisitPK userVisitPK, SetDefaultScaleTypeForm form) {
        return CDI.current().select(SetDefaultScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditScaleTypeResult> editScaleType(UserVisitPK userVisitPK, EditScaleTypeForm form) {
        return CDI.current().select(EditScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteScaleType(UserVisitPK userVisitPK, DeleteScaleTypeForm form) {
        return CDI.current().select(DeleteScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createScaleTypeDescription(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form) {
        return CDI.current().select(CreateScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleTypeDescriptionResult> getScaleTypeDescription(UserVisitPK userVisitPK, GetScaleTypeDescriptionForm form) {
        return CDI.current().select(GetScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleTypeDescriptionsResult> getScaleTypeDescriptions(UserVisitPK userVisitPK, GetScaleTypeDescriptionsForm form) {
        return CDI.current().select(GetScaleTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditScaleTypeDescriptionResult> editScaleTypeDescription(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form) {
        return CDI.current().select(EditScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteScaleTypeDescription(UserVisitPK userVisitPK, DeleteScaleTypeDescriptionForm form) {
        return CDI.current().select(DeleteScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

     // -------------------------------------------------------------------------
    //   Scales
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createScale(UserVisitPK userVisitPK, CreateScaleForm form) {
        return CDI.current().select(CreateScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetScaleChoicesResult> getScaleChoices(UserVisitPK userVisitPK, GetScaleChoicesForm form) {
        return CDI.current().select(GetScaleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetScaleResult> getScale(UserVisitPK userVisitPK, GetScaleForm form) {
        return CDI.current().select(GetScaleCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScalesResult> getScales(UserVisitPK userVisitPK, GetScalesForm form) {
        return CDI.current().select(GetScalesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultScale(UserVisitPK userVisitPK, SetDefaultScaleForm form) {
        return CDI.current().select(SetDefaultScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditScaleResult> editScale(UserVisitPK userVisitPK, EditScaleForm form) {
        return CDI.current().select(EditScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteScale(UserVisitPK userVisitPK, DeleteScaleForm form) {
        return CDI.current().select(DeleteScaleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createScaleDescription(UserVisitPK userVisitPK, CreateScaleDescriptionForm form) {
        return CDI.current().select(CreateScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetScaleDescriptionResult> getScaleDescription(UserVisitPK userVisitPK, GetScaleDescriptionForm form) {
        return CDI.current().select(GetScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleDescriptionsResult> getScaleDescriptions(UserVisitPK userVisitPK, GetScaleDescriptionsForm form) {
        return CDI.current().select(GetScaleDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditScaleDescriptionResult> editScaleDescription(UserVisitPK userVisitPK, EditScaleDescriptionForm form) {
        return CDI.current().select(EditScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteScaleDescription(UserVisitPK userVisitPK, DeleteScaleDescriptionForm form) {
        return CDI.current().select(DeleteScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createScaleUseType(UserVisitPK userVisitPK, CreateScaleUseTypeForm form) {
        return CDI.current().select(CreateScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleUseTypeChoicesResult> getScaleUseTypeChoices(UserVisitPK userVisitPK, GetScaleUseTypeChoicesForm form) {
        return CDI.current().select(GetScaleUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleUseTypeResult> getScaleUseType(UserVisitPK userVisitPK, GetScaleUseTypeForm form) {
        return CDI.current().select(GetScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleUseTypesResult> getScaleUseTypes(UserVisitPK userVisitPK, GetScaleUseTypesForm form) {
        return CDI.current().select(GetScaleUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultScaleUseType(UserVisitPK userVisitPK, SetDefaultScaleUseTypeForm form) {
        return CDI.current().select(SetDefaultScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditScaleUseTypeResult> editScaleUseType(UserVisitPK userVisitPK, EditScaleUseTypeForm form) {
        return CDI.current().select(EditScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteScaleUseType(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form) {
        return CDI.current().select(DeleteScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createScaleUseTypeDescription(UserVisitPK userVisitPK, CreateScaleUseTypeDescriptionForm form) {
        return CDI.current().select(CreateScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleUseTypeDescriptionResult> getScaleUseTypeDescription(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionForm form) {
        return CDI.current().select(GetScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetScaleUseTypeDescriptionsResult> getScaleUseTypeDescriptions(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionsForm form) {
        return CDI.current().select(GetScaleUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditScaleUseTypeDescriptionResult> editScaleUseTypeDescription(UserVisitPK userVisitPK, EditScaleUseTypeDescriptionForm form) {
        return CDI.current().select(EditScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteScaleUseTypeDescription(UserVisitPK userVisitPK, DeleteScaleUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Scale Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyScaleUse(UserVisitPK userVisitPK, CreatePartyScaleUseForm form) {
        return CDI.current().select(CreatePartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyScaleUseResult> getPartyScaleUse(UserVisitPK userVisitPK, GetPartyScaleUseForm form) {
        return CDI.current().select(GetPartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyScaleUsesResult> getPartyScaleUses(UserVisitPK userVisitPK, GetPartyScaleUsesForm form) {
        return CDI.current().select(GetPartyScaleUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyScaleUseResult> editPartyScaleUse(UserVisitPK userVisitPK, EditPartyScaleUseForm form) {
        return CDI.current().select(EditPartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyScaleUse(UserVisitPK userVisitPK, DeletePartyScaleUseForm form) {
        return CDI.current().select(DeletePartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

}
