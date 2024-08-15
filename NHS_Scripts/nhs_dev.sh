#!/usr/bin/bash
whoami

echo "installing MariaDB..."
sudo dnf install mariadb-server -y
sudo systemctl start mariadb
sudo systemctl status mariadb
sudo systemctl enable mariadb


echo "creating mysql_secure_installation.txt..."
touch mysql_secure_installation.txt
cat << `EOF` >> mysql_secure_installation.txt

n
Y
comsc
comsc
Y
Y
Y
Y
Y
`EOF`

echo "running mysql_secure_installation..."
sudo mysql_secure_installation < mysql_secure_installation.txt

mysql -uroot -pcomsc -e "CREATE DATABASE IF NOT EXISTS nhs;CREATE USER 'BSE'@'localhost' IDENTIFIED BY 'comsc';GRANT ALL PRIVILEGES ON nhs.* TO 'BSE'@'localhost';FLUSH PRIVILEGES;"

echo "setting up git..."
sudo dnf install git -y

echo "setting up java..."
sudo yum install java-17-openjdk -y

echo "creating project deployment key file..."
cat << `EOF` >> nhs_keypair.key
-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----


`EOF`

echo "installing the deployment key..."
chmod 400 nhs_keypair.key

echo "connecting to gitlab..."
touch ~/.ssh/known_hosts
ssh-keyscan git.cardiff.ac.uk >> ~/.ssh/known_hosts
chmod 644 ~/.ssh/known_hosts

echo "cloning project from git..."
ssh-agent bash -c 'ssh-add nhs_keypair.key; git clone git@git.cardiff.ac.uk:c23102007/dissertation-nhs-lymphoedema.git'


echo "setting up gradle..."
sudo dnf install unzip -y
sudo dnf install wget -y
sudo wget https://services.gradle.org/distributions/gradle-8.8-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.8-bin.zip

echo "setting up gradle path..."
export PATH=$PATH:/opt/gradle/gradle-8.8/bin
gradle -v

echo "running application..."
cd dissertation-nhs-lymphoedema
git checkout main
cd nhs

gradle build
gradle bootrun