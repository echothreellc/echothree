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

public class Isbn10ChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    private Isbn10ChecksumLogic() {
        super();
    }
    
    private static class Isbn10ChecksumLogicHolder {
        static Isbn10ChecksumLogic instance = new Isbn10ChecksumLogic();
    }
    
    public static Isbn10ChecksumLogic getInstance() {
        return Isbn10ChecksumLogicHolder.instance;
    }

    private int getIsbn10CheckDigit(String alias, int offset) {
        var result = -1;
        var digit = alias.charAt(offset);

        if(digit >= '0' && digit <= '9') {
            result = digit - '0';
        } else if(digit == 'X') {
            result = 10;
        }

        return result;
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 10) {
            var hasCharacterError = false;
            var runningTotal = 0;
            var checksum = 0;

            for(var i = 0; i < 9; i++) {
                var digit = getDigit(alias, i);

                if(digit == -1) {
                    hasCharacterError = true;
                    break;
                } else {
                    runningTotal += digit;
                    checksum += runningTotal;
                }
            }

            if(!hasCharacterError) {
                var checkDigit = getIsbn10CheckDigit(alias, 9);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    runningTotal += checkDigit;
                    checksum += runningTotal;

                    if(checksum % 11 != 0) {
                        eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Checksum.name(), alias);
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Character.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectIsbn10Length.name(), alias);
        }
    }

}
