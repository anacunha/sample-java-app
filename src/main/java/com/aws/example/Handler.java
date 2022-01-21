package com.aws.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Handler {

    public void handleRequest(Context context) {
        LambdaLogger logger = context.getLogger();
        ScanFriends scanFriends = new ScanFriends();
        boolean answer = scanFriends.sendFriendMessage();
        if (answer) {
            System.out.println("Messages sent: " + answer);
        }
    }
}
