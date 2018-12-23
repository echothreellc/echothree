// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.index.server.analysis.SecurityRoleGroupAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDetail;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class SecurityRoleGroupIndexer
        extends BaseIndexer<SecurityRoleGroup> {
    
    SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

    /** Creates a new instance of SecurityRoleGroupIndexer */
    public SecurityRoleGroupIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new SecurityRoleGroupAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected SecurityRoleGroup getEntity(final EntityInstance entityInstance) {
        return securityControl.getSecurityRoleGroupByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final SecurityRoleGroup securityRoleGroup) {
        SecurityRoleGroupDetail securityRoleGroupDetail = securityRoleGroup.getLastDetail();
        SecurityRoleGroup parentSecurityRoleGroup = securityRoleGroupDetail.getParentSecurityRoleGroup();
        String description = securityControl.getBestSecurityRoleGroupDescription(securityRoleGroup, language);
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, securityRoleGroup.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_SecurityRoleGroupName, securityRoleGroupDetail.getSecurityRoleGroupName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_SecurityRoleGroupName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(securityRoleGroupDetail.getSecurityRoleGroupName())));
        if(parentSecurityRoleGroup != null) {
            document.add(new Field(IndexConstants.IndexField_ParentSecurityRoleGroupName, parentSecurityRoleGroup.getLastDetail().getSecurityRoleGroupName(),
                    FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(description != null) {
            document.add(new Field(IndexConstants.IndexField_Description, description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                    new BytesRef(description)));
        }
        
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        return document;
    }

}
