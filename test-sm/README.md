### Criando containers do SecretsManager e MySQL...

Executar comando em um terminal...

```bash
docker-compose up
```


Para criar o secret de acesso ao banco de dados... Executar o comando abaixo em uma nova janela...

```bash
awslocal secretsmanager create-secret --name test_sm/secret/db --secret-string file://mycreds.json
```

Para criar o container de API, digitar o comando abaixo em uma nova janela do terminal...

```bash
docker build -t test-sm test-sm/. && docker run -p 5000:8080 test-sm
```


