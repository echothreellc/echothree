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

package com.echothree.ui.cli.form;

import com.echothree.ui.cli.form.util.FormUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;

public class Form {

    static Options getOptions() {
        var options = new Options();
        options.addOption(new Option("v", "verbose", false, "verbose debugging messages"));
        options.addOption(new Option("g", "generated", true, "target directory for generated code"));
        options.addOption(new Option("s", "source", true, "source directory for java classes"));

        return options;
    }

    static CommandLine getCommandLine(String[] args)
            throws ParseException {
        return new DefaultParser().parse(getOptions(), args);
    }

    public static void main(String[] args)
            throws Exception {
        var line = getCommandLine(args);
        var doVerbose = line.hasOption("v");
        var doGenerated = line.hasOption("g");
        var doSource = line.hasOption("s");

        if(doGenerated && doSource) {
            var generatedDirectory = line.getOptionValue("g");
            var sourceDirectory = line.getOptionValue("s");
            var formUtils = FormUtils.getInstance();

            formUtils.generateClasses(generatedDirectory + "/java", sourceDirectory);

            if(doVerbose) {
                // TODO: Print some statistics
            }
        }
    }

}
