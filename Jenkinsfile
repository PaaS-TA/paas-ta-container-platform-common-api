pipeline {
	environment {
		IMAGE_NAME = "container-platform-common-api"
		REGISTRY_CREDENTIAL = ''
		REGISTRY_URL = "15.164.129.57:5000"
	}
	agent any
	stages {
		stage('Cloning Github') {
			steps {
				git branch: 'dev', credentialsId: '7f02bc4a-645f-48db-b3fe-343abb92ef03', url: 'https://github.com/PaaS-TA/paas-ta-container-platform-common-api'
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
					dockerImage = docker.build REGISTRY_URL+"/"+IMAGE_NAME+":latest"
					dockerVersionedImage = docker.build REGISTRY_URL+"/"+IMAGE_NAME+":$BUILD_NUMBER"
				}
			}
		}
		stage('Deploy Image') {
			steps{
				script {
					docker.withRegistry("http://"+REGISTRY_URL) 
					{
						dockerImage.push()
						dockerVersionedImage.push()
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
				echo "REGISTRY_URL: $REGISTRY_URL"
				echo "IMAGE_NAME: $IMAGE_NAME"
				echo "BUILD_NUMBER: $BUILD_NUMBER"
				sh "docker rmi $REGISTRY_URL/$IMAGE_NAME:latest"
				sh "docker rmi $REGISTRY_URL/$IMAGE_NAME:$BUILD_NUMBER"
			}
		}
	}
}
