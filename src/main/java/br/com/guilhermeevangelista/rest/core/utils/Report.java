package br.com.guilhermeevangelista.rest.core.utils;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.rajatthareja.reportbuilder.ReportBuilder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateFormat dateFormatFile = new SimpleDateFormat("ddMMyyyy");
    private static final Date date = new Date();

    public static void gerarRelatio(){

        // Create ReportBuilder Object
        ReportBuilder reportBuilder = new ReportBuilder();

        reportBuilder.setReportDirectory("target/");

        // Set output report file name
        reportBuilder.setReportFileName(dateFormatFile.format(date));

        // Set Report Title
        reportBuilder.setReportTitle("Frame ResAssured Bdd G.Evangelista");
        reportBuilder.setReportColor("yellow");

        // Enable voice control for report
        reportBuilder.enableVoiceControl();

        // Add additional info for Report
        reportBuilder.setAdditionalInfo("Date", dateFormat.format(date));

        // Create list or report Files or Directories or URLs or JSONObject or JSONString
        List<Object> cucumberJsonReports = new ArrayList<>();
        cucumberJsonReports.add(new File("target/cucumber.json"));
        cucumberJsonReports.add(new File("target/"));

        // Build your report
        reportBuilder.build(cucumberJsonReports);
    }
}
