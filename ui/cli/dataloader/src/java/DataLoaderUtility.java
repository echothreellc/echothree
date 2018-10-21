// --------------------------------------------------------------------------------

import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.hts.HtsParser;
import com.echothree.ui.cli.dataloader.zipcode.ZipCodeParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataLoaderUtility {
    
    private static final Log log = LogFactory.getLog(DataLoaderUtility.class);
    
    static CommandLine getCommandLine(String args[])
            throws ParseException {
        return new DefaultParser().parse(new Options()
                .addOption("i", "initial", false, "load initial data into database")
                .addOption("f", "file", true, "load data into database from specified file")
                .addOption("z", "zipcode", true, "load zip code data from specified file")
                .addOption("h", "hts", true, "load harmonized tariff schedule codes from specified directory")
                .addOption("e", "errors", true, "show command result when an error is returned")
                .addOption("v", "verbose", true, "verbose command results"), args);
    }
    
    public static void main(String args[])
            throws Exception {
        CommandLine line = getCommandLine(args);
        
        boolean doInitial = line.hasOption("i");
        boolean doFile = line.hasOption("f");
        boolean doZipCode = line.hasOption("z");
        boolean doHts = line.hasOption("h");
        
        BaseHandler.setDoErrors(line.hasOption("e"));
        BaseHandler.setDoVerbose(line.hasOption("v"));
        
        if(doInitial) {
            log.info("Loading initial data into database...");
            new InitialDataParser("src/xml/InitialData.xml").execute();
            log.info("   ...done.");
        }
        
        if(doFile) {
            String filename = line.getOptionValue("f");

            log.info("Loading data from \"" + filename + "\" into database...");
            new InitialDataParser(filename).execute();
            log.info("   ...done.");
        }
        
        if(doZipCode) {
            String filename = line.getOptionValue("z");

            log.info("Loading zip code data from \"" + filename + "\" into database...");
            new ZipCodeParser(filename).execute();
            log.info("   ...done.");
        }
        
        if(doHts) {
            String htsDirectory = line.getOptionValue("h");

            log.info("Loading harmonized tariff schedules from \"" + htsDirectory + "\" into database...");
            new HtsParser(htsDirectory).execute();
            log.info("   ...done.");
        }
    }
}
