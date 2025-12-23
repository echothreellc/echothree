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

package com.echothree.control.user.index.server;

import com.echothree.control.user.index.common.IndexRemote;
import com.echothree.control.user.index.common.form.*;
import com.echothree.control.user.index.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class IndexBean
        extends IndexFormsImpl
        implements IndexRemote, IndexLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "IndexBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Index Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexType(UserVisitPK userVisitPK, CreateIndexTypeForm form) {
        return CDI.current().select(CreateIndexTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeChoices(UserVisitPK userVisitPK, GetIndexTypeChoicesForm form) {
        return CDI.current().select(GetIndexTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexType(UserVisitPK userVisitPK, GetIndexTypeForm form) {
        return CDI.current().select(GetIndexTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypes(UserVisitPK userVisitPK, GetIndexTypesForm form) {
        return CDI.current().select(GetIndexTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndexType(UserVisitPK userVisitPK, SetDefaultIndexTypeForm form) {
        return CDI.current().select(SetDefaultIndexTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexType(UserVisitPK userVisitPK, EditIndexTypeForm form) {
        return CDI.current().select(EditIndexTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexType(UserVisitPK userVisitPK, DeleteIndexTypeForm form) {
        return CDI.current().select(DeleteIndexTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexTypeDescription(UserVisitPK userVisitPK, CreateIndexTypeDescriptionForm form) {
        return CDI.current().select(CreateIndexTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeDescription(UserVisitPK userVisitPK, GetIndexTypeDescriptionForm form) {
        return CDI.current().select(GetIndexTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexTypeDescriptions(UserVisitPK userVisitPK, GetIndexTypeDescriptionsForm form) {
        return CDI.current().select(GetIndexTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexTypeDescription(UserVisitPK userVisitPK, EditIndexTypeDescriptionForm form) {
        return CDI.current().select(EditIndexTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexTypeDescription(UserVisitPK userVisitPK, DeleteIndexTypeDescriptionForm form) {
        return CDI.current().select(DeleteIndexTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Fields
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexField(UserVisitPK userVisitPK, CreateIndexFieldForm form) {
        return CDI.current().select(CreateIndexFieldCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldChoices(UserVisitPK userVisitPK, GetIndexFieldChoicesForm form) {
        return CDI.current().select(GetIndexFieldChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexField(UserVisitPK userVisitPK, GetIndexFieldForm form) {
        return CDI.current().select(GetIndexFieldCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFields(UserVisitPK userVisitPK, GetIndexFieldsForm form) {
        return CDI.current().select(GetIndexFieldsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndexField(UserVisitPK userVisitPK, SetDefaultIndexFieldForm form) {
        return CDI.current().select(SetDefaultIndexFieldCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexField(UserVisitPK userVisitPK, EditIndexFieldForm form) {
        return CDI.current().select(EditIndexFieldCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexField(UserVisitPK userVisitPK, DeleteIndexFieldForm form) {
        return CDI.current().select(DeleteIndexFieldCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Field Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexFieldDescription(UserVisitPK userVisitPK, CreateIndexFieldDescriptionForm form) {
        return CDI.current().select(CreateIndexFieldDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldDescription(UserVisitPK userVisitPK, GetIndexFieldDescriptionForm form) {
        return CDI.current().select(GetIndexFieldDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexFieldDescriptions(UserVisitPK userVisitPK, GetIndexFieldDescriptionsForm form) {
        return CDI.current().select(GetIndexFieldDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexFieldDescription(UserVisitPK userVisitPK, EditIndexFieldDescriptionForm form) {
        return CDI.current().select(EditIndexFieldDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexFieldDescription(UserVisitPK userVisitPK, DeleteIndexFieldDescriptionForm form) {
        return CDI.current().select(DeleteIndexFieldDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Indexes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndex(UserVisitPK userVisitPK, CreateIndexForm form) {
        return CDI.current().select(CreateIndexCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexChoices(UserVisitPK userVisitPK, GetIndexChoicesForm form) {
        return CDI.current().select(GetIndexChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndex(UserVisitPK userVisitPK, GetIndexForm form) {
        return CDI.current().select(GetIndexCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexes(UserVisitPK userVisitPK, GetIndexesForm form) {
        return CDI.current().select(GetIndexesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultIndex(UserVisitPK userVisitPK, SetDefaultIndexForm form) {
        return CDI.current().select(SetDefaultIndexCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndex(UserVisitPK userVisitPK, EditIndexForm form) {
        return CDI.current().select(EditIndexCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndex(UserVisitPK userVisitPK, DeleteIndexForm form) {
        return CDI.current().select(DeleteIndexCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Index Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createIndexDescription(UserVisitPK userVisitPK, CreateIndexDescriptionForm form) {
        return CDI.current().select(CreateIndexDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexDescription(UserVisitPK userVisitPK, GetIndexDescriptionForm form) {
        return CDI.current().select(GetIndexDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getIndexDescriptions(UserVisitPK userVisitPK, GetIndexDescriptionsForm form) {
        return CDI.current().select(GetIndexDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editIndexDescription(UserVisitPK userVisitPK, EditIndexDescriptionForm form) {
        return CDI.current().select(EditIndexDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteIndexDescription(UserVisitPK userVisitPK, DeleteIndexDescriptionForm form) {
        return CDI.current().select(DeleteIndexDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult updateIndexes(UserVisitPK userVisitPK, UpdateIndexesForm form) {
        return CDI.current().select(UpdateIndexesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult forceReindex(UserVisitPK userVisitPK, ForceReindexForm form) {
        return CDI.current().select(ForceReindexCommand.class).get().run(userVisitPK, form);
    }
    
}
