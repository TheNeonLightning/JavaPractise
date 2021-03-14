package ru.sber.ReportGenerator;

import java.io.OutputStream;
import java.util.List;


public interface ReportGenerator<T> {

    Report generate(List<T> entities);
}