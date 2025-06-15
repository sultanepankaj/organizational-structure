package org.structure.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReadFileTest {
    @Mock
    ReadFile readFile;

    @Test
    void testReadAndMapFile() {
        readFile.readAndMapFile();
        Mockito.verify(readFile, Mockito.times(1)).readAndMapFile();
    }

}