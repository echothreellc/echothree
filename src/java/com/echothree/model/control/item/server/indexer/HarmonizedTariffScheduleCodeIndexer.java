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

package com.echothree.model.control.item.server.indexer;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.HarmonizedTariffScheduleCodeAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class HarmonizedTariffScheduleCodeIndexer
        extends BaseIndexer<HarmonizedTariffScheduleCode> {
    
    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of HarmonizedTariffScheduleCodeIndexer */
    public HarmonizedTariffScheduleCodeIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new HarmonizedTariffScheduleCodeAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected HarmonizedTariffScheduleCode getEntity(final EntityInstance entityInstance) {
        return itemControl.getHarmonizedTariffScheduleCodeByEntityInstance(entityInstance);
    }
    
    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = itemControl.getBestHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, harmonizedTariffScheduleCode.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_CountryGeoCodeName, harmonizedTariffScheduleCodeDetail.getCountryGeoCode().getLastDetail().getGeoCodeName(), FieldTypes.NOT_STORED_TOKENIZED));
        
        document.add(new Field(IndexConstants.IndexField_HarmonizedTariffScheduleCodeName, harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_HarmonizedTariffScheduleCodeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName())));

        if(harmonizedTariffScheduleCodeTranslation != null) {
            String description = harmonizedTariffScheduleCodeTranslation.getDescription();
            String overview = harmonizedTariffScheduleCodeTranslation.getOverview();
            
            document.add(new Field(IndexConstants.IndexField_Description, description, FieldTypes.NOT_STORED_TOKENIZED));
            document.add(new SortedDocValuesField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                    new BytesRef(description)));
            
            if(overview != null) {
                if(harmonizedTariffScheduleCodeTranslation.getOverviewMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.CLOB.name())) {
                    // TODO: mime type conversion to text/plain happens here
                    document.add(new Field(IndexConstants.IndexField_Overview, overview, FieldTypes.NOT_STORED_TOKENIZED));
                }
            }
        }
        
        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        return document;
    }

}
