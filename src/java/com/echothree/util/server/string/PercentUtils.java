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

package com.echothree.util.server.string;

import java.util.Formatter;

public class PercentUtils {
    
    private PercentUtils() {
        super();
    }
    
    private static class PercentUtilsHolder {
        static PercentUtils instance = new PercentUtils();
    }
    
    public static PercentUtils getInstance() {
        return PercentUtilsHolder.instance;
    }
    
    public String formatFractionalPercent(Integer percent) {
        String result = null;
        
        if(percent != null) {
            int rawPercent = percent;
            var sbPercent = new StringBuilder().append(rawPercent / 1000).append('.');
            
            new Formatter(sbPercent).format("%03d%%", rawPercent % 1000);
            
            result = sbPercent.toString();
        }
        
        return result;
    }
    
}
