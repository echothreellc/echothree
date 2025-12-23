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

package com.echothree.view.client.web.json;

import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.BaseWrapper;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.el.ExpressionFactory;
import net.sf.json.util.PropertyFilter;

public class ResultPropertyFilter
        implements PropertyFilter {

    private static ExpressionFactory expressionFactory = new ExpressionFactoryImpl();
    private Map<Class, Map<String, String>> classAndPropertyFilters = new HashMap<>();
    private Map<Class, String> collectionFilters = new HashMap<>();

    public void addClassAndProperty(Class clazz, String property, String expression) {
        var properties = classAndPropertyFilters.get(clazz);
        
        if(properties == null) {
            classAndPropertyFilters.put(clazz, properties);
        }
        
        if(property != null) {
            if(properties == null) {
                properties = new HashMap<>();

                classAndPropertyFilters.put(clazz, properties);
            }
            
            properties.put(property, expression);
        }
    }
    
    public void addListFilter(Class clazz, String expression) {
        collectionFilters.put(clazz, expression);
    }
    
    @Override
    public boolean apply(Object source, String name, Object value) {
        var filteredOut = true;
        var clazz = source.getClass();
        
        if(Proxy.isProxyClass(clazz)) {
            if(source instanceof BaseResult) {
                clazz = BaseResult.class;
            }
        }
        
        if(classAndPropertyFilters.containsKey(clazz)) {
            var properties = classAndPropertyFilters.get(clazz);
            
            if(properties == null) {
                filteredOut = false;
            } else if(properties.containsKey(name)) {
                var expression = properties.get(name);

                if(expression == null) {
                    filteredOut = false;
                } else {
                    var simpleContext = new SimpleContext();

                    simpleContext.setVariable(name, expressionFactory.createValueExpression(value, value.getClass()));
                    var e = expressionFactory.createValueExpression(simpleContext, expression, Boolean.class);

                    filteredOut = !(Boolean)e.getValue(simpleContext);
                }
            }
        }
        
        if(!filteredOut && value instanceof BaseWrapper) {
            @SuppressWarnings("unchecked")
            var collection = ((BaseWrapper<Object>)value).getCollection();
            
            if(collection.size() > 0) {
                var firstObject = collection.iterator().next();
                
                if(firstObject instanceof BaseTransfer) {
                    var listContains = firstObject.getClass();
                    var expression = collectionFilters.get(listContains);
                    
                    if(expression != null) {
                        var simpleName = StringUtils.getInstance().lowerCaseFirstCharacter(listContains.getSimpleName());
                        var variableName = simpleName.substring(0, simpleName.length() - 8);
                        Set<Object> objectsToRemove = new HashSet<>();

                        collection.forEach((object) -> {
                            var simpleContext = new SimpleContext();
                            simpleContext.setVariable(variableName, expressionFactory.createValueExpression(object, object.getClass()));
                            if (!(Boolean)expressionFactory.createValueExpression(simpleContext, expression, Boolean.class).getValue(simpleContext)) {
                                objectsToRemove.add(object);
                            }
                        });
                        
                        collection.removeAll(objectsToRemove);
                    }
                }
            }
        }
        
        return filteredOut;
    }

}
