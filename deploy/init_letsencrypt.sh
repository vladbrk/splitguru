#!/bin/bash

sudo apt update
sudo apt install -y software-properties-common

sudop apt install -y certbot
sudo ufw allow 80
sudo ufw allow 443

sudo certbot certonly --standalone --preferred-challenges http --email vladbrk@gmail.com -d splitguru.com

cd /etc/letsencrypt/live/splitguru.com
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root


