#!/usr/bin/bash

echo "update logging configuration..."
sudo sh -c "echo '*.info;mail.none;authpriv.none;cron.none /dev/ttyS0' >> /etc/rsyslog.conf"
sudo systemctl restart rsyslog


cd /home/rocky
echo in directory $PWD

echo "installing MariaDB..."
# sudo yum install mysql -y
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

sudo yum install wget -y
sudo yum install unzip -y
sudo yum install git -y

echo "Installing Java 17..."
sudo yum install java-17-openjdk-devel -y
echo java --version

echo "install Jenkins"
# https://pkg.jenkins.io/redhat-stable/
curl --silent --location http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo | sudo tee /etc/yum.repos.d/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
sudo rpm --import https://pkg.jenkins.io/redhat/jenkins.io-2023.key
sudo dnf install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm -y
sudo yum install jenkins -y

echo "installing gitlab server key... has to be added to the jenkins user home (~) dir "
mkdir /var/lib/jenkins/.ssh
sudo touch /var/lib/jenkins/.ssh/known_hosts
sudo ssh-keyscan git.cardiff.ac.uk >> /var/lib/jenkins/.ssh/known_hosts
sudo chmod 644 /var/lib/jenkins/.ssh/known_hosts


# If you want jenkins on port 8081 so you can run your app on 8080 then change the default jenkins port.
#(look up linux sed - it is really cool)
# sudo sed -i 's/JENKINS_PORT="8080"/JENKINS_PORT="8081"/g' /etc/sysconfig/jenkins
sudo systemctl start jenkins
systemctl status jenkins
sudo systemctl enable jenkins


echo "Installing gradle..."
# wget https://services.gradle.org/distributions/gradle-6.7.1-bin.zip
wget https://services.gradle.org/distributions/gradle-8.8-bin.zip
sudo mkdir /opt/gradle
# sudo unzip -d /opt/gradle gradle-6.7.1-bin.zip
# export PATH=$PATH:/opt/gradle/gradle-6.7.1/bin
sudo unzip -d /opt/gradle gradle-8.8-bin.zip
export PATH=$PATH:/opt/gradle/gradle-8.8/bin
echo gradle -v

echo "Installing terraform..."
cd /home/rocky
wget https://releases.hashicorp.com/terraform/1.1.5/terraform_1.1.5_linux_amd64.zip
unzip terraform_1.1.5_linux_amd64.zip
sudo mv terraform /usr/local/bin/
