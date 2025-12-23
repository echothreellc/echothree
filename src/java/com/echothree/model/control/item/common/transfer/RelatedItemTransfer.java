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

package com.echothree.model.control.item.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class RelatedItemTransfer
        extends BaseTransfer {
    
    private RelatedItemTypeTransfer relatedItemType;
    private ItemTransfer fromItem;
    private ItemTransfer toItem;
    private Integer sortOrder;
    
    /** Creates a new instance of RelatedItemTransfer */
    public RelatedItemTransfer(RelatedItemTypeTransfer relatedItemType, ItemTransfer fromItem, ItemTransfer toItem, Integer sortOrder) {
        this.relatedItemType = relatedItemType;
        this.fromItem = fromItem;
        this.toItem = toItem;
        this.sortOrder = sortOrder;
    }
    
    public RelatedItemTypeTransfer getRelatedItemType() {
        return relatedItemType;
    }
    
    public void setRelatedItemType(RelatedItemTypeTransfer relatedItemType) {
        this.relatedItemType = relatedItemType;
    }
    
    public ItemTransfer getFromItem() {
        return fromItem;
    }
    
    public void setFromItem(ItemTransfer fromItem) {
        this.fromItem = fromItem;
    }
    
    public ItemTransfer getToItem() {
        return toItem;
    }
    
    public void setToItem(ItemTransfer toItem) {
        this.toItem = toItem;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
