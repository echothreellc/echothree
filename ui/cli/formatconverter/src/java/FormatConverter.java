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

/**
 * http://jroller.com/page/mlconnor?entry=java_character_encoding_conversion
 *
 * Most people won't care about character encoding/decoding
 * in Java until it bites them. CSV files that are created
 * on Windows from Excel for example are stored in the
 * Windows standard CP1252 encoding. Using these files on
 * your Linux machine in production may produce exceptions
 * and otherwise nasty results as your Linux machine probably
 * won't have CP1252 encoding. Doing it on the fly isn't an
 * option without the encoding so the best solution I came
 * up with is just a Java program to read in one encoding and
 * write out another.
 *
 * This program will take a file of one encoding and
 * convert it to a file with another encoding.  Both
 * encodings are provided on the command line.  Keep
 * in mind that there is no good way to detect an
 * encoding so this code trusts that you are providing
 * the correct input encoding.
 *
 * Also note that not every platform comes with all of
 * the encodings that you may need.  Whether or not
 * a platform will have the encoding you provide is
 * completely dependent on that platform.  Java comes
 * with a few standard encodings such as UTF-8.
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FormatConverter {
    
    public static void main(String[] args)
    throws IOException {
        if ( args.length != 4 ) {
            System.out.println("usage : java FormatConverter inputFile inputEncoding outputfile outputEncoding");
            System.exit(1);
        }
        
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);
        String inputEncoding = args[1];
        String outputFileName = args[2];
        File outputFile = new File(outputFileName);
        String outputEncoding = args[3];
        
        if ( ! inputFile.exists() ) throw new RuntimeException("input file '" + inputFileName + " does not exist");
        if ( ! inputFile.isFile() ) throw new RuntimeException("input file '" + inputFileName + " is not a file");
        if ( ! inputFile.canRead() ) throw new RuntimeException("input file '" + inputFileName + " cannot be read, check the file permissions");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), inputEncoding));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile), outputEncoding));
                
        String line = null;
        while((line = reader.readLine()) != null) {
            writer.println(line);
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}
