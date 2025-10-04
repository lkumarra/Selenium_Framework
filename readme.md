
# Selenium Test Automation Framework

A robust and scalable test automation framework built using Selenium WebDriver, TestNG, and ExtentReports. This framework follows Page Object Model design pattern and provides comprehensive test reporting capabilities with support for cross-browser testing, parallel execution, and detailed HTML reporting.

## Features

- **Page Object Model (POM)**: Enhances test maintenance and reduces code duplication
- **TestNG Integration**: Parallel execution and flexible test configuration
- **Extent Reports**: Detailed HTML test reports with screenshots
- **Data-Driven Testing**: Excel-based test data management
- **Cross-Browser Testing**: Support for Chrome, Firefox, Safari, and Edge
- **Remote Execution**: Selenium Grid support for distributed testing
- **Retry Mechanism**: Automatic retry for flaky tests
- **Screenshot Capture**: Automatic capture on test failure
- **Logging**: Comprehensive logging with SLF4J and Logback
- **Property Management**: Externalized configuration
- **CI/CD Ready**: Jenkins pipeline support

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser
- IDE (IntelliJ IDEA or Eclipse) with Lombok plugin installed
- Git for version control

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/lkumarra/Selenium_Framework.git
   ```
2. **Navigate to the project directory:**
   ```sh
   cd Selenium_Framework
   ```
3. **Install dependencies:**
   ```sh
   mvn clean install
   ```

## Running Tests

### Run all tests:
```bash
mvn test
```

### Run specific test groups:
```bash
mvn test -Dgroups=smoke,regression
```

### Run with specific browser:
```bash
mvn test -Dbrowser=firefox
```

### Run with custom properties:
```bash
mvn test -DconfigFile=custom-config.properties
```

## Best Practices

1. Follow Page Object Model
2. Use meaningful names for test methods
3. Keep tests independent
4. Use appropriate assertions
5. Handle timeouts properly
6. Add comments for complex logic
7. Update test data in external files
8. Use TestNG groups effectively

## Troubleshooting

1. **Tests fail with TimeoutException**
   - Check implicit/explicit wait configurations
   - Verify element locators
   - Check page load timeouts

2. **Unable to start browser**
   - Verify WebDriver configuration
   - Check browser version compatibility
   - Update browser drivers

3. **Test data issues**
   - Verify file paths
   - Check file permissions
   - Validate data format

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/org/bank/
│   │       ├── constants/      # Constants and enums
│   │       ├── driverfactory/  # WebDriver initialization
│   │       ├── exceptions/     # Custom exceptions
│   │       ├── models/         # Data models/POJOs
│   │       ├── pages/         # Page objects
│   │       └── utils/         # Utility classes
│   └── resources/
│       ├── Configuration.properties
│       ├── jenkins.groovy
│       ├── logback.xml
│       └── reportportal.properties
└── test/
    ├── java/
    │   └── com/org/bank/
    │       ├── listeners/     # TestNG listeners
    │       └── tests/        # Test classes
    └── resources/
        ├── testData/        # Test data files
        └── testng.xml       # TestNG configuration

## Configuration

### Browser Configuration
Supported browsers in `Configuration.properties`:
```properties
browser=chrome     # Chrome (default)
browser=firefox    # Firefox
browser=edge      # Microsoft Edge
browser=safari    # Safari
```

### Test Data Configuration
Place test data files in `src/test/resources/testData/`:
- Excel files for data-driven tests
- JSON files for API test data
- Properties files for environment configuration

## Reporting

### Extent Reports
- Location: `test-output/ExtentReport.html`
- Features:
  - Test execution summary
  - Step-by-step execution details
  - Screenshots of failures
  - Environment details
  - Browser and platform information

### TestNG Reports
- Default TestNG reports in `test-output/index.html`
- Emailable report in `test-output/emailable-report.html`

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## Author

**Lavendra Kumar Rajput**
- Principal Software Development Engineer in Test (SDET)
- GitHub: [lkumarra](https://github.com/lkumarra)
- LinkedIn: [Lavendra Kumar Rajput](https://www.linkedin.com/in/lavendra-rajput)
- Email: Lavendra.rajputc1@gmail.com

## License

MIT License

Copyright (c) 2025 Lavendra Kumar Rajput

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Support

For support and queries:
1. Create an [issue](https://github.com/lkumarra/Selenium_Framework/issues)
2. Contact the author directly
3. Check existing documentation
