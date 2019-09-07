package com.laodu.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO {
    private boolean hasFirstPage;
    private boolean hasLastPage;
    private boolean hasPrePage;
    private boolean hasNextPage;
    private int currentPage;
    private int lastPage;
    private List<Integer> showPage;
}
