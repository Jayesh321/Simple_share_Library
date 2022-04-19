def call(String grepo = 'a', String gbranch = 'a', String gitcred = 'a') {

pipeline {
environment { 
		gitRepo = "${grepo}"
		gitBranch = "${gbranch}"
		gitCredId = "${gitcred}"
	}
		
	agent any
	
	stages {
		stage("POLL SCM"){
			steps {
				 checkout([$class: 'GitSCM', branches: [[name: "$gitBranch"]], extensions: [], userRemoteConfigs: [[credentialsId: "$gitCredId", url: "$gitRepo"]]])
			}
		}	
		stage('BUILD IMAGE') { 
			 steps { 
				 sh "mvn clean install"
			} 
		}
		stage {
			steps {
				sshagent(['tomcat']) {
					sh "scp -o StrictHostKeyChecking=no /var/lib/jenkins/workspace/shared/target/works-with-heroku-1.0.war ec2-user@3.110.128.73:/opt/apache-tomcat-9.0.62/webapps"
				}
			}
		}			
	}
			  
}

}
