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

package com.echothree.model.control.core.server.indexer;

import com.echothree.model.control.core.server.analyzer.EntityTypeAnalyzer;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class EntityTypeIndexer
        extends BaseIndexer<EntityType> {
    
    /** Creates a new instance of EntityTypeIndexer */
    public EntityTypeIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new EntityTypeAnalyzer(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }
    
    @Override
    protected EntityType getEntity(final EntityInstance entityInstance) {
        return entityTypeControl.getEntityTypeByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final EntityType entityType) {
        var entityTypeDetail = entityType.getLastDetail();
        var description = entityTypeControl.getBestEntityTypeDescription(entityType, language);

        var document = newDocumentWithEntityInstanceFields(entityInstance, entityType.getPrimaryKey());

        document.add(new Field(IndexFields.componentVendorName.name(),
                entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.componentVendorName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName())));
        document.add(new Field(IndexFields.entityTypeName.name(),
                entityTypeDetail.getEntityTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.entityTypeName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                new BytesRef(entityTypeDetail.getEntityTypeName())));
        document.add(new Field(IndexFields.keepAllHistory.name(), entityTypeDetail.getKeepAllHistory().toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));
        document.add(new Field(IndexFields.isExtensible.name(), entityTypeDetail.getIsExtensible().toString(), FieldTypes.NOT_STORED_NOT_TOKENIZED));

        if(description != null) {
            document.add(new Field(IndexFields.description.name(), description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexFields.description.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(),
                    new BytesRef(description)));
        }

        return document;
    }

}
