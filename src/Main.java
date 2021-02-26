import java.io.*;
import java.util.*;

public class Main {
	public static Scanner sc;
	public static ArrayList < Edge > [] graph;
	public static HashMap<String, Integer> endIntersection;
	public static HashMap<String, Integer> length;
	public static PriorityQueue<Car> carsQ;
	public static HashMap<Integer, Schedule> resultHM;

	public static void setRW() {
		// DONT FORGET TO CHANGE BUILD SYSTEM to RunJava
		// call the function
		// setRW();
		try {
			System.setIn(new FileInputStream("C:\\Users\\rouna\\Desktop\\Codes\\HashCode\\a.txt"));
			System.setOut(new PrintStream(new FileOutputStream("C:\\Users\\rouna\\Desktop\\Codes\\HashCode\\ao.txt")));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static class Edge {

		int src;
		int des;
		String st_name;
		int wt;

		Edge(int src, int des, String st_name, int wt) {
			this.src = src;
			this.des = des;
			this.st_name = st_name;
			this.wt = wt;
		}
	}

	public static class Car implements Comparable<Car> {
		int id;
		int carId;
		int totalDist;
		int totalEdge;
		ArrayList<String> path;

		Car(int id, int totalDist, int totalEdge, ArrayList<String> path) {
			this.id = id;
			this.totalDist = totalDist;
			this.totalEdge = totalEdge;
			this.path = path;
		}

		@Override
		public int compareTo(Car o) {
			return this.totalDist - o.totalDist;
		}
	}

	public static class Schedule {

		// HM storing the streets and their green light time
		HashMap<String, Integer> scheduleHM;
		Schedule(HashMap<String, Integer> scd) {

			this.scheduleHM = scd;
		}
	}


// -------MAIN------

	public static void main(String[] args) throws Exception {
		// setRW();
		sc = new Scanner(System.in);


		endIntersection = new HashMap<>();
		length = new HashMap<>();
		carsQ = new PriorityQueue<>();
		resultHM = new HashMap<>();

		int duration = sc.nextInt();
		int vtces = sc.nextInt();
		int edges = sc.nextInt();
		int cars = sc.nextInt();
		int score = sc.nextInt();

		graph = new ArrayList[vtces];
		for (int i = 0; i < vtces; i++) {
			graph[i] = new ArrayList < > ();
		}

		for (int i = 0; i < edges; i++) {
			int src = sc.nextInt();
			int des = sc.nextInt();
			String st_name = sc.next();
			int wt = sc.nextInt();

			graph[des].add(new Edge(des, src, st_name, wt));
			endIntersection.put(st_name, des);
			length.put(st_name, wt);
		}


		for (int id = 0; id < cars; id++) {

			int totalEdges = sc.nextInt();
			ArrayList<String> al = new ArrayList<>();
			int totalDist = 0;
			for (int ed = 0; ed < totalEdges; ed++) {
				String street = sc.next();
				al.add(street);
				totalDist += length.get(street);
			}

			carsQ.add(new Car(id, totalDist, totalEdges, al));
		}

		// WORK

		while (carsQ.size() > 0) {
			Car car = carsQ.remove();
			System.err.println(carsQ.size());

			for (int i = 0; i < car.path.size(); i++) {

				String street = car.path.get(i);
				int inter = endIntersection.get(street);

				if (i == 0) {
					// +2 for 1st path
					if (resultHM.containsKey(inter)) {
						Schedule s = resultHM.get(inter);
						// s.streetCount++;
						if (s.scheduleHM.containsKey(street)) {
							s.scheduleHM.put(street, Math.min(s.scheduleHM.get(street) + 2 , duration));
						} else {

							s.scheduleHM.put(street, 2);
						}


					} else {
						HashMap<String, Integer> hmp = new HashMap<>();
						hmp.put(street, 2);
						Schedule s = new Schedule(hmp);
						resultHM.put(inter, s);
					}

				} else {
					//+1 for rest
					if (resultHM.containsKey(inter)) {
						Schedule s = resultHM.get(inter);
						// s.streetCount++;
						if (s.scheduleHM.containsKey(street)) {
							s.scheduleHM.put(street, Math.min(s.scheduleHM.get(street) + 1 , duration));
						} else {

							s.scheduleHM.put(street, 1);
						}


					} else {
						HashMap<String, Integer> hmp = new HashMap<>();
						hmp.put(street, 1);
						Schedule s = new Schedule(hmp);
						resultHM.put(inter, s);
					}

				}
			}
		}

		//SHOW
		System.out.println(resultHM.size());

		for (Integer it : resultHM.keySet()) {
			System.out.println(it);
			System.out.println(resultHM.get(it).scheduleHM.size());
			HashMap<String , Integer> resH = resultHM.get(it).scheduleHM;

			for (String path : resH.keySet()) {

				System.out.println(path + " " + resH.get(path));
			}

		}

	}
}