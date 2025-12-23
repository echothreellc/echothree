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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.form.CreateIndexForm;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.index.server.logic.IndexLogic;
import com.echothree.model.control.index.server.logic.IndexTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateIndexCommand
        extends BaseSimpleCommand<CreateIndexForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Index.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IndexName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IndexTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Directory", FieldType.STRING, true, null, 80L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateIndexCommand */
    public CreateIndexCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var indexControl = Session.getModelController(IndexControl.class);
        var indexName = form.getIndexName();
        var index = indexControl.getIndexByName(indexName);
        
        if(index == null) {
            var directory = form.getDirectory();
            
            index = indexControl.getIndexByDirectory(directory);
            
            if(index == null) {
                var indexTypeName = form.getIndexTypeName();
                var indexType = IndexTypeLogic.getInstance().getIndexTypeByName(this, indexTypeName);

                if(!hasExecutionErrors()) {
                    var languageIsoName = form.getLanguageIsoName();
                    var language = languageIsoName == null ? null : LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);

                    if(!hasExecutionErrors()) {
                        index = language == null ? null : indexControl.getIndex(indexType, language);

                        if(index == null) {
                            var entityType = indexType.getLastDetail().getEntityType();
                            var partyPK = getPartyPK();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();

                            index = indexControl.createIndex(indexName, indexType, language, directory, isDefault, sortOrder, partyPK);

                            if(description != null) {
                                indexControl.createIndexDescription(index, getPreferredLanguage(), description, partyPK);
                            }
                            
                            if(entityType != null) {
                                IndexLogic.getInstance().reindex(session, this, entityType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateIndex.name(), indexTypeName, languageIsoName);
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateDirectory.name(), directory);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateIndexName.name(), indexName);
        }
        
        return null;
    }
    
}
