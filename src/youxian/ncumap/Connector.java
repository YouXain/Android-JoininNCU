package youxian.ncumap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import youxian.ncumap.ActivityData;



class Connector{
	static SocketAddress sockaddr = new InetSocketAddress("115.43.127.93", 1234); //設定要連線的IP和PORT
	
	@SuppressWarnings("unchecked")
	public static List<ActivityData> SendGetDataCommand(){
		List<ActivityData> activitys = new ArrayList<ActivityData>();
		Socket sock = new Socket();
		try {    
			sock.connect(sockaddr,10000);  //建立連線,延時1秒
			
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("1;140.115.123.111;");

			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			//String message = (String)ois.readObject();
			String s = (String)ois.readObject();
			activitys = (List<ActivityData>)ois.readObject();		
			//Log.e(s,"Error");
			if (activitys == null) {
				new Exception();
			}
			else{
				List<ActivityData> t = new ArrayList<ActivityData>();
				for(int i=activitys.size()-1;i>=0;i--){
					t.add(activitys.get(i));
				}
				sock.close();
				return t;
			}
			sock.close();  
		  
		}catch(IOException e) {
			//NotConnected(context);
			return null;
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
		}
		return activitys;
		
		
	}
	public static String SendWriteDataCommand(ActivityData test){
		
		String s="";
		//ActivityData test = new ActivityData(0, "fk you", "fk you", "fk you","fk you", "fk you","fk you");
		Socket sock = new Socket();
		try {    
			sock.connect(sockaddr,1000);  //建立連線,延時1秒
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("2;140.115.123.111;");
			oos.writeObject(test);
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			s = (String)ois.readObject();
		}catch(IOException e) {
			//NotConnected(context);
			return "failure";
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
		}
		return s;
		
	}
	@SuppressWarnings("unchecked")
	public static List<ActivityData> SendAuthenticateCommand(String a, String p){
		List<ActivityData> activitys = new ArrayList<ActivityData>();
		String s="";
		Socket sock = new Socket();
		try {    
			sock.connect(sockaddr,1000);  //建立連線,延時1秒
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("3;140.115.123.111;");
			oos.writeObject(a);
			oos.writeObject(p);
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			s = (String)ois.readObject();
			activitys = (List<ActivityData>)ois.readObject(); 
			if (activitys == null) {
				new Exception();
			}
			else{
				List<ActivityData> t = new ArrayList<ActivityData>();
				for(int i=activitys.size()-1;i>=0;i--){
					t.add(activitys.get(i));
				}
				sock.close();
				return t;
			}
			sock.close();  
		}catch(IOException e) {
			//NotConnected(context);
			return null;
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
		}
		//Log.e(s, "s");
		return activitys;
		
	}
	@SuppressWarnings("unchecked")
	public static List<ActivityData> SendGetUserDataCommand(String name){
		List<ActivityData> activitys = new ArrayList<ActivityData>();
		Socket sock = new Socket();
		try {    
			sock.connect(sockaddr,10000);  //建立連線,延時1秒
			
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("4;140.115.123.111;");
			oos.writeObject(name);
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			//String message = (String)ois.readObject();
			String s = (String)ois.readObject();
			activitys = (List<ActivityData>)ois.readObject(); 
			//Log.e(s,"Error");
			if (activitys == null) {
				new Exception();
			}
			else{
				List<ActivityData> t = new ArrayList<ActivityData>();
				for(int i=activitys.size()-1;i>=0;i--){
					t.add(activitys.get(i));
				}
				sock.close();
				return t;
			}
			sock.close();  
		  
		}catch(IOException e) {
			//NotConnected(context);
			return null;
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
		}
		return activitys;
		
		
	}
	public static boolean SendEditUserDataCommand(ActivityData a){
		Socket sock = new Socket();
		String s;
		try {    
			sock.connect(sockaddr,10000);  //建立連線,延時1秒			
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("5;140.115.123.111;");
			oos.writeObject(a);
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			//String message = (String)ois.readObject();
			s = (String)ois.readObject();		
			//Log.e(s,"Error");
			sock.close();  
		  
		}catch(IOException e) {
			//NotConnected(context);
			return false;
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
			return false;
		}
		if(s.contentEquals("success"))
			return true;
		else
			return false;
		
	}
	public static boolean SendDeleteUserDataCommand(int id){
		Socket sock = new Socket();
		String s;
		try {    
			sock.connect(sockaddr,10000);  //建立連線,延時1秒			
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject("6;140.115.123.111;");
			oos.writeObject(id);
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			ois.available();
			//String message = (String)ois.readObject();
			s = (String)ois.readObject();		
			//Log.e(s,"Error");
			sock.close();  
		  
		}catch(IOException e) {
			//NotConnected(context);
			return false;
		}catch(Exception e) {
			//NotConnected(context);
			e.printStackTrace();
			return false;
		}
		if(s.contentEquals("success"))
			return true;
		else
			return false;
		
	}
}