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

package com.echothree.control.user.graphql.server.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.ExecutionInput;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GraphQlDocumentCache
        implements PreparsedDocumentProvider {

    private final Log log = LogFactory.getLog(this.getClass());
    private final Cache<String, PreparsedDocumentEntry> cache = Caffeine.newBuilder()
            .maximumSize(1_000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .recordStats()
            .build();

    private GraphQlDocumentCache() {
        super();
    }

    private static class StringUtilsHolder {
        static GraphQlDocumentCache instance = new GraphQlDocumentCache();
    }

    public static PreparsedDocumentProvider getInstance() {
        return GraphQlDocumentCache.StringUtilsHolder.instance;
    }

    @Override
    public PreparsedDocumentEntry getDocument(final ExecutionInput executionInput,
            final Function<ExecutionInput, PreparsedDocumentEntry> parseAndValidateFunction) {
        return null;
    }

    @Override
    public CompletableFuture<PreparsedDocumentEntry> getDocumentAsync(ExecutionInput executionInput,
            Function<ExecutionInput, PreparsedDocumentEntry> computeFunction) {
        Function<String, PreparsedDocumentEntry> mapCompute = key -> computeFunction.apply(executionInput);
        var completableFuture = CompletableFuture.completedFuture(cache.get(executionInput.getQuery(), mapCompute));

        var cacheStats = cache.stats();
        if(cacheStats.requestCount() % 1000 == 0) {
            log.info(cacheStats.toString());
        }

        return completableFuture;
    }


}
