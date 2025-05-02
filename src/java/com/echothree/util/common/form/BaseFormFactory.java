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

package com.echothree.util.common.form;

import com.echothree.util.common.command.ProxyInvocationHandler;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;

public class BaseFormFactory {
    
    static public <F extends BaseForm> F createForm(Class<F> form) {
        var pih = new ProxyInvocationHandler();
        var baseForm = (F)Proxy.newProxyInstance(form.getClassLoader(), new Class[]{form, Serializable.class}, pih);
        var className = form.getName();
        var nameOffset = className.lastIndexOf('.');
        var formName = new String(className.getBytes(StandardCharsets.UTF_8), nameOffset + 1, className.length() - nameOffset - 1, StandardCharsets.UTF_8);

        baseForm.setFormName(formName);
        
        return baseForm;
    }
    
}
