package com.jobsity.bowling.components.parser;

import java.util.List;

public interface FileProcessor {

    List<String> getFileLines(String filePath);
}
