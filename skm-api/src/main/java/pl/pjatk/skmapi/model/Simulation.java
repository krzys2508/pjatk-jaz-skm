package pl.pjatk.skmapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private List<Train> trains;
    Random rand = new Random();

    public List<Train> getTrains() {
        return trains;
    }

    public Simulation(int x, int y, int z) {
        this.trains = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            List<Section> sections = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                sections.add(new Section(z));
            }
            trains.add(new Train(sections));
        }
    }

    private String toJson(Object O) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(O);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String getJsonStringStatus() {
        return toJson(trains);
//        return "<pre>%s</pre>".formatted(toJson(trains)); //Debug
    }

    public void move() {
        for (Train train : trains) {
            // Load people to trains
            train.managePeople();

            // Move all trains
            train.nextStation();
        }
    }

    public String displayTrains() {
        List<Object> list = new ArrayList<>();
        trains.stream().forEach(train -> list.add(trains.indexOf(train)));
        return toJson(list);
    }

    public String displayTrain(@PathVariable int id) {
        try {
            return toJson(trains.get(id));
        } catch (IndexOutOfBoundsException e) {
            return toJson("Not found");
        }
    }
}