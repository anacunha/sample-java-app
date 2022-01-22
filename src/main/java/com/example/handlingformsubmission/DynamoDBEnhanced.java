package com.example.handlingformsubmission;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Component("DynamoDBEnhanced")
public class DynamoDBEnhanced {

    public void injectDynamoItem(final Greeting greeting) {

        final Region region = Region.US_EAST_1;
        final AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                "AKIAIOSFODNN7EXAMPLE",
                "je7MtGbClwBF/2Zp9Utk/h3yCo8nvbEXAMPLEKEY");
        final DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        try {
            DynamoDbEnhancedClient dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(dynamoDbClient)
                    .build();

            final DynamoDbTable<GreetingItems> dynamoDbTable = dynamoDbEnhancedClient.table("Greeting", TableSchema.fromBean(GreetingItems.class));
            final GreetingItems greetingItems = new GreetingItems();
            greetingItems.setId(greeting.getId());
            greetingItems.setName(greeting.getName());
            greetingItems.setMessage(greeting.getBody());
            greetingItems.setTitle(greeting.getTitle());

            System.out.println(greetingItems);

            final PutItemEnhancedRequest<GreetingItems> request = PutItemEnhancedRequest.builder(GreetingItems.class)
                    .item(greetingItems)
                    .build();

            dynamoDbTable.putItem(request);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
