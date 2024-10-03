package com.proj.metro_app;
//import com.proj.metro_app.Heap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.*;

@Service
public class Graph_M
{
	public class Vertex
	{
		HashMap<String, Integer> nbrs = new HashMap<>();
	}

	static HashMap<String, Vertex> vtces;
	public Graph_M()
	{
		vtces = new HashMap<>();
	}

	public boolean containsVertex(String vname)
	{
		return vtces.containsKey(vname);
	}

	public void addVertex(String vname)
	{
		Vertex vtx = new Vertex();
		vtces.put(vname, vtx);
	}

	public boolean containsEdge(String vname1, String vname2)
	{
		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);

		if (vtx1 == null || vtx2 == null || !vtx1.nbrs.containsKey(vname2)) {
			return false;
		}

		return true;
	}

	public void addEdge(String vname1, String vname2, int value)
	{
		Vertex vtx1 = vtces.get(vname1);
		Vertex vtx2 = vtces.get(vname2);

		if (vtx1 == null || vtx2 == null || vtx1.nbrs.containsKey(vname2)) {
			return;
		}

		vtx1.nbrs.put(vname2, value);
		vtx2.nbrs.put(vname1, value);
	}

	public String display_Map()
	{
		System.out.println("\t Chennai Metro Map");
		System.out.println("\t------------------");
		System.out.println("----------------------------------------------------\n");
		ArrayList<String> keys = new ArrayList<>(vtces.keySet());
		//ArrayList<String> map = new ArrayList<String>();
		String map = "";
		for (String key : keys)
		{
			String str = key + " =>\n"+"<br>";
			Vertex vtx = vtces.get(key);
			ArrayList<String> vtxnbrs = new ArrayList<>(vtx.nbrs.keySet());

			for (String nbr : vtxnbrs)
			{
				str = str + "\t" + "&emsp;" + nbr + "&emsp;"+"\t";
				if (nbr.length()<16)
					str = str + "\t"+"&emsp;";
				if (nbr.length()<8)
					str = str + "\t"+"&emsp;";
				str = str + vtx.nbrs.get(nbr) +"<br>" +"\n";
			}
			map+=str+"<br>";
			System.out.println(str);
		}
		System.out.println("&emsp;"+"\t------------------");
		System.out.println("---------------------------------------------------\n"+"<br>");
		return map;
	}

	public String display_Stations()
	{
		String result =" ";
		System.out.println("\n***********************************************************************\n");
		ArrayList<String> keys = new ArrayList<>(vtces.keySet());
		int i=1;
		for(String key : keys)
		{
			result+=i+". "+ key+"<br>";
			System.out.println(i + ". " + key);
			i++;
		}
		System.out.println("\n***********************************************************************\n");
		return result;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> processed)
	{
		// DIR EDGE
		if (containsEdge(vname1, vname2)) {
			return true;
		}

		//MARK AS DONE
		processed.put(vname1, true);

		Vertex vtx = vtces.get(vname1);
		ArrayList<String> nbrs = new ArrayList<>(vtx.nbrs.keySet());

		//TRAVERSE THE NBRS OF THE VERTEX
		for (String nbr : nbrs)
		{

			if (!processed.containsKey(nbr))
				if (hasPath(nbr, vname2, processed))
					return true;
		}

		return false;
	}


	private class DijkstraPair implements Comparable<DijkstraPair>
	{
		String vname;
		String psf;
		int cost;

			/*
			The compareTo method is defined in Java.lang.Comparable.
			Here, we override the method because the conventional compareTo method
			is used to compare strings,integers and other primitive data types. But
			here in this case, we intend to compare two objects of DijkstraPair class.
			*/

		/*
        Removing the overriden method gives us this errror:
        The type Graph_M.DijkstraPair must implement the inherited abstract method Comparable<Graph_M.DijkstraPair>.compareTo(Graph_M.DijkstraPair)

        This is because DijkstraPair is not an abstract class and implements Comparable interface which has an abstract
        method compareTo. In order to make our class concrete(a class which provides implementation for all its methods)
        we have to override the method compareTo
         */
		@Override
		public int compareTo(DijkstraPair o)
		{
			return o.cost - this.cost;
		}
	}

	public int dijkstra(String src, String des, boolean nan)
	{
		int val = 0;
		ArrayList<String> ans = new ArrayList<>();
		HashMap<String, DijkstraPair> map = new HashMap<>();

		Heap<DijkstraPair> heap = new Heap<>();

		for (String key : vtces.keySet())
		{
			DijkstraPair np = new DijkstraPair();
			np.vname = key;
			//np.psf = "";
			np.cost = Integer.MAX_VALUE;

			if (key.equals(src))
			{
				np.cost = 0;
				np.psf = key;
			}

			heap.add(np);
			map.put(key, np);
		}

		//keep removing the pairs while heap is not empty
		while (!heap.isEmpty())
		{
			DijkstraPair rp = heap.remove();

			if(rp.vname.equals(des))
			{
				val = rp.cost;
				break;
			}

			map.remove(rp.vname);

			ans.add(rp.vname);

			Vertex v = vtces.get(rp.vname);
			for (String nbr : v.nbrs.keySet())
			{
				if (map.containsKey(nbr))
				{
					int oc = map.get(nbr).cost;
					Vertex k = vtces.get(rp.vname);
					int nc;
					if(nan)
						nc = rp.cost + 120 + 40*k.nbrs.get(nbr);
					else
						nc = rp.cost + k.nbrs.get(nbr);

					if (nc < oc)
					{
						DijkstraPair gp = map.get(nbr);
						gp.psf = rp.psf + nbr;
						gp.cost = nc;

						heap.updatePriority(gp);
					}
				}
			}
		}
		return val;
	}

	private class Pair
	{
		String vname;
		String psf;
		int min_dis;
		int min_time;
	}

	public String Get_Minimum_Distance(String src, String dst)
	{
		int min = Integer.MAX_VALUE;
		//int time = 0;
		String ans = "";
		HashMap<String, Boolean> processed = new HashMap<>();
		LinkedList<Pair> stack = new LinkedList<>();

		// create a new pair
		Pair sp = new Pair();
		sp.vname = src;
		sp.psf = src + "  ";
		sp.min_dis = 0;
		sp.min_time = 0;

		// put the new pair in stack
		stack.addFirst(sp);

		// while stack is not empty keep on doing the work
		while (!stack.isEmpty())
		{
			// remove a pair from stack
			Pair rp = stack.removeFirst();

			if (processed.containsKey(rp.vname))
			{
				continue;
			}

			// processed put
			processed.put(rp.vname, true);

			//if there exists a direct edge b/w removed pair and destination vertex
			if (rp.vname.equals(dst))
			{
				int temp = rp.min_dis;
				if(temp<min) {
					ans = rp.psf;
					min = temp;
				}
				continue;
			}

			Vertex rpvtx = vtces.get(rp.vname);
			ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());

			for(String nbr : nbrs)
			{
				// process only unprocessed nbrs
				if (!processed.containsKey(nbr)) {

					// make a new pair of nbr and put in queue
					Pair np = new Pair();
					np.vname = nbr;
					np.psf = rp.psf + nbr + "  ";
					np.min_dis = rp.min_dis + rpvtx.nbrs.get(nbr);
					//np.min_time = rp.min_time + 120 + 40*rpvtx.nbrs.get(nbr);
					stack.addFirst(np);
				}
			}
		}
		ans = ans + Integer.toString(min);
		return ans;
	}


	public String Get_Minimum_Time(String src, String dst)
	{
		int min = Integer.MAX_VALUE;
		String ans = "";
		HashMap<String, Boolean> processed = new HashMap<>();
		LinkedList<Pair> stack = new LinkedList<>();

		// create a new pair
		Pair sp = new Pair();
		sp.vname = src;
		sp.psf = src + "  ";
		sp.min_dis = 0;
		sp.min_time = 0;

		// put the new pair in queue
		stack.addFirst(sp);

		// while queue is not empty keep on doing the work
		while (!stack.isEmpty()) {

			// remove a pair from queue
			Pair rp = stack.removeFirst();

			if (processed.containsKey(rp.vname))
			{
				continue;
			}

			// processed put
			processed.put(rp.vname, true);

			//if there exists a direct edge b/w removed pair and destination vertex
			if (rp.vname.equals(dst))
			{
				int temp = rp.min_time;
				if(temp<min) {
					ans = rp.psf;
					min = temp;
				}
				continue;
			}

			Vertex rpvtx = vtces.get(rp.vname);
			ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());

			for (String nbr : nbrs)
			{
				// process only unprocessed nbrs
				if (!processed.containsKey(nbr)) {

					// make a new pair of nbr and put in queue
					Pair np = new Pair();
					np.vname = nbr;
					np.psf = rp.psf + nbr + "  ";
					//np.min_dis = rp.min_dis + rpvtx.nbrs.get(nbr);
					np.min_time = rp.min_time + 120 + 40*rpvtx.nbrs.get(nbr);
					stack.addFirst(np);
				}
			}
		}
		Double minutes = Math.ceil((double)min / 60);
		ans = ans + Double.toString(minutes);
		return ans;
	}
	public static String distance(Graph_M g,String st1, String st2){
		System.out.println("SHORTEST DISTANCE FROM "+st1+" TO "+st2+" IS "+g.dijkstra(st1, st2, false)+"KM\n");
		return "SHORTEST DISTANCE FROM "+st1+" TO "+st2+" IS "+g.dijkstra(st1, st2, false)+"KM<br>";
	}
	public static String time(Graph_M g,String sat1, String sat2){
		System.out.println("SHORTEST TIME FROM ("+sat1+") TO ("+sat2+") IS "+g.dijkstra(sat1, sat2, true)/60+" MINUTES\n\n");
		return "SHORTEST TIME FROM ("+sat1+") TO ("+sat2+") IS "+g.dijkstra(sat1, sat2, true)/60+" MINUTES\n\n";
	}
	public ArrayList<String> get_Interchanges(String str)
	{
		ArrayList<String> arr = new ArrayList<>();
		String res[] = str.split("  ");
		arr.add(res[0]);
		int count = 0;
		for(int i=1;i<res.length-1;i++)
		{
			int index = res[i].indexOf('~');
			String s = res[i].substring(index+1);

			if(s.length()==2)
			{
				String prev = res[i-1].substring(res[i-1].indexOf('~')+1);
				String next = res[i+1].substring(res[i+1].indexOf('~')+1);

				if(prev.equals(next))
				{
					arr.add(res[i]);
				}
				else
				{
					arr.add(res[i]+" ==> "+res[i+1]);
					i++;
					count++;
				}
			}
			else
			{
				arr.add(res[i]);
			}
		}
		arr.add(Integer.toString(count));
		arr.add(res[res.length-1]);
		return arr;
	}
	public static String shortest_path(String s1,String s2,Graph_M g){
		HashMap<String, Boolean> processed2 = new HashMap<>();
		String res = "";
		if(!g.containsVertex(s1) || !g.containsVertex(s2) || !g.hasPath(s1, s2, processed2))
			System.out.println("THE INPUTS ARE INVALID");
		else
		{
			ArrayList<String> str = g.get_Interchanges(g.Get_Minimum_Distance(s1, s2));
			int len = str.size();
			System.out.println("SOURCE STATION : " + s1);
			res+="SOURCE STATION : " + s1 + "<br>";
			System.out.println("DESTINATION STATION : " + s2);
			res+="DESTINATION STATION : " + s2+"<br>";
			System.out.println("DISTANCE : " + str.get(len-1));
			res+="DISTANCE : " + str.get(len-1)+"<br>";
			System.out.println("NUMBER OF INTERCHANGES : " + str.get(len-2));
			res+="NUMBER OF INTERCHANGES : " + str.get(len-2) + "<br>";
			//System.out.println(str);
			System.out.println("~~~~~~~~~~~~~");
			System.out.println("START  ==>  " + str.get(0));
			res+="START  ==>  " + str.get(0)+"<br>";
			for(int i=1; i<len-3; i++)
			{
				System.out.println(str.get(i));
				res+=str.get(i)+"<br>";
			}
			System.out.print(str.get(len-3) + "   ==>    END");
			res+=str.get(len-3) + "   ==>    END<br>";
			System.out.println("\n~~~~~~~~~~~~~");
		}
		return res;
	}
	public static void Create_Metro_Map(Graph_M g)
	{
		g.addVertex("Airport~B");
		g.addVertex("Meenambakkam~B");
		g.addVertex("Alandur~BG");
		g.addVertex("Guindy~B");
		g.addVertex("Little Mount~B");
		g.addVertex("Saidapet~B");
		g.addVertex("Teynampet~B");
		g.addVertex("AG DMS~B");
		g.addVertex("Thousand Lights~B");
		g.addVertex("LIC~B");
		g.addVertex("Chennai Central~BGR");
		g.addVertex("High Court~B");
		g.addVertex("Mannadi~B");
		g.addVertex("Washermanpet~B");
		g.addVertex("St Thomas Mount~G");
		g.addVertex("Ekkattuthangal~G");
		g.addVertex("Ashok Nagar~G");
		g.addVertex("Vadapalani~G");
		g.addVertex("CMBT~G");
		g.addVertex("Koyembedu~G");
		g.addVertex("Thirumangalam~G");
		g.addVertex("Anna Nagar Tower~G");
		g.addVertex("Anna Nagar East~G");
		g.addVertex("Shenoy Nagar~G");
		g.addVertex("Kilpauk~G");
		g.addVertex("Egmore~G");
		g.addVertex("Velachery~R");
		g.addVertex("Taramanni~R");
		g.addVertex("Thiruvanmyur~RO");
		g.addVertex("Adayar~R");
		g.addVertex("Light House~R");
		g.addVertex("Chepauk~R");
		g.addVertex("Park Town~R");
		g.addVertex("Medavakkam~O");
		g.addVertex("Perumbakkam~O");
		g.addVertex("Shollinganallur~O");
		g.addVertex("Perungudi~O");
		g.addVertex("Thoraipakkam~O");

		//Blue Line
		g.addEdge("Airport~B", "Meenambakkam~B", 8);
		g.addEdge("Meenambakkam~B", "Alandur~BG", 10);
		g.addEdge("Alandur~BG", "Guindy~B", 7);
		g.addEdge("Guindy~B", "Little Mount~B", 6);
		g.addEdge("Little Mount~B", "Saidapet~B", 5);
		g.addEdge("Saidapet~B", "Teynampet~B", 7);
		g.addEdge("Teynampet~B", "AG DMS~B", 4);
		g.addEdge("AG DMS~B", "Thousand Lights~B", 8);
		g.addEdge("Thousand Lights~B", "LIC~B", 4);
		g.addEdge("LIC~B", "Chennai Central~BGR", 8);

		//Green line
		g.addEdge("St Thomas Mount~G", "Alandur~BG", 7);
		g.addEdge("Alandur~BG", "Ekkattuthangal~G", 7);
		g.addEdge("Ekkattuthangal~G", "Ashok Nagar~G", 7);
		g.addEdge("Ashok Nagar~G", "Vadapalani~G", 7);
		g.addEdge("Vadapalani~G", "CMBT~G", 7);
		g.addEdge("CMBT~G", "Koyembedu~G", 7);
		g.addEdge("Koyembedu~G", "Thirumangalam~G", 7);
		g.addEdge("Thirumangalam~G", "Anna Nagar Tower~G", 7);
		g.addEdge("Anna Nagar Tower~G", "Anna Nagar East~G", 7);
		g.addEdge("Anna Nagar East~G", "Shenoy Nagar~G", 7);
		g.addEdge("Shenoy Nagar~G", "Kilpauk~G", 7);
		g.addEdge("Kilpauk~G", "Egmore~G", 7);
		g.addEdge("Egmore~G", "Chennai Central~BGR", 7);

		//Red line
		g.addEdge("Velachery~R", "Taramani~R", 7);
		g.addEdge("Taramani~R", "Thiruvanmyur~RO", 7);
		g.addEdge("Thiruvanmyur~RO", "Adayar~R", 7);
		g.addEdge("Adayar~R", "Light House~R", 7);
		g.addEdge("Light House~R", "Chepauk~R", 7);
		g.addEdge("Chepauk~R", "Park Town~R", 7);
		g.addEdge("Park Town~R", "Chennai Central~BGR", 7);

		//orange line
		g.addEdge("Medavakkam~O", "Perumbakkam~O", 7);
		g.addEdge("Perumbakkam~O", "Shollinganallur~O", 7);
		g.addEdge("Shollinganallur~O", "Perungudi~O", 7);
		g.addEdge("Perungudi~O", "Thoraipakkam~O", 7);
		g.addEdge("Thoraipakkam~O", "Thiruvanmyur~RO", 7);
	}
}