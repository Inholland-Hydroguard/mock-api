package hydroguard.api.mock.models;

import java.util.List;

public class GetMoreDTO<T> {
    private List<T> data;
    private int total;
    private int currentPage;

    public GetMoreDTO() {
    }

    public GetMoreDTO(List<T> data, int total, int currentPage) {
        this.data = data;
        this.total = total;
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return data.size();
    }
}