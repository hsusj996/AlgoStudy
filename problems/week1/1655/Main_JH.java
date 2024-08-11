import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main_JH {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        // 시각적으로 생각한다면 maxHeap이 왼쪽 minHeap이 오른쪽이 됨
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // 이유 maxHeap은 내림차순으로 기준해야 peek가 큰 값이 됨.
        // 그래야 기준이 maxHeap의 루트보다 작은 값은 maxHeap에 추가하게 됨
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(br.readLine());

            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                // 기준은 항상 maxHeap의 루트보다 작으면 max로 감
                maxHeap.offer(num);
            } else { // maxHeap의 루트보다 크면 min으로 감
                minHeap.offer(num);
            }

            if (maxHeap.size() > minHeap.size() + 1) {
                minHeap.offer(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }


            bw.write(maxHeap.peek() + "\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}