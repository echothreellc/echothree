// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.content.common.transfer.ContentPageLayoutAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaBlob;
import com.echothree.model.data.content.server.entity.ContentPageAreaClob;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageAreaString;
import com.echothree.model.data.content.server.entity.ContentPageAreaUrl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ContentPageAreaTransferCache
        extends BaseContentTransferCache<ContentPageArea, ContentPageAreaTransfer> {

    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
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

        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ContentOptions.ContentPageAreaIncludeBlob);
            includeClob = options.contains(ContentOptions.ContentPageAreaIncludeClob);
            includeString = options.contains(ContentOptions.ContentPageAreaIncludeString);
            includeUrl = options.contains(ContentOptions.ContentPageAreaIncludeUrl);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ContentPageAreaTransfer.class);
            
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
        ContentPageAreaTransfer contentPageAreaTransfer = get(contentPageArea);
        
        if(contentPageAreaTransfer == null) {
            ContentPageAreaDetail contentPageAreaDetail = contentPageArea.getLastDetail();
            ContentPageTransfer contentPageTransfer = filterContentPage ? null : contentControl.getContentPageTransfer(userVisit, contentPageAreaDetail.getContentPage());
            ContentPageLayoutAreaTransfer contentPageLayoutAreaTransfer = filterContentPageLayoutArea ? null : contentControl.getContentPageLayoutAreaTransfer(userVisit, contentPageAreaDetail.getContentPageLayoutArea());
            LanguageTransfer languageTransfer = filterLanguage ? null : partyControl.getLanguageTransfer(userVisit, contentPageAreaDetail.getLanguage());
            MimeType mimeType = contentPageAreaDetail.getMimeType();
            MimeTypeTransfer mimeTypeTransfer = filterMimeType ? null : mimeType == null ? null : coreControl.getMimeTypeTransfer(userVisit, mimeType);
            ByteArray blob = null;
            String clob = null;
            String string = null;
            String url = null;
            
            if(mimeType != null) {
                if(includeBlob) {
                    ContentPageAreaBlob contentPageAreaBlob = contentControl.getContentPageAreaBlob(contentPageAreaDetail);

                    if(contentPageAreaBlob != null) {
                        blob = contentPageAreaBlob.getBlob();
                    }
                }

                if(includeClob) {
                    ContentPageAreaClob contentPageAreaClob = contentControl.getContentPageAreaClob(contentPageAreaDetail);

                    if(contentPageAreaClob != null) {
                        clob = contentPageAreaClob.getClob();
                    }
                }
            }

            if(includeString) {
                ContentPageAreaString contentPageAreaString = contentControl.getContentPageAreaString(contentPageAreaDetail);

                if(contentPageAreaString != null) {
                    string = contentPageAreaString.getString();
                }
            }

            if(includeUrl) {
                ContentPageAreaUrl contentPageAreaUrl = contentControl.getContentPageAreaUrl(contentPageAreaDetail);

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
