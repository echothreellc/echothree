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

package com.echothree.control.user.batch.server;

import com.echothree.control.user.batch.common.BatchRemote;
import com.echothree.control.user.batch.common.form.*;
import com.echothree.control.user.batch.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
        return new CreateBatchTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBatchTypeChoices(UserVisitPK userVisitPK, GetBatchTypeChoicesForm form) {
        return new GetBatchTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBatchType(UserVisitPK userVisitPK, GetBatchTypeForm form) {
        return new GetBatchTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBatchTypes(UserVisitPK userVisitPK, GetBatchTypesForm form) {
        return new GetBatchTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultBatchType(UserVisitPK userVisitPK, SetDefaultBatchTypeForm form) {
        return new SetDefaultBatchTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editBatchType(UserVisitPK userVisitPK, EditBatchTypeForm form) {
        return new EditBatchTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteBatchType(UserVisitPK userVisitPK, DeleteBatchTypeForm form) {
        return new DeleteBatchTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeDescription(UserVisitPK userVisitPK, CreateBatchTypeDescriptionForm form) {
        return new CreateBatchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchTypeDescription(UserVisitPK userVisitPK, GetBatchTypeDescriptionForm form) {
        return new GetBatchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchTypeDescriptions(UserVisitPK userVisitPK, GetBatchTypeDescriptionsForm form) {
        return new GetBatchTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBatchTypeDescription(UserVisitPK userVisitPK, EditBatchTypeDescriptionForm form) {
        return new EditBatchTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBatchTypeDescription(UserVisitPK userVisitPK, DeleteBatchTypeDescriptionForm form) {
        return new DeleteBatchTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchTypeEntityType(UserVisitPK userVisitPK, CreateBatchTypeEntityTypeForm form) {
        return new CreateBatchTypeEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchTypeEntityType(UserVisitPK userVisitPK, GetBatchTypeEntityTypeForm form) {
        return new GetBatchTypeEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchTypeEntityTypes(UserVisitPK userVisitPK, GetBatchTypeEntityTypesForm form) {
        return new GetBatchTypeEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBatchTypeEntityType(UserVisitPK userVisitPK, DeleteBatchTypeEntityTypeForm form) {
        return new DeleteBatchTypeEntityTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasType(UserVisitPK userVisitPK, CreateBatchAliasTypeForm form) {
        return new CreateBatchAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliasTypeChoices(UserVisitPK userVisitPK, GetBatchAliasTypeChoicesForm form) {
        return new GetBatchAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliasType(UserVisitPK userVisitPK, GetBatchAliasTypeForm form) {
        return new GetBatchAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliasTypes(UserVisitPK userVisitPK, GetBatchAliasTypesForm form) {
        return new GetBatchAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultBatchAliasType(UserVisitPK userVisitPK, SetDefaultBatchAliasTypeForm form) {
        return new SetDefaultBatchAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBatchAliasType(UserVisitPK userVisitPK, EditBatchAliasTypeForm form) {
        return new EditBatchAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBatchAliasType(UserVisitPK userVisitPK, DeleteBatchAliasTypeForm form) {
        return new DeleteBatchAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAliasTypeDescription(UserVisitPK userVisitPK, CreateBatchAliasTypeDescriptionForm form) {
        return new CreateBatchAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliasTypeDescription(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionForm form) {
        return new GetBatchAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliasTypeDescriptions(UserVisitPK userVisitPK, GetBatchAliasTypeDescriptionsForm form) {
        return new GetBatchAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBatchAliasTypeDescription(UserVisitPK userVisitPK, EditBatchAliasTypeDescriptionForm form) {
        return new EditBatchAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBatchAliasTypeDescription(UserVisitPK userVisitPK, DeleteBatchAliasTypeDescriptionForm form) {
        return new DeleteBatchAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createBatchAlias(UserVisitPK userVisitPK, CreateBatchAliasForm form) {
        return new CreateBatchAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAlias(UserVisitPK userVisitPK, GetBatchAliasForm form) {
        return new GetBatchAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getBatchAliases(UserVisitPK userVisitPK, GetBatchAliasesForm form) {
        return new GetBatchAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editBatchAlias(UserVisitPK userVisitPK, EditBatchAliasForm form) {
        return new EditBatchAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteBatchAlias(UserVisitPK userVisitPK, DeleteBatchAliasForm form) {
        return new DeleteBatchAliasCommand(userVisitPK, form).run();
    }

}
