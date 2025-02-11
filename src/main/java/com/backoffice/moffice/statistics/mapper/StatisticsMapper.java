package com.backoffice.moffice.statistics.mapper;

import com.backoffice.moffice.statistics.model.Statistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    List<Statistics> getStatistics(String periodType);
    List<Statistics> getStatisticsWith(String periodType);
}
