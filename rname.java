/*
 *  Copyright 2011, iSoftStone Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF iSoftStone CO., LTD.
 *  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED
 *  TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF NEWTOUCH CO., LTD.
 */
/**
 * 
 */
//package junit.com.iss.pms.core.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author junni
 * 
 */
public class rname {
	private static Log log = LogFactory.getLog(rname.class);
	String classpath;
	String _classpath;
	private final static String encodeFileSuffix=".jar";

	{
		_classpath = rname.class.getClassLoader().getResource(".")
				.toString();
		_classpath = _classpath.replaceAll("file:", "");
		_classpath = _classpath.substring(1, _classpath.length());
		System.out.println("当前默认classpath为："+_classpath);

	}
	
	static{
		System.loadLibrary("MSVCRTD");
		System.loadLibrary("MFC42D");
		System.loadLibrary("coar");
	}
	
	
	public native String rJarName(String iname);

	public native String rrJarName(String iname);
	

	rname() {
		classpath = rname.class.getClassLoader().getResource(".")
				.toString();
		classpath = classpath.replaceAll("file:", "");
		classpath = classpath.substring(1, classpath.length());
	}

	rname(String classpath) {
		if (classpath.equals("") || classpath == null) {
			classpath = rname.class.getClassLoader().getResource(".")
					.toString();
			classpath = classpath.replaceAll("file:", "");
			classpath = classpath.substring(1, classpath.length());

		}
	}

	/**
	* 入口程序
	*/
	void inputLine(String str){
	
	if(str!=null)
		str=str.trim();
		
	if("".equals(str.trim())||str==null)
			str=_classpath;
	try{	
		if((str.indexOf(encodeFileSuffix) != -1||str.indexOf(encodeFileSuffix.toUpperCase()) != -1)){
			
			 
			if(str.lastIndexOf("/")==-1&&str.lastIndexOf("\\")==-1){
			 
				str=findAbsolutePath(str,null);	
			}else if(str.lastIndexOf("/")!=-1&&str.lastIndexOf(":")==-1){				
				str=_classpath+str;
			}else if(str.lastIndexOf("\\")!=-1&&str.lastIndexOf(":")==-1){				
				str=_classpath+str;			
				str=str.replaceAll("\\\\","/");			
			}else;
			 
			int w=str.lastIndexOf("/")+str.lastIndexOf("\\")+1;				 
			renameFile(str.substring(0,w+1),str.substring(w+1,str.length()));				  
       }else if(str.indexOf(":") == -1) {
		   
			if(str.lastIndexOf("/")!=-1){				
				str=_classpath+str+"/";
			}else if(str.lastIndexOf("\\")!=-1){				
				str=_classpath+str+"\\";			
				str=str.replaceAll("\\\\","/");			
			}else
				str=_classpath+str+"/";

			 
			 renameFolder(str);		
	   }else{
		 
		  renameFolder(str);
	   }
		  
	  }catch(Exception e){
		log.error(str+"操作失败！");		 
	  }
	}


	/**处理单个jar文件*/
	public void renameFile(String _path,String fileName) throws Exception{
		 File f=new File(_path+fileName);
		// String oFileName=rJarName(fileName)+String.valueOf(Math.random()).substring(2,8)+".jar";
		  String oFileName=rJarName(fileName)+encodeFileSuffix;
		 System.gc();
		 File mm=new File(_path+oFileName);     
        if(f.renameTo(mm))           
		  log.info(_path+fileName+"文件修改为："+oFileName);        
        else  
		  log.error(_path+fileName+"文件修改失败！");   	 
	
	}



	/**处理文件目录*/
	public void renameFolder(String _path) throws Exception{			

		 if(_path.lastIndexOf("\\")!=-1)
			 if(_path.lastIndexOf("\\")<_path.length()-1)
				_path+="\\";
		 else if(_path.lastIndexOf("/")!=-1)
			 if(_path.lastIndexOf("/")<_path.length()-1)
				_path+="/";	 
		
		File ff=new File(_path);
		if(!ff.exists()){
			log.error("指定的目录不存在！");
			return;
		}
		if(!ff.isDirectory()){
			log.error("指定的不是目录");
			return;
		}			
		String[] flist=ff.list();		 
		for(String f:flist){
			File f2=new File(_path+f);			
			if(!f2.isDirectory()&&(f.indexOf(encodeFileSuffix)!=-1||f.indexOf(encodeFileSuffix.toUpperCase())!=-1)){
				log.info("正在混淆文件:"+_path+f);				 
				renameFile(_path,f);
			}else if(f2.isDirectory()){				 
				System.out.println("处理下一级目录:"+f);
			   if(_path.lastIndexOf("\\")!=-1)
				renameFolder(_path+f+"\\");
			  else if(_path.lastIndexOf("/")!=-1)
			   renameFolder(_path+f+"/");
			  else
				renameFolder(_path+f);				 
			  continue;				
			}else
				continue;		 
				
		}
	
	}


	/**
	 * 重编译指定目录下class类文件
	 */
	void compileAll(String inputComment) {				 
		if(inputComment.equals("")||inputComment==null){
			inputComment=_classpath;			 
		}
		if (inputComment.indexOf(encodeFileSuffix) != -1||inputComment.indexOf(encodeFileSuffix.toUpperCase()) != -1) {
			//System.out.println(inputComment+"##");
			compileClass(inputComment);			
		}else{			
			File fl=new File(inputComment);
			if(!fl.exists()){
				log.error("指定的目录不存在！");
				return;
			}
			if(!fl.isDirectory()){
				log.error("指定的不是目录");
				return;
			}
			String[] flist=fl.list();
			for(String f:flist){
				File f2=new File(inputComment+f);
				if(!f2.isDirectory()){
					log.info("编译加密class文件："+f);
					compileClass(inputComment+f);
					System.out.println(inputComment+f+"文件重编译成功");
				}else{
					log.info("编译下一级目录...");
					compileAll(inputComment+f);
					System.out.println("正在编译同级目录"+inputComment+f+"。。。");
				}
			}
		}
	}
	
	/**
	 * 编译单个class文件
	 */
	void compileClass(String fPath){		
		try {
			String _fpath="";
			//System.out.println("#####################"+fPath);
			if((fPath.indexOf("/")!=-1||fPath.indexOf("\\")!=-1)&&(fPath.indexOf(encodeFileSuffix)!=-1||fPath.indexOf(encodeFileSuffix.toUpperCase())!=-1)){
				_fpath=fPath;
				//int w=fPath.lastIndexOf("/")+fPath.lastIndexOf("\\")+1;	
				//System.out.println(fPath.substring(w+1,fPath.length()));
				// _fpath=findAbsolutePath(fPath.substring(w+1,fPath.length()),fPath.substring(0,w+1));		 
			//	 System.out.println("#####################223344:"+_fpath);
			}else{				 
				_fpath=findAbsolutePath(fPath,null);
			//	System.out.println("#####################223355:"+_fpath);
			}
			
			if(_fpath==null||_fpath.equals("")){
				System.out.println("没找到所要编译的文件");
				return ;
			}

			
			FileInputStream fi1 = new FileInputStream(new File(_fpath
					));
			byte data[] = new byte[fi1.available()];
			fi1.read(data);
			fi1.close();
			System.out.println("传入字节大小"+data.length);
			
			byte[] _data=data;

			System.out.println("##传出字符串为："+new String(_data));
	
			FileOutputStream fo = new FileOutputStream(new File(_fpath));
			fo.write(_data);
			fo.close();
			
		} catch (Exception e) {
			log.error("编译class文件时出错:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	/**
	 * 返回需要查找文件的全路径
	 * @param fName
	 * @return 
	 */
	String findAbsolutePath(String fName,String filePath) throws Exception{
		//System.out.println(fName+"$");
		//System.out.println(filePath+"$#");
		String _rpath="";
		if(filePath==null||filePath.equals(""))
			filePath=_classpath;
		else{			 
			if(filePath.lastIndexOf("/")!=filePath.length()-1){
				filePath+="/";				
			}else if(filePath.lastIndexOf("/")==filePath.length()-1)
				;
		     else 
				 filePath+="\\";			 		 
			System.out.println(filePath+fName);
			File f2=new File(filePath+fName);
			if(f2.exists()){				
				return filePath+fName;		
			}
					
		}
		File ff=new File(filePath);
		if(!ff.exists()){
			log.error("指定的目录不存在！");
			return null;
		}
		if(!ff.isDirectory()){
			log.error("指定的不是目录");
			return null;
		}	
		String[] flist=ff.list();		 
		for(String f:flist){
			File f2=new File(filePath+f);			
			if(!f2.isDirectory()&&fName.equalsIgnoreCase(f)){
				log.info("查找的文件全路径:"+filePath+f);
				System.out.println("查找的文件全路径:"+filePath+f);
				return filePath+f;
			}else if(f2.isDirectory()){
				log.info("查找下一级目录...");
				System.out.println("查找下一级目录:"+filePath+f);
				_rpath=findAbsolutePath(fName,filePath+f);
				if("".equals(_rpath))
					continue;
				else
					break; 
			}else
				continue;		 
				
		}
		
		return _rpath;
		
	}
	
	public static void main(String[] args) throws Exception
	{
		rname rcc=new rname();
		System.out.println("请输入密码：");
		int a;
		StringBuffer str=new StringBuffer("");
		while((a=System.in.read())!= -1){			 
			str.append((char)a);
			if((char)a=='\n'){
				System.out.println("\n");
				if(("bmlqdW4yMDEyAAAAAAAA").equals(rcc.rJarName(str.toString().trim()))){
				//bmlqdW4NCm5panVuMjAx
				//bmlqdW4yMDEyAAAAAAAA
					System.out.println("身份正确！\n");
					str=new StringBuffer("");
					
					System.gc();
					break;
				}else{
					System.out.println("身份错误！");
					str=new StringBuffer("");
					System.gc();					
				}
					
			}
		}
		


		
		System.out.println("服务器已启动...");
		System.out.print("请输入混淆命令：");

		int c;
		int i=0;
		
		try { 
			str=new StringBuffer("");
			while((c=System.in.read())!= -1){
					str.append((char)c);
					//System.out.print((char*)c);
	//			  System.out.print((char)c); 
				  if((char)c=='\n'){
					  System.out.println("混淆准备中...");				  
					  rcc.inputLine(str.toString().trim());
					  i=0;
					 // break;
				  }				  
			}			
		}catch(Exception e) {
			  System.out.println("混淆出错："+e.getMessage());
			  e.printStackTrace();
		}
		System.out.println("");
		
		System.gc();
	}

}
