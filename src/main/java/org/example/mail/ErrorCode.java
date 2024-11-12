package org.example.mail;

public enum ErrorCode {
    DUPLICATE_EMAIL("이미 등록된 이메일입니다."),
    SERVER_ERROR("서버 오류가 발생했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
