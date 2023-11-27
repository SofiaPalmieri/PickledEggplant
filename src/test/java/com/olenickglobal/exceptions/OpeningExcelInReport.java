package com.olenickglobal.exceptions;

public class OpeningExcelInReport extends RuntimeException {
    public OpeningExcelInReport(Exception e) {
        super("Error occurred while loading into excel inside the RPVReporter. Check file integrity.", e);
    }
}
