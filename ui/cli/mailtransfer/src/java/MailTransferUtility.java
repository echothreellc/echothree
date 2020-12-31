// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.ui.cli.mailtransfer.MailTransfer;
import com.echothree.ui.cli.mailtransfer.blogentry.BlogEntryTransfer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailTransferUtility {

    private static Log log = LogFactory.getLog(MailTransferUtility.class);
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
        } catch(ConfigurationException ex) {
            log.error(ex);
        }

        return result;
    }

    public static void main(String args[])
            throws Exception {
        CommandLine line = getCommandLine(args);
        boolean doVerbose = line.hasOption("v");
        boolean doCommunicationsEvents = line.hasOption("c");
        boolean doBlogEntries = line.hasOption("b");

        if(doCommunicationsEvents) {
            MailTransfer mailTransfer = new MailTransfer();

            mailTransfer.setDoVerbose(doVerbose);
            mailTransfer.transfer();
        }

        if(doBlogEntries) {
            new BlogEntryTransfer(configuration, doVerbose).transfer();
        }
    }

}
