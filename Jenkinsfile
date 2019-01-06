pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    stages {
        stage('Clean') {
            steps {
                slackSend(color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
                ansiColor('xterm') {
                    withAnt(installation: 'Apache Ant Latest', jdk: 'Java SE Development Kit Latest') {
                        sh 'ant clean'
                    }
                }
            }
        }
        stage('All Tasks') {
            environment {
                ANT_OPTS = '-Xmx3072m'
            }
            steps {
                ansiColor('xterm') {
                    withAnt(installation: 'Apache Ant Latest', jdk: 'Java SE Development Kit Latest') {
                        sh 'ant complete'
                    }
                }
            }
        }
        stage('Artifacts') {
            parallel {
                stage('Deployables') {
                    steps {
                        script {
                            archiveArtifacts artifacts: 'build/ears/*.ear,build/ui/web/**/*.war,build/service/**/*.war,build/service/**/*.jar,build/lib/mysql-connector-java.jar'
                            currentBuild.result = 'SUCCESS'
                        }
                    }
                }
                stage('Javadoc') {
                    when {
                        branch 'master'
                    }
                    steps {
                        ansiColor('xterm') {
                            withAnt(installation: 'Apache Ant Latest', jdk: 'Java SE Development Kit Latest') {
                                sh 'ant publish-javadoc'
                            }
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            slackSend(color: '#00FF00', message: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend(color: '#FF0000', message: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            script {
                currentBuild.result = 'FAILURE'
            }
        }
        always {
            step([$class: 'Mailer',
                notifyEveryUnstableBuild: true,
                recipients: 'jenkins-echothree-notify@echothree.com'])
        }
    }
}
