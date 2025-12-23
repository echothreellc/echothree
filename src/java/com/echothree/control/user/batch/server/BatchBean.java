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

package com.echothree.control.user.batch.server;

import com.echothree.control.user.batch.common.BatchRemote;
import com.echothree.control.user.batch.common.form.*;
import com.echothree.control.user.batch.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class BatchBean
        extends BatchFormsImpl
        implements BatchRemote, BatchLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "BatchBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBatchType(UserVisitPK userVisitPK, CreateBatchTypeForm form) {
        return CDI.current().select(CreateBatchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchTypeChoices(UserVisitPK userVisitPK, GetBatchTypeChoicesForm form) {
        return CDI.current().select(GetBatchTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchType(UserVisitPK userVisitPK, GetBatchTypeForm form) {
        return CDI.current().select(GetBatchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getBatchTypes(UserVisitPK userVisitPK, GetBatchTypesForm form) {
        return CDI.current().select(GetBatchTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultBatchType(UserVisitPK userVisitPK, SetDefaultBatchTypeForm form) {
        return CDI.current().select(SetDefaultBatchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editBatchType(UserVisitPK userVisitPK, EditBatchTypeForm form) {
        return CDI.current().select(EditBatchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteBatchType(UserVisitPK userVisitPK, DeleteBatchTypeForm form) {
        return CDI.current().select(DeleteBatchTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeDescription(UserVisitPK userVisitPK, CreateBatchTypeDescriptionForm form) {
        return CDI.current().select(CreateBatchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeDescription(UserVisitPK userVisitPK, GetBatchTypeDescriptionForm form) {
        return CDI.current().select(GetBatchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeDescriptions(UserVisitPK userVisitPK, GetBatchTypeDescriptionsForm form) {
        return CDI.current().select(GetBatchTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchTypeDescription(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form) {
        return CDI.current().select(EditBatchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchTypeDescription(UserVisitPK userVisitPK, DeleteBatchTypeDescriptionForm form) {
        return CDI.current().select(DeleteBatchTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeEntityType(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form) {
        return CDI.current().select(CreateBatchTypeEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeEntityType(UserVisitPK userVisitPK, GetBatchTypeEntityTypeForm form) {
        return CDI.current().select(GetBatchTypeEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchTypeEntityTypes(UserVisitPK userVisitPK, GetBatchTypeEntityTypesForm form) {
        return CDI.current().select(GetBatchTypeEntityTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchTypeEntityType(UserVisitPK userVisitPK, DeleteBatchTypeEntityTypeForm form) {
        return CDI.current().select(DeleteBatchTypeEntityTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasType(UserVisitPK userVisitPK, CreateBatchAliasTypeForm form) {
        return CDI.current().select(CreateBatchAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeChoices(UserVisitPK userVisitPK, GetBatchAliasTypeChoicesForm form) {
        return CDI.current().select(GetBatchAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasType(UserVisitPK userVisitPK, GetBatchAliasTypeForm form) {
        return CDI.current().select(GetBatchAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypes(UserVisitPK userVisitPK, GetBatchAliasTypesForm form) {
        return CDI.current().select(GetBatchAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultBatchAliasType(UserVisitPK userVisitPK, SetDefaultBatchAliasTypeForm form) {
        return CDI.current().select(SetDefaultBatchAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAliasType(UserVisitPK userVisitPK, EditBatchAliasTypeForm form) {
        return CDI.current().select(EditBatchAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAliasType(UserVisitPK userVisitPK, DeleteBatchAliasTypeForm form) {
        return CDI.current().select(DeleteBatchAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasTypeDescription(UserVisitPK userVisitPK, CreateBatchAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateBatchAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeDescription(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form) {
        return CDI.current().select(GetBatchAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliasTypeDescriptions(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetBatchAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAliasTypeDescription(UserVisitPK userVisitPK, EditBatchAliasTypeDescriptionForm form) {
        return CDI.current().select(EditBatchAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAliasTypeDescription(UserVisitPK userVisitPK, DeleteBatchAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteBatchAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAlias(UserVisitPK userVisitPK, CreateBatchAliasForm form) {
        return CDI.current().select(CreateBatchAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAlias(UserVisitPK userVisitPK, GetBatchAliasForm form) {
        return CDI.current().select(GetBatchAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getBatchAliases(UserVisitPK userVisitPK, GetBatchAliasesForm form) {
        return CDI.current().select(GetBatchAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editBatchAlias(UserVisitPK userVisitPK, EditBatchAliasForm form) {
        return CDI.current().select(EditBatchAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteBatchAlias(UserVisitPK userVisitPK, DeleteBatchAliasForm form) {
        return CDI.current().select(DeleteBatchAliasCommand.class).get().run(userVisitPK, form);
    }

}
