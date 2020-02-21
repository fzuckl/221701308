import java.io.*;
import java.lang.*;
import java.util.*;

public class InfectStatistic 
{
	static String log,out,date;
	static String[] typ=new String[4];	//存储要求输出的类型，最多4个
	static String[] pro=new String[35];	//存储输入的省份名称
	static String Pro[]={
		"全国","安徽","澳门","北京","重庆","福建","甘肃",
		 "广东","广西","贵州","海南","河北","河南","黑龙江",
		"湖北","湖南","吉林","江苏","江西","辽宁","内蒙古",
		 "宁夏","青海","山东","山西","陕西","上海","四川",
		"台湾","天津","西藏","香港","新疆","云南","浙江"
	};
	static int[][] humC=new int[35][4];//定义一个二维数组记录所有种类的人数依次为感染、疑似、治愈、死亡
	
	public static void main(String[] args){	
		humC=set0();	//初始化记录感染、疑似、治愈、死亡人数的数组
		dealARGS(args);	//处理输入参数
		outpTo(out);	//按要求输出结果到目标文档
	}
	
	public static int[][] set0(){
		int[][] a=new int[35][4];
		for(int i=0;i<35;i++){
			for(int j=0;j<4;j++){
				a[i][j]=0;
			}
		}
		return a;
	}
	
	public static void dealARGS(String[] args){	//处理输入参数
		for(int i=0;i<args.length;i++){
			if(args[i].compareTo("-log")==0){
				log=args[i+1];
				i++;
			}
			else if(args[i].compareTo("-out")==0){
				out=args[i+1];
				i++;
			}
			else if(args[i].compareTo("-date")==0){
				date=args[i+1];
				i++;
			}
			else if(args[i].compareTo("-type")==0){
				for(int a=0;(i+1+a)<args.length;){
					if((args[i+1+a].compareTo("ip")==0)||(args[i+1+a].compareTo("sp")==0)||
							(args[i+1+a].compareTo("cure")==0)||(args[i+1+a].compareTo("dead")==0)){
						typ[a]=args[i+1+a];
						a++;
						if((i+2+a)<args.length){
							continue;
						}
						else{
							i+=a;
							break;
						}
					}
					else{
						i+=a;
						break;
					}
				}
			}
			else if(args[i].compareTo("-province")==0){
				for(int a=0;a<Pro.length;a++){
					if((i+1+a)<args.length){
						if(args[i+1+a].length()!=0){
							String b=args[i+1+a];
							pro[a]=b;
						}
					}
				}
			}
		}
	}
	
	public static void rTxt(String path){
		try{
			File f1=new File(path);
			if((f1.isFile())&&(f1.exists())){
				InputStreamReader r= new InputStreamReader(new FileInputStream(f1),"UTF-8");
	            BufferedReader buffR= new BufferedReader(r);
	            String line= null;
	            while((line=buffR.readLine())!=null){	//读取文档并处理文档内数据
	            	if(line.contains("//")){	//文档结尾，统计全国感染、疑似、治愈、死亡人数
	            		for(int i=1;i<Pro.length;i++){
	            			for(int j=0;j<4;j++){
	            				humC[0][j]+=humC[i][j];
	            			}
	            		}
	            		break;
	            	}
	            	String[] s=line.split(" ");//分割读进来的字符串
	            	for(int i=1;i<Pro.length;i++){
	            		if((s[0].compareTo(Pro[i]))==0){	//识别省份名称
	            			if(s[1].compareTo("新增")==0){
	            				if(s[2].compareTo("感染患者")==0){
	            					humC[i][0]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            				else if(s[2].compareTo("疑似患者")==0){
	            					humC[i][1]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            			}
	            			else if(s[1].compareTo("死亡")==0){
	            				humC[i][3]+=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            				humC[i][0]-=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            			}
	            			else if(s[1].compareTo("治愈")==0){
	            				humC[i][2]+=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            				humC[i][0]-=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            			}
	            			else if(s[1].compareTo("排除")==0){
	            				humC[i][1]-=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            			}
	            			else if(s[1].compareTo("疑似患者")==0){
	            				if(s[2].compareTo("流入")==0){
	            					for(int j=0;j<Pro.length;j++){
	            						if(s[3].compareTo(Pro[j])==0){
	            							humC[i][1]-=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            							humC[j][1]+=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            						}
	            					}
	            				}
	            				else if(s[2].compareTo("确诊感染")==0){
	            					humC[i][1]-=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            					humC[i][0]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            			}
	            			else if(s[1].compareTo("感染患者")==0){
	            				if(s[2].compareTo("流入")==0){
	            					for(int j=0;j<Pro.length;j++){
	            						if(s[3].compareTo(Pro[j])==0){
	            							humC[i][0]-=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            							humC[j][0]+=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            						}
	            					}
	            				}
	            			}
	            		}
	            	}
	            }
	            r.close();
			}
		}
		catch(Exception e){
			System.out.print("文件读取出现错误\n");
		}
	}
	
	public static void outpTo(String path){	//用来将数据输出到txt文件中
		try{
			humC=set0();
			File f1=new File(path);
			if(!f1.exists()){	//输出文件不存在则创建文件
				f1.createNewFile();        
            }
			OutputStreamWriter outp=new OutputStreamWriter(new FileOutputStream(f1),"UTF-8");
			BufferedWriter buffO=new BufferedWriter(outp);
			File f=new File(log);//日志文件存在的文件夹
			File[] name=f.listFiles();
			for(File s:name){
				String fName=s.getName();
				int a=fName.lastIndexOf('.');
				fName=fName.substring(0,a);
				if((date==null)||((fName.compareTo(date))<=0)){
					rTxt(s.getPath());
				}
			}
			//对输出进行限定
			if(pro[0]==null){	//没有输入任何省份只输出全国
				buffO.write(Pro[0]+" ");
				out(buffO,0);
				buffO.newLine();//换行
			}
			else{
				for(int i=0;i<pro.length;i++){
					int proN;
					for(int j=0;j<Pro.length;j++){
						if(pro[i]==Pro[j]){	//寻找对应省份
							buffO.write(Pro[j]+" ");
							proN=j;
							out(buffO,proN);
							buffO.newLine();
						}
					}
				}
			}
			String last_line="// 该文档并非真实数据，仅供测试使用";
			buffO.write(last_line);
			buffO.flush();  
			buffO.close();
		}
		catch(Exception e){	
			System.out.print("文件读取出现错误\n");
		}
	}
	
	public static void out(BufferedWriter buffO,int proN){	//根据要求进行输出
		try{
			if(type[0]!=null){
				for(int i=0;i<typ.length;i++){
					if(typ[i]=="ip"){
						String a="感染患者"+humC[proN][0]+"人 ";
						buffO.write(a);
					}
					else if(typ[i]=="sp"){
						String a="疑似患者"+humC[proN][1]+"人 ";
						buffO.write(a);
					}
					else if(typ[i]=="cure"){
						String a="治愈"+humC[proN][2]+"人 ";
						buffO.write(a);
					}
					else if(typ[i]=="dead"){
						String a="死亡"+humC[proN][3]+"人 ";
						buffO.write(a);
					}
				}
			}
			else{
				String a="感染患者"+humC[proN][0]+"人 "+
						"疑似患者"+humC[proN][1]+"人 "+
						"治愈"+humC[proN][2]+"人 "+
						"死亡"+humC[proN][3]+"人";
				buffO.write(a);
			}
		}
		catch(Exception e){
			System.out.print("输出过程中发生错误");
		}
	}
	
}
