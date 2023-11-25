package com.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RevenueOneAndTwoMonthsAgo {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RevenueOneAndTwoMonthsAgo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Integer>StatisticalPeopleSubscribe(int company_input){
        String sql = "CALL social_travel.statistical_people_subrice(?)";
        Map<String,Object> result = jdbcTemplate.queryForMap(sql,company_input);
        Map<String, Integer> resultStatic = new HashMap<>();
        resultStatic.put("registered_currentMonth", (Integer) result.get("registered_currentMonth"));
        resultStatic.put("registered_LastMonthAgo", (Integer) result.get("registered_LastMonthAgo"));
        return resultStatic;
    }

    public Map<String, Integer>StatisticalPeopleSubscribeBy12Month(int company_input){
        String sql = "CALL social_travel.statistical_people_subrice_by_12Months(?)";
        Map<String,Object> result = jdbcTemplate.queryForMap(sql,company_input);

        Map<String, Integer> resultStatic = new HashMap<>();
        resultStatic.put("registered_January", (Integer) result.get("registered_January"));
        resultStatic.put("registered_February", (Integer) result.get("registered_February"));
        resultStatic.put("registered_April", (Integer) result.get("registered_April"));
        resultStatic.put("registered_March", (Integer) result.get("registered_March"));
        resultStatic.put("registered_May", (Integer) result.get("registered_May"));
        resultStatic.put("registered_June", (Integer) result.get("registered_June"));
        resultStatic.put("registered_July", (Integer) result.get("registered_July"));
        resultStatic.put("registered_August", (Integer) result.get("registered_August"));
        resultStatic.put("registered_September", (Integer) result.get("registered_September"));
        resultStatic.put("registered_October", (Integer) result.get("registered_October"));
        resultStatic.put("registered_November", (Integer) result.get("registered_November"));
        resultStatic.put("registered_December", (Integer) result.get("registered_December"));
        return resultStatic;
    }

    public Map<String, BigDecimal>statistical_revenue_by_12_months(int company_input){
        String sql = "CALL social_travel.statistical_revenue_by_12_months(?)";
        Map<String,Object> result = jdbcTemplate.queryForMap(sql,company_input);

        Map<String, BigDecimal> resultStatic = new HashMap<>();
        resultStatic.put("statistical_January", (BigDecimal) result.get("statistical_January"));
        resultStatic.put("statistical_February", (BigDecimal) result.get("statistical_February"));
        resultStatic.put("statistical_March", (BigDecimal) result.get("statistical_March"));
        resultStatic.put("statistical_April", (BigDecimal) result.get("statistical_April"));
        resultStatic.put("statistical_May", (BigDecimal) result.get("statistical_May"));
        resultStatic.put("statistical_June", (BigDecimal) result.get("statistical_June"));
        resultStatic.put("statistical_July", (BigDecimal) result.get("statistical_July"));
        resultStatic.put("statistical_August", (BigDecimal) result.get("statistical_August"));
        resultStatic.put("statistical_September", (BigDecimal) result.get("statistical_September"));
        resultStatic.put("statistical_October", (BigDecimal) result.get("statistical_October"));
        resultStatic.put("statistical_November", (BigDecimal) result.get("statistical_November"));
        resultStatic.put("statistical_December", (BigDecimal) result.get("statistical_December"));
        return resultStatic;
    }

    public Map<String, BigDecimal> callStatisticalRevenueProcedure(int companyId1) {
        String sql = "CALL statistical_revenue_by_oneMonthAgo_and_twoMonthsAgo(?)";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, companyId1);

        Map<String, BigDecimal> statisticalData = new HashMap<>();
        statisticalData.put("statistical_oneMonthAgo", (BigDecimal) result.get("statistical_oneMonthAgo"));
        statisticalData.put("statistical_twoMonthAgo", (BigDecimal) result.get("statistical_twoMonthAgo"));

        return statisticalData;
    }
    public List<Map<String, Object>> callStatisticalRevenuePerTour(int companyId1) {
        String sql = "CALL statistical_revenue_per_tour(?)";
        try {
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, companyId1);
            return result;
        }catch (Exception e ){
            return Collections.emptyList();
        }
    }
    public List<Map<String,Object>> calltotalInterac() {
        String sql = "CALL total_interact()";

        try {
            List<Map<String,Object>> results = jdbcTemplate.queryForList(sql);
            return results;
        }catch (Exception e ){
            return Collections.emptyList();
        }
    }


}
