package com.kyc.core.model.notifications;

import com.kyc.core.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class NotificationData extends BaseModel {

    @NotNull
    @Pattern(regexp = "^[A-Z\\s\\d]{1,50}",message = "Invalid format")
    private String message;

    @NotNull
    @Pattern(regexp = "^[A-Z\\s\\d]{1,15}",message = "Invalid format")
    private String event;

    private Date date;
}
