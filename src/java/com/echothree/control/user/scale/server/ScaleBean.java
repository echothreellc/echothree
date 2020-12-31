// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
        return new CreateScaleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleTypeChoices(UserVisitPK userVisitPK, GetScaleTypeChoicesForm form) {
        return new GetScaleTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleType(UserVisitPK userVisitPK, GetScaleTypeForm form) {
        return new GetScaleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleTypes(UserVisitPK userVisitPK, GetScaleTypesForm form) {
        return new GetScaleTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultScaleType(UserVisitPK userVisitPK, SetDefaultScaleTypeForm form) {
        return new SetDefaultScaleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editScaleType(UserVisitPK userVisitPK, EditScaleTypeForm form) {
        return new EditScaleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteScaleType(UserVisitPK userVisitPK, DeleteScaleTypeForm form) {
        return new DeleteScaleTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Scale Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleTypeDescription(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form) {
        return new CreateScaleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleTypeDescription(UserVisitPK userVisitPK, GetScaleTypeDescriptionForm form) {
        return new GetScaleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleTypeDescriptions(UserVisitPK userVisitPK, GetScaleTypeDescriptionsForm form) {
        return new GetScaleTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editScaleTypeDescription(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form) {
        return new EditScaleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteScaleTypeDescription(UserVisitPK userVisitPK, DeleteScaleTypeDescriptionForm form) {
        return new DeleteScaleTypeDescriptionCommand(userVisitPK, form).run();
    }

     // -------------------------------------------------------------------------
    //   Scales
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScale(UserVisitPK userVisitPK, CreateScaleForm form) {
        return new CreateScaleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getScaleChoices(UserVisitPK userVisitPK, GetScaleChoicesForm form) {
        return new GetScaleChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getScale(UserVisitPK userVisitPK, GetScaleForm form) {
        return new GetScaleCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScales(UserVisitPK userVisitPK, GetScalesForm form) {
        return new GetScalesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultScale(UserVisitPK userVisitPK, SetDefaultScaleForm form) {
        return new SetDefaultScaleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editScale(UserVisitPK userVisitPK, EditScaleForm form) {
        return new EditScaleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteScale(UserVisitPK userVisitPK, DeleteScaleForm form) {
        return new DeleteScaleCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Scale Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScaleDescription(UserVisitPK userVisitPK, CreateScaleDescriptionForm form) {
        return new CreateScaleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getScaleDescription(UserVisitPK userVisitPK, GetScaleDescriptionForm form) {
        return new GetScaleDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleDescriptions(UserVisitPK userVisitPK, GetScaleDescriptionsForm form) {
        return new GetScaleDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editScaleDescription(UserVisitPK userVisitPK, EditScaleDescriptionForm form) {
        return new EditScaleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteScaleDescription(UserVisitPK userVisitPK, DeleteScaleDescriptionForm form) {
        return new DeleteScaleDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Scale Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseType(UserVisitPK userVisitPK, CreateScaleUseTypeForm form) {
        return new CreateScaleUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleUseTypeChoices(UserVisitPK userVisitPK, GetScaleUseTypeChoicesForm form) {
        return new GetScaleUseTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleUseType(UserVisitPK userVisitPK, GetScaleUseTypeForm form) {
        return new GetScaleUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleUseTypes(UserVisitPK userVisitPK, GetScaleUseTypesForm form) {
        return new GetScaleUseTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultScaleUseType(UserVisitPK userVisitPK, SetDefaultScaleUseTypeForm form) {
        return new SetDefaultScaleUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editScaleUseType(UserVisitPK userVisitPK, EditScaleUseTypeForm form) {
        return new EditScaleUseTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteScaleUseType(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form) {
        return new DeleteScaleUseTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseTypeDescription(UserVisitPK userVisitPK, CreateScaleUseTypeDescriptionForm form) {
        return new CreateScaleUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleUseTypeDescription(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionForm form) {
        return new GetScaleUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getScaleUseTypeDescriptions(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionsForm form) {
        return new GetScaleUseTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editScaleUseTypeDescription(UserVisitPK userVisitPK, EditScaleUseTypeDescriptionForm form) {
        return new EditScaleUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteScaleUseTypeDescription(UserVisitPK userVisitPK, DeleteScaleUseTypeDescriptionForm form) {
        return new DeleteScaleUseTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Scale Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyScaleUse(UserVisitPK userVisitPK, CreatePartyScaleUseForm form) {
        return new CreatePartyScaleUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyScaleUse(UserVisitPK userVisitPK, GetPartyScaleUseForm form) {
        return new GetPartyScaleUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyScaleUses(UserVisitPK userVisitPK, GetPartyScaleUsesForm form) {
        return new GetPartyScaleUsesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyScaleUse(UserVisitPK userVisitPK, EditPartyScaleUseForm form) {
        return new EditPartyScaleUseCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyScaleUse(UserVisitPK userVisitPK, DeletePartyScaleUseForm form) {
        return new DeletePartyScaleUseCommand(userVisitPK, form).run();
    }

}
