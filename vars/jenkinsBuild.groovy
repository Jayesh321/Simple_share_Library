def call(String grepo = 'a', String gbranch = 'a', String gitcred = 'a') {

pipeline {
environment { 
		gitRepo = "${grepo}"
		gitBranch = "${gbranch}"
		gitCredId = "${gitcred}"
	}
		
	agent none
	
	stages {
		stage("POLL SCM"){
			steps {
				 checkout([$class: 'GitSCM', branches: [[name: "$gitBranch"]], extensions: [], userRemoteConfigs: [[credentialsId: "$gitCredId", url: "$gitRepo"]]])
			}
		}	
					
	}
			  
}

}
