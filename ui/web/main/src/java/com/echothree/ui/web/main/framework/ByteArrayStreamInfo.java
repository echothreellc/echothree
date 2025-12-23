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

package com.echothree.ui.web.main.framework;

import com.echothree.ui.web.main.framework.MainBaseDownloadAction.StreamInfo;
import java.io.ByteArrayInputStream;

public class ByteArrayStreamInfo
        implements StreamInfo {

    private String contentType;
    private ByteArrayInputStream inputStream;
    private String contentDisposition;
    private String filename;

    /**
     * Creates a new instance of ByteArrayStreamInfo
     */
    public ByteArrayStreamInfo(String contentType, ByteArrayInputStream inputStream, String contentDisposition, String filename) {
        this.contentType = contentType;
        this.inputStream = inputStream;
        this.contentDisposition = contentDisposition;
        this.filename = filename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String getContentDisposition() {
        return this.contentDisposition;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

}
