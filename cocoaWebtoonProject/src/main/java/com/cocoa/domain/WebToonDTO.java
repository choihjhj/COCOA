package com.cocoa.domain;

import lombok.Data;


@Data
public class WebToonDTO {
    private int toonId; 
    private int toonStatus;
    private String toonName;
    private String author;
    private String summary;
    private int dayOfWeek;
    
}