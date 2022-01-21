package com.aws.example;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScanFriends {

    public boolean sendFriendMessage() {
        boolean send = false;

        String DATE_FORMAT = "MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern(DATE_FORMAT);

        Date currentDate = new Date();
        System.out.println("date : " + dateFormat.format(currentDate));
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime : " + dateFormat8.format(localDateTime));

        String date = dateFormat8.format(localDateTime);
        System.out.println(date);

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();

        try {
            Map<String, String> expressionAttributeNames = new HashMap<>();
            expressionAttributeNames.put("#birthday", "birthday");

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":val", AttributeValue.builder().s(date).build());

            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName("Friend")
                    .filterExpression("#birthday = :val")
                    .expressionAttributeNames(expressionAttributeNames)
                    .expressionAttributeValues(expressionAttributeValues)
                    .limit(10)
                    .build();

            Iterator<Map<String, AttributeValue>> friends = dynamoDbClient.scan(scanRequest).items().iterator();

            while (friends.hasNext()) {
                Map<String, AttributeValue> friend = friends.next();
                String firstName = friend.get("firstName").s();
                String phone = friend.get("phone").s();

                SnsClient snsClient = SnsClient.builder()
                        .region(Region.US_EAST_1)
                        .build();

                String message = "Happy birthday, " + firstName + "! \uD83C\uDF88";

                try {
                    PublishRequest publishRequest = PublishRequest.builder()
                            .message(message)
                            .phoneNumber(phone)
                            .build();

                    snsClient.publish(publishRequest);
                } catch (SnsException e) {
                    System.err.println(e.awsErrorDetails().errorMessage());
                    System.exit(1);
                }
                send = true;
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return send;
    }
}
