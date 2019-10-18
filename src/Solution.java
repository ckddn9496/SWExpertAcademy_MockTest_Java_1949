import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

	static class Location {
		int x, y;
		int h;
		int len;

		public Location(int x, int y, int h, int len) {
			this.x = x;
			this.y = y;
			this.h = h;
			this.len = len;
		}

		public String toString() {
			return "(" + x + ", " + y + ") = " + h + " : " + len;
		}
	}

	static int[][] map;
	static int[][] temp;
	static int N, K;

	static List<Location> firstHighestLocations = new ArrayList<>();
//	static List<Location> secondHighestLocations = new ArrayList<>();

	static int firstHighest;
//	static int secondHighest;
	static int maxLength;

	static int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N];

			firstHighest = 0;
//			secondHighest = 0;
			firstHighestLocations.clear();
//			secondHighestLocations.clear();
			maxLength = 1;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					int height = Integer.parseInt(st.nextToken());
					if (height >= firstHighest)
						firstHighest = height;
//					else if (height > secondHighest) {
//						secondHighest = height;
//					}
					map[i][j] = height;
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] == firstHighest) {
						firstHighestLocations.add(new Location(i, j, map[i][j], 1));
					}
//					else if (map[i][j] == secondHighest) {
//						secondHighestLocations.add(new Location(i, j, map[i][j], 1));
//					}
				}
			}

//			System.out.println(firstHighestLocations);
//			System.out.println(secondHighestLocations);

			computeRoadLength(map);

			for (int k = 1; k <= K; k++) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (map[i][j] < k)
							continue;
						temp = new int[N][N];
						for (int r = 0; r < N; r++) {
							for (int c = 0; c < N; c++) {
								temp[r][c] = map[r][c];
							}
						}
						temp[i][j] = temp[i][j] - k;
						computeRoadLength(temp);
					}
				}
			}

			System.out.println("#" + test_case + " " + maxLength);
		}

	}

	private static void computeRoadLength(int[][] tempMap) {
//		boolean hasFirstHighestLocation = false;
//		for (int[] line : tempMap) {
//			if (Arrays.stream(line).anyMatch(h -> h == firstHighest)) {
//				hasFirstHighestLocation = true;
//			}
//		}

//		if (hasFirstHighestLocation) {
		for (int locIdx = 0; locIdx < firstHighestLocations.size(); locIdx++) {
			Location loc = firstHighestLocations.get(locIdx);
			
			Queue<Location> q = new LinkedList<>();
			q.add(new Location(loc.x, loc.y, tempMap[loc.x][loc.y], 1));
			int len = 1;
			while (!q.isEmpty()) {
				Location curLoc = q.poll();
				if (len < curLoc.len) // 이전에 검사를 한 지역이나 생성할 수 있는 등산로의 길이가 더 짧은경우 교체
					len = curLoc.len;

				for (int i = 0; i < dir.length; i++) {
					int nextX = curLoc.x + dir[i][0];
					int nextY = curLoc.y + dir[i][1];

					if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
						if (curLoc.h > tempMap[nextX][nextY])
							q.add(new Location(nextX, nextY, tempMap[nextX][nextY], curLoc.len + 1));
					}
				}
			}

			if (maxLength < len) {
				maxLength = len;
			}
		}

//		} 
//		else { // 두번째 큰 숫자위치에서 스타트
//			for (int locIdx = 0; locIdx < secondHighestLocations.size(); locIdx++) {
//				Location loc = secondHighestLocations.get(locIdx);
//				if (map[loc.x][loc.y] != secondHighest)
//					continue;
//
//				Queue<Location> q = new LinkedList<>();
//				q.add(loc);
//				int len = 1;
//				while (!q.isEmpty()) {
//					Location curLoc = q.poll();
//					if (len < curLoc.len)
//						len = curLoc.len;
//
//					for (int i = 0; i < dir.length; i++) {
//						int nextX = curLoc.x + dir[i][0];
//						int nextY = curLoc.y + dir[i][1];
//
//						if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
//							if (curLoc.h > map[nextX][nextY])
//								q.add(new Location(nextX, nextY, map[nextX][nextY], curLoc.len + 1));
//						}
//					}
//				}
//
//				if (maxLength < len)
//					maxLength = len;
//			}
//
//		}

	}

//	private static void printMap(int[][] tempMap) {
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(tempMap[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//
//	}

}
