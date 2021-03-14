package ru.sber.ReportGenerator;

import java.io.OutputStream;
import java.util.List;


public interface Report {

    byte[] asBytes();

    void writeTo(OutputStream os);
}