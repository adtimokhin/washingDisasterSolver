package com.adtimokhin.util.backup.files;

import java.io.IOException;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

public interface FileWorker {
    void openConnection(String filename) throws IOException;
    void closeConnection() throws IOException;
}
