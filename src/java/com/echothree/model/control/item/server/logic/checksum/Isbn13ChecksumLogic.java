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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class Isbn13ChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    protected Isbn13ChecksumLogic() {
        super();
    }

    public static Isbn13ChecksumLogic getInstance() {
        return CDI.current().select(Isbn13ChecksumLogic.class).get();
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 13) {
            var hasCharacterError = false;
            var checksum = 0;

            for(int i = 0; i < 12; i++) {
                int d = getDigit(alias, i);

                if(d == -1) {
                    hasCharacterError = true;
                    break;
                }

                // Positions are 1-based in the spec: odd positions weight 1, even positions weight 3.
                // i is 0-based, so i % 2 == 0 means even position.
                checksum += i % 2 == 0 ? d : (3 * d);
            }

            if(!hasCharacterError) {
                var checkDigit = getDigit(alias, 12);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    checksum += checkDigit;

                    if(!(checksum % 10 == 0)) {
                        eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Checksum.name(), alias);
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Character.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectIsbn13Length.name(), alias);
        }
    }


}
