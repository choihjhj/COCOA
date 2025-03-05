package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.EpisodeDTO;

public interface EpisodeService {
	public List<EpisodeDTO> getEpisodesBytoonId(int toonId); //toondetail에서 toonid에 해당하는 에피소드 목록 내림차순 조회
	public EpisodeDTO getEpisode(int epId); //epid에 해당하는 에피소드 조회
	public List<Integer> getEpidsByToonId(int toonId); //toonid에 해당하는 epid 오름차순 조회
}
