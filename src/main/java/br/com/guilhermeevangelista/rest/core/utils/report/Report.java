package br.com.guilhermeevangelista.rest.core.utils.report;

import com.rajatthareja.reportbuilder.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateFormat dateFormatFile = new SimpleDateFormat("ddMMyyyy");
    private static final Date date = new Date();

    public static void gerarRelatorioReportBuilder(){
        ReportBuilder reportBuilder = new ReportBuilder();

        reportBuilder.setReportDirectory("target/");

        reportBuilder.setReportFileName(dateFormatFile.format(date));

        reportBuilder.setReportTitle("Frame ResAssured Bdd G.Evangelista");
        reportBuilder.setReportColor("yellow");

        reportBuilder.enableVoiceControl();

        reportBuilder.setAdditionalInfo("Date", dateFormat.format(date));

        List<Object> cucumberJsonReports = new ArrayList<>();
        cucumberJsonReports.add(new File("target/cucumber-report/cucumber.json"));
        cucumberJsonReports.add(new File("target/"));

        reportBuilder.build(cucumberJsonReports);
    }

    public static void gerarRelarotioClueCumber(){
        try {
            Process process = Runtime.getRuntime().exec(
                    "cmd /c report.bat", null, new File("./"));

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gerarRelatorio(){
        gerarRelarotioClueCumber();
    }

}