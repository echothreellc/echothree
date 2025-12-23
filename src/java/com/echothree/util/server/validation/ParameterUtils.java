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

package com.echothree.util.server.validation;

import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import static java.lang.Math.toIntExact;
import java.util.Arrays;
import java.util.Objects;

public class ParameterUtils
        extends BaseLogic {

    private ParameterUtils() {
        super();
    }

    private static class ParameterUtilsHolder {
        static ParameterUtils instance = new ParameterUtils();
    }

    public static ParameterUtils getInstance() {
        return ParameterUtils.ParameterUtilsHolder.instance;
    }

    public int countNonNullParameters(Object... objects) {
        return toIntExact(Arrays.stream(objects)
                .filter(Objects::nonNull)
                .count());
    }

    public int countNullParameters(Object... objects) {
        return toIntExact(Arrays.stream(objects)
                .filter(Objects::isNull)
                .count());
    }

    public boolean isExactlyOneBooleanTrue(Boolean... booleans) {
        var areAnyTrue = false;
        var areTwoTrue = false;

        for(var i = 0; !areTwoTrue && (i < booleans.length); i++) {
            areTwoTrue = (areAnyTrue && booleans[i]);
            areAnyTrue |= booleans[i];
        }

        return areAnyTrue && !areTwoTrue;
    }

    public boolean isExactlyOneBooleanTrue(final ExecutionErrorAccumulator eea, Boolean... booleans) {
        var result = isExactlyOneBooleanTrue(booleans);
        
        if(!result) {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }

    public boolean isExactlyOneParameterPresent(String... parameters) {
        var nullTestedParameters = Arrays.stream(parameters)
                .map(Objects::nonNull)
                .toArray(Boolean[]::new);

        return isExactlyOneBooleanTrue(nullTestedParameters);
    }

    public boolean isExactlyOneParameterPresent(final ExecutionErrorAccumulator eea, String... parameters) {
        var result = isExactlyOneParameterPresent(parameters);

        if(!result) {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }

}
