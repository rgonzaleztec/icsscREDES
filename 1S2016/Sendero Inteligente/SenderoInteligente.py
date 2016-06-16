#!/usr/bin/python
# -*- coding: utf-8 -*-

import threading
import time
from tkinter import *

def readFile(code):
	#get the code 
	#code='2'
	specie = {'Name': '', 'Description':''}
	# Open a file
	file = open("db_senderos.txt", "r")
	for line in file:
		info=line.split('/')
		if info[0]==code:
			specie['Name'] = info[1];
			specie['Description'] = info[2];
			break
	# Close opend file
	file.close()
	#return the dictionary
	return specie

def readCode(x):
   n=x
   x=x+1
   return n

class Example(Frame):
  
    def __init__(self, parent):
        Frame.__init__(self, parent, background="white")     
        self.parent = parent
        self.parent.title("Smart Trails")
        self.pack(fill=BOTH, expand=1)
        self.centerWindow()
        self.num=1
        #Vars and GUI of the Name
        self.varName = StringVar()
        self.name = Label(self.parent,bg="white",textvariable=self.varName,font=("Helvetica", 16))
        self.name.place(x=10, y=70)

	#vars and GUI of Multitext
        self.description = Text(self.parent, width=96,height=22, relief="flat")
        self.description.place(x=10, y=100)

        self.read = Button(self.parent, text ="Read Code", command = self.getInformation)
        self.read.pack()
        self.read.place(bordermode=OUTSIDE, height=50, width=800)

    def getInformation(self):
        self.description.delete('1.0','end')
        #Leer el archivo y buscar elemento
        code=readCode(self.num)
        self.num=code+1
        specie=readFile(str(code))
        self.varName.set(specie['Name'].upper())
        self.description.insert(INSERT,specie['Description'])

    def getInfo(self):
        print ("hello")
        while(True):
            time.sleep(2)
            print ("Hey")
        

    def centerWindow(self):      
        w = 800
        h = 480

        sw = self.parent.winfo_screenwidth()
        sh = self.parent.winfo_screenheight()
        
        x = (sw - w)/2
        y = (sh - h)/2
        self.parent.geometry('%dx%d+%d+%d' % (w, h, x, y))
        self.parent.minsize(w,h)
        self.parent.maxsize(w,h)


def main():
    root = Tk()
    ex = Example(root)
    #t = threading.Thread(target=ex.getInfo())
    #t.setDaemon(True)
    #t.start()
    root.mainloop()


if __name__ == '__main__':
    main()  
