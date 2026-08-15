package com.kyc.core.model.notifications;

import com.kyc.core.model.BaseModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationData extends BaseModel {

    @NotNull
    @Pattern(regexp = "^[A-Za-z\\s\\d]{1,50}",message = "Invalid format")
    private String message;

    @NotNull
    @Pattern(regexp = "^[A-Z\\s\\d]{1,15}",message = "Invalid format")
    private String event;

    private LocalDateTime date = LocalDateTime.now();
}
