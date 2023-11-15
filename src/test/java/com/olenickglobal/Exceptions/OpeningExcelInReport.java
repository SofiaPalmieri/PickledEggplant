package com.olenickglobal.Exceptions;

import java.io.IOException;

public class OpeningExcelInReport extends RuntimeException {
    public OpeningExcelInReport(Exception e) {
        super("Error occurred while loading into excel inside the RPVReporter. Check file integrity.");
    }
}
