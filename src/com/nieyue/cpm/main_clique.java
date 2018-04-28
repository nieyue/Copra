package com.nieyue.cpm;
import java.io.*;
import java.util.Vector;

public class main_clique 
{
	public static void main(String args[])throws IOException{
     long start=System.currentTimeMillis();
		int relation_members[];//下三角 存放关系矩阵
		int member_num  = 0; //网络中点的个数
		try{
			String encording="UTF8";
			File my_input_file  = new File("cpm/Karate.txt");

			if(my_input_file.exists() && my_input_file.isFile() ){//file.isFile() && file.exists() 
				InputStreamReader read_file = new InputStreamReader( new FileInputStream(my_input_file), encording);
				BufferedReader bufferreader = new BufferedReader(read_file);
				String text_line = null;
				String text = null;

				if((text_line = bufferreader.readLine()) == null ){
					System.out.println("网络中点的个数读取为空");
				} 

				member_num  = Integer.parseInt(text_line);//先读取网络中点的个数;
				relation_members = new int[(member_num-1)*member_num/2];//申请空间，下三角，存放关系矩阵，压缩存放
				
				while( (text_line=bufferreader.readLine()) != null ){
					text = text_line.replace(",", "");
					String[] version = text.split("-");

					for(int i = 0; i < version.length; i++ ){
						String[] version_2 = version[i].split(":");
						int point_x = Integer.parseInt(version_2[0]);
						int point_y = Integer.parseInt(version_2[1]);
						int x=( (point_x > point_y) ? point_x:point_y )-1;
						int y=( (point_x < point_y) ? point_x:point_y )-1;//x是行值，y是列值.文本中的数据下标是从1开始的，而程序中则是从0开始，注意此点
						/* 下面是根据行值x和列值y来定位数据在下三角矩阵中的位置*/
						int position_xy = (x-1)*x/2+y;
						relation_members[position_xy] = 1;
					}


				}
				bufferreader.close();
				read_file.close();

				/* 下面开始进行算法*/
				clique_child clique_c = new clique_child( relation_members, member_num);
				clique_c.traceback_find_clique();
				Vector< Vector<Integer> >  vector_s = clique_c.get_vector_result();//vector_s 中得到所有的派系
                System.out.printf("找出的派系如下：");
				for(int i = 0; i < vector_s.size(); i++){
					int s = vector_s.elementAt(i).size();
					System.out.printf("\n%d派系：",s );
					for(int j = 0; j < vector_s.elementAt(i).size(); j++){
						System.out.printf("%d  ", vector_s.elementAt(i).elementAt(j).intValue());
					}
				}

				int k = 3;
				calculate_clique_clique_overlap_matrix  c_c_matrx_deal = new calculate_clique_clique_overlap_matrix(vector_s,k);//生成派系社团重叠矩阵		
				
				int[] k_clique_matrix = c_c_matrx_deal.get_k_clique_overlap_matrix(k);//得到派系社团重叠矩阵
				Vector< Vector<Integer> > vector_k_clique = c_c_matrx_deal.get_vector_k_clique();
				Vector< Vector<Integer> > vector_final_k_clique = c_c_matrx_deal.get_vector_final_k_clique();
				System.out.println();
				System.out.printf("%d派系社团情况如下：",k);
				int ii;
				for( ii = 0; ii < vector_k_clique.size(); ii++){//定位到某个k派系社团
					int number=0;
					System.out.printf("\n%d派系社团%d为: ",k,ii);
					
					for(int j = 0; j < vector_k_clique.elementAt(ii).size(); j++){//定位到某个k派系社团中的某个派系
						int num = vector_k_clique.elementAt(ii).elementAt(j).intValue();//这个派系在vector_s中的编号
						int size_max = vector_s.elementAt(num).size();
						
						System.out.printf("\n%d派系:  ",size_max);
						for(int s = 0; s < size_max; s++,number++){
							int point = vector_s.elementAt(num).elementAt(s).intValue();
							System.out.printf("%d ",point);
						}
					}
					System.out.println();
					System.out.printf("社团%d共有点的个数为%d: ",ii,number);
				}
				
				System.out.println();
				System.out.println("共有："+vector_k_clique.size()+" 个社团");
				System.out.println();
				double EQ=0 ;
				double EQ_temp=0;
				int m=0;
			/*	for(int i=0;i<vector_k_clique.size();i++)
				{
					for(int j = 0; j < vector_k_clique.elementAt(i).size(); j++)
					{//定位到某个k派系社团中的某个派系
						int num = vector_k_clique.elementAt(ii).elementAt(j).intValue();//这个派系在vector_s中的编号
						int size_max = vector_s.elementAt(num).size();
						for(int s = 0; s < size_max; s++){
							int point = vector_s.elementAt(num).elementAt(s).intValue();
							
						}
					}
				}*/
				
			}
			else{
				System.out.println("找不到指定的文件");
			}
		}
		catch(Exception e){
			System.out.println("读取数据错误");
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();
		System.out.println("算法运行时间为："+(end-start)+"ms");

	}
}
