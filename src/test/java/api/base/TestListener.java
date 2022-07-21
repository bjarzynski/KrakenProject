package api.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestListener implements ITestListener {
  private final Logger logger = LoggerFactory.getLogger(TestListener.class);

  @Override
  public void onTestStart(ITestResult result) {
    ITestListener.super.onTestStart(result);
    logger.info("Starting test: " + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ITestListener.super.onTestSuccess(result);
    logger.info("PASSED: " + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ITestListener.super.onTestFailure(result);
    logger.info("FAILED: " + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ITestListener.super.onTestSkipped(result);
    logger.info("SKIPPED: " + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    logger.info("Test within success percentage: "
            + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {
    ITestListener.super.onTestFailedWithTimeout(result);
    logger.info("FAILED with timeout: "
            + result.getName() + Arrays.toString(result.getParameters()));
  }

  @Override
  public void onStart(ITestContext context) {
    ITestListener.super.onStart(context);
    logger.info("\n\n-------------STARTING EXECUTION-------------\n");
  }

  @Override
  public void onFinish(ITestContext context) {
    ITestListener.super.onFinish(context);
    logger.info(TestLogUtils.getExecutionSummary(context));
  }
}
