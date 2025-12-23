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

package com.echothree.model.control.content.server.analyzer;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.index.server.analyzer.DictionaryAnalyzer;
import com.echothree.model.control.index.server.analyzer.WhitespaceLowerCaseAnalyzer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;

public class ContentCatalogItemAnalyzer
        extends BasicAnalyzer {

    public ContentCatalogItemAnalyzer(final ExecutionErrorAccumulator eea, final Language language,
            final EntityType entityType, final List<EntityAliasType> entityAliasTypes,
            final List<EntityAttribute> entityAttributes, final List<TagScope> tagScopes) {
        super(eea, language, entityType, entityAliasTypes, entityAttributes, tagScopes);
    }

    public ContentCatalogItemAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(eea, language, entityType);
    }
    
    protected Map<String, Analyzer> getItemAliasTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        itemControl.getItemAliasTypes().forEach(itemAliasType ->
                fieldAnalyzers.put(itemAliasType.getLastDetail().getItemAliasTypeName(), new WhitespaceLowerCaseAnalyzer())
        );
        
        return fieldAnalyzers;
    }
    
    /**
     * 
     * @param fieldAnalyzers A Map of field Analyzers.
     * @return the field Analyzer map with additional Analyzers for ItemDescriptionTypes added.
     */
    protected Map<String, Analyzer> getItemDescriptionAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        itemControl.getItemDescriptionTypes().stream().map(ItemDescriptionType::getLastDetail).forEach((itemDescriptionTypeDetail) -> {
            var mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();
            if (mimeTypeUsageType == null || mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.TEXT.name())) {
                fieldAnalyzers.put(itemDescriptionTypeDetail.getItemDescriptionTypeName() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.dictionary.name(),
                        new DictionaryAnalyzer());
            }
        });
        
        return fieldAnalyzers;
    }
    
    @Override
    protected Map<String, Analyzer> getEntityTypeFieldAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeFieldAnalyzers(fieldAnalyzers);

        // Items
        fieldAnalyzers.put(IndexFields.aliases.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemNameAndAliases.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemUseTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemDeliveryTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemInventoryTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.inventorySerialized.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.shippingChargeExempt.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.allowClubDiscounts.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.allowCouponDiscounts.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.allowAssociatePayments.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.unitOfMeasureKindName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.itemPriceTypeName.name(), new WhitespaceLowerCaseAnalyzer());

        // Content Catalogs
        fieldAnalyzers.put(IndexFields.contentCollectionName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.contentCatalogName.name(), new WhitespaceLowerCaseAnalyzer());

        // Content Category Items
        fieldAnalyzers.put(IndexFields.inventoryConditionName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.unitOfMeasureKindName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.unitOfMeasureTypeName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.currencyIsoName.name(), new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexFields.contentCategoryNames.name(), new WhitespaceLowerCaseAnalyzer());

        return getItemDescriptionAnalyzers(getItemAliasTypeAnalyzers(fieldAnalyzers));
    }

    @Override
    protected Map<String, Analyzer> getFieldAnalyzers(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final List<EntityAttribute> entityAttributes, final List<TagScope> tagScopes) {
        var fieldAnalyzers = new HashMap<String, Analyzer>();

        // Items
        fieldAnalyzers.putAll(super.getFieldAnalyzers(eea, EntityTypeLogic.getInstance().getEntityTypeByName(eea,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.Item.name()), entityAttributes, tagScopes));

        // Content Catalogs
        fieldAnalyzers.putAll(super.getFieldAnalyzers(eea, EntityTypeLogic.getInstance().getEntityTypeByName(eea,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.ContentCatalog.name()), entityAttributes, tagScopes));

        // Content Category Items
        fieldAnalyzers.putAll(super.getFieldAnalyzers(eea, entityType, entityAttributes, tagScopes));

        return fieldAnalyzers;


    }

    @Override
    public Set<String> getLongFields() {
        return Set.of(
                IndexFields.unitPrice.name(),
                IndexFields.minimumUnitPrice.name(),
                IndexFields.maximumUnitPrice.name(),
                IndexFields.unitPriceIncrement.name()
        );
    }

}
