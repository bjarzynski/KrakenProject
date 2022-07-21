package api.base;

import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Set;

public class TestLogUtils {
  public static String getExecutionSummary(ITestContext context) {
    StringBuilder executionSummary = new StringBuilder();
    if (context.getFailedTests().size() > 0) {
      executionSummary.append("\n\nFailed tests: \n")
              .append(getTestNamesWithParameters(context.getFailedTests().getAllResults()));
    }
    if (context.getSkippedTests().size() > 0) {
      executionSummary.append("\nSkipped tests: ")
              .append(getTestNamesWithParameters(context.getSkippedTests().getAllResults()));
    }
    return executionSummary.toString();
  }

  private static String getTestNamesWithParameters(Set<ITestResult> testResultsSet) {
    StringBuilder testNamesWithParameters = new StringBuilder();
    testResultsSet.forEach(t -> testNamesWithParameters
            .append(t.getMethod().getMethodName())
            .append(Arrays.toString(t.getParameters()))
            .append("\n"));
    return testNamesWithParameters.toString();
  }
}
