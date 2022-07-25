package com.example.accidentsRS.converter.impl;

import com.example.accidentsRS.converter.FilterConverter;
import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.model.filter.FilterWrapperModel;
import com.example.accidentsRS.model.filter.OperationEnum;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class DefaultFilterConverter implements FilterConverter {

    private static final String FILTER_DATE_FORMAT = "dd/MM/yyyy";
    private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");

    protected boolean isDate(final Object value) {
        return value instanceof String && DATE_PATTERN.matcher((String) value).find();
    }

    protected void processDateFilterValue(final FilterWrapperData filterWrapperData,
                                          final FilterWrapperModel filterWrapperModel) {
        try {
            Date inputDate = new SimpleDateFormat(FILTER_DATE_FORMAT).parse((String) filterWrapperData.getValue());
            filterWrapperModel.setValue(inputDate);
        } catch (ParseException parseException) {
            // Should never happen as we check before parsing
        }
    }

    @Override
    public FilterWrapperModel convert(FilterWrapperData filterWrapperData) {
        FilterWrapperModel filterWrapperModel = new FilterWrapperModel();
        filterWrapperModel.setFields(filterWrapperData.getFields());
        filterWrapperModel.setOperation(OperationEnum.valueOf(filterWrapperData.getOperator()));
        if (isDate(filterWrapperData.getValue())) {
            processDateFilterValue(filterWrapperData, filterWrapperModel);
        } else {
            filterWrapperModel.setValue(filterWrapperData.getValue());
        }
        return filterWrapperModel;
    }

    @Override
    public List<FilterWrapperModel> convertAll(List<FilterWrapperData> filterWrapperDataList) {
        List<FilterWrapperModel> filterWrapperModelList = new ArrayList<>();
        for (FilterWrapperData filterWrapperData : filterWrapperDataList) {
            filterWrapperModelList.add(convert(filterWrapperData));
        }
        return filterWrapperModelList;
    }
}
