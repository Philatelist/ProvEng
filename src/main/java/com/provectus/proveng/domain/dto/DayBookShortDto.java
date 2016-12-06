package com.provectus.proveng.domain.dto;

import com.provectus.proveng.domain.DayBook;

import java.util.Date;

public class DayBookShortDto {
    private Long id;
    private int mark;
    private String type;
    private Date markDate;

    public static DayBookShortDto convertToDto(DayBook dayBook) {
        DayBookShortDto dayBookDto = new DayBookShortDto();
        dayBookDto.setId(dayBook.getId());
        dayBookDto.setMark(dayBook.getMark());
        dayBookDto.setMarkDate(dayBook.getMarkDate());
        dayBookDto.setType(dayBook.getType());

        return dayBookDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getMarkDate() {
        return markDate;
    }

    public void setMarkDate(Date markDate) {
        this.markDate = markDate;
    }
}
