package com.cocoa.mapper;

import java.util.List;

import com.cocoa.domain.EpisodeDTO;

public interface EpisodeMapper {
	public List<EpisodeDTO> selectByToonId(int toodId);
	public EpisodeDTO selectByEpId(int epId);
	public List<Integer> selectEpidByToonId(int toodId); //최소ep


}
