package com.robintegg.htmx.demo.contacts;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public record Contact(Integer id, @Length(min = 3) String first, String last, String phone, @Email String email) {
}
