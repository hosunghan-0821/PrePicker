def getCommitType(commit) {
    script {
        return sh(script : "git show -s --format=%B $commit", returnStdout: true)
    }
}


node {
    def vars = checkout scm
    def prj_type = getCommitType(vars.GIT_COMMIT).substring(1,3)

    def branch = env.BRANCH_NAME
    def jenkins_home= env.JENKINS_HOME
    echo "Branch: ${branch}"


    if(prj_type=="BE" && branch=="dev"){

        stage('Clean'){
             step([$class: 'WsCleanup'])
        }
       stage('clone') {
            git branch: branch, url:'https://github.com/hosunghan-0821/PrePicker.git'
        }
        stage('settings'){
             withCredentials([file(credentialsId: "prepicker_properties", variable: 'CREDENTIALS_FILE_PATH')]) {
                    def credentials_content = readFile env.CREDENTIALS_FILE_PATH
                    def properties_file_path = "${env.WORKSPACE}/Backend/src/main/resources/application.yml"
                    writeFile file: properties_file_path, text: credentials_content
                }
        }

        dir("${env.WORKSPACE}") {
            stage ('Gradle Build') {
                sh 'chmod +x ./Backend/gradlew'
                sh './Backend/gradlew clean build'
            }

            stage ('Docker Build') {
                sh 'docker build -t prepicker -f ./Backend/DockerFile .'
            }

            stage ('Deploy') {
                try{
                     sh 'docker stop prepicker_jar'
                     sh 'docker rm prepicker_jar'
                }
                catch(Exception){
                    echo "Docker Container 실행 중이지 않았음"
                }

                sh 'docker run -d --name prepicker_jar -p 8084:8080 prepicker '
            }
            stage ('Finish'){
               sh 'docker rmi $(docker images -f "dangling=true" -q)'
            }
        }
    }

}