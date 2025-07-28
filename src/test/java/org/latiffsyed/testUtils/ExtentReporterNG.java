package org.latiffsyed.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	
	static ExtentReports extent;
	
	public static ExtentReports getReporterObject() {
		
		//ExtentReport, ExtentSparkReporter
		String path = System.getProperty("user.dir")+"//reports//index.html";
		System.out.println("Hello"+path);
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Result");
		reporter.config().setDocumentTitle("Test Result");
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Latiff Syed");
		return extent;
	}

}
