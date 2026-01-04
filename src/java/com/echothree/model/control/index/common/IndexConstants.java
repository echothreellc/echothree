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

package com.echothree.model.control.index.common;

public interface IndexConstants {

    // Only used behind-the-scenes, may be a character that QueryParser doesn't like.
    String INDEX_FIELD_VARIATION_SEPARATOR = ":";

    // May be used by users of the system, should be able to be escaped but must not
    // be a character that's valid in a Name.
    String INDEX_SUBFIELD_SEPARATOR = ".";

}
