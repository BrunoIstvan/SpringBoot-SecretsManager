#!/bin/sh

echo '>>>> Antes de criar o secret usando secretsmanager'

awslocal secretsmanager create-secret --name test_sm/secret/db --secret-string file://mycreds.json

echo '<<<< Depois de criar o secret usando secretsmanager'
