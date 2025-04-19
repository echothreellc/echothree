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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;

public class ContentPageAreaTransferCache
        extends BaseContentTransferCache<ContentPageArea, ContentPageAreaTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    boolean includeBlob;
    boolean includeClob;
    boolean includeString;
    boolean includeUrl;

    TransferProperties transferProperties;
    boolean filterContentPage;
    boolean filterContentPageLayoutArea;
    boolean filterLanguage;
    boolean filterMimeType;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentPageAreaTransferCache */
    public ContentPageAreaTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);

        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ContentOptions.ContentPageAreaIncludeBlob);
            includeClob = options.contains(ContentOptions.ContentPageAreaIncludeClob);
            includeString = options.contains(ContentOptions.ContentPageAreaIncludeString);
            includeUrl = options.contains(ContentOptions.ContentPageAreaIncludeUrl);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentPageAreaTransfer.class);
            
            if(properties != null) {
                filterContentPage = !properties.contains(ContentProperties.CONTENT_PAGE);
                filterContentPageLayoutArea = !properties.contains(ContentProperties.CONTENT_PAGE_LAYOUT_AREA);
                filterLanguage = !properties.contains(ContentProperties.LANGUAGE);
                filterMimeType = !properties.contains(ContentProperties.MIME_TYPE);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public ContentPageAreaTransfer getContentPageAreaTransfer(ContentPageArea contentPageArea) {
        var contentPageAreaTransfer = get(contentPageArea);
        
        if(contentPageAreaTransfer == null) {
            var contentPageAreaDetail = contentPageArea.getLastDetail();
            var contentPageTransfer = filterContentPage ? null : contentControl.getContentPageTransfer(userVisit, contentPageAreaDetail.getContentPage());
            var contentPageLayoutAreaTransfer = filterContentPageLayoutArea ? null : contentControl.getContentPageLayoutAreaTransfer(userVisit, contentPageAreaDetail.getContentPageLayoutArea());
            var languageTransfer = filterLanguage ? null : partyControl.getLanguageTransfer(userVisit, contentPageAreaDetail.getLanguage());
            var mimeType = contentPageAreaDetail.getMimeType();
            var mimeTypeTransfer = filterMimeType ? null : mimeType == null ? null : mimeTypeControl.getMimeTypeTransfer(userVisit, mimeType);
            ByteArray blob = null;
            String clob = null;
            String string = null;
            String url = null;
            
            if(mimeType != null) {
                if(includeBlob) {
                    var contentPageAreaBlob = contentControl.getContentPageAreaBlob(contentPageAreaDetail);

                    if(contentPageAreaBlob != null) {
                        blob = contentPageAreaBlob.getBlob();
                    }
                }

                if(includeClob) {
                    var contentPageAreaClob = contentControl.getContentPageAreaClob(contentPageAreaDetail);

                    if(contentPageAreaClob != null) {
                        clob = contentPageAreaClob.getClob();
                    }
                }
            }

            if(includeString) {
                var contentPageAreaString = contentControl.getContentPageAreaString(contentPageAreaDetail);

                if(contentPageAreaString != null) {
                    string = contentPageAreaString.getString();
                }
            }

            if(includeUrl) {
                var contentPageAreaUrl = contentControl.getContentPageAreaUrl(contentPageAreaDetail);

                if(contentPageAreaUrl != null) {
                    url = contentPageAreaUrl.getUrl();
                }
            }

            contentPageAreaTransfer = new ContentPageAreaTransfer(contentPageTransfer, contentPageLayoutAreaTransfer, languageTransfer, mimeTypeTransfer,
                    blob, clob, string, url);
            put(contentPageArea, contentPageAreaTransfer);
        }
        
        return contentPageAreaTransfer;
    }

}
