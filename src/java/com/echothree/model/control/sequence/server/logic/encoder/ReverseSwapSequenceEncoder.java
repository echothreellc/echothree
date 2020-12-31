// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.sequence.server.logic.encoder;

public class ReverseSwapSequenceEncoder
        implements SequenceEncoder {

    private ReverseSwapSequenceEncoder() {
        super();
    }

    private static class SequenceEncoderHolder {
        static SequenceEncoder instance = new ReverseSwapSequenceEncoder();
    }

    public static SequenceEncoder getInstance() {
        return SequenceEncoderHolder.instance;
    }

    @Override
    public String encode(String value) {
        var codePoints = value.codePoints().toArray();
        var result = new StringBuilder();
        var length = codePoints.length;
        var ending = length % 2;
        var hasExtra = ending == 1;

        for(int i = length - 1; i > (hasExtra ? 0 : -1); i -= 2) {
            result.appendCodePoint(codePoints[i - 1]);
            result.appendCodePoint(codePoints[i]);
        }

        if(hasExtra) {
            result.appendCodePoint(codePoints[0]);
        }

        return result.toString();
    }

}
