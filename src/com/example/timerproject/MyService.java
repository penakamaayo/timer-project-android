package com.example.timerproject;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service{
	private static final String TAG = "MyService";
	BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    
    
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Connecting to arduino..", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		/*
		findBT();
		try {
			openBT();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		String method = intent.getExtras().getString("method");
		String value_to_send = intent.getExtras().getString("value");
		
		
		if(method.equals("connect")) {
			//Toast.makeText(this, "KONEK", Toast.LENGTH_LONG).show();

			findBT();
			try {
				openBT();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(method.equals("disconnect")) {
			//Toast.makeText(this, "DISKONEK", Toast.LENGTH_LONG).show();

			try {
				closeBT();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if(method.equals("count_up")) {
			try {
				sendData("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(this, "PLAY", Toast.LENGTH_LONG).show();
		}

		else if(method.equals("pause")) {
			try {
				sendData("2");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(this, "PAUSED", Toast.LENGTH_LONG).show();
		}
		
		else if(method.equals("reset")) {
			try {
				sendData("3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(this, "RESET", Toast.LENGTH_LONG).show();
		}
		
		else if(method.equals("set")) {
			if(value_to_send.equals("10")) {
				try {
					sendData("4");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "10sec" , Toast.LENGTH_LONG).show();
			}
			else if(value_to_send.equals("24")) {
				try {
					sendData("5");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "24sec" , Toast.LENGTH_LONG).show();
			}
			else if(value_to_send.equals("60")) {
				try {
					sendData("6");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "1min" , Toast.LENGTH_LONG).show();
			}
			else if(value_to_send.equals("600")) {
				try {
					sendData("7");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "10min" , Toast.LENGTH_LONG).show();
			}
			else if(value_to_send.equals("1800")) {
				try {
					sendData("8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "30min" , Toast.LENGTH_LONG).show();
			}
			else if(value_to_send.equals("3600")) {
				try {
					sendData("9");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "COUNTDOWN:" + "60min" , Toast.LENGTH_LONG).show();
			}
		}
		
		else if(method.equals("count_down")) {
			Toast.makeText(this, "PLAY" , Toast.LENGTH_LONG).show();

			try {
				sendData("0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onStart");
		
		//Note: You can start a new thread and use it for long background processing from here.
			
	}
	
	//find bluetooth h505
	 void findBT() {
	    	Log.i("findBT","in findBT");
	    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    	Log.i("findBT","bt adapter set");

	        if(mBluetoothAdapter == null)
	        {
	        	Log.i("findBT","null");
	        }
	        
	        if(!mBluetoothAdapter.isEnabled())
	        {
	            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            //startActivityForResult(enableBluetooth, 0);
	        }
	        
	        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	    	Log.i("findBT","paired dev: "+pairedDevices.toString());

	        if(pairedDevices.size() > 0)
	        {
	            for(BluetoothDevice device : pairedDevices)
	            {
	                if(device.getName().equals("HC-05")) 
	                {
	                	Log.i("findBT","found seeed");

	                    mmDevice = device;
	                    break;
	                }
	            }
	        }
	    	Log.i("findBT","could not fine seeed");
	    }
	    
	
	 	 //connect to bt
		 void openBT() throws IOException
		    {
		        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
		        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);   
		        Log.i("openBT", "after createRf");
	
		        mmSocket.connect();
		        Log.i("openBT", "after connect");
		        mmOutputStream = mmSocket.getOutputStream();
		        mmInputStream = mmSocket.getInputStream();
		        
		        beginListenForData();
		        
		        Toast.makeText(this, "Bluetooth Opened", Toast.LENGTH_LONG).show();
		    }
	    
		 	//listen for incoming data from bt
		    void beginListenForData()
		    {
		        final Handler handler = new Handler(); 
		        final byte delimiter = 10; //This is the ASCII code for a newline character
		        
		        stopWorker = false;
		        readBufferPosition = 0;
		        readBuffer = new byte[1024];
		        workerThread = new Thread(new Runnable()
		        {
		            public void run()
		            {                
		               while(!Thread.currentThread().isInterrupted() && !stopWorker)
		               {
		                    try 
		                    {
		                        int bytesAvailable = mmInputStream.available();                        
		                        if(bytesAvailable > 0)
		                        {
		                            byte[] packetBytes = new byte[bytesAvailable];
		                            mmInputStream.read(packetBytes);
		                            for(int i=0;i<bytesAvailable;i++)
		                            {
		                                byte b = packetBytes[i];
		                                if(b == delimiter)
		                                {
		                                    byte[] encodedBytes = new byte[readBufferPosition];
		                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
		                                    final String data = new String(encodedBytes, "US-ASCII");
		                                    readBufferPosition = 0;
		                                    
		                             /*       handler.post(new Runnable()
		                                    {
		                                        public void run()
		                                        {
		                                            myLabel.setText(data);
		                                        }
		                                    });*/
		                                }
		                                else
		                                {
		                                    readBuffer[readBufferPosition++] = b;
		                                }
		                            }				
		                        }
		                    } 
		                    catch (IOException ex) 
		                    {
		                        stopWorker = true;
		                    }
		               }
		            }
		        });

	        workerThread.start();
	    }
	    
		    
		//disconnect BT
	    void closeBT() throws IOException
	    {
	        stopWorker = true;
	        mmOutputStream.close();
	        mmInputStream.close();
	        mmSocket.close();
	    }
	    
	    //sends data to BT
	    void sendData(String msg) throws IOException
	    {
//	    	String msg = Integer.toString(x);
	        msg += "\n";
//	        mmOutputStream.write(msg.charAt(0));
	        mmOutputStream.write(msg.getBytes());
	    }
	 	
	  

	@Override
	public void onDestroy() {
		Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
}