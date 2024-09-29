package com.example.UrlShortner.URLshortner.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
    public  String message;
    public  boolean status;
    public String encodedUrl;
}
