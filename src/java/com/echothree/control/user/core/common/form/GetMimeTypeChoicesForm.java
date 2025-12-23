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

package com.echothree.control.user.core.common.form;

import com.echothree.control.user.comment.common.spec.CommentSpec;
import com.echothree.control.user.comment.common.spec.CommentTypeSpec;
import com.echothree.control.user.core.common.spec.MimeTypeUsageTypeSpec;
import com.echothree.control.user.document.common.spec.DocumentSpec;
import com.echothree.control.user.document.common.spec.DocumentTypeSpec;
import com.echothree.control.user.forum.common.spec.ForumMessageSpec;
import com.echothree.control.user.forum.common.spec.ForumSpec;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeSpec;

public interface GetMimeTypeChoicesForm
        extends MimeTypeUsageTypeSpec, ItemDescriptionTypeSpec, ForumSpec, ForumMessageSpec, CommentTypeSpec, CommentSpec, DocumentTypeSpec, DocumentSpec {
    
    String getDefaultMimeTypeChoice();
    void setDefaultMimeTypeChoice(String defaultMimeTypeChoice);
    
    String getAllowNullChoice();
    void setAllowNullChoice(String allowNullChoice);
    
}
