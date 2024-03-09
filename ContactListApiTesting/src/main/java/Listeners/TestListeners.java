package Listeners;

import Utils.ExtentReportUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {
    public static ExtentReports reports;
    public static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {
     ExtentTest test=reports.createTest(result.getTestClass().getName()+"::"+ result.getMethod().getMethodName());
     extentTest.set(test);
    }
    @Override
    public void onTestSuccess(ITestResult result) {
    ExtentReportUtils.logPass(result.getMethod().getMethodName() +"::::"+ "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportUtils.logFail(result.getMethod().getMethodName() +"::::"+ "Fail");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportUtils.logFail(result.getMethod().getMethodName() +"::::"+ "Fail");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        String filename=ExtentReportUtils.getReportName();
        reports=ExtentReportUtils.setup(filename,"Contact Listing API Testing");
    }

    @Override
    public void onFinish(ITestContext context) {
        if(reports!=null){
            reports.flush();
        }
    }
}
