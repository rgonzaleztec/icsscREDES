#!/usr/bin/env python
          
import time
import serial
import http.client
      
      
ser = serial.Serial(
              
	port='/dev/ttyAMA0',
	baudrate = 9600,
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=1
           )
counter=0
          
      
while 1:
         

        x=ser.readline()
        print (x)
        w=str(x,'utf-8') 
        print (w)
        a,b,c,d,e,f,g,h,i,j,k,l=w.split("/")

        print (a)
        print (b)
        print (c)
        print (d)
        print (e)
        print (f)
        print (g)
        print (h)
        print (i)
        print (j)
        print (k)
        print (l)

        conn = http.client.HTTPConnection("54.218.29.64")
        headers = { 'content-type': "application/json", 'cache-control': "no-cache", 'postman-token': "979ecfbc-e555-8c2a-ac2e-0fe23a30ea2e" }
        conn.request("POST", "/postMeasure/developmentStation"+"/"+ a +"/"+ b +"/"+ c +"/"+ d +"/"+ e +"/"+ f +"/"+ g +"/"+ h +"/"+ i +"/"+ j +"/"+ k +"/", headers=headers)       
        #conn.request("POST", "/postMeasure/developmentStation/1/2/3/4/5/6/7/8/9/10/11/", headers=headers)   



        
