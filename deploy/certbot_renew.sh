#!/bin/sh

echo 'renew_hook = service splitguru restart' >> /etc/letsencrypt/renewal/splitguru.com.conf

# how to make it ????
# echo 'openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name tomcat -CAfile chain.pem -caname root' >> /etc/letsencrypt/renewal/splitguru.com.conf

# sudo certbot renew --dry-run

sudo service certbot restart

