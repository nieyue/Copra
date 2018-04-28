package com.nieyue.cpm;

import java.util.Vector;

public class calculate_clique_clique_overlap_matrix {
	private int[] c_c_overlap_matrix;//派系重叠矩阵
	private int[] k_clique_overlap_matri;//k派系社团的矩阵，通过这个来派别k派系社团
	Vector< Vector<Integer> > vector_result;//存放的是传递过来的派系存放情况
	Vector< Vector<Integer> > vector_k_clique;//存放最后找到所有的k派系社团
	int k;//要寻找的k派系社团
	Vector< Vector<Integer> > vector_final_k_clique;//存放所有k派系社团的包含点的情况，和vector_k_clique不一样，vector_k_clique存放的是派系的编号
	/*********************************************************************************************************/	
	calculate_clique_clique_overlap_matrix( Vector< Vector<Integer> > vector_result,int k){
		int num = vector_result.size();
		this.k = k;
		this.vector_k_clique = new Vector< Vector<Integer> >();
		this.c_c_overlap_matrix = new int[num*(num+1)/2];//申请空间，下三角（含对角线）
		this.k_clique_overlap_matri = new int[this.c_c_overlap_matrix.length];//申请空间，下三角（含对角线）
		this.vector_result = vector_result;
		this.calculate_overlap_matrix();
		this.find_k_clique();
		this.vector_final_k_clique = new Vector< Vector<Integer> >();
		this.store_vector_final_k_clique();
	}
	/*********************************************************************************************************/	
	private void calculate_overlap_matrix(){
		for(int i = 0; i < this.vector_result.size(); i++){//对角线上的元素赋值
			int position = this.find_position(i, i);
			this.c_c_overlap_matrix[position] = this.vector_result.elementAt(i).size();
		}
		for(int i = 0; i < this.vector_result.size(); i++){//非对角线上的元素赋值
			for(int j = i+1; j < this.vector_result.size(); j++){
				int sum = this.find_the_same_point_number(i, j);
				int position = this.find_position(i, j);
				this.c_c_overlap_matrix[position] = sum;
				this.k_clique_overlap_matri = this.get_k_clique_overlap_matrix(this.k);
			}
		}
	}
	/*********************************************************************************************************/	
	/*定位行值为i，列值为j的数据矩阵在下三角(包含对角线上的元素)中矩阵的位置，返回的是position*/
	public int find_position(int i, int j){
		int x= ((i >= j) ? i:j );//把较大的值给x,
		int y= ((i <= j) ? i:j );//把较小的值给y,
		int position = (x+1)*x/2+y;//定位数据在下三角矩阵中的位置
		return position;
	}
	/*********************************************************************************************************/	
	private int find_the_same_point_number(int i, int j){//找出this.vector_result.elementAt(i)和this.vector_result.elementAt(j)这两个派系含有的公共点个数
		int sum = 0;
		for(int i1 = 0; i1 < this.vector_result.elementAt(i).size(); i1++){
			int i_member = this.vector_result.elementAt(i).elementAt(i1).intValue();
			for(int j1 = 0; j1 < this.vector_result.elementAt(j).size(); j1++){
				int j_member = this.vector_result.elementAt(j).elementAt(j1).intValue();
				if(i_member == j_member){
					sum++;
					break;
				}
			}
		}
		return sum;
	}
	/*********************************************************************************************************/	
	//就是寻找K派系社团的关系矩阵
	public int[] get_k_clique_overlap_matrix(int k)
	{
		for(int i = 0; i < this.c_c_overlap_matrix.length; i++)
		{
			if(this.c_c_overlap_matrix[i] >= k-1 ){
				this.k_clique_overlap_matri[i] = 1;//先统一赋值，如果大于k-1，则给this.k_clique_overlap_matri对应的地方赋值为1，下面可以修改对角线上的值
			}
		}
		//下面是对角线上的元素的赋值，同时也是更正
		for(int i = 0; i < this.vector_result.size(); i++)
		{
			int position = this.find_position(i, i);
			if(this.c_c_overlap_matrix[position] >= k){
				this.k_clique_overlap_matri[position] = 1;//对角线上的重叠矩阵，如果大于等于k，则赋值为1
			}
			else{
				this.k_clique_overlap_matri[position] = 0;
			}
		}

		return this.k_clique_overlap_matri;
	}	
	/*********************************************************************************************************/	
	//寻找所有k派系的情况，存放到this.vector_k_clique里
	private void  find_k_clique(){
		Vector<Integer> k_clique_i;//放的是派系编号
		boolean[] matrix_sign = new boolean[this.vector_result.size()];//记录某个派系是否被加入到某个派系社团中，加入了则置为true
		for(int i = 0; i < this.vector_result.size(); i++){
			int position1 = this.find_position(i, i);
			if(this.k_clique_overlap_matri[position1] == 1 && matrix_sign[i] == false){
				k_clique_i = new Vector<Integer>();//生成这个派系社团存放的结构
				k_clique_i.add(new Integer(i));//把这个派系加入到这个派系社团中
				matrix_sign[i] = true;
				for(int j = 0; j < k_clique_i.size(); j++){
					int Iterator = k_clique_i.elementAt(j).intValue();
					for(int s = 0; s < this.vector_result.size(); s++){
						if( s == Iterator){
							continue;
						}
						int position2 = this.find_position(Iterator, s);
						if(this.k_clique_overlap_matri[position2] == 1 && matrix_sign[s] == false){
							k_clique_i.add(new Integer(s));//把这个派系加入到这个派系社团中
							matrix_sign[s] =true;//并把此项置为true
						} 
					}

				}
				this.vector_k_clique.add(k_clique_i);
			}
		}

		//return this.vector_k_clique;
	}
	/*********************************************************************************************************/	
	//把k派系社团最终情况存放到this.vector_final_k_clique里
	private void store_vector_final_k_clique(){
		Vector<Integer> point_belong_k_clique;
		for(int i = 0; i < this.vector_k_clique.size(); i++){//先定位到某个k派系社团
			point_belong_k_clique = new Vector<Integer>();//生成存放这个派系社团的点的结构
			for(int j = 0; j < this.vector_k_clique.elementAt(i).size(); j++){//再定位到这个k派系社团中的某个派系
				int num = this.vector_k_clique.elementAt(i).elementAt(j).intValue();//得到某个派系的编号
				label0:
					for(int s = 0; s < this.vector_result.elementAt(num).size(); s++){//定位到这个派系中的某个点
						Integer point_now = this.vector_result.elementAt(num).elementAt(s);// 得到这个派系里的某个点
						for(int t = 0 ; t < point_belong_k_clique.size(); t++){//检验是否和前面已经存放的点重复
							int point_have_existed = point_belong_k_clique.elementAt(t).intValue();
							if(point_now.intValue() == point_have_existed){
								continue label0;//如果发现是重复的点就不存储了，直接检验下一个点
							}
						}
						point_belong_k_clique.add(point_now);//将这个点加入到point_belong_k_clique
					}
			}
			this.vector_final_k_clique.add(point_belong_k_clique);//最后把这个k派系社团包含点的情况加入到this.vector_final_k_clique
		}
	}
	/*********************************************************************************************************/
	public Vector< Vector<Integer> > get_vector_k_clique(){
		return this.vector_k_clique;
	}
	/*********************************************************************************************************/
	public Vector< Vector<Integer> > get_vector_final_k_clique(){
		return this.vector_final_k_clique;
	}
	/*********************************************************************************************************/

}
