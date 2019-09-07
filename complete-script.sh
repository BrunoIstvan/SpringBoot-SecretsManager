#!/bin/sh

echo '>>>> Criando containers do SecretsManager e MySQL...'

docker-compose up

echo '<<<< Fim da criação dos containers do SecretsManager e MySQL...'

echo '>>>> Criando secret para acesso ao MySql'

awslocal secretsmanager create-secret --name test_sm/secret/db --secret-string file://mycreds.json

echo '<<<< Secret criado...'

echo '>>>> Criando container para rodar a API de test'

cd test-sm

docker build -t test-sm . && docker run -p 5000:8080 test-sm

cd ..

echo '<<<< Container criado...'

