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

package com.echothree.model.control.search.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class UserVisitSearchFacetTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private ListWrapper<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItems;
    private ListWrapper<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegers;
    private ListWrapper<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongs;
    
    /** Creates a new instance of UserVisitSearchFacetTransfer */
    public UserVisitSearchFacetTransfer(EntityAttributeTransfer entityAttribute, ListWrapper<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItems,
            ListWrapper<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegers, ListWrapper<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongs) {
        this.entityAttribute = entityAttribute;
        this.userVisitSearchFacetListItems = userVisitSearchFacetListItems;
        this.userVisitSearchFacetIntegers = userVisitSearchFacetIntegers;
        this.userVisitSearchFacetLongs = userVisitSearchFacetLongs;
    }

    /**
     * Returns the entityAttribute.
     * @return the entityAttribute
     */
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }

    /**
     * Sets the entityAttribute.
     * @param entityAttribute the entityAttribute to set
     */
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     * Returns the userVisitSearchFacetListItems.
     * @return the userVisitSearchFacetListItems
     */
    public ListWrapper<UserVisitSearchFacetListItemTransfer> getUserVisitSearchFacetListItems() {
        return userVisitSearchFacetListItems;
    }

    /**
     * Sets the userVisitSearchFacetListItems.
     * @param userVisitSearchFacetListItems the userVisitSearchFacetListItems to set
     */
    public void setUserVisitSearchFacetListItems(ListWrapper<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItems) {
        this.userVisitSearchFacetListItems = userVisitSearchFacetListItems;
    }

    /**
     * Returns the userVisitSearchFacetIntegers.
     * @return the userVisitSearchFacetIntegers
     */
    public ListWrapper<UserVisitSearchFacetIntegerTransfer> getUserVisitSearchFacetIntegers() {
        return userVisitSearchFacetIntegers;
    }

    /**
     * Sets the userVisitSearchFacetIntegers.
     * @param userVisitSearchFacetIntegers the userVisitSearchFacetIntegers to set
     */
    public void setUserVisitSearchFacetIntegers(ListWrapper<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegers) {
        this.userVisitSearchFacetIntegers = userVisitSearchFacetIntegers;
    }

    /**
     * Returns the userVisitSearchFacetLongs.
     * @return the userVisitSearchFacetLongs
     */
    public ListWrapper<UserVisitSearchFacetLongTransfer> getUserVisitSearchFacetLongs() {
        return userVisitSearchFacetLongs;
    }

    /**
     * Sets the userVisitSearchFacetLongs.
     * @param userVisitSearchFacetLongs the userVisitSearchFacetLongs to set
     */
    public void setUserVisitSearchFacetLongs(ListWrapper<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongs) {
        this.userVisitSearchFacetLongs = userVisitSearchFacetLongs;
    }

}
