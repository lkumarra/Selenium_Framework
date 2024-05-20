import org.dom4j.Branch

pipeline {
    agent any
    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Choose Browser')
        string(name: 'groups', defaultValue: 'all', description: 'Choose Test Group')
        string(name: 'environment', defaultValue: 'jenkins', description: 'Choose Test Group')
        gitParameter(name: 'Branch', type: 'PT_BRANCH', defaultValue: 'main', description: 'Git branch to build from', branch: '', useRepository: 'https://github.com/lkumarra/Selenium_Framework.git', sortMode: 'ASCENDING', selectedValue: 'DEFAULT', quickFilterEnabled: true)
    }
    stages {
        stage("Clean Workspace"){
            steps{
                deleteDir()
            }
        }
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [
                                  [name: '*/$Branch']
                          ],
                          gitTool:'Default',
                          userRemoteConfigs: [[url: 'https://github.com/lkumarra/Selenium_Framework.git']]])
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean test -Denv=%environment% -Dbrowser=%browser% -Dgroups=%groups%"
            }
        }
    }
}
