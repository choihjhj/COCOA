package com.cocoa.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.mapper.EpisodeMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {
	

	private final EpisodeMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public List<EpisodeDTO> findBytoonId(int toonId) {

		return mapper.selectByToonId(toonId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EpisodeDTO getEpisode(int epId) {
		return mapper.selectByEpId(epId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Integer> findMinMaxEpBytoonId(int toonId) {
		return mapper.selectEpidByToonId(toonId);
	}

}