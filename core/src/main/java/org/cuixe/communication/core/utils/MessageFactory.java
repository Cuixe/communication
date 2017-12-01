package org.cuixe.communication.core.utils;

import com.sun.org.apache.regexp.internal.RE;
import org.cuixe.communication.core.Messaging;

public class MessageFactory {

    private static Messaging.Message.Builder message = Messaging.Message.newBuilder();
    private static Messaging.Header.Builder header = Messaging.Header.newBuilder();
    private static Messaging.Test.Builder test = Messaging.Test.newBuilder();
    private static Messaging.PingRequest.Builder pingRequest = Messaging.PingRequest.newBuilder();
    private static Messaging.PingResponse.Builder pingResponse = Messaging.PingResponse.newBuilder();

    public static Messaging.Message getTest(int side) {
        test.clear();
        setBasicMessage(Messaging.Type.TEST);
        test.setSide(side);
        message.setTest(test);
        return message.build();
    }

    public static Messaging.Message getPingRequest(int attempt) {
        pingRequest.clear();
        setBasicMessage(Messaging.Type.PING_REQUEST);
        pingRequest.setAttempt(attempt);
        message.setPingRequest(pingRequest);
        return message.build();
    }

    public static Messaging.Message getPingResponse(int attempt) {
        pingResponse.clear();
        setBasicMessage(Messaging.Type.PING_REQUEST);
        pingResponse.setAttempt(attempt);
        message.setPingResponse(pingResponse);
        return message.build();
    }


    private static void setBasicMessage(Messaging.Type type) {
        cleanBuilders();
        header.setType(type);
        header.setSendingTime(System.currentTimeMillis());
        message.setHeader(header);
    }

    private static void cleanBuilders() {
        header.clear();
        message.clear();
    }
}
