package com.nieyue.copra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Vector;

public class COPRA {
    static String separator = "------------------------------------------------------------------------";
    static String welcome1 = "***************************************";
    static String welcome2 = "* com.nieyue.copra COPRA";
    static int delVerts = 0;
    static int delClusters = 0;
    static int simpleDelVerts = 0;
    static int simpleDelClusters = 0;

    public COPRA() {
    }
    //函数入口
    public static void main(String[] args) {
        String s1 = "clusters-";//用于修改输出文件名
        Vector v1 = null;
        Vector v2 = null;
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        int a1 = 0;
        int a2 = 2147483647;
        int a3 = 1;
        String filePath = fileToPath("copra/karate.txt");//获取输入文件名对应的路径，如要换文件执行，修改这个即可
        String fileName = (new File(filePath)).getName();
        String var26 = s1 + fileName;
        //输出txt文件
        String outputName = s1 + fileName;
        String var28 = null;

        ArrayList var32 = new ArrayList();
        ArrayList var33 = new ArrayList();
        HashMap var34 = new HashMap();
        ArrayList var35 = var32;
        ArrayList var36 = var33;
        HashMap var37 = var34;
        ArrayList var38 = null;
        //读取数据
        readBiGraphEdges(filePath, var32, var35, var33, var36, var34, var37, var38);
        try {
            double var45 = 0.0D;
            double var51 = 0.0D;
            Vector v3 = null;
            int var17 = 1;
            //计算结果，并存储到txt
            VecPair vp1 = clusterGraph1(a2, filePath, var32, var35, var33, var36, var38, var26, outputName, var28, var17, a3, b1, b2, (String) null, a1);
            v2 = vp1.value;
            v1 = vp1.name;

            //输出copra综合结果
            report(((Double) v2.get(2)).doubleValue(), ((Double) v2.get(3)).doubleValue(), ((Double) v2.get(4)).doubleValue(), ((Double) v2.get(5)).doubleValue(), var45, var51, ((Double) v2.get(0)).doubleValue(), ((Double) v2.get(1)).doubleValue(), b3, b4);
            sumRes(v3, v2, 0);
        } catch (Exception e) {
            System.err.println("com.nieyue.copra.COPRA error: " + e.toString());
            e.printStackTrace();
        }
    }

    //打印结果
    public static void printResult(String filePath) {  	
        try {
        	System.out.println("copra结果如下：");
            BufferedReader var3 = new BufferedReader(new FileReader(filePath));
            String var2;
            while ((var2 = var3.readLine()) != null) {
                System.out.println(var2);
            }

            var3.close();
            System.out.println("======================================================");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    //获取路径
	public static String fileToPath(String filename){
		//URL url = COPRA.class.getResource(filename);
		File file=new File(filename);
		 return file.getAbsolutePath();
	}
    
    public static VecPair clusterGraph(int var0, String var1, String var2, String var3, String var4, float var5, int var6, boolean var7, boolean var8, String var9, int var10, boolean var11, boolean var12) {
        if (var12) {
            System.exit(1);
        }

        VecPair var13 = clusterGraph(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
        return var13;
    }

    public static VecPair clusterGraph(int var0, String var1, String var2, String var3, String var4, float var5, int var6, boolean var7, boolean var8, String var9, int var10, boolean var11) {
        boolean var12 = var4 != null;
        ArrayList var13 = new ArrayList();
        ArrayList var14 = new ArrayList();
        HashMap var15 = new HashMap();
        ArrayList var16 = var13;
        ArrayList var17 = var14;
        HashMap var18 = var15;
        ArrayList var19 = null;
        if (var12) {
            var17 = new ArrayList();
            var18 = new HashMap();
            var16 = new ArrayList();
        }

        if (var11) {
            var19 = new ArrayList();
        }

        readBiGraphEdges(var1, var13, var16, var14, var17, var15, var18, var19);
        return clusterGraph1(var0, var1, var13, var16, var14, var17, var19, var2, var3, var4, var5, var6, var7, var8, var9, var10);
    }

    private static VecPair clusterGraph1(int var0, String var1, List<HashSet<Integer>> var2, List<HashSet<Integer>> var3, List<String> var4, List<String> var5, List<HashMap<Integer, Float>> var6, String var7, String outputName, String var9, float var10, int var11, boolean var12, boolean var13, String var14, int var15) {
        Vector var16 = new Vector();
        Vector var17 = new Vector();
        long var27 = 0L;
        long var29 = 0L;
        boolean var31 = var9 != null;
        Map var34 = null;
        Object var35 = new HashMap();
        Object var36 = new HashMap();
        List var37 = null;
        List var38 = null;
        boolean var39 = var15 > 0 && !var31;
        double var40 = 0.0D;
        double var42 = 0.0D;
        double var44 = 0.0D;
        double var46 = 0.0D;
        double var48 = 0.0D;
        Vector var50 = new Vector();
        Vector var51 = new Vector();
        boolean var52 = true;
        boolean var53 = false;
        Object var56 = new HashMap();
        Object var57 = new HashMap();
        long var21 = (new Date()).getTime();
        int var19;
        byte var20;
        if (var31) {
            if (var2.size() >= var3.size()) {
                var19 = var2.size();
                var20 = 1;
                var37 = simpleClustering(var19, var10);
            } else {
                var19 = var3.size();
                var20 = 2;
                var38 = simpleClustering(var19, var10);
            }
        } else {
            var19 = var2.size();
            var20 = 0;
            var37 = simpleClustering(var19, var10);
        }

        int var18;
        //此处未用
        for (var18 = 0; var18 < var0; ++var18) {
            if (var20 == 1) {
                var38 = propagateClusters(var37, var3, var6, var19, var10);
                var37 = propagateClusters(var38, var2, var6, var19, var10);
            } else if (var20 == 2) {
                var37 = propagateClusters(var38, var2, var6, var19, var10);
                var38 = propagateClusters(var37, var3, var6, var19, var10);
            } else {
                var38 = propagateClusters(var37, var2, var6, var19, var10);
                var37 = var38;
            }

            long var23 = (new Date()).getTime();
            Map var32;
            Map var33;
            if (var11 == 1) {
                var32 = clusteringSummary1(var37);
                var34 = minSummary((Map) var36, var32);
                if (var31) {
                    var33 = clusteringSummary1(var38);
                    var35 = minSummary((Map) var57, var33);
                }
            } else if (var11 == 2) {
                var32 = clusteringSummary2(var37);
                var34 = minSummary((Map) var36, var32);
                if (var31) {
                    var33 = clusteringSummary2(var38);
                    var35 = minSummary((Map) var57, var33);
                }
            } else if (var39) {
                if (!var52) {
                    Map var54 = clusteringSummary1(var37);
                    Map var55 = minSummary((Map) var56, var54);
                    if (var55.equals(var56)) {
                        System.out.println("Termination 1 at " + var18);
                        var40 = (double) var18;
                        outputClusters(var37, var38, var7, outputName, var9, var2, var3, var4, var5, var12, var13);
                        var44 = ModOverlap.modOverlap(var1, outputName);
                        System.out.println(var18 + ": " + var44);
                        var52 = true;
                    }

                    var56 = var55;
                }

                if (!var53) {
                    var33 = clusteringSummary2(var37);
                    var35 = minSummary((Map) var57, var33);
                    if (var35.equals(var57)) {
                        System.out.println("Termination 2 at " + var18);
                        var42 = (double) var18;
                        outputClusters(var37, var38, var7, outputName, var9, var2, var3, var4, var5, var12, var13);
                        var46 = ModOverlap.modOverlap(var1, outputName);
                        System.out.println(var18 + ": " + var46);
                        var53 = true;
                    }

                    var57 = var35;
                }
            }

            long var25;
            if (var11 >= 1 && var34.equals(var36) && var35.equals(var57)) {
                var25 = (new Date()).getTime();
                var23 = var25 - var23;
                var27 += var23;
                break;
            }

            var25 = (new Date()).getTime();
            var23 = var25 - var23;
            var27 += var23;
            var36 = var34;
            var57 = var35;
            if (var39 && var18 == var15) {
                outputClusters(var37, var38, var7, outputName, var9, var2, var3, var4, var5, var12, var13);
                var48 = ModOverlap.modOverlap(var1, outputName);
                System.out.println(var18 + ": " + var48);
                var50.add(Double.valueOf(var48));
                var51.add(Integer.valueOf(var18));
                var15 += Math.max(var18 / 10, 1);
            }
        }

        var21 = (new Date()).getTime() - var21;
        //计算结果的函数
        Vector var58 = outputClusters(var37, var38, var7, outputName, var9, var2, var3, var4, var5, var12, var13);
        if (var31) {
            var16.add(Double.valueOf(((Double) var58.get(2)).doubleValue() / (double) var2.size()));
            var17.add("Overlap mode 1");
            var16.add(Double.valueOf(((Double) var58.get(3)).doubleValue() / (double) var3.size()));
            var17.add("Overlap mode 2");
        } else {
            var16.add(Double.valueOf(((Double) var58.get(2)).doubleValue() / (double) var2.size()));
            var17.add("Overlap");
            var16.add(Double.valueOf(0.0D));
            var17.add("Unused");
        }

        var16.add(var58.get(0));
        var17.add("Communities");
        var16.add(var58.get(1));
        var17.add("Non-singleton communities");
        var16.add(Double.valueOf((double) var18));
        var17.add("Iterations");
        var16.add(Double.valueOf((double) var21 + ((Double) var58.get(4)).doubleValue()));
        var17.add("Total time");
        var16.add(Double.valueOf((double) var27));
        var17.add("Termination check time (included in total)");
        var16.add(var58.get(4));
        var17.add("Simplification time (included in total)");
        var16.add(Double.valueOf((double) var2.size()));
        var17.add("Vertices");
        var16.add(Double.valueOf((double) nEdges(var2)));
        var17.add("Edges");
        if (var39) {
            var16.add(Double.valueOf(var40));
            var17.add("Termination 1");
            var16.add(Double.valueOf(var42));
            var17.add("Termination 2");
            var16.add(Double.valueOf(var44));
            var17.add("Termination 1 modOverlap");
            var16.add(Double.valueOf(var46));
            var17.add("Termination 2 modOverlap");

            for (var18 = 0; var18 < var50.size(); ++var18) {
                var16.add(var50.get(var18));
                var17.add(var51.get(var18) + "");
            }

            var50.clear();
        }

        return new VecPair(var17, var16);
    }

    private static int nEdges(List<HashSet<Integer>> var0) {
        int var2 = 0;

        for (int var1 = 0; var1 < var0.size(); ++var1) {
            var2 += ((HashSet) var0.get(var1)).size();
        }

        return var2 / 2;
    }

    private static double stdDev(double var0, int var2, double var3) {
        double var5 = var0 / (double) var2 - var3 * var3;
        double var7 = 0.0D;
        if (var5 < 0.0D) {
            var7 = 0.0D;
        } else {
            var7 = Math.sqrt(var5);
        }

        return var7;
    }

    private static Map<Integer, Float> minSummary(Map<Integer, Float> var0, Map<Integer, Float> var1) {
        if (!var0.keySet().equals(var1.keySet())) {
            return var1;
        } else {
            Iterator var3 = var0.keySet().iterator();
            HashMap var4 = new HashMap();

            while (var3.hasNext()) {
                int var2 = ((Integer) var3.next()).intValue();
                var4.put(Integer.valueOf(var2), Float.valueOf(Math.min(((Float) var0.get(Integer.valueOf(var2))).floatValue(), ((Float) var1.get(Integer.valueOf(var2))).floatValue())));
            }

            return var4;
        }
    }

    private static Map<Integer, Float> clusteringSummary1(List<ClusterLabel> var0) {
        HashMap var4 = new HashMap();

        for (int var1 = 0; var1 < var0.size(); ++var1) {
            Iterator var3 = ((ClusterLabel) var0.get(var1)).labelSet().iterator();

            while (var3.hasNext()) {
                int var2 = ((Integer) var3.next()).intValue();
                if (var4.containsKey(Integer.valueOf(var2))) {
                    var4.put(Integer.valueOf(var2), Float.valueOf(((Float) var4.get(Integer.valueOf(var2))).floatValue() + 1.0F));
                } else {
                    var4.put(Integer.valueOf(var2), Float.valueOf(1.0F));
                }
            }
        }

        return var4;
    }

    private static Map<Integer, Float> clusteringSummary2(List<ClusterLabel> var0) {
        HashMap var6 = new HashMap();

        for (int var1 = 0; var1 < var0.size(); ++var1) {
            Map var5 = ((ClusterLabel) var0.get(var1)).labelMap();
            Iterator var4 = var5.keySet().iterator();

            while (var4.hasNext()) {
                int var2 = ((Integer) var4.next()).intValue();
                float var3 = ((Float) var5.get(Integer.valueOf(var2))).floatValue();
                if (var6.containsKey(Integer.valueOf(var2))) {
                    var6.put(Integer.valueOf(var2), Float.valueOf(((Float) var6.get(Integer.valueOf(var2))).floatValue() + var3));
                } else {
                    var6.put(Integer.valueOf(var2), Float.valueOf(var3));
                }
            }
        }

        return var6;
    }

    public static void propagate(String var0, int var1, String var2, String var3, String var4, float var5) {
        ArrayList var6 = new ArrayList();
        ArrayList var7 = new ArrayList();
        ArrayList var8 = new ArrayList();
        ArrayList var9 = new ArrayList();
        HashMap var10 = new HashMap();
        HashMap var11 = new HashMap();
        readBiGraphEdges(var3, var6, var7, var8, var9, var10, var11, (List) null);
        List var12 = readClusters(var2, var1, var5, var10);
        List var13 = propagateClusters(var12, var7, (List) null, var1, var5);
        outputClusters(var13, var13, (String) null, var4, var4, var6, var6, var9, var9, true, true);
    }

    private static List<ClusterLabel> propagateClusters(List<ClusterLabel> var0, List<HashSet<Integer>> var1, List<HashMap<Integer, Float>> var2, int var3, float var4) {
        float var7 = 1.0F;
        ClusterLabel var9 = null;
        ArrayList var10 = new ArrayList();

        for (int var5 = 0; var5 < var1.size(); ++var5) {
            var9 = new ClusterLabel(var3, var4, var5, false);

            int var6;
            for (Iterator var8 = ((HashSet) var1.get(var5)).iterator(); var8.hasNext(); var9.neighbour((ClusterLabel) var0.get(var6), var7)) {
                var6 = ((Integer) var8.next()).intValue();
                if (var2 != null) {
                    var7 = ((Float) ((HashMap) var2.get(var5)).get(Integer.valueOf(var6))).floatValue();
                }
            }

            var9.noMore();
            var10.add(var9);
        }

        return var10;
    }

    private static void reportRes(Vector<Double> var0, Vector<String> var1, int var2) {
        if (var0 != null) {
            for (int var3 = 0; var3 < var0.size(); ++var3) {
                if (!"Unused".equals(var1.get(var3))) {
                    System.out.println(String.format("%s: %.3f", new Object[]{var1.get(var3), Double.valueOf(((Double) var0.get(var3)).doubleValue() / (double) var2)}));
                }
            }
        }

    }

    private static Vector<Double> sumRes(Vector<Double> var0, Vector<Double> var1, int var2) {
        Vector var4 = new Vector();
        int var3;
        if (var0 == null) {
            for (var3 = var2; var3 < var1.size(); ++var3) {
                var4.add(var1.get(var3));
            }
        } else {
            for (var3 = var2; var3 < var1.size(); ++var3) {
                var4.add(Double.valueOf(((Double) var0.get(var3 - var2)).doubleValue() + ((Double) var1.get(var3)).doubleValue()));
            }
        }

        return var4;
    }

    private static void report(double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, boolean var16, boolean var17) {
        System.out.print("社区总数、迭代次数、耗时、重叠：");
    	String var18 = String.format("%.0f (%.0f) communities, %.0f iterations, %.0fms", new Object[]{Double.valueOf(var0), Double.valueOf(var2), Double.valueOf(var4), Double.valueOf(var6)});
        if (var16) {
            var18 = var18 + String.format(", overlap=%.3f/%.3f", new Object[]{Double.valueOf(var12), Double.valueOf(var14)});
            if (var17) {
                var18 = var18 + String.format(", mod=%.3f/%.3f", new Object[]{Double.valueOf(var8), Double.valueOf(var10)});
            }
        } else {
            var18 = var18 + String.format(", overlap=%.3f", new Object[]{Double.valueOf(var12)});
            if (var17) {
                var18 = var18 + String.format(", mod=%.3f", new Object[]{Double.valueOf(var8)});
            }
        }

        System.out.println(var18);
    }

    public static void readBiGraphEdges(String var0, List<HashSet<Integer>> var1, List<HashSet<Integer>> var2, List<String> var3, List<String> var4, List<HashMap<Integer, Float>> var5) {
        HashMap var6 = new HashMap();
        HashMap var7 = new HashMap();
        readBiGraphEdges(var0, var1, var2, var3, var4, var6, var7, var5);
    }

    private static void copyFile(String var0, String var1) {
        try {
            BufferedReader var3 = new BufferedReader(new FileReader(var0));
            PrintStream var4 = new PrintStream(new FileOutputStream(var1));

            String var2;
            while ((var2 = var3.readLine()) != null) {
                var4.println(var2);
            }

            var3.close();
            var4.close();
        } catch (Exception var5) {
            System.out.println("copyFile error: " + var0 + " " + var1);
            System.out.println(var5.toString());
            var5.printStackTrace();
            System.exit(1);
        }

    }

    //读文件
    private static int readBiGraphEdges(String filename, List<HashSet<Integer>> lstHashSet1, List<HashSet<Integer>> lstHashSet2, List<String> lstString1, List<String> lstString2, Map<String, Integer> map1, Map<String, Integer> map2, List<HashMap<Integer, Float>> lstHashMap) {
        int lineNum = 0;
        boolean a = lstString1 != lstString2;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            while (true) {
                while (true) {
                    String lineContent;
                    do {
                        do {
                            if ((lineContent = br.readLine()) == null) {
                                br.close();
                                return lineNum;
                            }
                        } while ("".equals(lineContent));
                    } while (lineContent.startsWith("#"));

                    String[] splitEdge = lineContent.split("[ \t]");
                    float var14;
                    if (Array.getLength(splitEdge) >= 3) {
                        var14 = Float.parseFloat(splitEdge[2]);
                    } else {
                        var14 = 1.0F;
                    }

                    if (!a && splitEdge[0].equals(splitEdge[1])) {
                        System.out.println("Invalid edge (ignored): " + splitEdge[0] + "/" + splitEdge[1]);
                    } else {
                        int var11 = nameInt(splitEdge[0], lstString1, map1);
                        int var12 = nameInt(splitEdge[1], lstString2, map2);

                        while (lstHashSet1.size() < var11 + 1) {
                            lstHashSet1.add(new HashSet());
                            if (lstHashMap != null) {
                                lstHashMap.add(new HashMap());
                            }
                        }

                        if (((HashSet) lstHashSet1.get(var11)).add(Integer.valueOf(var12))) {
                            ++lineNum;
                        }

                        while (lstHashSet2.size() < var12 + 1) {
                            lstHashSet2.add(new HashSet());
                            if (lstHashMap != null) {
                                lstHashMap.add(new HashMap());
                            }
                        }

                        ((HashSet) lstHashSet2.get(var12)).add(Integer.valueOf(var11));
                        if (lstHashMap != null) {
                            ((HashMap) lstHashMap.get(var11)).put(Integer.valueOf(var12), Float.valueOf(var14));
                            ((HashMap) lstHashMap.get(var12)).put(Integer.valueOf(var11), Float.valueOf(var14));
                        }
                    }
                }
            }
        } catch (Exception var17) {
            System.out.println("readBiGraphEdges error: " + filename);
            System.out.println(var17.toString());
            var17.printStackTrace();
            System.exit(1);
            return lineNum;
        }
    }

    private static int nameInt(String var0, List<String> var1, Map<String, Integer> var2) {
        boolean var3 = false;
        int var4;
        if (var1 == null) {
            var4 = Integer.parseInt(var0);
        } else if (var2.containsKey(var0)) {
            var4 = ((Integer) var2.get(var0)).intValue();
        } else {
            var4 = var1.size();
            var1.add(var0);
            var2.put(var0, Integer.valueOf(var4));
        }

        return var4;
    }

    private static Vector<Double> outputClusters(List<ClusterLabel> var0, List<ClusterLabel> var1, String var2, String outputName, String var4, List<HashSet<Integer>> var5, List<HashSet<Integer>> var6, List<String> var7, List<String> var8, boolean var9, boolean var10) {
        long var11 = (new Date()).getTime();
        double var13 = 0.0D;
        int var15 = var0.size();
        boolean var16 = var7 != var8;
        ArrayList var17 = new ArrayList();
        List var20 = null;
        List var21 = null;
        long var22 = (new Date()).getTime();
        List var18 = convertClusters(var0, var17, 0);
        if (var16) {
            var20 = convertClusters(var1, var17, var15);
            combineClusters(var18, var20);
            var5 = combineGraphs(var5, var6, var15);
        }

        long var24 = (new Date()).getTime();
        //增加结点到var26变量中
        HashSet var26 = flatten(var18);
        long var27 = (new Date()).getTime();
        //去除重复节点
        simpleSimplifyClusters(var18, var17);
        long var29 = (new Date()).getTime();
        //保留非空节点
        var18 = removeEmpty(var18);
        int var31 = var18.size();
        long var32 = (new Date()).getTime();
        List var19;
        if (var9) {
            var19 = contiguous(var18, var5);
            if (var31 != var19.size()) {
                var19 = removeEmpty(var19);
            }
        } else {
            var19 = var18;
        }

        if (var10) {
            simplifyClusters(var19);
        }

        double var34 = (double) var19.size();
        long var36 = (new Date()).getTime();
        addSingletons(var19, var26);
        long var38 = (new Date()).getTime();
        double var40 = (double) var19.size();
        var11 = (new Date()).getTime() - var11;
        if (var16) {
            writeBiClusters(var19, var2, var7, var8, var15);
            var21 = filterClusters(var19, var15);
            writeClusters(var21, var4, var8);
            var13 = (double) totSize(var21);
        }
        //保存结果到txt
        writeClusters(var19, outputName, var7);
        double var42 = (double) totSize(var19);
        //存储每个步骤执行的时差，包含了执行时间
        Vector var44 = new Vector();
        var44.add(Double.valueOf(var40));
        var44.add(Double.valueOf(var34));
        var44.add(Double.valueOf(var42));
        var44.add(Double.valueOf(var13));
        var44.add(Double.valueOf((double) var11));
        var44.add(Double.valueOf((double) (var24 - var22)));
        var44.add(Double.valueOf((double) (var27 - var24)));
        var44.add(Double.valueOf((double) (var29 - var27)));
        var44.add(Double.valueOf((double) (var32 - var29)));
        var44.add(Double.valueOf((double) (var36 - var32)));
        var44.add(Double.valueOf((double) (var38 - var36)));
        return var44;
    }

    private static int totSize(List<TreeSet<Integer>> var0) {
        int var1 = 0;

        for (int var2 = 0; var2 < var0.size(); ++var2) {
            var1 += ((TreeSet) var0.get(var2)).size();
        }

        return var1;
    }

    private static List<TreeSet<Integer>> filterClusters(List<TreeSet<Integer>> var0, int var1) {
        ArrayList var7 = new ArrayList();
        Iterator var5 = var0.iterator();

        while (var5.hasNext()) {
            TreeSet var3 = (TreeSet) var5.next();
            Iterator var6 = var3.iterator();
            TreeSet var4 = new TreeSet();

            while (var6.hasNext()) {
                int var2 = ((Integer) var6.next()).intValue();
                if (var2 >= var1) {
                    var4.add(Integer.valueOf(var2 - var1));
                    var6.remove();
                }
            }

            if (var3.isEmpty()) {
                var5.remove();
            }

            if (!var4.isEmpty()) {
                var7.add(var4);
            }
        }

        return var7;
    }

    private static List<HashSet<Integer>> combineGraphs(List<HashSet<Integer>> var0, List<HashSet<Integer>> var1, int var2) {
        ArrayList var4 = new ArrayList();

        int var5;
        for (var5 = 0; var5 < var0.size(); ++var5) {
            var4.add(new HashSet());
            Iterator var3 = ((HashSet) var0.get(var5)).iterator();

            while (var3.hasNext()) {
                ((HashSet) var4.get(var5)).add(Integer.valueOf(((Integer) var3.next()).intValue() + var2));
            }
        }

        while (var5 < var2) {
            var4.add(new HashSet());
            ++var5;
        }

        for (var5 = 0; var5 < var1.size(); ++var5) {
            var4.add(var1.get(var5));
        }

        return var4;
    }

    private static void combineClusters(List<TreeSet<Integer>> var0, List<TreeSet<Integer>> var1) {
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            ((TreeSet) var0.get(var2)).addAll((Collection) var1.get(var2));
        }

    }

    private static void addSingletons(List<TreeSet<Integer>> var0, HashSet<Integer> var1) {
        HashSet var4 = flatten(var0);
        Iterator var5 = var1.iterator();

        while (var5.hasNext()) {
            int var2 = ((Integer) var5.next()).intValue();
            if (!var4.contains(Integer.valueOf(var2))) {
                TreeSet var3 = new TreeSet();
                var3.add(Integer.valueOf(var2));
                var0.add(var3);
            }
        }

    }

    private static HashSet<Integer> flatten(List<TreeSet<Integer>> var0) {
        HashSet var2 = new HashSet();

        for (int var1 = 0; var1 < var0.size(); ++var1) {
            var2.addAll((Collection) var0.get(var1));
        }

        return var2;
    }

    private static void writeClusters(List<TreeSet<Integer>> var0, String outputName, List<String> var2) {
        try {
            if ("".equals(outputName)) {
                writeClusters(var0, System.out, var2);
            } else {
            	String temp  = fileToPath(outputName);
                PrintStream t = new PrintStream(new FileOutputStream(temp));
                writeClusters(var0, t, var2);
                t.close();
            }
        } catch (Exception var4) {
            System.out.println("writeClusters error " + var4.toString());
            var4.printStackTrace();
            System.exit(1);
        }

    }

    private static List<TreeSet<Integer>> convertClusters(List<ClusterLabel> var0, List<TreeSet<Integer>> var1, int var2) {
        ArrayList var7 = new ArrayList();

        for (int var3 = 0; var3 < var0.size(); ++var3) {
            TreeSet var5 = ((ClusterLabel) var0.get(var3)).labelSet();

            int var4;
            for (Iterator var6 = var5.iterator(); var6.hasNext(); ((TreeSet) var1.get(var4)).remove(Integer.valueOf(var4))) {
                var4 = ((Integer) var6.next()).intValue();

                while (var7.size() < var4 + 1) {
                    var7.add(new TreeSet());
                    var1.add(null);
                }

                ((TreeSet) var7.get(var4)).add(Integer.valueOf(var3 + var2));
                if (var1.get(var4) == null) {
                    var1.set(var4, new TreeSet(var5));
                } else {
                    ((TreeSet) var1.get(var4)).retainAll(var5);
                }
            }
        }

        return var7;
    }

    private static List<TreeSet<Integer>> removeEmpty(List<TreeSet<Integer>> var0) {
        ArrayList var3 = new ArrayList();

        for (int var1 = 0; var1 < var0.size(); ++var1) {
            TreeSet var2 = (TreeSet) var0.get(var1);
            if (var2.size() > 1) {
                var3.add(var2);
            }
        }

        return var3;
    }

    private static void simpleSimplifyClusters(List<TreeSet<Integer>> var0, List<TreeSet<Integer>> var1) {
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            if (var1.get(var2) != null && !((TreeSet) var1.get(var2)).isEmpty() && deletable(var2, var1)) {
                ++simpleDelClusters;
                simpleDelVerts += ((TreeSet) var0.get(var2)).size();
                ((TreeSet) var0.get(var2)).clear();
            }
        }

    }

    private static boolean deletable(int var0, List<TreeSet<Integer>> var1) {
        TreeSet var3 = (TreeSet) var1.get(var0);
        Iterator var4 = var3.iterator();

        int var2;
        do {
            if (!var4.hasNext()) {
                return false;
            }

            var2 = ((Integer) var4.next()).intValue();
        } while (((TreeSet) var1.get(var2)).contains(Integer.valueOf(var0)) && var2 >= var0);

        return true;
    }

    private static void simplifyClusters(List<TreeSet<Integer>> var0) {
        int var5 = var0.size();
        if (var5 > 1) {
            SetPair[] var6 = new SetPair[var5];

            int var1;
            for (var1 = 0; var1 < var5; ++var1) {
                var6[var1] = new SetPair(var1, (TreeSet) var0.get(var1));
            }

            var0.clear();
            Arrays.sort(var6);

            for (var1 = 0; var1 < var5 - 1; ++var1) {
                boolean var3 = true;

                for (int var2 = var1 + 1; var2 < var5; ++var2) {
                    if (var6[var2].set.containsAll(var6[var1].set)) {
                        ++delClusters;
                        delVerts += var6[var1].set.size();
                        var3 = false;
                        break;
                    }
                }

                if (var3) {
                    var0.add(var6[var1].set);
                }
            }

            var0.add(var6[var1].set);
        }
    }

    private static List<TreeSet<Integer>> contiguous(List<TreeSet<Integer>> var0, List<HashSet<Integer>> var1) {
        ArrayList var5 = new ArrayList();

        for (int var2 = 0; var2 < var0.size(); ++var2) {
            TreeSet var3 = (TreeSet) var0.get(var2);

            while (!var3.isEmpty()) {
                TreeSet var4 = contiguous(((Integer) var3.iterator().next()).intValue(), var3, var1);
                var5.add(var4);
                var3.removeAll(var4);
            }
        }

        return var5;
    }

    private static TreeSet<Integer> contiguous(int var0, TreeSet<Integer> var1, List<HashSet<Integer>> var2) {
        LinkedList var4 = new LinkedList();
        TreeSet var5 = new TreeSet();
        var4.add(Integer.valueOf(var0));
        var5.add(Integer.valueOf(var0));

        while (!var4.isEmpty()) {
            var0 = ((Integer) var4.removeFirst()).intValue();
            HashSet var3 = new HashSet((Collection) var2.get(var0));
            var3.retainAll(var1);
            var3.removeAll(var5);
            var4.addAll(var3);
            var5.addAll(var3);
        }

        return var5;
    }

    private static void writeBiClusters(List<TreeSet<Integer>> var0, String var1, List<String> var2, List<String> var3, int var4) {
        try {
            PrintStream var12 = new PrintStream(new FileOutputStream(var1));

            for (int var5 = 0; var5 < var0.size(); ++var5) {
                TreeSet var8 = (TreeSet) var0.get(var5);
                int var6 = var8.size();
                if (var6 > 0) {
                    TreeSet var9 = new TreeSet();
                    TreeSet var10 = new TreeSet();
                    Iterator var11 = var8.iterator();

                    while (var11.hasNext()) {
                        int var7 = ((Integer) var11.next()).intValue();
                        if (var7 < var4) {
                            var9.add(Integer.valueOf(var7));
                        } else {
                            var10.add(Integer.valueOf(var7 - var4));
                        }
                    }

                    writeCluster(var9, var12, var2);
                    var12.print("\t");
                    writeCluster(var10, var12, var3);
                    var12.println();
                }
            }
        } catch (Exception var13) {
            System.out.println("writeBiClusters error " + var13.toString());
            var13.printStackTrace();
            System.exit(1);
        }

    }

    private static void writeClusters(List<TreeSet<Integer>> var0, PrintStream psm, List<String> var2) {
    	System.out.println("COPRA算法计算结果为：");
        for (int var3 = 0; var3 < var0.size(); ++var3) {
            TreeSet var5 = (TreeSet) var0.get(var3);
            int var4 = var5.size();
            if (var4 > 0) {
                writeCluster(var5, psm, var2);
                psm.println();
            }
        }

    }

    private static void writeCluster(TreeSet<Integer> var0, PrintStream psm, List<String> var2) {
        int var5 = var0.size();
        Integer[] var6 = new Integer[var5];
        var0.toArray(var6);
        String[] var7 = new String[var5];

        int var3;
        for (var3 = 0; var3 < var5; ++var3) {
            int var4 = var6[var3].intValue();
            if (var2 == null) {
                var7[var3] = var4 + "";
            } else {
                var7[var3] = (String) var2.get(var4);
            }
        }
        Arrays.sort(var7);
        //var7存储结果
        for(int i = 0; i < var5; i++) {
        	System.out.print(var7[i] + " ");
        }
        System.out.println();
        for (var3 = 0; var3 < var5; ++var3) {
            psm.print(var7[var3] + " ");
        }

    }

    private static List<ClusterLabel> simpleClustering(int var0, float var1) {
        ArrayList var4 = new ArrayList();

        for (int var2 = 0; var2 < var0; ++var2) {
            ClusterLabel var3 = new ClusterLabel(var0, var1, var2, true);
            var4.add(var3);
        }

        return var4;
    }


    private static List<ClusterLabel> readClusters(String var0, int var1, float var2, Map<String, Integer> var3) {
        int var7 = 0;
        String[] var9 = null;
        ArrayList var10 = new ArrayList();

        try {
            int var4;
            String var8;
            for (BufferedReader var11 = new BufferedReader(new FileReader(var0)); (var8 = var11.readLine()) != null; ++var7) {
                var9 = var8.split(" ");
                byte var5;
                if (var9[0].endsWith(":")) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                for (var4 = var5; var4 < Array.getLength(var9); ++var4) {
                    int var6 = ((Integer) var3.get(var9[var4])).intValue();

                    while (var10.size() < var6 + 1) {
                        var10.add(new ClusterLabel(var1, var2));
                    }

                    ((ClusterLabel) var10.get(var6)).add(var7);
                }
            }

            for (var4 = 0; var4 < var10.size(); ++var4) {
                ((ClusterLabel) var10.get(var4)).normalize();
            }
        } catch (Exception var12) {
            System.err.println("readClusters error: " + var12.toString());
            var12.printStackTrace();
            System.exit(1);
        }

        return var10;
    }
}