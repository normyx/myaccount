#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    gitlabCommitStatus(name: 'build') {
        docker.image('openjdk:8').inside('-u root -e MAVEN_OPTS="-Duser.home=./"') {
            stage('check java') {
                sh "java -version"
            }

            stage('clean') {
                sh "chmod +x mvnw"
                sh "./mvnw clean"
            }

            stage('install tools') {
                sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v8.11.3 -DyarnVersion=v1.6.0"
            }

            stage('yarn install') {
                sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
            }

            stage('backend tests') {
                try {
                    sh "./mvnw test"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }

            stage('frontend tests') {
                try {
                    sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn -Dfrontend.yarn.arguments=test"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/target/test-results/jest/TESTS-*.xml'
                }
            }

            //stage('package and deploy') {
            //    sh "./mvnw com.heroku.sdk:heroku-maven-plugin:2.0.5:deploy -DskipTests -Pprod -Dheroku.appName="
            //    archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            //}

            stage('package') {
                sh "./mvnw package -DskipTests -Pprod"
                archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }

            stage('quality analysis') {
                withSonarQubeEnv('Sonar') {
                    sh "./mvnw sonar:sonar"
                }
            }
        }
    }
}
