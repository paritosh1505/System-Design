package com.example.UrlShortner.URLshortner.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="urlentry")
public class UrlEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalurl;
    @Column(nullable = false,unique = true)
    private String encryptedurl;
    @Column(nullable = false,unique=true)
    private String encodedentry;
    private int clickcount = 5;
    private LocalDateTime createat;
    private LocalDateTime updatedat;
    private boolean isExpired=false;


}
