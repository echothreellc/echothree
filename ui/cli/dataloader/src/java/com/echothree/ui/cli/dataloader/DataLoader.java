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

package com.echothree.ui.cli.dataloader;

import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.hts.HtsParser;
import com.echothree.ui.cli.dataloader.util.zipcode.ZipCodeParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataLoader {
    
    private static Logger logger = LoggerFactory.getLogger(DataLoader.class);
    
    static CommandLine getCommandLine(String args[])
            throws ParseException {
        return new DefaultParser().parse(new Options()
                .addOption("i", "initial", false, "load initial data into database")
                .addOption("f", "file", true, "load data into database from specified file")
                .addOption("z", "zipcode", false, "load zip code data from specified file")
                .addOption("h", "hts", false, "load harmonized tariff schedule codes from specified directory")
                .addOption("e", "errors", false, "show command result when an error is returned")
                .addOption("v", "verbose", false, "verbose command results"), args);
    }
    
    public static void main(String args[])
            throws Exception {
        var line = getCommandLine(args);

        var doInitial = line.hasOption("i");
        var doFile = line.hasOption("f");
        var doZipCode = line.hasOption("z");
        var doHts = line.hasOption("h");
        
        BaseHandler.setDoErrors(line.hasOption("e"));
        BaseHandler.setDoVerbose(line.hasOption("v"));
        
        if(doInitial) {
            logger.info("Loading initial data into database...");
            new InitialDataParser("/InitialData.xml").execute();
            logger.info("   ...done.");
        }
        
        if(doFile) {
            var filename = line.getOptionValue("f");

            logger.info("Loading data from \"{}\" into database...", filename);
            new InitialDataParser(filename).execute();
            logger.info("   ...done.");
        }
        
        if(doZipCode) {
            logger.info("Loading zip code data into database...");
            new ZipCodeParser().execute();
            logger.info("   ...done.");
        }
        
        if(doHts) {
            logger.info("Loading harmonized tariff schedules into database...");
            new HtsParser().execute();
            logger.info("   ...done.");
        }
    }
}
