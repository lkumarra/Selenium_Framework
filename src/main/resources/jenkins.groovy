pipeline {
    agent any
    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Choose Browser')
        string(name: 'groups', defaultValue: 'all', description: 'Choose Test Group')
        string(name: 'environment', defaultValue: 'jenkins', description: 'Choose Environment')
        gitParameter(name: 'Branch', type: 'PT_BRANCH', defaultValue: 'main', description: 'Git branch to build from', branch: '', useRepository: 'https://github.com/lkumarra/Selenium_Framework.git', sortMode: 'ASCENDING', selectedValue: 'DEFAULT', quickFilterEnabled: true)
    }
    stages {
        stage("Clean Workspace") {
            steps {
                deleteDir()
            }
        }
        stage('List Branches') {
            steps {
                bat 'git ls-remote --heads https://github.com/lkumarra/Selenium_Framework.git'
            }
        }
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch: ${params.Branch}"
                }
                checkout([$class: 'GitSCM',
                          branches: [
                                  [name: "*/${params.Branch}"]
                          ],
                          gitTool: 'Default',
                          userRemoteConfigs: [[url: 'https://github.com/lkumarra/Selenium_Framework.git']]
                ])
            }
        }
        stage('Build') {
            steps {
                bat "mvn clean test -Denv=${params.environment} -Dbrowser=${params.browser} -Dgroups=${params.groups}"
            }
        }
    }
}
