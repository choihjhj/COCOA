package com.cocoa.domain;

import lombok.Data;

@Data
public class EpisodeDTO {

	int epId;
	int toonId;
	int epNumber;
	String epTitle;
	int epStatus;
	int viewCnt;
	int price;

}
