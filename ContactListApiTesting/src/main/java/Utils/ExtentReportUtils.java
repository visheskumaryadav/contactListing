package Utils;

import Listeners.TestListeners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportUtils {

    public static ExtentReports setup(String filename,String reportName){
        ExtentSparkReporter container=new ExtentSparkReporter(System.getProperty("user.dir")+"\\report\\" + filename );
        container.config().setDocumentTitle("Report");
        ExtentReports body=new ExtentReports();
        body.attachReporter(container);
        return body;
    }

    public static String getReportName() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);
        String reportName = "TestReport" + formattedTime + ".html";
        return reportName;
    }

    public static void logPass(String log){
        TestListeners.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }
    public static  void logFail(String log){
        TestListeners.extentTest.get().fail(MarkupHelper.createLabel(log,ExtentColor.RED));
    }
    public static void logInfo(String log) {
        TestListeners.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.GREY));
    }
    public static void logSkip(String log) {
        TestListeners.extentTest.get().skip(MarkupHelper.createLabel(log, ExtentColor.BROWN));
    }
}
