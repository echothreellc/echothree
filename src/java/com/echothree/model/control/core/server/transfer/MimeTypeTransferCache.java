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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MimeTypeTransferCache
        extends BaseCoreTransferCache<MimeType, MimeTypeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    boolean includeMimeTypeFileExtensions;
    
    TransferProperties transferProperties;
    boolean filterMimeTypeName;
    boolean filterEntityAttributeType;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of MimeTypeTransferCache */
    protected MimeTypeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeMimeTypeFileExtensions = options.contains(CoreOptions.MimeTypeIncludeMimeTypeFileExtensions);
        }

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(MimeTypeTransfer.class);
            
            if(properties != null) {
                filterMimeTypeName = !properties.contains(CoreProperties.MIME_TYPE_NAME);
                filterEntityAttributeType = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_TYPE);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public MimeTypeTransfer getMimeTypeTransfer(UserVisit userVisit, MimeType mimeType) {
        var mimeTypeTransfer = get(mimeType);

        if(mimeTypeTransfer == null) {
            var mimeTypeDetail = mimeType.getLastDetail();
            var mimeTypeName = filterMimeTypeName ? null : mimeTypeDetail.getMimeTypeName();
            var entityAttributeType = filterEntityAttributeType ? null : coreControl.getEntityAttributeTypeTransfer(userVisit, mimeTypeDetail.getEntityAttributeType());
            var isDefault = filterIsDefault ? null : mimeTypeDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : mimeTypeDetail.getSortOrder();
            var description = filterDescription ? null : mimeTypeControl.getBestMimeTypeDescription(mimeType, getLanguage(userVisit));

            mimeTypeTransfer = new MimeTypeTransfer(mimeTypeName, entityAttributeType, isDefault, sortOrder, description);
            put(userVisit, mimeType, mimeTypeTransfer);

            if(includeMimeTypeFileExtensions) {
                mimeTypeTransfer.setMimeTypeFileExtensions(new ListWrapper<>(mimeTypeControl.getMimeTypeFileExtensionTransfersByMimeType(userVisit, mimeType)));
            }
        }

        return mimeTypeTransfer;
    }

}
