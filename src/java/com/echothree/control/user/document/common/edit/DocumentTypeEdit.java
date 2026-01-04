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

package com.echothree.control.user.document.common.edit;

import com.echothree.control.user.core.common.spec.MimeTypeUsageTypeSpec;
import com.echothree.control.user.document.common.spec.DocumentTypeSpec;

public interface DocumentTypeEdit
        extends DocumentTypeSpec, MimeTypeUsageTypeSpec, DocumentTypeDescriptionEdit {
    
    String getParentDocumentTypeName();
    void setParentDocumentTypeName(String parentDocumentTypeName);

    String getMaximumPages();
    void setMaximumPages(String maximumPages);

    String getIsDefault();
    void setIsDefault(String isDefault);

    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
