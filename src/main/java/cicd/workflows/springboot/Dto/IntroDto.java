package cicd.workflows.springboot.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IntroDto {

    @JsonProperty("date")
    private LocalDate localDate;

    private String intro;
}
