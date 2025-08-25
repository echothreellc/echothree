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

public class UpcEChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    private UpcEChecksumLogic() {
        super();
    }
    
    private static class UpcEChecksumLogicHolder {
        static UpcEChecksumLogic instance = new UpcEChecksumLogic();
    }
    
    public static UpcEChecksumLogic getInstance() {
        return UpcEChecksumLogicHolder.instance;
    }

    private static String expandUpcEToUpcA(String upcE) {
        char numberSystem = upcE.charAt(0);
        char[] mfr = upcE.substring(1, 6).toCharArray();
        char lastDigit = upcE.charAt(6);
        char checksum = upcE.charAt(7);

        return switch(lastDigit) {
            case '0', '1', '2' ->
                    "" + numberSystem + mfr[0] + mfr[1] + lastDigit + "0000" + mfr[2] + mfr[3] + mfr[4] + checksum;
            case '3' ->
                    "" + numberSystem + mfr[0] + mfr[1] + mfr[2] + "00000" + mfr[3] + mfr[4] + checksum;
            case '4' ->
                    "" + numberSystem + mfr[0] + mfr[1] + mfr[2] + mfr[3] + "00000" + mfr[4] + checksum;
            default -> // '5' to '9'
                    "" + numberSystem + mfr[0] + mfr[1] + mfr[2] + mfr[3] + mfr[4] + "0000" + lastDigit + checksum;
        };
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 8) {
            if(alias.matches("\\d{8}")) {
                switch(getDigit(alias, 0)) {
                    case 0, 1 -> UpcAChecksumLogic.getInstance().checkChecksum(eea, expandUpcEToUpcA(alias));
                    default -> eea.addExecutionError(ExecutionErrors.IncorrectUpcENumberSystem.name(), alias);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.IncorrectUpcECharacter.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectUpcELength.name(), alias);
        }
    }

}
