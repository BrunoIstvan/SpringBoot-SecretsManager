package br.com.bicmsystems.test_sm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;

@Configuration
@Order(1)
public class LocalStackConfig {

	//AWSCredentialsProvider credentialsProvider = null;
	
	public LocalStackConfig() {
		
		String secretName = "mysecret/db/test_sm";
		
		String endpoint = "http://localhost:4584";
        EndpointConfiguration config = new EndpointConfiguration(endpoint, "us-east-1");
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();
        String secret = null;
//        ByteBuffer binarySecretData;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResponse = null;
        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        } catch(ResourceNotFoundException e) {
            System.out.println("The requested secret " + secretName + " was not found");
        } catch (InvalidRequestException e) {
            System.out.println("The request was invalid due to: " + e.getMessage());
        } catch (InvalidParameterException e) {
            System.out.println("The request had invalid params: " + e.getMessage());
        }

        if(getSecretValueResponse == null) {
            return;
        }

        // Decrypted secret using the associated KMS CMK
        // Depending on whether the secret was a string or binary, one of these fields will be populated
        if(getSecretValueResponse.getSecretString() != null) {
            secret = getSecretValueResponse.getSecretString();
        }
//        else {
//            binarySecretData = getSecretValueResponse.getSecretBinary();
//        }
	
        System.out.println(secret);
        
	}
		
}
