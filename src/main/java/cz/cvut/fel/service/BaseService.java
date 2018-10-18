package cz.cvut.fel.service;

import cz.cvut.fel.web.filter.BaseFilter;

import java.util.List;

public interface BaseService<Data, Filter extends BaseFilter> {

    long getRowCount(Filter filter);

    List<Data> getByFilter(Filter filter);

    Data getDataById(String id);
}
