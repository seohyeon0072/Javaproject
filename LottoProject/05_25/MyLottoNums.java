 
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

class MyLottoNums {
	private Integer[] nums;

	// 자동 번호 뽑기
	public MyLottoNums() {
		nums = autoSelect(6);
	}

	public MyLottoNums(Integer... nums) {
		Arrays.sort(nums);
		this.nums = nums;
	}

	// 1~45까지 6개를 뽑아내는 메소드
	protected Integer[] autoSelect(int count) {
		// 랜덤 객체를 이용해서 난수를 뽑음
		Random r = new Random();
		HashSet<Integer> set = new HashSet<Integer>();

		while (set.size() < count) {
			// == (int)(Math.random() * 45) + 1
			set.add(r.nextInt(45) + 1);
		}
		Integer[] nums = set.toArray(new Integer[0]);
		// 0~5번인덱스까지 정렬(6개뽑음)
		Arrays.sort(nums, 0, 6);
		return nums;
	}

	public Integer[] getNums() {
		return nums;
	}

	public void setNums(Integer... nums) {
		this.nums = nums;
	}

}