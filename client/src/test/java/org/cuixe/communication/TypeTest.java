package org.cuixe.communication;

import org.cuixe.communication.core.Messaging;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypeTest {

    @Test
    public void prebuild() {
        Messaging.Message.Builder message = Messaging.Message.newBuilder();
        Messaging.Header.Builder header = Messaging.Header.newBuilder();
        Messaging.PingRequest.Builder ping = Messaging.PingRequest.newBuilder();

        //ping.clear();

        ping.setAttempt(100);
        header.setSendingTime(1234567890);

        message.setHeader(header);
        message.setPingRequest(ping);

        Messaging.Message message1 = message.build();
        Assert.assertEquals(100, message1.getPingRequest().getAttempt());
        Assert.assertEquals(1234567890, message1.getHeader().getSendingTime());
    }

}