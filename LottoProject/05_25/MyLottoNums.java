 
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

class MyLottoNums {
	private Integer[] nums;

	// �ڵ� ��ȣ �̱�
	public MyLottoNums() {
		nums = autoSelect(6);
	}

	public MyLottoNums(Integer... nums) {
		Arrays.sort(nums);
		this.nums = nums;
	}

	// 1~45���� 6���� �̾Ƴ��� �޼ҵ�
	protected Integer[] autoSelect(int count) {
		// ���� ��ü�� �̿��ؼ� ������ ����
		Random r = new Random();
		HashSet<Integer> set = new HashSet<Integer>();

		while (set.size() < count) {
			// == (int)(Math.random() * 45) + 1
			set.add(r.nextInt(45) + 1);
		}
		Integer[] nums = set.toArray(new Integer[0]);
		// 0~5���ε������� ����(6������)
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