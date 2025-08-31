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

package com.echothree.model.control.item.server.logic.checksum;

import com.echothree.util.server.control.BaseLogic;

public class BaseChecksumLogic
        extends BaseLogic {

    protected BaseChecksumLogic() {
        super();
    }

    protected int getDigit(String alias, int offset) {
        var result = -1;
        var digit = alias.charAt(offset);

        if(digit >= '0' && digit <= '9') {
            result = digit - '0';
        }

        return result;
    }

}
