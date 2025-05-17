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

package com.echothree.util.common.persistence.type;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;
import javax.imageio.stream.MemoryCacheImageInputStream;

public class ByteArray
        implements Serializable {
    
    byte []_bytes;
    
    /** Creates a new instance of ByteArray */
    public ByteArray(byte []bytes) {
        _bytes = bytes;
    }
    
    public byte[]byteArrayValue() {
        return _bytes;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ByteArray that) {
            return Arrays.equals(_bytes, that.byteArrayValue());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        var hash = 7;

        hash = 53 * hash + Arrays.hashCode(this._bytes);

        return hash;
    }
    
    public int length() {
        return _bytes.length;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        return new ByteArrayInputStream(_bytes);
    }

    public ByteArrayInputStream getByteArrayInputStream(int offset, int length) {
        return new ByteArrayInputStream(_bytes, offset, length);
    }

    public MemoryCacheImageInputStream getMemoryCacheImageInputStream() {
        return new MemoryCacheImageInputStream(getByteArrayInputStream());
    }

}
