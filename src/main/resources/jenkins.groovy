pipeline {
    agent any
    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Choose Browser')
        string(name: 'groups', defaultValue: 'all', description: 'Choose Test Group')
        string(name: 'environment', defaultValue: 'jenkins', description: 'Choose Environment')
        gitParameter(name: 'Branch', type: 'PT_BRANCH', defaultValue: 'master', description: 'Git branch to build from', branch: '', useRepository: 'https://github.com/lkumarra/Selenium_Framework.git', sortMode: 'ASCENDING', selectedValue: 'DEFAULT', quickFilterEnabled: true)
    }
    stages {
        stage("Clean Workspace") {
            steps {
                deleteDir()
            }
        }
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch: ${params.Branch}"
                }
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.Branch}"]],
                          userRemoteConfigs: [[url: 'https://github.com/lkumarra/Selenium_Framework.git']],
                          extensions: [[$class: 'LocalBranch', localBranch: "${params.Branch}"]]
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
