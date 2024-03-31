package com.example.accounts.utils.results;

import com.example.accounts.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Getter
public class Result<T extends BaseEntity> {
    private boolean isSuccess;
    private String message;
    private T data;

    Result(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    Result(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    Result(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }




    public static class Success<T extends BaseEntity> extends Result<T> {
        public Success() {
            super(true);
        }

        public Success(String message) {
            super(true, message);
        }

        public Success(T data) {
            super(true, data);
        }

        public Success(String message, T data) {
            super(true, message, data);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class Failure<T extends BaseEntity> extends Result<T> {
        public Failure() {
            super(false);
        }

        public Failure(String message) {
            super(false, message);
        }

        public Failure(T data) {
            super(false, data);
        }

        public Failure(String message, T data) {
            super(false, message, data);

        }
    }
}
