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

package com.echothree.ui.web.letter.framework;

import java.util.Locale;

/**
 * Used by the web container to store information that is needed and used
 * by all users.
 */
public class ApplicationContainer {
    
    /**
     * Retrieve the locale for the system.
     */
    public Locale getSystemLocale() {
        return systemLocale;
    }
    
    /**
     * Set the locale for the system.
     */
    public void setSystemLocale(Locale aLocale) {
        systemLocale = aLocale;
    }
    
    /**
     * Constructor
     */
    /** Creates a new instance of ApplicationContainer */
    public ApplicationContainer() {
        super();
    }
    
    /**
     * The default locale for the application.
     */
    private Locale systemLocale = null;
}
