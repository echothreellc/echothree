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

package com.echothree.model.control.item.server.logic.checksum;

import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class Ean13ChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    protected Ean13ChecksumLogic() {
        super();
    }

    public static Ean13ChecksumLogic getInstance() {
        return CDI.current().select(Ean13ChecksumLogic.class).get();
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 13) {
            var hasCharacterError = false;
            int sum = 0;

            for(int i = 0; i < 12; i++) {
                int d = getDigit(alias, i);

                if(d == -1) {
                    hasCharacterError = true;
                    break;
                }

                // Positions are 1-based in the spec: odd positions weight 1, even positions weight 3.
                // i is 0-based, so i % 2 == 0 means even position.
                sum += i % 2 == 0 ? d : (3 * d);
            }

            if(!hasCharacterError) {
                int checkDigit = getDigit(alias, 12);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    int computed = (10 - (sum % 10)) % 10;

                    if(computed != checkDigit) {
                        eea.addExecutionError(ExecutionErrors.IncorrectEan13Checksum.name(), alias);
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectEan13Character.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectEan13Length.name(), alias);
        }
    }


}
