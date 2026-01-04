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

package com.echothree.model.control.index.server.indexer;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

public class FieldTypes {

    private FieldTypes() {
        super();
    }
    
    /**
     * Indexed, stored, not tokenized. This is typically used when storing an EntityRef in an indexed document.
     */
    public static final FieldType STORED_NOT_TOKENIZED = new FieldType();
    
    /**
     * Indexed, not stored, tokenized. This is typically used for descriptions of things.
     */
    public static final FieldType NOT_STORED_TOKENIZED = new FieldType();
    
    /**
     * Indexed, not stored, not tokenized. This is typically used for the "not analyzed" variation of descriptions.
     */
    public static final FieldType NOT_STORED_NOT_TOKENIZED = new FieldType();

    static {
        STORED_NOT_TOKENIZED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        STORED_NOT_TOKENIZED.setStored(true);
        STORED_NOT_TOKENIZED.setTokenized(false);
        STORED_NOT_TOKENIZED.freeze();

        NOT_STORED_TOKENIZED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        NOT_STORED_TOKENIZED.setStored(false);
        NOT_STORED_TOKENIZED.setTokenized(true);
        NOT_STORED_TOKENIZED.freeze();

        NOT_STORED_NOT_TOKENIZED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        NOT_STORED_NOT_TOKENIZED.setStored(false);
        NOT_STORED_NOT_TOKENIZED.setTokenized(false);
        NOT_STORED_NOT_TOKENIZED.freeze();
    }

}
