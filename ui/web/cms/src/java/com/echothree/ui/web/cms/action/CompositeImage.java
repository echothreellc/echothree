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

package com.echothree.ui.web.cms.action;

import com.echothree.util.common.persistence.type.ByteArray;
import java.io.Serializable;

public class CompositeImage
        implements Serializable {

    String mimeTypeName;
    ByteArray blobDescription;
    String imagesIncludedETagComponent;
    Long lastModified;
    boolean hasMissingItemDescription;

    public CompositeImage(String mimeTypeName, ByteArray blobDescription, String imagesPresent, Long lastModified, boolean hasMissingItemDescription) {
        this.mimeTypeName = mimeTypeName;
        this.blobDescription = blobDescription;
        this.imagesIncludedETagComponent = imagesPresent;
        this.lastModified = lastModified;
        this.hasMissingItemDescription = hasMissingItemDescription;
    }

}