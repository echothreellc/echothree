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

import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;

public class UpcAChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    private UpcAChecksumLogic() {
        super();
    }
    
    private static class UpcAChecksumLogicHolder {
        static UpcAChecksumLogic instance = new UpcAChecksumLogic();
    }
    
    public static UpcAChecksumLogic getInstance() {
        return UpcAChecksumLogicHolder.instance;
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 12) {
            var hasCharacterError = false;
            var totalA = 0;
            var totalB = 0;

            for(var i = 0; i < 11; i++) {
                var digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                } else {
                    if(i % 2 == 0) {
                        totalA += digit;
                    } else {
                        totalB += digit;
                    }
                }
            }

            if(!hasCharacterError) {
                var checkDigit = getDigit(alias, 11);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    var intermediate = (10 - (totalA * 3 + totalB) % 10) % 10;

                    if(intermediate != checkDigit) {
                        eea.addExecutionError(ExecutionErrors.IncorrectUpcAChecksum.name());
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectUpcACharacter.name());
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectUpcALength.name());
        }
    }

}
