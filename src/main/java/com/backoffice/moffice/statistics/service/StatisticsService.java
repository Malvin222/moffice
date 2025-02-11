package com.backoffice.moffice.statistics.service;

import com.backoffice.moffice.statistics.mapper.StatisticsMapper;
import com.backoffice.moffice.statistics.model.Statistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final StatisticsMapper statisticsMapper;

    public List<Statistics> getStatistics(String periodType){
        return statisticsMapper.getStatistics(periodType);
    }

    public List<Statistics> getStatisticsWith(String periodType){
        return statisticsMapper.getStatisticsWith(periodType);
    }
}
