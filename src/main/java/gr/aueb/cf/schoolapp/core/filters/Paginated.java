package gr.aueb.cf.schoolapp.core.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class Paginated<T> {

    List<T> data;
    long totalElements;
    int totalPages;
    int currentPage;
    int numberOfElements;
    int pageSize;

    public Paginated(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.pageSize = page.getSize();
    }
}
