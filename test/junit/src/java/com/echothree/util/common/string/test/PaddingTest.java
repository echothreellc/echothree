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

package com.echothree.util.common.string.test;

import com.echothree.util.common.string.StringUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class PaddingTest {

    @Test
    public void PadLeftZeros() {
        assertThat(StringUtils.getInstance().padLeftZeros("123", 5)).isEqualTo("00123");
    }

    @Test
    public void PadLeftZerosWithNullInput() {
        assertThat(StringUtils.getInstance().padLeftZeros(null, 3)).isEqualTo("000");
    }

    @Test
    public void PadLeftZerosRejectsTooLongInput() {
        assertThatThrownBy(() -> StringUtils.getInstance().padLeftZeros("1234", 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void PadLeftSpaces() {
        assertThat(StringUtils.getInstance().padLeftSpaces("abc", 5)).isEqualTo("  abc");
    }

    @Test
    public void PadLeftSpacesWithNullInput() {
        assertThat(StringUtils.getInstance().padLeftSpaces(null, 3)).isEqualTo("   ");
    }

    @Test
    public void PadLeftSpacesRejectsTooLongInput() {
        assertThatThrownBy(() -> StringUtils.getInstance().padLeftSpaces("abcd", 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void PadRightSpaces() {
        assertThat(StringUtils.getInstance().padRightSpaces("abc", 5)).isEqualTo("abc  ");
    }

    @Test
    public void PadRightSpacesWithNullInput() {
        assertThat(StringUtils.getInstance().padRightSpaces(null, 3)).isEqualTo("   ");
    }

    @Test
    public void PadRightSpacesRejectsTooLongInput() {
        assertThatThrownBy(() -> StringUtils.getInstance().padRightSpaces("abcd", 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
