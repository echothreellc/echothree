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

package com.echothree.view.client.web.filter;

// Source: http://h30499.www3.hp.com/t5/HP-Security-Research-Blog/Protect-your-Struts1-applications/ba-p/6463188#.U2sCOl4y32N
// alvaro_munoz | 05-06-2014 07:21 AM
// Hi Patrick,
// Thanks for your comment. The fix is not licensed in any way and its completely open source so that any one is free to use it or modify it.
// It is provided as-is with no guarantees nor support and designed as a temporal workaround until a patch is made available.
//
// Improved version: https://gist.github.com/anonymous/0045ef4df99b31b43daa

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParamFilter
        implements Filter {

    private Pattern pattern;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        pattern = Pattern.compile(filterConfig.getInitParameter("excludeParams"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new ParamFilteredRequest(request, pattern), response);
    }

    @Override
    public void destroy() {
    }

    private static class ParamFilteredRequest
            extends HttpServletRequestWrapper {

        private final Pattern pattern;

        public ParamFilteredRequest(ServletRequest request, Pattern pattern) {
            super((HttpServletRequest)request);
            this.pattern = pattern;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration<String> getParameterNames() {
            List<String> requestParameterNames = Collections.list(super.getParameterNames()); // unchecked
            List<String> finalParameterNames = new ArrayList<>(requestParameterNames.size());

            requestParameterNames.stream().filter((parameterName) -> !pattern.matcher(parameterName).matches()).forEach((parameterName) -> {
                finalParameterNames.add(parameterName);
            });

            return Collections.enumeration(finalParameterNames);
        }

    }

}
