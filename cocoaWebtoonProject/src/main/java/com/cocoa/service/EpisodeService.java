package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.EpisodeDTO;

public interface EpisodeService {
	public List<EpisodeDTO> findBytoonId(int toonId);
	public EpisodeDTO getEpisode(int epId);
	public List<Integer> findMinMaxEpBytoonId(int toonId);
}
