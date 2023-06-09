package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.exception.BadBowlingInputException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileProcessorTest {

  @Spy
  private FileProcessorImpl fileProcessor;
  @TempDir
  Path path;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetFileLines() throws IOException {
    File file = prepareTestFile();
    List<String> lines = fileProcessor.getFileLines(file.getAbsolutePath());
    Assertions.assertFalse(lines.isEmpty());
    Assertions.assertEquals(35, lines.size());
  }

  @Test
  void testGetFileLinesLaunchExc() {
    Assertions.assertThrows(BadBowlingInputException.class, () -> fileProcessor.getFileLines(null));
  }

  private File prepareTestFile() throws IOException {
    String filePath = path.toFile().getAbsolutePath();
    File targetFile = new File(filePath + File.separator + "sample.txt");
    try (InputStream stream = getClass().getResourceAsStream("/input/sample.txt")) {
      Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    return targetFile;
  }
}
