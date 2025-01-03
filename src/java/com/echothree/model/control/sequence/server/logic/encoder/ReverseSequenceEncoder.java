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

package com.echothree.model.control.sequence.server.logic.encoder;

public class ReverseSequenceEncoder
        implements SequenceEncoder {

    private ReverseSequenceEncoder() {
        super();
    }

    private static class SequenceEncoderHolder {
        static SequenceEncoder instance = new ReverseSequenceEncoder();
    }

    public static SequenceEncoder getInstance() {
        return SequenceEncoderHolder.instance;
    }

    @Override
    public String encode(String value) {
        var codePoints = value.codePoints().toArray();
        var result = new StringBuilder();

        for(var i = codePoints.length - 1; i > -1; i--) {
            result.appendCodePoint(codePoints[i]);
        }

        return result.toString();
    }

}
