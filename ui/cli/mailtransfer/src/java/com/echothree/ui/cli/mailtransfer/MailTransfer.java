package com.echothree.ui.cli.mailtransfer;// --------------------------------------------------------------------------------
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

import com.echothree.ui.cli.mailtransfer.util.MailTransferUtility;
import com.echothree.ui.cli.mailtransfer.util.blogentry.BlogEntryTransfer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailTransfer {

    private static Logger logger = LoggerFactory.getLogger(MailTransfer.class);
    private static Configuration configuration = loadConfiguration();

    private static CommandLine getCommandLine(String args[])
            throws ParseException {
        return new DefaultParser().parse(new Options()
                .addOption("v", "verbose", false, "verbose command results")
                .addOption("c", "communicationevents", false, "import communication events")
                .addOption("b", "blogentries", false, "import blog entries"), args);
    }

    private static Configuration loadConfiguration() {
        Configuration result = null;

        try {
            result = new DefaultConfigurationBuilder("config.xml").getConfiguration();
        } catch(ConfigurationException ce) {
            logger.error("An Exception occurred:", ce);
        }

        return result;
    }

    public static void main(String args[])
            throws Exception {
        var line = getCommandLine(args);
        var doVerbose = line.hasOption("v");
        var doCommunicationsEvents = line.hasOption("c");
        var doBlogEntries = line.hasOption("b");

        if(doCommunicationsEvents) {
            var mailTransferUtility = new MailTransferUtility();

            mailTransferUtility.setDoVerbose(doVerbose);
            mailTransferUtility.transfer();
        }

        if(doBlogEntries) {
            new BlogEntryTransfer(configuration, doVerbose).transfer();
        }
    }

}
