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
     * Returns the cacheEntryKey.
     * @return the cacheEntryKey
     */
    public String getCacheEntryKey() {
        return cacheEntryKey;
    }

    /**
     * Sets the cacheEntryKey.
     * @param cacheEntryKey the cacheEntryKey to set
     */
    public void setCacheEntryKey(String cacheEntryKey) {
        this.cacheEntryKey = cacheEntryKey;
    }

    /**
     * Returns the mimeType.
     * @return the mimeType
     */
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mimeType.
     * @param mimeType the mimeType to set
     */
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Returns the createdTime.
     * @return the createdTime
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the createdTime.
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Returns the unformattedCreatedTime.
     * @return the unformattedCreatedTime
     */
    public Long getUnformattedCreatedTime() {
        return unformattedCreatedTime;
    }

    /**
     * Sets the unformattedCreatedTime.
     * @param unformattedCreatedTime the unformattedCreatedTime to set
     */
    public void setUnformattedCreatedTime(Long unformattedCreatedTime) {
        this.unformattedCreatedTime = unformattedCreatedTime;
    }

    /**
     * Returns the validUntilTime.
     * @return the validUntilTime
     */
    public String getValidUntilTime() {
        return validUntilTime;
    }

    /**
     * Sets the validUntilTime.
     * @param validUntilTime the validUntilTime to set
     */
    public void setValidUntilTime(String validUntilTime) {
        this.validUntilTime = validUntilTime;
    }

    /**
     * Returns the unformattedValidUntilTime.
     * @return the unformattedValidUntilTime
     */
    public Long getUnformattedValidUntilTime() {
        return unformattedValidUntilTime;
    }

    /**
     * Sets the unformattedValidUntilTime.
     * @param unformattedValidUntilTime the unformattedValidUntilTime to set
     */
    public void setUnformattedValidUntilTime(Long unformattedValidUntilTime) {
        this.unformattedValidUntilTime = unformattedValidUntilTime;
    }

    /**
     * Returns the clob.
     * @return the clob
     */
    public String getClob() {
        return clob;
    }

    /**
     * Sets the clob.
     * @param clob the clob to set
     */
    public void setClob(String clob) {
        this.clob = clob;
    }

    /**
     * Returns the blob.
     * @return the blob
     */
    public ByteArray getBlob() {
        return blob;
    }

    /**
     * Sets the blob.
     * @param blob the blob to set
     */
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }

    /**
     * Returns the cacheEntryDependencies.
     * @return the cacheEntryDependencies
     */
    public ListWrapper<CacheEntryDependencyTransfer> getCacheEntryDependencies() {
        return cacheEntryDependencies;
    }

    /**
     * Sets the cacheEntryDependencies.
     * @param cacheEntryDependencies the cacheEntryDependencies to set
     */
    public void setCacheEntryDependencies(ListWrapper<CacheEntryDependencyTransfer> cacheEntryDependencies) {
        this.cacheEntryDependencies = cacheEntryDependencies;
    }

}
