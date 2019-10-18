# SWExpertAcademy_MockTest_Java_1949

## SW Expert Academy 1949. [모의 SW 역량테스트] 등산로 조성

### 1. 문제설명

출처: https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PoOKKAPIDFAUq

input으로 지도의 한변의 길이 `N`과 깎을 수 있는 산의 깊이 `K`가 들어온다. 이어서 `N * N`의 산의 지도정보가 들어온다. 지도정보에서 값은 해당 지역의 높이를 의미한다. 지도에서 가장 높은 곳을 *봉우리* 라고 할때, 봉우리 부터 시작하는 등산로를 조성하려고한다. 등산로는 가로나 세로로 낮은 지역만으로만 조성할 수 있으며 한 지역에대해 최대 깊이 `K`만큼 깎아내여 더 긴 등산로를 조성할 수 있다. 이때, 가장 길게 만들 수 있는 조성로의 길이를 출력하는문제.

**추가 조건**

문제를 해결하는과정에서 잘 설명되지 않은 사항이다. 봉우리를 깎아내어 더이상 가장 높은 지점이 아니게되었더라도 시작지점은 깎기전 봉우리에서만 시작가능하기 때문에 두번째로 높은 봉우리들을 별도로 찾아서 적용할 필요가 없다. 그부분도 생각했었으나 이렇게하면 오답으로 처리되는 문항이있었다. 

[제약 사항]

    1. 시간 제한 : 최대 50개 테스트 케이스를 모두 통과하는 데 C/C++/Java 모두 3초
    2. 지도의 한 변의 길이 N은 3 이상 8 이하의 정수이다. (3 ≤ N ≤ 8)
    3. 최대 공사 가능 깊이 K는 1 이상 5 이하의 정수이다. (1 ≤ K ≤ 5)
    4. 지도에 나타나는 지형의 높이는 1 이상 20 이하의 정수이다.
    5. 지도에서 가장 높은 봉우리는 최대 5개이다.
    6. 지형은 정수 단위로만 깎을 수 있다.
    7. 필요한 경우 지형을 깎아 높이를 1보다 작게 만드는 것도 가능하다.

[입력]

> 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 `T`가 주어지고, 그 다음 줄부터 `T`개의 테스트 케이스가 주어진다.
> 각 테스트 케이스의 첫 번째 줄에는 지도의 한 변의 길이 `N`, 최대 공사 가능 깊이 `K`가 차례로 주어진다.
> 그 다음 `N`개의 줄에는 `N * N` 크기의 지도 정보가 주어진다.

[출력]

> 테스트 케이스 개수만큼 `T`개의 줄에 각각의 테스트 케이스에 대한 답을 출력한다.
> 각 줄은 `#t`로 시작하고 공백을 하나 둔 다음 정답을 출력한다. (`t`는 `1`부터 시작하는 테스트 케이스의 번호이다)
> 출력해야 할 정답은 만들 수 있는 가장 긴 등산로의 길이이다.


### 2. 풀이

input이 들어옴과 함께 지도정보를 이차원배열에 저장하고, 가장 높은 장소의 높이를 찾는다. 이후 봉우리들에 대해 저장할 객체를 만들어 위치와 높이 길이정보를 넣는다. 이때 길이라는 정보는 `1`로 초기화 하며, 가로세로에 더 낮은 지형으로 등산로를 개척해 간다면 길이를 `1`증가시켜 검사를 진행하도록 하였다. 이에대해서는 이후 더 자세히 설명한다.

최대 `K`만큼 깎을 수 있기 때문에 아무곳도 깎지않는 경우부터 모든곳에 대하여 `1`부터 `K`까지 깎는경우를 모두 만드어 수행한다.

```java
// 아무곳도 깎지 않는 경우
computeRoadLength(map);

// 모든곳에 대해 K만큼 깎는 경우
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

```

`computeRoadLength`는 봉우리에서부터 시작하여 만들수있는 가장 긴 조성로를 계산하는 함수이다. 시작시 찾은 봉우리중 하나를 꺼내 큐에넣는다. BFS를 이용하였으며 시작시 큐에서 담긴 위치정보를 꺼내 가로세로의 범위가 맵을 넘어가지 않는지 확인한후 높이가 더 낮다면 해당 방향으로 등산로를 조성한다. 이때 해당 위치에대해서 길이는 이전에 만든 위치에서 `1`더 추가된 등산로이기 때문에 `1`을더하여 넣어준다. 큐가 빌때까지 위의 작업을 반복하며 작업이 끝나면 만들어진 등산로의 길이가 이전에 검사한 등산로들중에 최대길이인지 비교하여 최대길이를 갱신해준다. 모든 경우에 대해 위 작업을 반복하여 가장 긴 등산로의 길이를 출력하여 해결하였다.


```java

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

	static int firstHighest;
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
			firstHighestLocations.clear();
			maxLength = 1;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					int height = Integer.parseInt(st.nextToken());
					if (height >= firstHighest)
						firstHighest = height;
					map[i][j] = height;
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] == firstHighest) {
						firstHighestLocations.add(new Location(i, j, map[i][j], 1));
					}
				}
			}


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


}


```
