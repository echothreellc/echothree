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

package com.echothree.model.control.content.server.indexer;

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ContentCategoryAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class ContentCategoryIndexer
        extends BaseIndexer<ContentCategory> {
    
    ContentControl contentControl = Session.getModelController(ContentControl.class);

    /** Creates a new instance of ContentCategoryIndexer */
    public ContentCategoryIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ContentCategoryAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected ContentCategory getEntity(final EntityInstance entityInstance) {
        return contentControl.getContentCategoryByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final ContentCategory contentCategory) {
        ContentCategoryDetail contentCategoryDetail = contentCategory.getLastDetail();
        ContentCatalogDetail contentCatalogDetail = contentCategoryDetail.getContentCatalog().getLastDetail();
        ContentCategory parentContentCategory = contentCategoryDetail.getParentContentCategory();
        String description = contentControl.getBestContentCategoryDescription(contentCategory, language);
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, contentCategory.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_ContentCollectionName, contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ContentCollectionName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName())));
        document.add(new Field(IndexConstants.IndexField_ContentCatalogName, contentCatalogDetail.getContentCatalogName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ContentCatalogName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(contentCatalogDetail.getContentCatalogName())));
        document.add(new Field(IndexConstants.IndexField_ContentCategoryName, contentCategoryDetail.getContentCategoryName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ContentCategoryName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(contentCategoryDetail.getContentCategoryName())));
        if(parentContentCategory != null) {
            document.add(new Field(IndexConstants.IndexField_ParentContentCategoryName, parentContentCategory.getLastDetail().getContentCategoryName(),
                    FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(description != null) {
            document.add(new Field(IndexConstants.IndexField_Description, description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                    new BytesRef(description)));
        }
        
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        return document;
    }

}
