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
import com.echothree.util.common.transfer.ListWrapper;

public class PostalAddressLineTransfer
        extends BaseTransfer {
    
    private PostalAddressFormatTransfer postalAddressFormat;
    private Integer postalAddressLineSortOrder;
    private String prefix;
    private Boolean alwaysIncludePrefix;
    private String suffix;
    private Boolean alwaysIncludeSuffix;
    private Boolean collapseIfEmpty;
    
    private ListWrapper<PostalAddressLineElementTransfer> postalAddressLineElements;
    
    /** Creates a new instance of PostalAddressLineTransfer */
    public PostalAddressLineTransfer(PostalAddressFormatTransfer postalAddressFormat, Integer postalAddressLineSortOrder,
            String prefix, Boolean alwaysIncludePrefix, String suffix, Boolean alwaysIncludeSuffix, Boolean collapseIfEmpty) {
        this.postalAddressFormat = postalAddressFormat;
        this.postalAddressLineSortOrder = postalAddressLineSortOrder;
        this.prefix = prefix;
        this.alwaysIncludePrefix = alwaysIncludePrefix;
        this.suffix = suffix;
        this.alwaysIncludeSuffix = alwaysIncludeSuffix;
        this.collapseIfEmpty = collapseIfEmpty;
    }
    
    public PostalAddressFormatTransfer getPostalAddressFormat() {
        return postalAddressFormat;
    }
    
    public void setPostalAddressFormat(PostalAddressFormatTransfer postalAddressFormat) {
        this.postalAddressFormat = postalAddressFormat;
    }
    
    public Integer getPostalAddressLineSortOrder() {
        return postalAddressLineSortOrder;
    }
    
    public void setPostalAddressLineSortOrder(Integer postalAddressLineSortOrder) {
        this.postalAddressLineSortOrder = postalAddressLineSortOrder;
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
    
    public Boolean getCollapseIfEmpty() {
        return collapseIfEmpty;
    }
    
    public void setCollapseIfEmpty(Boolean collapseIfEmpty) {
        this.collapseIfEmpty = collapseIfEmpty;
    }

    public ListWrapper<PostalAddressLineElementTransfer> getPostalAddressLineElements() {
        return postalAddressLineElements;
    }

    public void setPostalAddressLineElements(ListWrapper<PostalAddressLineElementTransfer> postalAddressLineElements) {
        this.postalAddressLineElements = postalAddressLineElements;
    }
    
}
