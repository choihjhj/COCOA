package com.cocoa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.mapper.EpisodeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {
	

	private final EpisodeMapper mapper;

	@Override
	public List<EpisodeDTO> findBytoonId(int toonId) {

		return mapper.selectByToonId(toonId);
	}
	
	@Override
	public EpisodeDTO getEpisode(int epId) {
		return mapper.selectByEpId(epId);
	}
	
	@Override
	public List<Integer> findMinMaxEpBytoonId(int toonId) {
		return mapper.selectEpidByToonId(toonId);
	}

}