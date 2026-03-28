FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    ansible \
    python3 \
    python3-pip \
    nmap \
    openssh-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

ENV ANSIBLE_STDOUT_CALLBACK=json
ENV ANSIBLE_HOST_KEY_CHECKING=False

CMD ["echo", "Conteiner pronto para uso!"]