package com.talentreef.interviewquestions.takehome.models;

import java.util.List;

/**
 * This model class represents a subset of widgets used for pagination.
 */
public class PaginatedResult {
    private List<Widget> widgets;
    private int total;

    public PaginatedResult(List<Widget> widgets, int total) {
        this.widgets = widgets;
        this.total = total;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public int getTotal() {
        return total;
    }
}