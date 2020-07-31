package com.yc.game.wuzi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.yc.game.wuzi.core.WuziGameImpl;
import com.yc.game.wuzi.swing.WuziWin;

/**
 * 五子棋主程序
 * @author 廖彦
 */
//效果
public class WuziMain {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// 传入游戏实现类对象
		Socket so=new Socket("127.0.0.1",8080);
		InetAddress my=so.getInetAddress();
		SocketAddress ot=so.getRemoteSocketAddress();
		System.out.println("我的地址"+my);
		System.out.println("客户端的地址"+ot);
		InputStream in = so.getInputStream();
		OutputStream out = so.getOutputStream();
		
	    WuziGameImpl wg=new WuziGameImpl();wg.c=2;
	    WuziWin ww= new WuziWin(wg);
        ww.start();
		

				
				
				//下
		new Thread() {
			public void run() {
				byte[] buffer = new byte[1024];
				int count;
				while (true) {
					try {
						count = in.read(buffer);
						String index = new String(buffer, 0, count);
						synchronized (this) {
							
							String[] demo = index.split("/");
							
							
							if(demo.length==3) {
								int a=Integer.parseInt(demo[0]);
								int b=Integer.parseInt(demo[1]);
								int c=Integer.parseInt(demo[2]);
								wg.down(a,b,c);
								
								
								
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		/**
		 * 
		 */
		new Thread() {
			public void run() {
				while (true) {
					try {
					
						out.write(wg.str.getBytes());
					
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		
	
		
	}

}

