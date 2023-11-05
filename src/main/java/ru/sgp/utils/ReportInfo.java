package ru.sgp.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportInfo {
    public String reportFile;
    public File outputFile;
    public Map<String, Object> parameters;
    public Connection connection;
    public HttpServletResponse response;
    public InputStream inputStream;
    public boolean preview = false;

    public ReportInfo(boolean preview, String reportFile, File outputFile) {
        this.preview = preview;
        this.reportFile = reportFile;
        this.outputFile = outputFile;
    }

    public void addParameter(String key, Object value) {
        if (parameters == null)
            parameters = new HashMap<>();

        parameters.put(key, value);
    }

    public Object getOrDefault(String key, String defaultValue) {
        return parameters.getOrDefault(key, defaultValue);
    }

}
