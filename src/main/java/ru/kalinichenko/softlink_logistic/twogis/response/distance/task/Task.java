package ru.kalinichenko.softlink_logistic.twogis.response.distance.task;

import lombok.Data;

@Data
public class Task {
    private String task_id;
    private String status;
    private Integer code;
    private String message;
    private String result_link;
}
