package com.nieyue.cpm;

import java.util.Vector;


public class clique_child {
	private int clique_s_num_max;//判断最大的派系数目s,也就是最有可能存在的最大全耦合网络的大小s
	private int members_num;//网络中点的个数
	private int []members_relation;//存储原始关系矩阵的数组，下三角矩阵
	private Vector<Integer>  vector_A;//放的是集合A，
	private Vector<Integer>  vector_B;//放的是集合B
	private boolean find_s_clique_at_first;//是否是开始找到的第一个s派系，如是是置为true,这里是为了用数组s_Iterator来记录一样大小的派系在vector_s中的首位置
	private int[] s_Iterator;//指示大小为s的派系在vector_s中的首位置，例如第一个4派系出现的位置在vector_s下标是2，则有s_sign[4] = 2;
	private Vector< Vector<Integer> > vector_result;//存放的是最终的找到的派系
	private int[] s_tell;//存放每种度数k包含点的个数m，也就是有m个点的度数是k
	private int[] members_relation_second; //存放的是每次找到新的派系后重新调整的关系矩阵，也是下三角
	private boolean[] point_all_reset;//存放每次经过寻找到新的派系后删除某个点剩下来的点,被删掉的点在数组中对应项为false
	private int s_produce;//记录当前将要产生的派系的大小
	private boolean have_found_clique;//从某个源点出发，是否找到了派系
	private Vector<Integer> vec_point;//存储所有的点，点下标从1开始，即vec_point[0]=1
	/*********************************************************************************************************/	

	clique_child( int[] members_relation,int members_num ){//构造函数

		this.find_s_clique_at_first = true;
		this.members_num = members_num;
		this.members_relation = members_relation;//存储关系矩阵的数组，下三角矩阵
		this.members_relation_second = new int[this.members_relation.length];		
		for(int i = 0; i < this.members_relation_second.length; i++){
			this.members_relation_second[i] = this.members_relation[i];
		}
		this.point_all_reset = new boolean[members_num];
		this.vec_point = new Vector<Integer>();
		for(int i = 0; i < this.point_all_reset.length; i++){
			this.point_all_reset[i] = true;//所有点刚开始都是初始化为1，表示可以用，没有别删除
			this.vec_point.add(new Integer(i+1));//生成所有的点
		}
		this.vector_A = new Vector<Integer>();//放的是集合A，
		this.vector_B = new Vector<Integer>();//放的是集合B，
		this.vector_result = new Vector< Vector<Integer> >();//存放的是最终的找到的派系
		this.s_tell = new int[this.members_num];//存放每种度数k包含点的个数m，也就是有m个点的度数是k,有m个点,则最大的度只能是m-1，故申请空间为this.members_num,例如度为8的有3个点，则有members_num[8]=3
		this.clique_s_num_max = this.tell_s();//判断出了最有可能存在的最大全耦合网络的大小s
		this.s_Iterator = new int[this.clique_s_num_max+1];//给指示器申请空间.多申请一个，因为对于的s_Iterator[0]不放数值
		this.have_found_clique = false;
	}
	/*********************************************************************************************************/	
	/*判断出了最有可能存在的最大全耦合网络的大小s*/	
	private int tell_s(){
		int sum_of_dushu=0;
		int final_return_s=0;

		for(int i =0; i < this.members_num; i++){//i是行值
			for(int j= 0; j < this.members_num; j++){//j是列值
				if(i == j){
					continue;
				}
				int position=this.find_position(i, j);//定位行值为i，列值为j的数据矩阵在下三角中矩阵的位置
				sum_of_dushu += members_relation[position];//计算点i对应的度数为sum_of_dushu								
			}
				this.s_tell[sum_of_dushu] += 1;//对应的判断矩阵中记录相应的度数更改
			
			
			sum_of_dushu = 0;//sum_of_dushu重新置0;
		}
		final_return_s = this.s_tell[this.s_tell.length-1];
		for(int i = this.s_tell.length-1; i >= 0; i--){
			if( this.s_tell[i] > final_return_s ){//找到度数包含的点最多的，例如最大的是某个度有8个点，说明可能形成8派系
				if(this.s_tell[i] >= i+1){
					final_return_s = i+1;//判断出了最有可能存在的最大全耦合网络的大小s
				}
				else{
					final_return_s = this.s_tell[i];//判断出了最有可能存在的最大全耦合网络的大小s
				}

			}

		}
		return final_return_s;


	}
	/*********************************************************************************************************/	
	/*出发开始找派系*/
	public void traceback_find_clique(){
		for(int i = this.clique_s_num_max; i > 2; i--){//寻找派系，从大到小寻找。当小于2时停止,因为2派系是一条边
			this.s_produce = i;//记录要产生的派系大小
			this.find_s_clique_at_first = true;//此项在每次要置为true
			for(int j = 0; j < this.point_all_reset.length; j++){// 一个一个点去寻找所有的派系
				this.have_found_clique = false;//此项每次都要初始化为false
				if(this.point_all_reset[j] == true){
					this.vector_A.clear();
					this.vector_B.clear();
					this.Initial_A_B(j);//调用Initial_A_B()函数,注意传递的参数点下标要从1开始的			
					//找出源点j出发的所有i派系。
					this.look_for_point_j_about_s_clique(i);
					//找到所有包含该节点j的大小为i的派系后，删除该节点和链接它的边,为了在下一个点出发的时候避免找到同一个派系
					if(this.have_found_clique == true){
						this.delete_point_j_and_v(j);
					}					
				}
			}
			/*每次找完指定的派系i后，要把连接矩阵members_relation_second和所有被删除大的点要还原，因为在找下一次的派系i-1时候要用到?*/
			this.revert_point_and_v();

		}

	}
	/*********************************************************************************************************/
	/*集合A和B的初始化,从点v出发,把点v归类到集合A,把和点v有连接的点归类到集合B，注意，这里点v下标是从0开始的，放入到vector中的点下标是从1开始的,注意转换*/
	private  void Initial_A_B(int v){
		this.vector_A.add(this.vec_point.elementAt(v));                                                                                                                                                                                                                                                                    
		for( int i =0; i < this.point_all_reset.length; i++){//把和点v有链接的点i加入到集合B
			if(this.point_all_reset[i]==true){//如果这个点没有被删除掉
				if( i == v){//如果是当前点，则跳过
					continue;
				}
				int position=this.find_position(i, v);//定位行值为i，列值为v的数据矩阵在下三角中矩阵的位置
				if( this.members_relation_second[position]==1){//把和点v有链接的点i加入到集合B
					this.vector_B.add(this.vec_point.elementAt(i));//加入集合的点的下标也是从1开始
				}
			}
		}

	}	
	/*********************************************************************************************************/

	//revert_or_delete为true时，表示还原这个点j的状态，false时表示要删除这个点j以及和它链接的边。
	private void delete_point_j_and_v(int j){
		//先删除这个点
		this.point_all_reset[j] = false;
		//再删除和这个点有链接的所有边。
		for(int i = 0; i < this.members_num; i++){
			if(i == j){
				continue;
			}
			int position = this.find_position(i, j);
			this.members_relation_second[position] = 0;
		}
	}
	/*********************************************************************************************************/
	//还原所有被删除的点和边
	private void revert_point_and_v(){
		for(int i = 0; i < this.point_all_reset.length; i++){
			this.point_all_reset[i] = true;
		}
		for(int i = 0; i < this.members_relation_second.length; i++ ){
			this.members_relation_second[i] = this.members_relation[i];
		}
	}
	/*********************************************************************************************************/
	// point_j是出发点，clique_s_num是层数。
	private void look_for_point_j_about_s_clique(int clique_s_num){//int point_j, 
		if(clique_s_num == 0){
			return;
		}
		Vector<Integer>  vector_A_temp = new Vector<Integer>();
		Vector<Integer>  vector_B_temp = new Vector<Integer>();
		//先把vector_A和vector_B的数据保存下来，在递归完一层后要还原，
		this.back_up_vector_A_and_vector_B(vector_A_temp, vector_B_temp);
		
		for(int i = 0; i < this.vector_B.size(); i++){
			this.vector_A.add(this.vector_B.elementAt(i));//将集合中B的一个点加入到集合A中。
			this.vector_B.remove(i);//从集合B中删除这个点。
			this.delete_point_from_B();//从集合B中删除掉某些点，这些点是和集合A中所有点不再相连的，相连的留下
			//再检测一下有没有必要再进行下去这一个支路寻找派系；没有必要则选择另一条支路去寻找
			boolean if_go_on_find = this.Judge_if_go_on_find_clique_s_num_max();
			if(if_go_on_find == false){
				//首先要还原this.vector_A和this.vector_B的情况
				this.revert_vector_A_and_vector_B(vector_A_temp, vector_B_temp);
				continue;//然后继续换另外一个点的支路寻找
			}
			if(this.vector_A.size() == this.s_produce && this.vector_B.size()==0){//如果this.vector_A的大小到了所要求的派系的大小，并且集合B的为空集就是到了最后一层，就是有新的派系产生了，存储集合A。clique_s_num == 1 || 
				this.store_new_clique();//存储新找到的派系
				return;
			}
			else{//如果没有找到
				this.look_for_point_j_about_s_clique(clique_s_num-1);//继续下一层的寻找派系
				//递归返回这一层的时候，要把this.vector_A和this.vector_B的数据还原。
				this.revert_vector_A_and_vector_B(vector_A_temp, vector_B_temp);
			}
		}
		return;
	}
	/*********************************************************************************************************/
	//先备份vector_A和vector_B的内容
	private void  back_up_vector_A_and_vector_B(Vector<Integer>  vector_A_temp, Vector<Integer>  vector_B_temp){
		for(int i = 0; i < this.vector_A.size(); i++){
			vector_A_temp.add(this.vector_A.elementAt(i));
		}
		for(int i = 0; i < this.vector_B.size(); i++){
			vector_B_temp.add(this.vector_B.elementAt(i));
		}
	}
	/*********************************************************************************************************/
	//还原vector_A和vector_B的内容
	private void revert_vector_A_and_vector_B(Vector<Integer>  vector_A_temp, Vector<Integer>  vector_B_temp){
		this.vector_A.clear();//先删除集合A中的所有元素
		this.vector_B.clear();//先删除集合B中的所有元素
		for(int A_i = 0; A_i < vector_A_temp.size(); A_i++){
			this.vector_A.add(vector_A_temp.elementAt(A_i));
		}
		for(int B_i = 0; B_i < vector_B_temp.size(); B_i++){
			this.vector_B.add(vector_B_temp.elementAt(B_i));
		}
	}
	/*********************************************************************************************************/
	//存储新找到的派系
	private void store_new_clique(){
		this.have_found_clique = true;//找到了新的派系，记下此项
		Vector<Integer>  vector_A_second = new Vector<Integer>();
		for(int A_i = 0; A_i < this.vector_A.size(); A_i++){
			vector_A_second.add(vector_A.elementAt(A_i));
		}
		if(this.find_s_clique_at_first == true){
			this.s_Iterator[this.s_produce] = this.vector_result.size();//记录下大小为this.s_produce的派系在this.vector_result出现的首位置
			this.find_s_clique_at_first =false; //同时要将这一项置为false
		}
		this.vector_result.add(vector_A_second);//把新的派系加入到this.vector_result中。
	}

	/*********************************************************************************************************/		
	/*从集合B中删掉不再与集合A中所有节点相连的节点*/
	private void delete_point_from_B(){
		for(int i = 0; i < this.vector_B.size(); i++){
			//集合B中的每个点去和集合A中的点匹配检验，看是否和集合A中的点有连接，没有连接的把这个点从集合B中删掉。
			for(int j = 0; j < this.vector_A.size(); j++){
				int  B_point = this.vector_B.elementAt(i).intValue()-1;
				int  A_point = this.vector_A.elementAt(j).intValue()-1;//因为A和B集合中的点是下标从1开始的，因此要减去1
				int position=this.find_position(B_point, A_point);//定位行值为B_point，列值为A_point的数据矩阵在下三角中矩阵的位置
				if(this.members_relation_second[position]!=1){
					this.vector_B.remove(i);//从集合B中删掉不再与集合A中所有节点相连的节点
					i--;//指针位置要向前调一位。因为this.vector_B.remove(i)方法在删除成员的同时也把指针往前调整了
					break;//同时跳出这一层的循环，检验集合B中的下一个点。
				}

			}
		}
	}
	/*********************************************************************************************************/	
	/*定位行值为i，列值为j的数据矩阵在下三角中矩阵的位置，返回的是position*/
	private int find_position(int i, int j){
		int x= ((i > j) ? i:j );//把较大的值给x,
		int y= ((i < j) ? i:j );//把较小的值给y,
		int position = (x-1)*x/2+y;//定位数据在下三角矩阵中的位置
		return position;
	}
	/*********************************************************************************************************/	
	/*判断是否要继续寻找派系，没有必要则返回false,有必要返回true;通过集合A和集合B来判断,s是要产生的派系,即是s派系*/
	private boolean Judge_if_go_on_find_clique_s_num_max(){
		int s = this.s_produce;
		/*如果集合A大小没有达到s时，集合B已经为空集，或者集合A和B的大小加起来小于s，则返回false，返回到递归前的一步*/
		if(this.vector_A.size() < s && this.vector_B.isEmpty() || (this.vector_A.size() + this.vector_B.size()) < s){
			return false;
		}
		boolean A_B_belong_to_clique_have_existed = this.if_vector_temp_belong_to_big_clique();
		/*如果集合A和集合B同时同时是已有一个较大的派系中的子集，则停止往下计算。返回false，返回递归的前一步*/
		if( A_B_belong_to_clique_have_existed == true){// && B_belong_to_clique_have_existed
			return false;
		}
		return true;//return false;
	}
	/*********************************************************************************************************/	
	/*判断集合vector_temp是否是已有一个较大派系中的子集，或者是相等于某个已知派系,如果集合vector_temp不属于哪个已知派系时，则返回false*/
	private boolean if_vector_temp_belong_to_big_clique(){
		label_1:	
			for(int i = 0; i < this.vector_result.size(); i++){//取已知派系中的一个派系，
				Vector<Integer> clique_have_existed = this.vector_result.elementAt(i);	
				/*先进行集合A的匹配*/		
				for(int j = 0; j < this.vector_A.size(); j++){//判断this.vector_A集合中的某个元素temp_x是否属于已经存在的派系clique_have_existed
					int temp_x = this.vector_A.elementAt(j).intValue();//取集合A中的一个元素
					boolean  temp_x_belong_to_clique_have_existed = this.if_temp_x_belong_to_clique_have_existed(temp_x, clique_have_existed);
					if( temp_x_belong_to_clique_have_existed == true){
						continue;//如果这个点属于这个已知的派系，进入下一个点和这个已知派系的匹配。
					}
					else{
						continue label_1;//如果这个点不属于这个已知的派系，说明集合A不包含在这个已知的派系当中，跳出当前循环，则进入和下一个已知派系的匹配。
					}			
				}
				/*再进行集合B的匹配*/
				for(int j = 0; j < this.vector_B.size(); j++){
					int temp_x = this.vector_B.elementAt(j).intValue();//取集合B中的某个元素
					boolean temp_x_belong_to_clique_have_existed = this.if_temp_x_belong_to_clique_have_existed(temp_x, clique_have_existed);
					if(temp_x_belong_to_clique_have_existed == true){
						continue;//如果这个点属于这个已知的派系，进入下一个点和这个已知派系的匹配。
					}
					else{
						continue label_1;
					}
				}
				return true;//如果集合A和集合B中的所有点都属于已知的派系，说明集合A和B是这个已知派系的子集。				
			}
	return false;//如果集合A和所有的已知派系进行匹配之后，还是没有找到集合A是哪个已知派系的子集，则返回false
	}
	/*********************************************************************************************************/	
	/*判断A集合中的某个元素temp_x是否属于已经存在的派系clique_have_existed,如果属于的话则返回true*/
	private boolean if_temp_x_belong_to_clique_have_existed(int temp_x, Vector<Integer> clique_have_existed){	
		for(int i = 0; i < clique_have_existed.size(); i++){
			int temp_x_second = clique_have_existed.elementAt(i).intValue();
			if( temp_x == temp_x_second){
				return true;
			}
		}
		return false;//如果全部元素都匹配完了，还是没有找到和A_x相等的，说明A_x不属于这个派系中的一员
	}
	/*********************************************************************************************************/	
	public Vector< Vector<Integer> > get_vector_result(){
		return this.vector_result;
	}
	/*********************************************************************************************************/	


}
