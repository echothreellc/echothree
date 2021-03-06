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

import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ItemAnalyzer;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.index.server.indexer.FieldTypes;
import com.echothree.model.control.index.server.indexer.IndexerDebugFlags;
import com.echothree.model.control.index.server.indexer.sortabledescriptionproducer.SortableDescriptionProducer;
import com.echothree.model.control.index.server.indexer.sortabledescriptionproducer.SortableDescriptionProducerFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemClobDescription;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDetail;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemInventoryType;
import com.echothree.model.data.item.server.entity.ItemStringDescription;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.util.BytesRef;

public class ItemIndexer
        extends BaseIndexer<Item> {
    
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    
    List<ItemDescriptionType> itemDescriptionTypes;
    SortableDescriptionProducer sortableDescriptionProducer;

    /** Creates a new instance of ItemIndexer */
    public ItemIndexer(final ExecutionErrorAccumulator eea, final Index index) {
        super(eea, index);
        
        itemDescriptionTypes = itemControl.getItemDescriptionTypesByIncludeInIndex();
        sortableDescriptionProducer = SortableDescriptionProducerFactory.getInstance().getSortableDescriptionProducer(index.getLastDetail().getLanguage());
    }

    @Override
    protected Analyzer getAnalyzer() {
        return new ItemAnalyzer(eea, language, entityType, entityAttributes, tagScopes);
    }
    
    @Override
    protected Item getEntity(final EntityInstance entityInstance) {
        return itemControl.getItemByEntityInstance(entityInstance);
    }

    @Override
    protected Document convertToDocument(final EntityInstance entityInstance, final Item item) {
        ItemDetail itemDetail = item.getLastDetail();
        List<ItemAlias> itemAliases = itemControl.getItemAliasesByItem(item);
        ItemDeliveryType itemDeliveryType = itemDetail.getItemDeliveryType();
        ItemInventoryType itemInventoryType = itemDetail.getItemInventoryType();
        ItemAccountingCategory itemAccountingCategory = itemDetail.getItemAccountingCategory();
        ItemPurchasingCategory itemPurchasingCategory = itemDetail.getItemPurchasingCategory();
        Boolean inventorySerialized = itemDetail.getInventorySerialized();
        Long shippingEndTime = itemDetail.getShippingEndTime();
        Long salesOrderEndTime = itemDetail.getSalesOrderEndTime();
        Long purchaseOrderStartTime = itemDetail.getPurchaseOrderStartTime();
        Long purchaseOrderEndTime = itemDetail.getPurchaseOrderEndTime();
        Document document = new Document();

        document.add(new Field(IndexConstants.IndexField_EntityRef, item.getPrimaryKey().getEntityRef(), FieldTypes.STORED_NOT_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_EntityInstanceId, entityInstance.getPrimaryKey().getEntityId().toString(), FieldTypes.STORED_NOT_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_ItemName, itemDetail.getItemName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new SortedDocValuesField(IndexConstants.IndexField_ItemName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                new BytesRef(itemDetail.getItemName())));
        document.add(new Field(IndexConstants.IndexField_ItemNameAndAliases, itemDetail.getItemName(), FieldTypes.NOT_STORED_TOKENIZED));

        document.add(new Field(IndexConstants.IndexField_ItemTypeName, itemDetail.getItemType().getItemTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_ItemUseTypeName, itemDetail.getItemUseType().getItemUseTypeName(), FieldTypes.NOT_STORED_TOKENIZED));

        if(itemDeliveryType != null) {
            document.add(new Field(IndexConstants.IndexField_ItemDeliveryTypeName, itemDeliveryType.getItemDeliveryTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(itemInventoryType != null) {
            document.add(new Field(IndexConstants.IndexField_ItemInventoryTypeName, itemInventoryType.getItemInventoryTypeName(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        itemAliases.stream().map((itemAlias) -> {
            document.add(new Field(IndexConstants.IndexField_Aliases, itemAlias.getAlias(), FieldTypes.NOT_STORED_TOKENIZED));
            return itemAlias;
        }).map((itemAlias) -> {
            document.add(new Field(IndexConstants.IndexField_ItemNameAndAliases, itemAlias.getAlias(), FieldTypes.NOT_STORED_TOKENIZED));
            return itemAlias;
        }).forEach((itemAlias) -> {
            document.add(new Field(itemAlias.getItemAliasType().getLastDetail().getItemAliasTypeName(), itemAlias.getAlias(), FieldTypes.NOT_STORED_TOKENIZED));
        });
        
        document.add(new Field(IndexConstants.IndexField_ItemCategoryName, itemDetail.getItemCategory().getLastDetail().getItemCategoryName(), FieldTypes.NOT_STORED_TOKENIZED));
        
        if(itemAccountingCategory != null) {
            document.add(new Field(IndexConstants.IndexField_ItemAccountingCategoryName, itemDetail.getItemAccountingCategory().getLastDetail().getItemAccountingCategoryName(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(itemPurchasingCategory != null) {
            document.add(new Field(IndexConstants.IndexField_ItemPurchasingCategoryName, itemDetail.getItemPurchasingCategory().getLastDetail().getItemPurchasingCategoryName(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        if(inventorySerialized != null) {
            document.add(new Field(IndexConstants.IndexField_InventorySerialized, itemDetail.getInventorySerialized().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        }
        
        document.add(new Field(IndexConstants.IndexField_ShippingChargeExempt, itemDetail.getShippingChargeExempt().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new LongPoint(IndexConstants.IndexField_ShippingStartTime, itemDetail.getShippingStartTime()));
        if(shippingEndTime != null) {
            document.add(new LongPoint(IndexConstants.IndexField_ShippingEndTime, shippingEndTime));
        }
        document.add(new LongPoint(IndexConstants.IndexField_SalesOrderStartTime, itemDetail.getSalesOrderStartTime()));
        if(salesOrderEndTime != null) {
            document.add(new LongPoint(IndexConstants.IndexField_SalesOrderEndTime, salesOrderEndTime));
        }
        if(purchaseOrderStartTime != null) {
            document.add(new LongPoint(IndexConstants.IndexField_PurchaseOrderStartTime, purchaseOrderStartTime));
        }
        if(purchaseOrderEndTime != null) {
            document.add(new LongPoint(IndexConstants.IndexField_PurchaseOrderEndTime, purchaseOrderEndTime));
        }
        document.add(new Field(IndexConstants.IndexField_AllowClubDiscounts, itemDetail.getAllowClubDiscounts().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_AllowCouponDiscounts, itemDetail.getAllowCouponDiscounts().toString(), FieldTypes.NOT_STORED_TOKENIZED));
        document.add(new Field(IndexConstants.IndexField_AllowAssociatePayments, itemDetail.getAllowAssociatePayments().toString(), FieldTypes.NOT_STORED_TOKENIZED));

        indexWorkflowEntityStatuses(document, entityInstance);
        indexEntityTimes(document, entityInstance);
        indexEntityAttributes(document, entityInstance);
        indexEntityTags(document, entityInstance);

        itemDescriptionTypes.forEach((itemDescriptionType) -> {
            ItemDescription itemDescription = itemControl.getBestItemDescription(itemDescriptionType, item, language);
            if (itemDescription != null) {
                ItemDescriptionTypeDetail itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();
                MimeTypeUsageType mimeTypeUsageType = itemDescriptionTypeDetail.getMimeTypeUsageType();
                String itemDescriptionTypeName = itemDescriptionTypeDetail.getItemDescriptionTypeName();

                if(mimeTypeUsageType == null) {
                    ItemStringDescription itemStringDescription = itemControl.getItemStringDescription(itemDescription);
                    String stringDescription = itemStringDescription.getStringDescription();
                    
                    document.add(new Field(itemDescriptionTypeName, stringDescription, FieldTypes.NOT_STORED_TOKENIZED));
                    document.add(new Field(itemDescriptionTypeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Dictionary,
                            stringDescription, FieldTypes.NOT_STORED_TOKENIZED));
                    document.add(new SortedDocValuesField(itemDescriptionTypeName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable,
                            new BytesRef(sortableDescriptionProducer.getSortableDescription(stringDescription))));

                    if(IndexerDebugFlags.LogItemIndexing) {
                        log.info("--- " + itemDescriptionTypeName + ", stringDescription = " + stringDescription);
                    }
                } else {
                    String mimeTypeUsageTypeName = mimeTypeUsageType.getMimeTypeUsageTypeName();

                    if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.TEXT.name())) {
                        ItemClobDescription itemClobDescription = itemControl.getItemClobDescription(itemDescription);
                        String clobDescription = itemClobDescription.getClobDescription();

                        // TODO: mime type conversion to text/plain happens here
                        document.add(new Field(itemDescriptionTypeName, clobDescription, FieldTypes.NOT_STORED_TOKENIZED));

                        if(IndexerDebugFlags.LogItemIndexing) {
                            log.info("--- " + itemDescriptionTypeName + ", clobDescription = " + clobDescription);
                        }
                    } // Others are not supported at this time, DOCUMENT probably should be.
                }
            }
        });

        return document;
    }

}
