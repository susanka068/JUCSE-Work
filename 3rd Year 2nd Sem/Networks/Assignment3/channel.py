import socket
import threading
import common as co

sockSenderRec=co.createSocket(co.portSenderReceive)

sockReceiverSend=co.createSocket(co.portReceiverSend)

def allow_new_conn():
	sockReceiverSend.listen(5)
	receive, addrrec=sockReceiverSend.accept()

	recThread=threading.Thread(target=send_to_receiver, args=[receive])
	recThread.start()

	while(True):
		# Wait for a connection
		sockSenderRec.listen(5)
		c, addr=sockSenderRec.accept()
		# Start a new thread for the sender
		sendThread=threading.Thread(target=receive_from_sender, args=[c,addr])
		sendThread.start()

# Function to send to receiver
def send_to_receiver(receive):

	while(True):
		# If buffer not empty send the frame and clear buffer
		if(len(co.shared_buffer)>0):
			# Send the frame
			print('Sending frame to receiver ')
			co.send_frame(co.shared_buffer[0], receive)
			del co.shared_buffer[0]
		else:
			continue

# Function to receive data from sender
def receive_from_sender(c, addr):
	# Receive data from the sender and keep it in stored buffer
	print('Started new connection to '+str(addr))

	while(True):
		# Receive data from sender
		frame=c.recv(1024).decode()
		co.shared_buffer.append(frame)

allow_new_conn()