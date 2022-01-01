// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

/*
Copyright 2005-2006 Seth Fitzsimmons <seth@note.amherst.edu>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.echothree.view.client.web.struts.sprout.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.WebApplicationContext;

/**
 * Sprout utility methods.
 * 
 * @author Seth Fitzsimmons
 */
public class SproutUtils {
    private static final Logger log = Logger.getLogger( SproutUtils.class );
    
    /**
     * Gets a collection of methods declared in a specified range of a given
     * class' hierarchy.
     * 
     * @param clazz Class to inspect.
     * @param upto Methods declared in this class and its subclasses will be
     * included.  Any methods declared in superclasses will be ignored.
     * @return Collection of methods declared within the specified range.
     */
    public static Collection<Method> getDeclaredMethods(Class clazz, final Class upto) {
        // collect methods to register (include methods for all classes up to and including this one)
        final Collection<Method> methods = new ArrayList<>();
        while ( !clazz.equals( upto.getSuperclass() ) ) {
            methods.addAll( Arrays.asList( clazz.getDeclaredMethods() ) );
            clazz = clazz.getSuperclass();
        }
        
        return methods;
    }
    
    /**
     * Bean initialization method.  Uses reflection to determine properties
     * for which auto-wiring may be appropriate.  Subsequently attempts to
     * retrieve appropriate beans from the WebApplicationContext and set them
     * locally.
     * 
     * @param bean Bean to initialize.
     * @param context WebApplicationContext containing Spring beans.
     * @param clazz Type of Sprout.  This is used to determine which declared
     * methods are candidates for auto-wiring.
     */
    public static void initialize(final Object bean, final WebApplicationContext context, final Class clazz) {
        final Collection<Method> methods = SproutUtils.getDeclaredMethods( bean.getClass(), clazz );
        
        final PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors( bean.getClass() );
        for ( final PropertyDescriptor descriptor : descriptors ) {
            final Class type = descriptor.getPropertyType();
            
            // beans should never be of type String
            // there must be a write method present
            // the write method must exist within the relevant subset of declared methods
            if ( !type.equals( String.class ) && null != descriptor.getWriteMethod() && methods.contains( descriptor.getWriteMethod() ) ) {
                final Object serviceBean = context.getBean( descriptor.getName() );
                if ( null != serviceBean ) {
                    try {
                        log.debug("Wiring property '" + descriptor.getName() + "' with bean of type " + serviceBean.getClass().getName() );
                        PropertyUtils.setProperty( bean, descriptor.getName(), serviceBean );
                    }
                    catch (final IllegalAccessException e) {
                        throw new RuntimeException( e );
                    }
                    catch (final InvocationTargetException e) {
                        throw new RuntimeException( e );
                    }
                    catch (final NoSuchMethodException e) {
                        throw new RuntimeException( e );
                    }
                }
            }
        }

        /**
         * TODO additional lifecycle interface callbacks as defined in BeanFactory
         * should be implemented here
         * @see org.springframework.beans.factory.BeanFactory
         */
        
        // InitializingBean callback
        if ( bean instanceof InitializingBean ) {
            try {
                ((InitializingBean) bean).afterPropertiesSet();
            }
            catch (final Exception e) {
                log.warn("Exception while running afterPropertiesSet() on an InitializingBean: " + e.getMessage(), e );
            }
        }
    }
}
