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

package com.echothree.model.control.document.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class DocumentTransfer
        extends BaseTransfer {
    
    private String documentName;
    private DocumentTypeTransfer documentType;
    private MimeTypeTransfer mimeType;
    private Integer pages;
    private String description;
    private ByteArray blob;
    private String clob;
    private String eTag;
    
    /** Creates a new instance of DocumentTransfer */
    public DocumentTransfer(String documentName, DocumentTypeTransfer documentType, MimeTypeTransfer mimeType, Integer pages, String description,
            ByteArray blob, String clob, String eTag) {
        this.documentName = documentName;
        this.documentType = documentType;
        this.mimeType = mimeType;
        this.pages = pages;
        this.description = description;
        this.blob = blob;
        this.clob = clob;
        this.eTag = eTag;
    }

    /**
     * Returns the documentName.
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the documentName.
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Returns the documentType.
     * @return the documentType
     */
    public DocumentTypeTransfer getDocumentType() {
        return documentType;
    }

    /**
     * Sets the documentType.
     * @param documentType the documentType to set
     */
    public void setDocumentType(DocumentTypeTransfer documentType) {
        this.documentType = documentType;
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
     * Returns the pages.
     * @return the pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * Sets the pages.
     * @param pages the pages to set
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Returns the eTag.
     * @return the eTag
     */
    public String geteTag() {
        return eTag;
    }

    /**
     * Sets the eTag.
     * @param eTag the eTag to set
     */
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }
    
}
