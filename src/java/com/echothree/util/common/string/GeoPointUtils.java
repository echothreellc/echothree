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

import com.google.common.math.IntMath;
import java.util.Formatter;

public class GeoPointUtils {

    private GeoPointUtils() {
        super();
    }

    private static class GeoPointUtilsHolder {
        static GeoPointUtils instance = new GeoPointUtils();
    }

    public static GeoPointUtils getInstance() {
        return GeoPointUtilsHolder.instance;
    }

    public String formatDegrees(Integer degrees) {
        int rawDegrees = degrees;
        var i = IntMath.pow(10, 6);
        var builtResult = new StringBuilder().append(rawDegrees / i).append(".");

        return new Formatter(builtResult).format("%0" + 6 + "d", Math.abs(rawDegrees) % i).toString();
    }
    
}
