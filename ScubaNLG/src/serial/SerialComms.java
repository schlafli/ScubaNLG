package serial;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialComms {
	
	private static SerialComms	instance;
	
	private SerialComms() {
		
	}
	
	public static boolean connect(String comm) throws SerialPortException,
			UnsupportedEncodingException {
		SerialPort port = new SerialPort(comm);
		
		port.openPort();
		port.writeString("Hello there!", "cp1252");
		port.closePort();
		
		return false;
	}
	
	public static void main(String args[]) throws Exception {
		Arrays.asList(SerialPortList.getPortNames()).stream()
				.forEach(System.out::println);
		// connect("COM5");
		
	}
}
