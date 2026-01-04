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

package com.echothree.service.job;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.result.GetPrinterGroupJobsResult;
import com.echothree.control.user.printer.common.result.GetPrinterGroupsResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.model.control.job.common.Jobs;
import com.echothree.model.control.printer.common.PrinterOptions;
import com.echothree.model.control.printer.common.transfer.PrinterGroupJobTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.common.workflow.PrinterGroupJobStatusConstants;
import com.echothree.model.control.printer.common.workflow.PrinterGroupStatusConstants;
import com.echothree.model.control.printer.common.workflow.PrinterStatusConstants;
import com.echothree.util.common.service.job.BaseScheduledJob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

@Singleton
public class PrintPrinterGroupJobsJob
        extends BaseScheduledJob {
    
    /** Creates a new instance of PrintPrinterGroupJobsJob */
    public PrintPrinterGroupJobsJob() {
        super(Jobs.PRINT_PRINTER_GROUP_JOBS.name());
    }

    @Override
    @Schedule(second = "40", minute = "*", hour = "*", persistent = false)
    public void executeJob()
            throws NamingException {
        super.executeJob();
    }

    class PrintJobListener
            extends PrintJobAdapter {

        String printerGroupJobName;

        public PrintJobListener(String printerGroupJobName) {
            this.printerGroupJobName = printerGroupJobName;
        }

        // Two received under Linux and OS X, one under Windows
        @Override
        public void printDataTransferCompleted(PrintJobEvent pje) {
            //System.out.println("printDataTransferCompleted: " + printerGroupJobName);
        }

        // Never received
        @Override
        public void printJobCompleted(PrintJobEvent pje) {
            //System.out.println("printJobCompleted: " + printerGroupJobName);
        }

    }

    private Map<String, PrinterGroupTransfer> getPrinterGroups()
            throws NamingException {
        Map<String, PrinterGroupTransfer> printerGroupsMap = new HashMap<>();
        var commandForm = PrinterUtil.getHome().getGetPrinterGroupsForm();

        Set<String> options = new HashSet<>();
        options.add(PrinterOptions.PrinterGroupIncludePrinters);
        commandForm.setOptions(options);

        var commandResult = PrinterUtil.getHome().getPrinterGroups(userVisitPK, commandForm);

        if(!commandResult.getHasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPrinterGroupsResult)executionResult.getResult();
            var printerGroups = result.getPrinterGroups();

            printerGroups.forEach((printerGroup) -> {
                printerGroupsMap.put(printerGroup.getPrinterGroupName(), printerGroup);
            });
        }

        return printerGroupsMap;
    }

    private void setPrinterGroupJobStatus(PrinterGroupJobTransfer printerGroupJob, String workflowDestinationName)
            throws NamingException {
        var commandForm = PrinterUtil.getHome().getSetPrinterGroupJobStatusForm();

        commandForm.setPrinterGroupJobName(printerGroupJob.getPrinterGroupJobName());
        commandForm.setPrinterGroupJobStatusChoice(workflowDestinationName);

        PrinterUtil.getHome().setPrinterGroupJobStatus(userVisitPK, commandForm);
    }

    private PrintService locatePrintService(PrinterGroupTransfer printerGroup, DocFlavor docFlavor, PrintRequestAttributeSet printRequestAttributeSet) {
        PrintService result = null;
        var printServices = PrintServiceLookup.lookupPrintServices(docFlavor, printRequestAttributeSet);
        var count = printServices.length;
        Set<String> printerNames = new HashSet<>(printerGroup.getPrinters().getSize());

        printerGroup.getPrinters().getList().stream().filter((printer) -> (printer.getPrinterStatus().getWorkflowStep().getWorkflowStepName().equals(PrinterStatusConstants.WorkflowStep_ACCEPTING_JOBS))).forEach((printer) -> {
            printerNames.add(printer.getPrinterName());
        });

        for(var i = 0; i < count; i++) {
            var printService = printServices[i];

            if(printerNames.contains(printService.getName())) {
                result = printService;
                break;
            }
        }

        return result;
    }

    private void printPrinterGroupJobs(List<PrinterGroupJobTransfer> printerGroupJobs)
            throws NamingException {
        if(!printerGroupJobs.isEmpty()) {
            var printerGroups = getPrinterGroups();

            for(var printerGroupJob : printerGroupJobs) {
                var printerGroup = printerGroups.get(printerGroupJob.getPrinterGroup().getPrinterGroupName());

                if(printerGroup != null && printerGroup.getPrinterGroupStatus().getWorkflowStep().getWorkflowStepName().equals(PrinterGroupStatusConstants.WorkflowStep_QUEUEING_JOBS)) {
                    var wasPrinted = false;
                    var mimeType = printerGroupJob.getDocument().getMimeType();
                    var docFlavor = new DocFlavor(mimeType.getMimeTypeName(), InputStream.class.getCanonicalName());
                    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

                    printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
                    printRequestAttributeSet.add(new Copies(printerGroupJob.getCopies()));
                    //printRequestAttributeSet.add(new JobPriority(printerGroupJob.getPriority()));
                    //printRequestAttributeSet.add(SheetCollate.COLLATED);

                    var printService = locatePrintService(printerGroup, docFlavor, printRequestAttributeSet);

                    if(printService != null) {
                        var docPrintJob = printService.createPrintJob();

			docPrintJob.addPrintJobListener(new PrintJobListener(printerGroupJob.getPrinterGroupJobName()));

			try {
                            InputStream inputStream = null;
                var document = printerGroupJob.getDocument();
                var entityAttributeTypeName = mimeType.getEntityAttributeType().getEntityAttributeTypeName();

                            if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                                inputStream = document.getBlob().getByteArrayInputStream();
                            } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                                inputStream = new ByteArrayInputStream(document.getClob().getBytes("UTF-8"));
                            }

                            Doc doc = new SimpleDoc(inputStream, docFlavor, null);

                            docPrintJob.print(doc, printRequestAttributeSet);
                            wasPrinted = true;
			} catch (UnsupportedEncodingException uee) {
                            uee.printStackTrace();
			} catch (PrintException pe) {
                            pe.printStackTrace();
			}
                    } else {
                        getLog().error("No suitable PrinterService was found for PrinterGroup " + printerGroup.getPrinterGroupName());
                    }

                    setPrinterGroupJobStatus(printerGroupJob, wasPrinted ? PrinterGroupJobStatusConstants.WorkflowDestination_QUEUED_TO_PRINTED : PrinterGroupJobStatusConstants.WorkflowDestination_QUEUED_TO_ERRORED);
                }
            }
        }
    }

    @Override
    public void execute()
            throws NamingException {
        var commandForm = PrinterUtil.getHome().getGetPrinterGroupJobsForm();

        commandForm.setPrinterGroupJobStatusChoice(PrinterGroupJobStatusConstants.WorkflowStep_QUEUED);

        Set<String> options = new HashSet<>();
        options.add(DocumentOptions.DocumentIncludeBlob);
        options.add(DocumentOptions.DocumentIncludeClob);
        commandForm.setOptions(options);

        var commandResult = PrinterUtil.getHome().getPrinterGroupJobs(userVisitPK, commandForm);

        if(!commandResult.getHasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPrinterGroupJobsResult)executionResult.getResult();

            printPrinterGroupJobs(result.getPrinterGroupJobs());
        }
    }
    
}
