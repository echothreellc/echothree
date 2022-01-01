// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.core.server.indexer;

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.EntityListItemAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDetail;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class EntityListItemIndexer
        extends BaseIndexer<EntityListItem> {
    
    /** Creates a new instance of EntityListItemIndexer */
    public EntityListItemIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new EntityListItemAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected EntityListItem getEntity(final EntityInstance entityInstance) {
        return coreControl.getEntityListItemByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final EntityListItem entityListItem) {
        EntityListItemDetail entityListItemDetail = entityListItem.getLastDetail();
        EntityAttributeDetail entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
        EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
        String description = coreControl.getBestEntityListItemDescription(entityListItem, language);
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, entityListItem.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_ComponentVendorName,
                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ComponentVendorName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName())));
        document.add(new Field(IndexConstants.IndexField_EntityTypeName,
                entityTypeDetail.getEntityTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_EntityTypeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(entityTypeDetail.getEntityTypeName())));
        document.add(new Field(IndexConstants.IndexField_EntityAttributeName,
                entityAttributeDetail.getEntityAttributeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_EntityAttributeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(entityAttributeDetail.getEntityAttributeName())));
        document.add(new Field(IndexConstants.IndexField_EntityListItemName,
                entityListItemDetail.getEntityListItemName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_EntityListItemName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(entityListItemDetail.getEntityListItemName())));

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
