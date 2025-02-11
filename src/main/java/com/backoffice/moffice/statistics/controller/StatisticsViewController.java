package com.backoffice.moffice.statistics.controller;

import com.backoffice.moffice.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class StatisticsViewController {

    private final StatisticsService statisticsService;

    @GetMapping("statistics")
    public String getStatistics(){
        return "statistics/statistics";
    }
}
