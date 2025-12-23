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

package com.echothree.util.server.transfer;

import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.BaseTransfer;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.el.ExpressionFactory;

public abstract class BaseWrapperBuilder {
    
    protected <E> Class getContainedClass(Collection<E> collection) {
        Class listContains = null;

        if(collection.size() > 0) {
            Object firstObject = collection.iterator().next();

            if(firstObject instanceof BaseTransfer) {
                listContains = firstObject.getClass();
            }
        }
        
        return listContains;
    }
    
    protected <E> Set<E> getObjectsToRemove(TransferProperties transferProperties, Collection<E> collection) {
        Set<E> objectsToRemove = null;
        
        if(transferProperties != null) {
            var listContains = getContainedClass(collection);
            
            if(listContains != null) {
                var expression = transferProperties.getExpression(listContains);
                
                if(expression != null) {
                    ExpressionFactory expressionFactory = new ExpressionFactoryImpl();
                    var simpleName = StringUtils.getInstance().lowerCaseFirstCharacter(listContains.getSimpleName());
                    var variableName = simpleName.substring(0, simpleName.length() - 8);
                    
                    objectsToRemove = new HashSet<>();

                    for(var object : collection) {
                        var simpleContext = new SimpleContext();

                        simpleContext.setVariable(variableName, expressionFactory.createValueExpression(object, object.getClass()));

                        if(!(Boolean)expressionFactory.createValueExpression(simpleContext, expression, Boolean.class).getValue(simpleContext)) {
                            objectsToRemove.add(object);
                        }
                    }
                }
            }
        }
        
        return objectsToRemove;
    }
    
    protected <E> void filterCollection(TransferProperties transferProperties, Collection<E> collection) {
        var objectsToRemove = getObjectsToRemove(transferProperties, collection);
        
        if(objectsToRemove != null) {
            collection.removeAll(objectsToRemove);
        }
    }
    
}
