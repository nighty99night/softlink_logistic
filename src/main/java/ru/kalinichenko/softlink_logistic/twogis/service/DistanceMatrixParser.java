package ru.kalinichenko.softlink_logistic.twogis.service;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.DistanceMatrix;
import ru.kalinichenko.softlink_logistic.twogis.request.DistanceApiRequest;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.task.Task;
import ru.kalinichenko.softlink_logistic.logic.distance.Point;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
@PropertySource(value = "classpath:2gis.properties")
@Log4j2
public class DistanceMatrixParser {

    @Value("${2gis.api.nav.key}")
    private String KEY;
    private final String CREATE_TASK_URL = "https://routing.api.2gis.com/async_matrix/create_task/get_dist_matrix?key={key}&version=2.0";
    private final String GET_TASK_URL = "https://routing.api.2gis.com/async_matrix/result/get_dist_matrix/{task_id}?key={key}";
    private final RestTemplate template;

    public DistanceMatrixParser(RestTemplate template) {
        this.template = template;
    }

    public DistanceMatrix parse(Point point1, Point point2){
        DistanceApiRequest distanceApiRequest = new DistanceApiRequest();
        distanceApiRequest.setPoints(new Point[]{point1, point2});
        distanceApiRequest.setSources(new Integer[]{0});
        distanceApiRequest.setTargets(new Integer[]{1});
        distanceApiRequest.setStartTime(new Date().toString());
        distanceApiRequest.setAllow_locked_roads(true);

        ResponseEntity<Task> createTaskResponse = template.postForEntity(CREATE_TASK_URL,distanceApiRequest, Task.class, Map.of("key", KEY));

        ResponseEntity<Task> getTaskResponse = template.getForEntity(GET_TASK_URL, Task.class, Map.of("task_id",
                Objects.requireNonNull(createTaskResponse.getBody()).getTask_id(), "key",KEY));

        while (!Objects.requireNonNull(getTaskResponse.getBody()).getStatus().equals("TASK_DONE")){
            getTaskResponse = template.getForEntity(GET_TASK_URL, Task.class, Map.of("task_id",
                    Objects.requireNonNull(createTaskResponse.getBody()).getTask_id(), "key",KEY));
        }

        ResponseEntity<String> resultTask = template.getForEntity(getTaskResponse.getBody().getResult_link(), String.class);

        log.info("Distance calculation: " + resultTask);
        JSONObject jsonObject = new JSONObject(resultTask.getBody());

        JSONObject route = jsonObject.getJSONArray("routes").getJSONObject(0);

        DistanceMatrix distanceMatrix = new DistanceMatrix();
        distanceMatrix.setDistance(route.getLong("distance"));
        distanceMatrix.setDuration(route.getLong("duration"));
        return distanceMatrix;
    }
}
