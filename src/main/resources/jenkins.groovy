pipeline {
    agent any
    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Choose Browser')
        string(name: 'groups', defaultValue: 'all', description: 'Choose Test Group')
        string(name: 'environment', defaultValue: 'jenkins', description: 'Choose Environment')
        string(name: 'Branch', defaultValue: 'master', description: 'Git branch to build from')
    }
    stages {
        stage("Setup Environment") {
            steps {
                script {
                    // Install Maven
                    sh '''
                    # Install necessary dependencies
                    apt-get update && apt-get install -y wget unzip

                    # Download Maven
                    MAVEN_VERSION=3.8.5
                    wget https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz
                    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt
                    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven

                    # Set Maven environment variables
                    echo "M2_HOME=/opt/maven" >> /etc/environment
                    echo "PATH=\$M2_HOME/bin:\$PATH" >> /etc/environment
                    source /etc/environment
                    '''
                }
            }
        }
        stage("Checkout") {
            steps {
                script {
                    echo "Checking out branch: ${params.Branch}"
                }
                deleteDir()
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.Branch}"]],
                          userRemoteConfigs: [[url: 'https://github.com/lkumarra/Selenium_Framework.git']],
                          extensions: [[$class: 'LocalBranch', localBranch: "${params.Branch}"]]
                ])
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean install test -Denv=${params.environment} -Dbrowser=${params.browser} -Dgroups=${params.groups}"
            }
        }
        stage('Send Email Report') {
            steps {
                script {
                    // Send email notification
                    emailext (
                            subject: "Build ${currentBuild.fullDisplayName} - ${currentBuild.result}",
                            body: """
                            <p>Build ${currentBuild.fullDisplayName} completed with status: ${currentBuild.result}</p>
                            <p>Check console output at ${env.BUILD_URL} to view the results.</p>
                            <p>Attached are the test results.</p>
                        """,
                            mimeType: 'text/html',
                            to: 'Lavendra.rajputc1@gmail.com',
                            attachLog: true,
                            attachmentsPattern: '**/target/surefire-reports/*.xml'
                    )
                }
            }
        }
    }
    post {
        always {
            publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "./src/test/resources/executionArtifacts/reports",
                    reportFiles: 'Guru99BankReport.html',
                    reportName: "UI Automation Results",
                    reportTitles: 'Test Results'
            ])
            script {
                // Archive test reports
                archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
            }
        }
        success {
            script {
                emailext (
                        subject: "SUCCESS: Build ${currentBuild.fullDisplayName}",
                        body: """
                        <p>Build ${currentBuild.fullDisplayName} completed successfully.</p>
                        <p>Check console output at ${env.BUILD_URL} to view the results.</p>
                        <p>Attached are the test results.</p>
                    """,
                        mimeType: 'text/html',
                        to: 'Lavendra.rajputc1@gmail.com',
                        attachLog: true,
                        attachmentsPattern: '**/target/surefire-reports/*.xml'
                )
            }
        }
        failure {
            script {
                emailext (
                        subject: "FAILURE: Build ${currentBuild.fullDisplayName}",
                        body: """
                        <p>Build ${currentBuild.fullDisplayName} failed.</p>
                        <p>Check console output at ${env.BUILD_URL} to view the results.</p>
                        <p>Attached are the test results.</p>
                    """,
                        mimeType: 'text/html',
                        to: 'Lavendra.rajputc1@gmail.com',
                        attachLog: true,
                        attachmentsPattern: '**/target/surefire-reports/*.xml'
                )
            }
        }
    }
}
