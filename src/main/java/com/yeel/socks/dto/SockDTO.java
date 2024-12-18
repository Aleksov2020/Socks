package com.yeel.socks.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SockDTO {
    @NotBlank(message = "Color cannot be blank")
    private String color;

    @Min(value = 0, message = "Cotton percentage must be < 0")
    @Max(value = 100, message = "Cotton percentage cannot be > 100")
    private int percentage;

    @Min(value = 1, message = "Count must be > 1")
    private int count;

    @Override
    public String toString() {
        return "color: " + color + " percentage: " + percentage + " count: " + count;
    }
}
