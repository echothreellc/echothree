// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.view.client.web.displaytag;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.TableDecorator;

public class BaseTableDecorator
        extends TableDecorator {
    
    public String computeURL(String path, Map parameters) {
        PageContext pageContext = getPageContext();
        StringBuilder url = new StringBuilder(path);
        String urlResult;
        
        if(parameters != null && parameters.size() > 0) {
            Set keySet = parameters.keySet();
            boolean separator = false;
            
            if(path.indexOf('?') == -1) {
                url.append('?');
            } else {
                separator = true;
            }
            
            for(Iterator keys = keySet.iterator(); keys.hasNext();) {
                String key = (String)keys.next();
                String value = (String)parameters.get(key);
                
                if(separator) {
                    url.append('&');
                } else {
                    separator = true;
                }
                
                url.append(key);
                url.append('=');
                if(value != null)
                    url.append(value);
            }
        }
        
        if(pageContext.getSession() != null) {
            HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
            
            urlResult = response.encodeURL(url.toString());
        } else {
            urlResult = url.toString();
        }
        
        return urlResult;
    }
    
}
