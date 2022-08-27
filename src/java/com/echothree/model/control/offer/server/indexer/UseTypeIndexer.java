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

package com.echothree.model.control.offer.server.indexer;

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analysis.UseTypeAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class UseTypeIndexer
        extends BaseIndexer<UseType> {

    UseTypeControl useTypeControl = Session.getModelController(UseTypeControl.class);

    /** Creates a new instance of UseTypeIndexer */
    public UseTypeIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new UseTypeAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected UseType getEntity(final EntityInstance entityInstance) {
        return useTypeControl.getUseTypeByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final UseType useType) {
        UseTypeDetail useTypeDetail = useType.getLastDetail();
        String description = useTypeControl.getBestUseTypeDescription(useType, language);
        Document document = new Document();

        document.add(new Field(IndexFields.entityRef.name(), useType.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexFields.entityInstanceId.name(), entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexFields.useTypeName.name(), useTypeDetail.getUseTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexFields.useTypeName.name() + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(useTypeDetail.getUseTypeName())));
        
        if(description != null) {
            document.add(new Field(IndexFields.description.name(), description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexFields.description.name() + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable,
                    new BytesRef(description)));
        }
        
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        return document;
    }

}
