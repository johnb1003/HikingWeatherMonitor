package com.johnburnsdev.hikingweathermonitor.client;

import com.twilio.Twilio;
import com.johnburnsdev.hikingweathermonitor.util.ConfigUtil;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class TwilioClient {

    private static final String TWILIO_ACCOUNT_SID_KEY = "TWILIO_ACCOUNT_SID";
    private static final String TWILIO_AUTH_TOKEN_KEY = "TWILIO_AUTH_TOKEN";
    private static final String TWILIO_MESSAGE_SERVICE_SID_KEY = "TWILIO_MESSAGE_SERVICE_SID";
    private static final String ALERT_PHONE_NUMBER_KEY = "ALERT_PHONE_NUMBER";

    private String twilioMessageServiceSid;
    private String alertPhoneNumber;

    public TwilioClient(ConfigUtil configUtil) {
        String twilioAccountSid = configUtil.getEnvironmentVariable(TWILIO_ACCOUNT_SID_KEY);
        String twilioAuthToken = configUtil.getEnvironmentVariable(TWILIO_AUTH_TOKEN_KEY);
        Twilio.init(twilioAccountSid, twilioAuthToken);

        twilioMessageServiceSid = configUtil.getEnvironmentVariable(TWILIO_MESSAGE_SERVICE_SID_KEY);
        alertPhoneNumber = configUtil.getEnvironmentVariable(ALERT_PHONE_NUMBER_KEY);
    }

    public void sendTextAlert(String alert) {
        log.finer("Publishing alert via Twilio:\n"+alert);
        Message message = Message.creator(new PhoneNumber(alertPhoneNumber),
                                            twilioMessageServiceSid, alert).create();

        if(message.getStatus().equals(Message.Status.FAILED)) {
            throw new RuntimeException("Failed to publish alert via Twilio:\n"+alert);
        }
    }
}
