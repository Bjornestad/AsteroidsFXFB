package dk.sdu.mmmi.cbse.scoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoringSystemApplication {

    private int score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoringSystemApplication.class, args);
    }

    @GetMapping("/score")
    public int calcScore(@RequestParam(value = "amount") int amount) {
        score += amount;
        System.out.println("Score: " + score);
        return score;
    }
    @GetMapping("/getScore")
    public int getScore() {
        return score;
    }
    @GetMapping("/resetScore")
    public int resetScore() {
        score = 0;
        return score;
    }

}
