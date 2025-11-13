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

package com.echothree.model.control.shipping.server.transfer;

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.shipping.common.ShippingOptions;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShippingMethodTransferCache
        extends BaseShippingTransferCache<ShippingMethod, ShippingMethodTransfer> {
    
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    ShippingControl shippingControl = Session.getModelController(ShippingControl.class);

    boolean includeComments;
    
    /** Creates a new instance of ShippingMethodTransferCache */
    public ShippingMethodTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ShippingOptions.ShippingMethodIncludeUuid));
            includeComments = options.contains(ShippingOptions.ShippingMethodIncludeComments);
            setIncludeEntityAttributeGroups(options.contains(ShippingOptions.ShippingMethodIncludeEntityAttributeGroups));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ShippingMethodTransfer getShippingMethodTransfer(ShippingMethod shippingMethod) {
        var shippingMethodTransfer = get(shippingMethod);

        if(shippingMethodTransfer == null) {
            var shippingMethodDetail = shippingMethod.getLastDetail();
            var shippingMethodName = shippingMethodDetail.getShippingMethodName();
            var geoCodeSelector = shippingMethodDetail.getGeoCodeSelector();
            var geoCodeSelectorTransfer = geoCodeSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, geoCodeSelector);
            var itemSelector = shippingMethodDetail.getItemSelector();
            var itemSelectorTransfer = itemSelector == null ? null : selectorControl.getSelectorTransfer(userVisit, itemSelector);
            var sortOrder = shippingMethodDetail.getSortOrder();
            var description = shippingControl.getBestShippingMethodDescription(shippingMethod, getLanguage(userVisit));

            shippingMethodTransfer = new ShippingMethodTransfer(shippingMethodName, geoCodeSelectorTransfer, itemSelectorTransfer, sortOrder, description);
            put(userVisit, shippingMethod, shippingMethodTransfer);

            if(includeComments) {
                setupComments(userVisit, shippingMethod, null, shippingMethodTransfer, CommentConstants.CommentType_SHIPPING_METHOD);
            }
        }

        return shippingMethodTransfer;
    }
    
}
