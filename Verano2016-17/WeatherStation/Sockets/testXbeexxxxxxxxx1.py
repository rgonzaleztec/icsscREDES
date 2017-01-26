import XBee
from time import sleep
import binascii

if __name__ == "__main__":
    xbee = XBee.XBee("/dev/ttyS0")  # Your serial port name here

    # A simple string message
   # sent = xbee.SendStr("Hello World")


while True:
    Msg = xbee.Receive()
    mensaje = str(Msg)
    	
    if Msg:
	print(mensaje[3:5])
	print("MsgFFF: " + mensaje)
	sleep(1)
        
