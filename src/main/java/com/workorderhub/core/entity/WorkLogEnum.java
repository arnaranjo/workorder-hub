package com.workorderhub.core.entity;

public enum WorkLogEnum {
    OPEN_ORDER("New Work Order Open"),
    START_ORDER("Work Order in progress"),
    COMMENT_ORDER("Comments from the technician"),
    EDIT_SPEAR_SPARES("Spare parts list edited"),
    CLOSE_ORDER("Work Order closed");

    private final String logComment;

    WorkLogEnum(String logComment) {
        this.logComment = logComment;
    }

    public String GetLogComment() {
        return this.logComment;
    }
}
