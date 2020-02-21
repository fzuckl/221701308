import java.io.*;
import java.lang.*;
import java.util.*;

public class InfectStatistic 
{
	static String log,out,date;
	static String[] typ=new String[4];	//�洢Ҫ����������ͣ����4��
	static String[] pro=new String[35];	//�洢�����ʡ������
	static String Pro[]={
		"ȫ��","����","����","����","����","����","����",
		 "�㶫","����","����","����","�ӱ�","����","������",
		"����","����","����","����","����","����","���ɹ�",
		 "����","�ຣ","ɽ��","ɽ��","����","�Ϻ�","�Ĵ�",
		"̨��","���","����","���","�½�","����","�㽭"
	};
	static int[][] humC=new int[35][4];//����һ����ά�����¼�����������������Ϊ��Ⱦ�����ơ�����������
	
	public static void main(String[] args){	
		humC=set0();	//��ʼ����¼��Ⱦ�����ơ���������������������
		dealARGS(args);	//�����������
		outpTo(out);	//��Ҫ����������Ŀ���ĵ�
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
	
	public static void dealARGS(String[] args){	//�����������
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
	            while((line=buffR.readLine())!=null){	//��ȡ�ĵ��������ĵ�������
	            	if(line.contains("//")){	//�ĵ���β��ͳ��ȫ����Ⱦ�����ơ���������������
	            		for(int i=1;i<Pro.length;i++){
	            			for(int j=0;j<4;j++){
	            				humC[0][j]+=humC[i][j];
	            			}
	            		}
	            		break;
	            	}
	            	String[] s=line.split(" ");//�ָ���������ַ���
	            	for(int i=1;i<Pro.length;i++){
	            		if((s[0].compareTo(Pro[i]))==0){	//ʶ��ʡ������
	            			if(s[1].compareTo("����")==0){
	            				if(s[2].compareTo("��Ⱦ����")==0){
	            					humC[i][0]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            				else if(s[2].compareTo("���ƻ���")==0){
	            					humC[i][1]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            			}
	            			else if(s[1].compareTo("����")==0){
	            				humC[i][3]+=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            				humC[i][0]-=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            			}
	            			else if(s[1].compareTo("����")==0){
	            				humC[i][2]+=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            				humC[i][0]-=Integer.parseInt(s[2].substring(0,s[2].length()-1));
	            			}
	            			else if(s[1].compareTo("�ų�")==0){
	            				humC[i][1]-=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            			}
	            			else if(s[1].compareTo("���ƻ���")==0){
	            				if(s[2].compareTo("����")==0){
	            					for(int j=0;j<Pro.length;j++){
	            						if(s[3].compareTo(Pro[j])==0){
	            							humC[i][1]-=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            							humC[j][1]+=Integer.parseInt(s[4].substring(0,s[4].length()-1));
	            						}
	            					}
	            				}
	            				else if(s[2].compareTo("ȷ���Ⱦ")==0){
	            					humC[i][1]-=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            					humC[i][0]+=Integer.parseInt(s[3].substring(0,s[3].length()-1));
	            				}
	            			}
	            			else if(s[1].compareTo("��Ⱦ����")==0){
	            				if(s[2].compareTo("����")==0){
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
			System.out.print("�ļ���ȡ���ִ���\n");
		}
	}
	
	public static void outpTo(String path){	//���������������txt�ļ���
		try{
			humC=set0();
			File f1=new File(path);
			if(!f1.exists()){	//����ļ��������򴴽��ļ�
				f1.createNewFile();        
            }
			OutputStreamWriter outp=new OutputStreamWriter(new FileOutputStream(f1),"UTF-8");
			BufferedWriter buffO=new BufferedWriter(outp);
			File f=new File(log);//��־�ļ����ڵ��ļ���
			File[] name=f.listFiles();
			for(File s:name){
				String fName=s.getName();
				int a=fName.lastIndexOf('.');
				fName=fName.substring(0,a);
				if((date==null)||((fName.compareTo(date))<=0)){
					rTxt(s.getPath());
				}
			}
			//����������޶�
			if(pro[0]==null){	//û�������κ�ʡ��ֻ���ȫ��
				buffO.write(Pro[0]+" ");
				out(buffO,0);
				buffO.newLine();//����
			}
			else{
				for(int i=0;i<pro.length;i++){
					int proN;
					for(int j=0;j<Pro.length;j++){
						if(pro[i]==Pro[j]){	//Ѱ�Ҷ�Ӧʡ��
							buffO.write(Pro[j]+" ");
							proN=j;
							out(buffO,proN);
							buffO.newLine();
						}
					}
				}
			}
			String last_line="// ���ĵ�������ʵ���ݣ���������ʹ��";
			buffO.write(last_line);
			buffO.flush();  
			buffO.close();
		}
		catch(Exception e){	
			System.out.print("�ļ���ȡ���ִ���\n");
		}
	}
	
	public static void out(BufferedWriter buffO,int proN){	//����Ҫ��������
		try{
			if(type[0]!=null){
				for(int i=0;i<typ.length;i++){
					if(typ[i]=="ip"){
						String a="��Ⱦ����"+humC[proN][0]+"�� ";
						buffO.write(a);
					}
					else if(typ[i]=="sp"){
						String a="���ƻ���"+humC[proN][1]+"�� ";
						buffO.write(a);
					}
					else if(typ[i]=="cure"){
						String a="����"+humC[proN][2]+"�� ";
						buffO.write(a);
					}
					else if(typ[i]=="dead"){
						String a="����"+humC[proN][3]+"�� ";
						buffO.write(a);
					}
				}
			}
			else{
				String a="��Ⱦ����"+humC[proN][0]+"�� "+
						"���ƻ���"+humC[proN][1]+"�� "+
						"����"+humC[proN][2]+"�� "+
						"����"+humC[proN][3]+"��";
				buffO.write(a);
			}
		}
		catch(Exception e){
			System.out.print("��������з�������");
		}
	}
	
}
