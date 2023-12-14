## Purpose of the project
The Pa11y-one library is designed to work seamlessly with the Pa11y-one Docker container available at https://hub.docker.com/repository/docker/bsattiva/pa11y-one/general. This integration enables test engineers to conduct web accessibility tests.

## Features
- Navigate through web apps using actions such as: navigate, click, sendKeys, wait, and waitClickable.
- Run Pa11y accessibility tests against the current DOM context.
- Generate an HTML report that highlights all discovered issues.

## Setup
### Docker
run following command locally or at a remote machine:
```
docker pull bsattiva/pa11y-one
```
```
docker run --privileged -d -p 3010:3010 bsattiva/pa11y-one
```

### Java project
- Include this library as a resource dependency of your Java project
- In your test code initialize a Pa11yDriver class:
```
String baseUrl = <landing page>;
Pa11yDriver driver = new Pa11yDriver(baseUrl);
```
- Then add test steps to navigate to the page you want to run accessibility tests against:
```
String selector = "<css_selector>";
int timout = "<timeout_millisec>";
driver.click(selector, timeout);
driver.wait(timeout);
driver.sendKeys(selector, "some text", timeout);
driver.waitClickable(selector, timeout);
```
- Once navigated to the needed context run the actual Accessibility test:
```
String scenarioName = "<name of scenario>";
driver.testAccessibility(hostname, port, scenarioName);
//port is 3010
```
- After all tests are run, generate the report
```
Issues.generateReport(System.getProperty("user.dir") + File.separator + "acc-report.html");
```
replace the path above with the one you need. 