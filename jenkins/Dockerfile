FROM jenkins/jenkins:lts

USER root

# Instala Docker CLI + Docker Compose V2
RUN apt-get update && \
    apt-get install -y curl gnupg2 lsb-release docker.io docker-compose-plugin && \
    apt-get clean

# Adiciona o usuário Jenkins ao grupo do Docker
RUN usermod -aG docker jenkins

USER jenkins
