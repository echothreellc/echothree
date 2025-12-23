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
public class Gtin8ChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    protected Gtin8ChecksumLogic() {
        super();
    }

    public static Gtin8ChecksumLogic getInstance() {
        return CDI.current().select(Gtin8ChecksumLogic.class).get();
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 8) {
            var hasCharacterError = false;
            var checksum = 0;

            for(int i = 0; i < 7; i++) {
                int d = getDigit(alias, i);

                if(d == -1) {
                    hasCharacterError = true;
                    break;
                }

                // https://www.gs1.org/services/how-calculate-check-digit-manually
                checksum += i % 2 == 0 ? (3 * d) : d;
            }

            if(!hasCharacterError) {
                var checkDigit = getDigit(alias, 7);

                hasCharacterError = checkDigit == -1;

                if(!hasCharacterError) {
                    var intermediate = (10 - (checksum % 10)) % 10;

                    if(intermediate != checkDigit) {
                        eea.addExecutionError(ExecutionErrors.IncorrectGtin8Checksum.name(), alias);
                    }
                }
            }

            if(hasCharacterError) {
                eea.addExecutionError(ExecutionErrors.IncorrectGtin8Character.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectGtin8Length.name(), alias);
        }
    }

}
