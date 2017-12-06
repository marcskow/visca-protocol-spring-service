package com.hellokoding.springboot;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViscaResponse {

    public ViscaResponse() {
    }

    public byte[] readResponse(SerialPort serialPort) throws TimeoutException, SerialPortException {
        List<Byte> data = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        long var10;
        do {
            while(serialPort.getInputBufferBytesCount() != 0) {
                byte[] responseData = serialPort.readBytes(1);
                byte idx = responseData[0];
                data.add(idx);
                if(idx == -1) {
                    responseData = new byte[data.size()];
                    int var9 = 0;

                    Byte b;
                    for(Iterator var7 = data.iterator(); var7.hasNext(); responseData[var9++] = b) {
                        b = (Byte)var7.next();
                    }

                    return responseData;
                }
            }

            long var8 = System.currentTimeMillis();
            var10 = var8 - startTime;
        } while(var10 <= 5000L);

        throw new TimeoutException();
    }
}
