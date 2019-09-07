package com.laodu.community.helper;

import com.laodu.community.dto.PaginationDTO;

import java.util.ArrayList;
import java.util.List;

public class PaginationHelper {

    public PaginationDTO getCurrentPageList(int currentPage, int total, int size) {

        int totalPage = (total % size == 0) ? total / size : total / size + 1;
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setLastPage(totalPage);
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPage) currentPage = totalPage;
        paginationDTO.setCurrentPage(currentPage);
        if (currentPage == totalPage) {
            paginationDTO.setHasLastPage(false);
            paginationDTO.setHasNextPage(false);
            paginationDTO.setHasFirstPage(true);
            paginationDTO.setHasPrePage(true);
        }
        if (currentPage == 1) {
            paginationDTO.setHasFirstPage(false);
            paginationDTO.setHasPrePage(false);
            paginationDTO.setHasLastPage(true);
            paginationDTO.setHasNextPage(true);
        }
        if (totalPage == 1 || (currentPage != 1 && currentPage != totalPage)) {
            paginationDTO.setHasFirstPage(false);
            paginationDTO.setHasPrePage(false);
            paginationDTO.setHasLastPage(false);
            paginationDTO.setHasNextPage(false);
        }
        if (currentPage != 1 && currentPage != totalPage) {
            paginationDTO.setHasFirstPage(true);
            paginationDTO.setHasPrePage(true);
            paginationDTO.setHasLastPage(true);
            paginationDTO.setHasNextPage(true);
        }
        List<Integer> pages = new ArrayList<>();
        pages.add(currentPage);
        for (int i = 1; i <= 3; i++) {
            if (currentPage - i > 0) {
                pages.add(0, currentPage - i);
            }
            if (currentPage + i <= totalPage) {
                pages.add(currentPage + i);
            }
        }
        paginationDTO.setShowPage(pages);
        return paginationDTO;
    }
}
