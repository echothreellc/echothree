// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

/**
 * MIT License
 * 
 * Copyright (c) 2016 Azamshul Azizy
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// Based on: https://github.com/azam/ulidj

package com.echothree.util.server.ulid;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.primitives.Longs;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ULID {

    private static final int RANDOM_LENGTH = 10;
    private static final int ULID_LENGTH = 26;
    
    private static final Pattern ULID = Pattern.compile("^([0123456789ABCDEFGHJKMNPQRSTVWXYZ]{" + ULID_LENGTH + "})$");

    private static final char[] base32 = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q',
        'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };
    
    // Time must be only 48 bits, not 64, and cannot be signed.
    private static final long MAX_TIME = 0x0000ffffffffffffL;
        
    private static byte[] getTime(Long time) {
        // If time is null, then we use currentTimeMillis
        time = time == null ? System.currentTimeMillis() : time;
        
        if (time > MAX_TIME) {
            throw new RuntimeException("time > MAX_TIME");
        }
        
        return Longs.toByteArray(time);
    }
    
    private static byte[] getRandom() {
        byte[] random = new byte[RANDOM_LENGTH];

        EncryptionUtils.getInstance().getRandom().nextBytes(random);

        return random;
    }
    
    private static String encodeTimeAndRandom(byte[] time, byte[] random) {
        char[] ulid = new char[ULID_LENGTH];

        // 0                   1                   2                   3
        //  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        // |0 0 0*0 0 0 0 0*0 0 0 0 0*0 0 0|0 0*0 0 0 0 0*0 0 0 0 0*0 0 0 0|
        // | time[2]       | time[3]       | time[4]       | time[5]       |
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        // |0*0 0 0 0 0*0 0 0 0 0*0 0 0 0 0|0 0 0 0 0*0 0 0 0 0*0 0 0 0 0*0|
        // | time[6]       | time[7]       | random[0]     | random[1]     |
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        // |0 0 0 0*0 0 0 0 0*0 0 0 0 0*0 0|0 0 0*0 0 0 0 0*0 0 0 0 0*0 0 0|
        // | random[2]     | random[3]     | random[4]     | random[5]     |
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        // |0 0*0 0 0 0 0*0 0 0 0 0*0 0 0 0|0*0 0 0 0 0*0 0 0 0 0*0 0 0 0 0|
        // | random[6]     | random[7]     | random[8]     | random[9]     |
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+   
        
        // Time Portion
        ulid[0] = base32[(byte)((time[2] & 0xff) >>> 5)];
        ulid[1] = base32[(byte)(time[2] & 0x1f)];
        ulid[2] = base32[(byte)((time[3] & 0xff) >>> 3)];
        ulid[3] = base32[(byte)(((time[3] << 2) | ((time[4] & 0xff) >>> 6)) & 0x1f)];
        ulid[4] = base32[(byte)(((time[4] & 0xff) >>> 1) & 0x1f)];
        ulid[5] = base32[(byte)(((time[4] << 4) | ((time[5] & 0xff) >>> 4)) & 0x1f)];
        ulid[6] = base32[(byte)(((time[5] << 1) | ((time[6] & 0xff) >>> 7)) & 0x1f)];
        ulid[7] = base32[(byte)(((time[6] & 0xff) >>> 2) & 0x1f)];
        ulid[8] = base32[(byte)(((time[6] << 3) | ((time[7] & 0xff) >>> 5)) & 0x1f)];
        ulid[9] = base32[(byte)(time[7] & 0x1f)];
        
        // Random Portion
        ulid[10] = base32[(byte)((random[0] & 0xff) >>> 3)];
        ulid[11] = base32[(byte)(((random[0] << 2) | ((random[1] & 0xff) >>> 6)) & 0x1f)];
        ulid[12] = base32[(byte)(((random[1] & 0xff) >>> 1) & 0x1f)];
        ulid[13] = base32[(byte)(((random[1] << 4) | ((random[2] & 0xff) >>> 4)) & 0x1f)];
        ulid[14] = base32[(byte)(((random[2] << 1) | ((random[3] & 0xff) >>> 7)) & 0x1f)];
        ulid[15] = base32[(byte)(((random[3] & 0xff) >>> 2) & 0x1f)];
        ulid[16] = base32[(byte)(((random[3] << 3) | ((random[4] & 0xff) >>> 5)) & 0x1f)];
        ulid[17] = base32[(byte)(random[4] & 0x1f)];
        
        ulid[18] = base32[(byte)((random[5] & 0xff) >>> 3)];
        ulid[19] = base32[(byte)(((random[5] << 2) | ((random[6] & 0xff) >>> 6)) & 0x1f)];
        ulid[20] = base32[(byte)(((random[6] & 0xff) >>> 1) & 0x1f)];
        ulid[21] = base32[(byte)(((random[6] << 4) | ((random[7] & 0xff) >>> 4)) & 0x1f)];
        ulid[22] = base32[(byte)(((random[7] << 1) | ((random[8] & 0xff) >>> 7)) & 0x1f)];
        ulid[23] = base32[(byte)(((random[8] & 0xff) >>> 2) & 0x1f)];
        ulid[24] = base32[(byte)(((random[8] << 3) | ((random[9] & 0xff) >>> 5)) & 0x1f)];
        ulid[25] = base32[(byte)(random[9] & 0x1f)];

        return new String(ulid);
    }
    
    private static String generateUlid() {
        return encodeTimeAndRandom(getTime(null), getRandom());
    }
        
    private static String generateUlid(EntityInstance entityInstance) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityTime entityTime = coreControl.getEntityTime(entityInstance);
        Long time = entityTime == null ? null : entityTime.getCreatedTime();
        
        return encodeTimeAndRandom(getTime(time), getRandom());
    }
        
    public static ULID randomULID() {
        return new ULID(generateUlid());
    }
    
    public static ULID randomULID(EntityInstance entityInstance) {
        return new ULID(generateUlid(entityInstance));
    }
    
    public static ULID fromString(String ulid) {
        Matcher matcher = ULID.matcher(ulid);
        
        if(!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        
        return new ULID(ulid);
    }
    
    private final String ulid;
    
    private ULID(String suppliedUlid) {
        ulid = suppliedUlid;
    }
    
    @Override
    public String toString() {
        return ulid;
    }
}
