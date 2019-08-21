package com.bootdo.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bootdo.common.domain.LogDO;
import com.bootdo.common.domain.PageDO;
import com.bootdo.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
	List<LogDO> list(Map<String, Object> map);
}
