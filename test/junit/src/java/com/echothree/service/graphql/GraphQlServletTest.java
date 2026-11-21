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

package com.echothree.service.graphql;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphQlServletTest {

    private String sanitizeHeaderValue(String headerValue) throws Exception {
        // The servlet is built separately and is available on the runtime classpath.
        var servletClass = Class.forName("com.echothree.service.graphql.GraphQlServlet");
        var method = servletClass.getDeclaredMethod("sanitizeHeaderValue", String.class);
        method.setAccessible(true);

        return (String)method.invoke(servletClass.getDeclaredConstructor().newInstance(), headerValue);
    }

    @Test
    public void removesCarriageReturnsAndLineFeedsFromHeaders() throws Exception {
        assertEquals("https://example.comX-Injected: true",
                sanitizeHeaderValue("https://example.com\r\nX-Injected: true\r\n"));
        assertEquals("abc", sanitizeHeaderValue("\ra\nb\r\nc\n\r"));
        assertEquals("", sanitizeHeaderValue("\r\n\r\n"));
    }

    @Test
    public void preservesMissingEmptyAndOrdinaryHeaderValues() throws Exception {
        assertNull(sanitizeHeaderValue(null));
        assertEquals("", sanitizeHeaderValue(""));
        var headerValue = "Content-Type, X-Request-ID; value=\"café\"\t/path";
        assertEquals(headerValue, sanitizeHeaderValue(headerValue));
    }

    @Test
    public void handlesLongHeaderValuesWithoutRegularExpressions() throws Exception {
        var headerValue = "a\r\nb\nc\r".repeat(100_000);
        assertEquals("abc".repeat(100_000), sanitizeHeaderValue(headerValue));
    }
}
