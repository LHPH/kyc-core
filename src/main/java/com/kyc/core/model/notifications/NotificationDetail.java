package com.kyc.core.model.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NotificationDetail extends NotificationData{

    private String issuer;
    private String recipient;
    private String channel;

    public NotificationDetail(NotificationData notificationData){
        super(notificationData.getMessage(), notificationData.getEvent(),notificationData.getDate());
    }
}
