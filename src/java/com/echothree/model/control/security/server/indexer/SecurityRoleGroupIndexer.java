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

package com.echothree.model.control.security.server.indexer;

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.security.server.analyzer.SecurityRoleGroupAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class SecurityRoleGroupIndexer
        extends BaseIndexer<SecurityRoleGroup> {
    
    SecurityControl securityControl = Session.getModelController(SecurityControl.class);

    /** Creates a new instance of SecurityRoleGroupIndexer */
    public SecurityRoleGroupIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new SecurityRoleGroupAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }
    
    @Override
    protected SecurityRoleGroup getEntity(final EntityInstance entityInstance) {
        return securityControl.getSecurityRoleGroupByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final SecurityRoleGroup securityRoleGroup) {
        var securityRoleGroupDetail = securityRoleGroup.getLastDetail();
        var parentSecurityRoleGroup = securityRoleGroupDetail.getParentSecurityRoleGroup();
        var description = securityControl.getBestSecurityRoleGroupDescription(securityRoleGroup, language);

        var document = newDocumentWithEntityInstanceFields(entityInstance, securityRoleGroup.getPrimaryKey());

        document.add(new Field(IndexFields.securityRoleGroupName.name(), securityRoleGroupDetail.getSecurityRoleGroupName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.securityRoleGroupName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(securityRoleGroupDetail.getSecurityRoleGroupName())));
        if(parentSecurityRoleGroup != null) {
            document.add(new Field(IndexFields.parentSecurityRoleGroupName.name(), parentSecurityRoleGroup.getLastDetail().getSecurityRoleGroupName(),
                    FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(description != null) {
            document.add(new Field(IndexFields.description.name(), description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexFields.description.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                    new BytesRef(description)));
        }

        return document;
    }

}
