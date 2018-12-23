// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.shipping.server.transfer;

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.shipping.common.ShippingOptions;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ShippingMethodTransferCache
        extends BaseShippingTransferCache<ShippingMethod, ShippingMethodTransfer> {
    
    SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
    
    boolean includeComments;
    
    /** Creates a new instance of ShippingMethodTransferCache */
    public ShippingMethodTransferCache(UserVisit userVisit, ShippingControl shippingControl) {
        super(userVisit, shippingControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(ShippingOptions.ShippingMethodIncludeKey));
            setIncludeGuid(options.contains(ShippingOptions.ShippingMethodIncludeGuid));
            includeComments = options.contains(ShippingOptions.ShippingMethodIncludeComments);
            setIncludeEntityAttributeGroups(options.contains(ShippingOptions.ShippingMethodIncludeEntityAttributeGroups));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ShippingMethodTransfer getShippingMethodTransfer(ShippingMethod shippingMethod) {
        ShippingMethodTransfer shippingMethodTransfer = get(shippingMethod);

        if(shippingMethodTransfer == null) {
            ShippingMethodDetail shippingMethodDetail = shippingMethod.getLastDetail();
            String shippingMethodName = shippingMethodDetail.getShippingMethodName();
            Selector geoCodeSelector = shippingMethodDetail.getGeoCodeSelector();
            SelectorTransfer geoCodeSelectorTransfer = geoCodeSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, geoCodeSelector);
            Selector itemSelector = shippingMethodDetail.getItemSelector();
            SelectorTransfer itemSelectorTransfer = itemSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, itemSelector);
            Integer sortOrder = shippingMethodDetail.getSortOrder();
            String description = shippingControl.getBestShippingMethodDescription(shippingMethod, getLanguage());

            shippingMethodTransfer = new ShippingMethodTransfer(shippingMethodName, geoCodeSelectorTransfer, itemSelectorTransfer, sortOrder, description);
            put(shippingMethod, shippingMethodTransfer);

            if(includeComments) {
                setupComments(shippingMethod, null, shippingMethodTransfer, CommentConstants.CommentType_SHIPPING_METHOD);
            }
        }

        return shippingMethodTransfer;
    }
    
}
