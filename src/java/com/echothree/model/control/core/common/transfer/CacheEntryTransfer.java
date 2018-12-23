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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class CacheEntryTransfer
        extends BaseTransfer {
    
    private String cacheEntryKey;
    private MimeTypeTransfer mimeType;
    private String createdTime;
    private Long unformattedCreatedTime;
    private String validUntilTime;
    private Long unformattedValidUntilTime;
    private String clob;
    private ByteArray blob;

    private ListWrapper<CacheEntryDependencyTransfer> cacheEntryDependencies;
    
    /** Creates a new instance of CacheEntryTransfer */
    public CacheEntryTransfer(String cacheEntryKey, MimeTypeTransfer mimeType, String createdTime, Long unformattedCreatedTime, String validUntilTime,
            Long unformattedValidUntilTime, String clob, ByteArray blob) {
        this.cacheEntryKey = cacheEntryKey;
        this.mimeType = mimeType;
        this.createdTime = createdTime;
        this.unformattedCreatedTime = unformattedCreatedTime;
        this.validUntilTime = validUntilTime;
        this.unformattedValidUntilTime = unformattedValidUntilTime;
        this.clob = clob;
        this.blob = blob;
    }

    /**
     * @return the cacheEntryKey
     */
    public String getCacheEntryKey() {
        return cacheEntryKey;
    }

    /**
     * @param cacheEntryKey the cacheEntryKey to set
     */
    public void setCacheEntryKey(String cacheEntryKey) {
        this.cacheEntryKey = cacheEntryKey;
    }

    /**
     * @return the mimeType
     */
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return the unformattedCreatedTime
     */
    public Long getUnformattedCreatedTime() {
        return unformattedCreatedTime;
    }

    /**
     * @param unformattedCreatedTime the unformattedCreatedTime to set
     */
    public void setUnformattedCreatedTime(Long unformattedCreatedTime) {
        this.unformattedCreatedTime = unformattedCreatedTime;
    }

    /**
     * @return the validUntilTime
     */
    public String getValidUntilTime() {
        return validUntilTime;
    }

    /**
     * @param validUntilTime the validUntilTime to set
     */
    public void setValidUntilTime(String validUntilTime) {
        this.validUntilTime = validUntilTime;
    }

    /**
     * @return the unformattedValidUntilTime
     */
    public Long getUnformattedValidUntilTime() {
        return unformattedValidUntilTime;
    }

    /**
     * @param unformattedValidUntilTime the unformattedValidUntilTime to set
     */
    public void setUnformattedValidUntilTime(Long unformattedValidUntilTime) {
        this.unformattedValidUntilTime = unformattedValidUntilTime;
    }

    /**
     * @return the clob
     */
    public String getClob() {
        return clob;
    }

    /**
     * @param clob the clob to set
     */
    public void setClob(String clob) {
        this.clob = clob;
    }

    /**
     * @return the blob
     */
    public ByteArray getBlob() {
        return blob;
    }

    /**
     * @param blob the blob to set
     */
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }

    /**
     * @return the cacheEntryDependencies
     */
    public ListWrapper<CacheEntryDependencyTransfer> getCacheEntryDependencies() {
        return cacheEntryDependencies;
    }

    /**
     * @param cacheEntryDependencies the cacheEntryDependencies to set
     */
    public void setCacheEntryDependencies(ListWrapper<CacheEntryDependencyTransfer> cacheEntryDependencies) {
        this.cacheEntryDependencies = cacheEntryDependencies;
    }

}
