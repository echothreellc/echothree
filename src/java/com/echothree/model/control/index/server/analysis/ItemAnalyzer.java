// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.index.server.analysis;

import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;

public class ItemAnalyzer
        extends BasicAnalyzer {
    
    public ItemAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType, final List<EntityAttribute> entityAttributes,
            final List<TagScope> tagScopes) {
        super(eea, language, entityType, entityAttributes, tagScopes);
    }

    public ItemAnalyzer(final ExecutionErrorAccumulator eea, final Language language, final EntityType entityType) {
        super(eea, language, entityType);
    }
    
    protected Map<String, Analyzer> getItemAliasTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        
        itemControl.getItemAliasTypes().stream().forEach((itemAliasType) -> {
            fieldAnalyzers.put(itemAliasType.getLastDetail().getItemAliasTypeName(), new WhitespaceLowerCaseAnalyzer());
        });
        
        return fieldAnalyzers;
    }
    
    /**
     * 
     * @param fieldAnalyzers A Map of field Analyzers.
     * @return the field Analyzer map with additional Analyzers for ItemDescriptionTypes added.
     */
    protected Map<String, Analyzer> getItemDescriptionAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        
        itemControl.getItemDescriptionTypes().stream().map((itemDescriptionType) -> itemDescriptionType.getLastDetail()).forEach((itemDescriptionTypeDetail) -> {
            MimeTypeUsageType mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();
            if (mimeTypeUsageType == null || mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.TEXT.name())) {
                fieldAnalyzers.put(itemDescriptionTypeDetail.getItemDescriptionTypeName() + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Dictionary,
                        new DictionaryAnalyzer());
            }
        });
        
        return fieldAnalyzers;
    }
    
    @Override
    protected Map<String, Analyzer> getEntityTypeAnalyzers(final Map<String, Analyzer> fieldAnalyzers) {
        super.getEntityTypeAnalyzers(fieldAnalyzers);
        
        fieldAnalyzers.put(IndexConstants.IndexField_Aliases, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemName, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemNameAndAliases, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemTypeName, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemUseTypeName, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemDeliveryTypeName, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ItemInventoryTypeName, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_InventorySerialized, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_ShippingChargeExempt, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_AllowClubDiscounts, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_AllowCouponDiscounts, new WhitespaceLowerCaseAnalyzer());
        fieldAnalyzers.put(IndexConstants.IndexField_AllowAssociatePayments, new WhitespaceLowerCaseAnalyzer());
        
        return getItemDescriptionAnalyzers(getItemAliasTypeAnalyzers(fieldAnalyzers));
    }
    
}
