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

package com.echothree.util.common.string;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlUtils {
    
    private UrlUtils() {
        super();
    }
    
    private static class UrlUtilsHolder {
        static UrlUtils instance = new UrlUtils();
    }
    
    public static UrlUtils getInstance() {
        return UrlUtilsHolder.instance;
    }
    
    public StringBuilder buildBaseUrl(HttpServletRequest request) {
        var scheme = request.getScheme();
        var serverName = request.getServerName();
        var serverPort = request.getServerPort();
        var contextPath = request.getContextPath();
        var servletPath = request.getServletPath();
        var includeServerPort = (scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443);
        
        return new StringBuilder(scheme).append("://").append(serverName).append(includeServerPort? ":" + serverPort: "").append(contextPath).append(servletPath);
    }

    public StringBuilder buildParams(StringBuilder url, Map<String, String> params) {
        if(params != null && params.size() > 0) {
            var entrySet = params.entrySet();
            var isFirst = true;

            for(var entry : entrySet) {
                url.append(isFirst ? '?' : '&');
                try {
                    url.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch(UnsupportedEncodingException uee) {
                    // If UTF-8 isn't a supported encoding, then there are severe problems.
                    throw new RuntimeException(uee);
                }
                isFirst = false;
            }
        }

        return url;
    }

    public String buildUrl(HttpServletRequest request, HttpServletResponse response, String path, Map<String, String> params) {
        return response.encodeURL(buildParams(buildBaseUrl(request).append(path), params).toString());
    }
    
}
