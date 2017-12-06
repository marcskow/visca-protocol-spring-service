package pl.edu.agh.visca;

import jssc.SerialPort;
import jssc.SerialPortException;
import pl.edu.agh.kis.visca.ViscaResponseReader;
import pl.edu.agh.kis.visca.cmd.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ViscaController {

    private String portNumber = "COM1";
    public static final String PORT_NUMBER = "COM4";

    private SerialPort serialPort;

    public ViscaController(String portNumber) {
        this.portNumber = portNumber;
    }

    public void open() {
        serialPort = new SerialPort(portNumber);
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void handleCommandInLoop() {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Please enter the command: ");
            String command = sc.nextLine();
            String[] singleCommand = command.split(",");
            for(int i = 0 ; i < singleCommand.length; i++) {
                String[] cmd = singleCommand[i].split(" ");
                String comm = cmd[0];
                int val = 0;
                if (cmd.length >1){
                    val = Integer.parseInt(cmd[1]);
                }
                System.out.println("Perform: " + comm + " with speed: " + val);
                performComplexCommand(comm, val, serialPort);

                int n = 0;
                try {
                    while(true) {
                        try {
                            ViscaResponse viscaResponse = new ViscaResponse();
                            byte[] response = new byte[0];
                            response = viscaResponse.readResponse(serialPort);
                            String hexa = bytesToHex(response);
                            System.out.print("> ");
                            for(int j = 0; j < response.length + 2; j+=2) {
                                System.out.print(" ");
                                System.out.print(hexa.substring(j, j+2));
                            }
                            System.out.println();
                            n++;
                            if(n == 2) {
                                break;
                            }
                        } catch (TimeoutException e) {
                            break;
                        }
                    }
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
//                System.out.println("> " + bytesToHex(response));

                try {
                    Thread.sleep(500); // było 1500
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*

       byte[] cmdData = (new AddressCmd()).createCommandData();
        ViscaCommand command = new ViscaCommand();
        command.commandData = cmdData;
        command.sourceAdr = 0;
        command.destinationAdr = 8;
        cmdData = command.getCommandData();

        System.out.println("@ " + byteArrayToString(cmdData));
        try {
            port.writeBytes(cmdData);
            ViscaResponse viscaResponse = new ViscaResponse();
            byte[] response = viscaResponse.readResponse(port);
            System.out.println("> " + byteArrayToString(response));
        } catch (TimeoutException | SerialPortException var17) {
            System.out.println("! TIMEOUT exception");
        }
     */

    public static void main(String[] args) {
        ViscaController viscaController = new ViscaController(PORT_NUMBER);
        viscaController.open();
        viscaController.handleCommandInLoop();
    }

    public void performComplexCommand(String cmd, int val, SerialPort port){
        switch (cmd){
            case ("clear"):
                performClearCommand(port);
                break;
            case ("address"):
                sendAddress(port);
                break;
            case ("left"):
                moveLeft(port,val);
                break;
            case ("right"):
                moveRight(port,val);
                break;
            case ("up"):
                moveUp(port,val);
                break;
            case ("down"):
                moveDown(port,val);
                break;
            case ("home"):
                moveHome(port);
                break;
            case ("absolute"):
                moveAbsolute(port,val);
                break;
            case ("wait"):
                int msec = val*1000;
                try {
                    Thread.sleep(msec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Something goes wrong");
        }
    }

    private void performMovementCommand(byte[] cmdData, SerialPort port, int movementSpeed){
        //set camera speed
        cmdData[3] = (byte) movementSpeed;

        //create Visca command
        ViscaCommand cmd = new ViscaCommand();
        cmd.commandData = cmdData;
        cmd.sourceAdr = 0;
        cmd.destinationAdr = 1;

        //get command data for port
        cmdData = cmd.getCommandData();
        try {
            port.writeBytes(cmdData);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    private void performClearCommand(SerialPort port){
        byte[] cmdData = (new ClearAllCmd()).createCommandData();

        //create Visca command
        ViscaCommand cmd = new ViscaCommand();
        cmd.commandData = cmdData;
        cmd.sourceAdr = 0;
        cmd.destinationAdr = 1;

        //get command data for port
        cmdData = cmd.getCommandData();
        try {
            port.writeBytes(cmdData);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
//--------------------MOVEMENT-COMMANDS------------------------
    private void moveRight(SerialPort port, int movementSpeec) {
        byte[] cmdData = (new PanTiltRightCmd()).createCommandData();
        performMovementCommand(cmdData,port, movementSpeec);
    }

    public void moveLeft(SerialPort port, int movementSpeed){
        byte[] cmdData = new PanTiltLeftCmd().createCommandData();
        performMovementCommand(cmdData,port, movementSpeed);
    }

    public void moveUp(SerialPort port, int movementSpeed){
        byte[] cmdData = new PanTiltUpCmd().createCommandData();
        performMovementCommand(cmdData,port, movementSpeed);
    }

    public void moveDown(SerialPort port, int movementSpeed){
        byte[] cmdData = new PanTiltDownCmd().createCommandData();
        performMovementCommand(cmdData,port, movementSpeed);
    }

    public void moveAbsolute(SerialPort port, int movementSpeed){
        byte[] cmdData = new PanTiltAbsolutePosCmd().createCommandData();
        performMovementCommand(cmdData,port, movementSpeed);
    }

    public void moveHome(SerialPort port){
        byte[] cmdData = new PanTiltHomeCmd().createCommandData();

        //create Visca command
        ViscaCommand cmd = new ViscaCommand();
        cmd.commandData = cmdData;
        cmd.sourceAdr = 0;
        cmd.destinationAdr = 1;

        //get command data for port
        cmdData = cmd.getCommandData();
        try {
            port.writeBytes(cmdData);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    /*

    byte[] cmdData = (new AddressCmd()).createCommandData();
        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;
        vCmd.destinationAdr = 8;
        cmdData = vCmd.getCommandData();
        System.out.println("@ " + byteArrayToString(cmdData));
        serialPort.writeBytes(cmdData);
     */


    public void sendAddress(SerialPort port) {
        byte[] cmdData = (new AddressCmd()).createCommandData();
        ViscaCommand command = new ViscaCommand();
        command.commandData = cmdData;
        command.sourceAdr = 0;
        command.destinationAdr = 8;
        cmdData = command.getCommandData();

        System.out.println("@ " + byteArrayToString(cmdData));
        try {
            port.writeBytes(cmdData);
            ViscaResponse viscaResponse = new ViscaResponse();
            byte[] response = viscaResponse.readResponse(port);
            System.out.println("> " + byteArrayToString(response));
        } catch (TimeoutException | SerialPortException var17) {
            System.out.println("! TIMEOUT exception");
        }
    }

    private String byteArrayToString(byte[] bytes) {
        return new String(bytes);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
