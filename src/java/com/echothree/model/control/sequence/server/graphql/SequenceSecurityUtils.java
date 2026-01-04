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

package com.echothree.model.control.sequence.server.graphql;

import com.echothree.control.user.sequence.server.command.GetSequenceChecksumTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceEncoderTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceTypeCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface SequenceSecurityUtils {

    static boolean getHasSequenceTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSequenceTypeCommand.class);
    }

    static boolean getHasSequenceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSequenceCommand.class);
    }

    static boolean getHasSequenceChecksumTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSequenceChecksumTypeCommand.class);
    }

    static boolean getHasSequenceEncoderTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSequenceEncoderTypeCommand.class);
    }

}
