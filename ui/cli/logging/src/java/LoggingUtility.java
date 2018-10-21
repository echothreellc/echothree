// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.ui.cli.logging.Export;
import com.echothree.ui.cli.logging.Import;
import com.echothree.ui.cli.logging.Resolve;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;

public class LoggingUtility {
    static Options getOptions() {
        Option optExport = new Option("e", "export", false, "export logs");
        Option optImport = new Option("i", "import", false, "import log");
        Option optResolve = new Option("r", "resolve", false, "resolve DNS for imported data");
        
        Options options = new Options();
        options.addOption(optExport);
        options.addOption(optImport);
        options.addOption(optResolve);
        
        return options;
    }
    
    static CommandLine getCommandLine(String args[])
    throws ParseException {
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);
        return line;
    }
    
    public static void main(String args[])
            throws Exception {
        CommandLine line = getCommandLine(args);
        
        Class.forName("com.mysql.jdbc.Driver");
        
        if(line.hasOption("e")) {
            new Export().execute();
        } else if(line.hasOption("i")) {
            new Import().execute();
        } else if(line.hasOption("r")) {
            new Resolve().execute();
        }
    }
    
}
