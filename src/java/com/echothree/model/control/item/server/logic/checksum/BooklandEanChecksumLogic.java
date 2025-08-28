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

public class BooklandEanChecksumLogic
        extends BaseChecksumLogic
        implements ItemAliasChecksumInterface {

    private BooklandEanChecksumLogic() {
        super();
    }
    
    private static class ItemAliasChecksumInterfaceHolder {
        static BooklandEanChecksumLogic instance = new BooklandEanChecksumLogic();
    }
    
    public static BooklandEanChecksumLogic getInstance() {
        return ItemAliasChecksumInterfaceHolder.instance;
    }

    @Override
    public void checkChecksum(final ExecutionErrorAccumulator eea, final String alias) {
        if(alias.length() == 13) {
            if(alias.matches("\\d{13}")) {
                switch(Integer.parseInt(alias.substring(0, 3))) {
                    case 978, 979 -> Ean13ChecksumLogic.getInstance().checkChecksum(eea, alias);
                    default -> eea.addExecutionError(ExecutionErrors.IncorrectBooklandEanPrefix.name(), alias);
                }
            } else {
                eea.addExecutionError(ExecutionErrors.IncorrectBooklandEanCharacter.name(), alias);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.IncorrectBooklandEanLength.name(), alias);
        }
    }

}
