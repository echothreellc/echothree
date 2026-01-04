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

package com.echothree.util.common.cyberneko;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.cyberneko.html.filters.Writer;

public class HtmlWriter
        extends Writer {
    
    /** Creates a new instance of HtmlWriter */
    public HtmlWriter() {
        super();
    }

    /** Creates a new instance of HtmlWriter */
    public HtmlWriter(OutputStream outputStream, String encoding)
            throws UnsupportedEncodingException {
        super(outputStream, encoding);
    }

    /** Creates a new instance of HtmlWriter */
    public HtmlWriter(java.io.Writer writer, String encoding) {
        super(writer, encoding);
    }

    private static Map<String, String> entityTranslations;

    static {
        Map<String, String> entityTranslationsMap = new HashMap<>(1);

        entityTranslationsMap.put("apos", "'");
        
        entityTranslations = Collections.unmodifiableMap(entityTranslationsMap);
    }
    
    /** Print entity. */
    @Override
    protected void printEntity(String entity) {
        var translatedEntity = entityTranslations.get(entity);
        
        if(translatedEntity == null) {
            super.printEntity(entity);
        } else {
            fPrinter.print(translatedEntity);
            fPrinter.flush();
        }
    }

}
