package com.accenture.test.springboot.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageOffsetAndSize implements Pageable {

    private int limit = 5;
    private int offset = 0;

    PageOffsetAndSize(){}

    PageOffsetAndSize(int limit, int offset){
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public int getPageNumber() {
        return this.offset/this.limit;
    }

    @Override
    public int getPageSize() {
        return this.limit;
    }

    @Override
    public long getOffset() {
        return this.offset;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
