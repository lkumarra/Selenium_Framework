pipeline {
    agent any

    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Choose Browser')
        string(name: 'groups', defaultValue: 'all', description: 'Choose Test Group')
        string(name: 'environment', defaultValue: 'jenkins', description: 'Choose Test Group')
        gitParameter(name: 'Branch', type: 'PT_BRANCH', defaultValue: 'main', description: 'Git branch to build from', branch: '', useRepository: 'https://github.com/lkumarra/Selenium_Framework.git', sortMode: 'ASCENDING', selectedValue: 'DEFAULT', quickFilterEnabled: true)
    }

    environment {
        MAVEN_HOME = tool name: 'Maven 3.6.3', type: 'maven'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: "${params.Branch}"]],
                          doGenerateSubmoduleConfigurations: false, extensions: [],
                          submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/lkumarra/Selenium_Framework.git']]])
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean test -Denv=%environment% -Dbrowser=%browser% -Dgroups=%groups%"
            }
        }
        stage('Echo Parameters') {
            steps {
                echo "STRING_PARAM_1: ${params.STRING_PARAM_1}"
                echo "STRING_PARAM_2: ${params.STRING_PARAM_2}"
                echo "GIT_PARAM: ${params.GIT_PARAM}"
            }
        }
    }
}
