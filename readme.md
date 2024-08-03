
# Selenium Framework

This is a comprehensive Selenium automation framework developed using Java. The framework integrates key technologies such as TestNG for testing, ExtentReports for reporting, and employs the Page Object Model (POM) design pattern.

## Features

- **Selenium WebDriver**: Automate web applications for testing purposes.
- **TestNG**: Manage test configurations and execution.
- **ExtentReports**: Generate detailed and visually appealing test reports.
- **Page Object Model**: Enhance code maintainability and reusability.

## Requirements

To use this framework, ensure you have the following installed:

- Java JDK 8 or later
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- IDE for Java (Eclipse, IntelliJ IDEA, etc.)

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

1. **Update configuration:**
   Modify `src/main/resources/config.properties` to set your application URL and other settings.
2. **Execute tests:**
   ```sh
   mvn test
   ```

## Project Structure

- **src/main/java**: Contains the main codebase.
    - **pages**: Page classes representing the web pages.
    - **tests**: Test classes.
    - **utils**: Utility classes and helper methods.
- **src/test/resources**: Test resources like test data and configuration files.
- **reports**: Generated test reports.

## Reporting

After executing the tests, the reports will be generated in the `reports` directory. Open the `index.html` file in your browser to view the test results.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the MIT License.

## Contact

For any inquiries, please contact [your email address].
