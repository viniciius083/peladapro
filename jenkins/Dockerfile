FROM jenkins/jenkins:lts

USER root

# Instala dependências básicas e Docker CLI
RUN apt-get update && \
    apt-get install -y \
        curl \
        gnupg \
        ca-certificates \
        lsb-release \
        docker.io \
        unzip && \
    apt-get clean

# Instala Docker Compose V2 manualmente
RUN curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose

# Garante que o grupo docker existe e adiciona o jenkins
RUN groupadd -f docker && usermod -aG docker jenkins

USER jenkins
