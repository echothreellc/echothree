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

package com.echothree.control.user.test.user.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class PreferredDateTimeFormatTest
        extends GraphQlTestCase {

    @Test
    public void setUserVisitPreferredDateTimeFormat()
            throws Exception {
        var setUserVisitPreferredDateTimeFormat = executeUsingPost("""
                mutation {
                    setUserVisitPreferredDateTimeFormat(input: { dateTimeFormatName: "DEFAULT", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setUserVisitPreferredDateTimeFormat, "data.setUserVisitPreferredDateTimeFormat.commandResult.hasErrors")).isFalse();
    }

}
