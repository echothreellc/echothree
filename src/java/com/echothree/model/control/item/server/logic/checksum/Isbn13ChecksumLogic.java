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

public class Isbn13ChecksumLogic
        extends BaseIsbnChecksumLogic
        implements ItemAliasChecksumInterface {

    private Isbn13ChecksumLogic() {
        super();
    }
    
    private static class Isbn13ChecksumLogicHolder {
        static Isbn13ChecksumLogic instance = new Isbn13ChecksumLogic();
    }
    
    public static Isbn13ChecksumLogic getInstance() {
        return Isbn13ChecksumLogicHolder.instance;
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 13) {
            var hasCharacterError = false;
            var checksum = 0;
            for(var i = 0; i < 12; i += 2) {
                var digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                }

                checksum += digit;
            }

            if(!hasCharacterError) {
                for(var i = 1; i < 12; i += 2) {
                    var digit = getDigit(alias, i);

                    if(digit == -1) {
                        hasCharacterError = true;
                        break;
                    }

                    checksum += getDigit(alias, i) * 3;
                }
            }

            if(!hasCharacterError) {
                var checkDigit = getIsbnCheckDigit(alias, 12);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    checksum += checkDigit;

                    if(!(checksum % 10 == 0)) {
                        eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Checksum.name());
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Character.name());
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Length.name());
        }
    }


}
