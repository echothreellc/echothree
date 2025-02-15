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

package com.echothree.model.control.sequence.server.logic.checksum;

import org.apache.commons.lang.StringUtils;

public class NoneSequenceChecksum
        implements SequenceChecksum {

    private NoneSequenceChecksum() {
        super();
    }

    private static class SequenceChecksumHolder {
        static SequenceChecksum instance = new NoneSequenceChecksum();
    }

    public static SequenceChecksum getInstance() {
        return SequenceChecksumHolder.instance;
    }

    @Override
    public String calculate(String value) {
        return StringUtils.EMPTY;
    }

    @Override
    public String regexp() {
        return StringUtils.EMPTY;
    }

    @Override
    public boolean verify(String value) {
        return true;
    }

}
