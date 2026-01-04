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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PostalAddressLineElementTransfer
        extends BaseTransfer {
    
    private PostalAddressLineTransfer postalAddressLine;
    private Integer postalAddressLineElementSortOrder;
    private PostalAddressElementTypeTransfer postalAddressElementType;
    private String prefix;
    private Boolean alwaysIncludePrefix;
    private String suffix;
    private Boolean alwaysIncludeSuffix;
    
    /** Creates a new instance of PostalAddressLineElementTransfer */
    public PostalAddressLineElementTransfer(PostalAddressLineTransfer postalAddressLine, Integer postalAddressLineElementSortOrder,
            PostalAddressElementTypeTransfer postalAddressElementType, String prefix, Boolean alwaysIncludePrefix, String suffix,
            Boolean alwaysIncludeSuffix) {
        this.postalAddressLine = postalAddressLine;
        this.postalAddressLineElementSortOrder = postalAddressLineElementSortOrder;
        this.postalAddressElementType = postalAddressElementType;
        this.prefix = prefix;
        this.alwaysIncludePrefix = alwaysIncludePrefix;
        this.suffix = suffix;
        this.alwaysIncludeSuffix = alwaysIncludeSuffix;
    }
    
    public PostalAddressLineTransfer getPostalAddressLine() {
        return postalAddressLine;
    }
    
    public void setPostalAddressLine(PostalAddressLineTransfer postalAddressLine) {
        this.postalAddressLine = postalAddressLine;
    }
    
    public Integer getPostalAddressLineElementSortOrder() {
        return postalAddressLineElementSortOrder;
    }
    
    public void setPostalAddressLineElementSortOrder(Integer postalAddressLineElementSortOrder) {
        this.postalAddressLineElementSortOrder = postalAddressLineElementSortOrder;
    }
    
    public PostalAddressElementTypeTransfer getPostalAddressElementType() {
        return postalAddressElementType;
    }
    
    public void setPostalAddressElementType(PostalAddressElementTypeTransfer postalAddressElementType) {
        this.postalAddressElementType = postalAddressElementType;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public Boolean getAlwaysIncludePrefix() {
        return alwaysIncludePrefix;
    }
    
    public void setAlwaysIncludePrefix(Boolean alwaysIncludePrefix) {
        this.alwaysIncludePrefix = alwaysIncludePrefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public Boolean getAlwaysIncludeSuffix() {
        return alwaysIncludeSuffix;
    }
    
    public void setAlwaysIncludeSuffix(Boolean alwaysIncludeSuffix) {
        this.alwaysIncludeSuffix = alwaysIncludeSuffix;
    }
    
}
