// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.graphql.server.util.count;

import com.echothree.util.server.validation.Validator;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public final class GraphQlCursorUtils {

    private static class GraphQlCursorUtilsHolder {
        static GraphQlCursorUtils instance = new GraphQlCursorUtils();
    }
    
    public static GraphQlCursorUtils getInstance() {
        return GraphQlCursorUtilsHolder.instance;
    }

    private final BaseEncoding baseEncoding = BaseEncoding.base64();
    private final Pattern cursorPattern = Pattern.compile("^([a-zA-Z0-9-_]+)\\/([a-zA-Z0-9-_]+)\\/([0-9]+)$");
    private final byte xorValue = (byte)0b10101010;

    // Intended to be as "basic" as it appears - just to prevent casual tampering.
    private byte[] xorBytes(byte[] originalBytes) {
        var modifiedBytes = new byte[originalBytes.length];

        for(var i = 0; i < originalBytes.length; i++) {
            modifiedBytes[i] = (byte)(originalBytes[i] ^ xorValue);
        }

        return modifiedBytes;
    }

    public Long fromCursor(final String componentVendorName, final String entityTypeName, final String cursor) {
        Long offset = null;

        // If it cannot be decoded, offset should remain null.
        if(cursor != null && baseEncoding.canDecode(cursor)) {
            var byteCursor = baseEncoding.decode(cursor);
            var xoredCursor = xorBytes(byteCursor);
            var unencodedCursor = new String(xoredCursor, StandardCharsets.UTF_8);
            var matcher = cursorPattern.matcher(unencodedCursor);

            // If it fails to match against cursorPattern, offset should remain null.
            if(matcher.matches()) {
                var foundComponentVendorName = matcher.group(1);
                var foundEntityTypeName = matcher.group(2);

                if(componentVendorName.equals(foundComponentVendorName) && entityTypeName.equals(foundEntityTypeName)) {
                    var unvalidatedCursor = matcher.group(3);
                    var validatedCursor = Validator.validateUnsignedLong(unvalidatedCursor);

                    offset = validatedCursor == null ? null : Long.valueOf(validatedCursor);
                }
            }
        }

        return offset;
    }

    public String toCursor(final String componentVendorName, final String entityTypeName, final long offset) {
        var unencodedCursor = componentVendorName + '/' + entityTypeName + '/' + offset;
        var byteCursor = unencodedCursor.getBytes(StandardCharsets.UTF_8);
        var xoredCursor = xorBytes(byteCursor);

        return baseEncoding.encode(xoredCursor);
    }

}
