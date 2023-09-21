package cicd.workflows.springboot.Controllers;

import cicd.workflows.springboot.Dto.IntroDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController("/api")
public class SimpleController {

    @GetMapping("/intro")
    public ResponseEntity<IntroDto> getIntro() {
        IntroDto intro = new IntroDto(LocalDate.now(), "This is me, Mario!");
        return new ResponseEntity<>(introDto, HttpStatus.OK);
    }

    @GetMapping("/question")
    public ResponseEntity<IntroDto> getQuestion() {
        IntroDto question = new IntroDto(LocalDate.now(), "How are you? Long time no see!");
        return new ResponseEntity<>(introDto, HttpStatus.OK);
    }
}
