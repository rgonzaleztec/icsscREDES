import socket               # Import socket module
import os
import datetime
import pytz

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)# Create a socket object
host = socket.gethostname() # Get local machine name
port = 8080
server_address = ('127.0.0.1', port)# Reserve a port for your service.
s.bind(server_address)
s.listen(5)  


    
while True:
    c, addr = s.accept()     # Establish connection with client.
    print 'Got connection from', addr
    
    print "Receiving..."
    f = open('prueba.jpg','wb')
    l = c.recv(1024)
    while (l):
        print "Receiving..."
        f.write(l)
        l = c.recv(1024)
    f.close()
    print "Done Receiving"
        
    #c.send('Thank you for connecting')
    c.close()
    





    

