package com.nieyue.lfm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LFM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Long start=System.currentTimeMillis();
			//File file = new File("lfm/Football.txt");
			File file = new File("lfm/Karate.txt");
			ArrayList<String> vec_relationship  = new ArrayList<String>();//记录有链接的边
			ArrayList<String> seedlist  = new ArrayList<String>();//记录种子
			Map map = new HashMap();//存点和对应点的邻居节点
			if(file.isFile() && file.exists()){
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF8");
				BufferedReader bufferedreader = new BufferedReader(read);
				String Text_line = null;
				String leftId="",rightId="";
				while((Text_line = bufferedreader.readLine())!= null){
						String[] version2 = Text_line.split(" ");
						leftId=version2[0];
						rightId=version2[1];
						if(!map.containsKey(leftId)){map.put(leftId, "0");}
						if(!map.containsKey(rightId)){map.put(rightId, "0");}
						vec_relationship.add(leftId+","+rightId);
				}
				read.close();
				bufferedreader.close();
			}
			int m = vec_relationship.size();//网络的总边数
			Map map3 = new HashMap();
			map3.putAll(map);//存放点的社区数量
				Iterator mapit = map.keySet().iterator();
				while(mapit.hasNext()){
					String point = mapit.next().toString();
					int count=0;String link="";
					for(int i=0,n=vec_relationship.size();i<n;i++){
						String[] line = vec_relationship.get(i).split(",");
						if(point.equals(line[0])||point.equals(line[1])){
							count++;//计算某点的度值
							if(point.equals(line[0])){
								link=link+",-"+line[1];//存邻居结点
							}else{link=link+","+line[0]+"-";}
						}
					}
					//System.out.println(point+":"+count+";"+link+",");
					map.put(point, count+";"+link+",");
					//out.println(point+":"+count+";"+link+",");
					count=0;link="";
					
				}
				//按节点的度降序放到种子队列中
				int mapsize = map.keySet().size();
				Map map2 = new HashMap();
				map2.putAll(map);
				while(seedlist.size()<mapsize){
					String dian="",dianTemp2;int degreed=0,degreedTemp=0;
					Iterator mapit2 = map2.keySet().iterator();
					while(mapit2.hasNext()){
						dianTemp2 = mapit2.next().toString();
						 degreedTemp = Integer.valueOf( map2.get(dianTemp2).toString().replaceAll("-", "").split(";")[0]);
						 if(degreedTemp>degreed){
							 degreed=degreedTemp;
							 dian=dianTemp2;
							 }
					}
					map2.remove(dian);
					seedlist.add(dian);
				}
				
				int communityCount=0;
				double zz=1,a=0.5,b=0.25,c=0.25,e_c_int=0,e_c_out=0;
				String groupClu = "";
				while(seedlist.size()>0){
					//以特定的某点为种子 开始LFM算法
					StringBuffer dian=new StringBuffer();
					int pointNum=1;
					dian.append(seedlist.get(0));
					//ground ground_c=new ground(seedlist.get(0));//升序0或降序seedlist.size()-1获取种子
					map3.put(seedlist.get(0), Integer.valueOf(map3.get(seedlist.get(0)).toString())+1);//点的社区数量
					
					//计算社团C邻居节点测度 当有测度大于0时把测度最大的点加入到社团中
					//当全部的邻居节点的测度都小于0时，计算社团C内部每一个点节点测度，把小于0的点从社团C中移除
					double e_in=0,e_out=0,ceDu_max=0,ceDu=0,proiceDu=0,xxproiceDu=0,ceDu_mix=0;//社团测度的中间值 ceDuTemp记录没加入点i时的测度
					String point_tem="",point_tem_mix="";//团测度ceDu_max暂时最大时的点，后面没有比此测度再大的话则把此点加入社团C
					String cc = "";
					int m3=dian.toString().split(",").length;//社团中总节点数
					
					int seed_outdside=0,seed_inoutsside=0,seed_outinsside=0;//aa点与社团外的双边数、aa指向外的单边数、社团外的点指向aa的单边数
					String seedLink = map.get(seedlist.get(0)).toString().split(";")[1];//获取aa的全部邻居节点
					String LinkExcludeseed=seedLink;
					String Linkseed = LinkExcludeseed.replace(",-", ",").replace("-,", ",").replace(","+seedlist.get(0)+",", ",");
					String[] linkseed = Linkseed.split(",");//点aa社团外的邻接节点 下标从1开始
					for(int aaa=1;aaa<linkseed.length;aaa++)
					{
						if(LinkExcludeseed.contains(",-"+linkseed[aaa]+",")&&LinkExcludeseed.contains(","+linkseed[aaa]+"-,")){
							seed_outdside++;//aa与社团外的双边数
						}else if(LinkExcludeseed.contains(",-"+linkseed[aaa]+",")){
							seed_inoutsside++;//aa指向外的单边数
						}else if(LinkExcludeseed.contains(","+linkseed[aaa]+"-,"))
						{
							seed_outinsside++;//社团外的点指向aa的单边数
						}
					}
					seedlist.remove(0);
					int in_dside=0,in_sside=0,groupoutd=0,right=0,left=0;
					int in_dsideold=0,in_ssideold=0,groupoutdold=seed_outdside,rightold=seed_outinsside,leftold=seed_inoutsside;
					int in_dsideoldtemp=0,in_ssideoldtemp=0,groupoutdoldtemp=0,rightoldtemp=0,leftoldtemp=0;
					
					
					do{		/* 寻找某一节点所有的社团的全部成员 */
						String bb = ","+dian+",";//记录社团中各点已算过节点测度的点，避免重复计算同一个邻居节点的节点测度
						double xx=0;
						
						String dianLink="";//存放某点的全部邻居节点
						String[] groupdianarray=dian.toString().split(",");
					//System.out.println("in_dsideoldtemp="+in_dsideoldtemp+" in_ssideoldtemp="+in_ssideoldtemp+" groupoutdoldtemp="+groupoutdoldtemp+" rightoldtemp="+rightoldtemp+" leftoldtemp="+leftoldtemp);
					for(int i=0;i<m3;i++){//m3为社团中总节点数
						String dianTem = groupdianarray[i];//点dianTem是社团C中的点
						//找社团C中某点的邻居节点 并计算它们的测度
						String[] pointarray = map.get(dianTem).toString().split(";");
						String[] point_link = pointarray[1].replaceAll("-", "").split(",");//存放点dianTem的邻居节点
						
						for(int ii=1,nn=point_link.length;ii<nn;ii++){
								String aa =point_link[ii];//dianTem的邻居节点
								if(bb.contains(","+aa+",")){
									continue;
								}else{bb=bb+","+aa+",";}//共同邻居只计算一次
								if(cc.contains(","+aa+",")){//上一次循环中团测度最小并且小于0的节点不再计算
									continue;
								}
								boolean o=false;
								String[] dianarray2 = dian.toString().split(",");//社区中全部的点
								for(int u=0;u<m3;u++)
								{
									if(dianarray2[u].equals(aa))o=true;
								}
								if(o){continue;}//确保找到的点不在社团C中
								
								/*计算加入后点aa与原社团成员的双边数、单边数 aa点与社团外的双边数、社团外的点指向aa的单边数、aa指向外的单边数 */
								int aa_dside=0,aa_sside=0,aa_atoin=0;//aa与原社团成员的双边数、单边数
								int aa_outdside=0,aa_inoutsside=0,aa_outinsside=0;//aa点与社团外的双边数、aa指向外的单边数、社团外的点指向aa的单边数
								dianLink = map.get(aa).toString().split(";")[1];//获取aa的全部邻居节点
								String LinkExcludeGroupPoint=dianLink;
								for(int j=0;j<dianarray2.length;j++)
								{
									if(dianLink.contains(",-"+dianarray2[j]+",")&&dianLink.contains(","+dianarray2[j]+"-,")){
										aa_dside++;//aa与原社团成员的双边数
										LinkExcludeGroupPoint=LinkExcludeGroupPoint.replace(",-"+dianarray2[j]+",", ",").replace(","+dianarray2[j]+"-,", ",");
									}else if(dianLink.contains(",-"+dianarray2[j]+","))
									{
										aa_atoin++;//点aa指向社区中的点
										LinkExcludeGroupPoint=LinkExcludeGroupPoint.replace(",-"+dianarray2[j]+",", ",").replace(","+dianarray2[j]+"-,", ",");
										
									}else if(dianLink.contains(","+dianarray2[j]+"-,"))
									{
										aa_sside++;//社区中的点指向aa
										LinkExcludeGroupPoint=LinkExcludeGroupPoint.replace(","+dianarray2[j]+"-,", ",");
									}
								}
								String LinkPoint = LinkExcludeGroupPoint.replace(",-", ",").replace("-,", ",");
								String[] linkdian = LinkPoint.split(",");//点aa社团外的邻接节点 下标从1开始
								for(int aaa=1;aaa<linkdian.length;aaa++)
								{
									if(LinkExcludeGroupPoint.contains(",-"+linkdian[aaa]+",")&&LinkExcludeGroupPoint.contains(","+linkdian[aaa]+"-,")){
										aa_outdside++;//aa与社团外的双边数
									}else if(LinkExcludeGroupPoint.contains(",-"+linkdian[aaa]+",")){
										aa_inoutsside++;//aa指向外的单边数
									}else if(LinkExcludeGroupPoint.contains(","+linkdian[aaa]+"-,"))
									{
										aa_outinsside++;//社团外的点指向aa的单边数
									}
								}
								int in_dsidenew = in_dsideold+aa_dside;//社团内的双边数
								int in_ssidenew = in_ssideold+aa_sside+aa_atoin;//社团内的单边数
								int groupoutdnew = groupoutdold-aa_dside+aa_outdside;//社团指向外边的双边数
								int rightnew = rightold-aa_atoin+aa_outinsside; //外边指向社团的单边数
								int leftnew = leftold-aa_sside+aa_inoutsside;//社团指向外边的单边数
								
								e_in=2*a*in_dsidenew+2*b*in_ssidenew;//没加入点aa时社区的团内度值
								e_out=a*groupoutdnew+b*rightnew+c*leftnew;//没加入点aa时社区的团外度值
								xx=e_in/java.lang.Math.pow((e_in+e_out),zz);
								ceDu=xx-proiceDu;
								//System.out.println("ceDu111="+ceDu);
								
								if(ceDu<ceDu_mix){
									ceDu_mix=ceDu;
									//cc =cc+","+point_tem_mix+",";
									point_tem_mix=aa;
									//System.out.println(ground_c.dian+"中邻居点测度最小的点为："+aa+"测度点"+ceDu);
								}//测度小于0的话下次邻居节点的测度计算时不包含它，可以减少时间复杂度
								if(ceDu>ceDu_max){
									//System.out.println(ground_c.dian+"中邻居点测度最大的点为："+aa+"测度点"+ceDu);
									ceDu_max=ceDu;
									point_tem=aa;
									xxproiceDu = xx;
									in_dsideoldtemp = in_dsidenew;
									in_ssideoldtemp = in_ssidenew;
									groupoutdoldtemp = groupoutdnew;
									rightoldtemp = rightnew; //外边指向社团的单边数
									leftoldtemp = leftnew;//没加入点aa时社区的团外度值
								}
						}
					}
					 if(ceDu_mix<0){
						 //System.out.println(ground_c.dian+"中邻居点测度最小的点为："+point_tem_mix+"测度 "+ceDu_mix);
						 //cc=cc+","+point_tem_mix+",";//每次都去掉测度值小于0并且是最小的邻居节点
						 ceDu_mix=0;
					 }
					if(ceDu_max>0){//把团测度最大的点加入到社团C中
						System.out.println(dian);
						dian.append(","+point_tem);
						bb=bb+","+point_tem+",";
						pointNum++;
						proiceDu = xxproiceDu;
						e_in=0;e_out=0;
						map3.put(point_tem, Integer.valueOf(map3.get(point_tem).toString())+1);//点的社区数量
						ceDu_max=0;ceDu=0; 
						seedlist.remove(point_tem);
						m3++;//社团每加入一点 个数增一
						
						in_dsideold = in_dsideoldtemp;
						in_ssideold = in_ssideoldtemp;
						groupoutdold = groupoutdoldtemp;
						rightold = rightoldtemp; 
						leftold = leftoldtemp;
					}else{ceDu_max=-1;}
					}while(ceDu_max>=0);
				
				System.out.println("总点数: "+pointNum);
				System.out.println("社团C里的全部点为: "+dian);
				groupClu=groupClu+";"+dian;
					communityCount++;
					
					
					//计算Q值中的e_in（团内度值） 和 e_out的值（团外度值）
					left=0;right=0;groupoutd=0;in_dside=in_sside=0;
					String dianLink="";String[] groupdianarray=dian.toString().split(",");
					for(int iii=0;iii<m3-1;iii++){
						for(int j=iii+1;j<m3;j++){
							dianLink = map.get(groupdianarray[iii]).toString().split(";")[1];
							if(dianLink.contains(",-"+groupdianarray[j]+",")&&dianLink.contains(","+groupdianarray[j]+"-,")){
								//System.out.println("社区中全部的点"+ground_c.dian+"  点"+groupdianarray[iii]+" 和 "+groupdianarray[j]+" 是双向边");
								in_dside++;//社团C内双向边的数目和
							}else if(dianLink.contains(",-"+groupdianarray[j]+",")||dianLink.contains(","+groupdianarray[j]+"-,")){
								//System.out.println("社区中全部的点"+ground_c.dian+"  点"+groupdianarray[iii]+" 和 "+groupdianarray[j]+" 是单向边");
								in_sside++;//社团C内单向边的数目和
							}
						}
					}
					
					String[] groupdian = dian.toString().split(",");
					for(int k2=0;k2<m3;k2++){
						dianLink = map.get(groupdian[k2]).toString().split(";")[1];//存放点groupdian[k2]的全部邻居节点
						for(int k22=0;k22<m3;k22++){//先过滤掉社团中的点
							dianLink=dianLink.replaceAll(",-"+groupdian[k22]+"," , ",");
							dianLink=dianLink.replaceAll(","+groupdian[k22]+"-," , ",");
						}
						String[] pointarray = dianLink.split(",");//存放点groupdian[k2]的全部邻居节点 已过滤掉社区中的点
						
						String quchong = ",";
						for(int k33=1,n33=pointarray.length;k33<n33;k33++){//注意要除去数组两边的空格
							
							String pointLink = dianLink.split(",")[k33].replaceAll("-", "");
							if(quchong.contains(","+pointLink+",")){continue;}else{quchong=quchong+pointLink+",";}//避免重复计算邻居节点的测度
							if(dianLink.contains(","+pointLink+"-,")&&dianLink.contains(",-"+pointLink+",")){
								groupoutd++;//社团指向团外的双向边数
								}else if(dianLink.contains(","+pointLink+"-,")){
									right++;//外边指向社团的单边数
								}else if(dianLink.contains(",-"+pointLink+",")){
									left++;//社团指向外边的单边数
								}
						}
					}
						//System.out.println("00000000000社区为= "+ground_c.dian +"时"+" 社团内双向边的数目=" +in_dside+" 社团内单向边的数目=" +in_sside+" 社团指向团外的双向边数=" +groupoutd+" 外边指向社团的单边数=" +right+" 社团指向外边的单边数=" +left);
						e_in=2*a*in_dside+2*b*in_sside;//团内度值
						e_out=a*groupoutd+b*right+c*left;//团外度值
						if(communityCount==4){}else{
						e_c_int=e_c_int+e_in;
						e_c_out=e_c_out+java.lang.Math.pow(e_out, 2);}
				}
			double Q=(e_c_int/(2*m))-(e_c_out/(4*java.lang.Math.pow(m, 2)));
			
			Long end=System.currentTimeMillis();
			
			
			
			groupClu=groupClu.substring(groupClu.indexOf(";")+1);
			String[] groupCluarray = groupClu.toString().split(";");
			System.out.println("共有："+groupCluarray.length+" 个社团");
			//计算重叠点
			int count=0;
			Iterator map3it = map3.keySet().iterator();
			String bbbbb="";//记录重叠点
			while(map3it.hasNext()){
				String aaa= map3it.next().toString();
				if(Integer.valueOf(map3.get(aaa).toString())>1){bbbbb +=","+aaa; count++;}
			}
			System.out.println("总的重叠点数为= "+count);
			System.out.println("重叠点= "+bbbbb);
			//计算EQ
			double EQ = 0,EQ_temp=0;int m3=0;
			for(int i = 0,n=groupCluarray.length;i<n;i++){
				String[] grouppoint = groupCluarray[i].split(",");//获取第I个社区全部的点
				m3 = grouppoint.length;
				
				for(int iii=0;iii<m3-1;iii++){
					double Qv = Double.valueOf(map3.get(grouppoint[iii]).toString());
					double Kv = Double.valueOf(map.get(grouppoint[iii]).toString().split(";")[0]);
					double Qw = 0;
					double Kw = 0;
					double Avw= 0;
					
					for(int j=iii+1;j<m3;j++){
						Qw = Double.valueOf(map3.get(grouppoint[j]).toString());
						Kw = Double.valueOf(map.get(grouppoint[j]).toString().split(";")[0]);
						String grouplink = map.get(grouppoint[iii]).toString().split(";")[1];
						
						if(grouplink.contains(",-"+grouppoint[j]+",")&&grouplink.contains(","+grouppoint[j]+"-,")){
							Avw=2.0;//社团C内双向边的数目和
						}else if(grouplink.contains(",-"+grouppoint[j]+",")||grouplink.contains(","+grouppoint[j]+"-,")){
							Avw=1.0;//社团C内单向边的数目和
						}
						
						EQ_temp = EQ_temp+(Avw-(Kv*Kw)/(Double.valueOf(2*m)))/(Qv*Qw);
					}
				}
			}
			EQ=EQ_temp/(2*m);
			System.out.println("EQ："+EQ);
			System.out.println("度模块Q= "+Q);
			System.out.println("算法运行时间为："+(end-start)+"ms");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
