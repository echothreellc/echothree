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

package com.echothree.model.control.core.common;

public enum MimeTypes {
    
    APPLICATION_PDF("application/pdf"),
    APPLICATION_POSTSCRIPT("application/postscript"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    MESSAGE_RFC822("message/rfc822"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_X_MARKUP("text/x-markup"),
    TEXT_X_TEXTILE("text/x-textile");

    private final String mimeTypeName;
    
    private MimeTypes(String mimeTypeName) {
        this.mimeTypeName = mimeTypeName;
    }
    
    public String mimeTypeName() {
        return mimeTypeName;
    }
    
}
