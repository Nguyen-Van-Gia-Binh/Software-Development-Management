package service;

import java.util.List;

public interface Iservice<T> {
    void addNew();
    void readFromFile(String fileName);
    void saveToFile(String fileName);
}
