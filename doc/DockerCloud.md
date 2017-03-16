# Instruction deploiment sur DockerCloud

## Mémo

Push sur dockerHub
```
docker -u <username> -p <password>
docker push <username>/<imagename>:<tag>
```

Build
```
docker build -t <username>/<imagename>:<tag> . # . if the Dockerfile is in the current directory
```

## Pré-requis

* Image jenkins (jenkins/Dockerfile) sur dockerHub [korlan/jenkins:latest]
* Image mysql (mysql/Dockerfile) sur dockerHub [korlan/mysql:latest]
* Image maven [maven:3.3.9-jdk-8]

## Deploy noeud sur DockerCould via instance EC2 d'AWS.

Utiliser une Instance t2.micro d'EC2
* Link Aws account to docker cloud https://docs.docker.com/docker-cloud/infrastructure/link-aws/#add-aws-account-credentials
* Create a node https://docs.docker.com/docker-cloud/getting-started/your_first_node/

:warn: Atention à la localisation de vos groupes de sécurité (Sur AWS: Services->EC2->Groupe de sécruité) l'Id du vpc du node sur DockerCloud doit correspondre au groupe que vous avez définis dans aws (default -> modifier inboud pour toute les ip)

## Service Jenkins
* Create jenkins service (reference: https://docs.docker.com/docker-cloud/getting-started/your_first_service/)

Use korlan/jenkins.
Configure Volumes
/var/run/docker.socker -> /var/run/docker.sock
/usr/bin/docker -> /usr/bin/docker
/prod-volume -> /prod-volume
/test-volume -> /test-volume
All on R&W.

* Go to the endpoint and configure jenkins
Help : DockerHub, go to Container select jenkins container and tab terminal to have a bash to cat initalPassword (/var/jenkins_home/secrets/)

## Configuration du noeud

### Connexion ssh
 *  Ref : []:https://docs.docker.com/docker-cloud/infrastructure/ssh-into-a-node/

Par défaut l'instance t2.micro n'a pas de swap. Sur 1024 Mo de ram c'est problématique puisque 1 noeud et 5 conteneur vont tourner dessus (jenkins + 2*mysql + tomcat + maven[pas tout le temps])

* Run dd if=/dev/zero of=/swapfile bs=1M count=1024
* Run mkswap /swapfile
* Run swapon /swapfile
* Add this line /swapfile swap swap defaults 0 0 to /etc/fstab
 Thanks to []: http://stackoverflow.com/questions/10284532/amazon-ec2-mysql-aborting-start-because-innodb-mmap-x-bytes-failed-errno-12

### Network
On the node
sudo docker network create --driver bridge test-network
sudo docker network create --driver bridge prod-network
sudo docker run -d --name=mysql-prod --net prod-network korlan/mysql:latest
sudo docker run -d --name=mysql --net test-network korlan/mysql:latest
sudo docker run -d -p 8081:8080 --name tomcat --net prod-network -v /prod-volume:/usr/local/tomcat/webapps/ tomcat:7-jre8

sudo docker run --net test-network --name maven -v /test-volume:/usr/src/mymaven -w /usr/src/mymaven -i maven:3.3.9-jdk-8 mvn clean package
Note: it will fail (no pom.xml) but container is created and can be start by jenkins after jenkis pull data.

## Conf jenkins.

### Freestyle
* conf for github project
* Build - shell script

```
sudo cp -r * /test-volume
sudo docker start -i maven
sudo cp /test-volume/target/cdb.war /prod-volume
sudo cp -r /test-volume/target .

sudo docker login -u korlan -p excilys
sudo docker commit tomcat korlan/tomcat:latest
sudo docker push korlan/tomcat:latest
```


### Pipeline
* conf for github project
* Pipeline script
```
node {
   stage('Prepare') {
     git branch: 'feature/clean-pom', url: 'https://github.com/koorlan/cdb-2017.git'
     sh "sudo cp -r * /test-volume"
   }
   stage('Test & Build') {
      // Run the maven build
      if (isUnix()) {
         sh "sudo docker start -i maven"
      }
   }
   stage('Deploy') {
       sh "sudo cp /test-volume/target/cdb.war /prod-volume"
   }
   stage('DockerHub') {
        sh "sudo docker login -u korlan -p excilys"
        sh "sudo docker commit tomcat korlan/tomcat:latest"
        sh "sudo docker push korlan/tomcat:latest"

   }
}
```

## Test
* trigger build
* goto node ip:8081
* cdb should be alive
