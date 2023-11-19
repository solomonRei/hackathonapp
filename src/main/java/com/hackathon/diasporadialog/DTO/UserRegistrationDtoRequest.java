package com.hackathon.diasporadialog.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDtoRequest {

    @NotBlank(message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    @Pattern(regexp = "[\\p{L}\\s]+", message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    @Size(max = 30, message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    private String name;

    @NotBlank(message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    @Pattern(regexp = "[\\p{L}\\s]+", message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    @Size(max = 30, message = "Only alphabetical characters are allowed, maximum length is 30 characters")
    private String surname;

    @NotBlank(message = "Invalid email. It should be like: 'example@email.com'")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email. It should be like: 'example@email.com'")
    private String email;

    @NotBlank(message = "Invalid password.Must be 5-10 characters, including symbols, upper- and lower-case letters." +
            "Should contain at least one digit,one upper case and one symbol")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_+\\-'\";.,])[A-Za-z\\d@$!%*?&#^()_+\\-'\";.,]{5,10}$",
            message = "Invalid password.Must be 5-10 characters, including symbols, upper- and lower-case letters." +
                    "Should contain at least one digit,one upper case and one symbol")
    private String password;

    @NotBlank(message = "Phone number must contain exactly 9 numeric characters(without +373).Starting with 0")
    @Pattern(regexp = "^0[67][0-9]{7}$", message = "Phone number must contain exactly 9 numeric characters(without +373).Starting with 0")
    private String phone;
}
