package br.com.bicmsystems.test_sm.config;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class DataSourceConfig {

	@Primary
	@Bean
	public DataSource getDataSource() throws JsonParseException, JsonMappingException, IOException {
		
		String secretName = "test_sm/secret/db";
		String endpoint = "http://localhost:4584";
        String region = "us-east-1";
        String secret = null;
		EndpointConfiguration config = new EndpointConfiguration(endpoint, region);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResponse = null;
        
        clientBuilder.setEndpointConfiguration(config);
        
        AWSSecretsManager client = clientBuilder.build();
        
        ByteBuffer binarySecretData = null;
        
        try {
        	
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        } catch(ResourceNotFoundException e) {
            
        	System.out.println("The requested secret " + secretName + " was not found");
            throw e;
        	
        } catch (InvalidRequestException e) {
        
        	System.out.println("The request was invalid due to: " + e.getMessage());
        	throw e;
        	
        } catch (InvalidParameterException e) {
        
        	System.out.println("The request had invalid params: " + e.getMessage());
        	throw e;
        	
        }

        if(getSecretValueResponse == null) {
            throw new DataSourceLookupFailureException("Datasource configuration not found");
        }

        // Decrypted secret using the associated KMS CMK
        // Depending on whether the secret was a string or binary, one of these fields will be populated
        if(getSecretValueResponse.getSecretString() != null) {
      
        	secret = getSecretValueResponse.getSecretString();
        
        }
        else {
        
        	binarySecretData = getSecretValueResponse.getSecretBinary();
        
        }
	
        System.out.println(secret);
        
        final ObjectMapper objectMapper = new ObjectMapper();

        final HashMap<String, String> secretMap = objectMapper.readValue(secret, HashMap.class);

        String url = String.format("jdbc:%s://%s:%s/%s", 
				secretMap.get("engine"),
				secretMap.get("host"),
				secretMap.get("port"),
				secretMap.get("dbInstanceIdentifier"));
        
        System.out.println("New URL: " + url);
        
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(secretMap.get("driverClassname"));
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(secretMap.get("username"));
        dataSourceBuilder.password(secretMap.get("password"));
        
        return dataSourceBuilder.build();
        
	}
		
}
