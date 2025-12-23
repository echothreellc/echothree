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

package com.echothree.model.control.content.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContentPageAreaTransfer
        extends BaseTransfer {

    private ContentPageTransfer contentPage;
    private ContentPageLayoutAreaTransfer contentPageLayoutArea;
    private LanguageTransfer language;
    private MimeTypeTransfer mimeType;
    private ByteArray blob;
    private String clob;
    private String string;
    private String url;

    /** Creates a new instance of ContentPageAreaTransfer */
    public ContentPageAreaTransfer(ContentPageTransfer contentPage, ContentPageLayoutAreaTransfer contentPageLayoutArea, LanguageTransfer language,
            MimeTypeTransfer mimeType, ByteArray blob, String clob, String string, String url) {
        this.contentPage = contentPage;
        this.contentPageLayoutArea = contentPageLayoutArea;
        this.language = language;
        this.mimeType = mimeType;
        this.blob = blob;
        this.clob = clob;
        this.string = string;
        this.url = url;
    }

    /**
     * Returns the contentPage.
     * @return the contentPage
     */
    public ContentPageTransfer getContentPage() {
        return contentPage;
    }

    /**
     * Sets the contentPage.
     * @param contentPage the contentPage to set
     */
    public void setContentPage(ContentPageTransfer contentPage) {
        this.contentPage = contentPage;
    }

    /**
     * Returns the contentPageLayoutArea.
     * @return the contentPageLayoutArea
     */
    public ContentPageLayoutAreaTransfer getContentPageLayoutArea() {
        return contentPageLayoutArea;
    }

    /**
     * Sets the contentPageLayoutArea.
     * @param contentPageLayoutArea the contentPageLayoutArea to set
     */
    public void setContentPageLayoutArea(ContentPageLayoutAreaTransfer contentPageLayoutArea) {
        this.contentPageLayoutArea = contentPageLayoutArea;
    }

    /**
     * Returns the language.
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
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
     * Returns the string.
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string.
     * @param string the string to set
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Returns the url.
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}