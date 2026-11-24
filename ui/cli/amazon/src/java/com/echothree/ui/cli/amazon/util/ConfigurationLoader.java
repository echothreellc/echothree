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

package com.echothree.ui.cli.amazon.util;

import java.net.URL;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.convert.LegacyListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;

/** Loads CLI properties without the XML builder's bean introspection. */
public final class ConfigurationLoader {

    private ConfigurationLoader() {
    }

    public static Configuration load(String fileName)
            throws ConfigurationException {
        var propertiesConfiguration = newPropertiesConfiguration();
        new FileHandler(propertiesConfiguration).load(fileName);

        return combineConfiguration(propertiesConfiguration);
    }

    public static Configuration load(URL propertiesUrl)
            throws ConfigurationException {
        if(propertiesUrl == null) {
            throw new ConfigurationException("Properties resource was not found");
        }

        var propertiesConfiguration = newPropertiesConfiguration();
        new FileHandler(propertiesConfiguration).load(propertiesUrl);

        return combineConfiguration(propertiesConfiguration);
    }

    private static PropertiesConfiguration newPropertiesConfiguration() {
        var propertiesConfiguration = new PropertiesConfiguration();
        propertiesConfiguration.setListDelimiterHandler(new LegacyListDelimiterHandler(','));

        return propertiesConfiguration;
    }

    private static Configuration combineConfiguration(PropertiesConfiguration propertiesConfiguration) {
        var configuration = new CompositeConfiguration();
        configuration.addConfiguration(new SystemConfiguration());
        configuration.addConfiguration(propertiesConfiguration);

        return configuration;
    }
}
