package org.example.mail.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String emailAddr;
    private String emailTitle;
    private String emailContent;
}
