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

package com.echothree.ui.web.cms.action;

import com.echothree.ui.web.cms.framework.ParameterConstants;
import com.google.common.base.Splitter;
import javax.servlet.http.HttpServletRequest;

public class ItemDescriptionNames {

    public String itemDescriptionTypeName;
    public String itemName;
    public String[] itemNames;
    public String languageIsoName;

    public ItemDescriptionNames(HttpServletRequest request) {
        var itemNamesParameter = request.getParameter(ParameterConstants.ITEM_NAMES);

        itemDescriptionTypeName = request.getParameter(ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME);
        itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);

        // If ItemName exists as a parameter, try to split it using a :. If there was only
        // one result, then clear itemNames, otherwise clear itemname. Onely one of the two
        // should be not null in the end.
        if(itemNamesParameter != null) {
            itemNames = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(itemNamesParameter).toArray(new String[0]);
            
            if(itemNames.length == 0) {
                itemNames = null;
            }
        }
    }

    public boolean hasAllNames() {
        // You must have either itemName or itemNames, but never both.
        return itemDescriptionTypeName != null
                && ((itemName != null && itemNames == null) || (itemName == null && itemNames != null))
                && languageIsoName != null;
    }

}
