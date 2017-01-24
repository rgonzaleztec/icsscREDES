import sys
import numpy as np
import SWHear
import time
import picamera

def takePicture():
    camera = picamera.PiCamera()
    nombre=time.strftime("%d/%m/%Y")+"_"+time.strftime("%H:%M:%S")+".jpg"
    nombre=nombre.replace("/",'-').replace(":",'-')
    camera.capture(nombre)
    camera.close()

def update():
    x=1
    ear=SWHear.SWHear(rate=25000,updatesPerSecond=1)
    ear.stream_start()
    while 1:
        
        if not ear.data is None and not ear.fft is None:
            pcmMax=np.max(np.abs(ear.data))
            print pcmMax
            if pcmMax >= 18000:
                takePicture()
                time.sleep(4)
                #break

        time.sleep(1)
        


update() #start with something
print()
