// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.printer;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.PrinterService;
import com.echothree.control.user.printer.common.form.CreatePrinterForm;
import com.echothree.control.user.printer.common.form.CreatePrinterGroupDescriptionForm;
import com.echothree.control.user.printer.common.form.CreatePrinterGroupJobForm;
import com.echothree.control.user.printer.common.form.PrinterFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.persistence.type.ByteArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PrinterGroupHandler
        extends BaseHandler {

    PrinterService printerService;
    String printerGroupName;

    boolean inPrinterGroupJob = false;
    Map<String, Object> createPrinterGroupJobMap = null;
    char []clob = null;

    /** Creates a new instance of PrinterGroupHandler */
    public PrinterGroupHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String printerGroupName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            printerService = PrinterUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.printerGroupName = printerGroupName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("printerGroupDescription")) {
            CreatePrinterGroupDescriptionForm commandForm = PrinterFormFactory.getCreatePrinterGroupDescriptionForm();

            commandForm.setPrinterGroupName(printerGroupName);
            commandForm.set(getAttrsMap(attrs));

            printerService.createPrinterGroupDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("printer")) {
            CreatePrinterForm commandForm = PrinterFormFactory.getCreatePrinterForm();

            commandForm.setPrinterGroupName(printerGroupName);
            commandForm.set(getAttrsMap(attrs));

            printerService.createPrinter(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new PrinterHandler(initialDataParser, this, commandForm.getPrinterName()));
        } else if(localName.equals("printerGroupJob")) {
            createPrinterGroupJobMap = getAttrsMap(attrs);

            inPrinterGroupJob = true;
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if(inPrinterGroupJob) {
            int oldLength = clob != null? clob.length: 0;
            char []newClob = new char[oldLength + length];

            if(clob != null) {
                System.arraycopy(clob, 0, newClob, 0, clob.length);
            }

            System.arraycopy(ch, start, newClob, oldLength, length);

            clob = newClob;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("printerGroupJob")) {
            CreatePrinterGroupJobForm commandForm = PrinterFormFactory.getCreatePrinterGroupJobForm();

            commandForm.setPrinterGroupName(printerGroupName);
            commandForm.set(createPrinterGroupJobMap);
            commandForm.setClob(clob == null ? null : new String(clob));

            String path = (String)createPrinterGroupJobMap.get("Path");
            if(path != null) {
                File file = new File(path);
                long length = file.length();

                if(length < Integer.MAX_VALUE) {
                    try {
                        InputStream is = new FileInputStream(file);
                        byte[] bytes = new byte[(int)length];
                        int offset = 0;
                        int numRead = 0;

                        while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                            offset += numRead;
                        }

                        // Ensure all the bytes have been read in
                        if (offset < bytes.length) {
                            throw new SAXException("Could not completely read file "+file.getName());
                        }

                        // Close the input stream and return bytes
                        is.close();

                        commandForm.setBlob(new ByteArray(bytes));
                    } catch(FileNotFoundException fnfe) {
                        throw new SAXException(fnfe);
                    } catch(IOException ioe) {
                        throw new SAXException(ioe);
                    }
                }
            }

            printerService.createPrinterGroupJob(initialDataParser.getUserVisit(), commandForm);

            inPrinterGroupJob = false;
            createPrinterGroupJobMap = null;
            clob = null;
        } else if(localName.equals("printerGroup")) {
            initialDataParser.popHandler();
        }
    }

}
