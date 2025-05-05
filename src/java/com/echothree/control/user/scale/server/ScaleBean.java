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

package com.echothree.control.user.scale.server;

import com.echothree.control.user.scale.common.ScaleRemote;
import com.echothree.control.user.scale.common.form.*;
import com.echothree.control.user.scale.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
    public CommandResult createScaleType(UserVisitPK userVisitPK, CreateScaleTypeForm form) {
        return new CreateScaleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeChoices(UserVisitPK userVisitPK, GetScaleTypeChoicesForm form) {
        return new GetScaleTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleType(UserVisitPK userVisitPK, GetScaleTypeForm form) {
        return new GetScaleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypes(UserVisitPK userVisitPK, GetScaleTypesForm form) {
        return new GetScaleTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScaleType(UserVisitPK userVisitPK, SetDefaultScaleTypeForm form) {
        return new SetDefaultScaleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleType(UserVisitPK userVisitPK, EditScaleTypeForm form) {
        return new EditScaleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleType(UserVisitPK userVisitPK, DeleteScaleTypeForm form) {
        return new DeleteScaleTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleTypeDescription(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form) {
        return new CreateScaleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeDescription(UserVisitPK userVisitPK, GetScaleTypeDescriptionForm form) {
        return new GetScaleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeDescriptions(UserVisitPK userVisitPK, GetScaleTypeDescriptionsForm form) {
        return new GetScaleTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleTypeDescription(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form) {
        return new EditScaleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleTypeDescription(UserVisitPK userVisitPK, DeleteScaleTypeDescriptionForm form) {
        return new DeleteScaleTypeDescriptionCommand().run(userVisitPK, form);
    }

     // -------------------------------------------------------------------------
    //   Scales
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScale(UserVisitPK userVisitPK, CreateScaleForm form) {
        return new CreateScaleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScaleChoices(UserVisitPK userVisitPK, GetScaleChoicesForm form) {
        return new GetScaleChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScale(UserVisitPK userVisitPK, GetScaleForm form) {
        return new GetScaleCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScales(UserVisitPK userVisitPK, GetScalesForm form) {
        return new GetScalesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScale(UserVisitPK userVisitPK, SetDefaultScaleForm form) {
        return new SetDefaultScaleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editScale(UserVisitPK userVisitPK, EditScaleForm form) {
        return new EditScaleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteScale(UserVisitPK userVisitPK, DeleteScaleForm form) {
        return new DeleteScaleCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScaleDescription(UserVisitPK userVisitPK, CreateScaleDescriptionForm form) {
        return new CreateScaleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScaleDescription(UserVisitPK userVisitPK, GetScaleDescriptionForm form) {
        return new GetScaleDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleDescriptions(UserVisitPK userVisitPK, GetScaleDescriptionsForm form) {
        return new GetScaleDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleDescription(UserVisitPK userVisitPK, EditScaleDescriptionForm form) {
        return new EditScaleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteScaleDescription(UserVisitPK userVisitPK, DeleteScaleDescriptionForm form) {
        return new DeleteScaleDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseType(UserVisitPK userVisitPK, CreateScaleUseTypeForm form) {
        return new CreateScaleUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeChoices(UserVisitPK userVisitPK, GetScaleUseTypeChoicesForm form) {
        return new GetScaleUseTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseType(UserVisitPK userVisitPK, GetScaleUseTypeForm form) {
        return new GetScaleUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypes(UserVisitPK userVisitPK, GetScaleUseTypesForm form) {
        return new GetScaleUseTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScaleUseType(UserVisitPK userVisitPK, SetDefaultScaleUseTypeForm form) {
        return new SetDefaultScaleUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleUseType(UserVisitPK userVisitPK, EditScaleUseTypeForm form) {
        return new EditScaleUseTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleUseType(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form) {
        return new DeleteScaleUseTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseTypeDescription(UserVisitPK userVisitPK, CreateScaleUseTypeDescriptionForm form) {
        return new CreateScaleUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeDescription(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionForm form) {
        return new GetScaleUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeDescriptions(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionsForm form) {
        return new GetScaleUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleUseTypeDescription(UserVisitPK userVisitPK, EditScaleUseTypeDescriptionForm form) {
        return new EditScaleUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleUseTypeDescription(UserVisitPK userVisitPK, DeleteScaleUseTypeDescriptionForm form) {
        return new DeleteScaleUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Scale Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyScaleUse(UserVisitPK userVisitPK, CreatePartyScaleUseForm form) {
        return new CreatePartyScaleUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyScaleUse(UserVisitPK userVisitPK, GetPartyScaleUseForm form) {
        return new GetPartyScaleUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyScaleUses(UserVisitPK userVisitPK, GetPartyScaleUsesForm form) {
        return new GetPartyScaleUsesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyScaleUse(UserVisitPK userVisitPK, EditPartyScaleUseForm form) {
        return new EditPartyScaleUseCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyScaleUse(UserVisitPK userVisitPK, DeletePartyScaleUseForm form) {
        return new DeletePartyScaleUseCommand().run(userVisitPK, form);
    }

}
