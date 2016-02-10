#!/bin/sh

#export HOSTNAME="mesos"
#echo $HOSTNAME | sudo tee /etc/hostname
#sudo hostname -F /etc/hostname



# Java repos
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EEA14886


# Marathon repos
DISTRO=$(lsb_release -is | tr '[:upper:]' '[:lower:]')
CODENAME=$(lsb_release -cs)
echo "deb http://repos.mesosphere.com/${DISTRO} ${CODENAME} main" | \
  sudo tee /etc/apt/sources.list.d/mesosphere.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv E56151BF
sudo apt-get -y update

sudo apt-get -y install oracle-java8-installer mesos marathon



# scala

wget http://downloads.typesafe.com/scala/2.10.6/scala-2.10.6.deb \
 && sudo dpkg -i scala-2.10.6.deb \
 && rm scala-2.10.6.deb


# kafka mesos

sudo apt-get -y install git
git clone git clone https://github.com/mesos/kafka kafka-mesos \
 && cd kafka-mesos \
 && git checkout v0.9.2.0 \
 && ./gradlew jar -xtest \
 && wget https://archive.apache.org/dist/kafka/0.8.2.1/kafka_2.10-0.8.2.1.tgz \
 && cd ..

export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
export LIBPROCESS_IP=127.0.0.1


./kafka-mesos.sh broker add 0 --heap 256 --mem 384 --cpus 0.1


# mesos-dns


curl -L -o /develop/mesos-dns https://github.com/mesosphere/mesos-dns/releases/download/v0.5.1/mesos-dns-v0.5.1-linux-amd64
chmod a+x /develop/mesos-dns

sudo sed -i '1s/^/nameserver clojure-stack\n /' /etc/resolv.conf

sudo service zookeeper restart
sudo service mesos start
sudo service marathon start


