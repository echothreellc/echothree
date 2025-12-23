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
import com.echothree.control.user.scale.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
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
    public CommandResult createScaleType(UserVisitPK userVisitPK, CreateScaleTypeForm form) {
        return CDI.current().select(CreateScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeChoices(UserVisitPK userVisitPK, GetScaleTypeChoicesForm form) {
        return CDI.current().select(GetScaleTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleType(UserVisitPK userVisitPK, GetScaleTypeForm form) {
        return CDI.current().select(GetScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypes(UserVisitPK userVisitPK, GetScaleTypesForm form) {
        return CDI.current().select(GetScaleTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScaleType(UserVisitPK userVisitPK, SetDefaultScaleTypeForm form) {
        return CDI.current().select(SetDefaultScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleType(UserVisitPK userVisitPK, EditScaleTypeForm form) {
        return CDI.current().select(EditScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleType(UserVisitPK userVisitPK, DeleteScaleTypeForm form) {
        return CDI.current().select(DeleteScaleTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleTypeDescription(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form) {
        return CDI.current().select(CreateScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeDescription(UserVisitPK userVisitPK, GetScaleTypeDescriptionForm form) {
        return CDI.current().select(GetScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleTypeDescriptions(UserVisitPK userVisitPK, GetScaleTypeDescriptionsForm form) {
        return CDI.current().select(GetScaleTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleTypeDescription(UserVisitPK userVisitPK, EditScaleTypeDescriptionForm form) {
        return CDI.current().select(EditScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleTypeDescription(UserVisitPK userVisitPK, DeleteScaleTypeDescriptionForm form) {
        return CDI.current().select(DeleteScaleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

     // -------------------------------------------------------------------------
    //   Scales
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScale(UserVisitPK userVisitPK, CreateScaleForm form) {
        return CDI.current().select(CreateScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScaleChoices(UserVisitPK userVisitPK, GetScaleChoicesForm form) {
        return CDI.current().select(GetScaleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScale(UserVisitPK userVisitPK, GetScaleForm form) {
        return CDI.current().select(GetScaleCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScales(UserVisitPK userVisitPK, GetScalesForm form) {
        return CDI.current().select(GetScalesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScale(UserVisitPK userVisitPK, SetDefaultScaleForm form) {
        return CDI.current().select(SetDefaultScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editScale(UserVisitPK userVisitPK, EditScaleForm form) {
        return CDI.current().select(EditScaleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteScale(UserVisitPK userVisitPK, DeleteScaleForm form) {
        return CDI.current().select(DeleteScaleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createScaleDescription(UserVisitPK userVisitPK, CreateScaleDescriptionForm form) {
        return CDI.current().select(CreateScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getScaleDescription(UserVisitPK userVisitPK, GetScaleDescriptionForm form) {
        return CDI.current().select(GetScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleDescriptions(UserVisitPK userVisitPK, GetScaleDescriptionsForm form) {
        return CDI.current().select(GetScaleDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleDescription(UserVisitPK userVisitPK, EditScaleDescriptionForm form) {
        return CDI.current().select(EditScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteScaleDescription(UserVisitPK userVisitPK, DeleteScaleDescriptionForm form) {
        return CDI.current().select(DeleteScaleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Scale Use Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseType(UserVisitPK userVisitPK, CreateScaleUseTypeForm form) {
        return CDI.current().select(CreateScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeChoices(UserVisitPK userVisitPK, GetScaleUseTypeChoicesForm form) {
        return CDI.current().select(GetScaleUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseType(UserVisitPK userVisitPK, GetScaleUseTypeForm form) {
        return CDI.current().select(GetScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypes(UserVisitPK userVisitPK, GetScaleUseTypesForm form) {
        return CDI.current().select(GetScaleUseTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultScaleUseType(UserVisitPK userVisitPK, SetDefaultScaleUseTypeForm form) {
        return CDI.current().select(SetDefaultScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleUseType(UserVisitPK userVisitPK, EditScaleUseTypeForm form) {
        return CDI.current().select(EditScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleUseType(UserVisitPK userVisitPK, DeleteScaleUseTypeForm form) {
        return CDI.current().select(DeleteScaleUseTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Scale Use Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createScaleUseTypeDescription(UserVisitPK userVisitPK, CreateScaleUseTypeDescriptionForm form) {
        return CDI.current().select(CreateScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeDescription(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionForm form) {
        return CDI.current().select(GetScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getScaleUseTypeDescriptions(UserVisitPK userVisitPK, GetScaleUseTypeDescriptionsForm form) {
        return CDI.current().select(GetScaleUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editScaleUseTypeDescription(UserVisitPK userVisitPK, EditScaleUseTypeDescriptionForm form) {
        return CDI.current().select(EditScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteScaleUseTypeDescription(UserVisitPK userVisitPK, DeleteScaleUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteScaleUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Scale Uses
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyScaleUse(UserVisitPK userVisitPK, CreatePartyScaleUseForm form) {
        return CDI.current().select(CreatePartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyScaleUse(UserVisitPK userVisitPK, GetPartyScaleUseForm form) {
        return CDI.current().select(GetPartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyScaleUses(UserVisitPK userVisitPK, GetPartyScaleUsesForm form) {
        return CDI.current().select(GetPartyScaleUsesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyScaleUse(UserVisitPK userVisitPK, EditPartyScaleUseForm form) {
        return CDI.current().select(EditPartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyScaleUse(UserVisitPK userVisitPK, DeletePartyScaleUseForm form) {
        return CDI.current().select(DeletePartyScaleUseCommand.class).get().run(userVisitPK, form);
    }

}
