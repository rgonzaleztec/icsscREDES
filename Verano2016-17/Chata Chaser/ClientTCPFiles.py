import socket               # Import socket module
import sys




s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 8080                # Reserve a port for your service.
server_address = ('127.0.0.1', port)
s.connect(server_address)


f = open('image.jpg','rb')
print 'Sending...'

s.sendall("4")
l = f.read(1024)
while (l):
    print 'Sending...'
    s.send(l)
    l = f.read(1024)
f.close()
print "Done Sending"
s.shutdown(socket.SHUT_WR)

    
s.close 
