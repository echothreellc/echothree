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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.log4j.Logger;

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

}
