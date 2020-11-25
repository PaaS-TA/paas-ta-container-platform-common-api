pipeline {
	environment {
		IMAGE_NAME = "container-platform-common-api"
		REGISTRY_HARBOR_CREDENTIAL = 'harbor-credential'
		REGISTRY_DOCKER_URL = "15.164.129.57:5000"
		REGISTRY_HARBOR_URL = "15.164.129.57:8090"
		PROJECT_NAME = "container-platform"
	}
	agent any
	stages {
		stage('Cloning Github') {
			steps {
				git branch: 'dev', credentialsId: '7f02bc4a-645f-48db-b3fe-343abb92ef03', url: 'https://github.com/PaaS-TA/paas-ta-container-platform-common-api'
				slackSend (channel: '#jenkins', color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
			}
		}
		stage('Environment') {
            parallel {
                stage('wrapper') {
                    steps {
                        sh 'gradle wrapper'
                    }
                }
                stage('display') {
                    steps {
                        sh 'ls -la'
                    }
                }
            }
        }
		stage('copy config') {
			steps {
				sh 'cp /var/lib/jenkins/workspace/config/common/application.yml src/main/resources/application.yml'
			}
		}
		stage('Clean Build') {
            steps {
                sh './gradlew clean'
            }
        }
		stage('Build Jar') {
            steps {
                sh './gradlew build'
            }
        }
		stage('Building image') {
			steps{
				script {
					dockerImage = docker.build REGISTRY_DOCKER_URL+"/"+IMAGE_NAME+":latest"
					dockerVersionedImage = docker.build REGISTRY_DOCKER_URL+"/"+IMAGE_NAME+":$BUILD_NUMBER"
					harborImage = docker.build REGISTRY_HARBOR_URL+"/"+PROJECT_NAME+"/"+IMAGE_NAME+":latest"
                    harborVersionedImage = docker.build REGISTRY_HARBOR_URL+"/"+PROJECT_NAME+"/"+IMAGE_NAME+":$BUILD_NUMBER"
				}
			}
		}
		stage('Deploy Image') {
			steps{
				script {
					docker.withRegistry("http://"+REGISTRY_DOCKER_URL)
					{
						dockerImage.push()
						dockerVersionedImage.push()
					}
					docker.withRegistry("http://"+REGISTRY_HARBOR_URL, REGISTRY_HARBOR_CREDENTIAL)
                    {
                        harborImage.push()
                        harborVersionedImage.push()
                    }
				}
			}
		}
		stage('Kubernetes deploy') {
			steps {
				kubernetesDeploy (
					configs: "yaml/Deployment.yaml", 
					kubeconfigId: '18a45b0c-e867-4f3e-9da3-67f65d5b63b4', 
					enableConfigSubstitution: true
				)
			}
		}
		stage('Remove Unused docker image') {
			steps{
				echo "REGISTRY_DOCKER_URL: $REGISTRY_DOCKER_URL"
                echo "REGISTRY_HARBOR_URL: $REGISTRY_HARBOR_URL"
                echo "PROJECT_NAME: $PROJECT_NAME"
                echo "IMAGE_NAME: $IMAGE_NAME"
                echo "BUILD_NUMBER: $BUILD_NUMBER"
                sh "docker rmi $REGISTRY_DOCKER_URL/$IMAGE_NAME:latest"
                sh "docker rmi $REGISTRY_DOCKER_URL/$IMAGE_NAME:$BUILD_NUMBER"
                sh "docker rmi $REGISTRY_HARBOR_URL/$PROJECT_NAME/$IMAGE_NAME:latest"
                sh "docker rmi $REGISTRY_HARBOR_URL/$PROJECT_NAME/$IMAGE_NAME:$BUILD_NUMBER"
			}
		}
	}
    post {
        success {
            slackSend (channel: '#jenkins', color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend (channel: '#jenkins', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}
