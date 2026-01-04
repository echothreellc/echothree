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

package com.echothree.control.user.printer.common.edit;

import com.echothree.control.user.core.common.spec.MimeTypeSpec;
import com.echothree.control.user.document.common.edit.DocumentDescriptionEdit;
import com.echothree.control.user.printer.common.spec.PrinterGroupSpec;
import com.echothree.util.common.persistence.type.ByteArray;

public interface PrinterGroupJobEdit
        extends PrinterGroupSpec, MimeTypeSpec, DocumentDescriptionEdit {
    
    String getCopies();
    void setCopies(String copies);

    String getPriority();
    void setPriority(String priority);

    ByteArray getBlob();
    void setBlob(ByteArray blob);
    
    String getClob();
    void setClob(String clob);
    
}
