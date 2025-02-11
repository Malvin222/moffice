package com.backoffice.moffice.statistics.controller;

import com.backoffice.moffice.statistics.model.Statistics;
import com.backoffice.moffice.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatisticsApiController {
    private final StatisticsService statisticsService;

    @GetMapping("api/statistics")
    public List<Statistics> getStatisticsData(
            @RequestParam(required = false, defaultValue = "daily") String periodType){
        return statisticsService.getStatistics(periodType);
    }

    @GetMapping("api/statistics_with")
    public List<Statistics> getStatisticsDataWith(
            @RequestParam(required = false, defaultValue = "daily") String periodType){
        return statisticsService.getStatisticsWith(periodType);
    }
}
