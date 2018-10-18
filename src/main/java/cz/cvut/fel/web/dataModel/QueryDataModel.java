package cz.cvut.fel.web.dataModel;

import cz.cvut.fel.service.BaseService;
import cz.cvut.fel.web.data.BaseData;
import cz.cvut.fel.web.filter.BaseFilter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class QueryDataModel<Data extends BaseData, Service extends BaseService<Data, Filter>, Filter extends BaseFilter> extends LazyDataModel<Data> {

    private final Service service;
    private final Filter filter;

    public QueryDataModel(Service service, Filter filter) {
        this.service = service;
        this.filter = filter;
    }

    @Override
    public Data getRowData(String rowKey) {
        return service.getDataById(rowKey);
    }


    @Override
    public Object getRowKey(Data data) {
        return data.getRowKey();
    }

    @Override
    public List<Data> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        filter.setFirst(first);
        filter.setPageSize(pageSize);
        filter.setSortField(sortField);
        filter.setSortOrder(sortOrder);
        this.setPageSize(pageSize);
        List<Data> data = service.getByFilter(filter);
        this.setRowCount((int) service.getRowCount(filter));
        this.setWrappedData(data);
        return data;
    }
}
