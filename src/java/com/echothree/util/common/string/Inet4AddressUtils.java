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

package com.echothree.util.common.string;

public class Inet4AddressUtils {
    
    private Inet4AddressUtils() {
        super();
    }
    
    private static class Inet4AddressUtilsHolder {
        static Inet4AddressUtils instance = new Inet4AddressUtils();
    }
    
    public static Inet4AddressUtils getInstance() {
        return Inet4AddressUtilsHolder.instance;
    }
    
    // http://darksleep.com/player/JavaAndUnsignedTypes.html
    public String formatInet4Address(Integer inet4Address) {
        var rawInet4Address = inet4Address & 0xFFFFFFFFL;
        var inet4Address0 = (int)(rawInet4Address & 0xFF);
        var inet4Address1 = (int)((rawInet4Address & 0xFF00) >> 8);
        var inet4Address2 = (int)((rawInet4Address & 0xFF0000) >> 16);
        var inet4Address3 = (int)(rawInet4Address >> 24);
        var sb = String.valueOf(inet4Address3) + '.' + inet4Address2 + '.' +
                inet4Address1 + '.' + inet4Address0;

        return sb;
    }

}
